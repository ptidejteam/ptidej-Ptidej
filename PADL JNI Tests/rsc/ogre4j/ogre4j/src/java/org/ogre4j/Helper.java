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
 * Helper.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/07/29 09:58:55 $
 * $Author: yavin02 $
 */
package org.ogre4j;

import javax.vecmath.Point3f;

/**
 * TODO Helper type/class description.
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Helper
{
    public static long getDirectXVersion() {
        return _getDirectXVersion();
    }
    
    public static void createSubMesh(Mesh mesh, HardwareBufferManager hwBufMgr, float[] vertices, int[] indices, boolean normals, boolean diffuse) throws OgreException
    {
        int n = 3;
        if(normals)
            n += 3;
        if(diffuse)
            n += 3;
        
        int nVertices = vertices.length/n;
                      
        SubMesh sub = mesh.createSubMesh();
        VertexData vd = new VertexData();      
        vd.setVertexCount(nVertices);
        sub.setVertexData(vd);
        
        VertexDeclaration decl = sub.getVertexData().getVertexDeclaration();
        int offset = 0;
        decl.addElement(0, offset, HardwareVertexBuffer.VET_FLOAT3,
                HardwareVertexBuffer.VES_POSITION);
        
        if(normals)
        {
            offset += VertexElement.getTypeSize(HardwareVertexBuffer.VET_FLOAT3);
            decl.addElement(0, offset, HardwareVertexBuffer.VET_FLOAT3,
                    HardwareVertexBuffer.VES_NORMAL);
            offset += VertexElement.getTypeSize(HardwareVertexBuffer.VET_FLOAT3);
        }
        
        if(diffuse)
        {
            decl.addElement(0, offset, HardwareVertexBuffer.VET_FLOAT3,
                    HardwareVertexBuffer.VES_DIFFUSE);
            offset += VertexElement.getTypeSize(HardwareVertexBuffer.VET_FLOAT3);
        }

        // HardwareVertexBuffer        
        HardwareVertexBuffer hwVB = hwBufMgr.createVertexBuffer(offset, vd
                .getVertexCount(), HardwareBuffer.HBU_STATIC_WRITE_ONLY);
        
        long sizeInBytes = hwVB.getSizeInBytes();
        hwVB.writeData(0, sizeInBytes, vertices, true);

        VertexBufferBinding vbBind = sub.getVertexData().getVertexBufferBinding();
        vbBind.setBinding(0, hwVB);

        // HardwareIndexBuffer
        HardwareIndexBuffer ibuf = hwBufMgr.createIndexBuffer(
                HardwareIndexBuffer.IT_32BIT, indices.length,
                HardwareBuffer.HBU_STATIC_WRITE_ONLY, false);
        
        sizeInBytes = ibuf.getSizeInBytes();
        ibuf.writeData(0, sizeInBytes, indices, true);

        sub.setUseSharedVertices(false);
        IndexData id = sub.getIndexData();
        id.setIndexBuffer(ibuf);
        id.setIndexCount(indices.length);
        id.setIndexStart(0);
        
        sub.setMaterialName("triangle");
    }
    
    public static AxisAlignedBox findBounds(float[] vertices, int nVertices)
    {
        Point3f min = null;
        Point3f max = null;
        // find bounds
        int n = vertices.length/nVertices;
        for(int i=0; i<nVertices; i++)
        {
            n = i*n;
            if(min == null)
                min = new Point3f( vertices[n+0], vertices[n+1],vertices[n+2] );
            if(max == null)
                max = new Point3f( vertices[n+0], vertices[n+1],vertices[n+2] );

            if(vertices[n+0]<min.x  )
                min.x = vertices[n+0];
            else if(vertices[n+0]>max.x )
                max.x = vertices[n+0];
            
            if(vertices[n+1]<min.y)
                min.y = vertices[n+1];
            else if(vertices[n+1]>max.y)
                max.y = vertices[n+1];
            
            if(vertices[n+2]<min.z)
                min.z = vertices[n+2];
            else if(vertices[n+2]>max.z)
                max.z = vertices[n+2];
        }
        return new AxisAlignedBox(min, max);
    }
    
    
    //-------------------------------------------------------------------------
    // NATIVES
    //-------------------------------------------------------------------------
    private static native long _getDirectXVersion();
}
