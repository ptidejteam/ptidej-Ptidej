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
 * SubMesh.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

// TODO was ist das mit dem vertexboneassignment???

/**
 * 100% c++ public methods except TODO BoneAssignmentIterator
 * getBoneAssignmentIterator (void)
 */
public class SubMesh extends NativeObject {
    private static native void addBoneAssignment(int pInstance,
            int vertexIndex, short boneIndex, float weight);

    private static native int getIndexData(int pInstance);

    private static native boolean getUseSharedVertices(int pInstance);

    private static native int getVertexData(int pInstance);

    private static native void setMaterialName(int pInstance, String matName);

    private static native void setUseSharedVertices(int pInstance,
            boolean sharedVert);

    private static native void setVertexData(int pInstance, int vertexData);

    SubMesh(InstancePointer pInstance) {
        super(pInstance);

    }

    /**
     * Returns a RenderOperation structure required to render this mesh.
     * 
     * @param rend
     * @param lodIndex
     */
    public void _getRenderOperation(RenderOperation rend, short lodIndex) {

    }

    /**
     * Assigns a vertex to a bone with a given weight, for skeletal animation.
     * 
     * @param vertBoneAssign
     */
    public void addBoneAssignment(VertexBoneAssignment vertBoneAssign) {
        addBoneAssignment(pInstance.getValue(), vertBoneAssign.vertexIndex,
                vertBoneAssign.boneIndex, vertBoneAssign.weight);
    }

    /**
     * Removes all bone assignments for this mesh.
     * 
     */
    public void clearBoneAssignments() {

    }

    public IndexData getIndexData() {
        return new IndexData(new InstancePointer(getIndexData(pInstance
                .getValue())));
    }

    public String getMaterialName() {
        return null;
    }

    public boolean getUseSharedVertices() {
        return getUseSharedVertices(pInstance.getValue());
    }

    public VertexData getVertexData() {
        return new VertexData(new InstancePointer(getVertexData(pInstance
                .getValue())));
    }

    /**
     * Returns true if a material has been assigned to the submesh, otherwise
     * returns false.
     * 
     * @return
     */
    public boolean isMatInitialised() {
        return false;
    }

    /**
     * Sets the name of the Material which this SubMesh will use.
     * 
     * @param matName
     */
    public void setMaterialName(String matName) {
        setMaterialName(pInstance.getValue(), matName);
    }

    public void setUseSharedVertices(boolean sharedVert) {
        setUseSharedVertices(pInstance.getValue(), sharedVert);
    }

    public void setVertexData(VertexData vertexData) {
        setVertexData(pInstance.getValue(), vertexData.getInstancePtr()
                .getValue());
    }

}
