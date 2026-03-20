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
        //load the shared background image
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        Image bgImage = new Image(path);
        BackgroundImage background = new BackgroundImage(
                bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT
        );
        root.setBackground(new Background(background));

        btnStart = new Button("Start");
        btnStart.setPrefWidth(150);
        btnStart.setPrefHeight(35);

        btnRules = new Button("Rules");
        btnRules.setPrefWidth(150);
        btnRules.setPrefHeight(35);

        btnSettings = new Button("Settings");
        btnSettings.setPrefWidth(150);
        btnSettings.setPrefHeight(35);

        btnLeaderBoard = new Button("Leaderboard");
        btnLeaderBoard.setPrefWidth(150);
        btnLeaderBoard.setPrefHeight(35);

        btnExit = new Button("Exit to Desktop");
        btnExit.setPrefWidth(150);
        btnExit.setPrefHeight(35);
        contentBox = new VBox(15, btnStart, btnRules, btnSettings, btnExit);
    }

    private void layoutNodes() {
        //content box with all buttons centered inside a styled box
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        contentBox.setMaxWidth(250);
        contentBox.setMaxHeight(250);
        contentBox.setStyle("-fx-border-color: #aaaaaa; -fx-border-radius: 8; -fx-background-color: white; -fx-background-radius: 8;");
        //StackPane centers the contentBox and scales naturally with window resize
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getChildren().add(contentBox);
    }

    public void showConfirmationOverlay(String message, Runnable onConfirm) {
        StackPane overlay = new StackPane();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);"); // Darken background

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

        root.getChildren().add(overlay);
    }

    public StackPane getPane() {
        return root;
    }

    public Button getStartButton() {
        return btnStart;
    }

    public Button getRulesButton() {
        return btnRules;
    }

    public Button getSettingsButton() {
        return btnSettings;
    }

    public Button getLeaderboardButton(){return  btnLeaderBoard;}

    public Button getBtnExit() {
        return btnExit;
    }
}