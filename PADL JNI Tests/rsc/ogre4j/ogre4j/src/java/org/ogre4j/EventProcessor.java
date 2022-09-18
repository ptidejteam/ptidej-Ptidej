package org.ogre4j;

public class EventProcessor extends FrameListener implements MouseTarget,
        MouseMotionTarget, KeyTarget {
    private static native int createCppInstance();

    public static EventProcessor getSingleton() {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new EventProcessor(ptr);
    }

    private static native int getSingletonImpl();

    private static native void startProcessingEvents(int ptrSelf);

    private static native void stopProcessingEvents(int ptrSelf);

    private KeyTarget keyTarget;

    private MouseTarget mouseTarget;

    private MouseMotionTarget mouseMotionTarget;

    public EventProcessor() {
        this(new InstancePointer(createCppInstance()));
    }

    protected EventProcessor(InstancePointer ptr) {
        super(ptr);
        keyTarget = new KeyTargetImpl(ptr);
        mouseTarget = new MouseTargetImpl(ptr);
        mouseMotionTarget = new MouseMotionTargetImpl(ptr);
    }

    public void addKeyListener(KeyListener l) {
        keyTarget.addKeyListener(l);
    }

    public void addMouseListener(MouseListener l) {
        mouseTarget.addMouseListener(l);
    }

    public void addMouseMotionListener(MouseMotionListener l) {
        mouseMotionTarget.addMouseMotionListener(l);
    }

    public InputReader getInputReader() {
        // TODO Auto-generated method stub
        return null;
    }

    public float getLeft() {
        // TODO Auto-generated method stub
        return 0;
    }

    public PositionTarget getPositionTargetParent() {
        // TODO Auto-generated method stub
        return null;
    }

    public float getTop() {
        // TODO Auto-generated method stub
        return 0;
    }

    public void initialise(RenderWindow window) {
        intialize(pInstance.getValue(), window.pInstance.getValue());
    }

    private static native void intialize(int ptrSelf, int ptrWindow);

    public boolean isKeyEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isMouseWithin() {
        return mouseTarget.isMouseWithin();
    }

    public void processEvent(InputEvent e) {
        // TODO Auto-generated method stub

    }

    public void processKeyEvent(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    public void processMouseEvent(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    public void processMouseMotionEvent(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    public void removeKeyListener(KeyListener l) {
        keyTarget.removeKeyListener(l);

    }

    public void removeMouseListener(MouseListener l) {
        mouseTarget.removeMouseListener(l);

    }

    public void removeMouseMotionListener(MouseMotionListener l) {
        mouseMotionTarget.removeMouseMotionListener(l);

    }

    /**
     * Registers FrameListener, and activates the queue.
     * 
     */
    public void startProcessingEvents() {
        startProcessingEvents(false);
    }

    /**
     * Registers FrameListener, and activates the queue.
     * 
     * @param registerListener
     */
    public void startProcessingEvents(boolean registerListener) {
        startProcessingEvents(pInstance.getValue());
    }

    /**
     * Removes this from being a FrameListener, and deactivates the queue.
     * 
     */
    public void stopProcessingEvents() {
        stopProcessingEvents(pInstance.getValue());
    }
}
