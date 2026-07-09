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
 * Plane.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.1 $
 * $Date: 2005/08/15 07:34:14 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * Defines a plane in 3D space.
 * <p>
 * <b>Remarks:</b><br/> A plane is defined in 3D space by the equation Ax + By +
 * Cz + D = 0<br/> This equates to a vector (the normal of the plane, whose x,
 * y and z components equate to the coefficients A, B and C respectively), and a
 * constant (D) which is the distance along the normal you have to go to move
 * the plane back to the origin.
 * </p>
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
abstract public class MovablePlane extends MovableObject {
    /**
     * The "positive side" of the plane is the half space to which the plane
     * normal points. The "negative side" is the other half space. The flag "no
     * side" indicates the plane itself.
     */
    static enum Side {
        NO_SIDE, POSITIVE_SIDE, NEGATIVE_SIDE
    };

    /**
     * Default constructor - sets everything to 0.
     */
    public MovablePlane() {
        super(new InstancePointer(createInstance()));
    }

    /**
     * Construct a plane through a normal, and a distance to move the plane
     * along the normal.
     * 
     * @param rkNormal
     * @param fConstant
     */
    public MovablePlane(Vector3f rkNormal, float fConstant) {
        super(new InstancePointer(createInstance(rkNormal.x, rkNormal.y,
                rkNormal.z, fConstant)));
    }

    public MovablePlane(Vector3f rkNormal, Point3f rkPoint) {
        super(new InstancePointer(createInstance(rkNormal.x, rkNormal.y,
                rkNormal.z, rkPoint.x, rkPoint.y, rkPoint.z)));
    }

    public MovablePlane(Point3f rkPoint0, Point3f rkPoint1, Point3f rkPoint2) {
        super(new InstancePointer(createInstance(rkPoint0.x, rkPoint0.y,
                rkPoint0.z, rkPoint1.x, rkPoint1.y, rkPoint1.z, rkPoint2.x,
                rkPoint2.y, rkPoint2.z)));
    }

    public void dispose() {
        dispose(pInstance.getValue());
    }

    public int getSide(Point3f rkPoint) {
        return getSide(pInstance.getValue(), rkPoint.y, rkPoint.y, rkPoint.z);
    }

    /**
     * This is a pseudodistance.
     * <p>
     * The sign of the return value is positive if the point is on the positive
     * side of the plane, negative if the point is on the negative side, and
     * zero if the point is on the plane.<br/> The absolute value of the return
     * value is the true distance only when the plane normal is a unit length
     * vector.
     * </p>
     * 
     * @return Returns the distance between the point and this plane.
     */
    public float getDistance(Point3f rkPoint) {
        return getDistance(pInstance.getValue(), rkPoint.y, rkPoint.y,
                rkPoint.z);
    }

    /**
     * Redefine this plane based on 3 points.
     * 
     * @param rkPoint0
     * @param rkPoint1
     * @param rkPoint2
     */
    public void redefine(Point3f rkPoint0, Point3f rkPoint1, Point3f rkPoint2) {
        redefine(pInstance.getValue(), rkPoint0.x, rkPoint0.y, rkPoint0.z,
                rkPoint1.x, rkPoint1.y, rkPoint1.z, rkPoint2.x, rkPoint2.y,
                rkPoint2.z);
    }

    // -------------------------------------------------------------------------
    // NATIVES
    // -------------------------------------------------------------------------
    private static native int createInstance();

    private static native int createInstance(float x, float y, float z,
            float constant);

    private static native int createInstance(float x, float y, float z,
            float x2, float y2, float z2);

    private static native int createInstance(float x, float y, float z,
            float x2, float y2, float z2, float x3, float y3, float z3);

    private static native void dispose(int pInstance);

    private static native int getSide(int pInstance, float y, float y2, float z);

    private static native float getDistance(int pInstance, float y, float y2,
            float z);

    private static native void redefine(int pInstance, float x, float y,
            float z, float x2, float y2, float z2, float x3, float y3, float z3);
}
