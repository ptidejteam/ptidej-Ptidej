package org.ogre4j;

abstract public class RenderQueueListener extends NativeObject {

    abstract public void renderQueueStarted(int id, boolean skipThisQueue);

    abstract public void renderQueueEnded(int id, boolean repeatThisQueue);
}
