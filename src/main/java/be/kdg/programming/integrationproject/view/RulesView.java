package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class RulesView {
    private Button btnBack;
    private StackPane pane;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;


    public RulesView() {
        String path = getClass().getResource("/mainMenu/mainMenuBackGrnd.png").toExternalForm();
        image = new Image(path);
        backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);


        initialiseNodes();
        layoutNodes();
    }


    private void initialiseNodes() {
        pane = new StackPane();
        btnBack = new Button("Go back");
        pane.getChildren().add(btnBack);
        pane.setBackground(new Background(backgroundImage));

    }

    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        btnBack.setStyle("-fx-margin: 10");
        pane.setPadding(new Insets(15));
    }

    public void setRulesText(String string) {


    }

    public StackPane getPane() {
        return pane;
    }

    public ButtonBase getBtnBack() {
        return this.btnBack;
    }
}
