package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.presenter.GamePresenter;

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

        view.getBtnStartGame().setOnAction(event -> {

            GameView gameView = new GameView();
            new GamePresenter(gameView, mainMenuView);

            view.getPane().getScene().setRoot(gameView.getPane());
        });
    }
}