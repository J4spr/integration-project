package be.kdg.programming.integrationproject.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Manages low-level environment profiles parsed directly via local properties
 * configuration to resolve structured live connection pipelines to a PostgreSQL target.
 *
 * @author Team 4
 * @version 1.0
 */
public class DbConnection {
    private final String url;
    private final String hostname;
    private final String databaseName;
    private final String username;
    private final String password;
    private final String portNumber;
    private final Properties p;
    private final FileReader fr;

    /**
     * Initializes configuration configurations by resolving local property configurations.
     * Throws an unchecked runtime initialization wrapper if files are missing or malformed.
     */
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
        this.url = String.format("jdbc:postgresql://%s:%s/%s", this.hostname, this.portNumber, this.databaseName);
    }

    /**
     * Loads the target JDBC structural driver profile cleanly into scope and registers
     * an active runtime connection session instance.
     *
     * @return an operational target database {@link Connection} instance context
     * @throws SQLException if structural authentication parameters or link tracks error out
     */
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load Database class");
            System.exit(1);
        }
        return DriverManager.getConnection(this.url, this.username, this.password);
    }
}