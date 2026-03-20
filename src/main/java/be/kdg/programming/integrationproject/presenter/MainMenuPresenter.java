package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import be.kdg.programming.integrationproject.view.SettingsView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import be.kdg.programming.integrationproject.view.LeaderboardView;
import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.view.LeaderboardView;
import be.kdg.programming.integrationproject.presenter.LeaderboardPresenter;
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

        view.getLeaderboardButton().setOnAction(e -> {
            LeaderboardView leaderboardView = new LeaderboardView();
            DbConnection dbConnection = new DbConnection();
            new LeaderboardPresenter(leaderboardView, view, dbConnection);
            view.getPane().getScene().setRoot(leaderboardView.getPane());
        });

        //exit the application completely
        view.getBtnExit().setOnAction(e -> {
            view.showConfirmationOverlay(
                    "Are you sure you want to exit to desktop?",
                    () -> Platform.exit()
            );
        });
    }
}