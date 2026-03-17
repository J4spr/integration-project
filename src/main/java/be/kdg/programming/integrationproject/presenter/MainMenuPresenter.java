package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainMenuPresenter {
    private final MainMenuView mmv;
    private RulesView rv;
    private SettingsView sv;
    private StartMenuView smv;
    private LeaderBoardView lbv;

    public MainMenuPresenter(MainMenuView view) {
        this.smv = new StartMenuView();
        this.rv = new RulesView();
        this.mmv = view;
        this.sv = new SettingsView();
        this.lbv = new LeaderBoardView();
        addEventHandlers();
    }

    private void addEventHandlers() {
        mmv.getStartButton().setOnAction(event -> {
            new StartMenuPresenter(smv, mmv);

            mmv.getPane().getScene().setRoot(smv.getPane());
        });
        mmv.getRulesButton().setOnAction(event -> {
            new RulesPresenter(rv, mmv);

            mmv.getPane().getScene().setRoot(rv.getPane());
        });
        mmv.getSettingsButton().setOnAction(event -> {
            new SettingsPresenter(sv, mmv);
            System.out.println("Settings pressed");
            mmv.getPane().getScene().setRoot(sv.getPane());
        });
        mmv.getLeaderboardButton().setOnAction(event -> {
            new LeaderBoardPresenter(lbv, mmv);
            System.out.println("Leaderboard pressed");
            mmv.getPane().getScene().setRoot(lbv.getPane());
        });
    }
}
