package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.dao.GameDao;
import be.kdg.programming.integrationproject.dao.PlayerDao;
import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import javafx.scene.control.Alert;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartMenuPresenter {

    private final StartMenuView view;
    private final MainMenuView mainMenuView;
    private PlayerDao playerDao; // Initialize this in your constructor
    private GameDao gameDao;
    private DbConnection conn;

    public StartMenuPresenter(StartMenuView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        addEventHandlers();
        this.conn = new DbConnection();
        this.gameDao = new GameDao(this.conn);
        this.playerDao = new PlayerDao(this.conn);
    }

    private void addEventHandlers() {
        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );

        view.getBtnStartGame().setOnAction(e -> startGame());
    }

    private void startGame() {
        //validate that the player has entered a name
        if (view.getPlayerName().isEmpty()) {
            showWarning("Please enter your name.");
            return;
        }

        try {
            HumanPlayer player1 = new HumanPlayer(view.getPlayerName());
            player1.setColor(view.getSelectedTokenColor());

            checkWithDb(player1);

            CpuPlayer player2 = new CpuPlayer(view.getSelectedDifficulty());
            player2.setPlayerId(0); // Indicator that this is not a DB user
            player2.setColor(pickCpuColor(view.getSelectedTokenColor()));

            Game game = new Game(player1, player2, view.getStartPlayer());

            gameDao.insert(game);

            String colorP1 = tokenColorToHex(player1.getColor());
            String colorP2 = tokenColorToHex(player2.getColor());

            GameView gameView = new GameView(player1.getName(), colorP1, "CPU", colorP2);
            new GamePresenter(game, gameView, mainMenuView);

            view.getPane().getScene().setRoot(gameView.getPane());

        } catch (SQLException e) {
            showWarning("Database Error: Could not start game. " + e.getMessage());
            e.printStackTrace();
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

    private void checkWithDb(Player player) {
        // AI doesn't go in DB
        if (!(player instanceof HumanPlayer)) return;

        Player existing = null;
        try {
            existing = playerDao.findByUsername(((HumanPlayer) player).getName());
        } catch (SQLException e) {
            System.err.printf("Exited with message: %s", e.getMessage());
        }

        if (existing != null) {
            // Use the ID from the database
            player.setPlayerId(existing.getPlayerId());
        } else {
            // Create new player in DB and the DAO will set the generated ID back on the object
            try {
                playerDao.insert(player);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}