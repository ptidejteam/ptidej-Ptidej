package org.ogre4j.examples;

import org.ogre4j.Animation;
import org.ogre4j.AnimationTrack;
import org.ogre4j.Billboard;
import org.ogre4j.BillboardSet;
import org.ogre4j.ColourValue;
import org.ogre4j.GpuProgramManager;
import org.ogre4j.KeyFrame;
import org.ogre4j.Light;
import org.ogre4j.OgreException;
import org.ogre4j.SceneNode;
import org.ogre4j.Vector3;

public class Shadows extends ExampleApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    private int currentAtheneMaterial;

    @Override
    protected void createScene() {
        generalSceneSetup();
    }

    private Light sunLight;

    private Light light;

    private SceneNode lightNode;

    private ColourValue minLightColour = new ColourValue(0.3f, 0.0f, 0.0f);

    private void generalSceneSetup() {
        try {
            // do this first so we generate edge lists
            // TODO
            // sceneMgr.setShadowTechnique(SHADOWTYPE_STENCIL_ADDITIVE);

            // Set the default Athene material
            // We'll default it to the normal map for ps_2_0 capable hardware
            // everyone else will default to the basic
            if (GpuProgramManager.getSingleton().isSyntaxSupported("ps_2_0")
                    || GpuProgramManager.getSingleton().isSyntaxSupported(
                            "arbfp1")) {
                currentAtheneMaterial = 0;
            } else {
                currentAtheneMaterial = 1;
            }

            // Set ambient light off
            sceneMgr.setAmbientLight(new ColourValue(0.0f, 0.0f, 0.0f));

            // Fixed light, dim
            sunLight = sceneMgr.createLight("SunLight");
            sunLight.setType(Light.LT_SPOTLIGHT);
            sunLight.setPosition(1000, 1250, 500);
            // TODO
            // sunLight.setSpotlightRange(Degree(30), Degree(50));

            Vector3 dir = new Vector3();
            // TODO dir = -sunLight.getPosition();
            dir.normalise();
            sunLight.setDirection(dir);
            sunLight.setDiffuseColour(0.35f, 0.35f, 0.38f);
            sunLight.setSpecularColour(0.9f, 0.9f, 1.0f);

            // Point light, movable, reddish
            light = sceneMgr.createLight("Light2");
            light.setDiffuseColour(minLightColour);
            light.setSpecularColour(1, 1, 1);
            light.setAttenuation(8000.0f, 1.0f, 0.0005f, 0.0f);

            // Create light node
            lightNode = sceneMgr.getRootSceneNode().createChildSceneNode(
                    "MovingLightNode");
            lightNode.attachObject(light);

            // create billboard set
            BillboardSet bbs = sceneMgr.createBillboardSet("lightbbs", 1);
            bbs.setMaterialName("Examples/Flare");
            Billboard bb = bbs.createBillboard(0, 0, 0, minLightColour);
            // attach
            lightNode.attachObject(bbs);

            // TODO create controller, after this is will get updated on its own
            // ControllerFunctionRealPtr func = ControllerFunctionRealPtr(
            // new WaveformControllerFunction(Ogre::WFT_SINE, 0.75, 0.5));
            // ControllerManager& contMgr = ControllerManager::getSingleton();
            // ControllerValueRealPtr val = ControllerValueRealPtr(
            // new LightWibbler(mLight, bb, mMinLightColour, mMaxLightColour,
            // mMinFlareSize, mMaxFlareSize));
            // Controller<Real>* controller = contMgr.createController(
            // contMgr.getFrameTimeSource(), val, func);

            lightNode.setPosition(new Vector3(300.0f, 250.0f, -300.0f));

            // Create a track for the light
            Animation anim = sceneMgr.createAnimation("LightTrack", 20);
            // Spline it for nice curves
            anim.setInterpolationMode(Animation.IM_SPLINE);
            // Create a track to animate the camera's node
            AnimationTrack track = anim.createTrack(0, lightNode);

            // Setup keyframes
            KeyFrame key = track.createKeyFrame(0); // A startposition
            key.setTranslate(new Vector3(300, 250, -300));
            key = track.createKeyFrame(2);// B
            key.setTranslate(new Vector3(150, 300, -250));
            key = track.createKeyFrame(4);// C
            key.setTranslate(new Vector3(-150, 350, -100));
            key = track.createKeyFrame(6);// D
            key.setTranslate(new Vector3(-400, 200, -200));
            key = track.createKeyFrame(8);// E
            key.setTranslate(new Vector3(-200, 200, -400));
            key = track.createKeyFrame(10);// F
            key.setTranslate(new Vector3(-100, 150, -200));
            key = track.createKeyFrame(12);// G
            key.setTranslate(new Vector3(-100, 75, 180));
            key = track.createKeyFrame(14);// H
            key.setTranslate(new Vector3(0, 250, 300));
            key = track.createKeyFrame(16);// I
            key.setTranslate(new Vector3(100, 350, 100.0f));
            key = track.createKeyFrame(18);// J
            key.setTranslate(new Vector3(250.0f, 300.0f, 0.0f));
            key = track.createKeyFrame(20);// K == A
            key.setTranslate(new Vector3(300, 250, -300));

            // Create a new animation state to track this
            // mAnimState = sceneMgr.createAnimationState("LightTrack");
            // mAnimState.setEnabled(true);
            // Make light node look at origin, this is for when we
            // change the moving light to a spotlight
            lightNode.setAutoTracking(true, sceneMgr.getRootSceneNode());

            // Prepare athene mesh for normalmapping
            // Mesh pAthene = MeshManager.getSingleton().load("athene.mesh",
            // ResourceGroupManager.DEFAULT_RESOURCE_GROUP_NAME);
            // unsigned short src, dest;
            // if (!pAthene->suggestTangentVectorBuildParams(src, dest))
            // {
            // pAthene->buildTangentVectors(src, dest);
            // }

            // rest

        } catch (OgreException oe) {

        }
    }
}
