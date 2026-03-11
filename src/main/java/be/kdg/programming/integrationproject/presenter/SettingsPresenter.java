package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

public class SettingsPresenter {
    private SettingsView view;
    private MainMenuView mainMenuView;

    public SettingsPresenter(SettingsView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getFullscreenBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = (Stage) view.getPane().getScene().getWindow();
                stage.setFullScreen(!stage.isFullScreen());
            }
        });

        view.getBtnBack().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                view.getPane().getScene().setRoot(mainMenuView.getPane());
            }
        });
    }
}
