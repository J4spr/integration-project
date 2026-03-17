-- 1. PlayerTable (20 Records)
INSERT INTO "PlayerTable" ("Username", "Email") VALUES
                                                    ('PatchMaster', 'player1@example.com'), ('ButtonKing', 'player2@example.com'),
                                                    ('QuiltQueen', 'player3@example.com'), ('StitchWiz', 'player4@example.com'),
                                                    ('ThimbleHero', 'player5@example.com'), ('NeedlePoint', 'player6@example.com'),
                                                    ('FabricFan', 'player7@example.com'), ('PatternPro', 'player8@example.com'),
                                                    ('LoomLord', 'player9@example.com'), ('WoolyWest', 'player10@example.com'),
                                                    ('CottonCandy', 'player11@example.com'), ('SilkRoad', 'player12@example.com'),
                                                    ('DenimDude', 'player13@example.com'), ('VelvetVibe', 'player14@example.com'),
                                                    ('SatinStar', 'player15@example.com'), ('LinenLife', 'player16@example.com'),
                                                    ('ThreadHead', 'player17@example.com'), ('BobbinBoy', 'player18@example.com'),
                                                    ('SeamDream', 'player19@example.com'), ('TailorSwift', 'player20@example.com');

-- 2. PatchTable (20 Records)
INSERT INTO "PatchTable" ("ButtonCost", "TimeCost", "ButtonIncome") VALUES
                                                                        (2, 3, 1), (5, 4, 2), (10, 5, 3), (0, 2, 0), (3, 1, 1),
                                                                        (1, 2, 0), (7, 6, 2), (4, 3, 1), (8, 4, 3), (6, 5, 2),
                                                                        (2, 1, 0), (1, 3, 1), (9, 2, 2), (4, 4, 1), (0, 5, 1),
                                                                        (11, 6, 3), (3, 2, 1), (5, 3, 2), (2, 4, 0), (7, 3, 2);

-- 3. GameTable (20 Records)
-- Assuming generated IDs start at 1
INSERT INTO "GameTable" ("GameType", "State", "Player1ID", "Player2ID", "StartingPlayer", "GameStartTime", "GameEndTime", "WinnerID", "7x7BonusWinner", "EmptySpacesP1", "EmptySpacesP2") VALUES
                                                                                                                                                                                              ('Standard', 'Finished', 1, 2, 1, '08:00:00', '08:30:00', 1, 1, 4, 10),
                                                                                                                                                                                              ('Standard', 'Finished', 3, 4, 4, '09:00:00', '09:45:00', 4, NULL, 12, 5),
                                                                                                                                                                                              ('Ranked', 'Finished', 5, 6, 5, '10:00:00', '10:40:00', 5, 5, 2, 8),
                                                                                                                                                                                              ('Ranked', 'Finished', 7, 8, 8, '11:00:00', '11:35:00', 8, 7, 9, 3),
                                                                                                                                                                                              ('Casual', 'Finished', 9, 10, 9, '12:00:00', '12:50:00', 10, NULL, 15, 12),
                                                                                                                                                                                              ('Standard', 'Finished', 11, 12, 11, '13:00:00', '13:25:00', 11, 11, 0, 6),
                                                                                                                                                                                              ('Standard', 'Finished', 13, 14, 14, '14:00:00', '14:55:00', 14, 14, 4, 4),
                                                                                                                                                                                              ('Ranked', 'Finished', 15, 16, 15, '15:00:00', '15:40:00', 15, NULL, 7, 11),
                                                                                                                                                                                              ('Ranked', 'Finished', 17, 18, 18, '16:00:00', '16:45:00', 17, 17, 5, 5),
                                                                                                                                                                                              ('Casual', 'Finished', 19, 20, 19, '17:00:00', '17:30:00', 20, 20, 12, 3),
                                                                                                                                                                                              ('Standard', 'Ongoing', 1, 3, 1, '18:00:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Standard', 'Ongoing', 2, 4, 4, '18:15:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Ranked', 'Ongoing', 5, 7, 5, '18:30:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Ranked', 'Ongoing', 6, 8, 8, '18:45:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Casual', 'Ongoing', 9, 11, 11, '19:00:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Standard', 'Ongoing', 10, 12, 10, '19:15:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Standard', 'Ongoing', 13, 15, 15, '19:30:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Ranked', 'Ongoing', 14, 16, 14, '19:45:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Ranked', 'Ongoing', 17, 19, 19, '20:00:00', NULL, NULL, NULL, NULL, NULL),
                                                                                                                                                                                              ('Casual', 'Ongoing', 18, 20, 18, '20:15:00', NULL, NULL, NULL, NULL, NULL);

