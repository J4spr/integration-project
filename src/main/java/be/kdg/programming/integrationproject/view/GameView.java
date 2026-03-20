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
    private StackPane root;
    private BorderPane gamePane;
    //sub-views
    private QuiltboardView quiltboardViewP1;
    private QuiltboardView quiltboardViewP2;
    private TimeboardView timeboardView;
    //stable wrapper VBoxes for each patch slot so layout references never change
    private VBox[] patchSlotWrappers;
    //the actual GridPanes are rebuilt on every update but kept inside the stable wrappers
    private GridPane[] patchSlots;
    private static final int PATCH_STORE_SIZE = 3;
    //fixed sizes for patch slot wrappers so they never rescale
    private static final double PATCH_SLOT_WIDTH = 140;
    private static final double PATCH_SLOT_HEIGHT = 120;
    //control buttons
    private Button btnPass;
    private Button btnRotate;
    private Button btnQuit;

    public GameView(String nameP1, String colorP1, String nameP2, String colorP2) {
        quiltboardViewP1 = new QuiltboardView(nameP1, colorP1);
        quiltboardViewP2 = new QuiltboardView(nameP2, colorP2);
        timeboardView = new TimeboardView();
        patchSlots = new GridPane[PATCH_STORE_SIZE];
        patchSlotWrappers = new VBox[PATCH_STORE_SIZE];
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        root = new StackPane();
        gamePane = new BorderPane();

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
        gamePane.setBackground(new Background(background));

        //fixed size buttons that never rescale
        btnPass = new Button("Pass");
        btnPass.setPrefWidth(100);
        btnPass.setPrefHeight(35);
        btnPass.setMinWidth(100);
        btnPass.setMaxWidth(100);
        btnPass.setMinHeight(35);
        btnPass.setMaxHeight(35);

        btnRotate = new Button("Rotate");
        btnRotate.setPrefWidth(100);
        btnRotate.setPrefHeight(35);
        btnRotate.setMinWidth(100);
        btnRotate.setMaxWidth(100);
        btnRotate.setMinHeight(35);
        btnRotate.setMaxHeight(35);

        btnQuit = new Button("Quit Game");
        btnQuit.setPrefWidth(100);
        btnQuit.setPrefHeight(35);
        btnQuit.setMinWidth(100);
        btnQuit.setMaxWidth(100);
        btnQuit.setMinHeight(35);
        btnQuit.setMaxHeight(35);

        //initialise each patch slot with a fixed size wrapper so they never rescale
        for (int i = 0; i < PATCH_STORE_SIZE; i++) {
            patchSlots[i] = new GridPane();

            Label lblSlotNumber = new Label("Patch " + (i + 1));
            lblSlotNumber.setStyle("-fx-font-size: 11; -fx-font-weight: bold;");

            patchSlotWrappers[i] = new VBox(5, lblSlotNumber, patchSlots[i]);
            patchSlotWrappers[i].setAlignment(Pos.TOP_CENTER);
            patchSlotWrappers[i].setStyle("-fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-color: #fafafa; -fx-background-radius: 4;");
            patchSlotWrappers[i].setPadding(new Insets(6));
            //fixed size so patch previews never affect the layout
            patchSlotWrappers[i].setPrefWidth(PATCH_SLOT_WIDTH);
            patchSlotWrappers[i].setMinWidth(PATCH_SLOT_WIDTH);
            patchSlotWrappers[i].setMaxWidth(PATCH_SLOT_WIDTH);
            patchSlotWrappers[i].setPrefHeight(PATCH_SLOT_HEIGHT);
            patchSlotWrappers[i].setMinHeight(PATCH_SLOT_HEIGHT);
            patchSlotWrappers[i].setMaxHeight(PATCH_SLOT_HEIGHT);
        }
    }

    private void layoutNodes() {
        gamePane.setPadding(new Insets(15));

        //left: player 1 quiltboard with fixed width
        VBox leftPane = new VBox(10, quiltboardViewP1.getPane());
        leftPane.setAlignment(Pos.CENTER);
        leftPane.setPadding(new Insets(0, 10, 0, 0));
        leftPane.setMinWidth(340);
        leftPane.setMaxWidth(340);
        gamePane.setLeft(leftPane);

        //right: player 2 quiltboard with fixed width
        VBox rightPane = new VBox(10, quiltboardViewP2.getPane());
        rightPane.setAlignment(Pos.CENTER);
        rightPane.setPadding(new Insets(0, 0, 0, 10));
        rightPane.setMinWidth(340);
        rightPane.setMaxWidth(340);
        gamePane.setRight(rightPane);

        gamePane.setCenter(buildCenterPane());
        root.getChildren().add(gamePane);
    }

    private VBox buildCenterPane() {
        //top row: patch slot 1
        HBox topPatches = new HBox(10, patchSlotWrappers[0]);
        topPatches.setAlignment(Pos.CENTER);
        topPatches.setMinHeight(PATCH_SLOT_HEIGHT);
        topPatches.setMaxHeight(PATCH_SLOT_HEIGHT);

        //bottom row: patch slots 2 and 3
        HBox bottomPatches = new HBox(10, patchSlotWrappers[1], patchSlotWrappers[2]);
        bottomPatches.setAlignment(Pos.CENTER);
        bottomPatches.setMinHeight(PATCH_SLOT_HEIGHT);
        bottomPatches.setMaxHeight(PATCH_SLOT_HEIGHT);

        //fixed control bar so buttons never move
        HBox controlBar = new HBox(15, btnPass, btnRotate, btnQuit);
        controlBar.setAlignment(Pos.CENTER);
        controlBar.setPadding(new Insets(10, 0, 0, 0));
        controlBar.setMinHeight(55);
        controlBar.setMaxHeight(55);

        VBox centerPane = new VBox(8,
                topPatches,
                timeboardView.getPane(),
                bottomPatches,
                controlBar
        );
        centerPane.setAlignment(Pos.CENTER);
        return centerPane;
    }

    //shows a warning banner at the top of the screen that disappears after 3 seconds
    public void showWarningBanner(String message) {
        Label banner = new Label(message);
        banner.setStyle(
                "-fx-background-color: #c62828;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14;" +
                        "-fx-font-weight: bold;" +
                        "-fx-padding: 12 24 12 24;" +
                        "-fx-background-radius: 8;"
        );
        StackPane.setAlignment(banner, Pos.TOP_CENTER);
        StackPane.setMargin(banner, new Insets(20, 0, 0, 0));
        root.getChildren().add(banner);

        //automatically remove the banner after 3 seconds
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> root.getChildren().remove(banner));
        pause.play();
    }

    //shows an inline confirmation overlay asking the user to confirm quitting
    //onConfirm is called if the user confirms, the overlay is removed either way
    public void showConfirmationOverlay(String message, Runnable onConfirm) {
        Label lblMessage = new Label(message);
        lblMessage.setStyle("-fx-font-size: 15; -fx-text-fill: black; -fx-text-alignment: center;");
        lblMessage.setWrapText(true);
        lblMessage.setMaxWidth(360);

        Button btnConfirm = new Button("Yes, quit");
        btnConfirm.setPrefWidth(120);
        btnConfirm.setPrefHeight(35);
        btnConfirm.setMinWidth(120);
        btnConfirm.setMaxWidth(120);
        btnConfirm.setMinHeight(35);
        btnConfirm.setMaxHeight(35);
        btnConfirm.setStyle("-fx-background-color: #c62828; -fx-text-fill: white;");

        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(120);
        btnCancel.setPrefHeight(35);
        btnCancel.setMinWidth(120);
        btnCancel.setMaxWidth(120);
        btnCancel.setMinHeight(35);
        btnCancel.setMaxHeight(35);

        HBox btnBar = new HBox(20, btnConfirm, btnCancel);
        btnBar.setAlignment(Pos.CENTER);

        VBox contentBox = new VBox(20, lblMessage, btnBar);
        contentBox.setAlignment(Pos.CENTER);
        contentBox.setPadding(new Insets(30));
        //fixed size so the box never touches the screen edges
        contentBox.setMinWidth(450);
        contentBox.setMaxWidth(450);
        contentBox.setMinHeight(160);
        contentBox.setMaxHeight(160);
        contentBox.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: black;" +
                        "-fx-border-width: 2;" +
                        "-fx-border-radius: 12;" +
                        "-fx-background-radius: 12;"
        );

        StackPane overlay = new StackPane(contentBox);
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");
        overlay.setAlignment(Pos.CENTER);
        root.getChildren().add(overlay);

        btnConfirm.setOnAction(e -> {
            root.getChildren().remove(overlay);
            onConfirm.run();
        });
        btnCancel.setOnAction(e -> root.getChildren().remove(overlay));
    }

    //shows the results screen as an overlay on top of the game screen
    public void showResultsScreen(ResultsScreenView resultsScreenView) {
        root.getChildren().add(resultsScreenView.getPane());
    }

    //rebuilds the GridPane inside the stable wrapper so layout references stay intact
    public void updatePatchSlot(int slotIndex, boolean[][] shape, int patchId, int buttonCost, int timeCost, int buttonIncome) {
        GridPane slot = new GridPane();
        slot.setHgap(2);
        slot.setVgap(2);
        slot.setUserData(patchId);

        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    Button cell = new Button();
                    //fixed cell size so patch previews never affect layout
                    cell.setPrefSize(18, 18);
                    cell.setMinSize(18, 18);
                    cell.setMaxSize(18, 18);
                    cell.setStyle("-fx-background-color: #90caf9; -fx-border-color: #42a5f5;");
                    cell.setUserData(patchId);
                    slot.add(cell, c, r);
                }
            }
        }

        //info label shown below the patch shape with cost, time and income
        Label lblInfo = new Label("Cost: " + buttonCost + "  Time: " + timeCost + "  Income: " + buttonIncome);
        lblInfo.setStyle("-fx-font-size: 9;");
        slot.add(lblInfo, 0, shape.length, Math.max(shape[0].length, 1), 1);

        //replace the old GridPane inside the stable wrapper
        //the wrapper has 2 children: the slot number label (index 0) and the GridPane (index 1)
        patchSlots[slotIndex] = slot;
        patchSlotWrappers[slotIndex].getChildren().set(1, slot);
    }

    //highlights the selected patch slot wrapper with a colored border
    //pass -1 to clear all highlights
    public void highlightPatchSlot(int slotIndex) {
        for (int i = 0; i < PATCH_STORE_SIZE; i++) {
            patchSlotWrappers[i].setStyle(
                    i == slotIndex
                            ? "-fx-border-color: #f57c00; -fx-border-width: 2; -fx-border-radius: 4; -fx-background-color: #fff9c4; -fx-background-radius: 4;"
                            : "-fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-color: #fafafa; -fx-background-radius: 4;"
            );
        }
    }

    public StackPane getPane() {
        return root;
    }

    public QuiltboardView getQuiltboardViewP1() {
        return quiltboardViewP1;
    }

    public QuiltboardView getQuiltboardViewP2() {
        return quiltboardViewP2;
    }

    public TimeboardView getTimeboardView() {
        return timeboardView;
    }

    public GridPane getPatchSlot(int index) {
        return patchSlots[index];
    }

    public VBox getPatchSlotWrapper(int index) {
        return patchSlotWrappers[index];
    }

    public int getPatchStoreSize() {
        return PATCH_STORE_SIZE;
    }

    public Button getBtnPass() {
        return btnPass;
    }

    public Button getBtnRotate() {
        return btnRotate;
    }

    public Button getBtnQuit() {
        return btnQuit;
    }
}