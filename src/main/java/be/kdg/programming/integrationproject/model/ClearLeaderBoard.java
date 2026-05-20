package be.kdg.programming.integrationproject.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

/**
 * Utility class responsible for wiping database transactional table data.
 * It reads a resource-based database script file (`/db.sql`) and applies it sequentially.
 *
 * @author Team 4
 * @version 1.0
 */
public class ClearLeaderBoard {
    /** The active target database connection profile instance. */
    private final DbConnection dbConn;

    /**
     * Initializes a new ClearLeaderBoard utility instance.
     */
    public ClearLeaderBoard() {
        this.dbConn = new DbConnection();
    }

    /**
     * Establishes a database transaction window to execute the structural reset script.
     * Catches and prints internal execution anomalies gracefully to standard error logs.
     */
    public void executeClear() {
        String scriptPath = "/db.sql";

        try (Connection conn = dbConn.getConnection()) {
            clear(conn, scriptPath);
            System.out.println("Leaderboard cleared successfully.");
        } catch (Exception e) {
            System.err.println("Error executing script: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Parses the resource script line-by-line, stripping comment markers,
     * buffering statements, and committing them to the connection upon encountering a semicolon.
     *
     * @param conn         the active target {@link Connection} to process statements against
     * @param resourcePath the relative classpath resource string to target for extraction
     * @throws Exception if the resource file cannot be resolved or database execution errors out
     */
    private void clear(Connection conn, String resourcePath) throws Exception {
        InputStream is = getClass().getResourceAsStream(resourcePath);
        if (is == null) throw new FileNotFoundException("Script not found in resources: " + resourcePath);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));
             Statement statement = conn.createStatement()) {

            String line;
            StringBuilder sqlStatement = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                String trimmedLine = line.trim();

                if (trimmedLine.isEmpty() || trimmedLine.startsWith("--") || trimmedLine.startsWith("//")) {
                    continue;
                }

                sqlStatement.append(line).append(" ");

                if (trimmedLine.endsWith(";")) {
                    statement.execute(sqlStatement.toString());
                    sqlStatement.setLength(0);
                }
            }
        }
    }
}