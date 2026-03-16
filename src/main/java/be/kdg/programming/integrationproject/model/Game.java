package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import java.time.LocalDate;
import java.util.Queue;
import java.util.LinkedList;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

public class Game {
    private HumanPlayer player1;
    private Player player2;
    private Player currentPlayer;
    private Player specialTileOwner;
    private Player winner;
    private GameStatus status;
    private LocalDate startDate;
    private int startPlayer;
    private Timeboard timeboard;
    private PatchStack patchStack;
    private Queue<Patch> leatherPatchQueue = new LinkedList<>();

    public Game(HumanPlayer player1, Player player2, int startPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.startPlayer = startPlayer;
        this.currentPlayer = startPlayer == 1 ? player1 : player2;
        this.status = GameStatus.ACTIVE;
        this.startDate = LocalDate.now();
        this.timeboard = new Timeboard();
        this.patchStack = new PatchStack();
    }

    //game getters & setter
    public HumanPlayer getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getSpecialTileOwner() {
        return specialTileOwner;
    }

    public Player getWinner() {
        return winner;
    }

    public void setSpecialTileOwner(Player specialTileOwner) {
        this.specialTileOwner = specialTileOwner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public int getStartPlayer() {
        return startPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Queue<Patch> getLeatherPatchQueue() {
        return leatherPatchQueue;
    }

    public Timeboard getTimeboard() {
        return timeboard;
    }

    public PatchStack getPatchStack() {
        return patchStack;
    }

    //player buys patch, buttons get taken from player and player's button income increases
    public Patch buyPatch(int patchID) {
        Patch patch = patchStack.removePatch(patchID);
        if (patch == null) return null;
        if (currentPlayer.getTotalButtons() < patch.getButtonCost()) return null;
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() - patch.getButtonCost());
        currentPlayer.setTotalButtonIncome(currentPlayer.getTotalButtonIncome() + patch.getButtonIncome());
        return patch;
    }

    //player gets buttons when they pass a buttonPosition
    public void collectButtonIncome(int buttonPositionsPassed) {
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() + (currentPlayer.getTotalButtonIncome() * buttonPositionsPassed));
    }

    //switches current player to player that's the furthest behind
    public void updateCurrentPlayer() {
        if (player1.getPosition() <= player2.getPosition()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    //player can place a patch on his quiltboard and chooses the rotation of the patch
    public boolean placePatch(Patch patch, int row, int col, PatchRotation rotation) {
        if (patch == null) return false;
        patch.setRotation(rotation);
        return currentPlayer.getQuiltBoard().placePatch(patch, row, col);
    }

    public void moveToken(int timeCost) {
        int oldPosition = currentPlayer.getPosition();
        int newPosition = timeboard.updatePosition(oldPosition, timeCost);
        currentPlayer.updatePosition(newPosition - oldPosition);
        int buttonPositionsPassed = timeboard.countButtonPositionsPassed(oldPosition, newPosition);
        collectButtonIncome(buttonPositionsPassed);
        int leatherPatchesPassed = timeboard.countLeatherPatchesPassed(oldPosition, newPosition);
        for (int i = 0; i < leatherPatchesPassed; i++) {
            leatherPatchQueue.add(Patch.createLeatherPatch(i));
        }
    }

    public void pass() {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        int newPosition = otherPlayer.getPosition() + 1;
        int steps = newPosition - currentPlayer.getPosition();
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() + steps);
        moveToken(steps);
    }

    private int calculateScore(Player player) {
        int score = player.getTotalButtons();
        score -= player.getQuiltBoard().countEmptySpaces() * 2;
        if (specialTileOwner == player) score += 7;
        return score;
    }

    public boolean checkGameEnd() {
        if (player1.getPosition() >= timeboard.getSize() - 1 && player2.getPosition() >= timeboard.getSize() - 1) {
            status = GameStatus.FINISHED;
            int scorePlayer1 = calculateScore(player1);
            int scorePlayer2 = calculateScore(player2);
            winner = scorePlayer1 >= scorePlayer2 ? player1 : player2;
            return true;
        }
        return false;
    }
}