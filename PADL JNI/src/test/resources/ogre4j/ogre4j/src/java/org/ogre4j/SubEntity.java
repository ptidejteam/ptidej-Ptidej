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
 * SubEntity.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Vector;

/**
 * progress: 100% public methods from C++
 * 
 * @author Stephen Tyler
 * 
 */
public class SubEntity extends NativeObject implements Renderable {
    private static native String getMaterialName(int pInstance);

    private static native boolean isVisible(int ptrSelf);

    private static native void setMaterialName(int pInstance, String name);

    private Renderable renderable;

    protected SubEntity(InstancePointer pInstance) {
        super(pInstance);
        renderable = new RenderableImpl(pInstance);
    }

    /**
     * Advanced method to get the temporarily blended vertex information for
     * entities which are software skinned.
     * 
     * @return
     */
    public VertexData _getBlendedVertexData() {
        return null;
    }

    public void _updateCustomGpuParameter() {
        renderable._updateCustomGpuParameter();
    }

    /**
     * Get the temporary blended vertex data for this subentity.
     * 
     * @return
     */
    public VertexData getBlendedVertexData() {
        // TODO
        return null;
    }

    public boolean getCastsShadows() {
        return renderable.getCastsShadows();
    }

    public Vector<MovablePlane> getClipPlanes() {
        return renderable.getClipPlanes();
    }

    /**
     * Gets the custom value associated with this Renderable at the given index.
     * 
     * @param index
     * @return
     */
    public Vector4 getCustomParameter(long index) {
        return renderable.getCustomParameter(index);
    }

    public Vector<Light> getLights() {
        return renderable.getLights();
    }

    /**
     * Overridden - see Renderable.
     * 
     * @return
     */
    public Material getMaterial() {
        return renderable.getMaterial();
    }

    /**
     * Gets the name of the Material in use by this instance.
     * 
     * @return
     */
    public String getMaterialName() {
        return getMaterialName(pInstance.getValue());
    }

    /**
     * Overridden - see Renderable.
     * 
     * @return
     */
    public boolean getNormaliseNormals() {
        return renderable.getNormaliseNormals();
    }

    /**
     * Overridden - see Renderable.
     * 
     * @return
     */
    public short getNumWorldTransforms() {
        return renderable.getNumWorldTransforms();
    }

    /**
     * Overridden, see Renderable.
     * 
     * @return
     */
    public int getRenderDetail() {
        return renderable.getRenderDetail();
    }

    /**
     * Gets whether this renderable's chosen detail level can be overridden
     * (downgraded) by the camera setting.
     * 
     * @return
     */
    public boolean getRenderDetailOverrideable() {
        return renderable.getRenderDetailOverrideable();
    }

    /**
     * Overridden - see Renderable.
     * 
     * @param op
     */
    public void getRenderOperation(RenderOperation op) {
        renderable.getRenderOperation(op);
    }

    /**
     * Overridden, see Renderable.
     * 
     * @param cam
     * @return
     */
    public float getSquaredViewDepth(Camera cam) {
        return renderable.getSquaredViewDepth(cam);
    }

    /**
     * Accessor method to read mesh data.
     * 
     * @param pInstance
     * @return
     */
    public SubMesh getSubMesh() {
        // TODO
        return null;
    }

    /**
     * Overridden - see Renderable.
     * 
     * @return
     */
    public Technique getTechnique() {
        return renderable.getTechnique();
    }

    public Quaternion getWorldOrientation() {
        return renderable.getWorldOrientation();
    }

    public Vector3 getWorldPosition() {
        return renderable.getWorldPosition();
    }

    /**
     * Overridden - see Renderable.
     * 
     * @param xform
     */
    public void getWorldTransforms(Matrix4 xform) {
        renderable.getWorldTransforms(xform);
    }

    /**
     * Returns whether or not this SubEntity is supposed to be visible.
     * 
     * @return
     */
    public boolean isVisible() {
        return isVisible(pInstance.getValue());
    }

    public void setCustomParameter(long index, Vector4 value) {
        renderable.setCustomParameter(index, value);
    }

    /**
     * Sets the name of the Material to be used.
     * 
     * @param name
     */
    public void setMaterialName(String name) {
        setMaterialName(pInstance.getValue(), name);
    }

    /**
     * Sets the rendering level (solid, wireframe) of this SubEntity.
     * 
     * @param renderDetail
     */
    public void setRenderDetail(int renderDetail) {

    }

    /**
     * Sets whether this renderable's chosen detail level can be overridden
     * (downgraded) by the camera setting.
     * 
     * @param override
     */
    public void setRenderDetailOverrideable(boolean override) {
        renderable.setRenderDetailOverrideable(override);
    }

    /**
     * Tells this SubEntity whether to be visible or not.
     * 
     * @param visible
     */
    public void setVisible(boolean visible) {
        // TODO
    }

    /**
     * Returns whether or not to use an 'identity' projection.
     * 
     * @return
     */
    public boolean useIdentityProjection() {
        return renderable.useIdentityProjection();
    }

    /**
     * Returns whether or not to use an 'identity' projection.
     * 
     * @return
     */
    public boolean useIdentityView() {
        return renderable.useIdentityView();
    }

}
