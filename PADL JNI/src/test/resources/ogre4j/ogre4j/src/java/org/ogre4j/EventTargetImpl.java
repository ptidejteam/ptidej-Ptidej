package org.ogre4j;

public class EventTargetImpl extends NativeObject implements EventTarget {

    public void processEvent(InputEvent e) {
    }

    protected EventTargetImpl(InstancePointer ptr) {
        super(ptr);
        hook(pInstance.getValue());
    }

    private native void hook(int ptrSelf);

}
