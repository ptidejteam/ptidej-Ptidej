package org.ogre4j.examples;

import org.ogre4j.Animation;
import org.ogre4j.AnimationState;
import org.ogre4j.AnimationTrack;
import org.ogre4j.Camera;
import org.ogre4j.ColourValue;
import org.ogre4j.Common;
import org.ogre4j.Entity;
import org.ogre4j.KeyFrame;
import org.ogre4j.Light;
import org.ogre4j.MeshManager;
import org.ogre4j.OgreException;
import org.ogre4j.Plane;
import org.ogre4j.RenderWindow;
import org.ogre4j.ResourceGroupManager;
import org.ogre4j.SceneNode;
import org.ogre4j.Vector3;

public class CameraTrack extends ExampleApplication {
    static {
        System.loadLibrary("Ogre4J-Win32");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new CameraTrack().go();

    }

    AnimationState animState;

    private SceneNode fountainNode;

    @Override
    protected void createScene() {
        try {
            // Set ambient light
            sceneMgr.setAmbientLight(new ColourValue(0.2f, 0.2f, 0.2f));

            // Create a skydome
            sceneMgr.setSkyDome(true, "Examples/CloudySky", 5.0f, 8.0f);

            // Create a light
            Light l = sceneMgr.createLight("MainLight");

            // Accept default settings: point light, white diffuse, just set
            // position
            // NB I could attach the light to a SceneNode if I wanted it to move
            // automatically with
            // other objects, but I don't
            l.setPosition(20, 80, 50);

            Plane p = new Plane();
            p.normal = Vector3.UNIT_Y;
            p.d = 200;

            MeshManager.getSingleton().createPlane("FloorPlane",
                    ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME, p,
                    200000, 200000, 20, 20, true, 1, 50, 50, Vector3.UNIT_Z);

            // Create an entity (the floor)
            Entity ent = sceneMgr.createEntity("floor", "FloorPlane");
            ent.setMaterialName("Examples/RustySteel");

            // Attach to child of root node, better for culling (otherwise
            // bounds are the combination of the 2)
            sceneMgr.getRootSceneNode().createChildSceneNode()
                    .attachObject(ent);

            // Add a head, give it it's own node
            SceneNode headNode = sceneMgr.getRootSceneNode()
                    .createChildSceneNode();
            ent = sceneMgr.createEntity("head", "ogrehead.mesh");
            headNode.attachObject(ent);

            // Make sure the camera track this node
            camera.setAutoTracking(true, headNode);

            // Create the camera node & attach camera
            SceneNode camNode = sceneMgr.getRootSceneNode()
                    .createChildSceneNode();
            camNode.attachObject(camera);

            // set up spline animation of node
            Animation anim = sceneMgr.createAnimation("CameraTrack", 10);

            // Spline it for nice curves
            anim.setInterpolationMode(Animation.IM_SPLINE);

            // Create a track to animate the camera's node
            AnimationTrack track = anim.createTrack((short) 0, camNode);

            // Setup keyframes
            KeyFrame key = track.createKeyFrame(0); // startposition
            key = track.createKeyFrame(2.5f);
            key.setTranslate(new Vector3(500, 500, -1000));
            key = track.createKeyFrame(5);
            key.setTranslate(new Vector3(-1500, 1000, -600));
            key = track.createKeyFrame(7.5f);
            key.setTranslate(new Vector3(0, -100, 0));
            key = track.createKeyFrame(10);
            key.setTranslate(new Vector3(0, 0, 0));

            // Create a new animation state to track this
            animState = sceneMgr.createAnimationState("CameraTrack");
            animState.setEnabled(true);

            // Put in a bit of fog for the hell of it
            sceneMgr.setFog(Common.FOG_EXP, ColourValue.White, 0.0002f);

        } catch (OgreException oe) {
            System.err.println(oe.getMessage());
            System.exit(0);
        }
    }

    public void createFrameListener() {
        frameListener = new CameraTrackListener(window, camera);
        ((CameraTrackListener) frameListener).setAnimState(animState);
        root.addFrameListener(frameListener);
    }
}

class CameraTrackListener extends ExampleFrameListener {

    public CameraTrackListener(RenderWindow window, Camera camera) {
        super(window, camera);
    }

    public boolean frameStarted(float timeSinceLastEvent,
            float timeSinceLastFrame) {
        animState.addTime(timeSinceLastFrame);

        // Call default
        return super.frameStarted(timeSinceLastEvent, timeSinceLastFrame);
    }

    private AnimationState animState;

    public void setAnimState(AnimationState animState) {
        this.animState = animState;
    }
}
