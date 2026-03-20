package be.kdg.programming.integrationproject.presenter;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.view.LeaderboardView;
import be.kdg.programming.integrationproject.view.MainMenuView;

import java.sql.*;

public class LeaderboardPresenter {

    private final LeaderboardView view;
    private final MainMenuView mainMenuView;
    private final DbConnection conn;

    public LeaderboardPresenter(
            LeaderboardView view,
            MainMenuView mainMenuView,
            DbConnection conn
    ) {
        this.view = view;
        this.mainMenuView = mainMenuView;
        this.conn = conn;

        loadStats();
        loadTable();
        addHandlers();
    }

    private void loadStats() {

        try (Connection c = conn.getConnection()) {

            Statement st = c.createStatement();

            ResultSet rs1 = st.executeQuery(
                    "SELECT COUNT(*) FROM \"GameTable\""
            );
            if (rs1.next())
                view.setTotalGames("Total Games: " + rs1.getInt(1));

            ResultSet rs2 = st.executeQuery(
                    "SELECT AVG(\"GameEndTime\"-\"GameStartTime\") FROM \"GameTable\" WHERE \"GameEndTime\" IS NOT NULL"
            );
            if (rs2.next())
                view.setAvgDuration("Avg Duration: " + rs2.getString(1));

            ResultSet rs3 = st.executeQuery(
                    "SELECT MAX(\"ButtonsP1\") FROM \"MoveTable\""
            );
            if (rs3.next())
                view.setTopScore("Top Score: " + rs3.getInt(1));

            ResultSet rs4 = st.executeQuery(
                    "SELECT COUNT(DISTINCT \"PlayerID\") FROM \"PlayerTable\""
            );
            if (rs4.next())
                view.setActivePlayers("Players: " + rs4.getInt(1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadTable() {

        try (Connection c = conn.getConnection()) {

            String sql = """
                    SELECT p."Username",
                           COUNT(g."GameID") AS gamesPlayed,
                           COUNT(CASE WHEN g."WinnerID" = p."PlayerID" THEN 1 END) AS wins
                    FROM "PlayerTable" p
                    LEFT JOIN "GameTable" g
                    ON p."PlayerID" = g."Player1ID"
                    OR p."PlayerID" = g."Player2ID"
                    GROUP BY p."Username"
                    ORDER BY wins DESC
                    """;

            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int rank = 1;

            while (rs.next()) {

                String name = rs.getString("Username");
                int wins = rs.getInt("wins");
                int games = rs.getInt("gamesPlayed");

                double winPerc = games == 0 ? 0 :
                        (wins * 100.0 / games);

                view.getTable().getItems().add(
                        new String[]{
                                "" + rank++,
                                name,
                                "" + wins,
                                String.format("%.1f", winPerc),
                                "" + wins,
                                "-",
                                "-"
                        }
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHandlers() {
        view.getBtnBack().setOnAction(e ->
                view.getPane().getScene().setRoot(mainMenuView.getPane())
        );
    }
}