package edu.cmu.cs.cs214.hw4.core;

/**
 * class that represents the meeple piece in the game
 * of carcassonne.
 */
public class Meeple {
    private int meeplePlayer;

    /**
     * constructor that simply creates a meeple given the player ID
     * of the player who will own this meeple.
     * @param playerId integer number that represents the playerID.
     */
    public Meeple(int playerId){
        meeplePlayer = playerId;
    }

    /**
     * method that returns the playerID of the player that owns
     * this meeple.
     * @return an integer number that is the player ID of this meeple.
     */
    public int getMeeplePlayer(){
        return meeplePlayer;
    }
}
