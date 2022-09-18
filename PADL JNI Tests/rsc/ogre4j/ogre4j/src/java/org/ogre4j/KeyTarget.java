package org.ogre4j;

public interface KeyTarget extends EventTarget {
    public void processKeyEvent(KeyEvent e);

    public void addKeyListener(KeyListener l);

    public void removeKeyListener(KeyListener l);
}
