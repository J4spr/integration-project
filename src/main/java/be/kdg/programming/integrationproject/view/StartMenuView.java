package be.kdg.programming.integrationproject.view;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class StartMenuView {

    private VBox pane;

    // Player 1 input
    private Label lblPlayer1;
    private TextField txfPlayer1Name;
    private Label lblColorChoice;
    private ComboBox<TokenColor> cmbColorChoice;

    // CPU difficulty
    private Label lblDifficulty;
    private ComboBox<Difficulty> cmbDifficulty;

    // Buttons
    private Button btnStartGame;
    private Button btnBack;

    public StartMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        pane = new VBox(15);

        // Player 1 name
        lblPlayer1 = new Label("Player 1 Name:");
        txfPlayer1Name = new TextField();
        txfPlayer1Name.setPromptText("Enter your name");
        txfPlayer1Name.setMaxWidth(200);

        // Color choice
        lblColorChoice = new Label("Choose your color:");
        cmbColorChoice = new ComboBox<>();
        cmbColorChoice.getItems().addAll(TokenColor.values());
        cmbColorChoice.setValue(TokenColor.RED);
        cmbColorChoice.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(TokenColor color, boolean empty) {
                super.updateItem(color, empty);
                setText(empty || color == null ? null : color.getColor());
            }
        });
        cmbColorChoice.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(TokenColor color, boolean empty) {
                super.updateItem(color, empty);
                setText(empty || color == null ? null : color.getColor());
            }
        });

        // CPU difficulty - only RANDOM for beta, more options in sprint 3
        lblDifficulty = new Label("CPU Difficulty:");
        cmbDifficulty = new ComboBox<>();
        cmbDifficulty.getItems().add(Difficulty.RANDOM);
        cmbDifficulty.setValue(Difficulty.RANDOM);
        cmbDifficulty.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Difficulty diff, boolean empty) {
                super.updateItem(diff, empty);
                setText(empty || diff == null ? null : diff.getDifficulty());
            }
        });
        cmbDifficulty.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Difficulty diff, boolean empty) {
                super.updateItem(diff, empty);
                setText(empty || diff == null ? null : diff.getDifficulty());
            }
        });
        cmbDifficulty.setDisable(true);

        // Buttons
        btnStartGame = new Button("Start Game");
        btnBack = new Button("Back");
    }

    private void layoutNodes() {
        pane.setPadding(new Insets(30));
        pane.setAlignment(Pos.CENTER_LEFT);

        HBox nameRow = new HBox(10, lblPlayer1, txfPlayer1Name);
        nameRow.setAlignment(Pos.CENTER_LEFT);

        HBox colorRow = new HBox(10, lblColorChoice, cmbColorChoice);
        colorRow.setAlignment(Pos.CENTER_LEFT);

        HBox difficultyRow = new HBox(10, lblDifficulty, cmbDifficulty);
        difficultyRow.setAlignment(Pos.CENTER_LEFT);

        HBox buttonRow = new HBox(10, btnStartGame, btnBack);
        buttonRow.setAlignment(Pos.CENTER_LEFT);

        pane.getChildren().addAll(
                new Label("New Game"),
                new Separator(),
                nameRow,
                colorRow,
                new Separator(),
                new Label("CPU Settings"),
                difficultyRow,
                new Separator(),
                buttonRow
        );
    }

    //getters voor de presenter
    public VBox getPane() {
        return pane;
    }

    public String getPlayer1Name() {
        return txfPlayer1Name.getText().trim();
    }

    public TokenColor getSelectedColor() {
        return cmbColorChoice.getValue();
    }

    public Difficulty getSelectedDifficulty() {
        return cmbDifficulty.getValue();
    }

    public Button getBtnStartGame() {
        return btnStartGame;
    }

    public Button getBtnBack() {
        return btnBack;
    }
}