package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private HumanPlayer player1;
    private Player player2;
    private GameStatus status;
    private LocalDate startDate;
    private int startPlayer;
    private Timeboard timeboard;
    private QuiltBoard quiltBoard;
    private PatchStack patchStack;
    private List<Turn> turns;

    public Game(HumanPlayer player1, Player player2, int startPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.startPlayer = startPlayer;
        this.status = GameStatus.ACTIVE;
        this.startDate = LocalDate.now();
        this.timeboard = new Timeboard();
        this.quiltBoard = new QuiltBoard();
        this.patchStack = new PatchStack();
        this.turns = new ArrayList<>();
    }

    public HumanPlayer getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
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
    
    public Timeboard getTimeboard() {
        return timeboard;
    }

    public QuiltBoard getQuiltBoard() {
        return quiltBoard;
    }

    public PatchStack getPatchStack() {
        return patchStack;
    }

    public List<Turn> getTurns() {
        return turns;
    }
}
    