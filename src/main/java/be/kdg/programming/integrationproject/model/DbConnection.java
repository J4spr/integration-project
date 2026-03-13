package be.kdg.programming.integrationproject.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private final String url;
    private final String hostname;
    private final String databaseName;
    private final String username;
    private final String password;

    public DbConnection() {
        this.hostname = "10.134.177.19";
        this.databaseName = "game";
        this.username = "game";
        this.password = "7sur7";
        this.url = String.format("jdbc:postgresql://%s:5432/%s", this.hostname, this.databaseName);;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
}
