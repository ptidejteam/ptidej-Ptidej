package org.ogre4j;

public class PlatformManager extends NativeObject {
    private static native int createInputReader(int ptrSelf);

    public static PlatformManager getSingleton() {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new PlatformManager(ptr);
    }

    private static native int getSingletonImpl();

    protected PlatformManager(InstancePointer ptr) {
        super(ptr);
    }

    /**
     * Gets a new instance of a platform-specific input reader.
     * 
     * @return
     */
    public InputReader createInputReader() {
        int address = createInputReader(pInstance.getValue());
        InstancePointer ptr = new InstancePointer(address);
        return new InputReader(ptr);
    }

    /**
     * Destroys an instance of a platform-specific input reader.
     * 
     * @param reader
     */
    public void destroyInputReader(InputReader reader) {

    }
}
