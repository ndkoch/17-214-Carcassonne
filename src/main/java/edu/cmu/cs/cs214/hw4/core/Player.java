package edu.cmu.cs.cs214.hw4.core;

import java.util.Stack;

/**
 * Class that represents the player in the game of Carcassonne.
 */
public class Player {
    private static final int TOTAL_MEEPLES = 7;
    private int playerId;
    private Stack<Meeple> meeples = new Stack<>();
    private int score;

    /**
     * constructor method that initializes the players meeples
     * and sets their ID number.
     * @param playerId integer number to differentiate players.
     */
    public Player(int playerId){
        this.playerId = playerId;
        initializeMeeples();
        score = 0;
    }

    /**
     * method that initialzes the stack of meeples, each player starts
     * with seven.
     */
    private void initializeMeeples(){
        for(int i = 0; i < TOTAL_MEEPLES; i++){
            meeples.push(new Meeple(playerId));
        }
    }

    /**
     * method for checking if the player has any meeples left.
     * @return a boolean value for if the player has any meeples.
     */
    public boolean hasMeeples(){return !meeples.empty();}

    /**
     * method that returns one of this players meeples.
     * @return a meeple from off the top of the stack.
     */
    public Meeple getMeeple(){
        if(!hasMeeples()){
            throw new IllegalArgumentException("Player has no Meeples left!");
        }
        return meeples.pop();
    }

    /**
     * method for returning the ID of the player
     * @return integer value of the player
     */
    public int getPlayerId() {
        return playerId;
    }

    /**
     * method that returns a meeple to the player by pushing it to
     * the top of the stack.
     * @param meeple meeple to be returned.
     */
    public void returnMeeple(Meeple meeple){
        meeples.push(meeple);
    }

    /**
     * method that updates the score of the player
     * @param update score that the user should be updated with.
     */
    public void updateScore(int update){
        this.score += update;
    }

    public int getScore(){return score;}

    public int numMeeples(){return meeples.size();}
}
