package be.kdg.programming.integrationproject;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void createTables(Connection conn) {

        try {
            // Maak een SQL statement-object om queries uit te voeren

            Statement stmt = conn.createStatement();

            // PLAYER TABLE
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS PlayerTable(
                PlayerID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                Username VARCHAR(50) NOT NULL CHECK (Username NOT LIKE '% %'), -- Username mag geen spaties bevatten
                Email VARCHAR(100) NOT NULL
            )
            """);

            // PATCH TABLE
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS PatchTable(
                PatchID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                ButtonCost INTEGER NOT NULL,
                TimeCost INTEGER NOT NULL,
                ButtonIncome INTEGER NOT NULL
            )
            """);

            // GAME TABLE
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS GameTable(
                GameID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                GameType VARCHAR(30),
                State VARCHAR(20),
                Player1ID INTEGER,
                Player2ID INTEGER,
                StartingPlayer INTEGER,
                GameStartTime TIMESTAMP,
                GameEndTime TIMESTAMP,
                WinnerID INTEGER,
                "7x7BonusWinner" INTEGER,
                EmptySpacesP1 INTEGER,
                EmptySpacesP2 INTEGER,

                FOREIGN KEY(Player1ID) REFERENCES PlayerTable(PlayerID),
                FOREIGN KEY(Player2ID) REFERENCES PlayerTable(PlayerID),
                FOREIGN KEY(WinnerID) REFERENCES PlayerTable(PlayerID),
                FOREIGN KEY("7x7BonusWinner") REFERENCES PlayerTable(PlayerID)
            )
            """);

            // TURN TABLE
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS TurnTable(
                TurnID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                GameID INTEGER,
                TurnStartTime TIMESTAMP,
                TurnEndTime TIMESTAMP,

                FOREIGN KEY(GameID) REFERENCES GameTable(GameID)
            )
            """);

            // MOVE TABLE
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS MoveTable(
                MoveID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                TurnID INTEGER,
                PatchID INTEGER,
                MoveStartTime TIMESTAMP,
                MoveEndTime TIMESTAMP,
                SpecialPatchesCollected INTEGER,
                SpacesMoved INTEGER,
                Position VARCHAR(50),
                RotationDegrees INTEGER,
                ButtonsP1 INTEGER,
                ButtonsP2 INTEGER,

                FOREIGN KEY(TurnID) REFERENCES TurnTable(TurnID),
                FOREIGN KEY(PatchID) REFERENCES PatchTable(PatchID)
            )
            """);

            System.out.println("Tables created or already exist");

        } catch (Exception e) {
            // Foutafhandeling: print stacktrace als er iets misgaat
            e.printStackTrace();
        }
    }
}
