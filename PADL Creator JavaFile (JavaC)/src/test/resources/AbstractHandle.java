/*
 * @(#)AbstractHandle.java 5.2
 *
 */
package javacTestCase;

import java.awt.*;


/**
 * AbstractHandle provides defaulf implementation for the
 * Handle interface.
 *
 * @see Figure
 * @see Handle
 */
public abstract class AbstractHandle {

    private static final int HANDLESIZE = 0;

	public abstract Point locate();


    public void invokeStart(int  x, int  y, Drawing drawing) { }


    public void invokeStart(int  x, int  y, DrawingView view) {
        invokeStart(x, y, view.drawing());
    }

    /**
     * @ deprecated As of version 4.1,
     * use invokeStep(x, y, anchorX, anchorY, drawingView)
     *
     * Tracks a step of the interaction.
     * @param dx x delta of this step
     * @param dy y delta of this step
     */
    public void invokeStep (int dx, int dy, Drawing drawing) { }

    /**
     * Tracks a step of the interaction.
     * @param x the current x position
     * @param y the current y position
     * @param anchorX the x position where the interaction started
     * @param anchorY the y position where the interaction started
     */
    public void invokeStep (int x, int y, int anchorX, int anchorY, DrawingView view) {
        invokeStep(x-anchorX, y-anchorY, view.drawing());
    }

    /**
     * Tracks the end of the interaction.
     * @param x the current x position
     * @param y the current y position
     * @param anchorX the x position where the interaction started
     * @param anchorY the y position where the interaction started
     */
    public void invokeEnd(int x, int y, int anchorX, int anchorY, DrawingView view) {
        invokeEnd(x-anchorX, y-anchorY, view.drawing());
    }

    /**
     * @deprecated As of version 4.1,
     * use invokeEnd(x, y, anchorX, anchorY, drawingView).
     *
     * Tracks the end of the interaction.
     */
    public void invokeEnd  (int dx, int dy, Drawing drawing) { }


    /**
     * Gets the display box of the handle.
     */
    public Rectangle displayBox() {
        Point p = locate();
        return new Rectangle(
                p.x - HANDLESIZE / 2,
                p.y - HANDLESIZE / 2,
                HANDLESIZE,
                HANDLESIZE);
    }

    /**
     * Tests if a point is contained in the handle.
     */
    public boolean containsPoint(int x, int y) {
        return displayBox().contains(x, y);
    }

    /**
     * Draws this handle.
     */
    public void draw(Graphics g) {
        Rectangle r = displayBox();

        g.setColor(Color.white);
        g.fillRect(r.x, r.y, r.width, r.height);

        g.setColor(Color.black);
        g.drawRect(r.x, r.y, r.width, r.height);
    }
}


