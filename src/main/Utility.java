package main;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

/** Utility class for image drawing optimization.
 * @author david.f@opendeusto.es*/
public class Utility {

    /** Given a BufferedImage, it resizes it to the desired dimension.
     * This method is used in other classes that require drawing to resize the used images
     * before using them, so they don't have to be resized again every time they are drawn.
     * @param original Original image to resize.
     * @param width Desired width to rescale the image into.
     * @param height Desired height to rescale the image into.
     * @return a rescaled instance of the image.*/
    public BufferedImage scaleImage(BufferedImage original, int width, int height) {

        // TODO: Use this method to optimize main.object drawing too
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, 0, 0, width, height, null);
        g2.dispose();

        return scaledImage;
    }

}
