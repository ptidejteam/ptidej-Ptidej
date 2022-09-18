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
 * Entity.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.4 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

/**
 * TODO Entity type/class description. to getname
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Entity extends MovableObject {

    private static native void attachObjectToBone(int pInstance, String name,
            int pMovableObject) throws OgreException;

    private static native void attachObjectToBone(int pInstance,
            String boneName, int value, float w, float x, float y, float z,
            float x2, float y2, float z2) throws OgreException;

    private static native int clone(int pInstance, String newName);

    private static native int detachObjectFromBone(int pInstance,
            String movableObjectName) throws OgreException;

    private static native int getAnimationState(int pInstance, String name)
            throws OgreException;

    private static native int getMesh(int pInstance);

    private static native int getNumSubEntities(int pInstance);

    private static native int getSkeleton(int pInstance);

    private static native int getSubEntity(int pInstance, int index);

    private static native int getSubEntity(int pInstance, String name);

    private static native boolean hasSkeleton(int pInstance);

    private static native void setDisplaySkeleton(int pInstance, boolean display);

    private static native void setMaterialName(int pInstance, String name);

    /**
     * Private constructor (instances cannot be created directly).
     * 
     */
    private Entity() {
        super(null);
    }

    /**
     * @param pInstance
     */
    protected Entity(InstancePointer pInstance) {
        super(pInstance);
    }

    public void attachObjectToBone(String boneName, MovableObject obj)
            throws OgreException {
        attachObjectToBone(pInstance.getValue(), boneName, obj.getInstancePtr()
                .getValue());
    }

    /**
     * @param boneName
     * @param removedMovable
     * @param finalOrientation
     * @param finalTranslation
     */
    public void attachObjectToBone(String boneName, MovableObject pMovable,
            Quat4f finalOrientation, Vector3f finalTranslation)
            throws OgreException {

        attachObjectToBone(pInstance.getValue(), boneName, pMovable
                .getInstancePtr().getValue(), finalOrientation.w,
                finalOrientation.x, finalOrientation.y, finalOrientation.z,
                finalTranslation.x, finalTranslation.y, finalTranslation.z);
    }

    /**
     * Clones this entity and returns a pointer to the clone.
     * 
     * @param newName
     * @return
     */
    public Entity clone(String newName) {
        InstancePointer ptrEntity = new InstancePointer(clone(pInstance
                .getValue(), newName));
        return new Entity(ptrEntity);
    }

    public MovableObject detachObjectFromBone(String movableName)
            throws OgreException {
        int address = detachObjectFromBone(pInstance.getValue(), movableName);
        InstancePointer ptr = new InstancePointer(address);
        return new MovableObject(ptr);
    }

    public AnimationState getAnimationState(String name) throws OgreException {

        InstancePointer ptrAnimState = new InstancePointer(getAnimationState(
                pInstance.getValue(), name));
        return new AnimationState(ptrAnimState);
    }

    /**
     * merge all the child object Bounds a return it
     * 
     * @return
     */
    public AxisAlignedBox getChildObjectsBoundingBox() {
        return null;
    }

    public Mesh getMesh() {
        int address = getMesh(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new Mesh(ptr);
    }

    /**
     * Retrieves the number of SubEntity objects making up this entity.
     * 
     * @return
     */
    public int getNumSubEntities() {
        return getNumSubEntities(pInstance.getValue());
    }

    public SkeletonInstance getSkeleton() {

        InstancePointer ptrSkeletonInstance = new InstancePointer(
                getSkeleton(pInstance.getValue()));
        return new SkeletonInstance(ptrSkeletonInstance);
    }

    /**
     * Gets a pointer to a SubEntity, ie a part of an Entity.
     * 
     * @param index
     * @return
     */
    public SubEntity getSubEntity(int index) {
        int address = getSubEntity(pInstance.getValue(), index);
        InstancePointer ptr = new InstancePointer(address);
        return new SubEntity(ptr);
    }

    /**
     * Gets a pointer to a SubEntity by name.
     * 
     * @param name
     * @return
     */
    public SubEntity getSubEntity(String name) {
        int address = getSubEntity(pInstance.getValue(), name);
        InstancePointer ptr = new InstancePointer(address);
        return new SubEntity(ptr);
    }

    public boolean hasSkeleton() {
        return hasSkeleton(pInstance.getValue());
    }

    public void setDisplaySkeleton(boolean display) {
        setDisplaySkeleton(pInstance.getValue(), display);
    }

    /**
     * Sets the material to use for the whole of this entity.
     * 
     * @param name
     */
    public void setMaterialName(String name) {
        setMaterialName(pInstance.getValue(), name);
    }

}
