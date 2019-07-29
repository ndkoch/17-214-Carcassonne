package edu.cmu.cs.cs214.hw4.gui;


import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.GuiImageHelper;
import edu.cmu.cs.cs214.hw4.core.GuiTileHelper;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * main class that handles a lot of the image processing
 * of the current tile in the deck, and where the meeples are
 * placed on it.
 */
public class TileDisplayPanel extends JPanel {
    private static final int PANEL_WIDTH = 100;
    private static final int PANEL_HEIGHT = 120;
    private static final String ROTATE = "Rotate";
    private static final int ROTATE_WIDTH = 200;
    private static final int ROTATE_HEIGHT = 30;

    private Carcassonne game;
    private DisplayTile tileDisplay;
    private GuiImageHelper imgHelper = new GuiTileHelper();
    private MeepleChoiceSelectorPanel selectorPanel;

    /**
     * constructor that simply sets up the tile display panel
     * and the rotate button, along with adding the listener functionality
     * for the button
     * @param game game that is being referenced across all panels
     */
    public TileDisplayPanel(Carcassonne game){
        this.setPreferredSize(new Dimension(PANEL_WIDTH,PANEL_HEIGHT));
        this.game = game;
        tileDisplay = new DisplayTile();
        JButton rotate = new JButton(ROTATE);
        rotate.setPreferredSize(new Dimension(ROTATE_WIDTH,ROTATE_HEIGHT));
        rotate.addActionListener(event -> {
            tileDisplay.rotateImage();
            game.rotateClockWise();
            selectorPanel.updateButtons();
        });
        setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        add(tileDisplay);
        add(rotate);
    }

    /**
     * method for setting the selector panel, so that it can be referenced
     * inside of the listener function for the rotate button.
     * @param e selector panel to be referenced
     */
    public void setSelectorPanel(MeepleChoiceSelectorPanel e){
        selectorPanel = e;
    }

    /**
     * method for setting the image of the tile to be displayed on screen
     * @throws IOException if the given tile picture file cannot be found.
     */
    public void setImage() throws IOException {
        tileDisplay.resetMeepleInfo();
        tileDisplay.setImage(imgHelper.getTileImage(game.getCurrentTile().getTileLetter().charAt(0)));
    }

    /**
     * method for setting a meeple, AKA a circle on the tile
     */
    public void setMeeple(){
        tileDisplay.placeMeeple();
    }

    /**
     * method for removing the meeple or circle on the tile.
     */
    public void removeMeeple(){
        tileDisplay.removeMeeple();
    }

    /**
     * method that returns the current image on the button
     * @return the image currently displayed on the button.
     */
    public BufferedImage getCurImg(){
        return tileDisplay.curImg;
    }

    /**
     * method for getting the image on the tile when there
     * was no meeple placed on it
     * @return the image of the tile with no meeple.
     */
    public BufferedImage getNoMeepleImg(){
        return tileDisplay.getPreMeepleImg();
    }

    /**
     * class that actually extends the JPanel super class
     * and handles all the smaller logic involved in displaying
     * the image etc.
     */
    private class DisplayTile extends JButton{
        private final Color[] playerColors = {new Color(244, 28, 4), new Color(26, 52, 221),
                new Color(65, 244, 65), new Color(244, 229, 66),
                new Color(108, 52, 131)};

        private BufferedImage curImg;
        private BufferedImage preMeepleImg;
        private int numRotationsAfterMeeple = 0;
        private Boolean hasMeeple = false;

        /**
         * empty constructor
         */
        DisplayTile(){
        }

        /**
         * method for setting an image on the button.
         * @param newImg image the button should be set to.
         */
        public void setImage(BufferedImage newImg){
            curImg = newImg;
            ImageIcon imageIcon = new ImageIcon(curImg);
            setIcon(imageIcon);
        }

        /**
         * method for rotating the image clockwise
         */
        public void rotateImage(){
            if(hasMeeple){
                numRotationsAfterMeeple++;
            }
            curImg = imgHelper.rotateTileClockWise(curImg,1);
            setImage(curImg);
        }

        /**
         * method for placing a meeple and updating the current image to reflect that.
         */
        public void placeMeeple(){
            numRotationsAfterMeeple = 0;
            preMeepleImg = curImg;
            hasMeeple = true;
            Color curPlayerColor = playerColors[game.getCurrentPlayer().getPlayerId() - 1];
            curImg = imgHelper.drawMeeple(curImg,curPlayerColor,
                    game.getCurrentTile().getSegmentWithMeeple().getLocations().get(0));
            setImage(curImg);
        }

        /**
         * method for removing the meeple from the main image.
         */
        public void removeMeeple(){
            if(!hasMeeple){
                return;
            }
            curImg = imgHelper.rotateTileClockWise(preMeepleImg,numRotationsAfterMeeple);
            setImage(curImg);
        }

        /**
         * method for reseting the information that handles
         * removing and placing meeples.
         */
        public void resetMeepleInfo(){
            numRotationsAfterMeeple = 0;
            preMeepleImg = null;
            hasMeeple = false;
        }

        /**
         * method that gets the image for the tile before there was a meeple there.
         * @return the image without a meeple.
         */
        public BufferedImage getPreMeepleImg(){
            if(!hasMeeple){
                return null;
            }
            return imgHelper.rotateTileClockWise(preMeepleImg,numRotationsAfterMeeple);
        }
    }
}
