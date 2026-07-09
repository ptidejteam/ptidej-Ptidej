package org.ogre4j;

public class KeyTargetImpl extends EventTargetImpl implements KeyTarget {

    public KeyTargetImpl(InstancePointer ptr) {
        super(ptr);
    }

    public void processKeyEvent(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void addKeyListener(KeyListener l) {
        // TODO Auto-generated method stub

    }

    private static native void addKeyListener(int ptrSelf, int ptrL);

    private static native void removeKeyListener(int ptrSelf, int ptrL);

    public void removeKeyListener(KeyListener l) {
        // removeKeyListener(pInstance.getValue(), l.pin)

    }
}
