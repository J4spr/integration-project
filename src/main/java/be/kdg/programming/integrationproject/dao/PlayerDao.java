package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Player;
import be.kdg.programming.integrationproject.model.PlayerStats;
import be.kdg.programming.integrationproject.model.CpuPlayer;
import be.kdg.programming.integrationproject.model.HumanPlayer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao extends AbstractDao implements Dao<Player> {
    public PlayerDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    public List<PlayerStats> getDetailedLeaderboard() throws SQLException {
        List<PlayerStats> statsList = new ArrayList<>();
        String sql = """
        SELECT p."Username",
            COUNT(DISTINCT g."GameID") AS gamesPlayed,
            COUNT(DISTINCT CASE WHEN g."WinnerID" = p."PlayerID" THEN g."GameID" END) AS wins,
            SUM(COALESCE(m."ButtonsP1", 0) + COALESCE(m."ButtonsP2", 0)) AS totalButtons
        FROM "PlayerTable" p
        LEFT JOIN "GameTable" g ON p."PlayerID" = g."Player1ID" OR p."PlayerID" = g."Player2ID"
        LEFT JOIN "TurnTable" t ON g."GameID" = t."GameID"
        LEFT JOIN "MoveTable" m ON t."TurnID" = m."TurnID"
        GROUP BY p."Username"
        ORDER BY wins DESC;
            """;

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // 1. Get the raw values from the ResultSet columns
                String user = rs.getString("Username");
                int games = rs.getInt("gamesPlayed");
                int wins = rs.getInt("wins");
                int buttons = rs.getInt("totalButtons");

                // 2. Calculate the percentage in Java (since it's not in the SQL SELECT)
                double winPerc = (games == 0) ? 0 : (wins * 100.0 / games);

                // 3. Add to the list using your calculated 'winPerc' variable
                statsList.add(new PlayerStats(
                        user,
                        wins,
                        games,
                        winPerc,  // Use the variable, NOT rs.getDouble("winPercentage")
                        buttons
                ));
            }
        }
        return statsList;
    }

    public int getActivePlayerCount() throws SQLException {
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(DISTINCT \"PlayerID\") FROM \"PlayerTable\"")) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public Player findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Player> findAll() throws SQLException {
        return List.of();
    }
    @Override
    public void insert(Player player) throws SQLException {

        String username;

        if(player instanceof HumanPlayer human){
            username = human.getName();
        }else{
            username = "CPU_Player";
        }

        String checkSql =
                "SELECT \"PlayerID\" FROM \"PlayerTable\" WHERE \"Username\"=?";

        try(PreparedStatement check= getConnection().prepareStatement(checkSql)){

            check.setString(1,username);

            ResultSet rs=check.executeQuery();

            if(rs.next()){player.setPlayerId(rs.getInt("PlayerID"));
                return;
            }
        }

        String email= username.toLowerCase().replace(" ","") +"@game.com";

        String sql="""
    INSERT INTO "PlayerTable"
    ("Username","Email")
    VALUES (?,?)
    RETURNING "PlayerID"
    """;

        try(PreparedStatement st= getConnection().prepareStatement(sql)){

            st.setString(1,username);
            st.setString(2,email);

            ResultSet rs=st.executeQuery();

            if(rs.next()){player.setPlayerId(rs.getInt(1));}
        }
    }

    @Override
    public void update(Player player) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

    }

    @Override
    protected Connection getConnection() throws SQLException {
        return super.getConnection();
    }

    public void getGlobalStats() {

    }




}
