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
 * Viewport.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/18 23:49:06 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * TODO Viewport type/class description. <br>
 * 100% C++ public methods except getActualDimensions(... ... ... ... )
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Viewport extends NativeObject {
    private static native int getActualHeight(int pInstance);

    private static native int getActualLeft(int pInstance);

    private static native int getActualTop(int pInstance);

    private static native int getActualWidth(int pInstance);

    private static native float[] getBackgroundColour(int pInstance);

    private static native int getCamera(int pInstance);

    private static native boolean getClearEveryFrame(int pInstance);

    private static native float getHeight(int pInstance);

    private static native float getLeft(int pInstance);

    private static native boolean getOverlaysEnabled(int pInstance);

    private static native int getTarget(int ptrSelf);

    private static native float getTop(int pInstance);

    private static native float getWidth(int pInstance);

    private static native int getZOrder(int ptrSelf);

    private static native void setBackgroundColour(int pInstance, float r,
            float g, float b, float a);

    private static native void setCamera(int pInstance, int cam);

    private static native void setClearEveryFrame(int pInstance, boolean clear);

    private static native void setDimensions(int pInstance, float left,
            float top, float width, float height);

    private static native void setOverlaysEnabled(int ptrSelf, boolean enabled);

    private static native void update(int pInstance);

    /**
     * @param pInstance
     */
    public Viewport(InstancePointer pInstance) {
        super(pInstance);
        // TODO Auto-generated constructor stub
    }

    public void _clearUpdatedFlag() {

    }

    /**
     * Gets the number of rendered faces in the last update.
     * 
     * @return
     */
    public int _getNumRenderedFaces() {
        return 0;
    }

    public boolean _isUpdated() {
        return false;
    }

    /**
     * Notifies the viewport of a possible change in dimensions.
     * 
     */
    public void _updateDimensions() {

    }

    /**
     * Gets one of the actual dimensions of the viewport, a value in pixels.
     * 
     * @return
     */
    public int getActualHeight() {
        return getActualHeight(pInstance.getValue());
    }

    /**
     * Gets one of the actual dimensions of the viewport, a value in pixels.
     * 
     * @return
     */
    public int getActualLeft() {
        return getActualLeft(pInstance.getValue());
    }

    /**
     * Gets one of the actual dimensions of the viewport, a value in pixels.
     * 
     * @return
     */
    public int getActualTop() {
        return getActualTop(pInstance.getValue());
    }

    /**
     * Gets one of the actual dimensions of the viewport, a value in pixels.
     * 
     * @return
     */
    public int getActualWidth() {
        return getActualWidth(pInstance.getValue());
    }

    /**
     * Gets the background colour.
     * 
     * @return
     */
    public ColourValue getBackgroundColour() {
        float[] cv = getBackgroundColour(pInstance.getValue());
        return new ColourValue(cv);
    }

    /**
     * Retrieves a pointer to the camera for this viewport.
     * 
     * @return
     */
    public Camera getCamera() {
        int address = getCamera(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new Camera(ptr);
    }

    /**
     * Determines if the viewport is cleared before every frame.
     * 
     * @return
     */
    public boolean getClearEveryFrame() {
        return getClearEveryFrame(pInstance.getValue());
    }

    /**
     * Gets one of the relative dimensions of the viewport, a value between 0.0
     * and 1.0.
     * 
     * @return
     */
    public float getHeight() {
        return getHeight(pInstance.getValue());
    }

    /**
     * Gets one of the relative dimensions of the viewport, a value between 0.0
     * and 1.0.
     * 
     * @return
     */
    public float getLeft() {
        return getLeft(pInstance.getValue());
    }

    /**
     * Returns whether or not Overlay objects (created in the SceneManager) are
     * displayed in this viewport.
     * 
     * @return
     */
    public boolean getOverlaysEnabled() {
        return getOverlaysEnabled(pInstance.getValue());
    }

    /**
     * Retrieves a pointer to the render target for this viewport.
     * 
     * @return
     */
    public RenderTarget getTarget() {
        int address = getTarget(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new RenderTarget(ptr);
    }

    /**
     * Gets one of the relative dimensions of the viewport, a value between 0.0
     * and 1.0.
     * 
     * @return
     */
    public float getTop() {
        return getTop(pInstance.getValue());
    }

    /**
     * Gets one of the relative dimensions of the viewport, a value between 0.0
     * and 1.0.
     * 
     * @return
     */
    public float getWidth() {
        return getWidth(pInstance.getValue());
    }

    /**
     * Gets the Z-Order of this viewport.
     * 
     * @return
     */
    public int getZOrder() {
        return getZOrder(pInstance.getValue());
    }

    /**
     * Sets the initial background colour of the viewport (before rendering
     * 
     * @param color
     */
    public void setBackgroundColour(ColourValue color) {
        setBackgroundColour(pInstance.getValue(), color.r, color.g, color.b,
                color.a);
    }

    /**
     * Sets the camera to use for rendering to this viewport.
     * 
     * @param cam
     */
    public void setCamera(Camera cam) {
        setCamera(pInstance.getValue(), cam.getInstancePtr().getValue());
    }

    /**
     * Determines whether to clear the viewport before rendering.
     * 
     * @param clear
     */
    public void setClearEveryFrame(boolean clear) {
        setClearEveryFrame(pInstance.getValue(), clear);
    }

    /**
     * Sets the dimensions (after creation).
     * 
     * @param left
     * @param top
     * @param width
     * @param height
     */
    public void setDimensions(float left, float top, float width, float height) {
        setDimensions(pInstance.getValue(), left, top, width, height);
    }

    /**
     * Tells this viewport whether it should display Overlay objects.
     * 
     */
    public void setOverlaysEnabled(boolean enabled) {
        setOverlaysEnabled(pInstance.getValue(), enabled);
    }

    /**
     * Instructs the viewport to updates its contents.
     * 
     */
    public void update() {
        update(pInstance.getValue());
    }
}
