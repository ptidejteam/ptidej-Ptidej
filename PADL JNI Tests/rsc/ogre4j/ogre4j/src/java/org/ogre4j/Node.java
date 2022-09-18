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
 * Node.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.4 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO Node type/class description.
 */
public class Node extends NativeObject implements Renderable {

    public static final int TS_LOCAL = 0;

    public static final int TS_PARENT = 1;

    public static final int TS_WORLD = 2;

    private static native float[] _getDerivedOrientation(int pInstance);

    private static native float[] _getDerivedPosition(int pInstance);

    private static native float[] _getDerivedScale(int pInstance);

    private static native void addChild(int pInstance, int i);

    private static native void cancelUpdate(int ptrSelf, int ptrChild);

    private static native int createChild(int ptrSelf, float transX,
            float transY, float transZ, float quatW, float quatX, float quatY,
            float quatZ);

    private static native int createChild(int ptrSelf, String name,
            float transX, float transY, float transZ, float quatW, float quatX,
            float quatY, float quatZ);

    private static native int getChild(int pInstance, int index)
            throws OgreException;

    private static native int getChild(int pInstance, String name)
            throws OgreException;

    private static native boolean getInheritScale(int pInstance);

    private static native float[] getInitialOrientation(int ptrSelf);

    private static native float[] getInitialPosition(int pInstance);

    private static native float[] getInitialScale(int ptrSelf);

    private static native String getName(int pInstance);

    private static native float[] getOrientation(int pInstance);

    private static native int getParent(int pInstance);

    private static native float[] getPosition(int pInstance);

    private static native float[] getScale(int pInstance);

    private static native float[] getWorldPosition(int pInstance);

    private static native void needUpdate(int ptrSelf);

    private static native int numChildren(int pInstance);

    private static native void pitch(int ptrSelf, float radians, int relativeTo);

    private static native void removeAllChildren(int pInstance);

    private static native void removeChild(int ptrSelf, int ptrChild);

    private static native int removeChild(int pInstance, short index);

    private static native int removeChild(int pInstance, String name);

    private static native void requestUpdate(int ptrSelf, int ptrChild);

    private static native void resetOrientation(int pInstance);

    private static native void resetToInitialState(int ptrSelf);

    private static native void roll(int ptrSelf, float radians, int relativeTo);

    private static native void rotateByQuaternion(int pInstance, float w,
            float x, float y, float z);

    private static native void rotateQuat(int ptrSelf, float qW, float qX,
            float qY, float qZ, int relativeTo);

    private static native void rotateVec(int ptrSelf, float axisX, float axisY,
            float axisZ, float radians, int relativeTo);

    private static native void scale(int pInstance, float x, float y, float z);

    private static native void setCustomParameter(int ptrSelf, int index,
            Vector4 value);

    private static native void setInheritScale(int pInstance, boolean inherit);

    private static native void setInitialState(int ptrSelf);

    private static native void setOrientation(int pInstance, float w, float x,
            float y, float z);

    private static native void setPosition(int pInstance, float x, float y,
            float z);

    private static native void setRenderDetailOverrideable(int ptrSelf,
            boolean override);

    private static native void setScale(int pInstance, float x, float y, float z);

    private static native void translate(int ptrSelf, float x, float y,
            float z, int relativeTo);

    private static native void yaw(int ptrSelf, float radians, int relativeTo);

    /**
     * @param pInstance
     */
    protected Node(InstancePointer pInstance) {
        super(pInstance);
        renderable = new RenderableImpl(pInstance);
    }

    private Renderable renderable;

    /**
     * Gets the orientation of the node as derived from all parents
     * 
     * @return
     */
    public Quaternion _getDerivedOrientation() {
        return new Quaternion(_getDerivedOrientation(pInstance.getValue()));
    }

