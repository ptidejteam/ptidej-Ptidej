package org.ogre4j;

public class MouseMotionTargetImpl extends PositionTargetImpl implements
        MouseMotionTarget {

    public static native void addMouseMotionListener(int ptrSelf, int ptrL);

    protected MouseMotionTargetImpl(InstancePointer ptr) {
        super(ptr);
    }

    public void addMouseMotionListener(MouseMotionListener l) {
        addMouseMotionListener(pInstance.getValue(), l.pInstance.getValue());

    }

    public void processMouseMotionEvent(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    private native void removeMouseMotionListener(int ptrSelf, int ptrL);

    public void removeMouseMotionListener(MouseMotionListener l) {
        removeMouseMotionListener(pInstance.getValue(), l.pInstance.getValue());
    }

}
