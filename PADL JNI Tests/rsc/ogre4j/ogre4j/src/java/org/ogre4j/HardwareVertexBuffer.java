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
 * HardwareVertexBuffer.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

/**
 * HardwareVertexBuffer
 * 
 * @author Gerhard Maier
 */
public class HardwareVertexBuffer extends HardwareBuffer
{

    // enum VertexElementSemantic
    public static int VES_POSITION = 1, VES_BLEND_WEIGHTS = 2,
            VES_BLEND_INDICES = 3, VES_NORMAL = 4, VES_DIFFUSE = 5,
            VES_SPECULAR = 6, VES_TEXTURE_COORDINATES = 7, VES_BINORMAL = 8,
            VES_TANGENT = 9;

    // enum VertexElementType
    public static int VET_FLOAT1   = 0, VET_FLOAT2 = 1, VET_FLOAT3 = 2,
            VET_FLOAT4 = 3, VET_COLOUR = 4, VET_SHORT1 = 5, VET_SHORT2 = 6,
            VET_SHORT3 = 7, VET_SHORT4 = 8, VET_UBYTE4 = 9;

    public HardwareVertexBuffer(InstancePointer pInstance)
    {

        super(pInstance);
    }

    public void dispose()
    {
        dispose(pInstance.getValue());
        pInstance = null;
    }

    // TODO:size_t getVertexSize (void) const

    // TODO:size_t getNumVertices (void) const

    // TODO:virtual void * lock (size_t offset, size_t length, LockOptions
    // options)

    // TODO:void * lock (LockOptions options)

    // TODO:virtual void unlock (void)

    // TODO:virtual void readData (size_t offset, size_t length, void *pDest)=0

    // TODO:virtual void writeData (size_t offset, size_t length, const void
    // *pSource, bool discardWholeBuffer=false)=0
    public void writeData(int offset, long length, float[] pSource)
    {

        writeData(offset, length, pSource, false);
    }

    public void writeData(int offset, long length, float[] pSource,
            boolean discardWholeBuffer)
    {
        writeData(pInstance.getValue(), offset, length, pSource,
                discardWholeBuffer);
    }

    // TODO:virtual void copyData (HardwareBuffer &srcBuffer, size_t srcOffset,
    // size_t dstOffset, size_t length, bool discardWholeBuffer=false)

    // TODO:virtual void _updateFromShadow (void)

    // TODO:Usage getUsage (void) const

    // TODO:bool isSystemMemory (void) const

    // TODO:bool hasShadowBuffer (void) const

    // TODO:bool isLocked (void) const

    // TODO:void suppressHardwareUpdate (bool suppress)

    /**
     * Overides org.ogre4j.HardwareBuffer#getSizeInBytes() because the instance
     * pointer of this objects is a shared pointer. Accounting to this it is not
     * valid to call the impl. of the super class.
     * 
     * @see org.ogre4j.HardwareBuffer#getSizeInBytes()
     */
    public long getSizeInBytes()
    {
        return getSizeInBytes(pInstance.getValue());
    }

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    private static native void writeData(int pInstance, int offset,
            long length, float[] pSource, boolean discardWholeBuffer);

    private static native void dispose(int pInstance);

    private static native long getSizeInBytes(int pInstance);
}
