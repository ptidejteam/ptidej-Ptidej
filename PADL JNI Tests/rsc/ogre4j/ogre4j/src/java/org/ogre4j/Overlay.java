package org.ogre4j;

public class Overlay extends NativeObject {

    private static native int getChild(int ptrSelf, String name);

    private static native String getName(int ptrSelf);

    private static native short getZOrder(int ptrSelf);

    private static native void hide(int ptrSelf);

    private static native void setZOrder(int ptrSelf, short zorder);

    private static native void show(int ptrSelf);

    protected Overlay(InstancePointer ptr) {
        super(ptr);
    }

    public OverlayContainer getChild(String name) {
        int address = getChild(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new OverlayContainer(ptr);
    }

    /**
     * Gets the name of this overlay.
     * 
     * @return
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    /**
     * Gets the ZOrder of this overlay.
     * 
     * @return
     */
    public short getZOrder() {
        return getZOrder(pInstance.getValue());
    }

    /**
     * Hides the overlay if it was visible.
     * 
     */
    public void hide() {
        hide(pInstance.getValue());
    }

    /**
     * Alters the ZOrder of this overlay.
     * 
     * @param zorder
     */
    public void setZOrder(short zorder) {
        setZOrder(pInstance.getValue(), zorder);
    }

    /**
     * Shows the overlay if it was hidden.
     * 
     */
    public void show() {
        show(pInstance.getValue());
    }

}
