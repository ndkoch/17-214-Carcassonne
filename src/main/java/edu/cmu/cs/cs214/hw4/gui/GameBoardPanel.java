package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.GuiImageHelper;
import edu.cmu.cs.cs214.hw4.core.GuiTileHelper;
import edu.cmu.cs.cs214.hw4.core.Tile;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.image.BufferedImage;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.io.IOException;

/**
 * Gameboard Panel that handles drawing the tiles, placing the
 * tiles, and scoring the players. Is a 2D array of Jbuttons that
 * will resize when necessary.
 */
public class GameBoardPanel extends JPanel {
    // constants
    private static final int TILE_OFFSET = 90;
    private static final int BOARD_SIZE = 143;
    private static final int START_UPPER = 72;
    private static final int START_LOWER = 70;
    private static final int BOARD_MIDDLE = 71;

    private Carcassonne game;
    private JButton[][] buttonBoard = new JButton[BOARD_SIZE][BOARD_SIZE];
    private JPanel resizePanel;
    private int curRowUpperBound;
    private int curRowLowerBound;
    private int curColUpperBound;
    private int curColLowerBound;
    private TileDisplayPanel tilePanel;
    private PlayerInfoPanel playerInfoPanel;
    private MeepleChoiceSelectorPanel meepleChoicePanel;
    private GuiImageHelper imgHelper = new GuiTileHelper();
    private BufferedImage[][] noMeepleImg = new BufferedImage[BOARD_SIZE][BOARD_SIZE];

    /**
     * constructor function for the panel, simply initializes the buttons
     * and sets the initial grid width.
     * @param game current reference to the carcassonne game.
     */
    public GameBoardPanel(Carcassonne game){
        this.game = game;
        initializeButtons();
        resizePanel = new JPanel();
        setResizePanel(START_UPPER,START_LOWER,START_UPPER,START_LOWER);
        add(resizePanel);
    }

    /**
     * private method for resizing the panel inside of the larger class
     * which is also a panel
     * @param rUpper upper limit for the row
     * @param rLower lower limit for the row
     * @param cUpper upper limit for the col
     * @param cLower lower limit for the col
     */
    private void setResizePanel(int rUpper, int rLower, int cUpper, int cLower){
        curRowUpperBound = rUpper;
        curRowLowerBound = rLower;
        curColUpperBound = cUpper;
        curColLowerBound = cLower;
        resizePanel.setLayout(new GridLayout((rUpper-rLower)+1,(cUpper-cLower)+1));
        setButtons();
    }

    /**
     * private method for making sure that the right buttons are being displayed
     * in the correct spot on the resizeable panel, which uses the gridlayout
     * layout manager.
     */
    private void setButtons(){
        for(int i = curRowLowerBound; i <= curRowUpperBound;i++){
            for(int j = curColLowerBound; j <= curColUpperBound; j++){
                resizePanel.add(buttonBoard[i][j]);
            }
        }
    }

    /**
     * method that defines the action performed function for all of the buttons
     * and initializes the main board of buttons with the data.
     */
    private void initializeButtons(){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                if(j == BOARD_MIDDLE && i == BOARD_MIDDLE){
                    JButton middleTile = new JButton();
                    middleTile.setPreferredSize(new Dimension(TILE_OFFSET, TILE_OFFSET));
                    try {
                        BufferedImage img = imgHelper.getTileImage('U');
                        ImageIcon icon = new ImageIcon(img);
                        middleTile.setIcon(icon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    buttonBoard[i][j] = middleTile;
                    continue;
                }
                JButton tileButton = new JButton();
                tileButton.setPreferredSize(new Dimension(TILE_OFFSET, TILE_OFFSET));
                tileButton.addActionListener(event ->{
                    int selRow = 0;
                    int selCol = 0;
                    for(int k = 0; k < BOARD_SIZE; k++){
                        for(int z = 0; z < BOARD_SIZE; z++){
                            if(buttonBoard[k][z] == event.getSource()){
                                selRow = k;
                                selCol = z;
                            }
                        }
                    }
                    if(!game.placeTile(selRow,selCol)){
                        return;
                    }
                    ImageIcon icon = new ImageIcon(tilePanel.getCurImg());
                    tileButton.setIcon(icon);
                    noMeepleImg[selRow][selCol] = tilePanel.getNoMeepleImg();
                    for(int a = 0; a < BOARD_SIZE; a++){
                        for(int b = 0; b < BOARD_SIZE; b++){
                            Tile tile = game.getTile(a,b);
                            if(tile != null && tile.getSegmentWithMeeple() == null && noMeepleImg[a][b] != null){
                                ImageIcon noMeepleIcon = new ImageIcon(noMeepleImg[a][b]);
                                buttonBoard[a][b].setIcon(noMeepleIcon);
                            }
                        }
                    }
                    game.draw();
                    game.updatePlayer();
                    meepleChoicePanel.updateButtons();
                    playerInfoPanel.updatePlayerInfo();
                    try {
                        tilePanel.setImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // resize the board
                    resizeBoard(selRow,selCol);
                });
                buttonBoard[i][j] = tileButton;
            }
        }
    }

    /**
     * private method for resizing the board, given a selected row and col.
     * @param selRow selected row on the game board.
     * @param selCol selected column on the game board.
     */
    private void resizeBoard(int selRow, int selCol){
        if(selRow == curRowUpperBound){
            setResizePanel(curRowUpperBound+1,curRowLowerBound,
                    curColUpperBound,curColLowerBound);
        }
        if(selRow == curRowLowerBound){
            setResizePanel(curRowUpperBound,curRowLowerBound-1,
                    curColUpperBound,curColLowerBound);
        }
        if(selCol == curColUpperBound){
            setResizePanel(curRowUpperBound,curRowLowerBound,
                    curColUpperBound+1,curColLowerBound);
        }
        if(selCol == curColLowerBound){
            setResizePanel(curRowUpperBound,curRowLowerBound,
                    curColUpperBound,curColLowerBound-1);
        }
    }

    /**
     * method for setting the current tile display, so it can be updated
     * in the listener function for the buttons on the board.
     * @param t tile panel display
     */
    public void setTilePanel(TileDisplayPanel t){
        tilePanel = t;
    }

    /**
     * another method for setting the player information panel, so that
     * is can be updated in the listener function.
     * @param p player information panel
     */
    public void setPlayerInfoPanel(PlayerInfoPanel p){
        playerInfoPanel = p;
    }

    /**
     * last method for assigning the meeple selector panel,
     * again so it can be updated in the listener function.
     * @param m meeple selector panel
     */
    public void setMeepleSelector(MeepleChoiceSelectorPanel m){
        meepleChoicePanel = m;
    }
}
