package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Turn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TurnDao extends AbstractDao implements Dao<Turn> {

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
        String sql = "INSERT INTO \"TurnTable\" (\"GameID\", \"TurnStartTime\") VALUES (?, ?);";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, turn.getGameId());
            ps.setTime(2, turn.getTurnStartTime());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    turn.setTurnId(rs.getInt(1));
                }
            }
        }
    }

    private Turn mapResultSetToTurn(ResultSet rs) throws SQLException {
        return new Turn(
                rs.getInt("TurnID"),
                rs.getInt("GameID"),
                rs.getTime("TurnStartTime"),
                rs.getTime("TurnEndTime")
        );
    }

    @Override
    public void update(Turn turn) throws SQLException {
        String sql = "UPDATE \"TurnTable\" SET \"TurnEndTime\" = ? WHERE \"TurnID\" = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setTime(1, turn.getTurnEndTime());
            ps.setInt(2, turn.getTurnId());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM \"TurnTable\" WHERE \"TurnID\" = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}