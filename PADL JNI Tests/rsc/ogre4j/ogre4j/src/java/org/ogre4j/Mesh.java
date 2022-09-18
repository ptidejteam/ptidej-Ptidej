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
 * Mesh.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * Mesh
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 * 
 * TODO AXISALIGNEDBOX... (<- setBounds())
 */
public class Mesh extends Resource {

    /**
     * @param ptrMesh
     */
    public Mesh() {
        this(null);
    }

    protected Mesh(InstancePointer pInstance) {
        super(pInstance);
    }

    public void dispose() {
        dispose(pInstance.getValue());
    }

    public void setSkeletonName(String skelName) {
        setSkeletonName(pInstance.getValue(), skelName);
    }

    public boolean hasSkeleton() {
        return hasSkeleton(pInstance.getValue());
    }

    public Skeleton getSkeleton() {
        InstancePointer ptrSkeleton = new InstancePointer(getSkeleton(pInstance
                .getValue()));
        return new Skeleton(ptrSkeleton);
    }

    public String getSkeletonName() {
        return getSkeletonName(pInstance.getValue());
    }

    public SubMesh createSubMesh() {
        InstancePointer ptrSubMesh = new InstancePointer(
                createSubMesh(pInstance.getValue()));
        return new SubMesh(ptrSubMesh);
    }

    public void setSharedVertexData(VertexData vertexData) {
        setSharedVertexData(pInstance.getValue(), vertexData.getInstancePtr()
                .getValue());
    }

    public VertexData getSharedVertexData() {
        InstancePointer ptrVertexData = new InstancePointer(
                getSharedVertexData(pInstance.getValue()));
        return new VertexData(ptrVertexData);
    }

    public void load() {
        load(pInstance.getValue());
    }

    public void unload() {
        unload(pInstance.getValue());
    }

    /**
     * Manually set the bounding box for this Mesh.
     * 
     * @see #_setBounds(AxisAlignedBox, boolean)
     */
    public void _setBounds(AxisAlignedBox bounds) {
        boolean pad = true;
        _setBounds(bounds, pad);
    }

    /**
     * Manually set the bounding box for this Mesh.
     * <p>
     * <b>Remarks:</b><br/>Calling this method is required when building
     * manual meshes now, because OGRE can no longer update the bounds for you,
     * because it cannot necessarily read vertex data back from the vertex
     * buffers which this mesh uses (they very well might be write-only, and
     * even if they are not, reading data from a hardware buffer is a
     * bottleneck).
     * </p>
     * 
     * @param bounds
     * @param pad
     *            If true, a certain padding will be added to the bounding box
     *            to separate it from the mesh
     */
    public void _setBounds(AxisAlignedBox bounds, boolean pad) {
        _setBounds(pInstance.getValue(), bounds.getInstancePtr().getValue(),
                pad);
    }

    /**
     * Manually set the bounding box for this Mesh.
     * 
     * @param mx
     *            Min X
     * @param my
     *            Min X
     * @param mz
     *            Min X
     * @param Mx
     *            Max X
     * @param My
     *            Max X
     * @param Mz
     *            Max X
     * @see #_setBounds(AxisAlignedBox, boolean)
     */
    public void _setBounds(float mx, float my, float mz, float Mx, float My,
            float Mz, boolean pad) {
        _setBounds(pInstance.getValue(), mx, my, mz, Mx, My, Mz, pad);
    }

    /**
     * @deprecated
     */
    public void setBounds(float mx, float my, float mz, float Mx, float My,
            float Mz) {
        setBounds(pInstance.getValue(), mx, my, mz, Mx, My, Mz);
    }

    public void _setBoundingSphereRadius(float radius) {
        _setBoundingSphereRadius(pInstance.getValue(), radius);
    }

    /**
     * @deprecated
     */
    public void setBoundingSphereRadius(float radius) {
        setBoundingSphereRadius(pInstance.getValue(), radius);
    }

    public String getName() {
        return getName(pInstance.getValue());
    }

    // -----------------------------------------------------------------------------
    // Natives
    // -----------------------------------------------------------------------------
    private static native void dispose(int pInstance);

    private static native void setSkeletonName(int pInstance, String skelName);

    private static native boolean hasSkeleton(int pInstance);

    private static native int getSkeleton(int pInstance);

    private static native String getSkeletonName(int pInstance);

    private static native int createSubMesh(int pInstance);

    private static native void setSharedVertexData(int pInstance, int vertexData);

    private static native int getSharedVertexData(int pInstance);

    private static native void load(int pInstance);

    private static native void unload(int pInstance);

    private static native void _setBounds(int pInstance, int pAAB, boolean pad);

    private static native void _setBounds(int pInstance, float mx, float my,
            float mz, float Mx, float My, float Mz, boolean pad);

    private static native void _setBoundingSphereRadius(int pInstance,
            float radius);

    private static native String getName(int pInstance);

    /**
     * @deprecated
     */
    private static native void setBounds(int pInstance, float mx, float my,
            float mz, float Mx, float My, float Mz);

    /**
     * @deprecated
     */
    private static native void setBoundingSphereRadius(int pInstance,
            float radius);
}
