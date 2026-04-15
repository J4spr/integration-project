package be.kdg.programming.integrationproject.view;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class StartMenuView {

    private StackPane root;

    //player settings
    private Label lblPlayerName;
    private TextField tfPlayerName;
    private Label lblTokenColor;
    private ComboBox<TokenColor> cbTokenColor;

    //game settings
    private Label lblDifficulty;
    private ComboBox<Difficulty> cbDifficulty;
    private Label lblStartPlayer;
    private ComboBox<String> cbStartPlayer;

    //buttons
    private Button btnStartGame;
    private Button btnBack;

    public StartMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        root = new StackPane();

        //player settings
        lblPlayerName = new Label("Your name:");
        tfPlayerName = new TextField();
        tfPlayerName.setPromptText("Enter your name");

        lblTokenColor = new Label("Time Token color:");
        cbTokenColor = new ComboBox<>();
        cbTokenColor.getItems().addAll(TokenColor.values());
        cbTokenColor.setValue(TokenColor.values()[0]);

        //game settings
        lblDifficulty = new Label("CPU difficulty:");
        cbDifficulty = new ComboBox<>();
        cbDifficulty.getItems().add(Difficulty.EASY);
        cbDifficulty.getItems().add(Difficulty.MEDIUM);
        cbDifficulty.getItems().add(Difficulty.HARD);
        cbDifficulty.setValue(Difficulty.EASY);
        
        lblStartPlayer = new Label("Starting player:");
        cbStartPlayer = new ComboBox<>();
        //1 = human player starts, 2 = CPU starts
        cbStartPlayer.getItems().addAll("You", "CPU");
        cbStartPlayer.setValue("You");

        btnStartGame = new Button("Start");
        btnStartGame.setPrefWidth(100);
        btnStartGame.setPrefHeight(35);

        btnBack = new Button("Back");
        btnBack.setPrefWidth(100);
        btnBack.setPrefHeight(35);
    }

    private void layoutNodes() {
        //player settings section in a titled box
        VBox playerFields = new VBox(6,
                new HBox(10, lblPlayerName, tfPlayerName),
                new HBox(10, lblTokenColor, cbTokenColor)
        );
        for (var row : playerFields.getChildren()) {
            ((HBox) row).setAlignment(Pos.CENTER_LEFT);
        }
        TitledPane playerSection = new TitledPane("Player settings", playerFields);
        playerSection.setCollapsible(false);

        //game settings section in a titled box
        VBox gameFields = new VBox(6,
                new HBox(10, lblDifficulty, cbDifficulty),
                new HBox(10, lblStartPlayer, cbStartPlayer)
        );
        for (var row : gameFields.getChildren()) {
            ((HBox) row).setAlignment(Pos.CENTER_LEFT);
        }
        TitledPane gameSection = new TitledPane("Game settings", gameFields);
        gameSection.setCollapsible(false);

        HBox btnBar = new HBox(10, btnStartGame, btnBack);
        btnBar.setAlignment(Pos.CENTER);
        //add some space above the button bar
        VBox.setMargin(btnBar, new Insets(15, 0, 0, 0));

        //inner box that holds all content, with a visible border and fixed max width/height
        VBox contentBox = new VBox(10, playerSection, gameSection, btnBar);
        contentBox.setPadding(new Insets(20));
        contentBox.setMaxWidth(450);
        contentBox.setMaxHeight(350);
        contentBox.setStyle("-fx-border-color: #aaaaaa; -fx-border-radius: 8; -fx-background-color: white; -fx-background-radius: 8;");

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

        root.setBackground(new Background(background));

        //StackPane centers the contentBox and scales naturally with window resize
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getChildren().add(contentBox);
    }

    public StackPane getPane() {
        return root;
    }

    public String getPlayerName() {
        return tfPlayerName.getText().trim();
    }

    public TokenColor getSelectedTokenColor() {
        return cbTokenColor.getValue();
    }

    public Difficulty getSelectedDifficulty() {
        return cbDifficulty.getValue();
    }

    //returns 1 if the human player starts, 2 if the CPU starts
    public int getStartPlayer() {
        return cbStartPlayer.getValue().equals("You") ? 1 : 2;
    }

    public Button getBtnStartGame() {
        return btnStartGame;
    }

    public Button getBtnBack() {
        return btnBack;
    }
}