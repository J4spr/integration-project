package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Governs the dimensional 9x9 layout matrix workspace grid for tile placement.
 * Validates collision conditions, boundary intersections, final scoring penalties,
 * and special 7x7 regional structural bonus constraints.
 *
 * @author Team 4
 * @version 1.0
 */
public class Quiltboard {
    /** The static dimensional scaling length indicating grid widths and heights (9x9). */
    private static final int SIZE = 9;
    /** The underlying primitive two-dimensional boolean matrix tracking filled vs empty cell slots. */
    private final boolean[][] grid = new boolean[SIZE][SIZE];
    /** The structural list collection containing all placed patch components. */
    private final List<Patch> placedPatches = new ArrayList<>();
    /** The track sequence collection tracking absolute placement origins. */
    private final List<PatchPlacement> placements = new ArrayList<>();

    /**
     * Retrieves the binary representation matrix layout mapping board occupation flags.
     *
     * @return a 2D boolean array mapping where {@code true} stands for an occupied tile cell
     */
    public boolean[][] getGrid() {
        return grid;
    }

    /**
     * Gets the constant bounding edge size dimension limit of the board layout.
     *
     * @return the edge width scale length
     */
    public static int getSize() {
        return SIZE;
    }

    /**
     * Evaluates a prospective tile placement to confirm it does not breach outer bounds
     * or overlap an already filled cell.
     *
     * @param patch the target tile component whose rotated layout footprint is being verified
     * @param row   the horizontal origin board vector index offset
     * @param col   the vertical origin board vector index offset
     * @return {@code true} if the patch fits perfectly within the target region bounds
     */
    public boolean canPlacePatch(Patch patch, int row, int col) {
        boolean[][] shape = patch.getRotatedShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    int newRow = row + r;
                    int newCol = col + c;
                    if (newRow < 0 || newCol < 0 || newRow >= SIZE || newCol >= SIZE) return false;
                    if (grid[newRow][newCol]) return false;
                }
            }
        }
        return true;
    }

    /**
     * Persists a patch onto the matrix grid layout. Marks the targeted cells as occupied
     * and updates placement tracking logs.
     *
     * @param patch the target structural patch instance component configuration to lay down
     * @param row   horizontal grid row index position choice selection parameter
     * @param col   vertical grid column index position choice selection parameter
     * @return {@code true} if placement finishes successfully; {@code false} if a constraint check is breached
     */
    public boolean placePatch(Patch patch, int row, int col) {
        if (!canPlacePatch(patch, row, col)) return false;
        boolean[][] shape = patch.getRotatedShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    grid[row + r][col + c] = true;
                }
            }
        }
        placedPatches.add(patch);
        placements.add(new PatchPlacement(patch, row, col));
        return true;
    }

    /**
     * Loops through all cell slots to calculate remaining vacant indices.
     * Used at game end to apply score deductions (minus 2 points per empty slot).
     *
     * @return the total number of unassigned empty cell coordinates
     */
    public int countEmptySpaces() {
        int count = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (!grid[r][c]) count++;
            }
        }
        return count;
    }

    /**
     * Tallies the total recurrent button income generated across all placed patches.
     * Serves as an internal sanity check against the player's total income attribute.
     *
     * @return the sum of button income values from all placed patches
     */
    public int countButtons() {
        int total = 0;
        for (Patch patch : placedPatches) {
            total += patch.getButtonIncome();
        }
        return total;
    }

    /**
     * Scans the 9x9 layout to find any completely filled 7x7 sub-grids.
     * The first player to satisfy this condition receives a special bonus tile.
     *
     * @return {@code true} if at least one fully occupied 7x7 sub-grid is discovered
     */
    public boolean hasSevenBySeven() {
        for (int r = 0; r <= SIZE - 7; r++) {
            for (int c = 0; c <= SIZE - 7; c++) {
                if (isSevenBySevenAt(r, c)) return true;
            }
        }
        return false;
    }

    /**
     * Helper evaluation routine verifying whether all 49 cells within a specific 7x7
     * coordinate window are occupied.
     *
     * @param startRow the horizontal top-left row index boundary marker
     * @param startCol the vertical top-left column index boundary marker
     * @return {@code true} if the evaluated 7x7 region is completely filled
     */
    private boolean isSevenBySevenAt(int startRow, int startCol) {
        for (int r = startRow; r < startRow + 7; r++) {
            for (int c = startCol; c < startCol + 7; c++) {
                if (!grid[r][c]) return false;
            }
        }
        return true;
    }

    /**
     * Gets the comprehensive list log collection containing absolute location vectors.
     *
     * @return list containing placement values
     */
    public List<PatchPlacement> getPlacements(){
        return placements;
    }
}