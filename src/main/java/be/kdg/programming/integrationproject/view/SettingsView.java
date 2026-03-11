package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class SettingsView {
    private Button fullscreenBtn;
    private StackPane pane;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;
    private Button btnBack;

    public SettingsView() {
        btnBack = new Button("Go back");
        String path = getClass().getResource("/mainMenu/mainMenuBackGrnd.png").toExternalForm();
        image = new Image(path);
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);

        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.pane = new StackPane();
        this.fullscreenBtn = new Button("Toggle Fullscreen");
        pane.setBackground(new Background(backgroundImage));
        btnBack = new Button("Go back");
        pane.getChildren().addAll(btnBack, fullscreenBtn);

    }

    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        StackPane.setAlignment(fullscreenBtn, Pos.CENTER);
        btnBack.setStyle("-fx-margin: 10");
        pane.setPadding(new Insets(15));

    }

    public Button getBtnBack() {
        return btnBack;
    }


    public Button getFullscreenBtn() {
        return this.fullscreenBtn;
    }

    public StackPane getPane() {
        return this.pane;
    }
}
