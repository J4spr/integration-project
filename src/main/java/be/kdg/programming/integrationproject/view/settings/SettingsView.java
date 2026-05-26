package be.kdg.programming.integrationproject.view.settings;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * View class that builds the graphical environment for modifying system parameters.
 * <p>
 * Displays configuration settings including window full-screen toggles, database clean-ups,
 * and volume metric sliders.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class SettingsView {
    /** Button trigger that tells the view layer to adjust stage scales. */
    private Button fullscreenBtn;
    /** Internal container used for vertical stacking of interactive settings widgets. */
    private VBox contentBox;
    /** The asset file used for rendering the background wallpaper. */
    private Image image;
    /** Layout specifications bounding the scale transformations of the background imagery. */
    private BackgroundSize backgroundSize;
    /** Tiling mapping directives for painting the core background components. */
    private BackgroundImage backgroundImage;
    /** Navigation link selector used for tracking backwards into the main menu. */
    private Button btnBack;
    /** Database manipulation button that deletes historical player records. */
    private Button clearLeaderBoardBtn;
    /** The base layout root stacking interaction elements on top of the background pattern layers. */
    private StackPane sp;
    /** Slider control allowing user adjustments of audio track outputs. */
    private Slider musicSlider;
    /** Accompanying text header describing audio control properties. */
    private Label musicSliderLabel;
    private Label titleLabel;

    /**
     * Initializes a new instance of {@code SettingsView}, setting up the nodes
     * and their structural positioning.
     */
    public SettingsView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Instantiates structural nodes, sets up asset paths, and arranges sub-container properties.
     */
    private void initialiseNodes() {
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        this.image = new Image(path);
        this.backgroundSize = new BackgroundSize(150, 150, false, false, false, false);

        this.backgroundImage = new BackgroundImage(
                this.image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                this.backgroundSize
        );

        this.sp = new StackPane();
        this.sp.setBackground(new Background(this.backgroundImage));

        this.contentBox = new VBox();
        this.musicSlider = new Slider(0, 100, 50);

        this.musicSliderLabel = new Label("Music controls");
        this.titleLabel = new Label("Settings");
        this.btnBack = createStyledButton("Go back");
        this.fullscreenBtn = createStyledButton("Toggle Fullscreen");
        this.clearLeaderBoardBtn = createStyledButton("Clear Leaderboard");

        sp.setBackground(new Background(backgroundImage));
        btnBack = new Button("Go back");
        contentBox.getChildren().addAll(
                titleLabel,
                fullscreenBtn,
                musicSliderLabel,
                musicSlider,
                clearLeaderBoardBtn,
                btnBack
        );

        sp.getChildren().add(contentBox);
    }

    /**
     * Assigns layout alignments, sets element margins, and applies default bounds properties.
     */
    private void layoutNodes() {

        sp.setAlignment(Pos.CENTER);

        contentBox.setAlignment(Pos.CENTER);

        contentBox.setSpacing(22);

        contentBox.setPadding(new Insets(35));

        contentBox.setMaxWidth(420);

        contentBox.setStyle("""
        -fx-background-color: rgba(0,0,0,0.82);
        -fx-background-radius: 18;
        -fx-border-radius: 18;
        -fx-border-color: rgba(255,255,255,0.18);
        -fx-border-width: 1.5;
    """);

        titleLabel.setStyle("""
        -fx-text-fill: white;
        -fx-font-size: 28px;
        -fx-font-weight: bold;
    """);

        musicSliderLabel.setStyle("""
        -fx-text-fill: white;
        -fx-font-size: 15px;
        -fx-font-weight: bold;
    """);

        musicSlider.setStyle("""
        -fx-control-inner-background: #2b2b2b;
    """);

        musicSlider.adjustValue(100);
    }
    /**
     * Returns the back navigation button.
     *
     * @return the exit trigger {@code Button} reference
     */
    Button getBtnBack() {
        return btnBack;
    }

    /**
     * Returns the full-screen window toggle button.
     *
     * @return the display modification {@code Button} reference
     */
    Button getFullscreenBtn() {
        return this.fullscreenBtn;
    }

    /**
     * Returns the root panel element containing all configuration nodes.
     *
     * @return the primary wrapping {@code StackPane} instance reference
     */
    public StackPane getPane() {
        return this.sp;
    }

    /**
     * Returns the target audio control slider element.
     *
     * @return the active tracking volume metric {@code Slider} component
     */
    Slider getMusicSlider() {
        return musicSlider;
    }

    /**
     * Returns the description tracking label accompanying audio volume nodes.
     *
     * @return the text header description {@code Label} reference
     */
    Label getMusicSliderLabel() {
        return musicSliderLabel;
    }

    /**
     * Returns the system clear operation control button trigger.
     *
     * @return the score wiping database {@code Button} component reference
     */
    Button getClearLeaderBoardBtn() {
        return this.clearLeaderBoardBtn;
    }
    private Button createStyledButton(String text) {

        Button button = new Button(text);

        button.setPrefWidth(220);
        button.setPrefHeight(48);

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
}