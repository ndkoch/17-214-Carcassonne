package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import static junit.framework.TestCase.*;

public class TestMeeplePlacement {
    private Player testPlayer1 = new Player(1);
    private Player testPlayer2 = new Player(2);

    private Tile getTile(String tileLetter){
        TileDeck tilePile = new TileDeck();
        Tile t = tilePile.draw();
        while(!t.getTileLetter().equals(tileLetter)){
            t = tilePile.draw();
        }
        return t;
    }

    @Test
    public void testMeeplePlacement(){
        Tile t = getTile("H");
        Meeple playerMeeple = testPlayer1.getMeeple();
        assertTrue(t.placeMeeple(playerMeeple,SegmentLocation.W));
    }

    @Test
    public void testWhenTileHasMeeple(){
        Tile t = getTile("O");
        Meeple playerMeeple = testPlayer1.getMeeple();
        assertTrue(t.placeMeeple(playerMeeple,SegmentLocation.S));
        assertTrue(!t.placeMeeple(playerMeeple,SegmentLocation.E));
        assertTrue(!t.placeMeeple(testPlayer2.getMeeple(),SegmentLocation.N));
    }

    @Test
    public void placementWithMonastery(){
        Tile t = getTile("A");
        assertTrue(t.placeMeeple(testPlayer1.getMeeple(),SegmentLocation.S));
        assertTrue(!t.placeMeeple(testPlayer1.getMeeple(),SegmentLocation.N));
        assertTrue(!t.placeMeeple(testPlayer2.getMeeple(),SegmentLocation.W));
    }

    @Test
    public void testMeepleInField(){
        Tile t = getTile("E");
        Meeple playerMeeple = testPlayer1.getMeeple();
        assertTrue(!t.placeMeeple(playerMeeple,SegmentLocation.S));
    }

    @Test
    public void testRemoveMeeples(){
        Tile t = getTile("E");
        t.placeMeeple(testPlayer1.getMeeple(),SegmentLocation.N);
        Meeple rem = t.removeMeeples();
        assertEquals(rem.getMeeplePlayer(),1);
        assertNull(t.getSegmentWithMeeple());
        assertTrue(t.placeMeeple(testPlayer1.getMeeple(),SegmentLocation.N));
    }
}
