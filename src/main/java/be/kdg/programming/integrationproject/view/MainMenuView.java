package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class MainMenuView {
    private Button startButton;
    private GridPane pane;

    public MainMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        Label label = new Label("Start");


    }

    private void layoutNodes() {
        pane.setPadding(new Insets(15));

    }

    public Button getStartButton() {
        return this.startButton;
    }

    public GridPane getPane() {
        return this.pane;
    }
}
