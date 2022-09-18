package org.ogre4j;

public class TextureManager extends ResourceManager {

    private static native int createCppInstance();

    private static native void enable32BitTextures(int ptrSelf, boolean enable);

    private static native long getDefaultNumMipmaps(int ptrSelf);

    public static TextureManager getSingleton() {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new TextureManager(ptr);
    }

    private static native int getSingletonImpl();

    private static native void setDefaultNumMipmaps(int ptrSelf, long num);

    // public TextureManager() {
    // super(new InstancePointer(createCppInstance()));
    // }

    protected TextureManager(InstancePointer ptr) {
        super(ptr);
    }

    /**
     * Enables / disables 32-bit textures.
     * 
     * @param setting
     */
    public void enable32BitTextures(boolean setting) {
        enable32BitTextures(pInstance.getValue(), setting);
    }

    /**
     * Gets the default number of mipmaps to be used for loaded textures.
     * 
     * @return
     */
    public long getDefaultNumMipmaps() {
        return getDefaultNumMipmaps(pInstance.getValue());
    }

    /**
     * Caused all currently loaded resources to be reloaded.
     * 
     */
    public void reloadAll() {

    }

    /**
     * Sets the default number of mipmaps to be used for loaded textures, for
     * when textures are loaded automatically (e.g.
     * 
     * @param num
     */
    public void setDefaultNumMipmaps(long num) {
        setDefaultNumMipmaps(pInstance.getValue(), num);
    }

}
