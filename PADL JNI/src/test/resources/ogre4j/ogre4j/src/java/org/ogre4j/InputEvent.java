package org.ogre4j;

/**
 * status 100% public methods
 * 
 * @author Stephen Tyler
 * 
 */
public class InputEvent extends NativeObject {

    public static final int ALT_MASK = 1 << 3;

    public static final int BUTTON_ANY_MASK = 0xF << 4;

    public static final int BUTTON0_MASK = 1 << 4;

    public static final int BUTTON1_MASK = 1 << 5;

    public static final int BUTTON2_MASK = 1 << 6;

    public static final int BUTTON3_MASK = 1 << 7;

    public static final int CTRL_MASK = 1 << 1;

    public static final int META_MASK = 1 << 2;

    public static final int SHIFT_MASK = 1 << 0;

    private static native void consume(int ptrSelf);

    private static native int getID(int ptrSelf);

    private static native int getModifiers(int ptrSelf);

    private static native int getSource(int ptrSelf);

    private static native float getWhen(int ptrSelf);

    private static native boolean isAltDown(int ptrSelf);

    private static native boolean isConsumed(int ptrSelf);

    private static native boolean isControlDown(int ptrSelf);

    private static native boolean isEventBetween(int ptrSelf, int start, int end);

    private static native boolean isMetaDown(int ptrSelf);

    private static native boolean isShiftDown(int ptrSelf);

    public InputEvent(EventTarget source, int id, long when, int modifiers) {

    }

    protected InputEvent(InstancePointer ptr) {
        super(ptr);
    }

    /**
     * Consumes this event so that it will not be processed in the default
     * manner by the source which originated it.
     * 
     */
    public void consume() {
        consume(pInstance.getValue());
    }

    public int getID() {
        return getID(pInstance.getValue());
    }

    /**
     * Returns the modifiers flag for this event.
     * 
     * @return
     */
    public int getModifiers() {
        return getModifiers(pInstance.getValue());
    }

    public EventTarget getSource() {
        int address = getSource(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new EventTargetImpl(ptr);
    }

    /**
     * Returns the timestamp of when this event occurred.
     * 
     * @return
     */
    public float getWhen() {
        return getWhen(pInstance.getValue());
    }

    /**
     * Returns whether or not the Alt modifier is down on this event.
     * 
     * @return
     */
    public boolean isAltDown() {
        return isAltDown(pInstance.getValue());
    }

    /**
     * Returns whether or not this event has been consumed.
     * 
     * @return
     */
    public boolean isConsumed() {
        return isConsumed(pInstance.getValue());
    }

    /**
     * Returns whether or not the Control modifier is down on this event.
     * 
     * @return
     */
    public boolean isControlDown() {
        return isControlDown(pInstance.getValue());
    }

    public boolean isEventBetween(int start, int end) {
        return isEventBetween(pInstance.getValue(), start, end);
    }

    /**
     * Returns whether or not the Meta modifier is down on this event.
     * 
     * @return
     */
    public boolean isMetaDown() {
        return isMetaDown(pInstance.getValue());
    }

    /**
     * Returns whether or not the Shift modifier is down on this event.
     * 
     * @return
     */
    public boolean isShiftDown() {
        return isShiftDown(pInstance.getValue());
    }
}
