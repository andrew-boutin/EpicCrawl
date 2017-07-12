package org.bakingbits.epiccrawl;

/**
 * Created by aboutin on 7/10/17.
 */
public class GridLocation {
    private final int row, col;

    public GridLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object other) {
        if(other == null)
            return false;

        if(!(other instanceof GridLocation))
            return false;

        GridLocation otherGridLocation = (GridLocation)other;

        return otherGridLocation.getRow() == this.getRow() && otherGridLocation.getCol() == this.getCol();
    }
}
