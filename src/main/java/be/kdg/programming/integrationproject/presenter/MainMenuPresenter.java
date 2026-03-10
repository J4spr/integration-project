package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import javafx.scene.image.Image;

public class MainMenuPresenter {
    private final MainMenuView view;

    public MainMenuPresenter(MainMenuView view) {
        this.view = view;
        addEventHandlers();
    }
    private void addEventHandlers() {
        view.getStartButton().setOnAction(event ->
                System.out.println("Start Pressed"));
        view.getRulesButton().setOnAction(event ->
                System.out.println("Rules Pressed"));
        view.getSettingsButton().setOnAction(event ->
                System.out.println("Settings Pressed"));
    }
}
