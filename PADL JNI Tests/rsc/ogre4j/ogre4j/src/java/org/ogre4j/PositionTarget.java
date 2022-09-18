package org.ogre4j;

public interface PositionTarget extends EventTarget {
    /**
     * Gets the left of this element in relation to the screen (where 0 = far
     * left, 1.0 = far right).
     * 
     * @return
     */
    public float getLeft();

    /**
     * Gets the top of this element in relation to the screen (where 0 = top,
     * 1.0 = bottom).
     * 
     * @return
     */
    public float getTop();

    public PositionTarget getPositionTargetParent();

    public boolean isKeyEnabled();

}
