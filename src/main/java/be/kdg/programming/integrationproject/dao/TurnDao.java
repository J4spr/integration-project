package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Turn;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object mapping structural timelines for sequential play interactions
 * contained within the {@code TurnTable}.
 *
 * @author Team 4
 * @version 1.0
 */
public class TurnDao extends AbstractDao implements Dao<Turn> {

    /**
     * Initializes a new TurnDao instance.
     *
     * @param conn the database connection manager
     */
    public TurnDao(DbConnection conn) {
        super(conn);
    }

    @Override
    public Turn findById(int turnId) throws SQLException {
        String sql = "SELECT * FROM \"TurnTable\" WHERE \"TurnID\" = ?;";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql)) {
            stmnt.setInt(1, turnId);
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTurn(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Turn> findAll() throws SQLException {
        List<Turn> turns = new ArrayList<>();
        String sql = "SELECT * FROM \"TurnTable\";";
        try (Statement stmnt = getConnection().createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                turns.add(mapResultSetToTurn(rs));
            }
        }
        return turns;
    }

    @Override
    public void insert(Turn turn) throws SQLException {
        String sql = "INSERT INTO \"TurnTable\" (\"GameID\", \"TurnStartTime\", \"TurnEndTime\") VALUES (?, ?, ?);";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql)) {
            stmnt.setInt(1, turn.getGameId());
            stmnt.setTime(2, turn.getTurnStartTime());
            stmnt.setTime(3, turn.getTurnEndTime()); // Can be null if turn is ongoing
            stmnt.executeUpdate();
        }
    }

    /**
     * Maps ResultSet tracking data directly into clear runtime game context objects.
     *
     * @param rs active database transaction response tracking cursor
     * @return constructed Turn container configuration instance
     * @throws SQLException if a column reading assignment error triggers
     */
    private Turn mapResultSetToTurn(ResultSet rs) throws SQLException {
        return new Turn(
                rs.getInt("TurnID"),
                rs.getInt("GameID"),
                rs.getTime("TurnStartTime"),
                rs.getTime("TurnEndTime")
        );
    }

    @Override
    public void update(Turn turn) { /* Implementation here */ }

    @Override
    public void delete(int id) { /* Implementation here */ }
}