package edu.cmu.cs.cs214.hw4.core;

/**
 * enumerations for the types of Features that are seen in the
 * carcassonne board game.
 */
public enum FeatureType {
    CITY(2),ROAD(1),FIELD(0),MONASTERY(9);

    private int scoreValue;

    FeatureType(int value){this.scoreValue = value;}

    public int getScoreValue(){return scoreValue;}
}
