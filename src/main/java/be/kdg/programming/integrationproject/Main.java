package be.kdg.programming.integrationproject;

import be.kdg.programming.integrationproject.view.mainMenu.MainMenuPresenter;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        MainMenuView view = new MainMenuView();
        new MainMenuPresenter(view);

        Scene scene = new Scene(view.getPane(), 400, 300);
    
        stage.setTitle("Patchwork");
        stage.setScene(scene);
        //start the application in fullscreen mode, ESC exits fullscreen by default
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("");
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}