package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StartMenuView {

    private VBox root;

    private Label title;
    private Label info;

    private Button btnStartGame;
    private Button btnBack;

    public StartMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {

        root = new VBox(20);
        root.setPadding(new Insets(30));

        title = new Label("Start Patchwork");
        info = new Label("Start a new game against CPU.");

        btnStartGame = new Button("Start Game");
        btnBack = new Button("Back");
    }

    private void layoutNodes() {

        root.getChildren().addAll(
                title,
                info,
                btnStartGame,
                btnBack
        );
    }

    public VBox getPane() { return root; }

    public Button getBtnStartGame() { return btnStartGame; }

    public Button getBtnBack() { return btnBack; }
}