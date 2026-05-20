package be.kdg.programming.integrationproject;

import be.kdg.programming.integrationproject.view.mainMenu.MainMenuPresenter;
import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

/**
 * The primary entry point and configuration window framework for the Patchwork board game application.
 * <p>
 * This class handles the initialization and execution lifecycle of the JavaFX application thread,
 * instantiating the core user interface layers and establishing global display settings.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Initializes and configures the main graphical window layer when launching the game.
     * <p>
     * This method constructs the base {@link MainMenuView} and its accompanying {@link MainMenuPresenter}
     * before generating the initial core scene framework. Additionally, it locks the system runtime configurations
     * directly into an un-escapable borderless fullscreen mode matching game engine display layouts.
     * </p>
     *
     * @param stage the primary container window provided by the JavaFX runtime architecture
     * @see MainMenuView
     * @see MainMenuPresenter
     */
    @Override
    public void start(Stage stage) {
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

    /**
     * The initial runtime execution channel called directly by the Java Virtual Machine (JVM).
     * <p>
     * Used primarily to pass system flags and delegate application boot protocols over
     * into underlying native JavaFX lifecycle routines.
     * </p>
     *
     * @param args command-line arguments array references passed down during application execution execution
     */
    public static void main(String[] args) {
        launch(args);
    }
}