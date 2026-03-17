package be.kdg.programming.integrationproject.model.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Move; // Assuming you have a Move model
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
                    // You must provide ALL arguments defined in the Move constructor
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
//                TODO add logic here
            }
        }
        return moves;
    }

    @Override
    public void insert(Move move) throws SQLException {
        String sql = "INSERT INTO \"MoveTable\" (\"TurnID\", \"PatchID\", \"MoveStartTime\", \"SpacesMoved\") VALUES (?, ?, ?, ?);";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql)) {
            // stmnt.setInt(1, move.getTurnId()); ... etc
            stmnt.executeUpdate();
        }
    }

    // Implement update and delete similarly...
    @Override public void update(Move move) {}
    @Override public void delete(int id) {}
}