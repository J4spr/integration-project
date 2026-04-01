package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.Difficulty;
import be.kdg.programming.integrationproject.model.Enums.TokenColor;
import be.kdg.programming.integrationproject.view.GameView;
import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.StartMenuView;
import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StartMenuPresenter {

    private final StartMenuView view;
    private final MainMenuView mainMenuView;

    public StartMenuPresenter(StartMenuView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );

        view.getBtnStartGame().setOnAction(e -> startGame());
    }

    private void startGame() {
        //validate that the player has entered a name
        if (view.getPlayerName().isEmpty()) {
            showWarning("Please enter your name.");
            return;
        }

        HumanPlayer player1 = new HumanPlayer(view.getPlayerName());
        player1.setPlayerId(1);
        player1.setColor(view.getSelectedTokenColor());

        CpuPlayer player2 = new CpuPlayer(view.getSelectedDifficulty());
        player2.setPlayerId(2);
        //pick a random color for the CPU that is different from player 1's color
        player2.setColor(pickCpuColor(view.getSelectedTokenColor()));

        Game game = new Game(player1, player2, view.getStartPlayer());

        String colorP1 = tokenColorToHex(player1.getColor());
        String colorP2 = tokenColorToHex(player2.getColor());

        GameView gameView = new GameView(player1.getName(), colorP1, "CPU", colorP2);
        new GamePresenter(game, gameView, mainMenuView);

        view.getPane().getScene().setRoot(gameView.getPane());
    }

    //picks a random TokenColor that is not the same as the human player's color
    private TokenColor pickCpuColor(TokenColor playerColor) {
        List<TokenColor> colors = new ArrayList<>(List.of(TokenColor.values()));
        colors.remove(playerColor);
        Collections.shuffle(colors);
        return colors.get(0);
    }

    //converts a TokenColor enum to a hex color string usable in JavaFX CSS
    private String tokenColorToHex(TokenColor color) {
        if (color == null) return "#aaaaaa";
        return switch (color) {
            case RED -> "#ef5350";
            case GREEN -> "#66bb6a";
            case YELLOW -> "#ffee58";
            case BLUE -> "#42a5f5";
        };
    }

    private void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}