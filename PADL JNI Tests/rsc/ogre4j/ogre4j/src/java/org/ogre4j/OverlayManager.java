package org.ogre4j;

public class OverlayManager extends ScriptLoader {

    private static native void destroy(int ptrSelf, String name);

    private static native void destroyAll(int ptrSelf);

    private static native int getByName(int ptrSelf, String name);

    private static native int getOverlayElement(int ptrSelf, String name,
            boolean isTemplate);

    public static OverlayManager getSingleton() {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new OverlayManager(ptr);
    }

    private static native int getSingletonImpl();

    private static native int getViewportHeight(int ptrSelf);

    private static native int getViewportWidth(int ptrSelf);

    protected OverlayManager(InstancePointer ptr) {
        super(ptr);
    }

    /**
     * Destroys an existing overlay by name.
     * 
     * @param name
     */
    public void destroy(String name) {
        destroy(pInstance.getValue(), name);
    }

    /**
     * Destroys all existing overlays.
     * 
     */
    public void destroyAll() {
        destroyAll(pInstance.getValue());
    }

    /**
     * Retrieve an Overlay by name.
     * 
     * @param string
     * @return
     */
    public Overlay getByName(String name) {
        int address = getByName(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new Overlay(ptr);
    }

    /**
     * Gets a reference to an existing element.
     * 
     * @param name
     * @return
     */
    public OverlayElement getOverlayElement(String name) {
        return getOverlayElement(name, false);
    }

    /**
     * Gets a reference to an existing element.
     * 
     * @param name
     * @param isTemplate
     * @return
     */
    public OverlayElement getOverlayElement(String name, boolean isTemplate) {
        int address = getOverlayElement(pInstance.getValue(), name, isTemplate);
        InstancePointer ptr = new InstancePointer(address);
        return new OverlayElement(ptr);
    }

    /**
     * Gets the height of the destination viewport in pixels.
     * 
     * @return
     */
    public int getViewportHeight() {
        return getViewportHeight(pInstance.getValue());
    }

    /**
     * Gets the width of the destination viewport in pixels.
     * 
     * @return
     */
    public int getViewportWidth() {
        return getViewportWidth(pInstance.getValue());
    }
}
