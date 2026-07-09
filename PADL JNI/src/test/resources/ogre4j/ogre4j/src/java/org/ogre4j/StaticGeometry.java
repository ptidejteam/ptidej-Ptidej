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
 * StaticGeometry.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * STATUS: ALL public java methods present
 * 
 * @author Gerhard Maier <shinobi@ogre4j.org>
 */
public class StaticGeometry extends NativeObject {
    private static native void addEntity(int pInstance, int pEntity,
            float locationX, float locationY, float locationZ, float rotationW,
            float rotationX, float rotationY, float rotationZ, float scaleX,
            float scaleY, float scaleZ) throws OgreException;

    private static native void build(int pInstance) throws OgreException;

    private static native int createInstance(int pOwner, String name);

    private static native void setOrigin(int pInstance, float originX,
            float originY, float originZ);

    private static native void setRegionDimensions(int pInstance,
            float dimensionX, float dimensionY, float dimensionZ);

    /**
     * @param pInstance
     */
    protected StaticGeometry(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * 
     */
    public StaticGeometry(SceneManager owner, String name) {
        super(new InstancePointer(createInstance(owner.getInstancePtr()
                .getValue(), name)));
    }

    /**
     * Adds an Entity to the static geometry.
     * 
     * @param ent
     * @param position
     * @param orientation
     * @param scale
     */
    public void addEntity(Entity ent, Vector3 position, Quaternion orientation,
            Vector3 scale) {
        // TODO
    }

    /**
     * @deprecated
     */
    public void addEntity(Entity entity, Vector3f v3Location)
            throws OgreException {

        addEntity(entity, v3Location, new Quat4f(0, 0, 0, 1));
    }

    /**
     * @deprecated
     * @param entity
     * @param v3Location
     * @param qRotation
     * @throws OgreException
     */
    public void addEntity(Entity entity, Vector3f v3Location, Quat4f qRotation)
            throws OgreException {

        addEntity(entity, v3Location, qRotation, new Vector3f(1, 1, 1));
    }

    public void addEntity(Entity entity, Vector3f location, Quat4f rotation,
            Vector3f scale) throws OgreException {
        addEntity(pInstance.getValue(), entity.getInstancePtr().getValue(),
                location.x, location.y, location.z, rotation.w, rotation.x,
                rotation.y, rotation.z, scale.x, scale.y, scale.z);
    }

    /**
     * Adds all the Entity objects attached to a SceneNode and all it's children
     * to the static geometry.
     * 
     */
    public void addSceneNode(SceneNode node) {

    }

    /**
     * Build the geometry.
     * 
     */
    public void build() throws OgreException {
        build(pInstance.getValue());
    }

    /**
     * Destroys all the built geometry state (reverse of build).
     * 
     */
    public void destroy() {
        // TODO
    }

    /**
     * Will the geometry from this object cast shadows?
     * 
     * @return
     */
    public boolean getCastShadows() {
        // TODO
        return false;
    }

    /**
     * Get the name of this object.
     * 
     * @return
     */
    public String getName() {
        // TODO
        return null;
    }

    /**
     * Dump the contents of this StaticGeometry to a file for diagnostic
     * purposes.
     * 
     * @param fileName
     */
    public void dump(String fileName) {
        // TODO
    }

    /**
     * Gets the origin of this geometry.
     * 
     */
    public Vector3 getOrigin() {
        // TODO
        return null;
    }

    /**
     * Gets the size of a single batch of geometry.
     * 
     * @return
     */
    public Vector3 getRegionDimensions() {
        // TODO
        return null;
    }

    /**
     * Gets the distance at which batches are no longer rendered
     * 
     * @return
     */
    public float getRenderingDistance() {
        // TODO
        return 0.0f;
    }

    /**
     * 
     * @see RenderQueue for a list of constants
     * @return
     */
    public int getRenderQueueGroup() {
        // TODO
        return 0;
    }

    /**
     * Gets the squared distance at which batches are no longer rendered.
     * 
     */
    public float getSquaredRenderingDistance() {
        // TODO
        return 0.0f;
    }

    /**
     * Are the batches visible?
     * 
     */
    public float isVisible() {
        // TODO
        return 0.0f;
    }

    /**
     * Clears any of the entities / nodes added to this geometry and destroys
     * anything which has already been built.
     * 
     */
    public void reset() {
        // TODO
    }

    /**
     * Sets whether this geometry should cast shadows.
     * 
     * @param castShadows
     */
    public void setCastShadows(boolean castShadows) {
        // TODO
    }

    /**
     * Sets the origin of the geometry.
     * 
     */
    public void setOrigin(Vector3 origin) {
        // TODO
    }

    /**
     * Sets the size of a single region of geometry.
     * 
     * @param size
     */
    public void setRegionDimensions(Vector3 size) {
        // TODO
    }

    /*
     * REGION
     */
    public void setRegionDimensions(Vector3f dimension) {
        setRegionDimensions(pInstance.getValue(), dimension.x, dimension.y,
                dimension.z);
    }

    /**
     * Sets the distance at which batches are no longer rendered.
     * 
     * @param dist
     */
    public void setRenderingDistance(float dist) {
        // TODO

    }

    /**
     * Sets the render queue group this object will be rendered through.
     * 
     * @see RenderQueue for a list of constants
     */
    public void setRenderQueueGroup(int queueID) {
        // TODO
    }

    /**
     * Hides or shows all the batches.
     * 
     * @param visible
     */
    public void setVisible(boolean visible) {
        // TODO
    }
}
