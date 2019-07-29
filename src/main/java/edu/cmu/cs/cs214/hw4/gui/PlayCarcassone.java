package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.IOException;

/**
 * main class that launches the gui, can be launched
 * by typing 'gradle run' in the terminal.
 */
public class PlayCarcassone {
    private static final int MAIN_WIDTH = 1000;
    private static final int MAIN_HEIGHT = 600;
    private static final int SCROLL_SPEED = 16;
    private static final String TITLE = "Carcassonne";
    private static final String NUM_PLAYERS = "# of Players";
    private static final String START = "Start";
    private static final int INPUT_WIDTH = 30;
    private static final int INPUT_HEIGHT = 45;
    private static final int NUM_ROWS = 3;
    private static final int NUM_COLS = 4;

    /**
     * main function to be run to play carcassonne
     * @param args input from the terminal
     */
    public static void main(String[] args){
        Carcassonne mainGame = new Carcassonne();

        // create the JFrame for the main Game
        JFrame mainFrame = new JFrame(TITLE);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setPreferredSize(new Dimension(MAIN_WIDTH,MAIN_HEIGHT));

        // create a new JPanel that displays the currentTile
        TileDisplayPanel tileDisplay = new TileDisplayPanel(mainGame);

        // create the player information panel
        PlayerInfoPanel playerInfo = new PlayerInfoPanel(mainGame);

        // create a panel that handles placing meeples
        MeepleChoiceSelectorPanel meepleSelector = new MeepleChoiceSelectorPanel(mainGame);
        tileDisplay.setSelectorPanel(meepleSelector);
        meepleSelector.setTileDisplay(tileDisplay);

        // create the game board panel
        GameBoardPanel boardPanel = new GameBoardPanel(mainGame);
        boardPanel.setTilePanel(tileDisplay);
        boardPanel.setMeepleSelector(meepleSelector);
        boardPanel.setPlayerInfoPanel(playerInfo);
        JScrollPane boardPane = new JScrollPane(boardPanel);
        boardPane.getHorizontalScrollBar().setUnitIncrement(SCROLL_SPEED);
        boardPane.getVerticalScrollBar().setUnitIncrement(SCROLL_SPEED);

        // create a JTextField and JButton that handles creating a new Game
        JTextField numPlayersInput = new JTextField(NUM_PLAYERS);
        JButton startGameButton = new JButton(START);
        startGameButton.addActionListener(event ->{
            mainGame.setPlayers(Integer.parseInt(numPlayersInput.getText()));
            mainGame.reset();
            mainGame.draw();
            try {
                tileDisplay.setImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
            playerInfo.updatePlayerInfo();
            meepleSelector.updateButtons();
        });
        JPanel startGamePanel = new JPanel();
        startGamePanel.setLayout(new FlowLayout());
        startGamePanel.add(numPlayersInput);
        startGamePanel.add(startGameButton);
        startGamePanel.setPreferredSize(new Dimension(INPUT_WIDTH,INPUT_HEIGHT));

        // create a JPanel that has the TileDisplay and the startGame
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(NUM_ROWS,NUM_COLS));
        mainPanel.add(startGamePanel);
        mainPanel.add(tileDisplay);
        mainPanel.add(playerInfo);
        //mainPanel.add(draw);
        mainPanel.add(meepleSelector);
        mainPanel.add(boardPane);

        // add the JPanel to the main frame.
        mainFrame.setContentPane(mainPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
