package org.ogre4j;

public interface MouseTarget extends PositionTarget {

    public void processMouseEvent(MouseEvent e);

    public void addMouseListener(MouseListener l);

    public void removeMouseListener(MouseListener l);

    public boolean isMouseWithin();

}
