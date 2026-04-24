package be.kdg.programming.integrationproject;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.presenter.MainMenuPresenter;
import be.kdg.programming.integrationproject.view.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

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

        try {
            DbConnection db = new DbConnection();

            if (!db.tableExists("playertable")) {
                System.out.println("Tabellen bestaan nog niet, worden aangemaakt...");
                db.runSqlScript("src/main/resources/db.sql");
            } else {
                System.out.println("Tabellen bestaan al.");
            }
        } catch (RuntimeException e) {
            System.out.println("Database niet gevonden");
        }
    }
}