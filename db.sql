DROP TABLE IF EXISTS PlayerTable;
DROP TABLE IF EXISTS PatchTable;
DROP TABLE IF EXISTS GameTable;
DROP TABLE IF EXISTS TurnTable;
DROP TABLE IF EXISTS MoveTable;


CREATE TABLE PlayerTable (
                             PlayerID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             Username VARCHAR(50) NOT NULL CHECK (Username NOT LIKE '% %'),
                             Email VARCHAR(100) NOT NULL
);

CREATE TABLE PatchTable (
                            PatchID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                            ButtonCost INTEGER NOT NULL,
                            TimeCost INTEGER NOT NULL,
                            ButtonIncome INTEGER NOT NULL
);

CREATE TABLE GameTable (
                           GameID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           GameType VARCHAR(30) NOT NULL,
                           State VARCHAR(20) NOT NULL,
                           Player1ID INTEGER,
                           Player2ID INTEGER,
                           StartingPlayer INTEGER,
                           GameStartTime TIMESTAMP,
                           GameEndTime TIMESTAMP,
                           WinnerID INTEGER,
                           "7x7BonusWinner" INTEGER,
                           EmptySpacesP1 INTEGER,
                           EmptySpacesP2 INTEGER,

                           FOREIGN KEY (Player1ID) REFERENCES PlayerTable(PlayerID),
                           FOREIGN KEY (Player2ID) REFERENCES PlayerTable(PlayerID),
                           FOREIGN KEY (WinnerID) REFERENCES PlayerTable(PlayerID),
                           FOREIGN KEY ("7x7BonusWinner") REFERENCES PlayerTable(PlayerID)
);

CREATE TABLE TurnTable (
                           TurnID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           GameID INTEGER NOT NULL,
                           TurnStartTime TIMESTAMP,
                           TurnEndTime TIMESTAMP,

                           FOREIGN KEY (GameID) REFERENCES GameTable(GameID)
);

CREATE TABLE MoveTable (
                           MoveID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                           TurnID INTEGER NOT NULL,
                           PatchID INTEGER,
                           MoveStartTime TIMESTAMP,
                           MoveEndTime TIMESTAMP,
                           SpecialPatchesCollected INTEGER,
                           SpacesMoved INTEGER,
                           Position VARCHAR(50),
                           RotationDegrees INTEGER,
                           ButtonsP1 INTEGER,
                           ButtonsP2 INTEGER,

                           FOREIGN KEY (TurnID) REFERENCES TurnTable(TurnID),
                           FOREIGN KEY (PatchID) REFERENCES PatchTable(PatchID)
);

ALTER TABLE "PlayerTable"
    ADD CONSTRAINT unique_username UNIQUE ("Username"),
    ADD CONSTRAINT unique_email UNIQUE ("Email"),
    ADD CONSTRAINT check_email_format CHECK ("Email" LIKE '%@%');

ALTER TABLE "GameTable"
    ADD CONSTRAINT check_different_players CHECK ("Player1ID" <> "Player2ID"),
    ADD CONSTRAINT check_game_time CHECK ("GameEndTime" >= "GameStartTime");

ALTER TABLE "TurnTable"
    ADD CONSTRAINT check_turn_time CHECK ("TurnEndTime" >= "TurnStartTime");

ALTER TABLE "MoveTable"
    ADD CONSTRAINT check_move_time CHECK ("MoveEndTime" >= "MoveStartTime"),
    ADD CONSTRAINT check_rotation_degrees CHECK ("RotationDegrees" IN (0, 90, 180, 270));

ALTER TABLE "PatchTable"
    ADD CONSTRAINT check_patch_costs CHECK ("ButtonCost" >= 0 AND "TimeCost" >= 0),
    ADD CONSTRAINT check_patch_income CHECK ("ButtonIncome" >= 0);

