package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenuView {
    // Buttons
    private Button btnStart;
    private Button btnRules;
    private Button btnSettings;
    private Button btnLeaderBoard;
    private Button btnExit;

    // Containers
    private StackPane stPane;
    private VBox contentBox;

    public MainMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        stPane = new StackPane();

        // 1. Setup Background
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        Image bgImage = new Image(path);

        // Using 150x150 size as per your original logic
        BackgroundSize bgSize = new BackgroundSize(150, 150, false, false, false, false);

        BackgroundImage background = new BackgroundImage(
                bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                bgSize
        );

        stPane.setBackground(new Background(background));

        // 2. Initialize Buttons with a helper to keep code DRY
        btnStart = createMenuButton("Start");
        btnRules = createMenuButton("Rules");
        btnSettings = createMenuButton("Settings");
        btnLeaderBoard = createMenuButton("Leaderboard");
        btnExit = createMenuButton("Exit to Desktop");

        // 3. Setup ContentBox (Transparent by default now)
        contentBox = new VBox(15, btnStart, btnRules, btnSettings, btnLeaderBoard, btnExit);
    }

    private void layoutNodes() {
        // Center the buttons within the VBox
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));

        // Set constraints so the VBox doesn't stretch to fill the whole screen
        contentBox.setMaxWidth(250);
        contentBox.setMaxHeight(320);

        /* * FIX: We only call setStyle once here.
         * Background is transparent so the white box disappears.
         */
        contentBox.setStyle("-fx-background-color: transparent;");

        // Configure the root StackPane
        stPane.setAlignment(Pos.CENTER);
        stPane.setPadding(new Insets(-40));
        stPane.getChildren().setAll(contentBox);
    }

    /**
     * Helper method to reduce repetitive button configuration code
     */
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(35);
        return button;
    }

    public void showConfirmationOverlay(String message, Runnable onConfirm) {
        StackPane overlay = new StackPane();

        // FIX 1: This makes the dark background cover the entire window
        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        VBox dialog = new VBox(20);
        dialog.setAlignment(Pos.CENTER);
        dialog.setMaxSize(400, 200);
        dialog.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10;");

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        // Reuse your helper method to keep them looking nice!
        Button btnYes = createMenuButton("Yes, Exit");
        Button btnNo = createMenuButton("Cancel");

        btnYes.setOnAction(e -> onConfirm.run());
        btnNo.setOnAction(e -> stPane.getChildren().remove(overlay));

        buttons.getChildren().addAll(btnYes, btnNo);
        dialog.getChildren().addAll(label, buttons);

        // FIX 2: Ensure the white box stays in the dead center of the screen
        StackPane.setAlignment(dialog, Pos.CENTER);
        overlay.getChildren().add(dialog);

        this.stPane.getChildren().add(overlay);
    }

    // Getters
    public StackPane getPane() { return stPane; }
    public Button getStartButton() { return btnStart; }
    public Button getRulesButton() { return btnRules; }
    public Button getSettingsButton() { return btnSettings; }
    public Button getLeaderboardButton() { return btnLeaderBoard; }
    public Button getBtnExit() { return btnExit; }
}