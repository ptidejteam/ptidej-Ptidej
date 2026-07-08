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
 * SceneNode.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.7 $
 * $Date: 2005/08/25 14:40:14 $
 * $Author: yavin02 $
 */
package org.ogre4j;

import javax.vecmath.Vector3f;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO SceneNode type/class
 *         description.
 */
public class SceneNode extends Node
{
    private static native int _getWorldAABB(int pInstance);

    private static native void attachObject(int pInstance, int obj) throws OgreException;

    private static native int createChildSceneNode(int ptrSelf, float transX, float transY, float transZ, float quatW, float quatX,
            float quatY, float quatZ);

    private static native int createChildSceneNode(int ptrSelf, String name, float transX, float transY, float transZ, float quatW,
            float quatX, float quatY, float quatZ);

    private static native void detachAllObjects(int ptrSelf);

    private static native void detachObject(int ptrSelf, int ptrObj);

    private static native int detachObject(int ptrSelf, short index);

    private static native int detachObject(int pInstance, String name) throws OgreException;

    private static native void dispose(int pInstanc);

    private static native int getAttachedObject(int ptrSelf, short index);

    private static native int getAttachedObject(int pInstance, String name) throws OgreException;

    private static native boolean getShowBoundingBox(int pInstance);

    private static native boolean isInSceneGraph(int ptrSelf);

    private static native void lookAt(int ptrSelf, float targetX, float targetY, float targetZ, int relativeTo, float localX, float localY,
            float localZ);

    private static native short numAttachedObjects(int ptrSelf);

    private static native void removeAndDestroyAllChildren(int pInstance) throws OgreException;

    private static native void removeAndDestroyChild(int pInstance, short index) throws OgreException;

    private static native void removeAndDestroyChild(int pInstance, String name) throws OgreException;

    private static native void setDirection(int pInstance, float y, float x, float z);

    private static native void setFixedYawAxis(int pInstance, boolean useFixed);

    private static native void setFixedYawAxis(int pInstance, boolean useFixed, float fixedAxis_X, float fixedAxis_Y, float fixedAxis_Z);

    private static native void setVisible(int ptrSelf, boolean visible, boolean cascade);

    private static native void showBoundingBox(int pInstance, boolean bShow);

    /**
     * @param pInstance
     */
    protected SceneNode(InstancePointer pInstance)
    {
        super(pInstance);
    }

    public AxisAlignedBox _getWorldAABB()
    {
        int ptrAAB = _getWorldAABB(pInstance.getValue());
        if (ptrAAB == 0)
            return null;
        return new AxisAlignedBox(new InstancePointer(ptrAAB));
    }

    /**
     * Adds an instance of a scene object to this node.
     */
    public void attachObject(MovableObject obj) throws OgreException
    {
        attachObject(pInstance.getValue(), obj.pInstance.getValue());
    }

    public SceneNode createChildSceneNode()
    {
        return createChildSceneNode(Vector3.ZERO);
    }

    public SceneNode createChildSceneNode(String name)
    {
        return createChildSceneNode(name, Vector3.ZERO);
    }

    public SceneNode createChildSceneNode(String name, Vector3 translate)
    {
        return createChildSceneNode(name, translate, Quaternion.IDENTITY);
    }

    public SceneNode createChildSceneNode(String name, Vector3 translate, Quaternion rotate)
    {
        int address = createChildSceneNode(pInstance.getValue(), name, translate.x, translate.y, translate.z, rotate.w, rotate.x, rotate.y,
                rotate.z);
        InstancePointer ptr = new InstancePointer(address);

        return new SceneNode(ptr);
    }

    public SceneNode createChildSceneNode(Vector3 translate)
    {
        return createChildSceneNode(translate, Quaternion.IDENTITY);
    }

    public SceneNode createChildSceneNode(Vector3 translate, Quaternion rotate)
    {
        int address = createChildSceneNode(pInstance.getValue(), translate.x, translate.y, translate.z, rotate.w, rotate.x, rotate.y,
                rotate.z);
        InstancePointer ptr = new InstancePointer(address);
        return new SceneNode(ptr);
    }

    /**
     * Detaches all objects attached to this node.
     * 
     */
    public void detachAllObjects()
    {
        detachAllObjects(pInstance.getValue());
    }

    /**
     * Detaches an object by pointer.
     * 
     * @param obj
     */
    public void detachObject(MovableObject obj)
    {
        detachObject(pInstance.getValue(), obj.pInstance.getValue());
    }

    /**
     * Detaches the indexed object from this scene node.
     * 
     * @param index
     * @return
     */
    public MovableObject detachObject(short index)
    {
        int address = detachObject(pInstance.getValue(), index);
        InstancePointer ptr = new InstancePointer(address);
        return new MovableObject(ptr);

    }

