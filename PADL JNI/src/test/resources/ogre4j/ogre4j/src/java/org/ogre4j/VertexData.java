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
 * VertexData.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

public class VertexData extends NativeObject {

    VertexData(InstancePointer pInstance) {
        super(pInstance);
    }

    public VertexData() {
        super(new InstancePointer(createInstance()));
    }

    public void dispose() {
        dispose(pInstance.getValue());
        pInstance = null;
    }

    public void setVertexCount(int vertexCount) {
        setVertexCount(pInstance.getValue(), vertexCount);
    }

    public int getVertexCount() {
        return getVertexCount(pInstance.getValue());
    }

    public VertexDeclaration getVertexDeclaration() {
        InstancePointer ptrVertexDecal = new InstancePointer(
                getVertexDeclaration(pInstance.getValue()));
        return new VertexDeclaration(ptrVertexDecal);
    }

    public VertexBufferBinding getVertexBufferBinding() {
        InstancePointer ptrVertexBinding = new InstancePointer(
                getVertexBufferBinding(pInstance.getValue()));
        return new VertexBufferBinding(ptrVertexBinding);
    }

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    private static native int createInstance();

    private static native void dispose(int pInstance);

    private static native void setVertexCount(int pInstance, int vertexCount);

    private static native int getVertexCount(int pInstance);

    private static native int getVertexDeclaration(int pInstance);

    private static native int getVertexBufferBinding(int pInstance);
}
