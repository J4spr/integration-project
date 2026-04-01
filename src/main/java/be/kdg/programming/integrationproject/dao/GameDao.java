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
    public void insert(Game game) throws SQLException {}

    @Override
    public void update(Game game) throws SQLException {}

    @Override
    public void delete(int id) throws SQLException {}
}