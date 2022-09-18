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
 * Root.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.8 $
 * $Date: 2005/08/26 06:52:31 $
 * $Author: yavin02 $
 */
package org.ogre4j;

/**
 * TODO Root type/class description.
 * 
 * @author Kai Klesatschke <yavin@ogre4j.org>
 */
public class Root extends NativeObject {
    private static native void addFrameListener(int ptrSelf, int ptrNewListener);

    private static native void addResourceLocation(int ptrSelf, String name,
            String locType, String groupName, boolean recursive);

    private static native void clearEventTimes(int pInstance);

    private static native int createCppInstance(String pluginFileName,
            String configFileName, String logFileName) throws OgreException;

    private static native int createRenderWindow(int ptrRoot, String name,
            int width, int height, boolean fullScreen, String title,
            int colourDepth, int left, int top, boolean depthBuffer,
            int externalWindowHandle, int parentWindowHandle, int FSAA,
            float displayFrequency, boolean vsync) throws OgreException;

    private static native int[] getAvailableRenderes(int pInstance);

    private static native String getErrorDescription(int pInstance,
            long errorNumber);

    private static native int getMeshManager(int ptrSelf);

    private static native int getRenderSystem(int ptrSelf);

    private static native int getSceneManager(int pInstance, int sceneType);

    /**
     * Returns the singleton of the Root.
     * 
     * @return Returns the Root. This can be null if the object wasn't
     *         initialized before.
     * @throws OgreException
     *             if any error occurs while creating the singleton.
     */
    public static Root getSingleton() throws OgreException {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new Root(ptr);
    }

    private static native int getSingletonImpl();

    private static native int getTextureManager(int ptrSelf);

    private static native int initialise(int ptrRoot, boolean autoCreateWindow,
            String windowTitle);

    private static native void loadPlugin(int pInstance, String pluginName)
            throws OgreException;

    private static native void queueEndRendering(int pInstance);

    private static native void removeFrameListener(int ptrSelf, int oldListener);

    private static native boolean renderOneFrame(int pInstance) throws OgreException;

    private static native void setRenderSystem(int pInstance, int pRenderSystem)
            throws OgreException;

    private static native boolean showConfigDialog(int ptrInstance);

    private static native void shutdown(int pInstance);

    private static native void startRendering(int pInstance);

    private static native void unloadPlugin(int pInstance, String pluginName)
            throws OgreException;

    public Root() throws OgreException {
        this("plugins.cfg");
    }

    protected Root(InstancePointer ptr) {
        super(ptr);
    }

    public Root(String pluginFileName) throws OgreException {
        this(pluginFileName, "ogre.cfg", "Ogre.log");
    }

    public Root(String pluginFileName, String configFileName)
            throws OgreException {
        this(pluginFileName, configFileName, "Ogre.log");
    }

    /**
     * 
     * 
     * @param pluginFileName
     * @param configFileName
     * @param logFileName
     * @throws OgreException
     */
    public Root(String pluginFileName, String configFileName, String logFileName)
            throws OgreException {
        super(new InstancePointer(createCppInstance(pluginFileName,
                configFileName, logFileName)));
    }

    /**
     * Registers a FrameListener which will be called back every frame.
     * 
     * @param frameListener
     */
    public void addFrameListener(FrameListener newListener) {
        addFrameListener(pInstance.getValue(), newListener.pInstance.getValue());
    }

    public void addResourceLocation(String name, String locType) {
        addResourceLocation(name, locType,
                ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);
    }

    public void addResourceLocation(String name, String locType,
            String groupName) {
        addResourceLocation(name, locType, groupName, false);
    }

    public void addResourceLocation(String name, String locType,
            String groupName, boolean recursive) {
        addResourceLocation(pInstance.getValue(), name, locType, groupName,
                recursive);
    }

    /**
     * Clears the history of all event times.
     * 
     */
    public void clearEventTimes() {
        clearEventTimes(pInstance.getValue());
    }

    public RenderWindow createRenderWindow(String name, int width, int height,
            boolean fullScreen, int externalWindowHandle) throws OgreException {
        return createRenderWindow(name, width, height, fullScreen, "", -1, -1,
                -1, true, externalWindowHandle, -1, -1, -1, false);
    }

