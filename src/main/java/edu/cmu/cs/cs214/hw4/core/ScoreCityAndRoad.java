package edu.cmu.cs.cs214.hw4.core;

import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;
import java.util.List;


import static edu.cmu.cs.cs214.hw4.core.FeatureType.CITY;

/**
 * class for scoring cities and roads, and in the future possibly monasterys.
 */
public class ScoreCityAndRoad {
    private RowColHelper logic = new RowColHelper();

    /**
     * method that updates the players score, when given a List of connected segments,
     * this method handles the scoring for cities and roads.
     * @param connectedSegments list of the connected segments, essentially the tiles and
     *                          features that are apart of this entire feature.
     * @param players list of the players in the game, and the ones that should be
     *                scored.
     * @param endGame boolean value for if this should score the segment with unfinished game logic.
     */
    public void scoreFeature(List<FeatureLink> connectedSegments, List<Player> players, boolean endGame){
        if(connectedSegments.size() == 0){
            return;
        }
        Set<Integer> seenTiles = new HashSet<>();
        Map<Integer, Integer> playerMeeples = new HashMap<>();
        int totalScore = 0;
        int featureScore = connectedSegments.get(0).getSegmentScore();
        int hasShieldScore = 2;
        if(endGame && featureScore == CITY.getScoreValue()){
            featureScore = 1;
            hasShieldScore = 1;
        }
        for(FeatureLink connections: connectedSegments){
            Segment curSeg = connections.getSegment();
            int connCol = connections.getCol();
            int connRow = connections.getRow();
            seenTiles.add((logic.getUniqueHash(connRow,connCol)) * curSeg.hashCode());
            if(curSeg.hasShield()){
                totalScore += hasShieldScore;
            }
            if(curSeg.hasMeeple()){
                Meeple curMeeple = curSeg.removeMeeple();
                returnMeeple(curMeeple,players);
                int playerId = curMeeple.getMeeplePlayer();
                playerMeeples.putIfAbsent(playerId,0);
                playerMeeples.put(playerId,playerMeeples.get(playerId) + 1);
            }
        }
        totalScore += featureScore * seenTiles.size();
        int maxMeeples = 0;
        for(Integer key:playerMeeples.keySet()){
            if(playerMeeples.get(key) > maxMeeples){
                maxMeeples = playerMeeples.get(key);
            }
        }

        for(Integer playerId: playerMeeples.keySet()){
            if(playerMeeples.get(playerId) == maxMeeples){
                updatePlayerScore(players,playerId,totalScore);
            }
        }
    }

    /**
     * helper function that will return a meeple to a player.
     * @param meeple meeple to return
     * @param players list of players, so the meeple can be returned to
     *                the correct player.
     */
    public void returnMeeple(Meeple meeple, List<Player> players){
        for(Player player: players){
            if(player.getPlayerId() == meeple.getMeeplePlayer()){
                player.returnMeeple(meeple);
                return;
            }
        }
    }

    /**
     * method for updating a players score.
     * @param players list of players
     * @param id id of the player whos score is being updated
     * @param update number to update the players score by
     */
    public void updatePlayerScore(List<Player> players, int id, int update){
        for(Player player: players){
            if(player.getPlayerId() == id){
                player.updateScore(update);
            }
        }
    }
}
