package be.kdg.programming.integrationproject.view.resultsScreen;

import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.model.HumanPlayer;
import be.kdg.programming.integrationproject.model.Player;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.scene.layout.StackPane;

/**
 * Controller class responsible for parsing endgame metrics, building performance summaries,
 * and managing post-game navigation links.
 *
 * @author Team 4
 * @version 1.0
 */
public class ResultsScreenPresenter {
    private final Game game;
    private final ResultsScreenView view;
    private final MainMenuView mainMenuView;
    private final StackPane gameRoot;

    /**
     * Binds endgame presentation logic to navigation handlers.
     *
     * @param game         completed domain match transaction engine framework session
     * @param view         results screen panel layout display node instance configuration
     * @param mainMenuView landing menu interface mapping handling fall-back tasks
     * @param gameRoot     top-level container stack pane used to reset active scenes
     */
    public ResultsScreenPresenter(Game game, ResultsScreenView view, MainMenuView mainMenuView, StackPane gameRoot) {
        this.game = game;
        this.view = view;
        this.mainMenuView = mainMenuView;
        this.gameRoot = gameRoot;
        initializeView();
        addEventHandlers();
    }

    /**
     * Pulls final balances and grid coverage metrics from the game model
     * to populate player score labels on the results screen.
     */
    private void initializeView() {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Player winner = game.getWinner();

        String nameP1 = ((HumanPlayer) p1).getName();
        String nameP2 = p2 instanceof HumanPlayer ? ((HumanPlayer) p2).getName() : "CPU";
        String winnerName = winner instanceof HumanPlayer ? ((HumanPlayer) winner).getName() : "CPU";

        int scoreP1 = game.calculateScore(p1);
        int scoreP2 = game.calculateScore(p2);

        String specialTileOwner = null;
        if (game.getSpecialTileOwner() != null) {
            specialTileOwner = game.getSpecialTileOwner() instanceof HumanPlayer
                    ? ((HumanPlayer) game.getSpecialTileOwner()).getName()
                    : "CPU";
        }
        view.setResults(winnerName, scoreP1, nameP1, scoreP2, nameP2, specialTileOwner);
    }

    /**
     * Attaches button click listeners to guide users back to the dashboard.
     */
    private void addEventHandlers() {
        view.getBtnMainMenu().setOnAction(e ->
                gameRoot.getScene().setRoot(mainMenuView.getPane())
        );
    }
}