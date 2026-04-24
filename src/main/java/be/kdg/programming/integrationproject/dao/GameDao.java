package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDao extends AbstractDao implements Dao<Game> {

    public GameDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    public int getTotalGamesCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM \"GameTable\"";
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public String getAverageDuration() throws SQLException {
        String sql = "SELECT AVG(\"GameEndTime\"-\"GameStartTime\") FROM \"GameTable\" WHERE \"GameEndTime\" IS NOT NULL";
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getString(1) : "0";
        }
    }

    public int getTopScore() throws SQLException {
        String sql = "SELECT MAX(\"ButtonsP1\") FROM \"MoveTable\"";
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public Game findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Game> findAll() throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void insert(Game game) throws SQLException {
        // We slaan alleen de opstart-gegevens op. Winner etc. komt pas aan het einde van het spel (via een UPDATE).
        String sql = "INSERT INTO \"GameTable\" (\"GameType\", \"State\", \"Player1ID\", \"Player2ID\", \"StartingPlayer\", \"GameStartTime\") VALUES (?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Vul de attributen in vanuit ons Game object
            pstmt.setString(1, game.getGameType());
            pstmt.setString(2, game.getStatus().name());
            pstmt.setInt(3, game.getPlayer1().getPlayerId());
            pstmt.setInt(4, game.getPlayer2().getPlayerId());
            pstmt.setInt(5, game.getStartPlayer());
            pstmt.setTime(6, game.getGameStartTime());

            pstmt.executeUpdate();

            // Haal de automatisch gegenereerde GameID op uit de database
            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    game.setGameId(rs.getInt(1)); // Koppel de ID aan je lopende game!
                }
            }
        }
    }

    @Override
    public void update(Game game) throws SQLException {
        String sql = "UPDATE \"GameTable\" SET \"State\" = ?, \"WinnerID\" = ?, \"GameEndTime\" = ? WHERE \"GameID\" = ?;";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, game.getStatus().name());

            if (game.getWinner() != null) {
                pstmt.setInt(2, game.getWinner().getPlayerId());
            } else {
                pstmt.setNull(2, java.sql.Types.INTEGER);
            }

            java.sql.Time endTime = new java.sql.Time(System.currentTimeMillis());
            pstmt.setTime(3, endTime);

            pstmt.setInt(4, game.getGameId());

            pstmt.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {}
}