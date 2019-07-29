package edu.cmu.cs.cs214.hw4.core;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

/**
 * class that parses json files to input the parameters for all
 * the new tiles that are added to a tile deck.
 */
public class JSONConfigReader {
    /**
     * class that represesents a segment in JSON, which has the type
     * parameter, a list of segment locations which describe where the
     * segment is located on the tile, and a boolean that represents whether
     * or not the segment has a shield.
     */
    // CHECKSTYLE:OFF
    public class JSONSegment{
        public FeatureType type;
        public List<SegmentLocation> locations;
        public boolean shield;
    }

    /**
     * JSON tile class that represents a tile in json, where it has
     * a list of segments that make up the tile, the number of times that
     * tile appears in the deck, the letter of the tile, and whether or not
     * that tile has a monastery at the center.
     */
    public class JSONTile{
        public String letter;
        public int count;
        public JSONSegment[] segments;
        public boolean monastery;
    }

    /**
     * JSON deck class that has a list of JSONtiles.
     */
    public class JSONDeck{
        public JSONTile[] tiles;
    }

    /**
     * static function that parses the contents of a given json file.
     * @param configFile string that represents the file location of
     *                   the given json file.
     * @return a JSONdeck class that contains a list of JSON tiles, that can
     * be used to convert to a list of regular Tile classes.
     */
    public static JSONDeck parse(String configFile){
        Gson gson = new Gson();
        try{
            Reader reader = new FileReader(new File(configFile));
            return gson.fromJson(reader,JSONDeck.class);
        } catch(IOException e){
            throw new IllegalArgumentException("Error when reading file: " + configFile, e);
        }
    }
    // CHECKSTYLE:ON
}
