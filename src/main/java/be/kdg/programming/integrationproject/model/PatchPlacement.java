package be.kdg.programming.integrationproject.model;

/**
 * Immutable value container recording a patch's coordinate placement on a quiltboard.
 * Primarily used to save states or track historical moves.
 *
 * @author Team 4
 * @version 1.0
 */
public class PatchPlacement {
    private final Patch patch;
    private final int row;
    private final int col;

    /**
     * Binds a patch instance configuration to absolute grid indexes.
     *
     * @param patch specific tile instance element being recorded
     * @param row   horizontal grid row index tracking location coordinate
     * @param col   vertical grid column index tracking location coordinate
     */
    public PatchPlacement(Patch patch, int row, int col){
        this.patch = patch;
        this.row = row;
        this.col = col;
    }

    public Patch getPatch(){ return patch; }
    public int getRow(){ return row; }
    public int getCol(){ return col; }
}