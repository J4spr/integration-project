package be.kdg.programming.integrationproject;

import be.kdg.programming.integrationproject.presenter.MainMenuPresenter;
import be.kdg.programming.integrationproject.view.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView view = new MainMenuView();
        new MainMenuPresenter(view);

        Scene scene = new Scene(view.getPane(), 400, 300);

        stage.setTitle("Patchwork Game");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}