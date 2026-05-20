package be.kdg.programming.integrationproject.view.mainMenu;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * Main dashboard view class. Builds tiled structural layouts,
 * renders control buttons, and handles verification overlays for exit requests.
 *
 * @author Team 4
 * @version 1.0
 */
public class MainMenuView {
    private Button btnStart;
    private Button btnRules;
    private Button btnSettings;
    private Button btnLeaderBoard;
    private Button btnExit;
    private Button btnContinue;

    private Image bgImage;
    private BackgroundSize bgSize;
    private BackgroundImage background;

    private StackPane stPane;
    private VBox contentBox;

    /**
     * Initializes structural element nodes and sets up background canvas configurations.
     */
    public MainMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Loads background image parameters, sets up menu buttons, and builds box layout containers.
     */
    private void initialiseNodes() {
        stPane = new StackPane();

        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        this.bgImage = new Image(path);
        this.bgSize = new BackgroundSize(150, 150, false, false, false, false);

        this.background = new BackgroundImage(
                this.bgImage,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                this.bgSize
        );

        stPane.setBackground(new Background(this.background));

        btnStart = createMenuButton("Start");
        btnRules = createMenuButton("Rules");
        btnSettings = createMenuButton("Settings");
        btnLeaderBoard = createMenuButton("Leaderboard");
        btnExit = createMenuButton("Exit to Desktop");
        btnContinue = createMenuButton("Continue Game");

        contentBox = new VBox(15, btnStart, btnContinue, btnRules, btnSettings, btnLeaderBoard, btnExit);
    }

    /**
     * Centers action containers while maintaining strict size constraints.
     */
    private void layoutNodes() {
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));

        contentBox.setMaxWidth(250);
        contentBox.setMaxHeight(320);

        contentBox.setStyle("-fx-background-color: transparent;");

        stPane.setAlignment(Pos.CENTER);
        stPane.setPadding(new Insets(-40));
        stPane.getChildren().setAll(contentBox);
    }

    /**
     * Factory utility formatting menu buttons to match standard widths and heights.
     *
     * @param text string literal descriptor label to imprint on the face of the node
     * @return a button adjusted to system dimensions
     */
    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setPrefHeight(35);
        return button;
    }

    /**
     * Darkens the background stage canvas and launches a confirmation dialog
     * before executing desktop shutdown commands.
     *
     * @param message   confirmation dialog text message
     * @param onConfirm operational callback lambda hook executing upon validation
     */
    public void showConfirmationOverlay(String message, Runnable onConfirm) {
        StackPane overlay = new StackPane();

        overlay.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        VBox dialog = new VBox(20);
        dialog.setAlignment(Pos.CENTER);
        dialog.setMaxSize(400, 200);
        dialog.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 10;");

        Label label = new Label(message);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox buttons = new HBox(20);
        buttons.setAlignment(Pos.CENTER);

        Button btnYes = createMenuButton("Yes, Exit");
        Button btnNo = createMenuButton("Cancel");

        btnYes.setOnAction(e -> onConfirm.run());
        btnNo.setOnAction(e -> stPane.getChildren().remove(overlay));

        buttons.getChildren().addAll(btnYes, btnNo);
        dialog.getChildren().addAll(label, buttons);

        StackPane.setAlignment(dialog, Pos.CENTER);
        overlay.getChildren().add(dialog);

        this.stPane.getChildren().add(overlay);
    }

    public StackPane getPane() { return stPane; }
    Button getStartButton() { return btnStart; }
    Button getRulesButton() { return btnRules; }
    Button getSettingsButton() { return btnSettings; }
    Button getLeaderboardButton() { return btnLeaderBoard; }
    Button getBtnExit() { return btnExit; }
    Button getContinueButton() { return btnContinue; }
}