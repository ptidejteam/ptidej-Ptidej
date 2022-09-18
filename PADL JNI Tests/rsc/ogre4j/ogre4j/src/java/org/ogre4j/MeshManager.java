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
 * MeshManager.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.4 $
 * $Date: 2005/08/19 09:07:17 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import javax.vecmath.Vector3f;

/**
 * SceneManager
 * 
 * TODO: implement JNI Material Methods TODO: implement JNI Light Methods
 * 
 * @author Gerhard Maier
 */
public class MeshManager extends ResourceManager implements
        ManualResourceLoader {
    private static native int _createManual(String meshName, String groupName,
            int loader) throws OgreException;

    private static native int _getByName(String name) throws OgreException;;

    private static native int _load(String filename, String groupName,
            int vertexBufferUsage, int indexBufferUsage,
            boolean vertexBufferShadowed, boolean indexBufferShadowed)
            throws OgreException;

    private static native int createCppInstance() throws OgreException;

    private native int createPlane(int ptrSelf, String name, String groupName,
            float planeX, float planeY, float planeZ, float planeD,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile,
            float upX, float upY, float upZ, int vertexBufferUsage,
            int indexBufferUsage, boolean vertexShadowBuffer,
            boolean indexShadowBuffer);

    private static native int createPlane(String name, String groupName,
            int pPlane, float width, float height, int xsegments,
            int ysegments, boolean normals, int numTexCoordSets, float uTile,
            float vTile, float upVectorX, float upVectorY, float upVectorZ)
            throws OgreException;

    private static native int createPlane(String name, String groupName,
            int pPlane, float width, float height, int xsegments,
            int ysegments, boolean normals, int numTexCoordSets, float uTile,
            float vTile, float upVectorX, float upVectorY, float upVectorZ,
            int vertexBufferUsage, int indexBufferUsage,
            boolean vertexShadowBuffer, boolean indexShadowBuffer)
            throws OgreException;

    /**
     * Returns the singleton of the MeshManager.
     * 
     * @return Returns a MeshManager. This can be null if the object wasn't
     *         initialized before.
     * @throws OgreException
     *             if any error occurs while creating the singleton.
     */
    public static MeshManager getSingleton() throws OgreException {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new MeshManager(ptr);
    }

    private static native int getSingletonImpl();

    /**
     * Creates a new MeshManager singleton. If created the singleton by this
     * constructor call dispose if you don't need the singleton anymore.
     * 
     * @throws OgreException
     *             if the singleton exists.
     */
    public MeshManager() throws OgreException {
        super(new InstancePointer(createCppInstance()));
    }

    protected MeshManager(InstancePointer ptr) {
        super(ptr);
    }

    public void createManual(Mesh mesh, String meshName, String groupName)
            throws OgreException {
        InstancePointer ptrMesh = new InstancePointer(_createManual(meshName,
                groupName, 0));
        Mesh msh = new Mesh(ptrMesh);
        if (mesh == null)
            msh.dispose();
        else
            mesh.pInstance = msh.pInstance;
    }

    public void createManual(String meshName, String groupName)
            throws OgreException {
        createManual(null, meshName, groupName);
    }

    /**
     * @see #createPlane(String, String, MovablePlane, float, float, int, int,
     *      boolean, int, float, float, Vector3f, int, int, boolean, boolean)
     */
    public void createPlane(Mesh mesh, String name, String groupName,
            MovablePlane plane, float width, float height) throws OgreException {
        // int meshPtr = createPlane(name, groupName,
        // plane.pInstance.getValue(),
        // width, height);

        // FIXME
        int meshPtr = 0;
        if (meshPtr == 0)
            return;

        // if the programmer doesn't need the java instance
        if (mesh == null)
            new Mesh(new InstancePointer(meshPtr)).dispose();
        else
            mesh.pInstance = new InstancePointer(meshPtr);
    }

    /**
     * @see #createPlane(String, String, MovablePlane, float, float, int, int,
     *      boolean, int, float, float, Vector3f, int, int, boolean, boolean)
     */
    public void createPlane(Mesh mesh, String name, String groupName,
            MovablePlane plane, float width, float height, int xsegments,
            int ysegments, boolean normals, int numTexCoordSets, float uTile,
            float vTile, Vector3f upVector) throws OgreException {
        int meshPtr = createPlane(name, groupName, plane.pInstance.getValue(),
                width, height, xsegments, ysegments, normals, numTexCoordSets,
                uTile, vTile, upVector.x, upVector.y, upVector.z);

        if (meshPtr == 0)
            return;

        // if the programmer doesn't need the java instance
        if (mesh == null)
            new Mesh(new InstancePointer(meshPtr)).dispose();
        else
            mesh.pInstance = new InstancePointer(meshPtr);
    }

    /**
     * Creates a basic plane, by default majoring on the x/y axes facing
     * positive Z.
     * 
     * @param mesh
     *            The java mesh object to save the instance pointer. This can be
     *            null if not needed.
     * @param name
     *            The name to give the resulting mesh
     * @param groupName
     *            The name of the resource group to assign the mesh to
     * @param plane
     *            The orientation of the plane and distance from the origin
     * @param width
     *            The width of the plane in world coordinates
     * @param height
     *            The height of the plane in world coordinates
     * @param xsegments
     *            The number of segements to the plane in the x direction
     * @param ysegments
     *            The number of segements to the plane in the y direction
     * @param normals
     *            If true, normals are created perpendicular to the plane
     * @param numTexCoordSets
     *            The number of 2D texture coordinate sets created - by default
     *            the corners are created to be the corner of the texture.
     * @param uTile
     *            The number of times the texture should be repeated in the u
     *            direction
     * @param vTile
     *            The number of times the texture should be repeated in the v
     *            direction
     * @param upVector
     *            The 'Up' direction of the plane.
     * @param vertexBufferUsage
     *            The usage flag with which the vertex buffer for this plane
     *            will be created
     * @param indexBufferUsage
     *            The usage flag with which the index buffer for this plane will
     *            be created
     * @param vertexShadowBuffer
     *            If this flag is set to true, the vertex buffer will be created
     *            with a system memory shadow buffer, allowing you to read it
     *            back more efficiently than if it is in hardware
     * @param indexShadowBuffer
     *            If this flag is set to true, the index buffer will be created
     *            with a system memory shadow buffer, allowing you to read it
     *            back more efficiently than if it is in hardware
     * @return
     * @throws OgreException
     */
    public void createPlane(Mesh mesh, String name, String groupName,
            Plane plane, float width, float height, int xsegments,
            int ysegments, boolean normals, int numTexCoordSets, float uTile,
            float vTile, Vector3f upVector, int vertexBufferUsage,
            int indexBufferUsage, boolean vertexShadowBuffer,
            boolean indexShadowBuffer) throws OgreException {
        // int meshPtr = createPlane(name, groupName,
        // plane.pInstance.getValue(),
        // width, height, xsegments, ysegments, normals, numTexCoordSets,
        // uTile, vTile, upVector.x, upVector.y, upVector.z,
        // vertexBufferUsage, indexBufferUsage, vertexShadowBuffer,
        // indexShadowBuffer);
        // FIXME
        int meshPtr = 0;
        if (meshPtr == 0)
            return;

        // if the programmer doesn't need the java instance
        if (mesh == null)
            new Mesh(new InstancePointer(meshPtr)).dispose();
        else
            mesh.pInstance = new InstancePointer(meshPtr);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height) {
        return createPlane(name, groupName, plane, width, height, 1);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments) {
        return createPlane(name, groupName, plane, width, height, xsegments, 1);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, true);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, 1);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, 1.0f);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, uTile, 1.0f);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, uTile, vTile,
                Vector3.NEGATIVE_UNIT_Y);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile,
            Vector3 upVector) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, uTile, vTile, upVector,
                HardwareBuffer.HBU_STATIC_WRITE_ONLY);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile,
            Vector3 upVector, int vertexBufferUsage) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, uTile, vTile, upVector,
                vertexBufferUsage, HardwareBuffer.HBU_STATIC_WRITE_ONLY);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile,
            Vector3 upVector, int vertexBufferUsage, int indexBufferUsage) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, uTile, vTile, upVector,
                vertexBufferUsage, indexBufferUsage, true);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile,
            Vector3 upVector, int vertexBufferUsage, int indexBufferUsage,
            boolean vertexShadowBuffer) {
        return createPlane(name, groupName, plane, width, height, xsegments,
                ysegments, normals, numTexCoordSets, uTile, vTile, upVector,
                vertexBufferUsage, indexBufferUsage, vertexShadowBuffer, true);
    }

    public Mesh createPlane(String name, String groupName, Plane plane,
            float width, float height, int xsegments, int ysegments,
            boolean normals, int numTexCoordSets, float uTile, float vTile,
            Vector3 upVector, int vertexBufferUsage, int indexBufferUsage,
            boolean vertexShadowBuffer, boolean indexShadowBuffer) {
        int address = createPlane(pInstance.getValue(), name, groupName,
                plane.normal.x, plane.normal.y, plane.normal.z, plane.d, width,
                height, xsegments, ysegments, normals, numTexCoordSets, uTile,
                vTile, upVector.x, upVector.y, upVector.z, vertexBufferUsage,
                indexBufferUsage, vertexShadowBuffer, indexShadowBuffer);
        InstancePointer ptr = new InstancePointer(address);
        return new Mesh(ptr);
    }

    public void getByName(Mesh mesh, String name) throws OgreException {

        int ptr = _getByName(name);
        if (ptr != 0) {
            Mesh msh = new Mesh(new InstancePointer(ptr));
            if (mesh == null)
                msh.dispose();
            else
                mesh.pInstance = msh.pInstance;
        }
    }

    public void getByName(String name) throws OgreException {
        getByName(null, name);
    }

    public void load(Mesh mesh, String fileName, String groupName)
            throws OgreException {

        // mesh==null || mesh.getInstancePointer().getValue()==0
        InstancePointer ptrMesh = new InstancePointer(_load(fileName,
                groupName, HardwareBuffer.HBU_STATIC_WRITE_ONLY,
                HardwareBuffer.HBU_STATIC_WRITE_ONLY, true, true));
        Mesh msh = new Mesh(ptrMesh);
        if (mesh == null)
            msh.dispose();
        else
            mesh.pInstance = msh.pInstance;
    }

    // --- END singleton ---
    // public Mesh load(String fileName, String groupName) throws OgreException
    // {
    public void load(String fileName, String groupName) throws OgreException {

        load(null, fileName, groupName);
    }

    public void loadResource(Resource resource) {
        // TODO Auto-generated method stub

    }
}
