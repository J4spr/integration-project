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
        String path = getClass().getResource("/mainMenu/mainMenuBackGrnd.png").toExternalForm();
        image = new Image(path);
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        stPane = new StackPane();
        pane = new GridPane();

        stPane.setBackground(new Background(backgroundImage));

        startButton = new Button("Start");
        rulesButton = new Button("Rules");
        settingsButton = new Button("Settings");

    }

    private void layoutNodes() {
        pane.setPadding(new Insets(30));
        pane.setVgap(10);
        pane.setHgap(0);
        pane.setAlignment(Pos.CENTER);


        pane.add(startButton, 0, 0);
        pane.add(rulesButton, 0, 1);
        pane.add(settingsButton, 0, 2);

        stPane.getChildren().add(pane);
    }


    public Button getStartButton() {
        return startButton;
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
