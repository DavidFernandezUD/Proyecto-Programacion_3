package main;

import java.awt.*;

/** Object that can be drawn into a Graphics2D object with the draw method.
 * @author david.f@opendeusto.es*/
public interface Drawable {

    /** Draws the object into a given Graphics2D object.
     * @param g2 Graphics2D object the object can be drawn into.*/
    void draw(Graphics2D g2);

}
