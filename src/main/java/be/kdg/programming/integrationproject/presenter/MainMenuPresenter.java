package be.kdg.programming.integrationproject.presenter;


import be.kdg.programming.integrationproject.view.*;

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

        view.getRulesButton().setOnAction(e -> {
            RulesView rulesView = new RulesView();
            new RulesPresenter(rulesView, view);
            view.getPane().getScene().setRoot(rulesView.getPane());
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
