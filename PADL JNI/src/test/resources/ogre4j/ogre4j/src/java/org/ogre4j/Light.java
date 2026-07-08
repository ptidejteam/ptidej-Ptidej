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
 * Light.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * Light
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Light extends MovableObject {

    public static final short LT_DIRECTIONAL = 1;

    public static final short LT_POINT = 0;

    public static final short LT_SPOTLIGHT = 2;

    private static native float getAttenuationConstant(int pInstance);

    private static native float getAttenuationLinear(int pInstance);

    private static native float getAttenuationQuadric(int pInstance);

    private static native float getAttenuationRange(int pInstance);

    private static native float[] getDerivedDirection(int pInstance);

    private static native float[] getDerivedPosition(int pInstance);

    private static native float[] getDiffuseColour(int pInstance);

    private static native float[] getDirection(int pInstance);

    private static native float[] getPosition(int pInstance);

    private static native float[] getSpecularColour(int pInstance);

    private static native float getSpotlightFalloff(int pInstance);

    private static native float getSpotlightInnerAngle(int pInstance);

    private static native float getSpotlightOuterAngle(int pInstance);

    private static native int getType(int pInstance);

    private static native void setAttenuation(int pInstance, float range,
            float ant, float linear, float quadratic);

    private static native void setDiffuseColour(int pInstance, float red,
            float green, float blue);

    private static native void setDirection(int pInstance, float x, float y,
            float z);

    private static native void setPosition(int pInstance, float x, float y,
            float z);

    private static native void setSpecularColour(int pInstance, float red,
            float green, float blue);

    private static native void setSpotlightRange(int pInstance,
            float innerAngle, float outerAngle, float falloff);

    private static native void setType(int pInstance, int type);

    /**
     * @param pInstance
     */
    protected Light(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Returns the constant factor in the attenuation formula.
     * 
     * @return
     */
    public float getAttenuationConstant() {
        return getAttenuationConstant(pInstance.getValue());
    }

    /**
     * Returns the linear factor in the attenuation formula.
     * 
     * @return
     */
    public float getAttenuationLinear() {
        return getAttenuationLinear(pInstance.getValue());
    }

    /**
     * Returns the quadric factor in the attenuation formula.
     * 
     * @return
     */
    public float getAttenuationQuadric() {
        return getAttenuationQuadric(pInstance.getValue());
    }

    /**
     * Returns the absolute upper range of the light.
     * 
     * @return
     */
    public float getAttenuationRange() {
        return getAttenuationRange(pInstance.getValue());
    }

    /**
     * Retrieves the direction of the light including any transform from nodes
     * it is attached to.
     * 
     * @return
     */
    public Vector3 getDerivedDirection() {
        return new Vector3(getDerivedDirection(pInstance.getValue()));
    }

    /**
     * Retrieves the position of the light including any transform from nodes it
     * is attached to.
     * 
     * @return
     */
    public Vector3 getDerivedPosition() {
        return new Vector3(getDerivedPosition(pInstance.getValue()));
    }

    /**
     * Returns the colour of the diffuse light given off by this light source
     * (see setDiffuseColour for more info).
     * 
     * @return
     */
    public ColourValue getDiffuseColour() {
        return new ColourValue(getDiffuseColour(pInstance.getValue()));
    }

    public Vector3 getDirection() {
        return new Vector3(getDirection(pInstance.getValue()));
    }

    /**
     * Returns the light's direction.
     * 
     * @return
     */
    public Vector3 getPosition() {
        return new Vector3(getPosition(pInstance.getValue()));
    }

    /**
     * Returns the colour of specular light given off by this light source.
     * 
     * @return
     */
    public ColourValue getSpecularColour() {
        return new ColourValue(getSpecularColour(pInstance.getValue()));
    }

    public float getSpotlightFalloff() {
        return getSpotlightFalloff(pInstance.getValue());
    }

    /**
     * Returns the angle covered by the spotlights inner cone.
     * 
     * @return
     */
    public float getSpotlightInnerAngle() {
        return getSpotlightInnerAngle(pInstance.getValue());
    }

    /**
     * Returns the angle covered by the spotlights outer cone.
     * 
     * @return
     */
    public float getSpotlightOuterAngle() {
        return getSpotlightOuterAngle(pInstance.getValue());
    }

    /**
     * Returns the light type.
     * 
     * @return
     */
    public int getType() {
        return getType(pInstance.getValue());
    }

    public void setAttenuation(float f, float g, float h, float i) {
        // TODO Auto-generated method stub

    }

    public void setAttenuationConstant(float constant) {
        setAttenuation(pInstance.getValue(), getAttenuationRange(pInstance
                .getValue()), constant, getAttenuationLinear(pInstance
                .getValue()), getAttenuationQuadric(pInstance.getValue()));
    }

    public void setAttenuationLinear(float linear) {
        setAttenuation(pInstance.getValue(), getAttenuationRange(pInstance
                .getValue()), getAttenuationConstant(pInstance.getValue()),
                linear, getAttenuationQuadric(pInstance.getValue()));
    }

    public void setAttenuationQuadric(float quadratic) {
        setAttenuation(pInstance.getValue(), getAttenuationRange(pInstance
                .getValue()), getAttenuationConstant(pInstance.getValue()),
                getAttenuationLinear(pInstance.getValue()), quadratic);
    }

    public void setAttenuationRange(float range) {
        setAttenuation(pInstance.getValue(), range,
                getAttenuationConstant(pInstance.getValue()),
                getAttenuationLinear(pInstance.getValue()),
                getAttenuationQuadric(pInstance.getValue()));
    }

    public void setDiffuseColour(ColourValue colour) {
        setDiffuseColour(pInstance.getValue(), colour.r, colour.g, colour.b);
    }

    public void setDiffuseColour(float red, float green, float blue) {
        setDiffuseColour(pInstance.getValue(), red, green, blue);
    }

    public void setDirection(float x, float y, float z) {
        setDirection(pInstance.getValue(), x, y, z);
    }

    public void setDirection(Vector3 vec) {
        setDirection(pInstance.getValue(), vec.x, vec.y, vec.z);
    }

    public void setPosition(float x, float y, float z) {
        setPosition(pInstance.getValue(), x, y, z);
    }

    public void setSpecularColour(ColourValue colour) {
        setSpecularColour(pInstance.getValue(), colour.r, colour.g, colour.b);
    }

    public void setSpecularColour(float red, float green, float blue) {
        setSpecularColour(pInstance.getValue(), red, green, blue);
    }

    public void setSpotlightFalloff(float falloff) {
        setSpotlightRange(pInstance.getValue(),
                getSpotlightInnerAngle(pInstance.getValue()),
                getSpotlightOuterAngle(pInstance.getValue()), falloff);
    }

    public void setSpotlightInnerAngle(float innerAngle) {
        setSpotlightRange(pInstance.getValue(), innerAngle,
                getSpotlightOuterAngle(pInstance.getValue()),
                getSpotlightFalloff(pInstance.getValue()));
    }

    public void setSpotlightOuterAngle(float outerAngle) {
        setSpotlightRange(pInstance.getValue(),
                getSpotlightInnerAngle(pInstance.getValue()), outerAngle,
                getSpotlightFalloff(pInstance.getValue()));
    }

    public void setSpotlightRange(float innerAngle, float outerAngle) {
        setSpotlightRange(pInstance.getValue(), innerAngle, outerAngle, 1.0f);
    }

    public void setType(short type) {
        setType(pInstance.getValue(), type);
    }

    @Override
    public void _updateRenderQueue() {
        // TODO Auto-generated method stub

    }
}