    /**
     * This method creates a new rendering window as specified by the
     * paramteters. The rendering system could be responible for only a single
     * window (e.g. in the case of a game), or could be in charge of multiple
     * ones (in the case of a level editor). The option to create the window as
     * a child of another is therefore given. This method will create an
     * appropriate subclass of RenderWindow depending on the API and platform
     * implementation.
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
    public RenderWindow createRenderWindow(String name, int width, int height,
            boolean fullScreen, String title, int colourDepth, int left,
            int top, boolean depthBuffer, int externalWindowHandle,
            int parentWindowHandle, int FSAA, float displayFrequency,
            boolean vsync) throws OgreException {
        int address = createRenderWindow(pInstance.getValue(), name, width,
                height, fullScreen, title, colourDepth, left, top, depthBuffer,
                externalWindowHandle, parentWindowHandle, FSAA,
                displayFrequency, vsync);
        InstancePointer ptr = new InstancePointer(address);
        return new RenderWindow(ptr);
    }

    /**
     * Retrieve a list of the available render systems.
     * 
     * @see Ogre::Root::getAvailableRenderers(void)
     * @see org.ogre4.RenderSystem.Type
     * @param pInstance
     *            This instance pointer.
     * @return Returns a int array which contains the instance pointers and
     *         their types. Array[0]=object_ptr,Array[1]=object_type.
     */
    public RenderSystemList getAvailableRenderers() {
        int objects[] = getAvailableRenderes(pInstance.getValue());
        if (objects == null || objects.length == 0)
            return null;

        RenderSystemList list = new RenderSystemList();
        for (int i = 0; i < objects.length; i = i + 2) {
            RenderSystem renderSystem = null;
            InstancePointer ptr = new InstancePointer(objects[i]);
            int type = objects[i + 1];
            if (type == RenderSystem.Type.DirectX7.ordinal())
                renderSystem = new D3D7RenderSystem(ptr);
            else if (type == RenderSystem.Type.DirectX9.ordinal())
                renderSystem = new D3D9RenderSystem(ptr);
            else if (type == RenderSystem.Type.OpenGL.ordinal())
                renderSystem = new GLRenderSystem(ptr);

            if (renderSystem != null)
                list.add(renderSystem);
        }

        return list;
    }

    /**
     * Utility function for getting a better description of an error code.
     * 
     * @param errorNumber
     * @return
     */
    public String getErrorDescription(long errorNumber) {
        return getErrorDescription(pInstance.getValue(), errorNumber);
    }

    /**
     * Retrieves a reference to the current MeshManager.
     * 
     * @return
     */
    public MeshManager getMeshManager() {
        int address = getMeshManager(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new MeshManager(ptr);
    }

    /**
     * Retrieve a pointer to the currently selected render system.
     * 
     * @return
     */
    public RenderSystem getRenderSystem() {
        int address = getRenderSystem(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new RenderSystem(ptr);
    }

    /**
     * Gets a reference to a SceneManager object.
     * 
     * @param sceneType
     * @return
     */
    public SceneManager getSceneManager(int sceneType) {
        int address = getSceneManager(pInstance.getValue(), sceneType);
        InstancePointer ptr = new InstancePointer(address);
        return new SceneManager(ptr);
    }

    /**
     * Retrieves a reference to the current TextureManager.
     * 
     * @return
     */
    public TextureManager getTextureManager() {
        int address = getTextureManager(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new TextureManager(ptr);
    }

    /**
     * @param autoCreateWindow
     * @param windowTitle
     * @return Pointer to a object of type Ogre::RenderWindow
     */
    public RenderWindow initialise(boolean autoCreateWindow, String windowTitle) {
        int address = initialise(pInstance.getValue(), autoCreateWindow,
                windowTitle);
        InstancePointer ptr = new InstancePointer(address);
        return new RenderWindow(ptr);
    }

    /**
     * Manually load a plugin.
     * 
     * @param pluginName
     * @throws OgreException
     */
    public void loadPlugin(String pluginName) throws OgreException {
        loadPlugin(pInstance.getValue(), pluginName);
    }

    /**
     * Queues the end of rendering.
     * 
     */
    public void queueEndRendering() {
        queueEndRendering(pInstance.getValue());
    }

    /**
     * Removes a FrameListener from the list of listening classes.
     * 
     */
    public void removeFrameListener(FrameListener oldListener) {
        removeFrameListener(pInstance.getValue(), oldListener.pInstance
                .getValue());
    }

    /**
     * Renders one frame.
     */
    public boolean renderOneFrame() throws OgreException {
        return renderOneFrame(pInstance.getValue());
    }

    /**
     * Sets the rendering subsystem to be used.
     * 
     * @see #setRenderSystem(int, int)
     * @param renderSystem
     *            The render system to use.
     */
    public void setRenderSystem(RenderSystem renderSystem) throws OgreException {
        setRenderSystem(pInstance.getValue(), renderSystem.getInstancePtr()
                .getValue());
    }

    /**
     * Displays a dialog asking the user to choose system settings.
     * 
     * @return
     */
    public boolean showConfigDialog() {
        return showConfigDialog(pInstance.getValue());
    }

    /**
     * Shuts down the system manually.
     * 
     */
    public void shutdown() {
        shutdown(pInstance.getValue());
    }

    /**
     * Starts / restarts the automatic rendering cycle.
     * 
     */
    public void startRendering() {
        startRendering(pInstance.getValue());
    }

    /**
     * Manually unloads a plugin.
     * 
     * @param pluginName
     * @throws OgreException
     */
    public void unloadPlugin(String pluginName) throws OgreException {
        unloadPlugin(pInstance.getValue(), pluginName);
    }
}
