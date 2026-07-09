package org.ogre4j.examples;

import org.ogre4j.ColourValue;
import org.ogre4j.Light;
import org.ogre4j.OgreException;
import org.ogre4j.MovablePlane;
import org.ogre4j.Vector3;

public class Terrain extends ExampleApplication {

    protected void createCamera() {
        try {
            // Create the camera
            camera = sceneMgr.createCamera("PlayerCam");

            // Position it at 500 in Z direction
            camera.setPosition(new Vector3(128.0f, 25.0f, 128.0f));
            // Look back along -Z
            camera.lookAt(new Vector3(0.0f, 0.0f, -300.0f));
            camera.setNearClipDistance(1);
            camera.setFarClipDistance(1000);
        } catch (OgreException oe) {

        }

    }

    @Override
    protected void createScene() {
        try {
            MovablePlane waterPlane = new MovablePlane();

            // Set ambient light
            sceneMgr.setAmbientLight(new ColourValue(0.5f, 0.5f, 0.5f));

            // Create a light
            Light l = sceneMgr.createLight("MainLight");
            // Accept default settings: point light, white diffuse, just set
            // position
            // NB I could attach the light to a SceneNode if I wanted it to move
            // automatically with
            // other objects, but I don't
            l.setPosition(20, 80, 50);

            // Fog
            // NB it's VERY important to set this before calling
            // setWorldGeometry
            // because the vertex program picked will be different
            ColourValue fadeColour = new ColourValue(0.93f, 0.86f, 0.76f);
            // TODO sceneMgr.setFog( FOG_LINEAR, fadeColour, .001, 500, 1000);
            window.getViewport(0).setBackgroundColour(fadeColour);

            // TODO std::string terrain_cfg("terrain.cfg");
            // #if OGRE_PLATFORM == OGRE_PLATFORM_APPLE
            // terrain_cfg = mResourcePath + terrain_cfg;
            // #endif

            // sceneMgr.setWorldGeometry(terrain_cfg);
            // // Infinite far plane?
            // if (root.getRenderSystem().getCapabilities().hasCapability(
            // RSC_INFINITE_FAR_PLANE)) {
            // camera.setFarClipDistance(0);
            // }

        } catch (OgreException oe) {

        }

    }
}
