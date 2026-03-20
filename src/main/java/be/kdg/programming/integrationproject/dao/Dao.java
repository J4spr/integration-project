package be.kdg.programming.integrationproject.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    T findById(int id) throws SQLException;
    List<T> findAll() throws SQLException;
    void insert(T t) throws SQLException;
    void update(T t) throws SQLException;
    void delete(int id) throws SQLException;
}
