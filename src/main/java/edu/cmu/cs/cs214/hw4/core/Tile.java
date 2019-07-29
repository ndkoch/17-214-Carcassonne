package edu.cmu.cs.cs214.hw4.core;

import java.util.List;

/**
 * Tile that is represented by different features, exactly
 * 4 different features a city,road,field and monastery. These are all
 * represented by the feature class.
 */
public class Tile {
    private String tileLetter;
    private List<Segment> segments;
    private boolean monastery;

    /**
     * constructor method for creating a tile
     * @param letter string that represents the letter of the given tile,
     *               as seen by the tiles.png image given in the handout.
     * @param segments a list of the segments that make up the tile, should be at most
     *                 4.
     * @param hasMonastery boolean value for whether the tile has a monsatery or
     *                     not, only 2 specific tiles have this feature.
     */
    public Tile(String letter, List<Segment> segments, boolean hasMonastery){
        this.tileLetter = letter;
        this.segments = segments;
        this.monastery = hasMonastery;
    }

    /**
     * method for rotating the tile clockwise, by rotating all the
     * segments.
     */
    public void rotateCW(){
        for (Segment seg: segments) {
            seg.rotate(0);
        }
    }

    /**
     * method for rotating the tile counter clockwise, similarly by
     * rotating all the segments.
     */
    public void rotateCCW(){
        for (Segment seg: segments) {
            seg.rotate(1);
        }
    }

    /**
     * method to see if the tile has a monastery.
     * @return boolean value thats true if the tile has a
     * monastery.
     */
    public boolean hasMonastery(){return monastery;}

    public List<Segment> getSegments(){return segments;}

    /**
     * method that returns the segment which occupies the given segment
     * location.
     * @param dir direction of which segment we are searching for.
     * @return the segment that occupies that direction of the tile.
     */
    public Segment getDirectionSegment(SegmentLocation dir){
        for(Segment seg:segments){
            if(seg.getLocations().contains(dir)){
                return seg;
            }
        }
        return null;
    }

    /**
     * method for placing a meeple on one of the segments that makes up
     * this tile.
     * @param meeple player meeple that is going to be placed.
     * @param location segment location for where the meeple needs
     *                 to be placed.
     * @return a boolean value that represents whether or not the placement
     * was successful.
     */
    public boolean placeMeeple(Meeple meeple, SegmentLocation location){
        if(getSegmentWithMeeple() != null){
            return false;
        }
        for(int i = 0; i < segments.size(); i++){
            Segment place = segments.get(i);
            if(place.getLocations().contains(location)){
                if(hasMonastery() && !place.hasMeeple() &&
                        place.getType() == FeatureType.FIELD){
                    place.placeMeeple(meeple);
                    segments.set(i,place);
                    return true;
                }
                if(place.getType() == FeatureType.FIELD || place.hasMeeple()) {
                    return false;
                } else {
                    place.placeMeeple(meeple);
                    segments.set(i,place);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method that returns the segment of the tile that has
     * the meeple located on it.
     * @return the meeple thats located on the tile, otherwise it
     * will return null if there are no meeples.
     */
    public Segment getSegmentWithMeeple(){
        for(Segment segment:segments){
            if(segment.hasMeeple()){
                return segment;
            }
        }
        return null;
    }

    /**
     * method that will return the Meeple on the Board,
     * otherwise it will return null
     * @return the meeple that is on the tile.
     */
    public Meeple removeMeeples(){
        for(Segment segment: segments){
            if(segment.hasMeeple()) {
                return segment.removeMeeple();
            }
        }
        return null;
    }

    /**
     * method for getting the letter associated with the tile.
     * mainly used for testing purposes, but may prove to be useful
     * down the line
     * @return the string associated with the tile.
     */
    public String getTileLetter(){return tileLetter;}

    @Override
    public String toString(){
        String result = "Tile " + tileLetter + " \nFeatures: \n" ;
        for(Segment seg: segments){
            result = result + seg.toString() + " \n";
        }
        if(hasMonastery()){
            result = result + "with monastery";
        } else {
            result = result + "without monastery";
        }
        return result;
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Tile)){
            return false;
        }
        Tile other = (Tile)o;
        return ((tileLetter.equals(other.tileLetter)));
    }

    @Override
    public int hashCode(){
        return tileLetter.hashCode();
    }
}
