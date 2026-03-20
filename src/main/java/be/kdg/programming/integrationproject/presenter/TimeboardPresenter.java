package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.view.GameView;

public class TimeboardPresenter {
    private final Game game;
    private final GameView view;
    //reference to the game presenter to access the color conversion utility
    private final GamePresenter gamePresenter;

    public TimeboardPresenter(Game game, GameView view, GamePresenter gamePresenter) {
        this.game = game;
        this.view = view;
        this.gamePresenter = gamePresenter;
    }

    //initializes the timeboard view based on the current player positions
    public void initializeView() {
        String colorP1 = gamePresenter.tokenColorToHex(game.getPlayer1().getColor());
        String colorP2 = gamePresenter.tokenColorToHex(game.getPlayer2().getColor());
        view.getTimeboardView().update(
                game.getPlayer1().getPosition(),
                game.getPlayer2().getPosition(),
                colorP1,
                colorP2
        );
    }
}