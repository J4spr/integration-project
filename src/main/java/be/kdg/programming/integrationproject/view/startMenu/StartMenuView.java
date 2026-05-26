package be.kdg.programming.integrationproject.view.startMenu;

import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.layout.VBox;

/**
 * Graphical interface wrapper processing initialization data parameters for new matches.
 * <p>
 * Uses specialized titled components to capture and pass on user attributes,
 * selected visual token colors, difficulty settings, and match turn order preferences.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class StartMenuView {

    /** The root canvas layout panel mapping background imagery and content overlays. */
    private StackPane root;
    private VBox contentBox;
    //player settings
    /** Informative label component pointing to the player identity text fields. */
    private Label lblPlayerName;
    /** Text input container capturing identity names data fields. */
    private TextField tfPlayerName;
    /** Informative description tracking indicator for token collection mappings. */
    private Label lblTokenColor;
    /** Dropdown selection tracking enumeration settings mapping color targets. */
    private ComboBox<TokenColor> cbTokenColor;

    //game settings
    /** Informative description tracking indicator for engine intelligence options. */
    private Label lblDifficulty;
    /** Dropdown parameter tracking intelligence configuration parameters. */
    private ComboBox<Difficulty> cbDifficulty;
    /** Informative tracking element pointing to turn setup preferences. */
    private Label lblStartPlayer;
    /** Dropdown selection field determining whether player or engine takes initial turn. */
    private ComboBox<String> cbStartPlayer;

    //buttons
    /** Initialization action button starting game initialization protocols. */
    private Button btnStartGame;
    /** Step-back utility control component routing away from the initialization menu. */
    private Button btnBack;

    /**
     * Initializes a new instance of {@code StartMenuView}, assembling component
     * containers and alignment fields.
     */
    public StartMenuView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Instantiates input components, sets default parameters,
     * and maps choices into selection lists.
     */
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

        btnStartGame = createStyledButton("Start");
        btnBack = createStyledButton("Back");
    }

    /**
     * Assembles input sub-containers into organized layout boxes,
     * configuring borders, spacing properties, and background images.
     */
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
        styleSection(playerSection);
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
        styleSection(gameSection);
        gameSection.setCollapsible(false);

        HBox btnBar = new HBox(10, btnStartGame, btnBack);
        btnBar.setAlignment(Pos.CENTER);
        //add some space above the button bar
        VBox.setMargin(btnBar, new Insets(15, 0, 0, 0));

        //inner box that holds all content, with a visible border and fixed max width/height
        contentBox = new VBox(20, playerSection, gameSection, btnBar);
        contentBox.setPadding(new Insets(20));
        contentBox.setMaxWidth(450);
        contentBox.setMaxHeight(350);
        contentBox.setStyle("""
    -fx-background-color: rgba(0,0,0,0.82);
    -fx-background-radius: 18;
    -fx-border-radius: 18;
    -fx-border-color: rgba(255,255,255,0.18);
    -fx-border-width: 1.5;
""");

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

    /**
     * Returns the root container panel element.
     *
     * @return the primary base {@code StackPane} instance reference
     */
    public StackPane getPane() {
        return root;
    }

    /**
     * Extracts input strings from player text elements, removing leading and trailing whitespace.
     *
     * @return the trimmed text string entered by the user
     */
    String getPlayerName() {
        return tfPlayerName.getText().trim();
    }

    /**
     * Extracts chosen token classification elements from selection components.
     *
     * @return the selected {@code TokenColor} enum constant
     */
    TokenColor getSelectedTokenColor() {
        return cbTokenColor.getValue();
    }

    /**
     * Extracts target AI operational parameters from selection components.
     *
     * @return the selected {@code Difficulty} enum level configuration reference
     */
    Difficulty getSelectedDifficulty() {
        return cbDifficulty.getValue();
    }

    /**
     * Translates player turn selection labels into mechanical identifier indices.
     *
     * @return {@code 1} if the human player is selected to start, or {@code 2} if the CPU starts
     */
    int getStartPlayer() {
        return cbStartPlayer.getValue().equals("You") ? 1 : 2;
    }

    /**
     * Returns the match confirmation launch control button.
     *
     * @return the game start trigger {@code Button} reference
     */
    Button getBtnStartGame() {
        return btnStartGame;
    }

    /**
     * Returns the navigation cancellation back control button.
     *
     * @return the cancel operation routing {@code Button} reference
     */
    Button getBtnBack() {
        return btnBack;
    }

    private Button createStyledButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(180);
        button.setPrefHeight(45);

        button.setStyle("""
        -fx-background-color: rgba(0,0,0,0.72);
        -fx-text-fill: white;
        -fx-font-size: 15px;
        -fx-font-weight: bold;
        -fx-background-radius: 14;
        -fx-border-radius: 14;
        -fx-border-color: rgba(255,255,255,0.18);
        -fx-border-width: 1.5;
        -fx-cursor: hand;
    """);

        button.setOnMouseEntered(e ->
                button.setStyle("""
                -fx-background-color: rgba(25,25,25,0.92);
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 14;
                -fx-border-radius: 14;
                -fx-border-color: white;
                -fx-border-width: 1.5;
                -fx-cursor: hand;
            """)
        );

        button.setOnMouseExited(e ->
                button.setStyle("""
                -fx-background-color: rgba(0,0,0,0.72);
                -fx-text-fill: white;
                -fx-font-size: 15px;
                -fx-font-weight: bold;
                -fx-background-radius: 14;
                -fx-border-radius: 14;
                -fx-border-color: rgba(255,255,255,0.18);
                -fx-border-width: 1.5;
                -fx-cursor: hand;
            """)
        );

        return button;

    }

    private void styleSection(TitledPane pane) {

        pane.setStyle("""
    -fx-text-fill: white;
    -fx-background-color: transparent;
""");

        pane.lookup(".title");
        pane.setStyle("""
    -fx-text-fill: white;
    -fx-background-color: transparent;
""");

        pane.setCollapsible(false);

        pane.setAnimated(false);

        pane.lookup(".content");

        lblPlayerName.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        lblTokenColor.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        lblDifficulty.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");
        lblStartPlayer.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        tfPlayerName.setStyle("""
        -fx-background-color: #2b2b2b;
        -fx-text-fill: white;
        -fx-background-radius: 10;
    """);

        cbTokenColor.setStyle("""
        -fx-background-color: #2b2b2b;
        -fx-text-fill: white;
        -fx-background-radius: 10;
    """);

        cbDifficulty.setStyle("""
        -fx-background-color: #2b2b2b;
        -fx-text-fill: white;
        -fx-background-radius: 10;
    """);

        cbStartPlayer.setStyle("""
        -fx-background-color: #2b2b2b;
        -fx-text-fill: white;
        -fx-background-radius: 10;
    """);
        pane.skinProperty().addListener((obs, oldSkin, newSkin) -> {

            Region titleRegion = (Region) pane.lookup(".title");
            if (titleRegion != null) {
                titleRegion.setStyle("""
            -fx-background-color: #1f1f1f;
            -fx-background-radius: 12 12 0 0;
        """);
            }

            Region contentRegion = (Region) pane.lookup(".content");
            if (contentRegion != null) {
                contentRegion.setStyle("""
            -fx-background-color: #2b2b2b;
            -fx-background-radius: 0 0 12 12;
            -fx-border-color: transparent;
        """);
            }
        });

    }
}