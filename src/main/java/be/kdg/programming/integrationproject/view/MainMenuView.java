package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class MainMenuView {
    private Button startButton;
    private Button rulesButton;
    private Button settingsButton;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;

    private GridPane pane;
    private StackPane stPane;



    public MainMenuView() {
        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        image = new Image(path);
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.stPane = new StackPane();
        this.pane = new GridPane();

        this.stPane.setBackground(new Background(backgroundImage));

        this.startButton = new Button("Start");
        this.rulesButton = new Button("Rules");
        this.settingsButton = new Button("Settings");

    }

    private void layoutNodes() {
        this.pane.setPadding(new Insets(30));
        this.pane.setVgap(10);
        this.pane.setHgap(0);
        this.pane.setAlignment(Pos.CENTER);

        this.pane.add(this.startButton, 0, 0);
        this.pane.add(this.rulesButton, 0, 1);
        this.pane.add(this.settingsButton, 0, 2);

        this.stPane.getChildren().add(pane);
    }


    public Button getStartButton() {
        return this.startButton;
    }

    public Button getRulesButton() {
        return rulesButton;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }


    public Pane getPane() {
        return stPane;
    }


}
