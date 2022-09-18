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
 * Camera.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.11 $
 * $Date: 2005/08/26 14:04:58 $
 * $Author: yavin02 $
 */
package org.ogre4j;

import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * TODO Camera type/class description.
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Camera extends Frustum implements Renderable {
    private static native int getCameraToViewportRay(int pInstance,
            float screenx, float screeny);

    private static native float[] getDerivedOrientation(int pInstance);

    private static native float[] getDerivedPosition(int pInstance);

    private static native int getDetailLevel(int pInstance);

    private static native float[] getDirection(int pInstance);

    private static native float[] getOrientation(int pInstance);

    private static native float[] getPosition(int pInstance);

    private static native void lookAt(int pInstance, float x, float y, float z);

    private static native void moveRelative(int pInstance, float x, float y,
            float z);

    private static native void pitch(int pInstance, float radians);

    private static native void roll(int pInstance, float radian);

    private static native void setAutoTracking(int ptrSelf, boolean enabled,
            int ptrTarget, float offsetX, float offsetY, float offsetZ);

    private static native void setDetailLevel(int pInstance, int detailLevel);
    
    private static native void setDirection(int pInstance, float y, float x,
            float z);
    private static native void setFixedYawAxis(int pInstance, boolean useFixed);    
    
    private static native void setFixedYawAxis(int pInstance, boolean useFixed, float fixedAxis_X, float fixedAxis_Y, float fixedAxis_Z);
    
    private static native void setOrientation(int pInstance, float x, float y, float z, float w);
    
    private static native void setPosition(int pInstance, float x, float y,
            float z);
    
    private static native void yaw(int pInstance, float radian);
    
    /**
     * @param ptrCamera
     */
    protected Camera(InstancePointer pInstance) {
        super(pInstance);
    }
    
    public Ray getCameraToViewportRay(float screenx, float screeny) {
        InstancePointer ptrRay = new InstancePointer(getCameraToViewportRay(
                pInstance.getValue(), screenx, screeny));
        return new Ray(ptrRay);
    }
    
    public Quat4f getDerivedOrientation()
    {
        float[] tuple = getDerivedOrientation(pInstance.getValue());
        return new Quat4f(tuple);
    }
    
    public Point3f getDerivedPosition()
    {
        float[] tuple = getDerivedPosition(pInstance.getValue());
        return new Point3f(tuple);
    }

    public int getDetailLevel() {
        return getDetailLevel(pInstance.getValue());
    }
    
    public Vector3f getDirection() {
        float[] tuple = getDirection(pInstance.getValue());
        return new Vector3f(tuple);
    }

    public Quat4f getOrientation()
    {
        float[] tuple = getOrientation(pInstance.getValue());
        return new Quat4f(tuple);
    }

    public Point3f getPosition() {
        return new Point3f(getPosition(pInstance.getValue()));
    }

    public void lookAt(float x, float y, float z) {
        lookAt(pInstance.getValue(), x, y, z);
    }

    public void lookAt(Point3f pos) {
        lookAt(pInstance.getValue(), pos.x, pos.y, pos.z);
    }

    public void lookAt(Vector3 vector3) {
        // TODO Auto-generated method stub

    }

    /**
     * @param vector
     */
    public void lookAt(Vector3f vector) {
        lookAt(vector.x, vector.y, vector.z);
    }

    public void moveRelative(Vector3 vec) {
        // TODO Auto-generated method stub

    }

    public void moveRelative(Vector3f d) {
        moveRelative(pInstance.getValue(), d.x, d.y, d.z);
    }

    public void pitch(float radians) {
        pitch(pInstance.getValue(), radians);
    }

    public void roll(float radians) {
        roll(pInstance.getValue(), radians);
    }

    /**
     * Enables / disables automatic tracking of a SceneNode.
     * 
     * @param enabled
     * @param target
     */
    public void setAutoTracking(boolean enabled) {
        setAutoTracking(enabled, null);
    }

    /**
     * Enables / disables automatic tracking of a SceneNode.
     * 
     * @param enabled
     * @param target
     */
    public void setAutoTracking(boolean enabled, SceneNode target) {
        setAutoTracking(enabled, target, Vector3.ZERO);
    }

    /**
     * Enables / disables automatic tracking of a SceneNode.
     * 
     * @param enabled
     * @param target
     * @param offset
     */
    public void setAutoTracking(boolean enabled, SceneNode target,
            Vector3 offset) {
        int ptrTarget = (target != null) ? target.pInstance.getValue() : 0;
        setAutoTracking(pInstance.getValue(), enabled, ptrTarget, offset.x,
                offset.y, offset.z);
    }

    public void setDetailLevel(int detailLevel) {
        setDetailLevel(pInstance.getValue(), detailLevel);
    }

    public void setDirection(float x, float y, float z) {
        setDirection(pInstance.getValue(), x, y, z);
    }

    public void setDirection(Vector3f vec) {
        setDirection(pInstance.getValue(), vec.x, vec.y, vec.z);
    }

    public void setFixedYawAxis(boolean useFixed)
    {
        setFixedYawAxis(pInstance.getValue(), useFixed);
    }

    public void setFixedYawAxis(boolean useFixed, Vector3f fixedAxis)
    {
        setFixedYawAxis(pInstance.getValue(), useFixed, fixedAxis.x, fixedAxis.y, fixedAxis.z);
    }

    public void setOrientation(Quat4f q)
    {
        setOrientation(pInstance.getValue(), q.x, q.y, q.z, q.w);
    }

    public void setPosition(float x, float y, float z) {
        setPosition(pInstance.getValue(), x, y, z);
    }

    public void setPosition(Point3f pos) {
        setPosition(pos.x, pos.y, pos.z);
    }

    public void setPosition(Vector3 vector3) {
        // TODO Auto-generated method stub

    }

    /**
     * @param pos
     */
    public void setPosition(Vector3f pos) {
        setPosition(pos.x, pos.y, pos.z);
    }

    public void yaw(float radians) {
        yaw(pInstance.getValue(), radians);
    }
}
