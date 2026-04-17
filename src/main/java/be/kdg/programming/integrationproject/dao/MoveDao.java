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
    public Move findById(int moveId) {
        String sql = "SELECT * FROM \"MoveTable\" WHERE \"MoveID\" = ?;";

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("Geen DB connectie in MoveDao.findById");
            return null;
        }

        try (PreparedStatement stmnt = conn.prepareStatement(sql)) {
            stmnt.setInt(1, moveId);

            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMove(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout in MoveDao.findById");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Move> findAll() {
        List<Move> moves = new ArrayList<>();
        String sql = "SELECT * FROM \"MoveTable\";";

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("Geen DB connectie in MoveDao.findAll");
            return moves;
        }

        try (Statement stmnt = conn.createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {

            while (rs.next()) {
                moves.add(mapResultSetToMove(rs));
            }

        } catch (SQLException e) {
            System.err.println("Fout in MoveDao.findAll");
            e.printStackTrace();
        }

        return moves;
    }

    @Override
    public void insert(Move move) {
        String sql = "INSERT INTO \"MoveTable\" (\"TurnID\", \"PatchID\", \"MoveStartTime\", \"MoveEndTime\", \"SpecialPatchesCollected\", \"SpacesMoved\", \"Position\", \"RotationDegrees\", \"ButtonsP1\", \"ButtonsP2\") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        Connection conn = getConnection();
        if (conn == null) {
            System.err.println("Geen DB connectie in MoveDao.insert");
            return;
        }

        try (PreparedStatement stmnt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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

            try (ResultSet keys = stmnt.getGeneratedKeys()) {
                if (keys.next()) {
                    move.setMoveId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout in MoveDao.insert");
            e.printStackTrace();
        }
    }

    @Override
    public void update(Move move) {
        System.err.println("update() is niet ondersteund in MoveDao");
    }

    @Override
    public void delete(int id) {
        System.err.println("delete() is niet ondersteund in MoveDao");
    }

    /**
     * Helper methode om ResultSet om te zetten naar Move object
     * Deze mag intern nog SQLException gooien (wordt opgevangen door bovenliggende try-catch)
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
}