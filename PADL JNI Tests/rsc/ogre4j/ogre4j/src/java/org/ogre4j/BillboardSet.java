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
 * BillboardSet.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.4 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Vector;

import javax.vecmath.Vector4f;

/**
 * BillboardSet
 * 
 * TODO: BillboardSet Methods
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class BillboardSet extends MovableObject implements Renderable {

    private static native int createBillboard(int pInstance, float x, float y,
            float z) throws OgreException;

    private static native void setDefaultWidth(int pInstance, float width);

    private static native void setMaterialName(int pInstance,
            String materialName) throws OgreException;

    private Renderable renderable;

    /**
     * @param pInstance
     */
    public BillboardSet(InstancePointer pInstance) {
        super(pInstance);
        renderable = new RenderableImpl(pInstance);
    }

    public Billboard createBillboard(float x, float y, float z)
            throws OgreException {
        InstancePointer ptrBillboard = new InstancePointer(createBillboard(
                pInstance.getValue(), x, y, z));
        return new Billboard(ptrBillboard);
    }

    public Billboard createBillboard(int i, int j, int k,
            ColourValue minLightColour) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getCastsShadows()
     */
    public boolean getCastsShadows() {
        return renderable.getCastsShadows();

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getNormaliseNormals()
     */
    public boolean getNormaliseNormals() {
        return renderable.getNormaliseNormals();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getNumWorldTransforms()
     */
    public short getNumWorldTransforms() {
        return renderable.getNumWorldTransforms();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getSquaredViewDepth(org.ogre4j.Camera)
     */
    public float getSquaredViewDepth(Camera cam) {
        return renderable.getSquaredViewDepth(cam);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getWorldOrientation()
     */
    public Quaternion getWorldOrientation() {
        return renderable.getWorldOrientation();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#setCustomParameter(long,
     *      javax.vecmath.Vector4f)
     */
    public void setCustomParameter(long index, Vector4 value) {
        renderable.setCustomParameter(index, value);

    }

    public void setDefaultWidth(float width) {
        setDefaultWidth(pInstance.getValue(), width);
    }

    public void setMaterialName(String materialName) throws OgreException {
        setMaterialName(pInstance.getValue(), materialName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#useIdentityProjection()
     */
    public boolean useIdentityProjection() {
        return renderable.useIdentityProjection();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#useIdentityView()
     */
    public boolean useIdentityView() {
        return renderable.useIdentityView();
    }

    public Vector4 getCustomParameter(long index) {
        return renderable.getCustomParameter(index);
    }

    public Material getMaterial() {
        return renderable.getMaterial();
    }

    public int getRenderDetail() {
        return renderable.getRenderDetail();
    }

    public Vector<Light> getLights() {
        return renderable.getLights();
    }

    public void setRenderDetailOverrideable(boolean override) {
        renderable.setRenderDetailOverrideable(override);
    }

    public boolean getRenderDetailOverrideable() {
        return renderable.getRenderDetailOverrideable();
    }

    public void _updateCustomGpuParameter() {
        renderable._updateCustomGpuParameter();
    }

    public void getRenderOperation(RenderOperation op) {
        renderable.getRenderOperation(op);
    }

    public Vector<MovablePlane> getClipPlanes() {
        return renderable.getClipPlanes();
    }

    public Technique getTechnique() {
        return renderable.getTechnique();
    }

    public Vector3 getWorldPosition() {
        return renderable.getWorldPosition();
    }

    public void getWorldTransforms(Matrix4 xform) {
        renderable.getWorldTransforms(xform);
    }
}
