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
 * ShadowRenderable.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/19 09:07:17 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import java.util.Vector;

/**
 * TODO ShadowRenderable type/class description.
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class ShadowRenderable extends NativeObject implements Renderable {

    private Renderable renderable;

    protected ShadowRenderable(InstancePointer ptr) {
        super(ptr);
        renderable = new RenderableImpl(ptr);
    }

    public void _updateCustomGpuParameter() {
        renderable._updateCustomGpuParameter();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getCastsShadows()
     */
    public boolean getCastsShadows() {
        return renderable.getCastsShadows();
    }

    public Vector<MovablePlane> getClipPlanes() {
        return renderable.getClipPlanes();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.IRenderable#getCustomParameter(long)
     */
    public Vector4 getCustomParameter(long index) {
        return renderable.getCustomParameter(index);
    }

    public Vector<Light> getLights() {
        return renderable.getLights();
    }

    public Material getMaterial() {
        return renderable.getMaterial();
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
     * @see org.ogre4j.IRenderable#getSquaredViewDepth(org.ogre4j.Camera)
     */
    public float getSquaredViewDepth(Camera cam) {
        return renderable.getSquaredViewDepth(cam);
    }

    public Technique getTechnique() {
        return renderable.getTechnique();
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
     * @see org.ogre4j.IRenderable#getWorldPosition()
     */
    public Vector3 getWorldPosition() {
        return renderable.getWorldPosition();
    }

    public void getWorldTransforms(Matrix4 xform) {
        renderable.getWorldTransforms(xform);
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

    public void setRenderDetailOverrideable(boolean override) {
        renderable.setRenderDetailOverrideable(override);
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

}
