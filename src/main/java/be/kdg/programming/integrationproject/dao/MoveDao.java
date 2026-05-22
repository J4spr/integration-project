package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Move;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object managing operations targeted around single player step logs
 * inside the {@code MoveTable}.
 * Note: Mutation processes such as {@code update} and {@code delete} are explicitly unsupported.
 *
 * @author Team 4
 * @version 1.0
 */
public class MoveDao extends AbstractDao implements Dao<Move> {

    /**
     * Initializes a new MoveDao instance.
     *
     * @param conn the database connection manager
     */
    public MoveDao(DbConnection conn) {
        super(conn);
    }

    @Override
    public Move findById(int moveId) throws SQLException {
        String sql = "SELECT * FROM \"MoveTable\" WHERE \"MoveID\" = ?;";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql)) {
            stmnt.setInt(1, moveId);
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMove(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Move> findAll() throws SQLException {
        List<Move> moves = new ArrayList<>();
        String sql = "SELECT * FROM \"MoveTable\";";
        try (Statement stmnt = getConnection().createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                moves.add(mapResultSetToMove(rs));
            }
        }
        return moves;
    }

    /**
     * Inserts a player move event log row and synchronizes the generated structural auto-increment
     * Primary Key assignment straight back to the runtime model domain object reference.
     *
     * @param move target move configuration instance payload
     * @throws SQLException if a database storage error occurs
     */
    @Override
    public void insert(Move move) throws SQLException {
        String sql = "INSERT INTO \"MoveTable\" (\"TurnID\", \"PatchID\", \"MoveStartTime\", \"MoveEndTime\", \"SpecialPatchesCollected\", \"SpacesMoved\", \"Position\", \"RotationDegrees\", \"ButtonsP1\", \"ButtonsP2\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmnt.setInt(1, move.getTurnId());
            stmnt.setInt(2, move.getPatchId());
            stmnt.setTime(3, move.getMoveStartTime());
            stmnt.setTime(4, move.getMoveEndTime());
            stmnt.setInt(5, move.getSpecialPatchesCollected());
            stmnt.setInt(6, move.getSpacesMoved());
            stmnt.setInt(7, move.getPosition());
            stmnt.setInt(8, move.getRotationDegrees());
            stmnt.setInt(9, move.getButtonsP1());
            stmnt.setInt(10, move.getButtonsP2());
            stmnt.executeUpdate();

            ResultSet keys = stmnt.getGeneratedKeys();
            if (keys.next()) {
                move.setMoveId(keys.getInt(1));
            }
        }
    }

    /**
     * Maps database rows parsed from raw ResultSets down cleanly into single domain objects.
     *
     * @param rs an active cursor from an executed SQL search action sequence
     * @return a structured operational runtime object representation container mapping matching fields
     * @throws SQLException if query index column adjustments throw structural format exceptions
     */
    private Move mapResultSetToMove(ResultSet rs) throws SQLException {
        return new Move(
                rs.getInt("MoveID"),
                rs.getInt("TurnID"),
                rs.getInt("PatchID"),
                rs.getTime("MoveStartTime"),
                rs.getTime("MoveEndTime"),
                rs.getInt("SpecialPatchesCollected"),
                rs.getInt("SpacesMoved"),
                rs.getInt("Position"),
                rs.getInt("RotationDegrees"),
                rs.getInt("ButtonsP1"),
                rs.getInt("ButtonsP2")
        );
    }

    /**
     * Unsupported modification tracking functionality constraint.
     *
     * @throws UnsupportedOperationException update sequences cannot modify historical steps
     */
    @Override
    public void update(Move move) {
        throw new UnsupportedOperationException("update() is not supported for MoveDao");
    }

    /**
     * Unsupported step erasure structural constraint.
     *
     * @throws UnsupportedOperationException step historical logs cannot be cleared individually
     */
    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("delete() is not supported for MoveDao");
    }
}