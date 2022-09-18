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
 * DefaultHardwareBufferManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

/**
 * TODO DefaultHardwareBufferManager type/class description.
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class DefaultHardwareBufferManager extends HardwareBufferManager
{
    public DefaultHardwareBufferManager() throws OgreException
    {
        if(singleton != null)
            throw new OgreException("DefaultHardwareBufferManager already initialized");
        
        int ptrHwBufMgr = _getSingleton();
        if (ptrHwBufMgr == 0)
            ptrHwBufMgr = createInstance();
        if (ptrHwBufMgr != 0)
            pInstance = new InstancePointer(ptrHwBufMgr);

        singleton = this;
        isManualCreated = true;
    }

    /**
     * @param instance
     */
    @SuppressWarnings("unused")
    private DefaultHardwareBufferManager(InstancePointer instance) throws OgreException
    {
        pInstance = instance;
    }

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    private static native int createInstance() throws OgreException;
}
