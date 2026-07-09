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
 * MeshSerializer.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;



/**
 * SceneManager
 * 
 * TODO: implement JNI Material Methods TODO: implement JNI Light Methods
 * 
 * @author Gerhard Maier
 */
public class MeshSerializer extends Serializer {
    
    public MeshSerializer()
    {
        super( new InstancePointer( createInstance() ) );
    }
    
    public MeshSerializer(InstancePointer pInstance) {
        super( pInstance );
    }
    
    public void dispose()
    {
        dispose(pInstance.getValue());
    }
    
    public void exportMesh(Mesh pMesh, String fileName)
    {
        exportMesh(pInstance.getValue(), pMesh.getInstancePtr().getValue(), fileName);
    }
    
    private static native int createInstance();
    private static native void dispose(int pInstance);
    private static native void exportMesh(int pInstance, int pMesh, String fileName);
}
