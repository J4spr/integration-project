package be.kdg.programming.integrationproject.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {
    private final String url;
    private final String hostname;
    private final String databaseName;
    private final String username;
    private final String password;
    private final String portNumber;
    private final Properties p;
    private final FileReader fr;

    public DbConnection() {
        this.p = new Properties();
        try {
            this.fr = new FileReader("db.properties");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            this.p.load(fr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.hostname = p.getProperty("hostname");
        this.username = p.getProperty("username");
        this.password = p.getProperty("password");
        this.databaseName = p.getProperty("dbname");
        this.portNumber = p.getProperty("port");
        this.url = String.format("jdbc:postgresql://%s:%s/%s", this.hostname,this.portNumber, this.databaseName);
    }

    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(this.url, this.username, this.password);

        } catch (ClassNotFoundException e) {
            System.err.println("Database driver niet gevonden");
            e.printStackTrace();

        } catch (SQLException e) {
            System.err.println("Fout bij maken van database connectie");
            e.printStackTrace();
        }

        return null;
    }

}
