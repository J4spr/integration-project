package be.kdg.programming.integrationproject.view.rules;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class RulesView {
    private Button btnBack;
    private StackPane pane;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;

    // We keep the name txRules so the Presenter stays happy,
    // but we change it to a VBox to allow for the "Card" layout.
    private VBox txRules;
    private ScrollPane scrollPane;

    public RulesView() {
        initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {
        this.pane = new StackPane();
        this.btnBack = new Button("Go back");

        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        this.image = new Image(path);
        this.backgroundSize = new BackgroundSize(150, 150, false, false, false, false);

        this.backgroundImage = new BackgroundImage(
                this.image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                this.backgroundSize
        );
        this.pane.setBackground(new Background(this.backgroundImage));

        // Create the container for the rules
        this.txRules = new VBox(20);
        this.txRules.setAlignment(Pos.TOP_CENTER);
        this.txRules.setPadding(new Insets(20));

        // Put it in a ScrollPane so we can scroll through long rules
        this.scrollPane = new ScrollPane(txRules);
        this.scrollPane.setFitToWidth(true);
        this.scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
    }

    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        StackPane.setMargin(btnBack, new Insets(15));

        StackPane.setAlignment(scrollPane, Pos.CENTER);
        StackPane.setMargin(scrollPane, new Insets(60, 20, 20, 20));

        pane.getChildren().addAll(scrollPane, btnBack);
    }

    // Inside RulesView.java

    // Keep this so your error handling in the Presenter still works
    public void setRulesText(String text) {
        this.txRules.getChildren().clear();
        addRuleCard("Rules", text);
    }

    // The new method that creates a separate card for each section
    public void addRuleCard(String title, String contentText) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setMaxWidth(650); // Standardize card width
        card.setStyle("-fx-background-color: rgba(255, 255, 255, 0.85); " +
                "-fx-background-radius: 15; " +
                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label lblTitle = new Label(title);
        lblTitle.setFont(Font.font("System", FontWeight.BOLD, 20));
        lblTitle.setTextFill(Color.web("#2c3e50")); // Dark blue-grey for a professional look

        Text text = new Text(contentText);
        text.setWrappingWidth(600);
        text.setFont(Font.font("System", 14));
        TextFlow flow = new TextFlow(text);

        card.getChildren().addAll(lblTitle, flow);

        // This adds the new card to the existing VBox (txRules)
        this.txRules.getChildren().add(card);
    }

    public StackPane getPane() {
        return pane;
    }

    // Kept the return type as ButtonBase or Button so the Presenter doesn't "tweak"
    Button getBtnBack() {
        return this.btnBack;
    }
}