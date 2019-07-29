package edu.cmu.cs.cs214.hw4.core;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * interface for an image helper.
 */
public interface GuiImageHelper {
    /**
     * method that gets the image for a tile given the string associated with it.
     * @param tileLetter letter that the tile can be associated with
     * @return the image of said tile
     * @throws IOException if the resources file containing all the images for the tiles
     * would be missing.
     */
    BufferedImage getTileImage(Character tileLetter) throws IOException;

    /**
     * method for rotating a given image clockwise.
     * @param img image to be rotated
     * @param n number of times to rotate the image clockwise.
     * @return the newly rotated image.
     */
    BufferedImage rotateTileClockWise(BufferedImage img, int n);

    /**
     * method for drawing a meeple on a given image.
     * @param src image to draw the meeple on.
     * @param color color of the meeple.
     * @param location segment location for where the meeple should be drawn
     * @return a new image with a meeple on it.
     */
    BufferedImage drawMeeple(BufferedImage src, Color color, SegmentLocation location);
}
