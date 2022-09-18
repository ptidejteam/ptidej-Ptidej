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
 * Frustum.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Vector;

import javax.vecmath.Vector4f;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO Frustum type/class
 *         description.
 */
public class Frustum extends MovableObject implements Renderable {

    public static final int FRUSTUM_PLANE_BOTTOM = 5;

    public static final int FRUSTUM_PLANE_FAR = 1;

    public static final int FRUSTUM_PLANE_LEFT = 2;

    public static final int FRUSTUM_PLANE_NEAR = 0;

    public static final int FRUSTUM_PLANE_RIGHT = 3;

    public static final int FRUSTUM_PLANE_TOP = 4;

    private static native float getFarClipDistance(int ptrSelf);

    private static native float getFOVy(int ptrSelf);

    private static native float getNearClipDistance(int ptrSelf);

    private static native void setAspectRatio(int ptrSelf, float ratio);

    private static native void setFarClipDistance(int ptrSelf, float farDist);

    private static native void setFOVy(int ptrSelf, float fovy);

    private static native void setNearClipDistance(int ptrSelf, float nearDist);

    /**
     * @param pInstance
     */
    protected Frustum(InstancePointer pInstance) {
        super(pInstance);
        renderable = new RenderableImpl(pInstance);
    }

    private Renderable renderable;

    public void _updateCustomGpuParameter() {
        // TODO Auto-generated method stub

    }

    /**
     * Retreives the current aspect ratio.
     * 
     * @return
     */
    public float getAspectRatio() {
        return 0.0f;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getCastsShadows()
     */
    public boolean getCastsShadows() {
        // TODO Auto-generated method stub
        return false;
    }

    public Vector<MovablePlane> getClipPlanes() {
        return renderable.getClipPlanes();
    }

    public Vector4 getCustomParameter(long index) {
        return getCustomParameter(index);
    }

    /**
     * Retrieves the distance from the frustum to the far clipping plane.
     * 
     * @return
     */
    public float getFarClipDistance() {
        return getFarClipDistance(pInstance.getValue());
    }

    /**
     * Retrieves the frustums Y-dimension Field Of View (FOV).
     * 
     * @return
     */
    public float getFOVy() {
        return getFOVy(pInstance.getValue());
    }

    /**
     * Retrieves a specified plane of the frustum.
     * 
     * @param plane
     * @return
     */
    public MovablePlane getFrustumPlane(short plane) {
        return null;
    }

    public Vector<Light> getLights() {
        // TODO Auto-generated method stub
        return null;
    }

    public Material getMaterial() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Gets the position of the near clipping plane.
     * 
     * @return
     */
    public float getNearClipDistance() {
        return getNearClipDistance(pInstance.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getNormaliseNormals()
     */
    public boolean getNormaliseNormals() {
        return renderable.getNormaliseNormals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getNumWorldTransforms()
     */
    public short getNumWorldTransforms() {
        return renderable.getNumWorldTransforms();
    }

    /**
     * Gets the projection matrix for this frustum.
     * 
     * @return
     */
    public Matrix4 getProjectionMatrix() {
        return null;
    }

    public int getRenderDetail() {
        return renderable.getRenderDetail();
    }

    public boolean getRenderDetailOverrideable() {
        return renderable.getRenderDetailOverrideable();
    }

    public void getRenderOperation(RenderOperation op) {
        renderable.getRenderOperation(op);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getSquaredViewDepth(org.ogre4j.Camera)
     */
    public float getSquaredViewDepth(Camera cam) {
        return renderable.getSquaredViewDepth(cam);
    }

    /**
     * Gets the 'standard' projection matrix for this frustum, ie the projection
     * matrix which conforms to standard right-handed rules.
     * 
     * @return
     */
    public Matrix4 getStandardProjectionMatrix() {
        return null;
    }

    public Technique getTechnique() {
        return renderable.getTechnique();
    }

    /**
     * Gets the view matrix for this frustum.
     * 
     * @return
     */
    public Matrix4 getViewMatrix() {
        return null;
    }

    public Quaternion getWorldOrientation() {
        return renderable.getWorldOrientation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getWorldPosition()
     */
    public Vector3 getWorldPosition() {
        return renderable.getWorldPosition();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#getWorldTransforms()
     */
    public Matrix4 getWorldTransforms() {
        // TODO Auto-generated method stub
        return null;
    }

    public void getWorldTransforms(Matrix4 xform) {
        // TODO Auto-generated method stub

    }

    /**
     * Tests whether the given container is visible in the Frustum.
     * 
     * @param bound
     * @param culledBy
     * @return
     */
    public boolean isVisible(AxisAlignedBox bound, int culledBy) {
        return false;
    }

    /**
     * Tests whether the given container is visible in the Frustum.
     * 
     * @param bound
     * @param culledBy
     * @return
     */
    public boolean isVisible(Sphere bound, int culledBy) {
        return false;
    }

    /**
     * Tests whether the given vertex is visible in the Frustum.
     * 
     * @param vert
     * @param culledBy
     * @return
     */
    public boolean isVisible(Vector3 vert, int culledBy) {
        return false;
    }

    /**
     * Sets the aspect ratio for the frustum viewport.
     * 
     * @param ratio
     */
    public void setAspectRatio(float ratio) {
        setAspectRatio(pInstance.getValue(), ratio);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#setCustomParameter(long,
     *      javax.vecmath.Vector4)
     */
    public void setCustomParameter(long index, Vector4 value) {
        renderable.setCustomParameter(index, value);
    }

    /**
     * Sets the distance to the far clipping plane.
     * 
     * @param farDist
     */
    public void setFarClipDistance(float farDist) {
        setFarClipDistance(pInstance.getValue(), farDist);
    }

    /**
     * Sets the Y-dimension Field Of View (FOV) of the frustum.
     * 
     * @param fovy
     */
    public void setFOVy(float fovy) {
        setFOVy(pInstance.getValue(), fovy);
    }

    /**
     * Sets the position of the near clipping plane.
     * 
     * @param nearDist
     */
    public void setNearClipDistance(float nearDist) {
        setNearClipDistance(pInstance.getValue(), nearDist);
    }

    public void setRenderDetailOverrideable(boolean override) {
        renderable.setRenderDetailOverrideable(override);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#useIdentityProjection()
     */
    public boolean useIdentityProjection() {
        return renderable.useIdentityProjection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.Renderable#useIdentityView()
     */
    public boolean useIdentityView() {
        return renderable.useIdentityView();
    }

}
