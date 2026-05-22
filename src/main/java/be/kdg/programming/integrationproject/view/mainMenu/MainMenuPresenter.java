package be.kdg.programming.integrationproject.view.mainMenu;

import be.kdg.programming.integrationproject.view.rules.RulesPresenter;
import be.kdg.programming.integrationproject.view.settings.SettingsPresenter;
import be.kdg.programming.integrationproject.view.startMenu.StartMenuPresenter;
import be.kdg.programming.integrationproject.view.unfinishedGames.UnfinishedGamesPresenter;
import be.kdg.programming.integrationproject.view.leaderboard.LeaderBoardPresenter;
import be.kdg.programming.integrationproject.view.leaderboard.LeaderBoardView;
import be.kdg.programming.integrationproject.view.rules.RulesView;
import be.kdg.programming.integrationproject.view.settings.SettingsView;
import be.kdg.programming.integrationproject.view.startMenu.StartMenuView;
import be.kdg.programming.integrationproject.view.unfinishedGames.UnfinishedGamesView;
import javafx.application.Platform;

/**
 * Controller class coordinating navigation pathways across all secondary
 * module panels branching off from the main dashboard menu.
 *
 * @author Team 4
 * @version 1.0
 */
public class MainMenuPresenter {
    private final MainMenuView mmv;
    private RulesView rv;
    private SettingsView sv;
    private StartMenuView smv;
    private LeaderBoardView lbv;

    /**
     * Binds sub-panel views to the dashboard control framework.
     *
     * @param view primary parent dashboard interactive menu layout view node instance
     */
    public MainMenuPresenter(MainMenuView view) {
        this.smv = new StartMenuView();
        this.rv = new RulesView();
        this.mmv = view;
        this.sv = new SettingsView();
        this.lbv = new LeaderBoardView();
        addEventHandlers();
    }

    /**
     * Links interface navigation controls to scene root transformation hooks.
     */
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

        mmv.getContinueButton().setOnAction(e -> {
            UnfinishedGamesView ugv = new UnfinishedGamesView();
            new UnfinishedGamesPresenter(ugv, mmv);
            mmv.getPane().getScene().setRoot(ugv.getPane());
        });
    }
}