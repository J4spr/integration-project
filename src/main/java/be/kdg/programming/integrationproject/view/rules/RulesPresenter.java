package be.kdg.programming.integrationproject.view.rules;

import be.kdg.programming.integrationproject.view.mainMenu.MainMenuView;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Controller class that bridges structural input streams and rule interfaces.
 * <p>
 * Handles processing steps to convert structural asset documentation streams (such as raw headers
 * enclosed inside {@code =====}) directly into dynamic rule cards.
 * </p>
 *
 * @author YourName
 * @version 1.0
 */
public class RulesPresenter {
    /** The view panel structure being updated by incoming file elements. */
    private final RulesView view;
    /** The parent main menu view node used for backward tracking steps. */
    private final MainMenuView mainMenuView;
    /** Placeholder layout tracking variable for specific background images. */
    private Image image;
    /** Placeholder configuration wrapper variable managing canvas background patterns. */
    private BackgroundImage backgroundImage;
    /** Placeholder scale dimensions variable managing background asset bounds metrics. */
    private BackgroundSize backgroundSize;

    /**
     * Instantiates an active presenter pairing, starting data streams parsing operations
     * and link event pipelines.
     *
     * @param view         the interactive canvas components receiver structure reference
     * @param mainMenuView the parent navigation target pointer
     */
    public RulesPresenter(RulesView view, MainMenuView mainMenuView) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        loadRules();
        addEventHandlers();
    }

    /**
     * Pairs interaction steps with JavaFX controls to swap root view nodes.
     */
    private void addEventHandlers() {
        view.getBtnBack().setOnAction(event -> view.getPane().getScene().setRoot(mainMenuView.getPane()));
    }

    /**
     * Stream reads raw file lines from system properties files and interprets content formatting.
     * <p>
     * Sections containing equals-sign strings (e.g., {@code ===== SETUP =====}) are parsed out as headers
     * to split text blocks into clean, distinct cards.
     * </p>
     */
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