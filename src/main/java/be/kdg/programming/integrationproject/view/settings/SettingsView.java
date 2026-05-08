package be.kdg.programming.integrationproject.view.settings;

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
    // Background Components
    private Image image;
    private BackgroundSize backgroundSize;
    private BackgroundImage backgroundImage;
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
        this.image = new Image(path);
        this.backgroundSize = new BackgroundSize(150, 150, false, false, false, false);

        this.backgroundImage = new BackgroundImage(
                this.image,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.DEFAULT,
                this.backgroundSize
        );

        this.sp = new StackPane();
        this.sp.setBackground(new Background(this.backgroundImage));

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

    Button getBtnBack() {
        return btnBack;
    }
    Button getFullscreenBtn() {
        return this.fullscreenBtn;
    }
    public StackPane getPane() {
        return this.sp;
    }
    Slider getMusicSlider() {
        return musicSlider;
    }
    Label getMusicSliderLabel() {
        return musicSliderLabel;
    }
    Button getClearLeaderBoardBtn() {
        return this.clearLeaderBoardBtn;
    }
}
