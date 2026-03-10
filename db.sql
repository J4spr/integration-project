set search_path = "public";
CREATE TABLE "Player" (
                          "PlayerID" int PRIMARY KEY,
                          "Username" varchar,
                          "Email" varchar
);

CREATE TABLE "GameSession" (
                               "GameSessionID" int PRIMARY KEY,
                               "StartTime" date,
                               "EndTime" time,
                               "Duration" int,
                               "WinnerID" int
);

CREATE TABLE "GamePlayer" (
                              "GameSessionID" int,
                              "PlayerID" int,
                              "FinalScore" int,
                              "FinalButtons" int,
                              "EmptySpaces" int,
                              PRIMARY KEY ("GameSessionID", "PlayerID")
);

CREATE TABLE "Patch" (
                         "PatchID" int PRIMARY KEY,
                         "ButtonCost" int,
                         "TimeCost" int
);

CREATE TABLE "Move" (
                        "MoveID" int PRIMARY KEY,
                        "GameSessionID" int,
                        "PlayerID" int,
                        "PatchID" int,
                        "TurnNumber" int,
                        "ActionType" varchar,
                        "SpacesMoved" int,
                        "ButtonsReceived" int,
                        "PositionX" int,
                        "PositionY" int,
                        "Rotation" int,
                        "MoveDuration" int,
                        "Timestamp" time
);

ALTER TABLE "GameSession" ADD FOREIGN KEY ("WinnerID") REFERENCES "Player" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "GamePlayer" ADD FOREIGN KEY ("GameSessionID") REFERENCES "GameSession" ("GameSessionID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "GamePlayer" ADD FOREIGN KEY ("PlayerID") REFERENCES "Player" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "Move" ADD FOREIGN KEY ("GameSessionID") REFERENCES "GameSession" ("GameSessionID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "Move" ADD FOREIGN KEY ("PlayerID") REFERENCES "Player" ("PlayerID") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "Move" ADD FOREIGN KEY ("PatchID") REFERENCES "Patch" ("PatchID") DEFERRABLE INITIALLY IMMEDIATE;