-- 4. TurnTable (20 Records)
INSERT INTO "TurnTable" ("GameID", "TurnStartTime", "TurnEndTime") VALUES
                                                                       (1, '08:01:00', '08:02:00'), (1, '08:02:05', '08:03:30'),
                                                                       (2, '09:01:00', '09:02:15'), (2, '09:02:20', '09:04:00'),
                                                                       (3, '10:01:00', '10:03:00'), (3, '10:03:10', '10:05:00'),
                                                                       (4, '11:01:00', '11:02:45'), (4, '11:03:00', '11:04:30'),
                                                                       (5, '12:01:00', '12:05:00'), (5, '12:05:10', '12:10:00'),
                                                                       (6, '13:01:00', '13:02:00'), (7, '14:01:00', '14:03:00'),
                                                                       (8, '15:01:00', '15:02:30'), (9, '16:01:00', '16:02:15'),
                                                                       (10, '17:01:00', '17:03:45'), (11, '18:01:00', '18:05:00'),
                                                                       (12, '18:16:00', '18:20:00'), (13, '18:31:00', '18:35:00'),
                                                                       (14, '18:46:00', '18:50:00'), (15, '19:01:00', '19:05:00');

-- 5. MoveTable (20 Records)
INSERT INTO "MoveTable" ("TurnID", "PatchID", "MoveStartTime", "MoveEndTime", "SpecialPatchesCollected", "SpacesMoved", "Position", "RotationDegrees", "ButtonsP1", "ButtonsP2") VALUES
                                                                                                                                                                                     (1, 1, '08:01:05', '08:01:45', 0, 3, 1, 0, 5, 5),
                                                                                                                                                                                     (2, 2, '08:02:10', '08:03:00', 1, 2, 5, 90, 3, 5),
                                                                                                                                                                                     (3, 3, '09:01:10', '09:02:00', 0, 4, 10, 180, 10, 10),
                                                                                                                                                                                     (4, 4, '09:02:30', '09:03:45', 0, 1, 12, 270, 10, 11),
                                                                                                                                                                                     (5, 5, '10:01:15', '10:02:30', 0, 5, 15, 0, 8, 8),
                                                                                                                                                                                     (6, 6, '10:03:20', '10:04:30', 0, 2, 18, 90, 8, 9),
                                                                                                                                                                                     (7, 7, '11:01:10', '11:02:20', 1, 3, 22, 180, 4, 6),
                                                                                                                                                                                     (8, 8, '11:03:15', '11:04:10', 0, 4, 28, 270, 6, 6),
                                                                                                                                                                                     (9, 9, '12:01:30', '12:04:30', 0, 2, 30, 0, 15, 12),
                                                                                                                                                                                     (10, 10, '12:05:20', '12:09:00', 1, 1, 35, 90, 12, 14),
                                                                                                                                                                                     (11, 11, '13:01:10', '13:01:50', 0, 3, 5, 180, 5, 5),
                                                                                                                                                                                     (12, 12, '14:01:15', '14:02:45', 0, 2, 8, 270, 7, 7),
                                                                                                                                                                                     (13, 13, '15:01:10', '15:02:10', 0, 1, 12, 0, 10, 4),
                                                                                                                                                                                     (14, 14, '16:01:15', '16:02:00', 0, 5, 18, 90, 8, 9),
                                                                                                                                                                                     (15, 15, '17:01:20', '17:03:00', 1, 3, 20, 180, 2, 10),
                                                                                                                                                                                     (16, 16, '18:01:30', '18:04:30', 0, 2, 25, 270, 12, 12),
                                                                                                                                                                                     (17, 17, '18:16:30', '18:19:30', 0, 4, 10, 0, 6, 8),
                                                                                                                                                                                     (18, 18, '18:31:30', '18:34:30', 0, 1, 15, 90, 9, 9),
                                                                                                                                                                                     (19, 19, '18:46:30', '18:49:30', 0, 3, 22, 180, 11, 5),
                                                                                                                                                                                     (20, 20, '19:01:30', '19:04:30', 1, 2, 28, 270, 4, 10);