package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import be.kdg.programming.integrationproject.view.StartMenuView;
import be.kdg.programming.integrationproject.presenter.StartMenuPresenter;

public class MainMenuPresenter {
    private final MainMenuView view;
    private RulesView rv;

    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        addEventHandlers();
    }
    private void addEventHandlers() {
        view.getStartButton().setOnAction(event -> {

            StartMenuView startMenuView = new StartMenuView();
            new StartMenuPresenter(startMenuView, view);

            view.getPane().getScene().setRoot(startMenuView.getPane());
        });
        view.getRulesButton().setOnAction(event -> {
            rv = new RulesView();
            new RulesPresenter(rv, view);

            view.getPane().getScene().setRoot(rv.getPane());
        });
        view.getSettingsButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Settings pressed");
            }
        });
    }
}
