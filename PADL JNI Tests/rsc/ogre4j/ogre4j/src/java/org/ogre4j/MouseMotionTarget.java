package org.ogre4j;

public interface MouseMotionTarget extends PositionTarget {
    public void processMouseMotionEvent(MouseEvent e);

    public void addMouseMotionListener(MouseMotionListener l);

    public void removeMouseMotionListener(MouseMotionListener l);
}
