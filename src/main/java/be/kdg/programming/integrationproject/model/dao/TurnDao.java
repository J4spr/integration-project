package be.kdg.programming.integrationproject.model.dao;

import be.kdg.programming.integrationproject.model.DbConnection;

public class TurnDao {
    private DbConnection conn;

    public TurnDao(DbConnection conn) {
        this.conn = conn;
    }
}
