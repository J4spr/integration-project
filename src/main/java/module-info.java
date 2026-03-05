module be.kdg.programming.integrationproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens be.kdg.programming.integrationproject to javafx.fxml;
    exports be.kdg.programming.integrationproject;
}