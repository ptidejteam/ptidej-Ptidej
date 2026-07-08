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
 * KeyFrame.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.4 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * progress: 100% c++ public methods
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO Native Implementation
 */
public class KeyFrame extends NativeObject {

    private static native float[] getRotation(int ptrSelf);

    private static native float[] getScale(int ptrSelf);

    private static native float getTime(int pInstance);

    private static native float[] getTranslate(int ptrSelf);

    private static native void setRotation(int pInstance, float w, float x,
            float y, float z);

    private static native void setScale(int ptrSelf, float scaleX,
            float scaleY, float scaleZ);

    private static native void setTranslate(int pInstance, float x, float y,
            float z);

    /**
     * @param pInstance
     */
    protected KeyFrame(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Gets the rotation applied by this keyframe.
     * 
     * @return
     */
    public Quaternion getRotation() {
        return new Quaternion(getRotation(pInstance.getValue()));
    }

    /**
     * Gets the scaling factor applied by this keyframe.
     * 
     * @return
     */
    public Vector3 getScale() {
        return new Vector3(getScale(pInstance.getValue()));
    }

    /**
     * Gets the time of this keyframe in the animation sequence.
     * 
     * @return
     */
    public float getTime() {
        return getTime(pInstance.getValue());
    }

    /**
     * Gets the translation applied by this keyframe.
     * 
     * @return
     */
    public Vector3 getTranslate() {
        return new Vector3(getTranslate(pInstance.getValue()));
    }

    /**
     * Sets the rotation applied by this keyframe.
     * 
     * @param rot
     */
    public void setRotation(Quaternion rot) {
        setRotation(pInstance.getValue(), rot.w, rot.x, rot.y, rot.z);
    }

    /**
     * Sets the scaling factor applied by this keyframe to the animable object
     * at it's time index.
     * 
     * @param scale
     */
    public void setScale(Vector3 scale) {
        setScale(pInstance.getValue(), scale.x, scale.y, scale.z);
    }

    /**
     * Sets the translation associated with this keyframe.
     * 
     * @param vector3
     */
    public void setTranslate(Vector3 translate) {
        setTranslate(pInstance.getValue(), translate.x, translate.y,
                translate.z);
    }

}
