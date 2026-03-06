package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainMenuView {
    private Button startButton;
    private Button rulesButton;
    private Button settingsButton;

    private GridPane pane;

    public MainMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        pane = new GridPane();

        startButton = new Button("Start");
        rulesButton = new Button("Rules");
        settingsButton = new Button("Settings");
    }

    private void layoutNodes() {
        pane.setPadding(new Insets(30));
        pane.setVgap(10);

        pane.add(startButton, 0, 0);
        pane.add(rulesButton, 0, 1);
        pane.add(settingsButton, 0, 2);
    }

    public Button getStartButton() {
        return startButton;
    }
    public Button getRulesButton() {
        return rulesButton;
    }
    public Button getSettingsButton(){
        return settingsButton;
    }
    public GridPane getPane() {
        return pane;
    }

}
