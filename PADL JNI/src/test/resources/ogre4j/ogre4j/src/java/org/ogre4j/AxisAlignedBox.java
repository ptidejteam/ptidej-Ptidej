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
 * AxisAlignedBox.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/24 11:10:33 $
 * $Author: yavin02 $
 */
package org.ogre4j;

import javax.vecmath.Matrix4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * A 3D box aligned with the x/y/z axes.
 * <p>
 * <b>Remarks:</b><br/> This class represents a simple box which is aligned
 * with the axes. Internally it only stores 2 points as the extremeties of the
 * box, one which is the minima of all 3 axes, and the other which is the maxima
 * of all 3 axes. This class is typically used for an axis-aligned bounding box
 * (AABB) for collision and visibility determination
 * </p>
 * TODO publicfloat volume()
 * TODO public boolean intersects(Sphere s) 
 * TODO public boolean intersects(Plane p)
 * TODO public boolean intersects(Vector3f v)
 * TODO public void setExtents(Vector3f min, Vector3f max)
 * TODO public void setExtents(float mx, float my, float mz, float Mx, float My, float Mz)
 * TODO public Vector3f getAllCorners() TODO public void merge(Vector3f point)
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class AxisAlignedBox extends NativeObject
{
    private static native int createInstance();

    private static native int createInstance(float mx, float my, float mz, float Mx, float My, float Mz);

    private static native void dispose(int pInstance);

    private static native float[] getCenter(int pInstance);

    private static native float[] getMaximum(int pInstance);

    private static native float[] getMinimum(int pInstance);

    private static native int intersection(int pInstance, int pB2);

    private static native boolean intersects(int pInstance, int pB2);

    private static native boolean isNull(int pInstance);

    private static native void merge(int pInstance, int pRhs);

    private static native void scale(int pInstance, float x, float y, float z);

    private static native void setMaximum(int pInstance, float x, float y, float z);

    private static native void setMinimum(int pInstance, float x, float y, float z);

    private static native void setNull(int pInstance);

    private static native void transform(int pInstance, float m00, float m01, float m02, float m03, float m10, float m11, float m12,
            float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33);

    public AxisAlignedBox()
    {
        super(new InstancePointer(createInstance()));
    }

    public AxisAlignedBox(AxisAlignedBox box)
    {
        this(box.getMinimum(), box.getMaximum());
    }

    public AxisAlignedBox(float mx, float my, float mz, float Mx, float My, float Mz)
    {
        super(new InstancePointer(createInstance(mx, my, mz, Mx, My, Mz)));
    }

    public AxisAlignedBox(InstancePointer pInstance)
    {
        super(pInstance);
    }

    public AxisAlignedBox(Point3f min, Point3f max)
    {
        this(min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public void dispose()
    {
        dispose(pInstance.getValue());
    }

    public Point3f getCenter()
    {
        float[] tuple = getCenter(pInstance.getValue());
        return new Point3f(tuple);
    }

    public Point3f getMaximum()
    {
        float[] tuple = getMaximum(pInstance.getValue());
        return new Point3f(tuple);
    }

    public Point3f getMinimum()
    {
        float[] tuple = getMinimum(pInstance.getValue());
        return new Point3f(tuple);
    }

    /**
     * Calculate the area of intersection of this box and another.
     * <p>
     * <b>Remarks:</b><br/>Ogre returns the new AxisAlignedBox by value
     * (copies it on the stack). For usage in Java the new AxisAlignedBox must
     * be created with 'new'. After using the returned object it must be
     * disposed to free the memory!
     * </p>
     * 
     * @param b2
     *            AxisAlignedBox to check.
     * @return Returns a new AxisAlignedBox.
     */
    public AxisAlignedBox intersection(AxisAlignedBox b2)
    {
        int ptrAAB = intersection(pInstance.getValue(), b2.getInstancePtr().getValue());
        if (ptrAAB == 0)
            return null;

        return new AxisAlignedBox(new InstancePointer(ptrAAB));
    }

    public boolean intersects(AxisAlignedBox b2)
    {
        return intersects(pInstance.getValue(), b2.getInstancePtr().getValue());
    }

    public boolean isNull()
    {
        return isNull(pInstance.getValue());
    }

    public void merge(AxisAlignedBox rhs)
    {
        merge(pInstance.getValue(), rhs.pInstance.getValue());
    }

    public void scale(Vector3f s)
    {
        scale(pInstance.getValue(), s.x, s.y, s.z);
    }

    public void setMaximum(float x, float y, float z)
    {
        setMaximum(pInstance.getValue(), x, y, z);
    }

    public void setMaximum(Vector3f vec)
    {
        setMaximum(vec.x, vec.y, vec.z);
    }
    public void setMinimum(float x, float y, float z)
    {
        setMinimum(pInstance.getValue(), x, y, z);
    }
    
    public void setMinimum(Vector3f vec)
    {
        setMinimum(vec.x, vec.y, vec.z);
    }
    
    public void setNull()
    {
        setNull(pInstance.getValue());
    }
    
    public void transform(Matrix4f matrix)
    {
        transform(pInstance.getValue(), matrix.m00, matrix.m01, matrix.m02, matrix.m03, matrix.m10, matrix.m11, matrix.m12, matrix.m13,
                matrix.m20, matrix.m21, matrix.m22, matrix.m23, matrix.m30, matrix.m31, matrix.m32, matrix.m33);
    }
}
