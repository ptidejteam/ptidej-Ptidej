package org.ogre4j.examples;

import org.ogre4j.ColourValue;
import org.ogre4j.Entity;
import org.ogre4j.Light;
import org.ogre4j.OgreException;
import org.ogre4j.ParticleSystem;
import org.ogre4j.ParticleSystemManager;

public class SkyBox extends ExampleApplication {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

    private ParticleSystem pThrusters;

    @Override
    protected void createScene() {
        try {
            // Set ambient light
            sceneMgr.setAmbientLight(new ColourValue(0.5f, 0.5f, 0.5f));

            // Create a skybox
            sceneMgr.setSkyBox(true, "Examples/SpaceSkyBox", 50);

            // Create a light
            Light l = sceneMgr.createLight("MainLight");

            // Accept default settings: point light, white diffuse, just set
            // position
            // NB I could attach the light to a SceneNode if I wanted it to move
            // automatically with
            // other objects, but I don't
            l.setPosition(20, 80, 50);

            // Also add a nice starship in
            Entity ent = sceneMgr.createEntity("razor", "razor.mesh");

            sceneMgr.getRootSceneNode().attachObject(ent);

            pThrusters = ParticleSystemManager.getSingleton().createSystem(
                    "ParticleSys1", 200);

            pThrusters.setMaterialName("Examples/Flare");
            pThrusters.setDefaultDimensions(25, 25);

            ParticleEmitter pEmit1 = pThrusters.addEmitter("Point");
            ParticleEmitter pEmit2 = pThrusters.addEmitter("Point");

            // Thruster 1

            // TODO
            // pEmit1.setAngle( Degree(3) );
            pEmit1.setTimeToLive(0.2);
            pEmit1.setEmissionRate(70);

            pEmit1.setParticleVelocity(50);

            // pEmit1.setDirection(-Vector3.UNIT_Z);
            // pEmit1.setColour(ColourValue.White, ColourValue.Red);

            // Thruster 2
            // pEmit2.setAngle(Degree(3));
            pEmit2.setTimeToLive(0.2);
            pEmit2.setEmissionRate(70);

            pEmit2.setParticleVelocity(50);

            // pEmit2->setDirection( -Vector3::UNIT_Z );
            // pEmit2->setColour( ColourValue::White, ColourValue::Red );

            // Set the position of the thrusters
            // pEmit1.setPosition( Vector3( 5.7f, 0.0f, 0.0f ) );
            // pEmit2.setPosition( Vector3( -18.0f, 0.0f, 0.0f ) );

            // mSceneMgr->getRootSceneNode()->createChildSceneNode( Vector3(
            // 0.0f, 6.5f, -67.0f ) )
            // ->attachObject(pThrusters);

        } catch (OgreException oe) {

        }

    }
}
