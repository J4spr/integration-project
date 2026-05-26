package be.kdg.programming.integrationproject.view.startMenu;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import be.kdg.programming.integrationproject.view.game.GamePresenter;
import be.kdg.programming.integrationproject.view.game.GameView;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.scene.control.Alert;
import be.kdg.programming.integrationproject.dao.GameDao;
import be.kdg.programming.integrationproject.dao.PlayerDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controller class that processes form data from the match startup menu.
 * <p>
 * Performs validation checks on character names, maps visual configurations, registers information
 * records into database entities, and links initialization states into active game panels.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class StartMenuPresenter {

    /** The underlying selection view component structure being updated. */
    private final StartMenuView view;
    /** The fallback parent menu frame used during backwards routing operations. */
    private final MainMenuView mainMenuView;

    /**
     * Assembles a presenter pair, binding user data handlers
     * to manage user profile forms initialization.
     *
     * @param view         the configuration panel view instance reference
     * @param mainMenuView the parent main menu controller path pointer
     */
    public StartMenuPresenter(StartMenuView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        addEventHandlers();
    }

    /**
     * Maps functional action triggers to view event listeners.
     */
    private void addEventHandlers() {
        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );

        view.getBtnStartGame().setOnAction(e -> startGame());
    }

    /**
     * Validates configuration parameters, stores model parameters, registers active database records,
     * and shifts active scene states into game components.
     */
    private void startGame() {
        //validate that the player has entered a name
        if (view.getPlayerName().isEmpty()) {
            showWarning("Please enter your name.");
            return;
        }

        HumanPlayer player1 = new HumanPlayer(view.getPlayerName());
        player1.setColor(view.getSelectedTokenColor());

        CpuPlayer player2 = new CpuPlayer(view.getSelectedDifficulty());
        try {PlayerDao playerDao = new PlayerDao(new DbConnection());

            playerDao.insert(player1);
            playerDao.insert(player2);
        }
        catch(Exception ex){ex.printStackTrace();}
        //pick a random color for the CPU that is different from player 1's color
        player2.setColor(pickCpuColor(view.getSelectedTokenColor()));

        Game game = new Game(player1, player2, view.getStartPlayer());
        try {GameDao dao = new GameDao(new DbConnection());

            int newGameId = dao.createNewPausedGame(player1.getPlayerId(), player2.getPlayerId(), view.getStartPlayer());
            game.setGameId(newGameId);
        }
        catch(Exception ex){ex.printStackTrace();}

        String colorP1 = tokenColorToHex(player1.getColor());
        String colorP2 = tokenColorToHex(player2.getColor());

        GameView gameView = new GameView(player1.getName(), colorP1, "CPU", colorP2);
        new GamePresenter(game, gameView, mainMenuView);

        view.getPane().getScene().setRoot(gameView.getPane());
    }

    /**
     * Randomly assigns an available token color variant to the computer player
     * to prevent duplicates with the human player.
     *
     * @param playerColor the configuration value chosen by the human user
     * @return a distinct {@code TokenColor} selection for the AI player
     */
    private TokenColor pickCpuColor(TokenColor playerColor) {
        List<TokenColor> colors = new ArrayList<>(List.of(TokenColor.values()));
        colors.remove(playerColor);
        Collections.shuffle(colors);
        return colors.get(0);
    }

    /**
     * Converts internal game token enum color states into hexadecimal text representations
     * for JavaFX CSS injection.
     *
     * @param color the enum value matching targeted tracking pieces
     * @return a hex code string (e.g., {@code "#ef5350"}) representing the color,
     * or a fallback default color string if unspecified
     */
    public String tokenColorToHex(TokenColor color) {
        if (color == null) return "#aaaaaa";
        return switch (color) {
            case RED -> "#ef5350";
            case GREEN -> "#66bb6a";
            case YELLOW -> "#ffee58";
            case BLUE -> "#42a5f5";
        };
    }

    /**
     * Triggers a modal popup warning window displaying explicit error tracking data to users.
     *
     * @param message the informational alert context string text
     */
    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}