package be.kdg.programming.integrationproject.model.dao;

import be.kdg.programming.integrationproject.model.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoveDao {
    private DbConnection conn;
    private PreparedStatement stmnt;
    private ResultSet rs;

    public MoveDao(DbConnection conn) {
        this.conn = conn;
    }

    public void findAll() throws SQLException {
        String sql = "SELECT * FROM MoveTable;";
        try {
            PreparedStatement stmt = this.conn.getConnection().prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void findById(int moveId) {
        String sql = "SELECT * FROM MoveTable WHERE MoveID = ?;";
        try {
            this.stmnt.setInt(1, moveId);
            this.stmnt = conn.getConnection().prepareStatement(sql);
            this.rs = this.stmnt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
