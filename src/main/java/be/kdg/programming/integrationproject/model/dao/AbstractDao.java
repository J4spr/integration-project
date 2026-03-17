package be.kdg.programming.integrationproject.model.dao;

import be.kdg.programming.integrationproject.model.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDao {
    protected DbConnection dbConnection;

    protected AbstractDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    protected Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }
}
