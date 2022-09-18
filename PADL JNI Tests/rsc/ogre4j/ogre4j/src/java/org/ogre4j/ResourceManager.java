package org.ogre4j;

public class ResourceManager extends ScriptLoader {

    private static native long getMemoryBudget(int ptrSelf);

    private static native void remove(int ptrSelf, String name);

    private static native void removeAll(int ptrSelf);

    private static native void setMemoryBudget(int ptrSelf, long bytes);

    private static native void unloadAll(int ptrSelf);

    private ResourceManager() {
        super(null);
    }

    protected ResourceManager(InstancePointer ptr) {
        super(ptr);
    }

    /**
     * Get the limit on the amount of memory this resource handler may use.
     * 
     * @return
     */
    public long getMemoryBudget() {
        return getMemoryBudget(pInstance.getValue());
    }

    /**
     * Remove a single resource by name.
     * 
     * @param name
     */
    public void remove(String name) {
        remove(pInstance.getValue(), name);
    }

    /**
     * Removes all resources.
     * 
     */
    public void removeAll() {
        removeAll(pInstance.getValue());
    }

    /**
     * Set a limit on the amount of memory this resource handler may use.
     * 
     * @param bytes
     */
    public void setMemoryBudget(long bytes) {
        setMemoryBudget(pInstance.getValue(), bytes);
    }

    /**
     * Unloads all resources.
     * 
     */
    public void unloadAll() {
        unloadAll(pInstance.getValue());
    }

}
