package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.ClearLeaderBoard;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class SettingsPresenter {
    private SettingsView view;
    private MainMenuView mainMenuView;
    private ClearLeaderBoard clrlb;

    public SettingsPresenter(SettingsView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        this.clrlb = new ClearLeaderBoard();
        addEventHandlers();
    }

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
