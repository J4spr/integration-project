package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenuView {
    private StackPane root;

    private Button btnStart;
    private Button btnRules;
    private Button btnSettings;
    private Button btnLeaderBoard;
    private Button btnExit;

    private VBox contentBox;

    public MainMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        root = new StackPane();

        // Load the shared background image
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        Image bgImage = new Image(path);
        BackgroundImage background = new BackgroundImage(
                bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        this.root.setBackground(new Background(background));

        this.btnStart = new Button("Start");
        this.btnStart.setPrefWidth(150);
        this.btnStart.setPrefHeight(35);

        this.btnRules = new Button("Rules");
        this.btnRules.setPrefWidth(150);
        this.btnRules.setPrefHeight(35);

        this.btnSettings = new Button("Settings");
        this.btnSettings.setPrefWidth(150);
        this.btnSettings.setPrefHeight(35);

        this.btnLeaderBoard = new Button("Leaderboard");
        this.btnLeaderBoard.setPrefWidth(150);
        this.btnLeaderBoard.setPrefHeight(35);

        this.btnExit = new Button("Exit to Desktop");
        this.btnExit.setPrefWidth(150);
        this.btnExit.setPrefHeight(35);

        // Added btnLeaderBoard to the VBox here so it appears in the menu
        this.contentBox = new VBox(15, btnStart, btnRules, btnSettings, btnLeaderBoard, btnExit);
    }

    private void layoutNodes() {
        // Style the content box (the white menu container)
        this.contentBox.setAlignment(Pos.CENTER);
        this.contentBox.setPadding(new Insets(30));
        this.contentBox.setMaxWidth(250);
        // Increased height to 320 to fit the extra Leaderboard button comfortably
        this.contentBox.setMaxHeight(320);
        this.contentBox.setStyle("-fx-border-color: #aaaaaa; -fx-border-radius: 8; -fx-background-color: white; -fx-background-radius: 8;");

        // Configure the root StackPane
        this.root.setAlignment(Pos.CENTER);
        this.root.setPadding(new Insets(40));

        // Use setAll to prevent duplicate children errors if this method is called again
        this.root.getChildren().setAll(contentBox);
    }

    public void showConfirmationOverlay(String message, Runnable onConfirm) {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");

        VBox dialog = new VBox(20);
        dialog.setAlignment(Pos.CENTER);
        dialog.setMaxSize(400, 200);
        dialog.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10;");

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        Button btnYes = new Button("Yes, Exit");
        Button btnNo = new Button("Cancel");

        btnYes.setOnAction(e -> onConfirm.run());
        btnNo.setOnAction(e -> root.getChildren().remove(overlay));

        buttons.getChildren().addAll(btnYes, btnNo);
        dialog.getChildren().addAll(label, buttons);
        overlay.getChildren().add(dialog);

        this.root.getChildren().add(overlay);
    }

    public StackPane getPane() {
        return this.root;
    }

    public Button getStartButton() {
        return this.btnStart;
    }

    public Button getRulesButton() {
        return this.btnRules;
    }

    public Button getSettingsButton() {
        return this.btnSettings;
    }

    public Button getLeaderboardButton() {
        return this.btnLeaderBoard;
    }

    public Button getBtnExit() {
        return this.btnExit;
    }
}