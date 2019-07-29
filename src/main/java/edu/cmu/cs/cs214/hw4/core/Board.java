package edu.cmu.cs.cs214.hw4.core;

import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import static edu.cmu.cs.cs214.hw4.core.FeatureType.FIELD;
import static edu.cmu.cs.cs214.hw4.core.FeatureType.MONASTERY;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.N;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.E;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.S;
import static edu.cmu.cs.cs214.hw4.core.SegmentLocation.W;

/**
 * Board class that represents the carcassonne game board, and handles
 * the logic of placing tiles on the board.
 * SIDE NOTE: current center of the board is 71,71
 */
public class Board {
    private static final int BOARD_WIDTH = 143;
    private static final int BOARD_CENTER = 71;
    private Tile[][] gameBoard = new Tile[BOARD_WIDTH][BOARD_WIDTH];
    private static final SegmentLocation[] LOCATIONS = {E,S,W,N};
    private static final int NUM_DIRECTIONS = 4;
    private RowColHelper rcLog = new RowColHelper();
    private ScoreCityAndRoad scoreCityAndRoads = new ScoreCityAndRoad();
    private List<FeatureLink> tilesWithMonasterys = new ArrayList<>();
    /**
     * constructor for creating a new board, which takes in the starting tile.
     * @param startTile first tile that will be placed in the center of the board.
     */
    public Board(Tile startTile){
        gameBoard[BOARD_CENTER][BOARD_CENTER] = startTile;
    }

    /**
     * method for placing the tile on the board, by first ensuring that
     * the tile placement is valid and then updating the board.
     * @param tile tile object to be placed onto the board
     * @param row row number where the tile should be placed
     * @param col col number where the tile should be placed.
     * @return a boolean value that represents whether or not the placement
     * was successful.
     */
    public boolean placeTile(Tile tile, int row, int col){
        if(!isValidPlacement(tile,row,col)){
            return false;
        }
        gameBoard[row][col] = tile;
        if(tile.hasMonastery()){
            if(tile.getSegmentWithMeeple() != null && tile.getSegmentWithMeeple().getType() == FIELD){
                tilesWithMonasterys.add(new FeatureLink(row,col));
            }
        }
        return true;
    }

    /**
     * method for updating the players scores after a tile has just been placed.
     * @param players list of players who score will be updated.
     * @param row row of the tile just placed.
     * @param col col of the tile just placed.
     */
    public void scorePlayers(List<Player> players, int row, int col){
        Tile recentlyPlacedTile = gameBoard[row][col];
        scoreGivenTile(recentlyPlacedTile,row,col,players,false);
        scoreMonastery(players,false);
    }

