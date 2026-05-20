package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract base class for Data Access Objects.
 * Provides underlying structural support for database connectivity management.
 *
 * @author Team 4
 * @version 1.0
 */
public abstract class AbstractDao {
    /** The database connection manager instance. */
    protected DbConnection dbConnection;

    /**
     * Constructs an AbstractDao wrapper with a specified connection manager.
     *
     * @param dbConnection the database connection wrapper to handle sessions
     */
    protected AbstractDao(DbConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    /**
     * Obtains an active connection instance to the database.
     *
     * @return an active {@link Connection} object
     * @throws SQLException if a database access configuration error occurs
     */
    protected Connection getConnection() throws SQLException {
        return dbConnection.getConnection();
    }
}