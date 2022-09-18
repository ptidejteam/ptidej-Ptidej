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
 * RenderWindow.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.5 $
 * $Date: 2005/08/25 03:04:08 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO RenderWindow interface
 *         description.
 */
public class RenderWindow extends RenderTarget {

    /**
     * @param pInstance
     */
    protected RenderWindow(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * Creates & displays the new window.
     * 
     * @param name
     *            The name of the window. Used in other methods later like
     *            setRenderTarget and getRenderWindow.
     * @param width
     *            The width of the new window.
     * @param height
     *            The height of the new window.
     * @param fullScreen
     *            Specify true to make the window full screen without borders,
     *            title bar or menu bar.
     * @param title
     *            The title of the window that will appear in the title bar
     *            Values: string Default: RenderTarget name
     * @param colourDepth
     *            Colour depth of the resulting rendering window; only applies
     *            if fullScreen is set. Values: 16 or 32 Default: desktop depth
     *            Notes: [W32 specific]
     * @param left
     *            screen x coordinate from left Values: positive integers
     *            Default: 'center window on screen' Notes: Ignored in case of
     *            full screen
     * @param top
     *            screen y coordinate from top Values: positive integers
     *            Default: 'center window on screen' Notes: Ignored in case of
     *            full screen
     * @param depthBuffer
     *            Use depth buffer Values: false or true Default: true
     * @param externalWindowHandle
     *            External window handle, for embedding the OGRE context Values:
     *            positive integer for W32 (HWND handle) posint:posint:posint
     *            for GLX (display:screen:windowHandle) Default: 0 (None)
     * @param parentWindowHandle
     *            Parent window handle, for embedding the OGRE context Values:
     *            positive integer for W32 (HWND handle) posint:posint:posint
     *            for GLX (display:screen:windowHandle) Default: 0 (None)
     * @param FSAA
     *            Full screen antialiasing factor Values: 0,2,4,6,... Default: 0
     * @param displayFrequency
     *            Display frequency rate, for fullscreen mode Values: 60...?
     *            Default: Desktop vsync rate
     * @param vsync
     *            Synchronize buffer swaps to vsync Values: true, false Default:
     *            0
     * @return Pointer to a object of type Ogre::RenderWindow
     */
    public void create(String name, int width, int height, boolean fullScreen,
            String title, int colourDepth, int left, int top,
            boolean depthBuffer, int externalWindowHandle,
            int parentWindowHandle, int FSAA, float displayFrequency,
            boolean vsync) {
        create(pInstance.getValue(), name, width, height, fullScreen, title,
                colourDepth, left, top, depthBuffer, externalWindowHandle,
                parentWindowHandle, FSAA, displayFrequency, vsync);
    }

    /**
     * Destroys the window.
     * 
     * @param pInstance
     */
    public void destroy() {
        destroy(pInstance.getValue());
    }

    public void resize(int width, int height) {
        resize(pInstance.getValue(), width, height);
    }

    /**
     * Reposition the window.
     * 
     * @param pInstance
     * @param left
     * @param top
     */
    public void reposition(int left, int top) {
        reposition(pInstance.getValue(), left, top);
    }

    public boolean isClosed() {
        return isClosed(pInstance.getValue());
    }

    public boolean isPrimary() {
        return isPrimary(pInstance.getValue());
    }

    public void swapBuffers() throws OgreException {
        swapBuffers(true);
    }

    public void swapBuffers(boolean waitForVSync) throws OgreException {
        swapBuffers(pInstance.getValue(), waitForVSync);
    }

    /**
     * Updates the window contents.
     * 
     * @param pInstance
     */
    public void update() {
        update(pInstance.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderTarget#requiresTextureFlipping()
     */
    @Override
    public boolean requiresTextureFlipping() {
        return requiresTextureFlipping(pInstance.getValue());
    }

    // TODO: public void getMetrics(int width, int height, int colourDepth, int
    // left, int top);

    // -----------------------------------------------------------------------------
    // NATIVES
    // -----------------------------------------------------------------------------
    private static native void update(int pInstance);

    private static native boolean isPrimary(int pInstance);

    private static native void create(int pInstance, String name, int width,
            int height, boolean fullScreen, String title, int colourDepth,
            int left, int top, boolean depthBuffer, int externalWindowHandle,
            int parentWindowHandle, int FSAA, float displayFrequency,
            boolean vsync);

    private static native void destroy(int pInstance);

    private static native void resize(int pInstance, int width, int height);

    private static native void reposition(int pInstance, int left, int top);

    private static native boolean isClosed(int pInstance);

    private static native void swapBuffers(int pInstance, boolean waitForVSync)
            throws OgreException;

    private static native boolean requiresTextureFlipping(int pInstance);

    public Viewport getViewport(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    public void writeContentsToFile(String tmp) {
        // TODO Auto-generated method stub

    }
}