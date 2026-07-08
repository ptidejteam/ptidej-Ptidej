package org.ogre4j;

public interface ManualResourceLoader {
    /**
     * Called when a resource wishes to load.
     * 
     * @param resource
     */
    public void loadResource(Resource resource);
}
