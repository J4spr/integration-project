package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.CpuPlayer;
import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.model.HumanPlayer;
import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.StartMenuView;

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

        // Create players (later we can take name + difficulty from UI)
        HumanPlayer player1 = new HumanPlayer("Player 1");
        CpuPlayer player2 = new CpuPlayer(Difficulty.EASY);

        player1.setPlayerId(1);
        player2.setPlayerId(2);

        // Create game model
        Game game = new Game(player1, player2, 1);

        // Create game screen
        GameView gameView = new GameView();

        // Create presenter that connects model + view
        new GamePresenter(game, gameView, mainMenuView);

        // Switch scene
        view.getPane().getScene().setRoot(gameView.getPane());
    }
}