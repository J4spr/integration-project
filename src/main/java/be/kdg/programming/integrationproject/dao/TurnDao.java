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
    public Turn findById(int turnId) {
        String sql = "SELECT * FROM \"TurnTable\" WHERE \"TurnID\" = ?;";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in TurnDao.findById");
            return null;
        }

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, turnId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToTurn(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout in TurnDao.findById");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Turn> findAll() {
        List<Turn> turns = new ArrayList<>();

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in TurnDao.findAll");
            return turns;
        }

        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM \"TurnTable\"")) {

            while (rs.next()) {
                turns.add(mapResultSetToTurn(rs));
            }

        } catch (SQLException e) {
            System.err.println("Fout in TurnDao.findAll");
            e.printStackTrace();
        }

        return turns;
    }

    @Override
    public void insert(Turn turn) {
        String sql = "INSERT INTO \"TurnTable\" (\"GameID\", \"TurnStartTime\", \"TurnEndTime\") VALUES (?, ?, ?)";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in TurnDao.insert");
            return;
        }

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, turn.getGameId());
            st.setTime(2, turn.getTurnStartTime());
            st.setTime(3, turn.getTurnEndTime());
            st.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fout in TurnDao.insert");
            e.printStackTrace();
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

    @Override public void update(Turn turn) {}
    @Override public void delete(int id) {}
}