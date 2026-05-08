package be.kdg.programming.integrationproject.view.rules;

import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
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

            String line;
            StringBuilder content = new StringBuilder();
            String currentTitle = "Rules"; // Default title

            while ((line = reader.readLine()) != null) {
                // Check if the line looks like a header (e.g., ===== SETUP =====)
                if (line.startsWith("=====") && line.endsWith("=====")) {
                    // If we already have content collected, send it to the view as a card
                    if (content.length() > 0) {
                        view.addRuleCard(currentTitle, content.toString().trim());
                        content.setLength(0); // Clear for next section
                    }
                    // Clean up the header to use as the next title (remove the ====)
                    currentTitle = line.replace("=", "").trim();
                } else {
                    content.append(line).append("\n");
                }
            }
            // Don't forget the last section!
            if (content.length() > 0) {
                view.addRuleCard(currentTitle, content.toString().trim());
            }

        } catch (Exception e) {
            view.setRulesText("Error loading rules: " + e.getMessage());
        }
    }


}
