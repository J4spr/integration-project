package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import be.kdg.programming.integrationproject.view.SettingsView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class MainMenuPresenter {
    private final MainMenuView view;
    private RulesView rv;
    private SettingsView sv;


    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getStartButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                TODO remove this when it is ready for production
                System.out.println("Start pressed");
            }
        });
        view.getRulesButton().setOnAction(event -> {
            rv = new RulesView();
            new RulesPresenter(rv, view);

            view.getPane().getScene().setRoot(rv.getPane());
        });
        view.getSettingsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                sv = new SettingsView();
                new SettingsPresenter(sv, view);
                System.out.println("Settings pressed");
                view.getPane().getScene().setRoot(sv.getPane());
            }
        });
    }
}