    /**
     * Detaches an object by pointer.
     * 
     * @param name
     * @return
     * @throws OgreException
     */
    public MovableObject detachObject(String name) throws OgreException
    {
        int address = detachObject(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new MovableObject(ptr);
    }

    public void dispose()
    {
        dispose(pInstance.getValue());
    }

    /**
     * Retrieves a pointer to an attached object.
     * 
     * @param index
     * @return
     */
    public MovableObject getAttachedObject(short index)
    {
        int address = getAttachedObject(pInstance.getValue(), index);
        InstancePointer ptr = new InstancePointer(address);
        return new MovableObject(ptr);
    }

    /**
     * Retrieves a pointer to an attached object.
     * 
     * @param name
     * @return
     * @throws OgreException
     */
    public MovableObject getAttachedObject(String name) throws OgreException
    {
        int address = getAttachedObject(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new MovableObject(ptr);
    }

    public boolean getShowBoundingBox()
    {
        return (getShowBoundingBox(pInstance.getValue()));
    }

    /**
     * Determines whether this node is in the scene graph, ie whether it's
     * ulitimate ancestor is the root scene node.
     * 
     * @return
     */
    public boolean isInSceneGraph()
    {
        return isInSceneGraph(pInstance.getValue());
    }

    public void lookAt(Vector3 targetPoint, int relativeTo)
    {
        lookAt(targetPoint, relativeTo, Vector3.NEGATIVE_UNIT_Z);
    }

    public void lookAt(Vector3 targetPoint, int relativeTo, Vector3 localDirectionVector)
    {
        lookAt(pInstance.getValue(), targetPoint.x, targetPoint.y, targetPoint.z, relativeTo, localDirectionVector.x,
                localDirectionVector.y, localDirectionVector.z);
    }

    /**
     * Reports the number of objects attached to this node.
     * 
     * @return
     */
    public short numAttachedObjects()
    {
        return numAttachedObjects(pInstance.getValue());
    }

    /**
     * Removes and destroys all children of this node.
     * <p>
     * <b>Remarks:</b><br/> Use this to destroy all child nodes of this node
     * and remove them from the scene graph. Note that all objects attached to
     * this node will be detached but will not be destroyed.
     * </p>
     * 
     * @throws OgreException
     */
    public void removeAndDestroyAllChildren() throws OgreException
    {
        removeAndDestroyAllChildren(pInstance.getValue());
    }

    /**
     * This method removes and destroys the child and all of its children.
     * <p>
     * <b>Remarks:</b><br/> Unlike removeChild, which removes a single named
     * child from this node but does not destroy it, this method destroys the
     * child and all of it's children. Use this if you wish to recursively
     * destroy a node as well as detaching it from it's parent. Note that any
     * objects attached to the nodes will be detached but will not themselves be
     * destroyed.
     * </p>
     * 
     * @param index
     *            Index of the child entity.
     * @throws OgreException
     */
    public void removeAndDestroyChild(short index) throws OgreException
    {
        removeAndDestroyChild(pInstance.getValue(), index);
    }

    /**
     * This method removes and destroys the child and all of its children.
     * <p>
     * <b>Remarks:</b><br/> Unlike removeChild, which removes a single named
     * child from this node but does not destroy it, this method destroys the
     * child and all of it's children. Use this if you wish to recursively
     * destroy a node as well as detaching it from it's parent. Note that any
     * objects attached to the nodes will be detached but will not themselves be
     * destroyed.
     * </p>
     * 
     * @param name
     *            Name of the child entity.
     * @throws OgreException
     */
    public void removeAndDestroyChild(String name) throws OgreException
    {
        removeAndDestroyChild(pInstance.getValue(), name);
    }

    public void setAutoTracking(boolean b, SceneNode rootSceneNode)
    {
        // TODO Auto-generated method stub
    }

    /**
     * Sets the node's direction vector ie it's local -z.
     * <p>
     * <b>Remarks:</b><br/> Note that the 'up' vector for the orientation will
     * automatically be recalculated based on the current 'up' vector (i.e. the
     * roll will remain the same). If you need more control, use setOrientation.
     * </p>
     * 
     * @param vec The direction vector.
     */
    public void setDirection(Vector3f vec)
    {
        setDirection(pInstance.getValue(), vec.x, vec.y, vec.z);
    }

    /**
     * @see #setFixedYawAxis(boolean, Vector3f)
     */
    public void setFixedYawAxis(boolean useFixed)
    {
        setFixedYawAxis(pInstance.getValue(), useFixed);
    }

    /**
     * Tells the node whether to yaw around it's own local Y axis or a fixed
     * axis of choice.
     * <p>
     * <b>Remarks:</b><br/> This method allows you to change the yaw behaviour
     * of the node - by default, it yaws around it's own local Y axis when told
     * to yaw with TS_LOCAL, this makes it yaw around a fixed axis. You only
     * really need this when you're using auto tracking (see setAutoTracking,
     * because when you're manually rotating a node you can specify the
     * TransformSpace in which you wish to work anyway.
     * </p>
     * 
     * @param useFixed
     *            If true, the axis passed in the second parameter will always
     *            be the yaw axis no matter what the node orientation. If false,
     *            the node returns to it's default behaviour.
     * @param fixedAxis
     *            The axis to use if the first parameter is true.
     */
    public void setFixedYawAxis(boolean useFixed, Vector3f fixedAxis)
    {
        setFixedYawAxis(pInstance.getValue(), useFixed, fixedAxis.x, fixedAxis.y, fixedAxis.z);
    }

    /**
     * Makes all objects attached to this node become visible / invisble.
     * 
     * @param visible
     */
    public void setVisible(boolean visible)
    {
        setVisible(visible, true);
    }

    /**
     * Makes all objects attached to this node become visible / invisble.
     * 
     * @param visible
     * @param cascade
     */
    public void setVisible(boolean visible, boolean cascade)
    {
        setVisible(pInstance.getValue(), visible, cascade);
    }

    public void showBoundingBox(boolean bShow)
    {
        showBoundingBox(pInstance.getValue(), bShow);
    }
}
