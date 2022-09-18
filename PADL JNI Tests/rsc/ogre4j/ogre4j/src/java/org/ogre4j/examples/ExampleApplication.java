package org.ogre4j.examples;

import org.ogre4j.Camera;
import org.ogre4j.ColourValue;
import org.ogre4j.OgreException;
import org.ogre4j.RenderWindow;
import org.ogre4j.ResourceGroupManager;
import org.ogre4j.Root;
import org.ogre4j.SceneManager;
import org.ogre4j.SceneType;
import org.ogre4j.TextureManager;
import org.ogre4j.Vector3;
import org.ogre4j.Viewport;

abstract public class ExampleApplication {

    protected Camera camera;

    protected ExampleFrameListener frameListener;

    protected Root root;

    protected SceneManager sceneMgr;

    protected RenderWindow window;

    protected void chooseSceneManager() {
        sceneMgr = root.getSceneManager(SceneType.ST_GENERIC);
    }

    protected boolean configure() {
        if (root.showConfigDialog()) {
            window = root.initialise(true, "Example Application");
            return true;
        } else {
            return false;
        }
    }

    protected void createCamera() {
        // Create the camera
        try {
            camera = sceneMgr.createCamera("PlayerCam");
        } catch (OgreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Position it at 500 in Z direction
        camera.setPosition(new Vector3(0.0f, 0.0f, 500.0f));

        // Look back along -Z
        camera.lookAt(new Vector3(0.0f, 0.0f, -300.0f));
        camera.setNearClipDistance(5.0f);
    }

    protected void createFrameListener() {
        frameListener = new ExampleFrameListener(window, camera);
        // TODO should be true
        frameListener.showDebugOverlay(false);
        root.addFrameListener(frameListener);

    }

    /**
     * Optional override method where you can create resource listeners (e.g.
     * for loading screens)
     * 
     */
    protected void createResourceListener() {
    }

    abstract protected void createScene();

    protected void createViewports() {
        // Create one viewport, entire window
        Viewport vp = window.addViewport(camera);
        vp.setBackgroundColour(new ColourValue(0, 0, 0));

        // Alter the camera aspect ratio to match the viewport
        camera.setAspectRatio(vp.getActualWidth() / vp.getActualHeight());
    }

    protected void destroyScene() {
    }

    protected void go() {
        if (!setup()) {
            return;
        }

        root.startRendering();

        destroyScene();
    }

    /**
     * 
     * Optional override method where you can perform resource group loading
     * Must at least do
     * ResourceGroupManager::getSingleton().initialiseAllResourceGroups();
     * 
     */
    protected void loadResources() {
        try {
            ResourceGroupManager.getSingleton().initialiseAllResourceGroups();
        } catch (OgreException e) {
            System.out.println("HIHI");
            e.printStackTrace();
        }
    }

    protected boolean setup() {
        try {
            root = new Root();
        } catch (OgreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        setupResources();

        boolean carryOn = configure();
        if (!carryOn) {
            return false;
        }

        chooseSceneManager();
        createCamera();
        createViewports();

        TextureManager.getSingleton().setDefaultNumMipmaps(5);

        createResourceListener();

        loadResources();

        createScene();

        createFrameListener();
        return true;
    }

    /**
     * Method which will define the source of resources (other than current
     * folder)
     * 
     */
    protected void setupResources() {
        try {
            ResourceGroupManager rgm = ResourceGroupManager.getSingleton();

            final String FILESYSTEM = "FileSystem";
            final String ZIP = "Zip";

            rgm.addResourceLocation("media/packs/OgreCore.zip", ZIP,
                    ResourceGroupManager.BOOTSTRAP_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/fonts", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/materials/programs", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/materials/scripts", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/materials/textures", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/models", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/overlays", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/particle", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/gui", FILESYSTEM,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/packs/cubemap.zip", ZIP,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/packs/cubemapsJS.zip", ZIP,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/packs/dragon.zip", ZIP,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/packs/fresneldemo.zip", ZIP,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/packs/ogretestmap.zip", ZIP,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            rgm.addResourceLocation("media/packs/skybox.zip", ZIP,
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);

            // FileSystem=media
            // FileSystem=media/fonts
            // FileSystem=media/materials/programs
            // FileSystem=media/materials/scripts
            // FileSystem=media/materials/textures
            // FileSystem=media/models
            // FileSystem=media/overlays
            // FileSystem=media/particle
            // FileSystem=media/gui
            // Zip=media/packs/cubemap.zip
            // Zip=media/packs/cubemapsJS.zip
            // Zip=media/packs/dragon.zip
            // Zip=media/packs/fresneldemo.zip
            // Zip=media/packs/ogretestmap.zip
            // Zip=media/packs/skybox.zip

        } catch (OgreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
