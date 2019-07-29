package edu.cmu.cs.cs214.hw4.core;

import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.S;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.N;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.E;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.W;

/**
 * class that helps handle the logic involved with the row and col
 * attributes of the main board, better to delegate the functionality
 * to this class so it can be reused.
 */
public class RowColHelper {
    public static final int BOARD_WIDTH = 143;
    public static final int NUM_DIRECTIONS = 4;

    /**
     * method for ensuring that a row col will be inbounds on the 2D board.
     * @param row row to be checked
     * @param col col to be checked
     * @return true if the row col are NOT in bounds, and false otherwise.
     */
    public boolean rowColNotInBounds(int row, int col){
        return !(row >= 0 && row <= BOARD_WIDTH - 1 && col >= 0 && col <= BOARD_WIDTH - 1);
    }

    /**
     * simple helper method that returns a new column number
     * based on the direction given.
     * @param col current column number
     * @param dir direction to update the column number
     * @return the new column number
     */
    public int getNewCol(int col, SegmentLocation dir){
        if(dir == E){
            return col + 1;
        } else if(dir == W){
            return col - 1;
        } else {
            return col;
        }
    }

    /**
     * helper method, that when given a direction and a row and col,
     * it will return the appropriate new row number that corresponds to
     * that direction.
     * @param row current row number
     * @param dir direction that the new row will be.
     * @return integer number for the new row.
     */
    public int getNewRow(int row, SegmentLocation dir){
        if(dir == N){
            return row - 1;
        } else if(dir == S){
            return row + 1;
        } else {
            return row;
        }
    }
    /**
     * simple method for finding the reverse direction, when given a direction
     * @param dir direction to find the inverse of
     * @return the inverted direction
     */
    public SegmentLocation getInverseDirection(SegmentLocation dir){
        int newDir = (dir.getValue() + 2) % NUM_DIRECTIONS;
        return SegmentLocation.valueOf(newDir);
    }

    /**
     * method that when given two POSITIVE integer values, will produce a new
     * hash value that will depend on the order of the given integers.
     * @param x first integer
     * @param y second integer
     * @return a new integer that will be a unique number based on the two inputs.
     */
    public int getUniqueHash(int x, int y){
        return Math.max(x,y)*Math.max(x,y) + Math.max(y,2*y - x);
    }
}
