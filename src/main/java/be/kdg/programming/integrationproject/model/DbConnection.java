package be.kdg.programming.integrationproject.model;

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


    public DbConnection() {
        this.p = new Properties();

        try (var input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties niet gevonden in resources!");
            }
            this.p.load(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.hostname = p.getProperty("hostname");
        this.username = p.getProperty("username");
        this.password = p.getProperty("password");
        this.databaseName = p.getProperty("dbname");
        this.portNumber = p.getProperty("port");

        this.url = String.format("jdbc:postgresql://%s:%s/%s",
                this.hostname, this.portNumber, this.databaseName);
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load Database class");
            System.exit(1);
        }
        return DriverManager.getConnection(this.url, this.username, this.password);
    }

    public boolean tableExists(String tableName) {
        try (Connection connection = getConnection()) {
            var metaData = connection.getMetaData();
            var result = metaData.getTables(null, null, tableName.toLowerCase(), null);
            return result.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void runSqlScript(String filePath) {
        try (Connection connection = getConnection()) {
            String sql = new String(java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(filePath)
            ));

            var statement = connection.createStatement();
            statement.execute(sql);

            System.out.println("Database tabellen aangemaakt.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
