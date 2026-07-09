/* This source file is part of ogre4j
 *     (The JNI bindings for OGRE)
 * For the latest info, see http://www.ogre4j.org/
 * 
 * Copyright (c) 2005 netAllied GmbH, Tettnang
 * Also see acknowledgements in Readme.html
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt.
 * 
 *
 * MovableObject.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * TODO MovableObject type/class description. status: 100% complete except
 * getedgelist and getshadowvolume...
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class MovableObject extends NativeObject implements ShadowCaster {
    private static native Matrix4 _getParentNodeFullTransform(int ptrSelf);

    private static native void _notifyAttached(int ptrSelf, int ptrParent,
            boolean isTagPoint);

    private static native void _notifyCurrentCamera(int ptrSelf, int ptrCam);

    private static native void _updateRenderQueue(int ptrSelf);

    private static native void addQueryFlags(int ptrSelf, long flags);

    private static native int getBoundingBox(int ptrSelf);

    private static native float getBoundingRadius(int ptrSelf);

    private native static boolean getCastShadows(int pInstance);

    private static native int getDarkCapBounds(int ptrSelf, int ptrLight,
            float dirLightExtrusionDist);

    private static native int getLightCapBounds(int ptrSelf);

    private static native String getMovableType(int ptrSelf);

    private static native String getName(int ptrSelf);

    private native static int getParentNode(int pInstance);

    private native static int getParentSceneNode(int pInstance);

    private static native float getPointExtrusionDistance(int ptrSelf, int ptrL);

    private native static long getQueryFlags(int pInstance);

    private static native int getRenderQueueGroup(int ptrSelf);

    private native static int getWorldBoundingBox(int pInstance, boolean derive);

    private static native int getWorldBoundingSphere(int ptrSelf, boolean derive);

    private native static boolean isAttached(int pInstance);

    private static native boolean isInScene(int ptrSelf);

    private native static boolean isVisible(int pInstance);

    private static native void removeQueryFlags(int ptrSelf, long flags);

    private native static void setCastShadows(int pInstance, boolean enabled);

    private native static void setQueryFlags(int pInstance, long flags);

    private static native void setRenderQueueGroup(int ptrSelf, int queueId);

    private native static void setVisible(int pInstance, boolean visible);

    private MovableObject() {
        super(null);
    }

    /**
     * @param pInstance
     */
    protected MovableObject(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * return the full transformation of the parent sceneNode or the
     * attachingPoint node
     * 
     * @return
     */
    public Matrix4 _getParentNodeFullTransform() {
        return _getParentNodeFullTransform(pInstance.getValue());
    }

    /**
     * Internal method called to notify the object that it has been attached to
     * a node.
     * 
     * @param parent
     * @param isTagPoint
     */
    public void _notifyAttached(Node parent, boolean isTagPoint) {
        _notifyAttached(pInstance.getValue(), parent.pInstance.getValue(),
                isTagPoint);
    }

    /**
     * Internal method to notify the object of the camera to be used for the
     * next rendering operation.
     * 
     * @param cam
     */
    public void _notifyCurrentCamera(Camera cam) {
        _notifyCurrentCamera(pInstance.getValue(), cam.pInstance.getValue());
    }

    /**
     * Internal method by which the movable object must add Renderable subclass
     * instances to the rendering queue.
     * 
     */
    public void _updateRenderQueue() {
        _updateRenderQueue(pInstance.getValue());
    }

    /**
     * As setQueryFlags, except the flags passed as parameters are appended to
     * the existing flags on this object.
     * 
     * @param flags
     */
    public void addQueryFlags(long flags) {
        addQueryFlags(pInstance.getValue(), flags);
    }

    /**
     * Retrieves the local axis-aligned bounding box for this object.
     * 
     * @return
     */
    public AxisAlignedBox getBoundingBox() {
        int address = getBoundingBox(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new AxisAlignedBox(ptr);
    }

    /**
     * Retrieves the radius of the origin-centered bounding sphere for this
     * object.
     * 
     * @return
     */
    public float getBoundingRadius() {
        return getBoundingRadius(pInstance.getValue());
    }

    /**
     * Returns whether shadow casting is enabled for this object.
     * 
     */
    public boolean getCastShadows() {
        return getCastShadows(pInstance.getValue());
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.ogre4j.ShadowCaster#getDarkCapBounds(org.ogre4j.Light, float)
     */
    public AxisAlignedBox getDarkCapBounds(Light light,
            float dirLightExtrusionDist) {
        int ptr = getDarkCapBounds(pInstance.getValue(), light.pInstance
                .getValue(), dirLightExtrusionDist);
        return new AxisAlignedBox(new InstancePointer(ptr));
    }

    /**
     * (non-Javadoc)
     * 
     * @see org.ogre4j.ShadowCaster#getLightCapBounds()
     */
    public AxisAlignedBox getLightCapBounds() {
        int ptr = getLightCapBounds(pInstance.getValue());
        return new AxisAlignedBox(new InstancePointer(ptr));
    }

    /**
     * Returns the type name of this object. This method is abstract.
     * 
     * @return
     */
    public String getMovableType() {
        return getMovableType(pInstance.getValue());
    }

    /**
     * Returns the name of this object.
     * 
     * @return
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    /**
     * Returns the node to which this object is attached.
     * 
     * @return
     */
    public Node getParentNode() {
        int ptrParent = getParentNode(pInstance.getValue());
        if (ptrParent == 0)
            return null;

        return new Node(new InstancePointer(ptrParent));
    }

    /**
     * Returns the node to which this object is attached.
     * 
     * @return
     */
    public SceneNode getParentSceneNode() {
        int ptrSceneNode = getParentSceneNode(pInstance.getValue());
        if (ptrSceneNode == 0)
            return null;
        return new SceneNode(new InstancePointer(ptrSceneNode));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.ShadowCaster#getPointExtrusionDistance(org.ogre4j.Light)
     */
    public float getPointExtrusionDistance(Light l) {
        return getPointExtrusionDistance(pInstance.getValue(), l.pInstance
                .getValue());
    }

    /**
     * Returns the query flags relevant for this object.
     * 
     * @return
     */
    public long getQueryFlags() {
        return getQueryFlags(pInstance.getValue());
    }

    /**
     * Gets the queue group for this entity, see setRenderQueueGroup for full
     * details.
     * 
     * @return
     */
    public int getRenderQueueGroup() {
        return getRenderQueueGroup(pInstance.getValue());
    }

    /**
     * Retrieves a pointer to a custom application object associated with this
     * movable by an earlier call to setUserObject.
     * 
     * @return
     */
    public UserDefinedObject getUserObject() {
        // TODO
        return null;
    }

    /**
     * Retrieves the axis-aligned bounding box for this object in world
     * coordinates.
     * 
     * @return
     */
    public AxisAlignedBox getWorldBoundingBox() {
        return getWorldBoundingBox(false);
    }

    /**
     * Retrieves the axis-aligned bounding box for this object in world
     * coordinates.
     * 
     * @param derive
     * @return
     */
    public AxisAlignedBox getWorldBoundingBox(boolean derive) {
        int ptrAAB = getWorldBoundingBox(pInstance.getValue(), derive);
        if (ptrAAB == 0)
            return null;
        return new AxisAlignedBox(new InstancePointer(ptrAAB));
    }

    /**
     * Retrieves the worldspace bounding sphere for this object.
     * 
     * @param derive
     * @return
     */
    public Sphere getWorldBoundingSphere(boolean derive) {
        int ptr = getWorldBoundingSphere(pInstance.getValue(), derive);
        return new Sphere(new InstancePointer(ptr));
    }

    /**
     * Returns true if this object is attached to a SceneNode or TagPoint.
     * 
     * @return
     */
    public boolean isAttached() {
        return isAttached(pInstance.getValue());
    }

    /**
     * Returns true if this object is attached to a SceneNode or TagPoint, and
     * this SceneNode / TagPoint is currently in an active part of the scene
     * graph.
     * 
     * @return
     */
    public boolean isInScene() {
        return isInScene(pInstance.getValue());
    }

    /**
     * Returns whether or not this object is supposed to be visible or not.
     * 
     * @return
     */
    public boolean isVisible() {
        return isVisible(pInstance.getValue());
    }

    /**
     * As setQueryFlags, except the flags passed as parameters are removed from
     * the existing flags on this object.
     * 
     * @param flags
     */
    public void removeQueryFlags(long flags) {
        removeQueryFlags(pInstance.getValue(), flags);
    }

    /**
     * Sets whether or not this object will cast shadows.
     * 
     * @param enabled
     */
    public void setCastShadows(boolean enabled) {
        setCastShadows(pInstance.getValue(), enabled);
    }

    /**
     * Sets the query flags for this object.
     * 
     * @param flags
     */
    public void setQueryFlags(long flags) {
        setQueryFlags(pInstance.getValue(), flags);
    }

    /**
     * Sets the render queue group this entity will be rendered through.
     * 
     * @param queueID
     */
    public void setRenderQueueGroup(int queueID) {
        setRenderQueueGroup(pInstance.getValue(), queueID);
    }

    /**
     * Call this to associate your own custom user object instance with this
     * MovableObject.
     * 
     * @param obj
     */
    public void setUserObject(UserDefinedObject obj) {
        // TODO
    }

    /**
     * Tells this object whether to be visible or not, if it has a renderable
     * component.
     * 
     * @param visible
     */
    public void setVisible(boolean visible) {
        setVisible(pInstance.getValue(), visible);
    }
}
