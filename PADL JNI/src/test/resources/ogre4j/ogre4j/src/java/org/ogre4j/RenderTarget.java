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
 * RenderTarget.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/22 02:23:31 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * TODO RenderTarget type/class description.
 * 
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class RenderTarget extends NativeObject {
    private static native void addListener(int ptrSelf, int ptrListener);

    private static native int addViewport(int pInstance, int pCam);

    // TODO: void getMetrics(int width, int height, int colourDepth);

    private static native int addViewport(int pInstance, int pCam, int ZOrder,
            float left, float top, float width, float height);

    private static native float getAverageFPS(int pInstance);

    private static native float getBestFPS(int pInstance);

    private static native float getBestFrameTime(int pInstance);

    private static native int getColourDepth(int pInstance);

    private static native String getDebugText(int pInstance);

    private static native int getHeight(int pInstance);

    private static native float getLastFPS(int pInstance);

    private static native String getName(int value);

    private static native int getNumViewports(int pInstance);

    private static native char getPriority(int ptrSelf);

    private static native long getTriangleCount(int pInstance);

    private static native int getWidth(int pInstance);

    private static native float getWorstFPS(int pInstance);

    private static native float getWorstFrameTime(int pInstance);

    private static native boolean isActive(int pInstance);

    private static native boolean isAutoUpdated(int ptrSelf);

    private static native boolean isPrimary(int pInstance);

    private static native void removeAllListeners(int pInstance);

    private static native void removeAllViewports(int ptrSelf);

    private static native void removeListener(int ptrSelf, int ptrListener);

    private static native void removeViewport(int ptrSelf, int ZOrder);

    // TODO: void getCustomAttribute( String name, int pData);

    private static native boolean requiresTextureFlipping(int ptrSelf);

    private static native void resetStatistics(int pInstance);

    private static native void setActive(int pInstance, boolean state);

    private static native void setAutoUpdated(int ptrSelf, boolean autoupdate);

    private static native void setDebugText(int pInstance, String text);

    private static native void setPriority(int ptrSelf, char priority);

    private static native void update(int pInstance);

    protected RenderTarget(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Add a listener to this RenderTarget which will be called back before &
     * after rendering.
     * 
     * @param listener
     */
    public void addListener(RenderTargetListener listener) {
        addListener(pInstance.getValue(), listener.pInstance.getValue());
    }

    /**
     * TODO addViewport description
     * 
     * @param cam
     * @return
     */
    public Viewport addViewport(Camera cam) {
        int address = addViewport(pInstance.getValue(), cam.getInstancePtr()
                .getValue());
        InstancePointer ptrViewport = new InstancePointer(address);
        return new Viewport(ptrViewport);
    }

    /**
     * Adds a viewport to the rendering target.
     */
    public Viewport addViewport(Camera cam, int ZOrder, float left, float top,
            float width, float height) {
        InstancePointer ptrViewport = new InstancePointer(addViewport(pInstance
                .getValue(), cam.getInstancePtr().getValue(), ZOrder, left,
                top, width, height));
        return new Viewport(ptrViewport);
    }

    /**
     * Individual stats access - gets the average frames per second (FPS) since
     * call to Root::startRendering.
     * 
     * @return
     */
    public float getAverageFPS() {
        return getAverageFPS(pInstance.getValue());
    }

    /**
     * Individual stats access - gets the best frames per second (FPS) since
     * call to Root::startRendering.
     * 
     * @return
     */
    public float getBestFPS() {
        return getBestFPS(pInstance.getValue());
    }

    /**
     * Individual stats access - gets the best frame time.
     * 
     * @return
     */
    public float getBestFrameTime() {
        return getBestFrameTime(pInstance.getValue());
    }

    public int getColourDepth() {
        return getColourDepth(pInstance.getValue());
    }

    /**
     * Returns the debug text.
     * 
     * @return
     */
    public String getDebugText() {
        return getDebugText(pInstance.getValue());
    }

    public int getHeight() {
        return getHeight(pInstance.getValue());
    }

    /**
     * Individual stats access - gets the number of frames per second (FPS)
     * based on the last frame rendered.
     * 
     * @return
     */
    public float getLastFPS() {
        return getLastFPS(pInstance.getValue());
    }

    /**
     * Retrieve target's name.
     * 
     * @return
     */
    public String getName() {
        return getName(pInstance.getValue());
    }

    // TODO: void writeContentsToFile( String filename) = 0;

    // TODO: String writeContentsToTimestampedFile( String filenamePrefix,
    // String filenameSuffix);

    /**
     * Returns the number of viewports attached to this target.
     * 
     * @return
     */
    public int getNumViewports() {
        return getNumViewports(pInstance.getValue());
    }

    // TODO: void _notifyCameraRemoved( Camera cam);

    /**
     * Gets the priority of a render target.
     */
    public char getPriority() {
        return getPriority(pInstance.getValue());
    }

    /**
     * Retieves details of current rendering performance.
     * 
     * @return
     */
    public FrameStats getStatistics() {
        // TODO
        return null;
    }

    /**
     * Gets the number of triangles rendered in the last update() call.
     * 
     * @return
     */
    public long getTriangleCount() {
        return getTriangleCount(pInstance.getValue());
    }

    /**
     * Retrieves a pointer to the viewport with the given index.
     * 
     */
    public Viewport getViewport(short index) {
        return null;
    }

    public int getWidth() {
        return getWidth(pInstance.getValue());
    }

    /**
     * Individual stats access - gets the worst frames per second (FPS) since
     * call to Root::startRendering.
     * 
     * @return
     */
    public float getWorstFPS() {
        return getWorstFPS(pInstance.getValue());
    }

    /**
     * Individual stats access - gets the worst frame time.
     * 
     * @return
     */
    public float getWorstFrameTime() {
        return getWorstFrameTime(pInstance.getValue());
    }

    /**
     * Used to retrieve or set the active state of the render target.
     * 
     * @param pInstance
     * @return
     */
    public boolean isActive() {
        return isActive(pInstance.getValue());
    }

    /**
     * Gets whether this target is automatically updated if Ogre's rendering
     * loop or Root::_updateAllRenderTargets is being used.
     * 
     * @return
     */
    public boolean isAutoUpdated() {
        return isAutoUpdated(pInstance.getValue());
    }

    /**
     * Indicates whether this target is the primary window.
     * 
     */
    public boolean isPrimary() {
        return isPrimary(pInstance.getValue());
    }

    /**
     * Removes all listeners from this instance.
     * 
     */
    public void removeAllListeners() {
        removeAllListeners(pInstance.getValue());
    }

    /**
     * Removes all viewports on this target.
     * 
     */
    public void removeAllViewports() {
        removeAllViewports(pInstance.getValue());
    }

    /**
     * Removes a RenderTargetListener previously registered using addListener.
     * 
     * @param listener
     */
    public void removeListener(RenderTargetListener listener) {
        removeListener(pInstance.getValue(), listener.pInstance.getValue());
    }

    /**
     * Removes a viewport at a given ZOrder
     * 
     * @param ZOrder
     */
    public void removeViewport(int ZOrder) {
        removeViewport(pInstance.getValue(), ZOrder);
    }

    public boolean requiresTextureFlipping() {
        return requiresTextureFlipping(pInstance.getValue());
    }

    /**
     * Resets saved frame-rate statistices.
     * 
     */
    public void resetStatistics() {
        resetStatistics(pInstance.getValue());
    }

    /**
     * Used to set the active state of the render target.
     * 
     * @param pInstance
     * @param state
     */
    public void setActive(boolean state) {
        setActive(pInstance.getValue(), state);
    }

    /**
     * Sets whether this target should be automatically updated if Ogre's
     * rendering loop or Root::_updateAllRenderTargets is being used.
     * 
     * @param autoupdate
     */
    public void setAutoUpdated(boolean autoupdate) {
        setAutoUpdated(pInstance.getValue(), autoupdate);
    }

    /**
     * Adds debug text to this window.
     * 
     */
    public void setDebugText(String text) {
        setDebugText(pInstance.getValue(), text);
    }

    /**
     * Sets the priority of this render target in relation to the others.
     * 
     * @param priority
     */
    public void setPriority(char priority) {
        setPriority(pInstance.getValue(), priority);
    }

    /**
     * Tells the target to update it's contents.
     * 
     */
    public void update() {
        update(pInstance.getValue());
    }
}
