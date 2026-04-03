package be.kdg.programming.integrationproject.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class SettingsView {
    private Button fullscreenBtn;
    private VBox pane;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;
    private Button btnBack;
    private Button clearLeaderBoardBtn;
    private StackPane sp;
    private Slider musicSlider;
    private Label musicSliderLabel;

    public SettingsView() {
       initialiseNodes();
        layoutNodes();
    }

    private void initialiseNodes() {

        String path = getClass().getResource("/menus/BackGrnd.png").toExternalForm();
        image = new Image(path);
        BackgroundSize bgSize = new BackgroundSize(150, 150, false, false, false, false);

        BackgroundImage background = new BackgroundImage(
                image,
                BackgroundRepeat.REPEAT,   // Repeat on X-axis
                BackgroundRepeat.REPEAT,   // Repeat on Y-axis
                BackgroundPosition.DEFAULT,
                bgSize
        );


        this.sp = new StackPane();
        this.pane = new VBox();
        this.musicSlider = new Slider(0, 100, 50);

        this.musicSliderLabel = new Label("Music controls");
        this.btnBack = new Button("Go back");
        this.fullscreenBtn = new Button("Toggle Fullscreen");
        this.clearLeaderBoardBtn = new Button("Clear leaderboard");


        sp.setBackground(new Background(backgroundImage));
        btnBack = new Button("Go back");
        pane.getChildren().add(fullscreenBtn);
        pane.getChildren().addAll(musicSliderLabel, musicSlider);
        sp.getChildren().addAll(pane, btnBack);

    }

    private void layoutNodes() {
        StackPane.setAlignment(btnBack, Pos.TOP_LEFT);
        pane.setAlignment(Pos.CENTER);
        btnBack.setStyle("-fx-margin: 10");
        sp.setPadding(new Insets(15));
        pane.setPadding(new Insets(20));
        pane.setSpacing(20);
        musicSlider.adjustValue(100);
        this.sp.getChildren().add(this.clearLeaderBoardBtn);


    }

    public Button getBtnBack() {
        return btnBack;
    }


    public Button getFullscreenBtn() {
        return this.fullscreenBtn;
    }

    public StackPane getPane() {
        return this.sp;
    }

    public Slider getMusicSlider() {
        return musicSlider;
    }

    public Label getMusicSliderLabel() {
        return musicSliderLabel;
    }

    public Button getClearLeaderBoardBtn() {
        return this.clearLeaderBoardBtn;
    }
}
