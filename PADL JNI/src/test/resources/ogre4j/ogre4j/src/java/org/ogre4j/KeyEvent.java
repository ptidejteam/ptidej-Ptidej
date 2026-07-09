package org.ogre4j;

/**
 * status 100% c++ public methods
 * 
 * @author Stephen Tyler
 * 
 */
public class KeyEvent extends InputEvent {
    public static final int KE_FIRST_EVENT = 2500;

    public static final int KE_LAST_EVENT = 2504;

    public static final int KE_KEY_CLICKED = 2500;

    public static final int KE_KEY_PRESSED = 2501;

    public static final int KE_KEY_RELEASED = 2502;

    public static final int KE_KEY_FOCUSIN = 2503;

    public static final int KE_KEY_FOCUSOUT = 2504;

    public KeyEvent(PositionTarget source, int id, int key, float when,
            int modifiers) {
        super(new InstancePointer(createCppInstance(id, key, when, modifiers)));
    }

    private static native int createCppInstance(int id, int key, float when,
            int modifiers);

    /**
     * Returns a parameter string identifying this event.
     * 
     * @return
     */
    public String paramString() {
        return null;
    }

    /**
     * return the ID of the button
     * 
     * @return
     */
    public int getKey() {
        return 0;
    }

    /**
     * return the char of the button
     * 
     * @return
     */
    public char getKeyChar() {
        return 0;
    }
}
