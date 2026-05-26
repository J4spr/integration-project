package be.kdg.programming.integrationproject.view;

import be.kdg.programming.integrationproject.model.PlayerStats;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import java.util.List;

public class LeaderBoardView extends VBox {
    private ListView<PlayerStats> listView;
    private StackPane pane;
    private Button btnBack;
    private Button btnSortWins;
    private Button btnSortGames;
    private Button btnSortWinPct;
    private Button btnSortSpent;

    private Image bgImage;
    private BackgroundSize bgSize;
    private BackgroundImage background;

    public LeaderBoardView() {
        initialiseNodes();
        layoutNodes();
    }

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

        this.btnBack      = new Button("Back");
        this.btnSortWins  = new Button("Sort: Wins");
        this.btnSortGames = new Button("Sort: Games Played");
        this.btnSortWinPct = new Button("Sort: Win%");
        this.btnSortSpent = new Button("Sort: Spent");

        this.btnBack.setPrefWidth(80);
        for (Button btn : new Button[]{btnSortWins, btnSortGames, btnSortWinPct, btnSortSpent}) {
            btn.setPrefWidth(140);
            btn.setStyle("-fx-background-color: #e0e0e0; -fx-border-color: #aaaaaa; -fx-border-radius: 4; -fx-background-radius: 4;");
        }

        this.listView = new ListView<>();
    }

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

        // Top bar: Back button links, sort buttons rechts
        HBox topBar = new HBox(10, btnBack);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(15));

        VBox sortButtons = new VBox(8, btnSortWins, btnSortGames, btnSortWinPct, btnSortSpent);
        sortButtons.setAlignment(Pos.TOP_RIGHT);
        sortButtons.setPadding(new Insets(10));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(topBar);
        mainLayout.setCenter(listView);
        mainLayout.setRight(sortButtons);

        BorderPane.setMargin(listView, new Insets(0, 10, 20, 20));
        BorderPane.setMargin(sortButtons, new Insets(0, 10, 0, 0));

        this.pane.getChildren().setAll(mainLayout);
        this.getChildren().setAll(pane);
        VBox.setVgrow(pane, Priority.ALWAYS);
    }

    public void showError(String message) {
        System.err.println(message);
    }

    public void setStatsData(List<PlayerStats> stats) {
        listView.setItems(FXCollections.observableArrayList(stats));
    }

    public ObservableList<PlayerStats> getStatsData() {
        return listView.getItems();
    }

    public StackPane getPane()         { return this.pane; }
    public Button getBtnBack()         { return btnBack; }
    public Button getBtnSortWins()     { return btnSortWins; }
    public Button getBtnSortGames()    { return btnSortGames; }
    public Button getBtnSortWinPct()   { return btnSortWinPct; }
    public Button getBtnSortSpent()    { return btnSortSpent; }
}