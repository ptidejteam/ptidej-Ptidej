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
 * Skeleton.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/13 19:45:34 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * Java wrapper for the Ogre::Skeleton class. Ogre always returns smart pointers
 * (ResourcePtr) for skeletons. To avoid a termination of a instance the use
 * count of the smart pointer is manual incremented. To revert this a Skeleton
 * must be disposed!.
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Skeleton extends Node {
    private static native int _createBone(int pInstance);

    private static native int _createBone(int pInstance, short handle);

    private static native int _createBone(int pInstance, String name);

    private static native int _createBone(int pInstance, String name,
            short handle);

    private static native void _updateTransforms(int pInstance);

    private static native int createAnimation(int pInstance, String name,
            float length);

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    private static native void dispose(int pInstance) throws OgreException;

    private static native int getAnimation(int pInstance, short index);

    private static native int getAnimation(int pInstance, String name);

    private static native int getBlendMode(int pInstance);

    private static native int getBone(int pInstance, short handle)
            throws OgreException;

    private static native int getBone(int pInstance, String name)
            throws OgreException;

    private static native String getName(int pInstance);

    private static native short getNumAnimations(int pInstance);

    private static native short getNumBones(int pInstance);

    private static native int getRootBone(int pInstance);

    private static native void removeAnimation(int pInstance, String name);

    private static native void reset(int pInstance);

    private static native void setBindingPose(int pInstance);

    private static native void setBlendMode(int pInstance, int state);

    public Skeleton() {
        this(null);
    }

    public Skeleton(InstancePointer pInstance) {
        super(pInstance);
    }

    public void _updateTransforms() {
        _updateTransforms(pInstance.getValue());
    }

    public Animation createAnimation(String name, float length) {
        return new Animation(new InstancePointer(createAnimation(pInstance
                .getValue(), name, length)));
    }

    public Bone createBone() {
        return new Bone(new InstancePointer(_createBone(pInstance.getValue())));
    }

    public Bone createBone(short handle) {
        return new Bone(new InstancePointer(_createBone(pInstance.getValue(),
                handle)));
    }

    public Bone createBone(String name) {
        return new Bone(new InstancePointer(_createBone(pInstance.getValue(),
                name)));
    }

    public Bone createBone(String name, short handle) {
        return new Bone(new InstancePointer(_createBone(pInstance.getValue(),
                name, handle)));
    }

    /**
     * Decrements the use count of a ResourcePtr for this skeleton instance.
     */
    public void dispose() throws OgreException {
        dispose(pInstance.getValue());
    }

    public Animation getAnimation(short index) {
        return new Animation(new InstancePointer(getAnimation(pInstance
                .getValue(), index)));
    }

    public Animation getAnimation(String name) {
        return new Animation(new InstancePointer(getAnimation(pInstance
                .getValue(), name)));
    }

    public int getBlendMode() {
        return getBlendMode(pInstance.getValue());
    }

    public Bone getBone(short handle) throws OgreException {
        return new Bone(new InstancePointer(getBone(pInstance.getValue(),
                handle)));
    }

    public Bone getBone(String name) throws OgreException {
        return new Bone(
                new InstancePointer(getBone(pInstance.getValue(), name)));
    }

    /**
     * Returns the name of the node.
     * 
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    public short getNumAnimations() {
        return getNumAnimations(pInstance.getValue());
    }

    public short getNumBones() {
        return getNumBones(pInstance.getValue());
    }

    public Bone getRootBone() {
        return new Bone(new InstancePointer(getRootBone(pInstance.getValue())));
    }

    public void removeAnimation(String name) {
        removeAnimation(pInstance.getValue(), name);
    }

    public void reset() {
        reset(pInstance.getValue());
    }

    public void setBindingPose() {
        setBindingPose(pInstance.getValue());
    }

    public void setBlendMode(int state) {
        setBlendMode(pInstance.getValue(), state);
    }
}
