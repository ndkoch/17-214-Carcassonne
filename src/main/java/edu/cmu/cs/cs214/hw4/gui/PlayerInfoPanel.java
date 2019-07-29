package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.Player;

import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;

/**
 * class that extends the JPanel class and holds all of the
 * player information in it.
 */
public class PlayerInfoPanel extends JPanel {
    private static final String[] COLUMN_TITLES = {"Player #", "Meeples Left","Score"};
    private static final String[] PLAYER_COLORS = {" (Red)", " (Blue)", " (Green)", " (Yellow)", " (Purple)"};

    private Carcassonne game;
    private String[][] playerData;
    private int numPlayers;
    private DefaultTableModel tableModel;

    /**
     * constructor for the player information, which simply
     * creates the tabel model and the JTabel that the users
     * information is stored in.
     * @param game main game that will be referenced throughout
     */
    public PlayerInfoPanel(Carcassonne game){
        this.game = game;
        intitializePlayerData();
        tableModel = new DefaultTableModel(playerData,COLUMN_TITLES){
            @Override
            public boolean isCellEditable(int row, int col) {return false;}
        };
        JTable playerTable = new JTable(tableModel);
        this.setLayout(new BorderLayout());
        JScrollPane tabelPane = new JScrollPane(playerTable);
        this.add(tabelPane,BorderLayout.CENTER);
    }

    /**
     * method that updates the players information, so that the
     * changes can be displayed on the gui.
     */
    public void updatePlayerInfo(){
        intitializePlayerData();
        setPlayerData();
        tableModel.setDataVector(playerData,COLUMN_TITLES);
    }

    /**
     * private method that sets the players data. These changes will eventually
     * be seen in the actual display.
     */
    private void setPlayerData(){
        for(int i = 0; i < numPlayers;i++){
            Player player = game.getPlayers().get(i);
            if(player.getPlayerId() == game.getCurrentPlayer().getPlayerId()){
                playerData[i][0] = player.getPlayerId() + PLAYER_COLORS[i] + " (current player)";
                playerData[i][1] = Integer.toString(player.numMeeples());
                playerData[i][2] = Integer.toString(player.getScore());
            } else {
                playerData[i][0] = player.getPlayerId() + PLAYER_COLORS[i];
                playerData[i][1] = Integer.toString(player.numMeeples());
                playerData[i][2] = Integer.toString(player.getScore());
            }
        }
    }

    /**
     * method for initializing the player data, used when restarting a game,
     * and in the constructor function.
     */
    private void intitializePlayerData(){
        numPlayers = game.getNumPlayers();
        playerData = new String[numPlayers][COLUMN_TITLES.length];
        setPlayerData();
    }
}
