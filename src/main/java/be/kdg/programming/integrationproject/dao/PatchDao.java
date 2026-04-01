package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Enums.PatchShape;
import be.kdg.programming.integrationproject.model.Patch;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatchDao extends AbstractDao implements Dao<Patch> {
    public PatchDao(DbConnection conn) {
        super(conn);
    }

    @Override
    public Patch findById(int id) throws SQLException {
        String sql = "SELECT * FROM \"PatchTable\" WHERE \"PatchID\" = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapResultSetToPatch(rs) : null;
            }
        }
    }

    @Override
    public List<Patch> findAll() throws SQLException {
        List<Patch> patches = new ArrayList<>();
        try (Connection c = getConnection(); Statement st = c.createStatement(); ResultSet rs = st.executeQuery("SELECT * FROM \"PatchTable\"")) {
            while (rs.next()) patches.add(mapResultSetToPatch(rs));
        }
        return patches;
    }

    @Override
    public void insert(Patch p) throws SQLException {
        String sql = "INSERT INTO \"PatchTable\" (\"ButtonCost\", \"TimeCost\", \"ButtonIncome\") VALUES (?, ?, ?)";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getButtonCost());
            ps.setInt(2, p.getTimeCost());
            ps.setInt(3, p.getButtonIncome());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Patch p) throws SQLException {
        String sql = "UPDATE \"PatchTable\" SET \"ButtonCost\" = ?, \"TimeCost\" = ?, \"ButtonIncome\" = ? WHERE \"PatchID\" = ?";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getButtonCost());
            ps.setInt(2, p.getTimeCost());
            ps.setInt(3, p.getButtonIncome());
            ps.setInt(4, p.getPatchID());
            ps.executeUpdate();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement("DELETE FROM \"PatchTable\" WHERE \"PatchID\" = ?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Patch mapResultSetToPatch(ResultSet rs) throws SQLException {
        return new Patch(rs.getInt("PatchID"), PatchShape.values()[0], rs.getInt("ButtonCost"), rs.getInt("TimeCost"), rs.getInt("ButtonIncome"));
    }
}