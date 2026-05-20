package be.kdg.programming.integrationproject.view.settings;

import be.kdg.programming.integrationproject.model.ClearLeaderBoard;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * Controller class managing backend operations for user configuration updates.
 * <p>
 * Binds scene configuration controls to window rendering properties
 * and connects file wipe requests with underlying model engines.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class SettingsPresenter {
    /** The configuration view interface being controlled. */
    private SettingsView view;
    /** The parent main menu dashboard layout state view used for step-back routing. */
    private MainMenuView mainMenuView;
    /** The model logic engine responsible for running data table wipe procedures. */
    private ClearLeaderBoard clrlb;

    /**
     * Instantiates an active presenter pairing, mapping data hooks
     * and setting up event handling pathways.
     *
     * @param view         the targeted visual configuration panel instance reference
     * @param mainMenuView the step-back view destination pointer
     */
    public SettingsPresenter(SettingsView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        this.clrlb = new ClearLeaderBoard();
        addEventHandlers();
    }

    /**
     * Registers actions to handle full-screen mode swaps, scene root redirections,
     * and database wipe triggers.
     */
    private void addEventHandlers() {
        view.getFullscreenBtn().setOnAction(event -> {
            Stage stage = (Stage) view.getPane().getScene().getWindow();
            stage.setFullScreenExitHint("");
            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            stage.setFullScreen(!stage.isFullScreen());
        });

        view.getBtnBack().setOnAction(event -> view.getPane().getScene().setRoot(mainMenuView.getPane()));

        view.getClearLeaderBoardBtn().setOnAction(event -> {
            clrlb.executeClear();
            System.out.println("Clear in progress");
        });
    }
}