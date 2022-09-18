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
 * IRenderable.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/25 07:27:22 $
 * $Author: yavin02 $
 */
package org.ogre4j;

import java.util.Vector;

/**
 * status 100% C++ classes, except _updateCustomGpuParameter
 * TODO IRenderable type/class description.
 * @author Kai Klesatschke <yavin@ogre4j.org> 
 */
public interface Renderable {
    /**
     * Method which reports whether this renderable would normally cast a
     * shadow.
     */
    public boolean getCastsShadows();

    /**
     * Gets the custom value associated with this Renderable at the given index.
     * 
     * @param index
     * @return
     */
    public Vector4 getCustomParameter(long index);

    /**
     * Retrieves a weak reference to the material this renderable object uses.
     * 
     * @return
     */
    public Material getMaterial();

    /**
     * Returns whether or not this Renderable wishes the hardware to normalise
     * normals.
     * 
     */
    public boolean getNormaliseNormals();

    /**
     * Returns the preferred rasterisation mode of this renderable.
     * 
     * @return
     */
    public int getRenderDetail();

    /**
     * Gets a list of lights, ordered relative to how close they are to this
     * renderable.
     * 
     * @return
     */
    public Vector<Light> getLights();

    /**
     * Sets whether this renderable's chosen detail level can be overridden
     * (downgraded) by the camera setting.
     * 
     * @param override
     */
    public void setRenderDetailOverrideable(boolean override);

    /**
     * Gets whether this renderable's chosen detail level can be overridden
     * (downgraded) by the camera setting.
     * 
     * @return
     */
    public boolean getRenderDetailOverrideable();

    /**
     * Returns the number of world transform matrices this renderable requires.
     * 
     * @return
     */
    public short getNumWorldTransforms();

    // TODO
    public void _updateCustomGpuParameter();

    /**
     * Gets the render operation required to send this object to the frame
     * buffer.
     * 
     */
    public void getRenderOperation(RenderOperation op);

    public Vector<MovablePlane> getClipPlanes();

    /**
     * Returns the camera-relative squared depth of this renderable.
     * 
     * @param cam
     * @return
     */
    public float getSquaredViewDepth(Camera cam);

    /**
     * Retrieves a pointer to the Material Technique this renderable object
     * uses.
     * 
     * @return
     */
    public Technique getTechnique();

    /**
     * Gets the worldspace orientation of this renderable; this is used in order
     * to more efficiently update parameters to vertex & fragment programs,
     * since inverting Quaterion and Vector in order to derive object-space
     * positions / directions for cameras and lights is much more efficient than
     * inverting a complete 4x4 matrix, and also eliminates problems introduced
     * by scaling.
     * 
     * @return
     */
    public Quaternion getWorldOrientation();

    /**
     * Gets the worldspace orientation of this renderable; this is used in order
     * to more efficiently update parameters to vertex & fragment programs,
     * since inverting Quaterion and Vector in order to derive object-space
     * positions / directions for cameras and lights is much more efficient than
     * inverting a complete 4x4 matrix, and also eliminates problems introduced
     * by scaling.
     * 
     * @return
     */
    public Vector3 getWorldPosition();

    /**
     * Gets the world transform matrix / matrices for this renderable object.
     * 
     */
    public void getWorldTransforms(Matrix4 xform);

    /**
     * Sets a custom parameter for this Renderable, which may be used to drive
     * calculations for this specific Renderable, like GPU program parameters.
     * 
     * @param index
     * @param value
     */
    public void setCustomParameter(long index, Vector4 value);

    /**
     * Returns whether or not to use an 'identity' projection.
     * 
     * @return
     */
    public boolean useIdentityProjection();

    /**
     * Returns whether or not to use an 'identity' projection.
     * 
     * @return
     */
    public boolean useIdentityView();
}
