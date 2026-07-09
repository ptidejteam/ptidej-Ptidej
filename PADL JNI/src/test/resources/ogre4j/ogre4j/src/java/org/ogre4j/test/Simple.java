package org.ogre4j.test;

import org.ogre4j.FrameListener;
import org.ogre4j.OgreException;
import org.ogre4j.Root;

public class Simple {
    static {
        System.loadLibrary("Ogre4J");
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            Root r = new Root();

            if (r.showConfigDialog()) {
                r.initialise(true, "yayay");

                r.addFrameListener(new SimpleFL());

                r.startRendering();
            } else {
                return;
            }

            r.dispose();

        } catch (OgreException oe) {

        }

    }

}

class SimpleFL extends FrameListener {
    public boolean frameEnded(float timeSinceLastEvent, float timeSinceLastFrame) {
        return true;
    }
}