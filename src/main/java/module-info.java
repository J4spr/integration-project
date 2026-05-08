module be.kdg.programming.integrationproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    // This allows JavaFX to read the 'Move' class properties for the table
    opens be.kdg.programming.integrationproject.model to javafx.base;

    exports be.kdg.programming.integrationproject;

}