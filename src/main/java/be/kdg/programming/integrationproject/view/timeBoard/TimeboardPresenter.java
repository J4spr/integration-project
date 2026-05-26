package be.kdg.programming.integrationproject.view.timeBoard;

import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.view.game.GamePresenter;
import be.kdg.programming.integrationproject.view.game.GameView;

/**
 * Controller class that coordinates state updates for the timeboard track display.
 * <p>
 * Pulls active player location metrics from core model instances and updates
 * position tokens within the user interface.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class TimeboardPresenter {
    /** The game manager model tracking active location variables. */
    private final Game game;
    /** The primary composite board user interface structure containing sub-view nodes. */
    private final GameView view;
    /** Reference pointer to the parent game manager presenter used to access color styling tools. */
    private final GamePresenter gamePresenter;

    /**
     * Instantiates a new {@code TimeboardPresenter} to handle display track updates.
     *
     * @param game          the core match logic tracking data instance reference
     * @param view          the central window component panel manager pointer
     * @param gamePresenter the root logic controller tracking system configurations link
     */
    public TimeboardPresenter(Game game, GameView view, GamePresenter gamePresenter) {
        this.game = game;
        this.view = view;
        this.gamePresenter = gamePresenter;
    }

    /**
     * Synchronizes display track cell states with the current positioning metrics
     * of both players.
     */
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