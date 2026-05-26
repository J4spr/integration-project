package be.kdg.programming.integrationproject.model;

/**
 * Models the linear game track path, consisting of 53 discrete positions (indices 0 to 52).
 * Manages token steps, tracks milestones for button income distribution events, and coordinates
 * single-use allocations for 1x1 leather patch reward squares.
 *
 * @author Team 4
 * @version 1.0
 */
public class Timeboard {
    /** The maximum size length scope constraint of the board track path (53 steps). */
    private static final int SIZE = 53;
    /** Fixed event intervals triggering button income generation payouts. */
    private static final int[] BUTTON_POSITIONS = {5, 11, 17, 23, 29, 35, 41, 47};
    /** Fixed map coordinate marks containing grabable 1x1 leather reward tiles. */
    private static final int[] LEATHER_PATCH_POSITIONS = {26, 32, 38, 44, 50};
    /** Boolean array flagging which leather patch positions have already been claimed. */
    private final boolean[] claimedLeatherPatches = new boolean[SIZE];

    /**
     * Gets the size configuration threshold capping the linear track path length.
     *
     * @return total tracks size number length
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Computes progression changes along the track, capping progress at the final slot (index 52).
     *
     * @param currentPosition the initial baseline slot position index value
     * @param timeCost        the step addition value increment scale factor
     * @return the newly resolved position index, capped at {@code SIZE - 1}
     */
    public int updatePosition(int currentPosition, int timeCost) {
        int newPosition = currentPosition + timeCost;
        if (newPosition >= SIZE) newPosition = SIZE - 1;
        return newPosition;
    }

    /**
     * Calculates the number of button milestone markings crossed during a move.
     * Evaluates intervals using an exclusive lower bound and inclusive upper bound
     * to prevent miscounting starting index overlaps.
     *
     * @param oldPosition the initial starting position track index scale factor
     * @param newPosition the destination endpoint track index scale factor
     * @return the total count of income events crossed
     */
    public int countButtonPositionsPassed(int oldPosition, int newPosition) {
        int count = 0;
        for (int buttonPosition : BUTTON_POSITIONS) {
            if (buttonPosition > oldPosition && buttonPosition <= newPosition) {
                count++;
            }
        }
        return count;
    }

    /**
     * Tracks whether a movement passes any unclaimed 1x1 leather patch tiles.
     * Successfully claimed tiles are flagged as dirty to prevent the trailing competitor
     * from collecting them again.
     *
     * @param oldPosition the starting location track index step reference
     * @param newPosition the destination endpoint track index step reference
     * @return the number of reward tiles collected during the move
     */
    public int countLeatherPatchesPassed(int oldPosition, int newPosition) {
        int count = 0;
        for (int leatherPosition : LEATHER_PATCH_POSITIONS) {
            if (leatherPosition > oldPosition && leatherPosition <= newPosition
                    && !claimedLeatherPatches[leatherPosition]) {
                claimedLeatherPatches[leatherPosition] = true;
                count++;
            }
        }
        return count;
    }
}