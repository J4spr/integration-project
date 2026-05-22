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
    private VBox pane;
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

        this.pane = new VBox();
        this.musicSlider = new Slider(0, 100, 50);

        this.musicSliderLabel = new Label("Music controls");
        this.btnBack = new Button("Go back");
        this.fullscreenBtn = new Button("Toggle Fullscreen");
        this.clearLeaderBoardBtn = new Button("Clear leaderboard");

        sp.setBackground(new Background(backgroundImage));
        btnBack = new Button("Go back");
        pane.getChildren().add(fullscreenBtn);
        pane.getChildren().addAll(musicSliderLabel, musicSlider);
        sp.getChildren().addAll(pane, btnBack);
    }

    /**
     * Assigns layout alignments, sets element margins, and applies default bounds properties.
     */
    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        pane.setAlignment(Pos.CENTER);
        btnBack.setStyle("-fx-margin: 10");
        sp.setPadding(new Insets(15));
        pane.setPadding(new Insets(20));
        pane.setSpacing(20);
        musicSlider.adjustValue(100);
        this.sp.getChildren().add(this.clearLeaderBoardBtn);
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
}