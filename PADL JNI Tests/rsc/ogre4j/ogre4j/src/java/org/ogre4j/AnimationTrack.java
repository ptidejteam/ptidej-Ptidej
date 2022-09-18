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
 * AnimationTrack.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * AnimationTrack
 * 
 * @author Gerhard Maier
 */
public class AnimationTrack extends NativeObject {

    /**
     * @param pInstance
     */
    public AnimationTrack(InstancePointer pInstance) {
        super(pInstance);
    }

    public KeyFrame createKeyFrame(float time) {
        InstancePointer ptrKeyFrame = new InstancePointer(createKeyFrame(
                pInstance.getValue(), time));
        return new KeyFrame(ptrKeyFrame);
    }

    public Node getAssociatedNode() {
        int ptr = getAssociatedNode(pInstance.getValue());
        if (ptr == 0)
            return null;
        else
            return new Node(new InstancePointer(ptr));
    }

    private static native int createKeyFrame(int pInstance, float time);

    private static native int getAssociatedNode(int pInstance);
}
