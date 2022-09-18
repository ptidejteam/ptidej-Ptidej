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
 * Animation.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * Animation
 * 
 * @author Kai Klesatschke <kai.klesatschke@ogre4j.org>
 */
public class Animation extends NativeObject {

    public static final int IM_LINEAR = 0;

    public static final int IM_SPLINE = 1;

    public static final int RIM_LINEAR = 0;

    public static final int RIM_SPHERICAL = 1;

    private static native int createTrack(int pInstance, short handle);

    private static native int createTrack(int ptrSelf, short handle,
            int ptrCamNode);

    private static native void destroyAllTracks(int pInstance);

    private static native void destroyTrack(int pInstance, short handle);

    /**
     * Gets the default interpolation mode for all animations.
     * 
     * @return
     */
    public static int getDefaultInterpolationMode() {
        return getDefaultInterpolationModeImpl();
    }

    private static native int getDefaultInterpolationModeImpl();

    /**
     * Gets the default rotation interpolation mode for all animations.
     * 
     * @return
     */
    public static int getDefaultRotationInterpolationMode() {
        return getDefaultRotationInterpolationModeImpl();
    }

    private static native int getDefaultRotationInterpolationModeImpl();

    private static native int getInterpolationMode(int pInstance);

    private static native float getLength(int pInstance);

    private static native String getName(int pInstance);

    private static native int getNumTracks(int pInstance);

    private static native int getTrack(int pInstance, short handle);

    private static native void optimise(int ptrSelf);

    /**
     * Sets the default animation interpolation mode.
     * 
     * @param im
     */
    public static void setDefaultInterpolationMode(int im) {
        setDefaultInterpolationModeImpl(im);
    }

    private static native void setDefaultInterpolationModeImpl(int im);

    /**
     * Sets the default rotation interpolation mode.
     * 
     * @param im
     */
    public static void setDefaultRotationInterpolationMode(int im) {
        setDefaultRotationInterpolationModeImpl(im);
    }

    private static native void setDefaultRotationInterpolationModeImpl(int im);

    private static native void setInterpolationMode(int pInstance,
            int interpolationMode);

    /**
     * @param pInstance
     */
    protected Animation(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Creates an AnimationTrack.
     * 
     * @param handle
     * @return
     */
    public AnimationTrack createTrack(short handle) {
        int address = createTrack(pInstance.getValue(), handle);
        InstancePointer ptr = new InstancePointer(address);
        return new AnimationTrack(ptr);
    }

    /**
     * Creates a new AnimationTrack automatically associated with a Node.
     * 
     * @param handle
     * @param node
     * @return
     */
    public AnimationTrack createTrack(short handle, Node node) {
        int address = createTrack(pInstance.getValue(), handle, node
                .getInstancePtr().getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new AnimationTrack(ptr);
    }

    /**
     * Removes and destroys all tracks making up this animation.
     * 
     */
    public void destroyAllTracks() {
        destroyAllTracks(pInstance.getValue());
    }

    /**
     * Destroys the track with the given handle.
     * 
     * @param handle
     */
    public void destroyTrack(short handle) {
        destroyTrack(pInstance.getValue(), handle);
    }

    /**
     * Gets the current interpolation mode of this animation.
     * 
     * @return
     */
    public int getInterpolationMode() {
        return getInterpolationMode(pInstance.getValue());
    }

    /**
     * Gets the total length of the animation.
     * 
     * @return
     */
    public float getLength() {
        return getLength(pInstance.getValue());
    }

    /**
     * Gets the name of this animation.
     * 
     * @return
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    /**
     * Gets the number of AnimationTrack objects which make up this animation.
     * 
     * @return
     */
    public int getNumTracks() {
        return getNumTracks(pInstance.getValue());
    }

    /**
     * Gets a track by it's handle.
     * 
     * @param handle
     * @return
     */
    public AnimationTrack getTrack(short handle) {
        int address = getTrack(pInstance.getValue(), handle);
        InstancePointer ptr = new InstancePointer(address);
        return new AnimationTrack(ptr);
    }

    /**
     * Optimise an animation by removing unnecessary tracks and keyframes.
     * 
     */
    public void optimise() {
        optimise(pInstance.getValue());
    }

    /**
     * Tells the animation how to interpolate between keyframes.
     * 
     * @param interpolationMode
     */
    public void setInterpolationMode(int interpolationMode) {
        setInterpolationMode(pInstance.getValue(), interpolationMode);
    }

}
