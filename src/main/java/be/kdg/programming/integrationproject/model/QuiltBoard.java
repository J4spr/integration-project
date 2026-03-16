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

    //Counts the number of empty spaces on the quilt board
    //Each empty space is minus 2 score at the end of the game
    public int countEmptySpaces() {
        int count = 0;
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (!grid[r][c]) count++;
            }
        }
        return count;
    }

    //Counts the total button income of all placed patches
    public int countButtons() {
        int total = 0;
        for (Patch patch : placedPatches) {
            total += patch.getButtonIncome();
        }
        return total;
    }

    public boolean hasSevenBySeven() {
        for (int r = 0; r <= SIZE - 7; r++) {
            for (int c = 0; c <= SIZE - 7; c++) {
                if (isSevenBySevenAt(r, c)) return true;
            }
        }
        return false;
    }

    private boolean isSevenBySevenAt(int startRow, int startCol) {
        for (int r = startRow; r < startRow + 7; r++) {
            for (int c = startCol; c < startCol + 7; c++) {
                if (!grid[r][c]) return false;
            }
        }
        return true;
    }
}