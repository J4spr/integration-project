package be.kdg.programming.integrationproject.model.dao;

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
    public Patch findById(int patchId) throws SQLException {
        String sql = "SELECT * FROM \"PatchTable\" WHERE \"PatchID\" = ?;";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql)) {
            stmnt.setInt(1, patchId);
            try (ResultSet rs = stmnt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatch(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Patch> findAll() throws SQLException {
        List<Patch> patches = new ArrayList<>();
        String sql = "SELECT * FROM \"PatchTable\";";
        try (Statement stmnt = getConnection().createStatement();
             ResultSet rs = stmnt.executeQuery(sql)) {
            while (rs.next()) {
                patches.add(mapResultSetToPatch(rs));
            }
        }
        return patches;
    }

    @Override
    public void insert(Patch patch) throws SQLException {
        String sql = "INSERT INTO \"PatchTable\" (\"ButtonCost\", \"TimeCost\", \"ButtonIncome\") VALUES (?, ?, ?);";
        try (PreparedStatement stmnt = getConnection().prepareStatement(sql)) {
            stmnt.setInt(1, patch.getButtonCost());
            stmnt.setInt(2, patch.getTimeCost());
            stmnt.setInt(3, patch.getButtonIncome());
            stmnt.executeUpdate();
        }
    }

    private Patch mapResultSetToPatch(ResultSet rs) throws SQLException {
        return new Patch(
                rs.getInt("PatchID"),
                PatchShape.L_SHAPE, // placeholder
                rs.getInt("ButtonCost"),
                rs.getInt("TimeCost"),
                rs.getInt("ButtonIncome")
        );
    }

    @Override public void update(Patch patch) { /* Implementation here */ }
    @Override public void delete(int id) { /* Implementation here */ }
}