    /**
     * Gets the position of the node as derived from all parents.
     * 
     * @return
     */
    public Vector3 _getDerivedPosition() {
        return new Vector3(_getDerivedPosition(pInstance.getValue()));
    }

    /**
     * Gets the scaling factor of the node as derived from all parents.
     * 
     * @return
     */
    public Vector3 _getDerivedScale() {
        return new Vector3(_getDerivedScale(pInstance.getValue()));
    }

    public void _updateCustomGpuParameter() {
        renderable._updateCustomGpuParameter();
    }

    /**
     * Internal weighted transform method.
     * 
     * @param weight
     * @param translate
     * @param rotate
     * @param scale
     */
    public void _weightedTransform(float weight, Vector3 translate,
            Quaternion rotate, Vector3 scale) {
        // TODO
    }

    /**
     * Adds a (precreated) child scene node to this node.
     * 
     * @param child
     */
    public void addChild(Node child) {
        addChild(pInstance.getValue(), child.getInstancePtr().getValue());
    }

    /**
     * Called by children to notify their parent that they no longer need an
     * update.
     * 
     * @param child
     */
    public void cancelUpdate(Node child) {
        cancelUpdate(pInstance.getValue(), child.pInstance.getValue());
    }

    /**
     * Creates an unnamed new Node as a child of this node.
     * 
     * @return
     */
    public Node createChild() {
        return createChild(Vector3.ZERO);
    }

    /**
     * Creates a new named Node as a child of this node.
     * 
     * @param name
     * @return
     */
    public Node createChild(String name) {
        return createChild(name, Vector3.ZERO);
    }

    /**
     * Creates a new named Node as a child of this node.
     * 
     * @param name
     * @param translate
     * @return
     */
    public Node createChild(String name, Vector3 translate) {
        return createChild(name, translate, Quaternion.IDENTITY);
    }

    /**
     * Creates a new named Node as a child of this node.
     * 
     * @param name
     * @param translate
     * @param rotate
     * @return
     */
    public Node createChild(String name, Vector3 translate, Quaternion rotate) {
        int ptr = createChild(pInstance.getValue(), name, translate.x,
                translate.y, translate.z, rotate.w, rotate.x, rotate.y,
                rotate.z);

        return new Node(new InstancePointer(ptr));
    }

    /**
     * Creates an unnamed new Node as a child of this node.
     * 
     * @param translate
     * @return
     */
    public Node createChild(Vector3 translate) {
        return createChild(translate, Quaternion.IDENTITY);
    }

