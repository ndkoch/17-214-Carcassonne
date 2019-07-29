package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * main class that controls all aspects of the game, is basically
 * like the controller of the game.
 */
public class Carcassonne {
    private List<Player> players;
    private int numPlayers;
    private int curPlayer = 0;
    private TileDeck gameDeck = new TileDeck();
    private Board gameBoard = new Board(gameDeck.getStartTile());
    private static final int MAX_PLAYERS = 5;
    private Tile curTile;

    /**
     * constructor that does not need the current number of players
     * to be known.
     */
    public Carcassonne(){
        this.numPlayers = 0;
        this.players = new ArrayList<>();
    }

    /**
     * method for initializing the players
     * @param numPlayers number of players in the game.
     */
    public void setPlayers(int numPlayers){
        this.numPlayers = numPlayers;
        if(players.size() != 0){
            players = new ArrayList<>();
        }
        if(numPlayers < 2 || numPlayers > MAX_PLAYERS){
            throw new IllegalArgumentException("Can only play Carcassonne with 2-5 players!");
        }
        for(int i = 0; i < numPlayers; i++){
            players.add(new Player(i + 1));
        }
    }

    /**
     * method for resetting the game.
     */
    public void reset(){
        gameDeck = new TileDeck();
        gameBoard = new Board(gameDeck.getStartTile());
        curPlayer = 0;
        curTile = null;
    }

    /**
     * method for getting the number of players in the game
     * @return the number of players
     */
    public int getNumPlayers(){return numPlayers;}

    /**
     * method for getting the list of players
     * @return the list of players.
     */
    public List<Player> getPlayers(){return players;}

    /**
     * method for placing a meeple
     * @param segment the given segment for the meeple to be placed on.
     */
    public void placeMeeple(Segment segment){
        Meeple meeple = getCurrentPlayer().getMeeple();
        curTile.placeMeeple(meeple,segment.getLocations().get(0));
    }

    /**
     * method for removing the meeples and replacing them back into the users
     * inventory.
     */
    public void removeMeepleWithReplacement(){
        Meeple placedMeeple = curTile.removeMeeples();
        for(Player p : players){
            if(p.getPlayerId() == placedMeeple.getMeeplePlayer()){
                p.returnMeeple(placedMeeple);
            }
        }
    }

    /**
     * method for updating the index of the current player.
     */
    public void updatePlayer(){
        curPlayer = (curPlayer + 1) % numPlayers;
    }

    /**
     * method for drawing a tile from the deck.
     */
    public void draw(){
        if(gameDeck.isEmpty()){
            gameBoard.scoreFinishedGame(players);
            return;
        }
        curTile = gameDeck.draw();
    }

    /**
     * method for getting the current tile
     * @return the current tile to be placed.
     */
    public Tile getCurrentTile(){return curTile;}

    /**
     * method for rotating a tile clockwise
     */
    public void rotateClockWise(){curTile.rotateCW();}

    /**
     * method for getting the player object of the current player
     * @return the current player.
     */
    public Player getCurrentPlayer(){return players.get(curPlayer);}

    /**
     * method for placing a tile at a given row and col.
     * @param row row for the tile to be placed.
     * @param col col for the tile to be placed.
     * @return true if the tile can be placed, or false if it cannot
     * at the given location.
     */
    public boolean placeTile(int row, int col){
        if(gameBoard.placeTile(curTile,row,col)){
            gameBoard.scorePlayers(players,row,col);
            return true;
        }
        return false;
    }

    /**
     * method for getting a tile a specific row and col
     * @param row row
     * @param col col
     * @return the tile at the given row and col
     */
    public Tile getTile(int row, int col){
        return gameBoard.getTile(row,col);
    }
}
