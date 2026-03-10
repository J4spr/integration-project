package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class RulesView {
    private Button btnBack;
    private StackPane pane;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;
    private TextArea txRules;


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
        pane.setBackground(new Background(backgroundImage));

        txRules = new TextArea();
        txRules.setEditable(false);
        txRules.setWrapText(true);

        txRules.setStyle("-fx-control-inner-background: rgba(255, 255, 255, 0.8);");
        pane.getChildren().addAll(txRules, btnBack);

    }

    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        btnBack.setStyle("-fx-margin: 10");
        pane.setPadding(new Insets(15));

        StackPane.setAlignment(txRules, Pos.CENTER);
        StackPane.setMargin(txRules, new Insets(50, 20, 20, 20));

        pane.setPadding(new Insets(15));
    }

    public void setRulesText(String text) {
        this.txRules.setText(text);

    }

    public StackPane getPane() {
        return pane;
    }

    public ButtonBase getBtnBack() {
        return this.btnBack;
    }
}
