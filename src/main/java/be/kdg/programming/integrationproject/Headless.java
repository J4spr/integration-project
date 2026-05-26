package be.kdg.programming.integrationproject;

import be.kdg.programming.integrationproject.model.*;
import be.kdg.programming.integrationproject.model.Enums.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Headless {
    public static void main(String[] args) {
        int totalGames = 10;
        String outputSqlFile = "src/main/resources/mockupData.sql";
        Random random = new Random();

        int nextGameId = 101;
        int nextTurnId = 501;

        System.out.println("=============================================");
        System.out.println("   LAUNCHING PATCHWORK HEADLESS ENGINE       ");
        System.out.println("=============================================");

        // Time formattings matching PostgreSQL expectations
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        try (FileWriter sqlWriter = new FileWriter(outputSqlFile, false)) {
            sqlWriter.append("\n\n-- =============================================\n");
            sqlWriter.append("-- GENERATED HEADLESS GAME SIMULATION INSERTS   \n");
            sqlWriter.append("-- DIFFICULTY SPECIFICATION: MEDIUM             \n");
            sqlWriter.append("-- =============================================\n\n");

            String p1Subquery = "(SELECT \"PlayerID\" FROM \"PlayerTable\" WHERE \"Username\" = 'PatchMaster')";
            String p2Subquery = "(SELECT \"PlayerID\" FROM \"PlayerTable\" WHERE \"Username\" = 'ButtonKing')";

            for (int gameCount = 1; gameCount <= totalGames; gameCount++) {
                int currentGameId = nextGameId++;

                // FIX: Initialize to a safe, steady baseline time for each distinct game match
                // Starts at 10:00 AM on the current date, guaranteeing we never hit midnight rolovers
                LocalDateTime gameClock = LocalDateTime.now().withHour(10).withMinute(0).withSecond(0);

                HumanPlayer simPlayer1 = new HumanPlayer("PatchMaster");
                CpuPlayer simPlayer2 = new CpuPlayer(Difficulty.MEDIUM);
                simPlayer1.setColor(TokenColor.GREEN);
                simPlayer2.setColor(TokenColor.YELLOW);

                CpuPlayer aiControllerForP1 = new CpuPlayer(Difficulty.MEDIUM);
                int startingPlayer = random.nextBoolean() ? 1 : 2;

                Game game = new Game(simPlayer1, simPlayer2, startingPlayer);
                game.setGameId(currentGameId);

                String startTimeStr = gameClock.format(timeFormatter);
                sqlWriter.append("-- >>> STARTING RELATIONAL MATCH #" + currentGameId + " <<<\n");

                sqlWriter.append(String.format(
                        "INSERT INTO \"GameTable\" (\"GameID\", \"GameType\", \"State\", \"Player1ID\", \"Player2ID\", \"StartingPlayer\", \"GameStartTime\", \"WinnerID\", \"7x7BonusWinner\", \"EmptySpacesP1\", \"EmptySpacesP2\", \"ButtonsP1\", \"ButtonsP2\", \"IncomeP1\", \"IncomeP2\", \"ColorP1\", \"ColorP2\") " +
                                "VALUES (%d, 'Standard', 'Ongoing', %s, %s, %d, '%s', NULL, NULL, NULL, NULL, 5, 5, 0, 0, '%s', '%s');\n",
                        currentGameId, p1Subquery, p2Subquery, startingPlayer, startTimeStr, simPlayer1.getColor().name(), simPlayer2.getColor().name()));

                while (game.getStatus() == GameStatus.ACTIVE) {
                    Player activePlayer = game.getCurrentPlayer();
                    int currentTurnId = nextTurnId++;

                    int leatherQueueBefore = game.getLeatherPatchQueue(activePlayer).size();
                    int patchesOwnedBefore = activePlayer.getQuiltBoard().getPlacements().size();

                    // Progressively add time using Java's time APIs instead of manual overflows
                    gameClock = gameClock.plusMinutes(1);
                    String turnStart = gameClock.withSecond(1).format(timeFormatter);
                    String turnEnd = gameClock.withSecond(45).format(timeFormatter);

                    sqlWriter.append(String.format(
                            "INSERT INTO \"TurnTable\" (\"TurnID\", \"GameID\", \"TurnStartTime\", \"TurnEndTime\") VALUES (%d, %d, '%s', '%s');\n",
                            currentTurnId, currentGameId, turnStart, turnEnd));

                    int posBefore = activePlayer.getPosition();
                    String activePlayerSubquery = (activePlayer == simPlayer1) ? p1Subquery : p2Subquery;

                    if (activePlayer == simPlayer1) {
                        aiControllerForP1.decideTurn(game);
                    } else {
                        ((CpuPlayer) activePlayer).decideTurn(game);
                    }

                    autoPlacePendingLeatherPatches(game, simPlayer1);
                    autoPlacePendingLeatherPatches(game, simPlayer2);

                    int spacesMoved = activePlayer.getPosition() - posBefore;
                    int patchCollected = (game.getLeatherPatchQueue(activePlayer).size() > leatherQueueBefore) ? 1 : 0;

                    int patchesOwnedAfter = activePlayer.getQuiltBoard().getPlacements().size();
                    String patchIdValue = "NULL";

                    if (patchesOwnedAfter > patchesOwnedBefore) {
                        patchIdValue = String.valueOf(random.nextInt(32) + 1);
                    }

                    int rotationDegrees = (random.nextBoolean()) ? 0 : 90;

                    // FIX: This fully maps the unified 'activePlayerSubquery' string into your SQL placeholder (%s)
                    sqlWriter.append(String.format(
                            "INSERT INTO \"MoveTable\" (\"TurnID\", \"PlayerID\", \"PatchID\", \"MoveStartTime\", \"MoveEndTime\", \"SpecialPatchesCollected\", \"SpacesMoved\", \"Position\", \"RotationDegrees\", \"ButtonsP1\", \"ButtonsP2\") " +
                                    "VALUES (%d, %s, %s, '%s', '%s', %d, %d, %d, %d, %d, %d);\n",
                            currentTurnId, activePlayerSubquery, patchIdValue, turnStart, turnEnd, patchCollected, spacesMoved, activePlayer.getPosition(), rotationDegrees, simPlayer1.getTotalButtons(), simPlayer2.getTotalButtons()));

                    game.updateCurrentPlayer();
                }

                String finalWinnerSubquery = (game.getWinner() == simPlayer1) ? p1Subquery : p2Subquery;
                if (game.getWinner() == null) finalWinnerSubquery = p1Subquery;

                String bonusWinnerStr = "NULL";
                if (game.getSpecialTileOwner() == simPlayer1) bonusWinnerStr = p1Subquery;
                if (game.getSpecialTileOwner() == simPlayer2) bonusWinnerStr = p2Subquery;

                // Add game buffer concluding time cleanly
                gameClock = gameClock.plusMinutes(5);
                String endTimeStr = gameClock.withSecond(0).format(timeFormatter);

                sqlWriter.append(String.format(
                        "UPDATE \"GameTable\" SET \"State\" = 'Finished', \"GameEndTime\" = '%s', \"WinnerID\" = %s, \"7x7BonusWinner\" = %s, " +
                                "\"EmptySpacesP1\" = %d, \"EmptySpacesP2\" = %d, \"ButtonsP1\" = %d, \"ButtonsP2\" = %d, \"IncomeP1\" = %d, \"IncomeP2\" = %d " +
                                "WHERE \"GameID\" = %d;\n\n",
                        endTimeStr, finalWinnerSubquery, bonusWinnerStr,
                        simPlayer1.getQuiltBoard().countEmptySpaces(), simPlayer2.getQuiltBoard().countEmptySpaces(),
                        simPlayer1.getTotalButtons(), simPlayer2.getTotalButtons(),
                        simPlayer1.getTotalButtonIncome(), simPlayer2.getTotalButtonIncome(),
                        currentGameId));
            }
            System.out.println("SUCCESS: Relational SQL inserts appended successfully!");
        } catch (IOException e) {
            System.err.println("CRITICAL FAILURE: " + e.getMessage());
        }
    }

    private static void autoPlacePendingLeatherPatches(Game game, Player player) {
        int boardSize = Quiltboard.getSize();
        while (!game.getLeatherPatchQueue(player).isEmpty()) {
            boolean patchPlaced = false;
            for (int r = 0; r < boardSize && !patchPlaced; r++) {
                for (int c = 0; c < boardSize && !patchPlaced; c++) {
                    if (game.placeLeatherPatch(player, r, c)) {
                        patchPlaced = true;
                    }
                }
            }
        }
    }
}