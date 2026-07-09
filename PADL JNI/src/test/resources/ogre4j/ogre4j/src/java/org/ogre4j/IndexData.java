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
 * IndexData.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;


/**
 * TODO IndexData type/class description.
 * 
 * @author Kai Klesatschke <kai.klesatschke@netallied.de>
 */
public class IndexData extends NativeObject {

    public IndexData(InstancePointer pInstance) {
        super(pInstance);
    }

    public IndexData()
    {
        super(new InstancePointer(createInstance()));
    }
    
    public void dispose()
    {
        dispose(pInstance.getValue());
    }

    public void setIndexCount(int indexCount)
    {
        setIndexCount(pInstance.getValue(), indexCount);
    }

    public int getIndexCount()
    {
        return getIndexCount(pInstance.getValue());
    }
    
    public void setIndexStart(int indexCount)
    {
        setIndexStart(pInstance.getValue(), indexCount);
    }

    public int getIndexStart()
    {
        return getIndexStart(pInstance.getValue());
    }
    
    public void setIndexBuffer(HardwareIndexBuffer hwIndexBuf)
    {
        setIndexBuffer(pInstance.getValue(), hwIndexBuf.getInstancePtr().getValue());
    }

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    private static native int createInstance();
    private static native void dispose(int pInstance);
    private static native void setIndexCount(int pInstance, int vertexCount);
    private static native int getIndexCount(int pInstance);
    private static native void setIndexStart(int pInstance, int vertexCount);
    private static native int getIndexStart(int pInstance);
    private static native void setIndexBuffer(int pInstance, int hwIndexBuf);
}
