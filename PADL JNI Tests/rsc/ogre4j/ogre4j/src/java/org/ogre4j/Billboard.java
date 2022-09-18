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
 * Billboard.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.2 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 * A billboard is a primitive which always faces the camera in every frame.
 * 
 * <p>
 * <b>Remarks:</b><br/> Billboards can be used for special effects or some
 * other trickery which requires the triangles to always facing the camera no
 * matter where it is. Ogre groups billboards into sets for efficiency, so you
 * should never create a billboard on it's own (it's ok to have a set of one if
 * you need it). Billboards have their geometry generated every frame depending
 * on where the camera is. It is most beneficial for all billboards in a set to
 * be identically sized since Ogre can take advantage of this and save some
 * calculations - useful when you have sets of hundreds of billboards as is
 * possible with special effects. You can deviate from this if you wish
 * (example: a smoke effect would probably have smoke puffs expanding as they
 * rise, so each billboard will legitimately have it's own size) but be aware
 * the extra overhead this brings and try to avoid it if you can. Billboards are
 * just the mechanism for rendering a range of effects such as particles. It is
 * other classes which use billboards to create their individual effects, so the
 * methods here are quite generic.
 * </p>
 * 
 * @see org.ogre4j.BillboardSet
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Billboard extends NativeObject {

    /**
     * @param pInstance
     */
    Billboard(InstancePointer pInstance) {
        super(pInstance);
    }

    public Billboard() throws OgreException {
        super(new InstancePointer(createInstance()));
    }

    public Billboard(Point3f position, BillboardSet owner, ColourValue colour)
            throws OgreException {
        super(new InstancePointer(createInstance(position.x, position.y,
                position.z, owner.getInstancePtr().getValue(), colour.r,
                colour.g, colour.b, colour.a)));
    }

    public void dispose() throws OgreException {
        dispose(pInstance.getValue());
    }

    /**
     * <p>
     * <b>Remarks:</b><br/>This rotation is relative to the center of the
     * billboard.
     * </p>
     * 
     * @return Get the rotation of the billboard.
     */
    public float getRotation() {
        return getRotation(pInstance.getValue());
    }

    /**
     * Set the rotation of the billboard.
     * 
     * @param rotation
     */
    public void setRotation(float rotation) {
        setRotation(pInstance.getValue(), rotation);
    }

    /**
     * Set the position of the billboard.
     * 
     * <p>
     * <b>Remarks:</b><br/>This position is relative to a point on the quad
     * which is the billboard. Depending on the BillboardSet, this may be the
     * center of the quad, the top-left etc. See
     * BillboardSet::setBillboardOrigin for more info.
     * </p>
     * 
     * @param position
     */
    public void setPosition(Point3f position) {
        setPosition(pInstance.getValue(), position.x, position.y, position.z);
    }

    /**
     * @see #setPosition(Point3f)
     * @param x
     *            Position x
     * @param y
     *            Position y
     * @param z
     *            Position z
     */
    public void setPosition(float x, float y, float z) {
        setPosition(pInstance.getValue(), x, y, z);
    }

    /**
     * Get the position of the billboard.
     * 
     * <p>
     * <b>Remarks:</b><br/>This position is relative to a point on the quad
     * which is the billboard. Depending on the BillboardSet, this may be the
     * center of the quad, the top-left etc. See
     * BillboardSet::setBillboardOrigin for more info.
     * </p>
     * 
     * @return
     */
    public Point3f getPosition() {
        float[] tuple = getPosition(pInstance.getValue());
        return new Point3f(tuple);
    }

    public Vector3f getDirection() {
        float[] tuple = getDirection(pInstance.getValue());
        return new Vector3f(tuple);
    }

    public void setDirection(Vector3f direction) {
        setDirection(pInstance.getValue(), direction.x, direction.y,
                direction.z);
    }

    public BillboardSet getParentSet() {
        int ptr = getParentSet(pInstance.getValue());
        if (ptr == 0)
            return null;

        return new BillboardSet(new InstancePointer(ptr));
    }

    /**
     * Sets the width and height for this billboard.
     * 
     * <p>
     * <b>Remarks:</b><br/> Note that it is most efficient for every billboard
     * in a BillboardSet to have the same dimensions. If you choose to alter the
     * dimensions of an individual billboard the set will be less efficient. Do
     * not call this method unless you really need to have different billboard
     * dimensions within the same set. Otherwise just call the
     * BillboardSet::setDefaultDimensions method instead.
     * </p>
     * 
     * @param width
     * @param height
     */
    public void setDimensions(float width, float height) {
        setDimensions(pInstance.getValue(), width, height);
    }

    /**
     * Resets this Billboard to use the parent BillboardSet's dimensions instead
     * of it's own.
     */
    public void resetDimensions() {
        resetDimensions(pInstance.getValue());
    }

    /**
     * Sets the colour of this billboard.
     * 
     * @param colour
     */
    public void setColour(ColourValue colour) {
        setColour(pInstance.getValue(), colour.r, colour.g, colour.b, colour.a);
    }

    /**
     * @return Gets the colour of this billboard.
     */
    public ColourValue getColour() {
        float[] tuple = getColour(pInstance.getValue());
        return new ColourValue(tuple);
    }

    /**
     * @return Returns true if this billboard deviates from the BillboardSet's
     *         default dimensions (i.e. if the Billboard::setDimensions method
     *         has been called for this instance).
     * 
     */
    public boolean hasOwnDimensions() {
        return hasOwnDimensions(pInstance.getValue());
    }

    /**
     * @return Retrieves the billboard's personal width, if hasOwnDimensions is
     *         true.
     */
    public float getOwnWidth() {
        return getOwnWidth(pInstance.getValue());
    }

    /**
     * @return Retrieves the billboard's personal width, if hasOwnDimensions is
     *         true.
     */
    public float getOwnHeight() {
        return getOwnHeight(pInstance.getValue());
    }

    /**
     * Internal method for notifying the billboard of it's owner.
     * 
     * @param owner
     */
    public void _notifyOwner(BillboardSet owner) {
        _notifyOwner(pInstance.getValue(), owner.getInstancePtr().getValue());
    }

    // -----------------------------------------------------------------------------
    // NATIVES
    // -----------------------------------------------------------------------------
    private static native int createInstance() throws OgreException;

    private static native int createInstance(float x, float y, float z,
            int pOwner, float r, float g, float b, float a)
            throws OgreException;

    private static native void dispose(int pInstance) throws OgreException;

    private static native float getRotation(int pInstance);

    private static native void setRotation(int pInstance, float rotation);

    private static native void setPosition(int pInstance, float x, float y,
            float z);

    private static native float[] getPosition(int pInstance);

    private static native float[] getDirection(int pInstance);

    private static native void setDirection(int pInstance, float x, float y,
            float z);

    private static native int getParentSet(int pInstance);

    private static native void setDimensions(int pInstance, float width,
            float height);

    private static native void resetDimensions(int pInstance);

    private static native float[] getColour(int pInstance);

    private static native void setColour(int pInstance, float r, float g,
            float b, float a);

    private static native boolean hasOwnDimensions(int pInstance);

    private static native float getOwnWidth(int pInstance);

    private static native float getOwnHeight(int pInstance);

    private static native void _notifyOwner(int pInstance, int pOwner);
}
