package edu.cmu.cs.cs214.hw4.core;

import java.util.HashMap;
import java.util.Map;

/**
 * enumeration for the four different locations a segment can occupy
 * on a tile.
 */
public enum SegmentLocation {
    N(0),
    E(1),
    S(2),
    W(3);

    private int value;
    private static Map<Integer, SegmentLocation> map = new HashMap<>();

    /**
     * assigning an enumeration an integer value
     * @param value integer value of the enumeration.
     */
    SegmentLocation(int value){
        this.value = value;
    }

    static {
        for(SegmentLocation location: SegmentLocation.values()){
            map.put(location.value,location);
        }
    }

    /**
     * method that determines the location given its value.
     * @param locationNum value for the enumeration
     * @return the segmentLocation that corresponds to the given value.
     */
    public static SegmentLocation valueOf(int locationNum){
        return(map.get(locationNum));
    }

    /**
     * method for getting the value of an enum
     * @return the value of the enum.
     */
    public int getValue() {return value;}
}