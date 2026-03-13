package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import be.kdg.programming.integrationproject.view.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import be.kdg.programming.integrationproject.view.StartMenuView;
import be.kdg.programming.integrationproject.presenter.StartMenuPresenter;

public class MainMenuPresenter {
    private final MainMenuView mmv;
    private RulesView rv;
    private SettingsView sv;
    private StartMenuView smv;

    public MainMenuPresenter(MainMenuView view) {
        this.mmv = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        mmv.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            smv = new StartMenuView();
            new StartMenuPresenter(smv,mmv);

            smv.getPane().getScene().setRoot(smv.getPane());
            }
        });
        mmv.getRulesButton().setOnAction(event -> {
            rv = new RulesView();
            new RulesPresenter(rv, mmv);

            mmv.getPane().getScene().setRoot(rv.getPane());
        });
        mmv.getSettingsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sv = new SettingsView();
                new SettingsPresenter(sv, mmv);
                System.out.println("Settings pressed");
                mmv.getPane().getScene().setRoot(sv.getPane());
            }
        });
    }
}
