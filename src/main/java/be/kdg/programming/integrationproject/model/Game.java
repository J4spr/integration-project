package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.dao.PlayerDao;
import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
    private int gameId;
    private HumanPlayer player1;
    //player2 is declared as Player to support both HumanPlayer and
    // CpuPlayer via polymorphism
    private Player player2;
    //the player whose turn it currently is
    private Player currentPlayer;
    //the player who first completed a 7x7 area on their quiltboard,
    // null if no one has yet
    private Player specialTileOwner;
    private Player firstToFinish;
    private Player winner;
    private GameStatus status;
    //1 = player1 starts, 2 = player2 starts
    private int startPlayer;
    private Timeboard timeboard;
    private PatchStack patchStack;
    //counter to ensure each leather patch gets a unique ID
    private int leatherPatchCounter = 0;
    //queue of leather patches the current player still needs to place
    private Queue<Patch> leatherPatchQueue = new LinkedList<>();
    // db stuff
    private PlayerDao playerDao;
    private DbConnection conn;

    public Game(HumanPlayer player1, Player player2, int startPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.startPlayer = startPlayer;
        //set the starting player based on the chosen start player
        this.currentPlayer = startPlayer == 1 ? player1 : player2;
        this.status = GameStatus.ACTIVE;
        this.timeboard = new Timeboard();
        //use PatchStackBuilder to create and shuffle all patches
        this.patchStack = PatchStackBuilder.build();
        //each player starts with 5 buttons according to the rules
        player1.setTotalButtons(5);
        player2.setTotalButtons(5);
        // init db connection
        this.conn = new DbConnection();
        // initialise the playerDao to put new players in db
        this.playerDao = new PlayerDao(this.conn);
    }

    //getters & setters
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

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

    public Player getWinner() {
        return winner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
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

    //internal method, only used by buyAndPlacePatch and CPU logic
    //fetches the patch first without removing it, only removes after button check passes
    private Patch buyPatch(int patchID) {
        Patch patch = patchStack.getPatch(patchID); // fetch only, do not remove yet
        if (patch == null) return null;
        if (currentPlayer.getTotalButtons() < patch.getButtonCost()) return null;
        patchStack.removePatch(patchID); // only remove once player has enough buttons
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() - patch.getButtonCost());
        currentPlayer.setTotalButtonIncome(currentPlayer.getTotalButtonIncome() + patch.getButtonIncome());
        return patch;
    }

    //internal method, only used by buyAndPlacePatch and CPU logic
    //applies rotation and places patch on the current player's quiltboard
    private boolean placePatch(Patch patch, int row, int col, PatchRotation rotation) {
        if (patch == null) return false;
        patch.setRotation(rotation);
        return currentPlayer.getQuiltBoard().placePatch(patch, row, col);
    }

    //public method to be used by presenter - handles buying and placing a patch safely
    //checks all conditions before modifying any state to prevent inconsistent game state
    // public method to be used by presenter - handles buying and placing a patch safely
    // checks all conditions before modifying any state to prevent inconsistent game state
    public boolean buyAndPlacePatch(int patchID, int row, int col, PatchRotation rotation) {
        Patch patch = patchStack.getPatch(patchID);
        if (patch == null) return false;
        if (currentPlayer.getTotalButtons() < patch.getButtonCost()) return false;
        patch.setRotation(rotation);
        // check placement before removing patch from stack or deducting buttons
        if (!currentPlayer.getQuiltBoard().canPlacePatch(patch, row, col)) return false;
        patchStack.removePatch(patchID);
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() - patch.getButtonCost());
        currentPlayer.setTotalButtonIncome(currentPlayer.getTotalButtonIncome() + patch.getButtonIncome());
        currentPlayer.getQuiltBoard().placePatch(patch, row, col);
        moveToken(patch.getTimeCost());
        checkSpecialTile();    // check if the current player completed a 7x7 area
        checkGameEnd();        // check if both players have reached the end of the timeboard
        updateCurrentPlayer();
        return true;
    }

    //called when the current player's token passes over a button position on the timeboard
    //multiplied by buttonPositionsPassed in case the player passes multiple button positions in one move
    public void collectButtonIncome(int buttonPositionsPassed) {
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() + (currentPlayer.getTotalButtonIncome() * buttonPositionsPassed));
    }

    //the player furthest behind on the timeboard is always the current player
    //if both players are on the same position, player1 goes first
    public void updateCurrentPlayer() {
        if (player1.getPosition() <= player2.getPosition()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    //moves the current player's token forward by timeCost steps
    //collects button income for any button positions passed
    //adds leather patches to the queue for any leather patch positions passed
    public void moveToken(int timeCost) {
        int oldPosition = currentPlayer.getPosition();
        int newPosition = timeboard.updatePosition(oldPosition, timeCost);
        currentPlayer.updatePosition(newPosition - oldPosition);
        // track who reached the end first for tiebreaker
        if (newPosition >= timeboard.getSize() - 1 && firstToFinish == null) {
            firstToFinish = currentPlayer;
        }
        int buttonPositionsPassed = timeboard.countButtonPositionsPassed(oldPosition, newPosition);
        collectButtonIncome(buttonPositionsPassed);
        int leatherPatchesPassed = timeboard.countLeatherPatchesPassed(oldPosition, newPosition);
        //add a leather patch to the queue for each leather patch position passed
        for (int i = 0; i < leatherPatchesPassed; i++) {
            leatherPatchQueue.add(Patch.createLeatherPatch(leatherPatchCounter++));
        }
    }

    //checks if either player has completed a 7x7 area on their quiltboard
    //only the first player to do so receives the special tile
    public void checkSpecialTile() {
        if (specialTileOwner == null) {
            if (player1.getQuiltBoard().hasSevenBySeven()) {
                specialTileOwner = player1;
            } else if (player2.getQuiltBoard().hasSevenBySeven()) {
                specialTileOwner = player2;
            }
        }
    }

    //places the next leather patch from the queue on the current player's quiltboard
    //the player chooses the row and col via the view
    public boolean placeLeatherPatch(int row, int col) {
        Patch leatherPatch = leatherPatchQueue.poll();
        if (leatherPatch == null) return false;
        return currentPlayer.getQuiltBoard().placePatch(leatherPatch, row, col);
    }

    //the current player passes their turn by moving their token just past the other player
    //they receive buttons equal to the number of spaces moved
    //throws IllegalStateException if the current player is already ahead, which should never happen
    public void pass() {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        int newPosition = otherPlayer.getPosition() + 1;
        int steps = newPosition - currentPlayer.getPosition();
        if (steps <= 0) throw new IllegalStateException("Current player is already ahead of the other player");
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() + steps);
        moveToken(steps);
        checkSpecialTile();    // check if the current player completed a 7x7 area
        checkGameEnd();        // check if both players have reached the end of the timeboard
        updateCurrentPlayer();
    }

    //calculates the final score for a player
    //buttons - (empty spaces * 2) + 7 if the player owns the special tile
    public int calculateScore(Player player) {
        int score = player.getTotalButtons();
        score -= player.getQuiltBoard().countEmptySpaces() * 2;
        if (specialTileOwner == player) score += 7;
        return score;
    }

    //checks if both players have reached the end of the timeboard
    //if so, calculates scores and determines the winner
    public boolean checkGameEnd() {
        if (player1.getPosition() >= timeboard.getSize() - 1 && player2.getPosition() >= timeboard.getSize() - 1) {
            status = GameStatus.FINISHED;
            int scorePlayer1 = calculateScore(player1);
            int scorePlayer2 = calculateScore(player2);
            if (scorePlayer1 == scorePlayer2) {
                winner = firstToFinish; // tiebreaker: first to reach the end wins
            } else {
                winner = scorePlayer1 > scorePlayer2 ? player1 : player2;
            }
            return true;
        }
        return false;
    }

    private boolean checkIfPlayerExistsInDb(Player player)  {
        if (!(player instanceof HumanPlayer)) {
            return false;
        }

        try{
            this.playerDao.findByUsername(((HumanPlayer) player).getName());
        } catch (SQLException e) {
            System.err.printf("db returned this message: %s with exit code %d", e.getMessage(), e.getErrorCode());
        }
        return true;
    }
}