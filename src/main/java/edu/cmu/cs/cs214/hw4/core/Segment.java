package edu.cmu.cs.cs214.hw4.core;
import java.util.List;

/**
 * Segment class which represents a feature segment that goes onto a
 * tile. Does this by first defining the type of segment ie: ROAD, FIELD, CITY,
 * or MONASTERY. Then has a list of locations that the segment occupies on the tile,
 * and finally a boolean value for whether or not the segment has a shield.
 */
public class Segment {
   private FeatureType type;
   private List<SegmentLocation> locations;
   private boolean shield;
   private Meeple segmentMeeple = null;
   private static final int NUM_LOCATIONS = 4;
   private static final int MAGIC_NUMBER = 31;

    /**
     * constructor function that makes a new segment.
     * @param type that contains the information for what type of feature it is.
     * @param locations list of locations that the segment occupies on the tile
     * @param hasShield boolean representation for whether or not the segment has a shield.
     */
   public Segment(FeatureType type, List<SegmentLocation> locations, boolean hasShield){
       this.type = type;
       this.locations = locations;
       this.shield = hasShield;
   }

    /**
     * rotate function that will rotate the segment by uodating the locations
     * list.
     * @param dir integer value for whether or not to rotate CW or CCW. Where
     *            0 is CW and anything else is CCW.
     */
   public void rotate(int dir){
       for(int i = 0; i < locations.size(); i++){
           int curDir = locations.get(i).getValue();
           if(dir == 0){
               curDir = (curDir + 1) % NUM_LOCATIONS;
           } else {
               curDir = (curDir + (NUM_LOCATIONS - 1)) % NUM_LOCATIONS;
           }
           SegmentLocation newDir = SegmentLocation.valueOf(curDir);
           locations.set(i,newDir);
       }
   }

    /**
     * method for seeing if the segment has a shield
     * @return a boolean value for whether or not this segment has a shield.
     */
    public boolean hasShield(){return shield;}

    /**
     * method for getting the type of segment
     * @return the feature type of the segment.
     */
    public FeatureType getType(){return type;}

    /**
     * method that returns the locations this segment occupies,
     * useful for the tile class when it needs to try and
     * find a certain segment in a certain location.
     * @return the list of locations this segment occupies.
     */
    public List<SegmentLocation> getLocations(){return locations;}

    /**
     * method for placing a meeple on a segment, the key feature for
     * earning points in the game of carcassone.
     * @param meeple meeple object to be placed.
     */
    public void placeMeeple(Meeple meeple){
        segmentMeeple = meeple;
    }

    /**
     * method for checking whether or not this segment has
     * a meeple.
     * @return a boolean value for whether or not this segment has a
     * meeple.
     */
    public boolean hasMeeple(){
        return (segmentMeeple != null);
    }

    /**
     * method for removing the meeple on the segment.
     * @return the meeple that was on this segment.
     */
    public Meeple removeMeeple(){
        Meeple res = segmentMeeple;
        segmentMeeple = null;
        return res;
    }

   @Override
    public String toString(){
       String result = (type + " @ " + locations.toString());
       if(hasShield()){
           result = result + " with shield";
       } else {
           result = result + " without shield";
       }
       return (result);
   }

   @Override
    public boolean equals(Object o){
       if(!(o instanceof Segment)){
           return false;
       }
       Segment other = (Segment) o;
       if(other.locations.size() != locations.size()){
           return false;
       }
       for(SegmentLocation s: other.locations){
           if(!locations.contains(s)){
               return false;
           }
       }
       return (other.type == type && other.shield == shield);
   }

   @Override
    public int hashCode(){
        if(shield) {
            return (MAGIC_NUMBER * (locations.hashCode()) + type.hashCode());
        }
        return (MAGIC_NUMBER * (1 + locations.hashCode()) + type.hashCode());
   }
}
