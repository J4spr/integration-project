package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import be.kdg.programming.integrationproject.view.SettingsView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import javafx.application.Platform;

public class MainMenuPresenter {
    private final MainMenuView view;

    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartButton().setOnAction(e -> {
            StartMenuView startMenuView = new StartMenuView();
            new StartMenuPresenter(startMenuView, view);
            view.getPane().getScene().setRoot(startMenuView.getPane());
        });

        view.getRulesButton().setOnAction(e -> {
            RulesView rulesView = new RulesView();
            new RulesPresenter(rulesView, view);
            view.getPane().getScene().setRoot(rulesView.getPane());
        });

        view.getSettingsButton().setOnAction(e -> {
            SettingsView settingsView = new SettingsView();
            new SettingsPresenter(settingsView, view);
            view.getPane().getScene().setRoot(settingsView.getPane());
        });
        //exit the application completely
        view.getBtnExit().setOnAction(e -> Platform.exit());
    }
}