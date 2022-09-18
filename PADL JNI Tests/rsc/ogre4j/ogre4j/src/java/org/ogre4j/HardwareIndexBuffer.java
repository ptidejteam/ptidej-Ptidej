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
 * HardwareIndexBuffer.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;


/**
 * HardwareIndexBuffer
 * 
 * @author Gerhard Maier
 */
public class HardwareIndexBuffer extends HardwareBuffer {

    //enum IndexType { IT_16BIT, IT_32BIT } 
    public static final int
        IT_16BIT = 0,
        IT_32BIT = 1;
    
    /**
     * @param pInstance
     */
    public HardwareIndexBuffer(InstancePointer pInstance)
    {
        super(pInstance);
    }   
    
    public void dispose()
    {
        dispose(pInstance.getValue());
        pInstance = null;
    }
    
    public void writeData(int offset,long length, int[] pSource) throws OgreException {
        
        writeData(offset, length, pSource, false);
    }
    
    public void writeData(int offset, long length, int[] pSource, boolean discardWholeBuffer) throws OgreException
    {
        writeData(pInstance.getValue(), offset, length, pSource, discardWholeBuffer);
    }
    
    public void writeData(int offset,long length, short[] pSource) throws OgreException {
        
        writeData(offset, length, pSource, false);
    }
    
    public void writeData(int offset, long length, short[] pSource, boolean discardWholeBuffer) throws OgreException
    {
        writeData(pInstance.getValue(), offset, length, pSource, discardWholeBuffer);
    }
    
    /**
     * Overides org.ogre4j.HardwareBuffer#getSizeInBytes() because the instance
     * pointer of this objects is a shared pointer. Accounting to this it is not
     * valid to call the impl. of the super class.
     * @see org.ogre4j.HardwareBuffer#getSizeInBytes()
     */
    public long getSizeInBytes()
    {
        return getSizeInBytes(pInstance.getValue());
    }
    //-------------------------------------------------------------------------
    // NATIVES
    //-------------------------------------------------------------------------
    private static native void dispose(int pInstance);
    private static native void writeData(int pInstance, int offset, long length, int[] pSource, boolean discardWholeBuffer) throws OgreException;
    private static native void writeData(int pInstance, int offset, long length, short[] pSource, boolean discardWholeBuffer) throws OgreException;
    private static native long getSizeInBytes(int pInstance);
}
