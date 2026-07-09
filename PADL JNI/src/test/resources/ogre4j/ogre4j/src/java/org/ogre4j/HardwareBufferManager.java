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
 * HardwareBufferManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

/**
 * This class should be abstract. But for internal handling it is not possible
 * to declare it abstract. Never create a instance by calling de default
 * constructor of this class!
 * 
 * @author Gerhard Maier
 */
public class HardwareBufferManager extends NativeSingleton
{
    protected static InstancePointer pInstance;
    protected static HardwareBufferManager singleton;

    /**
     * Returns the singleton of the HardwareBufferManager.
     * 
     * @return Returns a HardwareBufferManager. This can be null if the object
     *         wasn't initialized before.
     * @throws OgreException
     *             if any error occurs while creating the singleton.
     */
    public static HardwareBufferManager getSingleton() throws OgreException
    {
        if (singleton != null && pInstance != null)
            return singleton;

        int ptrHwBufMgr = _getSingleton();
        if (ptrHwBufMgr == 0)
            return null;

        pInstance = new InstancePointer(ptrHwBufMgr);
        singleton = new HardwareBufferManager(pInstance);
        return singleton;
    }

    /**
     * Never use this constructor. It is for internal usage only!
     * @throws OgreException
     */
    public HardwareBufferManager() throws OgreException
    {
        if (singleton != null)
            throw new OgreException("HardwareBufferManager already initialized");
        isManualCreated = true;
    }

    /**
     * @param instance
     */
    protected HardwareBufferManager(InstancePointer instance)
    {
        HardwareBufferManager.pInstance = instance;
    }

    /**
     * TODO dispose description
     */
    public void dispose()
    {
        if(isManualCreated)
            dispose(pInstance.getValue());
        pInstance = null;
        singleton = null;
    }

    /**
     * TODO createVertexBuffer description
     * 
     * @param vertexSize
     * @param numVerts
     * @param usage
     * @return
     */
    public HardwareVertexBuffer createVertexBuffer(int vertexSize,
            int numVerts, int usage)
    {

        return createVertexBuffer(vertexSize, numVerts, usage, false);
    }

    /**
     * TODO createVertexBuffer description
     * 
     * @param vertexSize
     * @param numVerts
     * @param usage
     * @param useShadowBuffer
     * @return
     */
    public HardwareVertexBuffer createVertexBuffer(int vertexSize,
            int numVerts, int usage, boolean useShadowBuffer)
    {
        InstancePointer ptrVertexBuffer = new InstancePointer(
                createVertexBuffer(pInstance.getValue(), vertexSize, numVerts,
                        usage, useShadowBuffer));
        
        return new HardwareVertexBuffer(ptrVertexBuffer);
    }

    /**
     * TODO createIndexBuffer description
     * 
     * @param itype
     * @param numIndexes
     * @param usage
     * @return
     */
    public HardwareIndexBuffer createIndexBuffer(int itype, int numIndexes,
            int usage)
    {
        return createIndexBuffer(itype, numIndexes, usage, false);
    }

    /**
     * TODO createIndexBuffer description
     * 
     * @param itype
     * @param numIndexes
     * @param usage
     * @param useShadowBuffer
     * @return
     */
    public HardwareIndexBuffer createIndexBuffer(int itype, int numIndexes,
            int usage, boolean useShadowBuffer)
    {

        InstancePointer ptrVertexIndexBuffer = new InstancePointer(
                createIndexBuffer(pInstance.getValue(), itype, numIndexes,
                        usage, useShadowBuffer));
        return new HardwareIndexBuffer(ptrVertexIndexBuffer);
    }

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    protected static native int _getSingleton() throws OgreException;

    protected static native void dispose(int pInstance);

    /**
     * @return Returns the pointer to a Ogre::HardwareVertexBufferSharedPtr.
     */
    protected static native int createVertexBuffer(int pInstance,
            int vertexSize, int numVerts, int usage, boolean useShadowBuffer);

    /**
     * @return Returns the pointer to a Ogre::HardwareIndexBufferSharedPtr.
     */
    protected static native int createIndexBuffer(int pInstance, int itype,
            int numIndexes, int usage, boolean useShadowBuffer);
}
