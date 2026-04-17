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
    public Patch findById(int patchId) {
        String sql = "SELECT * FROM \"PatchTable\" WHERE \"PatchID\" = ?;";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in PatchDao.findById");
            return null;
        }

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, patchId);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPatch(rs);
                }
            }

        } catch (SQLException e) {
            System.err.println("Fout in PatchDao.findById");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Patch> findAll() {
        List<Patch> patches = new ArrayList<>();
        String sql = "SELECT * FROM \"PatchTable\";";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in PatchDao.findAll");
            return patches;
        }

        try (Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                patches.add(mapResultSetToPatch(rs));
            }

        } catch (SQLException e) {
            System.err.println("Fout in PatchDao.findAll");
            e.printStackTrace();
        }

        return patches;
    }

    @Override
    public void insert(Patch patch) {
        String sql = "INSERT INTO \"PatchTable\" (\"ButtonCost\", \"TimeCost\", \"ButtonIncome\") VALUES (?, ?, ?);";

        Connection c = getConnection();
        if (c == null) {
            System.err.println("Geen DB connectie in PatchDao.insert");
            return;
        }

        try (PreparedStatement st = c.prepareStatement(sql)) {
            st.setInt(1, patch.getButtonCost());
            st.setInt(2, patch.getTimeCost());
            st.setInt(3, patch.getButtonIncome());
            st.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Fout in PatchDao.insert");
            e.printStackTrace();
        }
    }

    private Patch mapResultSetToPatch(ResultSet rs) throws SQLException {
        return new Patch(
                rs.getInt("PatchID"),
                PatchShape.BIG_L,
                rs.getInt("ButtonCost"),
                rs.getInt("TimeCost"),
                rs.getInt("ButtonIncome")
        );
    }

    @Override public void update(Patch patch) {}
    @Override public void delete(int id) {}
}