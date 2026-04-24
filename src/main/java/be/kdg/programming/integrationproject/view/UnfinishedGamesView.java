package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class UnfinishedGamesView {

    private StackPane root;

    private VBox contentBox;

    private ListView<String> gameList;

    private Button btnLoad;
    private Button btnBack;

    public UnfinishedGamesView() {
        initialiseNodes();
        layoutNodes();
    }

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
        gameList.setPrefSize(500,300);

        btnLoad = new Button("Load Game");
        btnBack = new Button("Back");

        btnLoad.setPrefWidth(120);
        btnBack.setPrefWidth(120);

        HBox buttonBar = new HBox(20, btnLoad, btnBack);

        buttonBar.setAlignment(Pos.CENTER);

        contentBox = new VBox(20, title, gameList, buttonBar);
    }

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


    // getters

    public StackPane getPane() {return root;}

    public ListView<String> getGameList() {return gameList;}

    public Button getBtnLoad() {return btnLoad;}

    public Button getBtnBack() {return btnBack;}
}