    /**
     * method for scoring the finished game, looks for all the tiles
     * that still have meeples on them and then scores the board, with endgame logic
     * @param players list of players to update their score.
     */
    public void scoreFinishedGame(List<Player> players){
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                if(gameBoard[i][j] != null){
                    Tile curTile = gameBoard[i][j];
                    if(curTile.getSegmentWithMeeple() != null){
                        Segment curSeg = curTile.getSegmentWithMeeple();
                        if(curTile.hasMonastery() && curSeg.getType() == FIELD){
                            continue;
                        }
                        List<FeatureLink> connections  = new ArrayList<>();
                        getConnectingFeatures(curSeg,i,j,new HashSet<>(),connections);
                        scoreCityAndRoads.scoreFeature(connections,players,true);
                    }
                }
            }
        }
        scoreMonastery(players,true);
    }

    /**
     * method for scoring a given tile, ie: the tile has just been placed, and
     * now the board will be checked to see if any of the segments on this
     * tile have completed a feature, and if so score it.
     * @param tile tile that was just placed.
     * @param row row of the tile that was just placed.
     * @param col column of the tile that was just placed.
     * @param players list of players, so their score can be updated.
     * @param endGame boolean value for whether or not to score with endgame
     *                logic.
     */
    private void scoreGivenTile(Tile tile, int row, int col, List<Player> players,boolean endGame){
        for(Segment segment: tile.getSegments()){
            if(segment.getType() != FeatureType.FIELD){
                List<FeatureLink> connectedSegments = new ArrayList<>();
                if(completesFeature(segment,row,col,new HashSet<>())){
                    getConnectingFeatures(segment,row,col,new HashSet<>(),connectedSegments);
                    scoreCityAndRoads.scoreFeature(connectedSegments,players,endGame);
                }
            }
        }
    }

    /**
     * method for scoring the monastery, this is handled differently because it
     * needs to know about the game board and the attributes it has.
     * @param players list of players in the game.
     * @param endGame boolean value for whether or not this should be scored
     *                with game over logic.
     */
    private void scoreMonastery(List<Player> players, boolean endGame){
        for(FeatureLink monasteryTile: tilesWithMonasterys){
            int monRow = monasteryTile.getRow();
            int monCol = monasteryTile.getCol();
            Tile monTile = gameBoard[monRow][monCol];
            int numSurroundingTiles = numSurrounded(monRow,monCol);
            if(!endGame && numSurroundingTiles == MONASTERY.getScoreValue()){
                Meeple curMeeple = monTile.removeMeeples();
                if(curMeeple != null){
                    scoreCityAndRoads.updatePlayerScore(players,curMeeple.getMeeplePlayer(),
                            MONASTERY.getScoreValue());
                    scoreCityAndRoads.returnMeeple(curMeeple,players);
                }
            } else if(endGame){
                Meeple curMeeple = monTile.removeMeeples();
                if(curMeeple != null){
                    scoreCityAndRoads.updatePlayerScore(players,curMeeple.getMeeplePlayer(),
                            numSurroundingTiles);
                    scoreCityAndRoads.returnMeeple(curMeeple,players);
                }
            }
        }
    }

    /**
     * method that when given a tile and its location on the board,
     * determines if it is valid to place it there.
     * @param tile tile that is going to be placed
     * @param row row number where it will be placed
     * @param col col number for where it should be placed
     * @return true if the tile can be placed at that location, or false if it cannot.
     */
    private boolean isValidPlacement(Tile tile, int row, int col){
        if(rcLog.rowColNotInBounds(row, col)){
            return false;
        }
        if(gameBoard[row][col] != null){
            return false;
        }
        Tile[] adjacentTiles = getAdjacentTiles(row,col);
        boolean seenTile = false;
        boolean validSegs = true;
        for(int i = 0; i < NUM_DIRECTIONS; i++){
            if(adjacentTiles[i] != null){
                seenTile = true;
                Segment curSeg = tile.getDirectionSegment(LOCATIONS[i]);
                Segment adjSeg = adjacentTiles[i].getDirectionSegment(rcLog.getInverseDirection(LOCATIONS[i]));
                validSegs = (validSegs && (curSeg.getType() == adjSeg.getType()));
            }
        }

        Segment meepleSeg = tile.getSegmentWithMeeple();
        if(seenTile && validSegs) {
            if(meepleSeg != null && meepleSeg.getType() != FeatureType.FIELD){
                return isMeepleTileValid(meepleSeg,row,col,new HashSet<>());
            }
            return true;
        }
        return false;
    }

    /**
     * recursive method for checking the validity of a tile being placed, that
     * contains a meeple.
     * @param segment the initial segment that has the meeple placed on it.
     * @param row row number of the tile
     * @param col col number of the tile
     * @param seenSegments hash set that contains the segments that this recursive call has
     *                     seen. This uses the segment hashCode along with the row and col
     *                     information to ensure that each entry is unique.
     * @return a boolean value for whether or not the tile can be placed with a meeple,
     * on that road.
     */
    private boolean isMeepleTileValid(Segment segment, int row, int col, Set<Integer> seenSegments){
        seenSegments.add(row*col*segment.hashCode());
        for(SegmentLocation dir:segment.getLocations()){
            if(gameBoard[rcLog.getNewRow(row,dir)][rcLog.getNewCol(col,dir)] != null){
                Tile adjTile = gameBoard[rcLog.getNewRow(row,dir)][rcLog.getNewCol(col,dir)];
                Segment connectingSegment = adjTile.getDirectionSegment(rcLog.getInverseDirection(dir));
                if(connectingSegment.hasMeeple()){
                    return false;
                }
                int newRow = rcLog.getNewRow(row,dir);
                int newCol = rcLog.getNewCol(col,dir);
                Integer nextSegmentSignature = newRow*newCol*connectingSegment.hashCode();
                if(seenSegments.contains(nextSegmentSignature)){
                    continue;
                }
                seenSegments.add(nextSegmentSignature);
                return isMeepleTileValid(connectingSegment,newRow,newCol, seenSegments);
            }
        }
        return true;
    }

    /**
     * returns an array of the tiles surrounding a given row,col.
     * will always be in the format of a length 4 array of Tiles that
     * follow this order: [right, down, left, up]
     * @param row row to check on the board
     * @param col col to check on the board
     * @return an array of length 4, always of length 4 that has the
     * tiles in the following adjacent spots: [right, down, left, up]
     */
    private Tile[] getAdjacentTiles(int row, int col) {
        Tile[] adjacentTiles = new Tile[NUM_DIRECTIONS];
        int[] directions = {1, -1};
        int index = 0;
        for(int dir:directions){
            int newCol = col + dir;
            int newRow = row + dir;
            adjacentTiles[index] = gameBoard[row][newCol];
            index++;
            adjacentTiles[index] = gameBoard[newRow][col];
            index++;
        }
        return adjacentTiles;
    }

    /**
     * method that checks on a given tile placement, if there are any new updated structures,
     * and returns true if their is, and false otherwise.
     * @param curSegment segment to start searching from.
     * @param row row number of the tile that is being placed.
     * @param col col number of the tile that is being placed.
     * @param seenSegments set of values that represent the segments that
     *                     have been seen on the recursive traversal of the board.
     * @return a boolean value that represents whether or not this segment
     * completes a feature. Also updates the connected segments list which holds the information
     * regarding the tiles covered and segments affected by this tile.
     */
    public boolean completesFeature(Segment curSegment, int row, int col,Set<Integer> seenSegments){
        Integer curSegmentSignature = (rcLog.getUniqueHash(row,col)) * curSegment.hashCode();
        if(seenSegments.contains(curSegmentSignature)){
            return true;
        }
        seenSegments.add(curSegmentSignature);
        boolean completeStructure = false;
        for(SegmentLocation dir: curSegment.getLocations()){
            int newRow = rcLog.getNewRow(row,dir);
            int newCol = rcLog.getNewCol(col,dir);
            if(gameBoard[newRow][newCol] == null){
                return false;
            }
            Tile adjTile = gameBoard[newRow][newCol];
            Segment adjSegment = adjTile.getDirectionSegment(rcLog.getInverseDirection(dir));
            Integer newSignature = adjSegment.hashCode() * rcLog.getUniqueHash(newRow,newCol);
            if(seenSegments.contains(newSignature)){
                completeStructure = true;
                continue;
            }
            completeStructure = completesFeature(adjSegment,newRow,newCol,seenSegments);
            if(!completeStructure){
                return false;
            }
        }
        return completeStructure;
    }

    /**
     * method for getting the connecting segments to a current segment, on a tile.
     * Useful for scoring when the game is over.
     * @param curSegment current segment to get the connecting features for.
     * @param row row of the current tile
     * @param col column of the current tile
     * @param seenSegments segments that have been seen during the recursion.
     * @param connectedSegments initially empty list that is populated throughout the search.
     */
    private void getConnectingFeatures(Segment curSegment, int row, int col,
               Set<Integer> seenSegments, List<FeatureLink> connectedSegments){
        Integer curSegmentSignature = (rcLog.getUniqueHash(row,col)) * curSegment.hashCode();
        if(seenSegments.contains(curSegmentSignature)){
            return;
        }
        seenSegments.add(curSegmentSignature);
        FeatureLink connection = new FeatureLink(row,col,curSegment);
        connectedSegments.add(connection);
        for(SegmentLocation dir: curSegment.getLocations()){
            int newRow = rcLog.getNewRow(row,dir);
            int newCol = rcLog.getNewCol(col,dir);
            if(gameBoard[newRow][newCol] == null){
                continue;
            }
            Tile adjTile = gameBoard[newRow][newCol];
            Segment adjSegment = adjTile.getDirectionSegment(rcLog.getInverseDirection(dir));
            Integer newSignature = adjSegment.hashCode() * rcLog.getUniqueHash(newRow,newCol);
            if(seenSegments.contains(newSignature)){
                continue;
            }
            getConnectingFeatures(adjSegment,newRow,newCol,seenSegments, connectedSegments);
        }
    }

    /**
     * method that finds out the number of tiles that surround
     * the given row and col.
     * @param row row of the tile
     * @param col column of the tile
     * @return number of tiles that are around this row and col.
     */
    private int numSurrounded(int row, int col){
        int count = 0;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(gameBoard[row+i][col+j] != null){
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * method for checking that a given tile can be placed on the current board.
     * @param tile tile to be checked
     * @param row row of the tile
     * @param col column of the tile
     * @return true if the tile can be placed, and false otherwise.
     */
    public boolean canBePlaced(Tile tile, int row, int col){
        for(int i = 0; i < BOARD_WIDTH; i++){
            for(int j = 0; j < BOARD_WIDTH; j++){
                if(isValidPlacement(tile,row,col)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * method for getting a tile at a given row and col
     * @param row row
     * @param col col
     * @return the tile at the given row and col
     */
    public Tile getTile(int row, int col){
        return gameBoard[row][col];
    }
}