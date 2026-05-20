package be.kdg.programming.integrationproject.view.rules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * View class responsible for displaying game documentation and instructions.
 * <p>
 * Employs a stylized card-based visual format housed inside an autonomous vertical layout container.
 * Sections can be added dynamically and viewed via structured scrolling.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class RulesView {
    /** Navigation button to return to the preceding screen. */
    private Button btnBack;
    /** The core canvas layer containing the background image patterns and rules wrapper. */
    private StackPane pane;
    /** The visual image asset used for tiling the menu canvas background. */
    private Image image;
    /** The configured background layout logic mapping the background image. */
    private BackgroundImage backgroundImage;
    /** The size bounds configurations used to constraint background asset scales. */
    private BackgroundSize backgroundSize;

    /**
     * The structural container holding segmented visual rule cards.
     * <p>
     * Kept as a {@code VBox} configuration under the historical name {@code txRules}
     * to keep backward compatibility layers intact within handling presenters.
     * </p>
     */
    private VBox txRules;
    /** Scroll viewport wrapper allowing users to browse rule entries that exceed display bounds. */
    private ScrollPane scrollPane;

    /**
     * Initializes a new instance of {@code RulesView}, setting up structural containers
     * and visual content wrappers.
     */
    public RulesView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Initializes structural panels, sets up tiled imagery paths,
     * and prepares the main scrolling content components.
     */
    private void initialiseNodes() {
        this.pane = new StackPane();
        this.btnBack = new Button("Go back");

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
        this.pane.setBackground(new Background(this.backgroundImage));

        // Create the container for the rules
        this.txRules = new VBox(20);
        this.txRules.setAlignment(Pos.TOP_CENTER);
        this.txRules.setPadding(new Insets(20));

        // Put it in a ScrollPane so we can scroll through long rules
        this.scrollPane = new ScrollPane(txRules);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
    }

    /**
     * Arranges view elements and maps target constraint spaces inside the primary canvas layout.
     */
    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        StackPane.setMargin(btnBack, new Insets(15));

        StackPane.setAlignment(scrollPane, Pos.CENTER);
        StackPane.setMargin(scrollPane, new Insets(60, 20, 20, 20));

        pane.getChildren().addAll(scrollPane, btnBack);
    }

    /**
     * Overwrites active view components by wiping out current cards and setting a single error fallback message card.
     * <p>
     * Maintained to provide generic pipeline safety fallbacks for existing presenter classes.
     * </p>
     *
     * @param text the raw literal messaging context to output
     */
    public void setRulesText(String text) {
        this.txRules.getChildren().clear();
        addRuleCard("Rules", text);
    }

    /**
     * Generates a separate drop-shadow card graphic segment mapping specific headers alongside contextual strings.
     *
     * @param title       the section heading string matching the individual category rule text card
     * @param contentText the extensive details text data mapped to wrap cleanly into the card
     */
    public void addRuleCard(String title, String contentText) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setMaxWidth(650); // Standardize card width
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85); " +
                "-fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        lblTitle.setTextFill(Color.web("#2c3e50")); // Dark blue-grey for a professional look

        Text text = new Text(contentText);
        text.setWrappingWidth(600);
        text.setFont(Font.font("System", 14));
        TextFlow flow = new TextFlow(text);

        card.getChildren().addAll(lblTitle, flow);

        // This adds the new card to the existing VBox (txRules)
        this.txRules.getChildren().add(card);
    }

    /**
     * Retrieves the structural core StackPane mapping active scene layer variables.
     *
     * @return the primary root {@code StackPane} reference
     */
    public StackPane getPane() {
        return pane;
    }

    /**
     * Returns the back navigation trigger node component.
     *
     * @return the navigation return trigger {@code Button} object reference
     */
    Button getBtnBack() {
        return this.btnBack;
    }
}