package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import be.kdg.programming.integrationproject.view.SettingsView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainMenuPresenter {
    private final MainMenuView mmv;
    private RulesView rv;
    private SettingsView sv;
    private StartMenuView smv;

    public MainMenuPresenter(MainMenuView view) {
        smv = new StartMenuView();
        rv = new RulesView();
        this.mmv = view;
        sv = new SettingsView();
        addEventHandlers();
    }

    private void addEventHandlers() {
        mmv.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new StartMenuPresenter(smv, mmv);

                mmv.getPane().getScene().setRoot(smv.getPane());
            }
        });
        mmv.getRulesButton().setOnAction(event -> {
            new RulesPresenter(rv, mmv);

            mmv.getPane().getScene().setRoot(rv.getPane());
        });
        mmv.getSettingsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new SettingsPresenter(sv, mmv);
                System.out.println("Settings pressed");
                mmv.getPane().getScene().setRoot(sv.getPane());
            }
        });
    }
}
