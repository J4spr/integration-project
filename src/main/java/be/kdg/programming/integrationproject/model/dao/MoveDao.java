package be.kdg.programming.integrationproject.model.dao;

import be.kdg.programming.integrationproject.model.DbConnection;

public class MoveDao {
    private DbConnection conn;

    public MoveDao(DbConnection conn) {
        this.conn = conn;
    }
}
