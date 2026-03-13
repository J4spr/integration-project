CREATE TABLE PlayerTable (
                             PlayerID INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                             Username VARCHAR(50) NOT NULL,
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