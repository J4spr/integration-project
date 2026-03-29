package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Player;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao extends AbstractDao implements Dao<Player> {
    public PlayerDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    public List<String[]> getLeaderboardData() throws SQLException {
        List<String[]> data = new ArrayList<>();
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

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int rank = 1;
            while (rs.next()) {
                String name = rs.getString("Username");
                int wins = rs.getInt("wins");
                int games = rs.getInt("gamesPlayed");
                double winPerc = games == 0 ? 0 : (wins * 100.0 / games);

                data.add(new String[]{
                        "" + rank++, name, "" + wins,
                        String.format("%.1f", winPerc), "" + wins, "-", "-"
                });
            }
        }
        return data;
    }

    public int getActivePlayerCount() throws SQLException {
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(DISTINCT \"PlayerID\") FROM \"PlayerTable\"")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public Player findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Player> findAll() throws SQLException {
        return List.of();
    }

    @Override
    public void insert(Player player) throws SQLException {

    }

    @Override
    public void update(Player player) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    protected Connection getConnection() throws SQLException {
        return super.getConnection();
    }

    public void getGlobalStats() {

    }


}
