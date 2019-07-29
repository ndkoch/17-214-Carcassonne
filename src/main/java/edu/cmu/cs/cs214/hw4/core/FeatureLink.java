package edu.cmu.cs.cs214.hw4.core;

/**
 * class that is simple used to represent a segment that is
 * attached to a tile, useful for scoring finished features.
 */
public class FeatureLink {
    private int row;
    private int col;
    private Segment segment;

    /**
     * constructor function that defines one of these featureLinks.
     * @param row row of the tile, this segment is on.
     * @param col col of the tile this segment is on.
     * @param segment segment that is attached to this tile.
     */
    public FeatureLink(int row, int col, Segment segment){
        this.row = row;
        this.col = col;
        this.segment = segment;
    }

    /**
     * constructor method that just takes in a row and col, essentially acts as
     * a way to store a point.
     * @param row row of tile
     * @param col col of tile
     */
    public FeatureLink(int row, int col){
        this.row = row;
        this.col = col;
        this.segment = null;
    }

    /**
     * method for getting the column of a featureLink
     * @return the column of the feature link
     */
    public int getCol() {
        return col;
    }

    /**
     * method for getting the row of the feature link
     * @return the row of the feature link
     */
    public int getRow(){
        return row;
    }

    /**
     * method for finding out what the score value of the feature is,
     * ie: roads are 1 and cities are 2.
     * @return the score of the feature.
     */
    public int getSegmentScore(){return segment.getType().getScoreValue();}

    /**
     * method for getting the segment associated with this feature link
     * @return the segment of the feature link
     */
    public Segment getSegment() {
        return segment;
    }

    @Override
    public String toString(){
        return row + " " + col + " " + segment.toString();
    }
}
