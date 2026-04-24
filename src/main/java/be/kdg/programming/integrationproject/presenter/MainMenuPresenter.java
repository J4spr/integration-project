package be.kdg.programming.integrationproject.presenter;


import be.kdg.programming.integrationproject.view.*;
import javafx.application.Platform;

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

        mmv.getRulesButton().setOnAction(e -> {
            RulesView rulesView = new RulesView();
            new RulesPresenter(rulesView, mmv);
            mmv.getPane().getScene().setRoot(rulesView.getPane());
        });
        mmv.getSettingsButton().setOnAction(event -> {
            new SettingsPresenter(sv, mmv);
            System.out.println("Settings pressed");
            mmv.getPane().getScene().setRoot(sv.getPane());
        });
        mmv.getLeaderboardButton().setOnAction(event -> {
            this.lbv = new LeaderBoardView();
            new LeaderBoardPresenter(lbv, mmv);

            mmv.getPane().getScene().setRoot(lbv);
        });
        mmv.getBtnExit().setOnAction(e -> {
            mmv.showConfirmationOverlay(
                    "Are you sure you want to exit to desktop?",
                    () -> Platform.exit()
            );
        });
        mmv.getContinueButton().setOnAction(e->{UnfinishedGamesView ugv= new UnfinishedGamesView();

                    new UnfinishedGamesPresenter(ugv, mmv);

                    mmv.getPane().getScene().setRoot(ugv.getPane());
                });

    }
}
