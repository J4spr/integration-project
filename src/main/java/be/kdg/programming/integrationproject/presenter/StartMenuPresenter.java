package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.StartMenuView;

public class StartMenuPresenter {

    private final StartMenuView view;
    private final MainMenuView mainMenuView;

    public StartMenuPresenter(StartMenuView view, MainMenuView mainMenuView) {

        this.view = view;
        this.mainMenuView = mainMenuView;

        addEventHandlers();
    }

    private void addEventHandlers() {

        view.getBtnBack().setOnAction(event ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );

        view.getBtnStartGame().setOnAction(event ->
                System.out.println("Game should start now")
        );
    }
}