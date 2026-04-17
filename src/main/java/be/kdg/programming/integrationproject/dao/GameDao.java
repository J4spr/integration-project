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

    public int getTotalGamesCount() {
        String sql = "SELECT COUNT(*) FROM \"GameTable\"";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in getTotalGamesCount");
            return 0;
        }

        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            System.err.println("Fout bij getTotalGamesCount");
            e.printStackTrace();
        }

        return 0;
    }

    public String getAverageDuration() {
        String sql = "SELECT AVG(\"GameEndTime\"-\"GameStartTime\") FROM \"GameTable\" WHERE \"GameEndTime\" IS NOT NULL";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in getAverageDuration");
            return "0";
        }

        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            return rs.next() ? rs.getString(1) : "0";

        } catch (SQLException e) {
            System.err.println("Fout bij getAverageDuration");
            e.printStackTrace();
        }

        return "0";
    }

    public int getTopScore() {
        String sql = "SELECT MAX(\"ButtonsP1\") FROM \"MoveTable\"";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in getTopScore");
            return 0;
        }

        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (SQLException e) {
            System.err.println("Fout bij getTopScore");
            e.printStackTrace();
        }

        return 0;
    }

    @Override public Game findById(int id) { return null; }
    @Override public List<Game> findAll() { return new ArrayList<>(); }
    @Override public void insert(Game game) {}
    @Override public void update(Game game) {}
    @Override public void delete(int id) {}
}