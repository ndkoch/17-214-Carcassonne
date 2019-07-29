package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.HashSet;

import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.*;
import static org.junit.Assert.*;

public class TestBoard {

    private Tile getTile(String tileLetter){
        TileDeck tilePile = new TileDeck();
        Tile t = tilePile.draw();
        while(!t.getTileLetter().equals(tileLetter)){
            t = tilePile.draw();
        }
        return t;
    }
    private Board testBoard = new Board(getTile("U"));
    private Player p1 = new Player(1);
    private Player p2 = new Player(2);

    @Test
    public void testInitialization(){
        Board test = new Board(getTile("U"));
        assertTrue(true);
    }

    @Test
    public void testPlacementAtCenter(){
        Tile t = getTile("A");
        assertTrue(!(testBoard.placeTile(t,71,71)));
    }

    @Test
    public void testOutOfBounds(){
        Tile t = getTile("U");
        assertTrue(!(testBoard.placeTile(t,0,143)));
    }

    @Test
    public void testSimplePlacement(){
        Tile t = getTile("O");
        assertTrue(testBoard.placeTile(t,70,71));
    }

    @Test
    public void testInvalidPlacement(){
        Tile t = getTile("C");
        assertTrue(!(testBoard.placeTile(t,72,71)));
    }

    @Test
    public void testValidPlacementWithRotation(){
        Tile t = getTile("J");
        assertTrue(!(testBoard.placeTile(t,72,71)));
        t.rotateCCW();
        assertTrue(testBoard.placeTile(t,72,71));
    }

    @Test
    public void testInvalidPlacementWithRotation(){
        Tile t = getTile("Q");
        t.rotateCW();
        assertTrue(!(testBoard.placeTile(t,71,70)));
        assertTrue(testBoard.placeTile(t,71,72));
    }

    @Test
    public void testPlacementWithMultipleTiles(){
        Tile t1 = getTile("K");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("P");
        t2.rotateCCW();
        t2.rotateCCW();
        assertTrue(testBoard.placeTile(t2,72,71));
        Tile t3 = getTile("D");
        t3.rotateCCW();
        assertTrue(testBoard.placeTile(t3,72,70));
        Tile t4 = getTile("H");
        assertTrue(!(testBoard.placeTile(t4,71,70)));
        t4.rotateCW();
        assertTrue(testBoard.placeTile(t4,71,70));
        Tile t5 = getTile("R");
        t5.rotateCW();
        assertTrue(!(testBoard.placeTile(t5,72,72)));
        t5.rotateCCW();
        assertTrue(testBoard.placeTile(t5,72,72));
    }

    @Test
    public void testPlacementWithCorrectMeeple(){
        Tile t1 = getTile("V");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("D");
        t2.rotateCCW();
        t2.placeMeeple(p1.getMeeple(),SegmentLocation.E);
        assertTrue(testBoard.placeTile(t2,70,72));
    }

    @Test
    public void testPlacementWithIncorrectMeeple(){
        Tile t1 = getTile("V");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("D");
        t2.rotateCCW();
        t2.placeMeeple(p1.getMeeple(),SegmentLocation.E);
        assertTrue(testBoard.placeTile(t2,70,72));
        Tile t3 = getTile("I");
        assertTrue(testBoard.placeTile(t3,71,72));
        Tile t4 = getTile("V");
        t4.rotateCW();
        t4.placeMeeple(p2.getMeeple(),SegmentLocation.W);
        assertTrue(!testBoard.placeTile(t4,72,71));
        t4.removeMeeples();
        assertTrue(testBoard.placeTile(t4,72,71));
    }

    @Test
    public void testMeeplePlacementOnIntersection(){
        Tile t1 = getTile("K");
        t1.rotateCW();
        assertTrue(testBoard.placeTile(t1,72,71));
        Tile t2 = getTile("V");
        t2.placeMeeple(p1.getMeeple(), S);
        assertTrue(testBoard.placeTile(t2,72,72));
        Tile t3 = getTile("W");
        t3.rotateCW();
        t3.rotateCW();
        t3.placeMeeple(p2.getMeeple(),SegmentLocation.W);
        assertTrue(testBoard.placeTile(t3,73,72));
        t3.removeMeeples();
        t3.placeMeeple(p2.getMeeple(),SegmentLocation.N);
        assertTrue(!testBoard.placeTile(t3,73,72));
    }

