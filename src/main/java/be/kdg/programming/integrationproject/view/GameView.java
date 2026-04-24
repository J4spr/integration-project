package be.kdg.programming.integrationproject.view;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class GameView {
    // Constants for Layout
    private static final int PATCH_STORE_SIZE = 3;
    private static final double PATCH_SLOT_WIDTH = 140;
    private static final double PATCH_SLOT_HEIGHT = 120;
    private static final double BUTTON_WIDTH = 100;
    private static final double BUTTON_HEIGHT = 35;
    private static final double SIDE_PANE_WIDTH = 340;

    // Main Containers
    private StackPane root;
    private BorderPane gamePane;
    private VBox leftPane;
    private VBox rightPane;

    // Sub-views
    private QuiltboardView quiltboardViewP1;
    private QuiltboardView quiltboardViewP2;
    private TimeboardView timeboardView;

    // Background
    private Image bgImage;
    private BackgroundImage background;

    // Patch Store Components
    private VBox[] patchSlotWrappers;
    private GridPane[] patchSlots;

    // Controls
    private Button btnPass;
    private Button btnRotate;
    private Button btnQuit;

    // Confirmation Overlay Components
    private Label lblConfirmationMessage;
    private Button btnConfirmQuit;
    private Button btnCancelQuit;
    private VBox confirmationContentBox;
    private StackPane confirmationOverlay;

    public GameView(String nameP1, String colorP1, String nameP2, String colorP2) {
        this.quiltboardViewP1 = new QuiltboardView(nameP1, colorP1);
        this.quiltboardViewP2 = new QuiltboardView(nameP2, colorP2);
        this.timeboardView = new TimeboardView();
        this.patchSlots = new GridPane[PATCH_STORE_SIZE];
        this.patchSlotWrappers = new VBox[PATCH_STORE_SIZE];

        this.initialiseNodes();
        this.layoutNodes();
    }

    private void initialiseNodes() {
        this.root = new StackPane();
        this.gamePane = new BorderPane();

        this.initBackground();
        this.initButtons();
        this.initPatchSlots();

        this.lblConfirmationMessage = new Label();
        this.btnConfirmQuit = new Button("Yes, quit");
        this.btnCancelQuit = new Button("Cancel");
    }

    private void initBackground() {
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        Image image = new Image(path);
        BackgroundSize bgSize = new BackgroundSize(150, 150, false, false, false, false);

        BackgroundImage background = new BackgroundImage(
                image,
                BackgroundRepeat.REPEAT,   // Repeat on X-axis
                BackgroundRepeat.REPEAT,   // Repeat on Y-axis
                BackgroundPosition.DEFAULT,
                bgSize
        );
        this.gamePane.setBackground(new Background(background));
    }

    private void initButtons() {
        this.btnPass = new Button("Pass");
        this.btnRotate = new Button("Rotate");
        this.btnQuit = new Button("Quit Game");

        for (Button btn : new Button[]{this.btnPass, this.btnRotate, this.btnQuit}) {
            btn.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            btn.setMinSize(BUTTON_WIDTH, BUTTON_HEIGHT);
            btn.setMaxSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        }
    }

    private void initPatchSlots() {
        for (int i = 0; i < PATCH_STORE_SIZE; i++) {
            this.patchSlots[i] = new GridPane();
            Label lblSlotNumber = new Label("Patch " + (i + 1));
            lblSlotNumber.setStyle("-fx-font-size: 11; -fx-font-weight: bold;");

            this.patchSlotWrappers[i] = new VBox(5, lblSlotNumber, this.patchSlots[i]);
            this.patchSlotWrappers[i].setAlignment(Pos.TOP_CENTER);
            this.patchSlotWrappers[i].setStyle("-fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-color: #fafafa; -fx-background-radius: 4;");
            this.patchSlotWrappers[i].setPadding(new Insets(6));

            this.patchSlotWrappers[i].setPrefSize(PATCH_SLOT_WIDTH, PATCH_SLOT_HEIGHT);
            this.patchSlotWrappers[i].setMinSize(PATCH_SLOT_WIDTH, PATCH_SLOT_HEIGHT);
            this.patchSlotWrappers[i].setMaxSize(PATCH_SLOT_WIDTH, PATCH_SLOT_HEIGHT);
        }
    }

    private void layoutNodes() {
        this.gamePane.setPadding(new Insets(15));

        this.leftPane = new VBox(10, this.quiltboardViewP1.getPane());
        this.leftPane.setAlignment(Pos.CENTER);
        this.leftPane.setPadding(new Insets(0, 10, 0, 0));
        this.leftPane.setMinWidth(SIDE_PANE_WIDTH);
        this.leftPane.setMaxWidth(SIDE_PANE_WIDTH);

        this.rightPane = new VBox(10, this.quiltboardViewP2.getPane());
        this.rightPane.setAlignment(Pos.CENTER);
        this.rightPane.setPadding(new Insets(0, 0, 0, 10));
        this.rightPane.setMinWidth(SIDE_PANE_WIDTH);
        this.rightPane.setMaxWidth(SIDE_PANE_WIDTH);

        this.gamePane.setLeft(this.leftPane);
        this.gamePane.setRight(this.rightPane);
        this.gamePane.setCenter(this.buildCenterPane());

        this.root.getChildren().add(this.gamePane);
    }

    private VBox buildCenterPane() {
        HBox topPatches = new HBox(10, this.patchSlotWrappers[0]);
        topPatches.setAlignment(Pos.CENTER);
        topPatches.setMaxHeight(PATCH_SLOT_HEIGHT);

        HBox bottomPatches = new HBox(10, this.patchSlotWrappers[1], this.patchSlotWrappers[2]);
        bottomPatches.setAlignment(Pos.CENTER);
        bottomPatches.setMaxHeight(PATCH_SLOT_HEIGHT);

        HBox controlBar = new HBox(15, this.btnPass, this.btnRotate, this.btnQuit);
        controlBar.setAlignment(Pos.CENTER);
        controlBar.setPadding(new Insets(10, 0, 0, 0));
        controlBar.setMinHeight(55);

        VBox centerPane = new VBox(8, topPatches, this.timeboardView.getPane(), bottomPatches, controlBar);
        centerPane.setAlignment(Pos.CENTER);
        return centerPane;
    }

    public void showWarningBanner(String message) {
        Label banner = new Label(message);
        banner.setStyle("-fx-background-color: #c62828; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-padding: 12 24 12 24; -fx-background-radius: 8;");
        StackPane.setAlignment(banner, Pos.TOP_CENTER);
        StackPane.setMargin(banner, new Insets(20, 0, 0, 0));
        this.root.getChildren().add(banner);

        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> this.root.getChildren().remove(banner));
        pause.play();
    }

    //shows a green info banner at the top of the screen for positive notifications like collecting a leather patch
    //auto-dismisses after 4 seconds, slightly longer than the warning banner to give the player time to read it
    public void showInfoBanner(String message) {
        Label banner = new Label(message);
        banner.setStyle("-fx-background-color: #2e7d32; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-padding: 12 24 12 24; -fx-background-radius: 8;");
        StackPane.setAlignment(banner, Pos.TOP_CENTER);
        StackPane.setMargin(banner, new Insets(20, 0, 0, 0));
        this.root.getChildren().add(banner);

        PauseTransition pause = new PauseTransition(Duration.seconds(4));
        pause.setOnFinished(e -> this.root.getChildren().remove(banner));
        pause.play();
    }

    public void showConfirmationOverlay(String message, Runnable onConfirm) {
        this.lblConfirmationMessage.setText(message);
        this.lblConfirmationMessage.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-text-alignment: center;");
        this.lblConfirmationMessage.setWrapText(true);
        this.lblConfirmationMessage.setMaxWidth(360);

        this.btnConfirmQuit.setStyle("-fx-background-color: #c62828; -fx-text-fill: white;");
        this.btnConfirmQuit.setPrefSize(120, 35);
        this.btnCancelQuit.setPrefSize(120, 35);

        HBox btnBar = new HBox(20, this.btnConfirmQuit, this.btnCancelQuit);
        btnBar.setAlignment(Pos.CENTER);

        this.confirmationContentBox = new VBox(20, this.lblConfirmationMessage, btnBar);
        this.confirmationContentBox.setAlignment(Pos.CENTER);
        this.confirmationContentBox.setPadding(new Insets(30));
        this.confirmationContentBox.setMaxSize(450, 160);
        this.confirmationContentBox.setStyle("-fx-background-color: white; -fx-border-color: black; -fx-border-width: 2; -fx-border-radius: 12; -fx-background-radius: 12;");

        this.confirmationOverlay = new StackPane(this.confirmationContentBox);
        this.confirmationOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        this.btnCancelQuit.setOnAction(e -> this.root.getChildren().remove(this.confirmationOverlay));
        this.btnConfirmQuit.setOnAction(e -> {
            this.root.getChildren().remove(this.confirmationOverlay);
            onConfirm.run();
        });

        this.root.getChildren().add(this.confirmationOverlay);
    }

    public void showResultsScreen(ResultsScreenView resultsScreenView) {
        this.root.getChildren().add(resultsScreenView.getPane());
    }

    public void updatePatchSlot(int slotIndex, boolean[][] shape, int patchId, int buttonCost, int timeCost, int buttonIncome) {
        GridPane slot = new GridPane();
        slot.setHgap(2);
        slot.setVgap(2);
        slot.setUserData(patchId);

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    Button cell = new Button();
                    cell.setPrefSize(18, 18);
                    cell.setStyle("-fx-background-color: #90caf9; -fx-border-color: #42a5f5;");
                    cell.setUserData(patchId);
                    slot.add(cell, c, r);
                }
            }
        }

        Label lblInfo = new Label("Cost: " + buttonCost + "  Time: " + timeCost + "  Income: " + buttonIncome);
        lblInfo.setStyle("-fx-font-size: 9;");
        slot.add(lblInfo, 0, shape.length, Math.max(shape[0].length, 1), 1);

        this.patchSlots[slotIndex] = slot;
        this.patchSlotWrappers[slotIndex].getChildren().set(1, slot);
    }

    public void highlightPatchSlot(int slotIndex) {
        for (int i = 0; i < PATCH_STORE_SIZE; i++) {
            this.patchSlotWrappers[i].setStyle(i == slotIndex
                    ? "-fx-border-color: #f57c00; -fx-border-width: 2; -fx-border-radius: 4; -fx-background-color: #fff9c4; -fx-background-radius: 4;"
                    : "-fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-color: #fafafa; -fx-background-radius: 4;");
        }
    }

    // Getters
    public StackPane getPane() { return this.root; }
    public QuiltboardView getQuiltboardViewP1() { return this.quiltboardViewP1; }
    public QuiltboardView getQuiltboardViewP2() { return this.quiltboardViewP2; }
    public TimeboardView getTimeboardView() { return this.timeboardView; }
    public GridPane getPatchSlot(int index) { return this.patchSlots[index]; }
    public VBox getPatchSlotWrapper(int index) { return this.patchSlotWrappers[index]; }
    public int getPatchStoreSize() { return PATCH_STORE_SIZE; }
    public Button getBtnPass() { return this.btnPass; }
    public Button getBtnRotate() { return this.btnRotate; }
    public Button getBtnQuit() { return this.btnQuit; }
}