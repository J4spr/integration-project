package be.kdg.programming.integrationproject.view;

import be.kdg.programming.integrationproject.model.Move;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

import java.util.List;

public class LeaderBoardView extends VBox {
    private TableView<Move> table;
    private TableColumn<Move, Integer> p1ScoreCol;
    private TableColumn<Move, Integer> p2ScoreCol;
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

        this.p1ScoreCol = new TableColumn<>("P1 Buttons");
        this.p2ScoreCol = new TableColumn<>("P2 Buttons");
        this.table = new TableView<>();
    }

    private void layoutNodes() {
        // 1. Setup Table Columns
        p1ScoreCol.setCellValueFactory(new PropertyValueFactory<>("buttonsP1"));
        p2ScoreCol.setCellValueFactory(new PropertyValueFactory<>("buttonsP2"));
        this.table.getColumns().setAll(p1ScoreCol, p2ScoreCol);

        // 2. Setup Button Container
        HBox buttonContainer = new HBox(btnBack);
        buttonContainer.setPadding(new Insets(10));
        buttonContainer.setAlignment(Pos.TOP_LEFT);

        // 3. Clear existing children ONCE to ensure a fresh start
        this.pane.getChildren().clear();
        this.getChildren().clear();

        // 4. Build the hierarchy
        // Add table and button to the StackPane (Background layer)
        this.pane.getChildren().addAll(table, buttonContainer);

        // Add the StackPane to the VBox (This view)
        this.getChildren().add(pane);
    }

    public void setTableData(List<Move> moves) {
        table.setItems(FXCollections.observableArrayList(moves));
    }

    public void showError(String message) {
        System.err.println(message);
    }

    public TableView<Move> getTable(){
        return this.table;
    }

    public StackPane getPane(){
        return this.pane;
    }

    public Button getBtnBack() {
        return btnBack;
    }
}
