package org.ogre4j;

public class MaterialManager extends NativeObject {

    public static MaterialManager getSingleton() {
        int address = getSingletonImpl();
        InstancePointer ptr = new InstancePointer(address);
        return new MaterialManager(ptr);
    }

    private static native int getSingletonImpl();

    private static native void setDefaultAnisotropy(int ptrSelf, int maxAniso);

    private static native void setDefaultTextureFiltering(int ptrSelf, int fo);

    public MaterialManager(InstancePointer ptr) {
        super(ptr);
    }

    public void setDefaultAnisotropy(int maxAniso) {
        setDefaultAnisotropy(pInstance.getValue(), maxAniso);
    }

    public void setDefaultTextureFiltering(int fo) {
        setDefaultTextureFiltering(pInstance.getValue(), fo);
    }
}
