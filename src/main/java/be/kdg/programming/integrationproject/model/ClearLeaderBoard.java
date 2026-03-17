package be.kdg.programming.integrationproject.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

public class ClearLeaderBoard {
    private final DbConnection dbConn;

    public ClearLeaderBoard() {
        this.dbConn = new DbConnection();
    }

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