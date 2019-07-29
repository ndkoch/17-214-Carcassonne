package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.assertNull;

public class TestScoring {

    private Tile getTile(String tileLetter){
        TileDeck tilePile = new TileDeck();
        Tile t = tilePile.draw();
        while(!t.getTileLetter().equals(tileLetter)){
            t = tilePile.draw();
        }
        return t;
    }
    private Board testBoard = new Board(getTile("U"));

    private List<Player> createPlayers(int num){
        List<Player> players = new ArrayList<>();
        for(int i = 0; i < num; i++){
            players.add(new Player(i+1));
        }
        return players;
    }

    private void printPlayerScores(List<Player> players){
        for(Player player: players){
            System.out.println("Player " + player.getPlayerId() + " has a score of " + player.getScore());
        }
    }

    @Test
    public void testUniqueHash(){
        RowColHelper logic = new RowColHelper();
        assertEquals(logic.getUniqueHash(72,72),logic.getUniqueHash(72,72));
        assertTrue(logic.getUniqueHash(71,72) != logic.getUniqueHash(72,71));
    }

    @Test
    public void testScoringCityFeature(){
        List<Player> players = createPlayers(3);
        Tile t1 = getTile("E");
        t1.rotateCW();
        assertTrue(testBoard.placeTile(t1,71,72));
        testBoard.scorePlayers(players,71,72);

        Tile t2 = getTile("E");
        t2.rotateCCW();
        t2.placeMeeple(players.get(1).getMeeple(),SegmentLocation.W);
        assertTrue(testBoard.placeTile(t2,71,73));
        assertEquals(players.get(1).numMeeples(),6);
        testBoard.scorePlayers(players,71,73);
        assertEquals(players.get(1).numMeeples(),7);
        assertEquals(players.get(1).getScore(),4);
        assertNull(t2.getSegmentWithMeeple());
    }

    @Test
    public void testTiedCityScoring(){
        List<Player> players = createPlayers(3);
        Tile t1 = getTile("K");
        t1.placeMeeple(players.get(0).getMeeple(),SegmentLocation.E);
        assertTrue(testBoard.placeTile(t1,72,71));
        testBoard.scorePlayers(players,72,71);
        assertEquals(players.get(0).getScore(),0);

        Tile t2 = getTile("E");
        t2.rotateCCW();
        t2.rotateCCW();
        t2.placeMeeple(players.get(1).getMeeple(),SegmentLocation.S);
        assertTrue(testBoard.placeTile(t2,71,72));
        testBoard.scorePlayers(players,72,71);
        assertEquals(players.get(0).getScore(),0);
        assertEquals(players.get(1).getScore(),0);

        Tile t3 = getTile("O");
        assertTrue(testBoard.placeTile(t3,72,72));
        testBoard.scorePlayers(players,72,72);
        assertEquals(players.get(0).getScore(),8);
        assertEquals(players.get(1).getScore(),8);
    }

