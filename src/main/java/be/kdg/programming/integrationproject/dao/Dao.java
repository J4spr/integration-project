package be.kdg.programming.integrationproject.dao;

import java.sql.SQLException;
import java.util.List;

public interface Dao<T> {
    T findById(int id);
    List<T> findAll();
    void insert(T t);
    void update(T t);
    void delete(int id);
}