    /**
     * Creates an unnamed new Node as a child of this node.
     * 
     * @param translate
     * @param rotate
     * @return
     */
    public Node createChild(Vector3 translate, Quaternion rotate) {
        int address = createChild(pInstance.getValue(), translate.x,
                translate.y, translate.z, rotate.w, rotate.x, rotate.y,
                rotate.z);
        InstancePointer ptr = new InstancePointer(address);
        return new Node(ptr);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getCastsShadows()
     */
    public boolean getCastsShadows() {
        return renderable.getCastsShadows();
    }

    /**
     * Gets a pointer to a child node.
     * 
     * @param index
     * @return
     * @throws OgreException
     */
    public Node getChild(short index) throws OgreException {
        InstancePointer ptrNode = new InstancePointer(getChild(pInstance
                .getValue(), index));
        return new Node(ptrNode);
    }

    /**
     * Gets a pointer to a named child node.
     * 
     * @param name
     * @return
     * @throws OgreException
     */
    public Node getChild(String name) throws OgreException {
        int address = getChild(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new Node(ptr);
    }

    /**
     * Retrieves an iterator for efficiently looping through all children of
     * this node.
     * 
     * @return
     */
    public Iterator getChildIterator() {
        // TODO
        return null;
    }

    public Vector<MovablePlane> getClipPlanes() {
        return renderable.getClipPlanes();
    }

    /**
     * Gets the custom value associated with this Renderable at the given index.
     */
    public Vector4 getCustomParameter(long index) {
        return renderable.getCustomParameter(index);
    }

    /**
     * Returns true if this node is affected by scaling factors applied to the
     * parent node.
     * 
     * @return
     */
    public boolean getInheritScale() {
        return getInheritScale(pInstance.getValue());
    }

    /**
     * Gets the initial orientation of this node, see setInitialState for more
     * info.
     * 
     * @return
     */
    public Quaternion getInitialOrientation() {
        return new Quaternion(getInitialOrientation(pInstance.getValue()));
    }

    /**
     * Gets the initial position of this node, see setInitialState for more
     * info.
     * 
     * @return
     */
    public Vector3 getInitialPosition() {
        float[] tuple = getInitialPosition(pInstance.getValue());
        return new Vector3(tuple);
    }

    /**
     * Gets the initial position of this node, see setInitialState for more
     * info.
     * 
     * @return
     */
    public Vector3 getInitialScale() {
        return new Vector3(getInitialScale(pInstance.getValue()));
    }

    public Vector<Light> getLights() {
        return renderable.getLights();
    }

    /**
     * Gets a matrix whose columns are the local axes based on the nodes
     * orientation relative to it's parent.
     * 
     * @return
     */
    public Matrix3 getLocalAxes() {
        return null;
    }

    public Material getMaterial() {
        return renderable.getMaterial();
    }

    /**
     * Returns the name of the node.
     * 
     * @return
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    /**
     * 
     * @see org.ogre4j.IRenderable#getNormaliseNormals()
     */
    public boolean getNormaliseNormals() {
        return renderable.getNormaliseNormals();
    }

    /**
     * Returns the number of world transform matrices this renderable requires.
     * 
     * @see org.ogre4j.IRenderable#getNumWorldTransforms()
     */
    public short getNumWorldTransforms() {
        return renderable.getNumWorldTransforms();
    }

    /**
     * Returns a quaternion representing the nodes orientation.
     * 
     * @return
     */
    public Quaternion getOrientation() {
        float[] tuple = getOrientation(pInstance.getValue());
        if (tuple == null)
            return null;
        return new Quaternion(tuple);
    }

    // TODO return null if root
    public Node getParent() {
        int address = getParent(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new Node(ptr);
    }

    /**
     * Gets the position of the node relative to it's parent.
     * 
     * @return
     */
    public Vector3 getPosition() {
        return new Vector3(getPosition(pInstance.getValue()));
    }

    /**
     * Returns the preferred rasterisation mode of this renderable.
     */
    public int getRenderDetail() {
        return renderable.getRenderDetail();
    }

    /**
     * Gets whether this renderable's chosen detail level can be overridden
     * (downgraded) by the camera setting.
     */
    public boolean getRenderDetailOverrideable() {
        return renderable.getRenderDetailOverrideable();
    }

    public void getRenderOperation(RenderOperation op) {
        renderable.getRenderOperation(op);
    }

    /**
     * Gets the scaling factor of this node.
     * 
     * @return
     */
    public Vector3 getScale() {
        return new Vector3(getScale(pInstance.getValue()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getSquaredViewDepth(org.ogre4j.Camera)
     */
    public float getSquaredViewDepth(Camera cam) {
        return renderable.getSquaredViewDepth(cam);
    }

    /**
     * Retrieves a pointer to the Material Technique this renderable object
     * uses.
     */
    public Technique getTechnique() {
        return renderable.getTechnique();
    }

    public Quaternion getWorldOrientation() {
        return renderable.getWorldOrientation();
    }

    public Vector3 getWorldPosition() {
        float[] tuple = getWorldPosition(pInstance.getValue());
        return new Vector3(tuple);
    }

    public void getWorldTransforms(Matrix4 xform) {
        renderable.getWorldTransforms(xform);

    }

    /**
     * To be called in the event of transform changes to this node that require
     * it's recalculation.
     * 
     */
    public void needUpdate() {
        needUpdate(pInstance.getValue());
    }

    /**
     * Reports the number of child nodes under this one.
     * 
     * @return
     */
    public int numChildren() {
        return numChildren(pInstance.getValue());
    }

    /**
     * Rotate the node around the X-axis.
     * 
     * @param radians
     */
    public void pitch(float radians) {
        pitch(radians, TS_LOCAL);
    }

    /**
     * Rotate the node around the X-axis.
     * 
     * @param radians
     * @param relativeTo
     */
    public void pitch(float radians, int relativeTo) {
        pitch(pInstance.getValue(), radians, relativeTo);
    }

    /**
     * Removes all child Nodes attached to this node.
     * 
     */
    public void removeAllChildren() {
        removeAllChildren(pInstance.getValue());
    }

    /**
     * Drops the specified child from this node.
     * 
     * @param child
     */
    public void removeChild(Node child) {
        removeChild(pInstance.getValue(), child.pInstance.getValue());
    }

    /**
     * Drops the specified child from this node.
     * 
     * @param index
     * @return
     */
    public Node removeChild(short index) {
        int address = removeChild(pInstance.getValue(), index);
        InstancePointer ptr = new InstancePointer(address);
        return new Node(ptr);
    }

    /**
     * Drops the named child from this node.
     * 
     * @param name
     * @return
     */
    public Node removeChild(String name) {
        int address = removeChild(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new Node(ptr);
    }

    public void requestUpdate(Node child) {
        requestUpdate(pInstance.getValue(), child.pInstance.getValue());
    }

    /**
     * Resets the nodes orientation (local axes as world axes, no rotation).
     * 
     */
    public void resetOrientation() {
        resetOrientation(pInstance.getValue());
    }

    /**
     * Resets the position / orientation / scale of this node to it's initial
     * state, see setInitialState for more info.
     * 
     */
    public void resetToInitialState() {
        resetToInitialState(pInstance.getValue());
    }

    /**
     * Rotate the node around the Z-axis
     * 
     * @param radians
     */
    public void roll(float radians) {
        roll(radians, TS_LOCAL);
    }

    /**
     * Rotate the node around the Z-axis
     * 
     * @param radians
     * @param relativeTo
     */
    public void roll(float radians, int relativeTo) {
        roll(pInstance.getValue(), radians, relativeTo);
    }

    /**
     * Rotate the node around an aritrary axis using a Quarternion.
     * 
     * @param q
     */
    public void rotate(Quaternion q) {
        rotate(q, TS_LOCAL);
    }

    /**
     * Rotate the node around an aritrary axis using a Quarternion.
     * 
     * @param q
     * @param relativeTo
     */
    public void rotate(Quaternion q, int relativeTo) {
        rotateQuat(pInstance.getValue(), q.w, q.x, q.y, q.z, relativeTo);
    }

    /**
     * Rotate the node around an arbitrary axis.
     * 
     * @param axis
     * @param radians
     */
    public void rotate(Vector3 axis, float radians) {
        rotate(axis, radians, TS_LOCAL);
    }

    /**
     * Rotate the node around an arbitrary axis.
     * 
     * @param axis
     * @param radians
     * @param relativeTo
     */
    public void rotate(Vector3 axis, float radians, int relativeTo) {
        rotateVec(pInstance.getValue(), axis.x, axis.y, axis.z, radians,
                relativeTo);
    }

    /**
     * Scales the node, combining it's current scale with the passed in scaling
     * factor.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void scale(float x, float y, float z) {
        scale(pInstance.getValue(), x, y, z);
    }

    /**
     * Scales the node, combining it's current scale with the passed in scaling
     * factor.
     * 
     * @param scale
     */
    public void scale(Vector3 scale) {
        scale(scale.x, scale.y, scale.z);
    }

    /**
     * Sets a custom parameter for this Renderable, which may be used to drive
     * calculations for this specific Renderable, like GPU program parameters.
     */
    public void setCustomParameter(int index, Vector4 value) {
        setCustomParameter(pInstance.getValue(), index, value);
    }

    public void setCustomParameter(long index, Vector4 value) {
        renderable.setCustomParameter(index, value);
    }

    /**
     * Tells the node whether it should inherit scaling factors from it's parent
     * node.
     * 
     * @param inherit
     */
    public void setInheritScale(boolean inherit) {
        setInheritScale(pInstance.getValue(), inherit);
    }

    /**
     * Sets the current transform of this node to be the 'initial state' ie that
     * position / orientation / scale to be used as a basis for delta values
     * used in keyframe animation.
     * 
     */
    public void setInitialState() {
        setInitialState(pInstance.getValue());
    }

    /**
     * Sets the orientation of this node via quaternion parameters.
     * 
     * @param w
     * @param x
     * @param y
     * @param z
     */
    public void setOrientation(float w, float x, float y, float z) {
        setOrientation(pInstance.getValue(), w, x, y, z);
    }

    /**
     * Sets the orientation of this node via a quaternion.
     * 
     * @param q
     */
    public void setOrientation(Quaternion q) {
        setOrientation(q.w, q.x, q.y, q.z);
    }

    /**
     * Sets the position of the node relative to it's parent.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setPosition(float x, float y, float z) {
        setPosition(pInstance.getValue(), x, y, z);
    }

    /**
     * Sets the position of the node relative to it's parent.
     * 
     * @param pos
     */
    public void setPosition(Vector3 pos) {
        setPosition(pos.x, pos.y, pos.z);
    }

    /**
     * Sets whether this renderable's chosen detail level can be overridden
     * (downgraded) by the camera setting.
     */
    public void setRenderDetailOverrideable(boolean override) {
        setRenderDetailOverrideable(pInstance.getValue(), override);
    }

    /**
     * Sets the scaling factor applied to this node.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setScale(float x, float y, float z) {
        setScale(pInstance.getValue(), x, y, z);
    }

    /**
     * Sets the scaling factor applied to this node.
     * 
     * @param scale
     */
    public void setScale(Vector3 scale) {
        setScale(scale.x, scale.y, scale.x);
    }

    /**
     * Moves the node along the cartesian axes.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void translate(float x, float y, float z) {
        translate(x, y, z, TS_PARENT);
    }

    /**
     * Moves the node along the cartesian axes.
     * 
     * @param x
     * @param y
     * @param z
     * @param relativeTo
     */
    public void translate(float x, float y, float z, int relativeTo) {
        translate(pInstance.getValue(), x, y, z, relativeTo);
    }

    /**
     * Moves the node along the cartesian axes.
     * 
     * @param d
     */
    public void translate(Vector3 d) {
        translate(d.x, d.y, d.z, TS_PARENT);
    }

    /**
     * Moves the node along the cartesian axes.
     * 
     * @param d
     * @param relativeTo
     */
    public void translate(Vector3 d, int relativeTo) {
        translate(d.x, d.y, d.z, relativeTo);
    }

    /**
     * Returns whether or not to use an 'identity' projection.
     */
    public boolean useIdentityProjection() {
        return renderable.useIdentityProjection();
    }

    /**
     * Returns whether or not to use an 'identity' projection.
     * 
     * @see org.ogre4j.IRenderable#useIdentityView()
     */
    public boolean useIdentityView() {
        return renderable.useIdentityView();
    }

    /**
     * Rotate the node around the Y-axis.
     * 
     * @param radians
     */
    public void yaw(float radians) {
        yaw(radians, TS_LOCAL);
    }

    /**
     * Rotate the node around the Y-axis.
     * 
     * @param radians
     * @param relativeTo
     */
    public void yaw(float radians, int relativeTo) {
        yaw(pInstance.getValue(), radians, relativeTo);
    }
}
