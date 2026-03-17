DROP TABLE IF EXISTS "PlayerTable" CASCADE;
DROP TABLE IF EXISTS "MoveTable" CASCADE;
DROP TABLE IF EXISTS "TurnTable" CASCADE;
DROP TABLE IF EXISTS "MoveTable" CASCADE;
DROP TABLE IF EXISTS "PatchTable" CASCADE;
DROP TABLE IF EXISTS "GameTable" CASCADE;


CREATE TABLE "PlayerTable"
(

    "PlayerID" SERIAL PRIMARY KEY,

    "Username" varchar NOT NULL,

    "Email"    varchar NOT NULL

);


CREATE TABLE "GameTable"
(

    "GameID"         SERIAL PRIMARY KEY,

    "GameType"       varchar NOT NULL,

    "State"          varchar NOT NULL,

    "Player1ID"      int,

    "Player2ID"      int,

    "StartingPlayer" int     NOT NULL,

    "GameStartTime"  time    NOT NULL,

    "GameEndTime"    time,

    "WinnerID"       int,

    "7x7BonusWinner" int,

    "EmptySpacesP1"  int,

    "EmptySpacesP2"  int

);


CREATE TABLE "TurnTable"
(

    "TurnID"        SERIAL PRIMARY KEY,

    "GameID"        int,

    "TurnStartTime" time NOT NULL,

    "TurnEndTime"   time

);


CREATE TABLE "MoveTable"
(

    "MoveID"                  SERIAL PRIMARY KEY,

    "TurnID"                  int,

    "PatchID"                 int,

    "MoveStartTime"           time NOT NULL,

    "MoveEndTime"             time,

    "SpecialPatchesCollected" int  NOT NULL,

    "SpacesMoved"             int,

    "Position"                int,

    "RotationDegrees"         int,

    "ButtonsP1"               int,

    "ButtonsP2"               int

);


CREATE TABLE "PatchTable"
(

    "PatchID"      SERIAL PRIMARY KEY,

    "ButtonCost"   int NOT NULL,

    "TimeCost"     int NOT NULL,

    "ButtonIncome" int NOT NULL

);


ALTER TABLE "GameTable"
    ADD FOREIGN KEY ("Player1ID") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;


ALTER TABLE "GameTable"
    ADD FOREIGN KEY ("Player2ID") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;


ALTER TABLE "GameTable"
    ADD FOREIGN KEY ("WinnerID") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;


ALTER TABLE "GameTable"
    ADD FOREIGN KEY ("7x7BonusWinner") REFERENCES "PlayerTable" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;


ALTER TABLE "TurnTable"
    ADD FOREIGN KEY ("GameID") REFERENCES "GameTable" ("GameID") DEFERRABLE INITIALLY IMMEDIATE;


ALTER TABLE "MoveTable"
    ADD FOREIGN KEY ("TurnID") REFERENCES "TurnTable" ("TurnID") DEFERRABLE INITIALLY IMMEDIATE;


ALTER TABLE "MoveTable"
    ADD FOREIGN KEY ("PatchID") REFERENCES "PatchTable" ("PatchID") DEFERRABLE INITIALLY IMMEDIATE;

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
