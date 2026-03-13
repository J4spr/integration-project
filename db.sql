

CREATE TABLE "PlayerTable" (
                               "PlayerID" int PRIMARY KEY,
                               "Username" varchar NOT NULL,
                               "Email" varchar NOT NULL
);

CREATE TABLE "GameTable" (
                             "GameID" int PRIMARY KEY,
                             "GameType" varchar NOT NULL,
                             "State" varchar NOT NULL,
                             "Player1ID" int,
                             "Player2ID" int,
                             "StartingPlayer" int NOT NULL,
                             "GameStartTime" time NOT NULL,
                             "GameEndTime" time,
                             "WinnerID" int,
                             "7x7BonusWinner" int,
                             "EmptySpacesP1" int,
                             "EmptySpacesP2" int
);

CREATE TABLE "TurnTable" (
                             "TurnID" int PRIMARY KEY,
                             "GameID" int,
                             "TurnStartTime" time NOT NULL,
                             "TurnEndTime" time
);

CREATE TABLE "MoveTable" (
                             "MoveID" int PRIMARY KEY,
                             "TurnID" int,
                             "PatchID" int,
                             "MoveStartTime" time NOT NULL,
                             "MoveEndTime" time,
                             "SpecialPatchesCollected" int NOT NULL,
                             "SpacesMoved" int,
                             "Position" int,
                             "RotationDegrees" int,
                             "ButtonsP1" int,
                             "ButtonsP2" int
);

CREATE TABLE "PatchTable" (
                              "PatchID" int PRIMARY KEY,
                              "ButtonCost" int NOT NULL,
                              "TimeCost" int NOT NULL,
                              "ButtonIncome" int NOT NULL
);

ALTER TABLE "GameTable" ADD FOREIGN KEY ("Player1ID") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "GameTable" ADD FOREIGN KEY ("Player2ID") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "GameTable" ADD FOREIGN KEY ("WinnerID") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "GameTable" ADD FOREIGN KEY ("7x7BonusWinner") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "TurnTable" ADD FOREIGN KEY ("GameID") REFERENCES "GameTable" ("GameID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "MoveTable" ADD FOREIGN KEY ("TurnID") REFERENCES "TurnTable" ("TurnID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "MoveTable" ADD FOREIGN KEY ("PatchID") REFERENCES "PatchTable" ("PatchID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "PlayerTable" ADD CONSTRAINT un_username UNIQUE ("Username");
ALTER TABLE "PlayerTable" ADD CONSTRAINT un_email UNIQUE ("Email");
ALTER TABLE "GameTable" ADD CONSTRAINT ch_gametable CHECK ( "Player1ID" <> "Player2ID" );
ALTER TABLE "GameTable" ADD CONSTRAINT ch_start_endTime CHECK ( "GameEndTime" >= "GameStartTime" );
ALTER TABLE "GameTable" ADD CONSTRAINT ch_start_endTime CHECK ( "GameEndTime" >= (SELECT to_date(now())))

