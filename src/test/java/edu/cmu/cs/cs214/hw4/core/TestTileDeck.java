package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestTileDeck {
    private TileDeck testDeck = new TileDeck();

    private Tile getTile(String tileLetter){
        TileDeck tilePile = new TileDeck();
        Tile t = tilePile.draw();
        while(!t.getTileLetter().equals(tileLetter)){
            t = tilePile.draw();
        }
        return t;
    }

    @Test
    public void testLoadTiles(){
        TileDeck td = new TileDeck();
        assertTrue(!td.isEmpty());
    }

    @Test
    public void testDrawNotNull(){
        Tile t1 = testDeck.draw();
        assertNotNull(t1);
    }

    @Test
    public void testDrawTile(){
        Tile t1 = testDeck.draw();
        Tile t2 = getTile(t1.getTileLetter());
        assertEquals(t1,t2);
    }

    @Test
    public void testSizeOfDeck(){
        for(int i = 0; i < 71; i++){
            assertTrue(!testDeck.isEmpty());
            testDeck.draw();
        }
        assertTrue(testDeck.isEmpty());
    }

    @Test
    public void testCorrectStartTile(){
        Tile start = testDeck.getStartTile();
        Tile expectedStart = getTile("U");
        assertEquals(start,expectedStart);
    }

    @Test
    public void testRotateCW(){
        Tile t = getTile("L");
        Segment s1 = t.getDirectionSegment(SegmentLocation.N);
        t.rotateCW();
        Segment s2 = t.getDirectionSegment(SegmentLocation.E);
        assertEquals(s1,s2);
    }

    @Test
    public void testRotateCCW(){
        Tile t = getTile("J");
        Segment s1 = t.getDirectionSegment(SegmentLocation.N);
        t.rotateCCW();
        Segment s2 = t.getDirectionSegment(SegmentLocation.W);
        assertEquals(s1,s2);
    }

    @Test
    public void testRotateCombo(){
        Tile t = getTile("I");
        Segment s1 = t.getDirectionSegment(SegmentLocation.S);
        t.rotateCW();
        t.rotateCCW();
        Segment s2 = t.getDirectionSegment(SegmentLocation.S);
        assertEquals(s1,s2);
    }

    @Test
    public void testHasMonastery(){
        Tile t = getTile("A");
        assertTrue(t.hasMonastery());
    }

    @Test
    public void testTileHasShield(){
        Tile t = getTile("M");
        Segment s = t.getDirectionSegment(SegmentLocation.W);
        assertTrue(s.hasShield());
    }
}
