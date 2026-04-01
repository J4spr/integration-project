package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.HumanPlayer;
import be.kdg.programming.integrationproject.model.Player;
import be.kdg.programming.integrationproject.model.PlayerStats;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao extends AbstractDao implements Dao<Player> {
    public PlayerDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    public Player findById(int id) throws SQLException {
        String sql = "SELECT * FROM \"PlayerTable\" WHERE \"PlayerID\" = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Player p = new HumanPlayer(rs.getString("Username"));
                    p.setPlayerId(rs.getInt("PlayerID"));
                    return p;
                }
            }
        }
        return null;
    }

    public Player findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM \"PlayerTable\" WHERE \"Username\" = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Player p = new HumanPlayer(rs.getString("Username"));
                    p.setPlayerId(rs.getInt("PlayerID"));
                    return p;
                }
            }
        }
        return null;
    }

    @Override
    public void insert(Player player) throws SQLException {
        String sql = "INSERT INTO \"PlayerTable\" (\"Username\", \"Email\") VALUES (?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Assumes HumanPlayer or casting for email access
            ps.setString(1, ((HumanPlayer) player).getName());
            ps.setString(2, "temp@kdg.be");
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) player.setPlayerId(rs.getInt(1));
            }
        }
    }

    @Override
    public List<Player> findAll() throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void update(Player player) throws SQLException {
    }

    @Override
    public void delete(int id) throws SQLException {
    }

    public List<PlayerStats> getDetailedLeaderboard() throws SQLException {
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

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // 1. Get the raw values from the ResultSet columns
                String user = rs.getString("Username");
                int games = rs.getInt("gamesPlayed");
                int wins = rs.getInt("wins");
                int buttons = rs.getInt("totalButtons");

                // 2. Calculate the percentage in Java (since it's not in the SQL SELECT)
                double winPerc = (games == 0) ? 0 : (wins * 100.0 / games);

                // 3. Add to the list using your calculated 'winPerc' variable
                statsList.add(new PlayerStats(
                        user,
                        wins,
                        games,
                        winPerc,  // Use the variable, NOT rs.getDouble("winPercentage")
                        buttons
                ));
            }
        }
        return statsList;
    }
}