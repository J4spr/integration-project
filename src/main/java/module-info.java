module be.kdg.programming.integrationproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    // This allows JavaFX to read the 'Move' class properties for the table
    opens be.kdg.programming.integrationproject.model to javafx.base;

    // This is good practice to allow JavaFX to access your view/presenter logic
    opens be.kdg.programming.integrationproject.view to javafx.fxml;
    opens be.kdg.programming.integrationproject.presenter to javafx.fxml;

    exports be.kdg.programming.integrationproject;
}