package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.view.MainMenuView;
import be.kdg.programming.integrationproject.view.RulesView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class RulesPresenter {
    private final RulesView view;
    private final MainMenuView mainMenuView;
    private Image image;
    private BackgroundImage backgroundImage;
    private BackgroundSize backgroundSize;


    public RulesPresenter(RulesView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        loadRules();
        addEventHandlers();
    }

    private void addEventHandlers() {
        view.getBtnBack().setOnAction(event -> view.getPane().getScene().setRoot(mainMenuView.getPane()));
    }

    private void loadRules() {
        try (InputStream is = getClass().getResourceAsStream("/rules.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            view.setRulesText(sb.toString());

        } catch (Exception e) {
            view.setRulesText("Error loading rules: " + e.getMessage());
        }
    }


}
