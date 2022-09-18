package org.ogre4j;

/**
 * status 100%
 * 
 * @author Stephen Tyler
 * 
 */
public interface KeyListener {

    /**
     * Invoked when the key has been clicked on a component.
     * 
     * @param e
     */
    public void keyClicked(KeyEvent e);

    /**
     * Invoked when the target receives the keyboard focus.
     * 
     * @param e
     */
    public void keyFocusIn(KeyEvent e);

    /**
     * Invoked when the target loses the keyboard focus.
     * 
     * @param e
     */
    public void keyFocusOut(KeyEvent e);

    /**
     * Invoked when a key button has been pressed on a component.
     * 
     * @param e
     */
    public void keyPressed(KeyEvent e);

    /**
     * Invoked when a key button has been released on a component.
     * 
     * @param e
     */
    public void keyReleased(KeyEvent e);
}
