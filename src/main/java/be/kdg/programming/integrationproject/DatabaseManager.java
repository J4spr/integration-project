package be.kdg.programming.integrationproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/patchwork_game";
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public static Connection connect() throws SQLException {
        // Methode om een verbinding te maken met de database
        return DriverManager.getConnection(URL, USER, PASSWORD);
        // Maakt en retourneert een nieuwe Connection met opgegeven URL, gebruiker en wachtwoord

    }
}
