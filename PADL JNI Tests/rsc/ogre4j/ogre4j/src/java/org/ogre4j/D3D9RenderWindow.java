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
 * $Revision: 1.2 $
 * $Date: 2005/08/19 09:07:17 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * @author Kai Klesatschke <yavin@ogre4j.org> TODO RenderWindow type/class
 *         description.
 *         @deprecated is this class necessary, i think not
 */
public class D3D9RenderWindow extends RenderWindow {
    private D3D9RenderWindow() {
        super(new InstancePointer(createInstance()));
    }

    public D3D9RenderWindow(InstancePointer pInstance) {
        super(pInstance);
    }

    /**
     * @see org.ogre4j.RenderWindow#create(java.lang.String, int, int, boolean,
     *      java.lang.String, int, int, int, boolean, int, int, int, float,
     *      boolean)
     */
    public void create(String name, int width, int height, boolean fullScreen,
            int externalWindowHandle) {
        create(name, width, height, fullScreen, null, -1, -1, -1, true,
                externalWindowHandle, -1, -1, -1, false);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderWindow#create(java.lang.String, int, int, boolean,
     *      java.lang.String, int, int, int, boolean, int, int, int, float,
     *      boolean)
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

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderWindow#destroy()
     */
    public void destroy() {
        destroy(pInstance.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderWindow#reposition(int, int)
     */
    public void reposition(int left, int top) {
        reposition(pInstance.getValue(), left, top);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderWindow#resize(int, int)
     */
    public void resize(int width, int heigth) {
        resize(pInstance.getValue(), width, heigth);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderTarget#update()
     */
    public void update() {
        update(pInstance.getValue());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderWindow#isClosed()
     */
    @Override
    public boolean isClosed() {
        return isClosed(pInstance.getValue());
    }

    public void writeContentsToFile(String filename) {
        writeContentsToFile(pInstance.getValue(), filename);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.ogre4j.RenderWindow#swapBuffers(boolean)
     */
    @Override
    public void swapBuffers(boolean waitForVSync) throws OgreException {
        swapBuffers(pInstance.getValue(), waitForVSync);
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

    // -----------------------------------------------------------------------------
    // NATIVES
    // -----------------------------------------------------------------------------
    private static native int createInstance();

    private static native void create(int pInstance, String name, int width,
            int height, boolean fullScreen, String title, int colourDepth,
            int left, int top, boolean depthBuffer, int externalWindowHandle,
            int parentWindowHandle, int FSAA, float displayFrequency,
            boolean vsync);

    private static native void destroy(int pInstance);

    private static native void resize(int pInstance, int width, int height);

    private static native void reposition(int pInstance, int left, int top);

    private static native boolean isClosed(int pInstance);

    private static native void update(int pInstance);

    private static native void writeContentsToFile(int pInstance,
            String filename);

    private static native void swapBuffers(int pInstance, boolean waitForVSync)
            throws OgreException;

    private static native boolean requiresTextureFlipping(int pInstance);
}
