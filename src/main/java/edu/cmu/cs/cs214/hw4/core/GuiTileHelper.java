package edu.cmu.cs.cs214.hw4.core;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * class that implements the gui image helper in a standard fashion.
 */
public class GuiTileHelper implements GuiImageHelper {
    private static final String TILE_PICTURES = "src/main/resources/carcassonne_tiles.png";
    private static final int RADIUS = 10;
    private static final int X_OFF = 45;
    private static final int Y_OFF = 20;
    private static final int IMG_WIDTH = 90;
    private static final int TILE_COL = 6;
    private static final int OFFSET = 11;

    @Override
    public BufferedImage getTileImage(Character tileLetter) throws IOException {
        BufferedImage image = ImageIO.read(new File(TILE_PICTURES));
        int tileNum = tileLetter;
        int col = (tileNum + 1) % TILE_COL;
        int row = (tileNum + 1) / TILE_COL - (OFFSET);
        return image.getSubimage(col*IMG_WIDTH,row*IMG_WIDTH,IMG_WIDTH,IMG_WIDTH);
    }

    @Override
    public BufferedImage rotateTileClockWise(BufferedImage src, int n) {
        int weight = src.getWidth();
        int height = src.getHeight();

        AffineTransform at = AffineTransform.getQuadrantRotateInstance(n, weight / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage dest = new BufferedImage(weight, height, src.getType());
        op.filter(src, dest);
        return dest;
    }

    @Override
    public BufferedImage drawMeeple(BufferedImage src, Color color, SegmentLocation location){
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());
        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.setColor(color);
        int x;
        int y;
        switch(location.getValue()){
            case(0):
                x = X_OFF;
                y = Y_OFF;
                break;
            case(1):
                x = IMG_WIDTH - Y_OFF;
                y = X_OFF;
                break;
            case(2):
                x = X_OFF;
                y = IMG_WIDTH - Y_OFF;
                break;
            case(2 + 1):
                x = Y_OFF;
                y = X_OFF;
                break;
            default:
                x = 0;
                y = 0;
                break;
        }
        g.fillOval(x - RADIUS, y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        g.dispose();

        return dest;
    }
}
