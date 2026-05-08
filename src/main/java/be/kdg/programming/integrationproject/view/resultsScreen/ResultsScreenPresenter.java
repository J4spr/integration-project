package be.kdg.programming.integrationproject.view.resultsScreen;

import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.model.HumanPlayer;
import be.kdg.programming.integrationproject.model.Player;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.scene.layout.StackPane;

public class ResultsScreenPresenter {
    private final Game game;
    private final ResultsScreenView view;
    private final MainMenuView mainMenuView;
    //the root pane of the game screen, used to navigate back to main menu
    private final StackPane gameRoot;

    public ResultsScreenPresenter(Game game, ResultsScreenView view, MainMenuView mainMenuView, StackPane gameRoot) {
        this.game = game;
        this.view = view;
        this.mainMenuView = mainMenuView;
        this.gameRoot = gameRoot;
        initializeView();
        addEventHandlers();
    }

    private void initializeView() {
        Player p1 = game.getPlayer1();
        Player p2 = game.getPlayer2();
        Player winner = game.getWinner();

        String nameP1 = ((HumanPlayer) p1).getName();
        String nameP2 = p2 instanceof HumanPlayer ? ((HumanPlayer) p2).getName() : "CPU";
        String winnerName = winner instanceof HumanPlayer ? ((HumanPlayer) winner).getName() : "CPU";

        //use the model's calculateScore method to ensure consistency with game logic
        int scoreP1 = game.calculateScore(p1);
        int scoreP2 = game.calculateScore(p2);

        //show who owns the special tile if anyone does
        String specialTileOwner = null;
        if (game.getSpecialTileOwner() != null) {
            specialTileOwner = game.getSpecialTileOwner() instanceof HumanPlayer
                    ? ((HumanPlayer) game.getSpecialTileOwner()).getName()
                    : "CPU";
        }
        view.setResults(winnerName, scoreP1, nameP1, scoreP2, nameP2, specialTileOwner);
    }

    private void addEventHandlers() {
        view.getBtnMainMenu().setOnAction(e ->
                gameRoot.getScene().setRoot(mainMenuView.getPane())
        );
    }
}