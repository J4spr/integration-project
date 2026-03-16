package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.GameStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private HumanPlayer player1;
    private Player player2;
    private Player currentPlayer;
    private Player specialTileOwner;
    private GameStatus status;
    private LocalDate startDate;
    private int startPlayer;
    private Timeboard timeboard;
    private PatchStack patchStack;
    private List<Turn> turns;

    public Game(HumanPlayer player1, Player player2, int startPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.startPlayer = startPlayer;
        this.currentPlayer = startPlayer == 1 ? player1 : player2;
        this.status = GameStatus.ACTIVE;
        this.startDate = LocalDate.now();
        this.timeboard = new Timeboard();
        this.patchStack = new PatchStack();
        this.turns = new ArrayList<>();
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

    public Timeboard getTimeboard() {
        return timeboard;
    }

    public PatchStack getPatchStack() {
        return patchStack;
    }

    public List<Turn> getTurns() {
        return turns;
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
}
    