    @Test
    public void testRoadLoopMeeple(){
        Tile t1 = getTile("V");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,71,72));
        Tile t2 = getTile("V");
        assertTrue(testBoard.placeTile(t2,71,73));
        Tile t3 = getTile("V");
        t3.rotateCW();
        t3.placeMeeple(p1.getMeeple(),SegmentLocation.W);
        assertTrue(testBoard.placeTile(t3,72,73));
        Tile t4 = getTile("V");
        t4.rotateCCW();
        t4.rotateCCW();
        t4.placeMeeple(p1.getMeeple(),SegmentLocation.N);
        assertTrue(!testBoard.placeTile(t4,72,72));
    }

    @Test
    public void testStraightRoadSegment(){
        Tile t1 = getTile("U");
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("U");
        t2.placeMeeple(p1.getMeeple(),SegmentLocation.N);
        assertTrue(testBoard.placeTile(t2,72,71));
        Tile t3 = getTile("V");
        t3.rotateCCW();
        t3.placeMeeple(p2.getMeeple(), S);
        assertTrue(!testBoard.placeTile(t3,69,71));
    }

    @Test
    public void testCityMeeplePlacement(){
        Tile t1 = getTile("N");
        assertTrue(t1.placeMeeple(p1.getMeeple(),SegmentLocation.W));
        assertTrue(testBoard.placeTile(t1,71,70));
        Tile t2 = getTile("N");
        t2.rotateCW();
        assertTrue(testBoard.placeTile(t2,71,69));
        Tile t3 = getTile("N");
        t3.rotateCW();
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,70,69));
        Tile t4 = getTile("R");
        t4.rotateCCW();
        assertTrue(testBoard.placeTile(t4,70,70));
        Tile t5 = getTile("E");
        t5.rotateCW();
        t5.rotateCW();
        t5.placeMeeple(p1.getMeeple(), S);
        assertTrue(!testBoard.placeTile(t5,69,70));
        //t5.removeMeeples();
        t1.removeMeeples();
        assertTrue(testBoard.placeTile(t5,69,70));
    }

    @Test
    public void testCircularRoadWithIntersection(){
        Tile t1 = getTile("V");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,71,72));
        assertTrue(t1.placeMeeple(p1.getMeeple(), S));
        Tile t2 = getTile("W");
        assertTrue(t2.placeMeeple(p1.getMeeple(), S));
        assertTrue(testBoard.placeTile(t2,71,73));
        Tile t3 = getTile("V");
        t3.rotateCW();
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,72,73));
        Tile t4 = getTile("V");
        t4.rotateCW();
        t4.placeMeeple(p2.getMeeple(),SegmentLocation.N);
        assertTrue(!testBoard.placeTile(t4,72,72));
    }

    @Test
    public void testBasicCityMeeple(){
        Tile t1 = getTile("D");
        assertTrue(t1.placeMeeple(p1.getMeeple(),SegmentLocation.E));
        assertTrue(testBoard.placeTile(t1,72,71));
        Tile t2 = getTile("H");
        assertTrue(t2.placeMeeple(p1.getMeeple(),SegmentLocation.W));
        assertTrue(!testBoard.placeTile(t2,72,72));
        t2.removeMeeples();
        assertTrue(testBoard.placeTile(t2,72,72));
    }

    @Test
    public void testSprawlingCityPlacement(){
        Tile t1 = getTile("N");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,71,70));
        Tile t2 = getTile("N");
        assertTrue(t2.placeMeeple(p1.getMeeple(),SegmentLocation.N));
        assertTrue(testBoard.placeTile(t2,72,70));
        Tile t3 = getTile("N");
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,72,69));
        Tile t4 = getTile("R");
        t4.rotateCCW();
        t4.rotateCCW();
        assertTrue(testBoard.placeTile(t4,71,69));
        Tile t5 = getTile("F");
        assertTrue(testBoard.placeTile(t5,71,68));
        Tile t6 = getTile("N");
        t6.rotateCCW();
        t6.rotateCCW();
        t6.placeMeeple(p1.getMeeple(),SegmentLocation.E);
        assertTrue(!testBoard.placeTile(t6,71,67));
        t2.removeMeeples();
        assertTrue(testBoard.placeTile(t6,71,67));
    }

    @Test
    public void testCompleteFeature(){
        Tile t1 = getTile("N");
        t1.rotateCCW();
        assertTrue(testBoard.placeTile(t1,71,70));
        Tile t2 = getTile("N");
        assertTrue(testBoard.placeTile(t2,72,70));
        Tile t3 = getTile("N");
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,72,69));
        Tile t4 = getTile("R");
        t4.rotateCCW();
        t4.rotateCCW();
        assertTrue(testBoard.placeTile(t4,71,69));
        Tile t5 = getTile("F");
        assertTrue(testBoard.placeTile(t5,71,68));
        Tile t6 = getTile("N");
        t6.rotateCCW();
        t6.rotateCCW();
        assertTrue(testBoard.placeTile(t6,71,67));
        Segment startSegment = t6.getDirectionSegment(S);
        assertTrue(!testBoard.completesFeature(startSegment,71,67,new HashSet<>()));
        Tile t7 = getTile("E");
        assertTrue(testBoard.placeTile(t7,72,67));
        startSegment = t7.getDirectionSegment(SegmentLocation.N);
        assertTrue(testBoard.completesFeature(startSegment,72,67,new HashSet<>()));
    }

    @Test
    public void testUnfinishedCities(){
        Tile t1 = getTile("I");
        assertTrue(testBoard.placeTile(t1,71,72));
        Tile t2 = getTile("N");
        Segment startSegment = t2.getDirectionSegment(SegmentLocation.N);
        assertTrue(testBoard.placeTile(t2,72,72));
        assertTrue(!testBoard.completesFeature(startSegment,72,72,new HashSet<>()));
        Tile t3 = getTile("D");
        assertTrue(testBoard.placeTile(t3,72,71));
        startSegment = t3.getDirectionSegment(SegmentLocation.E);
        assertTrue(testBoard.completesFeature(startSegment,72,71,new HashSet<>()));
        startSegment = t3.getDirectionSegment(S);
        assertTrue(!testBoard.completesFeature(startSegment,72,71,new HashSet<>()));
        Tile t4 = getTile("E");
        t4.rotateCCW();
        assertTrue(testBoard.placeTile(t4,71,73));
        startSegment = t4.getDirectionSegment(SegmentLocation.W);
        assertTrue(testBoard.completesFeature(startSegment,71,73,new HashSet<>()));
    }

    @Test
    public void testCompletedRoadLoop(){
        Tile t1 = getTile("J");
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("V");
        assertTrue(testBoard.placeTile(t2,70,72));
        Tile t3 = getTile("U");
        assertTrue(testBoard.placeTile(t3,71,72));
        Tile t4 = getTile("V");
        t4.rotateCW();
        assertTrue(testBoard.placeTile(t4,72,72));
        Segment startSegment = t4.getDirectionSegment(SegmentLocation.E);
        assertTrue(!testBoard.completesFeature(startSegment,72,72,new HashSet<>()));
        Tile t5 = getTile("V");
        t5.rotateCW();
        t5.rotateCW();
        t5.placeMeeple(p1.getMeeple(),SegmentLocation.N);
        assertTrue(testBoard.placeTile(t5,72,71));
        startSegment = t5.getDirectionSegment(SegmentLocation.N);
        assertTrue(testBoard.completesFeature(startSegment,72,71,new HashSet<>()));
    }

    @Test
    public void testDeadEndFinishedRoad(){
        Tile t1 = getTile("W");
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("X");
        assertTrue(testBoard.placeTile(t2,72,71));
        Segment start = t1.getDirectionSegment(S);
        assertTrue(testBoard.completesFeature(start,70,71,new HashSet<>()));
    }

    @Test
    public void testWithUnfinishedRoad(){
        Tile t1 = getTile("W");
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("X");
        assertTrue(testBoard.placeTile(t2,72,71));
        Segment start = t1.getDirectionSegment(W);
        assertTrue(!testBoard.completesFeature(start,70,71,new HashSet<>()));
    }

    @Test
    public void testEasyCityFeature(){
        Tile t1 = getTile("N");
        t1.rotateCW();
        t1.rotateCW();
        assertTrue(testBoard.placeTile(t1,71,72));
        Tile t2 = getTile("E");
        t2.rotateCCW();
        assertTrue(testBoard.placeTile(t2,71,73));
        Segment start = t2.getDirectionSegment(W);
        assertTrue(!testBoard.completesFeature(start,71,73,new HashSet<>()));
        Tile t4 = getTile("R");
        t4.rotateCW();
        assertTrue(testBoard.placeTile(t4,72,72));
        start = t4.getDirectionSegment(N);
        assertTrue(!testBoard.completesFeature(start,72,72,new HashSet<>()));
        assertTrue(testBoard.placeTile(t2,72,73));
    }

    @Test
    public void testSimpleRoadScore(){
        Tile t1 = getTile("W");
        t1.placeMeeple(p1.getMeeple(),S);
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("T");
        t2.rotateCCW();
        t2.rotateCCW();
        assertTrue(testBoard.placeTile(t2,72,71));
    }

    @Test
    public void testRoadTie(){
        Tile t1 = getTile("J");
        t1.placeMeeple(p1.getMeeple(),S);
        assertTrue(testBoard.placeTile(t1,70,71));
        Tile t2 = getTile("U");
        t2.placeMeeple(p2.getMeeple(),N);
        assertTrue(testBoard.placeTile(t2,71,72));
        Tile t3 = getTile("V");
        assertTrue(testBoard.placeTile(t3,70,72));

        Tile t4 = getTile("V");
        t4.rotateCW();
        assertTrue(testBoard.placeTile(t4,72,72));
        Tile t5 = getTile("V");
        t5.rotateCCW();
        t5.rotateCCW();
        assertTrue(testBoard.placeTile(t5,72,71));
    }

    @Test
    public void testUnplaceableTile(){
        Tile t1 = getTile("C");
        assertTrue(!testBoard.canBePlaced(t1,71,72));
    }
}
