package be.kdg.programming.integrationproject.view.unfinishedGames;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

/**
 * View class responsible for displaying the list of suspended or uncompleted matches.
 * <p>
 * This interface presents the user with a list view containing historical game states loaded
 * from the database layer, allowing them to select and resume a previous session or navigate
 * back to the main menu.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class UnfinishedGamesView {

    /** The root panel container handling background tiling and centering the content layout overlay. */
    private StackPane root;

    /** The main inner container vertically grouping the title header, game list view, and action controls. */
    private VBox contentBox;

    /** The list view component populated with textual summaries of saved, incomplete matches. */
    private ListView<String> gameList;

    /** Button component triggered to resume the game session currently selected in the list view. */
    private Button btnLoad;

    /** Navigation button used to return to the application's main menu screen. */
    private Button btnBack;

    /**
     * Constructs a new {@code UnfinishedGamesView}, initializing the user interface nodes,
     * loading background image layers, and arranging component spacing configurations.
     */
    public UnfinishedGamesView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Instantiates the graphical components, applies the standard repeating background image panel layer,
     * and sets default text rules and initial dimension parameters.
     */
    private void initialiseNodes() {

        root = new StackPane();

        // same repeating background as other screens
        String path = getClass()
                .getResource("/menus/BackGrnd.png")
                .toExternalForm();

        Image image = new Image(path);

        BackgroundSize bgSize =
                new BackgroundSize(
                        150,
                        150,
                        false,
                        false,
                        false,
                        false
                );

        BackgroundImage background =
                new BackgroundImage(
                        image,
                        BackgroundRepeat.REPEAT,
                        BackgroundRepeat.REPEAT,
                        BackgroundPosition.DEFAULT,
                        bgSize
                );

        root.setBackground(new Background(background));

        Label title = new Label("Unfinished Games");
        title.setStyle(
                "-fx-font-size: 22;" +
                        "-fx-font-weight: bold;"
        );

        gameList = new ListView<>();
        gameList.setPrefSize(500, 300);

        btnLoad = new Button("Load Game");
        btnBack = new Button("Back");

        btnLoad.setPrefWidth(120);
        btnBack.setPrefWidth(120);

        HBox buttonBar = new HBox(20, btnLoad, btnBack);

        buttonBar.setAlignment(Pos.CENTER);

        contentBox = new VBox(20, title, gameList, buttonBar);
    }

    /**
     * Organizes the content box panel components inside the root layout wrapper, assigning
     * structural styling borders, padding values, and rounded corner boundaries.
     */
    private void layoutNodes() {

        contentBox.setAlignment(Pos.CENTER);

        contentBox.setPadding(new Insets(30));

        contentBox.setMaxWidth(650);

        contentBox.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-border-color: #aaaaaa;" +
                        "-fx-border-radius: 10;"
        );

        root.setAlignment(Pos.CENTER);

        root.getChildren().add(contentBox);
    }

    /**
     * Returns the root container panel structure of the unfinished games view window.
     *
     * @return the primary wrapping {@code StackPane} layout component
     */
    public StackPane getPane() {
        return root;
    }

    /**
     * Returns the list component displaying suspended match identity strings.
     * <p>
     * Package-private accessibility allows the companion presenter layer to populate items
     * and fetch target selection indices directly.
     * </p>
     *
     * @return the internal selection tracking {@code ListView} object reference
     */
    ListView<String> getGameList() {
        return gameList;
    }

    /**
     * Returns the session reload confirmation button control trigger.
     *
     * @return the match initialization load {@code Button} instance reference
     */
    Button getBtnLoad() {
        return btnLoad;
    }

    /**
     * Returns the navigation cancellation back control button trigger.
     *
     * @return the menu return navigation {@code Button} object reference
     */
    Button getBtnBack() {
        return btnBack;
    }
}