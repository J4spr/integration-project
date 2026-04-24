package be.kdg.programming.integrationproject.view;

import be.kdg.programming.integrationproject.model.Player;
import be.kdg.programming.integrationproject.model.PlayerStats;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
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

    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;


    public LeaderBoardView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.pane = new StackPane();
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        image = new Image(path);
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        this.pane.setBackground(new Background(backgroundImage));

        this.btnBack = new Button("Back");
        this.btnBack.setPrefWidth(80);
        this.listView = new ListView<>();

    }

    private void layoutNodes() {
        // Define how to display the PlayerStats object in the list
        listView.setCellFactory(lv -> new ListCell<PlayerStats>() {
            @Override
            protected void updateItem(PlayerStats item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%-15s | Wins: %d | Games: %d | Win%%: %.1f%% | Spent: %d",
                            item.getUsername(),
                            item.getWins(),
                            item.getGamesPlayed(),
                            item.getWinPercentage(),
                            item.getTotalButtonsSpent()));
                }
            }
        });

        HBox buttonContainer = new HBox(btnBack);
        buttonContainer.setPadding(new Insets(15));

        BorderPane mainLayout = new BorderPane();
        mainLayout.setTop(buttonContainer);
        mainLayout.setCenter(listView); // Add the list here

        BorderPane.setMargin(listView, new Insets(0, 20, 20, 20));

        this.pane.getChildren().setAll(mainLayout);
        this.getChildren().setAll(pane);

        VBox.setVgrow(pane, Priority.ALWAYS);
    }


    public void showError(String message) {
        System.err.println(message);
    }

    public ListView<PlayerStats> getTable() {
        return this.listView;
    }

    public StackPane getPane() {
        return this.pane;
    }

    public Button getBtnBack() {
        return btnBack;
    }

    public void setStatsData(List<PlayerStats> stats) {
        listView.setItems(FXCollections.observableArrayList(stats));
    }
}
