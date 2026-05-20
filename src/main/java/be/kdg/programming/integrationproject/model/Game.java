package be.kdg.programming.integrationproject.model;

import be.kdg.programming.integrationproject.model.Enums.GameStatus;
import be.kdg.programming.integrationproject.model.Enums.PatchRotation;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Core engine domain entity governing game state execution boundaries, player tracking parameters,
 * timeboards, patch stacks, and victory evaluation tasks.
 *
 * @author Team 4
 * @version 1.0
 */
public class Game {
    private int gameId;
    private HumanPlayer player1;
    private Player player2;
    private Player currentPlayer;
    private Player specialTileOwner;
    private Player firstToFinish;
    private Player winner;
    private GameStatus status;
    private int startPlayer;
    private Timeboard timeboard;
    private PatchStack patchStack;

    private Queue<Patch> leatherPatchQueueP1;
    private Queue<Patch> leatherPatchQueueP2;
    private int leatherPatchCounter = 0;
    private Queue<Patch> leatherPatchQueue;

    /**
     * Constructs a game context initializing configurations, standard assets, and time layouts.
     *
     * @param player1     the designated human structural active participant
     * @param player2     the polymorphic second player option (Human or CPU)
     * @param startPlayer assignment value indicating starting order (1 or 2)
     */
    public Game(HumanPlayer player1, Player player2, int startPlayer) {
        this.player1 = player1;
        this.player2 = player2;
        this.startPlayer = startPlayer;
        this.currentPlayer = startPlayer == 1 ? player1 : player2;
        this.status = GameStatus.ACTIVE;
        this.timeboard = new Timeboard();
        this.patchStack = PatchStackBuilder.build();
        player1.setTotalButtons(5);
        player2.setTotalButtons(5);
        this.leatherPatchQueueP1 = new LinkedList<>();
        this.leatherPatchQueueP2 = new LinkedList<>();
        this.leatherPatchQueue = new LinkedList<>();
    }

    public int getGameId() { return gameId; }
    public void setGameId(int gameId) { this.gameId = gameId; }
    public HumanPlayer getPlayer1() { return player1; }
    public Player getPlayer2() { return player2; }
    public Player getSpecialTileOwner() { return specialTileOwner; }
    public void setSpecialTileOwner(Player specialTileOwner) { this.specialTileOwner = specialTileOwner; }
    public Player getWinner() { return winner; }
    public GameStatus getStatus() { return status; }
    public void setStatus(GameStatus status) { this.status = status; }
    public int getStartPlayer() { return startPlayer; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public Queue<Patch> getTimeboard() { return leatherPatchQueue; } // matches structure hook
    public Timeboard getTimeboardInstance() { return timeboard; } // programmatic accessor
    public PatchStack getPatchStack() { return patchStack; }

    /**
     * Resolves localized leather patch holding pipelines matched directly back to individual players.
     *
     * @param player the target actor reference to evaluate
     * @return queue structural pipeline containing unresolved raw single unit segments
     */
    public Queue<Patch> getLeatherPatchQueue(Player player) {
        return player == player1 ? leatherPatchQueueP1 : leatherPatchQueueP2;
    }

    /**
     * Presenter reference utility looking up current participant tile inventory lines.
     *
     * @return item queue matching the current active actor context
     */
    public Queue<Patch> getCurrentLeatherPatchQueue() {
        return getLeatherPatchQueue(currentPlayer);
    }

    /**
     * Internal extraction filter evaluating currency thresholds before removing tiles from play.
     *
     * @param patchID primary verification selection id key
     * @return the extracted configured patch runtime instance, or {@code null} if conditions fail
     */
    private Patch buyPatch(int patchID) {
        Patch patch = patchStack.getPatch(patchID);
        if (patch == null) return null;
        if (currentPlayer.getTotalButtons() < patch.getButtonCost()) return null;
        patchStack.removePatch(patchID);
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() - patch.getButtonCost());
        currentPlayer.setTotalButtonIncome(currentPlayer.getTotalButtonIncome() + patch.getButtonIncome());
        return patch;
    }

    /**
     * Maps transformation matrices onto structural tile objects to test board layouts.
     *
     * @param patch    target configuration to lay down
     * @param row      target horizontal grid line row index orientation
     * @param col      target vertical grid column index configuration
     * @param rotation target orientation matrix choice reference
     * @return {@code true} if successful
     */
    private boolean placePatch(Patch patch, int row, int col, PatchRotation rotation) {
        if (patch == null) return false;
        patch.setRotation(rotation);
        return currentPlayer.getQuiltBoard().placePatch(patch, row, col);
    }

