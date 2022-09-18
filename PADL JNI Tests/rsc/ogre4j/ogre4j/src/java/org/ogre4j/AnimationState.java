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
 * AnimationState.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/20 03:01:56 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * AnimationState
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class AnimationState extends ControllerValue {

    private static native void addTime(int pInstance, float time);

    private static native int createInstance(String animName, float timePos,
            float length, float weight, boolean enabled);

    private static native void dispose(int pInstance);

    private static native String getAnimationName(int pInstance);

    private static native boolean getEnabled(int pInstance);

    private static native float getLength(int pInstance);

    private static native boolean getLoop(int pInstance);

    private static native float getTimePosition(int pInstance);

    private static native float getWeight(int pInstance);

    private static native void setAnimationName(int pInstance, String name);

    private static native void setEnabled(int pInstance, boolean enabled);

    private static native void setLength(int pInstance, float length);

    private static native void setLoop(int pInstance, boolean loop);

    private static native void setTimePosition(int pInstance, float timePos);

    private static native void setWeight(int pInstance, float weight);

    /**
     * @param ptrAnimState
     */
    protected AnimationState(InstancePointer ptrAnimState) {
        super(ptrAnimState);
    }

    public AnimationState(String animName, float timePos, float length,
            float weight, boolean enabled) {
        super(new InstancePointer(createInstance(animName, timePos, length,
                weight, enabled)));
    }

    public void addTime(float time) {
        addTime(pInstance.getValue(), time);
    }

    public void dispose() {
        dispose(pInstance.getValue());
    }

    public String getAnimationName() {
        return getAnimationName(pInstance.getValue());
    }

    public boolean getEnabled() {
        return getEnabled(pInstance.getValue());
    }

    public float getLength() {
        return getLength(pInstance.getValue());
    }

    public boolean getLoop() {
        return getLoop(pInstance.getValue());
    }

    public float getTimePosition() {
        return getTimePosition(pInstance.getValue());
    }

    public float getWeight() {
        return getWeight(pInstance.getValue());
    }

    public void setAnimationName(String name) {
        setAnimationName(pInstance.getValue(), name);
    }

    public void setEnabled(boolean enabled) {
        setEnabled(pInstance.getValue(), enabled);
    }

    public void setLength(float length) {
        setLength(pInstance.getValue(), length);
    }

    public void setLoop(boolean loop) {
        setLoop(pInstance.getValue(), loop);
    }

    public void setTimePosition(float timePos) {
        setTimePosition(pInstance.getValue(), timePos);
    }

    public void setWeight(float weight) {
        setWeight(pInstance.getValue(), weight);
    }
}
