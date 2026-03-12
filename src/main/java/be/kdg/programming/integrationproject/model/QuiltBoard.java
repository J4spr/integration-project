package be.kdg.programming.integrationproject.model;

import java.util.ArrayList;
import java.util.List;

public class QuiltBoard {
    private static final int SIZE = 9;//board is a 9x9 grid
    private final boolean[][] grid = new boolean[SIZE][SIZE];//true = occupied, false = empty
    private final List<Patch> placedPatches = new ArrayList<>();//list of placed patches

    //Checks if a patch can be placed at the given position without
    //going out of bounds or overlapping with existing patches.
    public boolean canPlacePatch(Patch patch, int row, int col) {
        boolean[][] shape = patch.getShape().getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    int newRow = row + r;
                    int newCol = col + c;
                    if (newRow >= SIZE || newCol >= SIZE) return false;//out of bounds
                    if (grid[newRow][newCol]) return false;//cell is occupied
                }
            }
        }
        return true;
    }

    //Places a patch on the board at the given position
    //Returns false if the patch cannot be placed
    public boolean placePatch(Patch patch, int row, int col) {
        if (!canPlacePatch(patch, row, col)) return false;
        boolean[][] shape = patch.getShape().getShape();
        for (int r = 0; r < shape.length; r++) {
            for (int c = 0; c < shape[r].length; c++) {
                if (shape[r][c]) {
                    grid[row + r][col + c] = true;//marks cell as occupied
                }
            }
        }
        placedPatches.add(patch);
        return true;
    }
}
