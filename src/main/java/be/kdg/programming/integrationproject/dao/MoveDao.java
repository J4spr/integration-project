package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Move;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MoveDao extends AbstractDao implements Dao<Move> {

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

    @Override
    public void insert(Move move) throws SQLException {
        String sql = "INSERT INTO \"MoveTable\" (\"TurnID\", \"PatchID\", \"MoveStartTime\", \"MoveEndTime\", \"SpecialPatchesCollected\", \"SpacesMoved\", \"Position\", \"RotationDegrees\", \"ButtonsP1\", \"ButtonsP2\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        //RETURN_GENERATED_KEYS ensures the DB-generated MoveID is accessible after insert
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
            //read back the generated ID and set it on the move object
            ResultSet keys = stmnt.getGeneratedKeys();
            if (keys.next()) {
                move.setMoveId(keys.getInt(1));
            }
        }
    }

    //maps a single row from the ResultSet to a Move object
    //used by both findById() and findAll() to avoid code duplication
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

    @Override
    public void update(Move move) {
        throw new UnsupportedOperationException("update() is not supported for MoveDao");
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("delete() is not supported for MoveDao");
    }
}