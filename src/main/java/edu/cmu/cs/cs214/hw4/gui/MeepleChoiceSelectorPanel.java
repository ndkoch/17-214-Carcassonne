package edu.cmu.cs.cs214.hw4.gui;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.FeatureType;
import edu.cmu.cs.cs214.hw4.core.Segment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * class that extends a JPanel, and holds all the information, and
 * logic for placing and removing a meeple on the current tile.
 */
public class MeepleChoiceSelectorPanel extends JPanel{
    private static final int NUM_BUTTONS = 4;
    private static final String PLAY_MEEPLE_TEXT = "Place Meeple";
    private static final String REMOVE_MEEPLE_TEXT = "Remove Meeple";

    private List<String> curSegments;
    private Carcassonne game;
    private JRadioButton[] selections = new JRadioButton[NUM_BUTTONS];
    private ButtonGroup selectionGroup = new ButtonGroup();
    private TileDisplayPanel tilePanel;

    /**
     * constructor function for the meeple placement panel,
     * which initializes the buttons and defines their listener
     * functions.
     * @param game game that is referenced in the listener functions.
     */
    public MeepleChoiceSelectorPanel(Carcassonne game){
        JButton placeMeepleButton;
        JButton removeMeepleButton;
        this.game = game;
        setLayout(new GridLayout(NUM_BUTTONS+1,0));
        for(int i = 0; i < NUM_BUTTONS; i++){
            selections[i] = new JRadioButton();
            selectionGroup.add(selections[i]);
            add(selections[i]);
        }
        placeMeepleButton = new JButton(PLAY_MEEPLE_TEXT);
        placeMeepleButton.addActionListener(event ->{
             ButtonModel selectedButton = selectionGroup.getSelection();
             if(selectedButton == null){
                 return;
             }
             String selectedSegmentStr = selectedButton.getActionCommand();
             Segment selectedSegment = getSegmentFromString(selectedSegmentStr);
             if(game.getCurrentTile().getSegmentWithMeeple() != null){
                 return;
             }
             game.placeMeeple(selectedSegment);
             tilePanel.setMeeple();
        });
        removeMeepleButton = new JButton(REMOVE_MEEPLE_TEXT);
        removeMeepleButton.addActionListener(event ->{
            game.removeMeepleWithReplacement();
            tilePanel.removeMeeple();
        });
        add(placeMeepleButton);
        add(removeMeepleButton);
    }

    /**
     * method that will be called when there has been a change in the game
     * data and the internal data needs to be changed so that the
     * panel can display these changes.
     */
    public void updateButtons(){
        updateCurSegments();
        setButtonTexts();
        selectionGroup.clearSelection();
    }

    /**
     * public method for assigning an internal tile display, so that it can be
     * accesed and updated in the listener functions.
     * @param t tileDisplay to be assigned.
     */
    public void setTileDisplay(TileDisplayPanel t){
        tilePanel = t;
    }

    /**
     * private method used for updating the current list of
     * segment strings
     */
    private void updateCurSegments(){
        curSegments = new ArrayList<>();
        List<Segment> segments = game.getCurrentTile().getSegments();
        for(int i = 0; i < segments.size(); i++){
            if(segments.get(i).getType() == FeatureType.FIELD && !game.getCurrentTile().hasMonastery()){
                continue;
            }
            curSegments.add(i,segments.get(i).toString());
        }
    }

    /**
     * method that will update the buttons so that their texts reflect the new changes
     * made to the segments on the tile.
     */
    private void setButtonTexts(){
        for(int i = 0; i < NUM_BUTTONS; i++){
            if(i >= curSegments.size()){
                selections[i].setVisible(false);
            } else {
                selections[i].setVisible(true);
                selections[i].setText(curSegments.get(i));
                selections[i].setActionCommand(curSegments.get(i));
            }
        }
    }

    /**
     * private method that when given a string of segment information,
     * will return the actual segment.
     * @param segmentStr string representation of the segment
     * @return the actual segment class that the given string represented.
     */
    private Segment getSegmentFromString(String segmentStr){
        for(int i = 0; i < curSegments.size();i++){
            if(segmentStr.equals(curSegments.get(i))){
                return game.getCurrentTile().getSegments().get(i);
            }
        }
        return null;
    }
}
