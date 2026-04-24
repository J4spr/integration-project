package be.kdg.programming.integrationproject.dao;

import be.kdg.programming.integrationproject.model.DbConnection;
import be.kdg.programming.integrationproject.model.Game;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import be.kdg.programming.integrationproject.model.HumanPlayer;
import be.kdg.programming.integrationproject.model.CpuPlayer;
import be.kdg.programming.integrationproject.model.Patch;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;
import be.kdg.programming.integrationproject.model.PatchPlacement;

public class GameDao extends AbstractDao implements Dao<Game> {

    public GameDao(DbConnection dbConnection) {
        super(dbConnection);
    }

    public int getTotalGamesCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM \"GameTable\"";
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    public String getAverageDuration() throws SQLException {
        String sql = "SELECT AVG(\"GameEndTime\"-\"GameStartTime\") FROM \"GameTable\" WHERE \"GameEndTime\" IS NOT NULL";
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getString(1) : "0";
        }
    }

    public int getTopScore() throws SQLException {
        String sql = "SELECT MAX(\"ButtonsP1\") FROM \"MoveTable\"";
        try (Connection c = getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    @Override
    public Game findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Game> findAll() throws SQLException {
        return new ArrayList<>();
    }

    @Override
    public void insert(Game game) throws SQLException {}

    @Override
    public void update(Game game) throws SQLException {}

    @Override
    public void delete(int id) throws SQLException {}

    public List<String> getUnfinishedGames() throws SQLException {

        List<String> games = new ArrayList<>();
        String sql = """
        SELECT  g."GameID",p."Username"
        FROM "GameTable" g
        JOIN "PlayerTable" p ON g."Player1ID"=p."PlayerID"
        WHERE g."State"='Ongoing'
        ORDER BY g."GameID"
        """;
        try(
                Connection c=getConnection();
                Statement st=c.createStatement();
                ResultSet rs=st.executeQuery(sql)
        ){

            while(rs.next()){
                games.add(rs.getString("Username") + " - Game #" + rs.getInt("GameID"));
            }
        }

        return games;
    }

    public Game loadUnfinishedGame(int gameId)
            throws SQLException {

        String gameSql="""
      SELECT *
      FROM "GameTable"
      WHERE "GameID"=?
    """;

        String moveSql="""
      SELECT m.*
      FROM "MoveTable" m
      JOIN "TurnTable" t
      ON m."TurnID"=t."TurnID"
      WHERE t."GameID"=?
      ORDER BY m."MoveID"
    """;

        try(Connection c=getConnection()){

            PreparedStatement gStmt= c.prepareStatement(gameSql);

            gStmt.setInt(1,gameId);

            ResultSet gRs=gStmt.executeQuery();

            if(!gRs.next()) return null;

            HumanPlayer p1=new HumanPlayer("Resumed Player");
            p1.setPlayerId(gRs.getInt("Player1ID"));

            CpuPlayer p2= new CpuPlayer(be.kdg.programming.integrationproject.model.Enums.Difficulty.EASY);

            p2.setPlayerId(gRs.getInt("Player2ID"));

            Game game= new Game(p1, p2, gRs.getInt("StartingPlayer"));

            game.setGameId(gameId);

            PreparedStatement mStmt= c.prepareStatement(moveSql);mStmt.setInt(1,gameId);

            ResultSet mRs= mStmt.executeQuery();

            while(mRs.next()){

                int patchId=mRs.getInt("PatchID");
                int row=mRs.getInt("SpacesMoved");
                int col=mRs.getInt("Position");
                int rot=mRs.getInt("RotationDegrees");
                int owner=mRs.getInt("PlayerID");

                Patch patch=game.getPatchStack().getPatch(patchId);

                if(patch!=null){

                    if(rot==90) patch.setRotation(PatchRotation.NINETY);
                    else if(rot==180) patch.setRotation(PatchRotation.ONEEIGHTY);
                    else if(rot==270) patch.setRotation(PatchRotation.TWOSEVENTY);

                    game.getPatchStack().removePatch(patchId);

                    if(owner==p1.getPlayerId()){
                        p1.getQuiltBoard().placePatch(patch,row,col);
                    }
                    else{
                        p2.getQuiltBoard().placePatch(patch,row,col);
                    }

                }

            }


            game.updateCurrentPlayer();

            return game;
        }
    }
    public void pauseGame(int gameId) throws SQLException {

        String sql = """
       UPDATE "GameTable"
       SET "State"='Ongoing'
       WHERE "GameID"=?
    """;
        try(PreparedStatement st = getConnection().prepareStatement(sql)){st.setInt(1, gameId);st.executeUpdate();}
    }
    public int createNewPausedGame(
            int player1Id,
            int player2Id,
            int startingPlayer
    ) throws SQLException {

        String sql = """
       INSERT INTO "GameTable"
       ("GameType","State","Player1ID","Player2ID","StartingPlayer","GameStartTime")
       VALUES('Standard','Ongoing',?,?,?,CURRENT_TIME)
       RETURNING "GameID"
""";

        try(PreparedStatement st= getConnection().prepareStatement(sql)){

            st.setInt(1,player1Id);
            st.setInt(2,player2Id);
            st.setInt(3,startingPlayer);

            ResultSet rs= st.executeQuery();

            if(rs.next()){return rs.getInt(1);}
        }

        return -1;
    }
    public void savePausedState(Game game) throws SQLException{
        String deleteMoves="""
DELETE FROM "MoveTable"
WHERE "TurnID" IN
(
SELECT "TurnID"
FROM "TurnTable"
WHERE "GameID"=?
)
""";

        String deleteTurns="""
DELETE FROM "TurnTable"
WHERE "GameID"=?
""";

        String createTurn="""
INSERT INTO "TurnTable"
("GameID",
"TurnStartTime")
VALUES(?,CURRENT_TIMESTAMP)RETURNING "TurnID"
""";
        String insertMove="""
INSERT INTO "MoveTable"
(
"TurnID",
"PlayerID",
"PatchID",
"MoveStartTime",
"SpecialPatchesCollected",
"ButtonsP1",
"ButtonsP2",
"SpacesMoved",
"Position",
"RotationDegrees"
)
VALUES(?,?,?,CURRENT_TIMESTAMP,0,?,?,?,?,?)""";

        Connection c=getConnection();

        PreparedStatement d1= c.prepareStatement(deleteMoves);

        d1.setInt(1, game.getGameId());

        d1.executeUpdate();

        PreparedStatement d2= c.prepareStatement(deleteTurns);

        d2.setInt(1, game.getGameId());

        d2.executeUpdate();

        PreparedStatement turn= c.prepareStatement(createTurn);

        turn.setInt(1, game.getGameId());
        ResultSet rs= turn.executeQuery();rs.next();

        int turnId= rs.getInt(1);

        PreparedStatement move= c.prepareStatement(insertMove);

        for(PatchPlacement pp : game.getPlayer1().getQuiltBoard().getPlacements()){

            move.setInt(1,turnId);
            move.setInt(2,game.getPlayer1().getPlayerId());
            move.setInt(3,pp.getPatch().getPatchID());

            move.setInt(4,game.getPlayer1().getTotalButtons());
            move.setInt(5,game.getPlayer2().getTotalButtons());

            move.setInt(6,pp.getRow());
            move.setInt(7,pp.getCol());

            move.setInt(
                    8,
                    pp.getPatch().getRotation().getRotation()
            );

            move.executeUpdate();

        }

        for(PatchPlacement pp :
                game.getPlayer2().getQuiltBoard().getPlacements()){

            move.setInt(1,turnId);
            move.setInt(2,game.getPlayer2().getPlayerId());
            move.setInt(3,pp.getPatch().getPatchID());

            move.setInt(4,game.getPlayer1().getTotalButtons());
            move.setInt(5,game.getPlayer2().getTotalButtons());

            move.setInt(6,pp.getRow());
            move.setInt(7,pp.getCol());

            move.setInt(
                    8,
                    pp.getPatch().getRotation().getRotation()
            );

            move.executeUpdate();

        }

        c.close();

    }
}

