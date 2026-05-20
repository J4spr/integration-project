package be.kdg.programming.integrationproject.dao;

import java.sql.SQLException;
import java.util.List;

/**
 * Generic Data Access Object (DAO) interface defining core CRUD operations.
 *
 * @param <T> the type of domain object managed by this DAO
 * @author Team 4
 * @version 1.0
 */
public interface Dao<T> {

    /**
     * Retrieves a single record by its unique identifier.
     *
     * @param id the unique identifier of the entity
     * @return the entity matching the id, or {@code null} if no match is found
     * @throws SQLException if a database access error occurs
     */
    T findById(int id) throws SQLException;

    /**
     * Retrieves all records for the entity type from the database.
     *
     * @return a {@link List} containing all entities found, or an empty list
     * @throws SQLException if a database access error occurs
     */
    List<T> findAll() throws SQLException;

    /**
     * Inserts a new record into the database.
     *
     * @param t the entity to persist
     * @throws SQLException if a database access error occurs
     */
    void insert(T t) throws SQLException;

    /**
     * Updates an existing record in the database with new entity states.
     *
     * @param t the entity containing updated values
     * @throws SQLException if a database access error occurs
     */
    void update(T t) throws SQLException;

    /**
     * Deletes a record from the database based on its unique identifier.
     *
     * @param id the unique identifier of the entity to remove
     * @throws SQLException if a database access error occurs
     */
    void delete(int id) throws SQLException;
}