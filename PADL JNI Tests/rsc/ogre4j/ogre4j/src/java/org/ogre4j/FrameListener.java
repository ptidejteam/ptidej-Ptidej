package org.ogre4j;

abstract public class FrameListener extends NativeObject {

    private static native int createCppInstance();

    protected FrameListener() {
        super(new InstancePointer(createCppInstance()));
        hook(pInstance.getValue());
    }

    protected FrameListener(InstancePointer ptr) {
        super(ptr);
        hook(pInstance.getValue());
    }

    private native void hook(int ptrSelf);

    /**
     * Called just after a frame has been rendered.
     * 
     * @param timeSinceLastEvent
     * @param timeSinceLastFrame
     * @return
     */
    public boolean frameEnded(float timeSinceLastEvent, float timeSinceLastFrame) {
        return true;
    }

    /**
     * Called when a frame is about to begin rendering.
     * 
     * @param timeSinceLastEvent
     * @param timeSinceLastFrame
     * @return
     */
    public boolean frameStarted(float timeSinceLastEvent,
            float timeSinceLastFrame) {
        return true;
    }

}
