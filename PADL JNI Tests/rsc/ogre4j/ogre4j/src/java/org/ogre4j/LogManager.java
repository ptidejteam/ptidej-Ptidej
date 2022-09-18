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
 * LogManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;


/**
 * LogManager
 * 
 * TODO: implement JNI Material Methods TODO: implement JNI Light Methods
 * 
 * @author Gerhard Maier
 */
public class LogManager extends NativeSingleton
{
    private static InstancePointer pInstance;
    private static LogManager singleton;

    public static LogManager getSingleton() throws OgreException
    {
        if (singleton != null && pInstance != null)
            return singleton;
       
        int ptrLogMgr = _getSingleton();
        if(ptrLogMgr == 0)
            return null;
        
        pInstance = new InstancePointer(ptrLogMgr);
        singleton = new LogManager(pInstance);
        return singleton;
    }

    public LogManager() throws OgreException
    {
        if(singleton != null)
            throw new OgreException("LogManager already initialized");
        
        int ptrMeshMgr = _getSingleton();
        if (ptrMeshMgr == 0)
            ptrMeshMgr = createInstance();
        if (ptrMeshMgr != 0)
            pInstance = new InstancePointer(ptrMeshMgr);

        singleton = this;
        isManualCreated = true;
    }

    private LogManager(InstancePointer pInstance)
    {
        LogManager.pInstance = pInstance;
    }
    
    public void dispose()
    {
        if(isManualCreated)
            dispose(pInstance.getValue());
        singleton = null;
        pInstance = null;
    }

    //public void logMessage(String message, LogMessageLevel lml=LML_NORMAL, boolean maskDebug=false) 
    public void logMessage(String message) {
        _logMessage(message);
    }

    
//-----------------------------------------------------------------------------
//  NATIVES
//-----------------------------------------------------------------------------
    private static native int createInstance() throws OgreException;      
    private static native int _getSingleton() throws OgreException;
    private static native void dispose(int pInstance);
    private static native void _logMessage(String message);
}
