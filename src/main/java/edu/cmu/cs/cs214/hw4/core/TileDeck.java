package edu.cmu.cs.cs214.hw4.core;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 *  Tile Deck class that has very basic functionality of dealing out cards.
 */
public class TileDeck {
    private List<Tile> gameDeck = new ArrayList<>();
    private Tile startTile;
    private List<String> tileIdentifiers = new ArrayList<>();
    /**
     * constructor function that calls the initialize function that calls a
     * JSONConfigReader script that loads in all the Tiles.
     */
    public TileDeck(){
        initializeDeck();
        Collections.shuffle(gameDeck);
        Collections.shuffle(tileIdentifiers);
    }

    /**
     * draw method that returns a random card in the deck
     * @return a Tile choosen randomly
     */
    public Tile draw() {
        if(tileIdentifiers.size() == 0){
            throw new IllegalArgumentException("No Tiles Left!");
        }
        int randomIndex = ThreadLocalRandom.current().nextInt(0,tileIdentifiers.size());
        String tileLetter = tileIdentifiers.get(randomIndex);
        tileIdentifiers.remove(randomIndex);
        TileDeck a = new TileDeck();
        for(Tile t:a.getGameDeck()){
            if(tileLetter.equals(t.getTileLetter())){
                return t;
            }
        }
        return null;
    }

    private List<Tile> getGameDeck(){
        return gameDeck;
    }

    /**
     * method for obtaining the start tile of the game.
     * @return the start tile.
     */
    public Tile getStartTile(){return startTile;}

    /**
     * checks to see if the deck is empty
     * @return true if the deck is empty
     */
    public boolean isEmpty(){
        return (tileIdentifiers.size() == 0);
    }

    /**
     * initializes the tile deck using a JSONConfigReader file.
     */
    private void initializeDeck(){
        JSONConfigReader.JSONDeck deck = JSONConfigReader.parse("src/main/resources/tileConfig.json");

        // loop through all the JSONTiles
        for(int i = 0; i < deck.tiles.length; i++){
            JSONConfigReader.JSONTile tile = deck.tiles[i];
            int numTilesToAdd = tile.count;
            if(tile.letter.equals("U")){
                numTilesToAdd = tile.count - 1;
                List<Segment> segments = new ArrayList<>();
                for(int j = 0; j < tile.segments.length; j++){
                    JSONConfigReader.JSONSegment seg = tile.segments[j];
                    segments.add(new Segment(seg.type, seg.locations, seg.shield));
                }
                startTile = new Tile(tile.letter, segments, tile.monastery);
            }
            for(int k = 0; k < numTilesToAdd; k++){
                List<Segment> segments = new ArrayList<>();
                for(int j = 0; j < tile.segments.length; j++){
                    JSONConfigReader.JSONSegment seg = tile.segments[j];
                    segments.add(new Segment(seg.type, seg.locations, seg.shield));
                }
                gameDeck.add(new Tile(tile.letter, segments, tile.monastery));
                tileIdentifiers.add(tile.letter);
            }
        }
    }
}
