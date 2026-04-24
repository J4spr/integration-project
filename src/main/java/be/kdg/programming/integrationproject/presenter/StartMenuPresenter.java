package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartMenuPresenter {

    private final StartMenuView view;
    private final MainMenuView mainMenuView;

    public StartMenuPresenter(StartMenuView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );

        view.getBtnStartGame().setOnAction(e -> startGame());
    }

    private void startGame() {
        // Check of de speler wel een naam heeft ingevuld
        if (view.getPlayerName().isEmpty()) {
            showWarning("Please enter your name.");
            return;
        }

        // Maak de Java objecten aan voor de spelers
        HumanPlayer player1 = new HumanPlayer(view.getPlayerName());
        player1.setUsername(view.getPlayerName());
        // We vullen een e-mail in om aan de CHECK constraint in de database te voldoen
        player1.setEmail(view.getPlayerName().toLowerCase() + "@student.kdg.be");
        player1.setColor(view.getSelectedTokenColor());

        CpuPlayer player2 = new CpuPlayer(view.getSelectedDifficulty());
        player2.setUsername("CPU_" + view.getSelectedDifficulty());
        player2.setEmail("cpu@patchwork.com");
        player2.setColor(pickCpuColor(view.getSelectedTokenColor()));

        // Sla de spelers en de game sessie op
        try {

            DbConnection db = new DbConnection();
            be.kdg.programming.integrationproject.dao.PlayerDao playerDao = new be.kdg.programming.integrationproject.dao.PlayerDao(db);
            be.kdg.programming.integrationproject.dao.GameDao gameDao = new be.kdg.programming.integrationproject.dao.GameDao(db);

            // Sla de spelers op in de database
            playerDao.insert(player1);
            playerDao.insert(player2);

            // Maak het Game object aan.
            // We geven "CPU_GAME" mee als type (zoals afgesproken in het aangepaste Game model).
            Game game = new Game(player1, player2, view.getStartPlayer(), "CPU_GAME");

            // Sla de start van de game sessie op in de database
            gameDao.insert(game);

            // Ga naar het spelscherm
            String colorP1 = tokenColorToHex(player1.getColor());
            String colorP2 = tokenColorToHex(player2.getColor());

            // Maak de GameView aan met de namen uit de database/objecten
            GameView gameView = new GameView(player1.getUsername(), colorP1, player2.getUsername(), colorP2);

            // Start de GamePresenter die het spel verder afhandelt
            new GamePresenter(game, gameView, mainMenuView);

            view.getPane().getScene().setRoot(gameView.getPane());

        } catch (java.sql.SQLException e) {
            // Als er iets misgaat met de database (bijv. Postgres staat uit),
            // tonen we een foutmelding en printen we de stacktrace voor debugging.
            e.printStackTrace();
            showWarning("Fout bij het opslaan in de database. Controleer of de database draait.");
        }
    }

    //picks a random TokenColor that is not the same as the human player's color
    private TokenColor pickCpuColor(TokenColor playerColor) {
        List<TokenColor> colors = new ArrayList<>(List.of(TokenColor.values()));
        colors.remove(playerColor);
        Collections.shuffle(colors);
        return colors.get(0);
    }

    //converts a TokenColor enum to a hex color string usable in JavaFX CSS
    private String tokenColorToHex(TokenColor color) {
        if (color == null) return "#aaaaaa";
        return switch (color) {
            case RED -> "#ef5350";
            case GREEN -> "#66bb6a";
            case YELLOW -> "#ffee58";
            case BLUE -> "#42a5f5";
        };
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}