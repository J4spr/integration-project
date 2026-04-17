package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao extends AbstractDao implements Dao<Player> {

    public PlayerDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    public List<PlayerStats> getDetailedLeaderboard() {
        List<PlayerStats> statsList = new ArrayList<>();

        String sql = """
        SELECT p."Username",
            COUNT(DISTINCT g."GameID") AS gamesPlayed,
            COUNT(DISTINCT CASE WHEN g."WinnerID" = p."PlayerID" THEN g."GameID" END) AS wins,
            SUM(COALESCE(m."ButtonsP1", 0) + COALESCE(m."ButtonsP2", 0)) AS totalButtons
        FROM "PlayerTable" p
        LEFT JOIN "GameTable" g ON p."PlayerID" = g."Player1ID" OR p."PlayerID" = g."Player2ID"
        LEFT JOIN "TurnTable" t ON g."GameID" = t."GameID"
        LEFT JOIN "MoveTable" m ON t."TurnID" = m."TurnID"
        GROUP BY p."Username"
        ORDER BY wins DESC;
        """;

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in getDetailedLeaderboard");
            return statsList;
        }

        try (PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String user = rs.getString("Username");
                int games = rs.getInt("gamesPlayed");
                int wins = rs.getInt("wins");
                int buttons = rs.getInt("totalButtons");

                double winPerc = (games == 0) ? 0 : (wins * 100.0 / games);

                statsList.add(new PlayerStats(user, wins, games, winPerc, buttons));
            }

        } catch (SQLException e) {
            System.err.println("Fout in getDetailedLeaderboard");
            e.printStackTrace();
        }

        return statsList;
    }

    public int getActivePlayerCount() {
        Connection c = getConnection();

        if (c == null) {
            System.err.println("Geen DB connectie in getActivePlayerCount");
            return 0;
        }

        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(DISTINCT \"PlayerID\") FROM \"PlayerTable\"")) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            System.err.println("Fout in getActivePlayerCount");
            e.printStackTrace();
        }

        return 0;
    }

    @Override public Player findById(int id) { return null; }
    @Override public List<Player> findAll() { return new ArrayList<>(); }
    @Override public void insert(Player player) {}
    @Override public void update(Player player) {}
    @Override public void delete(int id) {}
}