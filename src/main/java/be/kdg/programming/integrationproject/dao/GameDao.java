package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import be.kdg.programming.integrationproject.model.Game;
import be.kdg.programming.integrationproject.model.HumanPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDao extends AbstractDao implements Dao<Game> {
    public GameDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    @Override
    public Game findById(int id) throws SQLException {
        String sql = "SELECT * FROM \"GameTable\" WHERE \"GameID\" = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSetToGame(rs) : null;
            }
        }
    }

    @Override
    public List<Game> findAll() throws SQLException {
        List<Game> games = new ArrayList<>();
        String sql = "SELECT * FROM \"GameTable\"";
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) games.add(mapResultSetToGame(rs));
        }
        return games;
    }

    @Override
    public void insert(Game game) throws SQLException {
        String sql = """
        INSERT INTO "GameTable" 
        ("GameType", "State", "Player1ID", "Player2ID", "StartingPlayer", "GameStartTime") 
        VALUES (?, ?, ?, ?, ?, ?);
    """;
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, "NORMAL");
            ps.setString(2, game.getStatus().name());
            ps.setInt(3, game.getPlayer1().getPlayerId());

            // --- THE FIX ---
            // If player 2 is human and has a valid DB ID, save it.
            // Otherwise, save it as NULL so the Foreign Key doesn't complain.
            if (game.getPlayer2() instanceof HumanPlayer && game.getPlayer2().getPlayerId() > 0) {
                ps.setInt(4, game.getPlayer2().getPlayerId());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            // ---------------

            ps.setInt(5, game.getStartPlayer());
            ps.setTime(6, new java.sql.Time(System.currentTimeMillis()));

            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) game.setGameId(rs.getInt(1));
            }
        }
    }

    @Override
    public void update(Game game) throws SQLException {
        String sql = """
                UPDATE "GameTable" SET "State" = ?, "WinnerID" = ?, "GameEndTime" = ?, 
                "EmptySpacesP1" = ?, "EmptySpacesP2" = ? WHERE "GameID" = ?
                """;
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, game.getStatus().name());
            ps.setObject(2, game.getWinner() != null ? game.getWinner().getPlayerId() : null, Types.INTEGER);
            ps.setTime(3, game.getStatus() == GameStatus.FINISHED ? new Time(System.currentTimeMillis()) : null);
            ps.setInt(4, game.getPlayer1().getQuiltBoard().countEmptySpaces());
            ps.setInt(5, game.getPlayer2().getQuiltBoard().countEmptySpaces());
            ps.setInt(6, game.getGameId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM \"GameTable\" WHERE \"GameID\" = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Game mapResultSetToGame(ResultSet rs) throws SQLException {
        // Basic reconstruction; logic assumes players are handled by a manager/service
        Game game = new Game(null, null, rs.getInt("StartingPlayer"));
        game.setGameId(rs.getInt("GameID"));
        game.setStatus(GameStatus.valueOf(rs.getString("State")));
        return game;
    }
}