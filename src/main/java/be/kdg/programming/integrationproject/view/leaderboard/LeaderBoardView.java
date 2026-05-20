package be.kdg.programming.integrationproject.view.leaderboard;

import be.kdg.programming.integrationproject.model.PlayerStats;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.List;

/**
 * Custom layout component rendering recorded metrics logs using cell list components
 * formatted into clear, structured text tables.
 *
 * @author Team 4
 * @version 1.0
 */
public class LeaderBoardView extends VBox {
    private ListView<PlayerStats> listView;
    private StackPane pane;
    private Button btnBack;

    private Image bgImage;
    private BackgroundSize bgSize;
    private BackgroundImage background;

    /**
     * Initializes structural element nodes and configures layout bounds.
     */
    public LeaderBoardView() {
        initialiseNodes();
        layoutNodes();
    }

    /**
     * Builds canvas layouts, loads backgrounds, and initializes buttons.
     */
    private void initialiseNodes() {
        this.pane = new StackPane();
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
        this.pane.setBackground(new Background(this.background));

        this.btnBack = new Button("Back");
        this.btnBack.setPrefWidth(80);
        this.listView = new ListView<>();
    }

    /**
     * Binds custom string format layout templates to the list cell generation
     * framework to produce clean column alignments.
     */
    private void layoutNodes() {
        listView.setCellFactory(lv -> new ListCell<PlayerStats>() {
            @Override
            protected void updateItem(PlayerStats item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%-15s | Wins: %d | Games: %d | Win%%: %.1f%% | Spent: %d",
                            item.getUsername(), item.getWins(), item.getGamesPlayed(),
                            item.getWinPercentage(), item.getTotalButtonsSpent()));
                }
            }
        });

        HBox buttonContainer = new HBox(btnBack);
        buttonContainer.setPadding(new Insets(15));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(buttonContainer);
        mainLayout.setCenter(listView);

        BorderPane.setMargin(listView, new Insets(0, 20, 20, 20));

        this.pane.getChildren().setAll(mainLayout);
        this.getChildren().setAll(pane);

        VBox.setVgrow(pane, Priority.ALWAYS);
    }

    /**
     * Formats database configuration errors out into systemic standard error streams.
     *
     * @param message logging track error information summary text line
     */
    void showError(String message) {
        System.err.println(message);
    }

    ListView<PlayerStats> getTable() { return this.listView; }
    public StackPane getPane() { return this.pane; }
    Button getBtnBack() { return btnBack; }

    /**
     * Converts raw list collections into observable arrays to refresh visible items.
     *
     * @param stats collection list of database statistical metrics log data objects
     */
    void setStatsData(List<PlayerStats> stats) {
        listView.setItems(FXCollections.observableArrayList(stats));
    }
}