    @Test
    public void testCityScoreWithTileSameTileSegment(){
        List<Player> players = createPlayers(2);
        Tile t1 = getTile("K");
        assertTrue(testBoard.placeTile(t1,72,71));

        Tile t2 = getTile("N");
        t2.rotateCCW();
        assertTrue(testBoard.placeTile(t2,72,72));

        Tile t3 = getTile("N");
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,73,72));

        Tile t4 = getTile("N");
        assertTrue(testBoard.placeTile(t4,73,73));
        testBoard.scorePlayers(players,73,73);
        assertEquals(players.get(0).getScore(),0);
        assertEquals(players.get(1).getScore(),0);

        Tile t5 = getTile("F");
        t5.rotateCW();
        t5.placeMeeple(players.get(0).getMeeple(),SegmentLocation.S);
        assertTrue(testBoard.placeTile(t5,72,73));

        Tile t6 = getTile("R");
        t6.rotateCCW();
        assertTrue(testBoard.placeTile(t6,71,73));

        Tile t7 = getTile("E");
        t7.rotateCW();
        assertTrue(testBoard.placeTile(t7,71,72));
        testBoard.scorePlayers(players,71,72);
        assertEquals(players.get(0).getScore(),0);
        assertEquals(players.get(1).getScore(),0);

        Tile t8 = getTile("E");
        t8.rotateCW();
        t8.rotateCW();
        assertTrue(testBoard.placeTile(t8,70,73));

        testBoard.scorePlayers(players,70,73);
        assertEquals(players.get(0).getScore(),18);
        assertEquals(players.get(1).getScore(),0);
    }

    @Test
    public void testThreePlayerCityScore(){
        List<Player> players = createPlayers(3);
        Tile t1 = getTile("K");
        t1.placeMeeple(players.get(0).getMeeple(),SegmentLocation.E);
        assertTrue(testBoard.placeTile(t1,72,71));

        Tile t7 = getTile("E");
        t7.rotateCW();
        t7.placeMeeple(players.get(2).getMeeple(),SegmentLocation.E);
        assertTrue(testBoard.placeTile(t7,71,72));

        Tile t2 = getTile("N");
        t2.rotateCCW();
        assertTrue(testBoard.placeTile(t2,72,72));

        Tile t5 = getTile("F");
        t5.rotateCW();
        t5.placeMeeple(players.get(0).getMeeple(),SegmentLocation.S);
        assertTrue(testBoard.placeTile(t5,72,73));

        Tile t3 = getTile("N");
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,73,72));

        Tile t4 = getTile("N");
        assertTrue(testBoard.placeTile(t4,73,73));

        Tile t6 = getTile("R");
        t6.rotateCCW();
        assertTrue(testBoard.placeTile(t6,71,73));

        Tile t8 = getTile("E");
        t8.rotateCW();
        t8.rotateCW();
        assertTrue(testBoard.placeTile(t8,70,73));

        testBoard.scorePlayers(players,70,73);
        assertEquals(players.get(0).getScore(),18);
        assertEquals(players.get(1).getScore(),0);
        assertEquals(players.get(2).getScore(),0);
    }

    @Test
    public void testScoringRoad(){
        List<Player> players = createPlayers(3);
        Tile t1 = getTile("K");
        assertTrue(testBoard.placeTile(t1,72,71));

        Tile t2 = getTile("D");
        t2.rotateCW();
        t2.placeMeeple(players.get(0).getMeeple(),SegmentLocation.W);
        assertTrue(testBoard.placeTile(t2,72,70));

        Tile t3 = getTile("O");
        assertTrue(testBoard.placeTile(t3,72,69));

        Tile t4 = getTile("X");
        assertTrue(testBoard.placeTile(t4,73,69));
        testBoard.scorePlayers(players,73,69);
        assertEquals(players.get(0).getScore(),0);

        Tile t5 = getTile("S");
        assertTrue(testBoard.placeTile(t5,70,71));
        testBoard.scorePlayers(players,70,71);
        assertEquals(players.get(0).getScore(),6);
        assertEquals(players.get(1).getScore(),0);
        assertEquals(players.get(2).getScore(),0);
    }

    @Test
    public void testScoringMonasteryAndUnfinishedStructures(){
        List<Player> players = createPlayers(2);
        Tile t1 = getTile("B");
        assertTrue(t1.placeMeeple(players.get(0).getMeeple(),SegmentLocation.N));
        assertTrue(testBoard.placeTile(t1,71,72));

        Tile t2 = getTile("V");
        t2.rotateCW();
        t2.placeMeeple(players.get(1).getMeeple(),SegmentLocation.N);
        assertTrue(testBoard.placeTile(t2,72,71));

        Tile t3 = getTile("E");
        t3.rotateCW();
        assertTrue(testBoard.placeTile(t3,72,72));
        testBoard.scorePlayers(players,72,72);
        assertEquals(players.get(0).getScore(),0);

        Tile t4 = getTile("E");
        t4.rotateCCW();
        t4.placeMeeple(players.get(0).getMeeple(),SegmentLocation.W);
        assertTrue(testBoard.placeTile(t4,72,73));
        testBoard.scorePlayers(players,72,73);
        assertEquals(players.get(0).getScore(),4);

        Tile t5 = getTile("B");
        assertTrue(t5.placeMeeple(players.get(1).getMeeple(),SegmentLocation.N));
        assertTrue(testBoard.placeTile(t5,71,73));

        Tile t6 = getTile("V");
        assertTrue(testBoard.placeTile(t6,70,71));

        Tile t7 = getTile("N");
        t7.rotateCW();
        assertTrue(testBoard.placeTile(t7,70,72));
        testBoard.scorePlayers(players,72,73);
        assertEquals(players.get(0).getScore(),4);
        assertEquals(players.get(1).getScore(),0);

        Tile t8 = getTile("R");
        t8.placeMeeple(players.get(0).getMeeple(),SegmentLocation.W);
        assertTrue(testBoard.placeTile(t8,70,73));
        testBoard.scorePlayers(players,70,73);
        assertEquals(players.get(0).getScore(),13);
        assertEquals(players.get(1).getScore(),0);

        Tile t9 = getTile("N");
        t9.rotateCCW();
        assertTrue(testBoard.placeTile(t9,70,74));

        Tile t10 = getTile("G");
        assertTrue(testBoard.placeTile(t10,71,74));
        assertEquals(players.get(0).getScore(),13);
        assertEquals(players.get(1).getScore(),0);

        Tile t11 = getTile("J");
        assertTrue(testBoard.placeTile(t11,72,74));
        testBoard.scorePlayers(players,72,74);
        assertEquals(players.get(0).getScore(),13);
        assertEquals(players.get(1).getScore(),9);

        testBoard.scoreFinishedGame(players);
        assertEquals(players.get(0).getScore(),18);
        assertEquals(players.get(1).getScore(),12);
    }

    @Test
    public void testScoringUnfinishedCity(){
        List<Player> players = createPlayers(2);
        Tile t1 = getTile("E");
        t1.rotateCCW();
        t1.rotateCCW();
        t1.placeMeeple(players.get(0).getMeeple(),SegmentLocation.S);
        assertTrue(testBoard.placeTile(t1,71,72));

        Tile t2 = getTile("D");
        t2.placeMeeple(players.get(1).getMeeple(),SegmentLocation.E);
        assertTrue(testBoard.placeTile(t2,72,71));
        testBoard.scoreFinishedGame(players);
        assertEquals(players.get(0).getScore(),1);
        assertEquals(players.get(1).getScore(),1);
    }

    @Test
    public void testPlayerHasCorrectNumberOfMeeples(){
        List<Player> players = createPlayers(2);
        assertEquals(players.get(0).numMeeples(),7);
        Tile t1 = getTile("E");
        t1.rotateCCW();
        t1.rotateCCW();
        t1.placeMeeple(players.get(0).getMeeple(),SegmentLocation.S);
        assertEquals(players.get(0).numMeeples(),6);
        assertTrue(testBoard.placeTile(t1,71,72));

        Tile t2 = getTile("D");
        t2.placeMeeple(players.get(0).getMeeple(),SegmentLocation.E);
        assertEquals(players.get(0).numMeeples(),5);
        assertTrue(testBoard.placeTile(t2,72,71));
        testBoard.scoreFinishedGame(players);
        assertEquals(players.get(0).numMeeples(),7);
    }

    @Test
    public void testScoringUnfinishedMonastery(){
        List<Player> players = createPlayers(2);
        Tile t1 = getTile("B");
        assertTrue(t1.placeMeeple(players.get(0).getMeeple(),SegmentLocation.N));
        assertTrue(testBoard.placeTile(t1,71,72));

        Tile t2 = getTile("V");
        t2.rotateCW();
        t2.placeMeeple(players.get(1).getMeeple(),SegmentLocation.N);
        assertTrue(testBoard.placeTile(t2,72,71));

        testBoard.scoreFinishedGame(players);
        assertEquals(players.get(0).getScore(),3);
        assertEquals(players.get(1).getScore(),2);
    }
}
