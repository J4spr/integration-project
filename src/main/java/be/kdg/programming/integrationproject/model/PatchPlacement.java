package be.kdg.programming.integrationproject.model;

public class PatchPlacement {

    private final Patch patch;
    private final int row;
    private final int col;

    public PatchPlacement(
            Patch patch,
            int row,
            int col
    ){
        this.patch=patch;
        this.row=row;
        this.col=col;
    }

    public Patch getPatch(){
        return patch;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

}