    /**
     * Public method to purchase and place a patch safely.
     * Checks all conditions before modifying state to prevent inconsistencies.
     *
     * @param patchID  target patch lookup identity
     * @param row      grid square index position selection row value
     * @param col      grid square index position selection column value
     * @param rotation geometric adjustment choice setting mapping selection
     * @return {@code true} if parameters match and transaction finishes seamlessly
     */
    public boolean buyAndPlacePatch(int patchID, int row, int col, PatchRotation rotation) {
        Patch patch = patchStack.getPatch(patchID);
        if (patch == null) return false;
        if (currentPlayer.getTotalButtons() < patch.getButtonCost()) return false;
        patch.setRotation(rotation);
        if (!currentPlayer.getQuiltBoard().canPlacePatch(patch, row, col)) return false;

        patchStack.removePatch(patchID);
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() - patch.getButtonCost());
        currentPlayer.setTotalButtonIncome(currentPlayer.getTotalButtonIncome() + patch.getButtonIncome());
        currentPlayer.getQuiltBoard().placePatch(patch, row, col);
        moveToken(patch.getTimeCost());
        checkSpecialTile();
        checkGameEnd();
        return true;
    }

    /**
     * Adjusts active currency assets based on passed board locations.
     *
     * @param buttonPositionsPassed scale amount multiplier reflecting elapsed segments
     */
    public void collectButtonIncome(int buttonPositionsPassed) {
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() + (currentPlayer.getTotalButtonIncome() * buttonPositionsPassed));
    }

    /**
     * Calculates positioning on the track layout timeline to determine turn order.
     * If both tokens share a slot index, player 1 takes precedence.
     */
    public void updateCurrentPlayer() {
        if (player1.getPosition() <= player2.getPosition()) {
            currentPlayer = player1;
        } else {
            currentPlayer = player2;
        }
    }

    /**
     * Shifts active structural components along track indices, triggering
     * income checks and distributing leather patches.
     *
     * @param timeCost linear step addition quantity parameter scale factor
     */
    public void moveToken(int timeCost) {
        int oldPosition = currentPlayer.getPosition();
        int newPosition = timeboard.updatePosition(oldPosition, timeCost);
        currentPlayer.updatePosition(newPosition - oldPosition);
        if (newPosition >= timeboard.getSize() - 1 && firstToFinish == null) {
            firstToFinish = currentPlayer;
        }
        int buttonPositionsPassed = timeboard.countButtonPositionsPassed(oldPosition, newPosition);
        collectButtonIncome(buttonPositionsPassed);
        int leatherPatchesPassed = timeboard.countLeatherPatchesPassed(oldPosition, newPosition);
        Queue<Patch> myQueue = getLeatherPatchQueue(currentPlayer);
        for (int i = 0; i < leatherPatchesPassed; i++) {
            myQueue.add(Patch.createLeatherPatch(leatherPatchCounter++));
        }
    }

    /**
     * Evaluates board spaces to award the special 7x7 tile bonus.
     */
    public void checkSpecialTile() {
        if (specialTileOwner == null) {
            if (player1.getQuiltBoard().hasSevenBySeven()) {
                specialTileOwner = player1;
            } else if (player2.getQuiltBoard().hasSevenBySeven()) {
                specialTileOwner = player2;
            }
        }
    }

    /**
     * Places a specific single-cell leather patch from a player's inventory queue.
     *
     * @param player corresponding user profile entity requesting processing execution trace
     * @param row    target coordinate index row placement line value
     * @param col    target coordinate index column placement tracking alignment
     * @return {@code true} if space layout configuration is verified and filled successfully
     */
    public boolean placeLeatherPatch(Player player, int row, int col) {
        Queue<Patch> queue = getLeatherPatchQueue(player);
        Patch leatherPatch = queue.poll();
        if (leatherPatch == null) return false;
        return player.getQuiltBoard().placePatch(leatherPatch, row, col);
    }

    /**
     * Passes the current turn, moving the player's token just past the opponent
     * and awarding buttons equal to the distance traveled.
     *
     * @throws IllegalStateException if the current player is already ahead of the opponent
     */
    public void pass() {
        Player otherPlayer = (currentPlayer == player1) ? player2 : player1;
        int newPosition = otherPlayer.getPosition() + 1;
        int steps = newPosition - currentPlayer.getPosition();
        if (steps <= 0) throw new IllegalStateException("Current player is already ahead of the other player");
        currentPlayer.setTotalButtons(currentPlayer.getTotalButtons() + steps);
        moveToken(steps);
        checkSpecialTile();
        checkGameEnd();
    }

    /**
     * Computes final scoring tallies by subtracting penalties for empty cells
     * and adding any bonus rewards.
     *
     * @param player actor identity target being measured
     * @return final comprehensive score calculation
     */
    public int calculateScore(Player player) {
        int score = player.getTotalButtons();
        score -= player.getQuiltBoard().countEmptySpaces() * 2;
        if (specialTileOwner == player) score += 7;
        return score;
    }

    /**
     * Verifies whether both players have reached the final track index.
     * Resolves ties by crowning the player who arrived first.
     *
     * @return {@code true} if match structures have fully wound down to resolution endpoints
     */
    public boolean checkGameEnd() {
        if (player1.getPosition() >= timeboard.getSize() - 1 && player2.getPosition() >= timeboard.getSize() - 1) {
            status = GameStatus.FINISHED;
            int scorePlayer1 = calculateScore(player1);
            int scorePlayer2 = calculateScore(player2);
            if (scorePlayer1 == scorePlayer2) {
                winner = firstToFinish;
            } else {
                winner = scorePlayer1 > scorePlayer2 ? player1 : player2;
            }
            return true;
        }
        return false;
    }
}