package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartMenuView {
    private Label title;
    private Label info;
    private VBox pane;

    private Button btnStartGame;
    private Button btnBack;

    public StartMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        pane = new VBox();
        title = new Label("Start Menu");
        info = new Label("Press start to begin.");
        btnStartGame = new Button("Start Game");
        btnBack = new Button("Back");
    }

    private void layoutNodes() {
        pane.setPadding(new Insets(30));
        pane.setSpacing(15);
        pane.getChildren().addAll(title, info, btnStartGame, btnBack);
    }

    public VBox getPane() {
        return pane;
    }

    public Button getBtnStartGame() {
        return btnStartGame;
    }

    public Button getBtnBack() {
        return btnBack;
    }
}