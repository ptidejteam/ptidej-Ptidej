package org.ogre4j;

import java.util.Vector;

public class RenderableImpl extends NativeObject implements Renderable {

    private static native short getNumWorldTransforms(int ptrSelf);

    private static native boolean getRenderDetailOverrideable(int ptrSelf);

    private static native float[] getWorldOrientation(int pInstance);

    protected RenderableImpl(InstancePointer ptr) {
        super(ptr);
    }

    public void _updateCustomGpuParameter() {
        // TODO Auto-generated method stub

    }

    public boolean getCastsShadows() {
        // TODO Auto-generated method stub
        return false;
    }

    public Vector<MovablePlane> getClipPlanes() {
        // TODO Auto-generated method stub
        return null;
    }

    public Vector4 getCustomParameter(long index) {
        // TODO Auto-generated method stub
        return null;
    }

    public Vector<Light> getLights() {
        // TODO Auto-generated method stub
        return null;
    }

    public Material getMaterial() {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean getNormaliseNormals() {
        return getNormaliseNormals(pInstance.getValue());
    }

    public short getNumWorldTransforms() {
        return getNumWorldTransforms(pInstance.getValue());
    }

    public int getRenderDetail() {
        return getRenderDetail(pInstance.getValue());
    }

    private static native boolean useIdentityProjection(int ptrSelf);

    private static native boolean useIdentityView(int ptrSelf);

    public boolean getRenderDetailOverrideable() {
        return getRenderDetailOverrideable(pInstance.getValue());
    }

    private static native boolean getNormaliseNormals(int ptrSelf);

    public void getRenderOperation(RenderOperation op) {
        // TODO Auto-generated method stub

    }

    public float getSquaredViewDepth(Camera cam) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Technique getTechnique() {
        // TODO Auto-generated method stub
        return null;
    }

    public Quaternion getWorldOrientation() {
        float[] tuple = getWorldOrientation(pInstance.getValue());
        return new Quaternion(tuple);
    }

    public Vector3 getWorldPosition() {
        // TODO Auto-generated method stub
        return null;
    }

    public void getWorldTransforms(Matrix4 xform) {
        // TODO Auto-generated method stub

    }

    public void setCustomParameter(long index, Vector4 value) {
        // TODO Auto-generated method stub

    }

    public void setRenderDetailOverrideable(boolean override) {
        // TODO Auto-generated method stub

    }

    private static native int getRenderDetail(int ptrSelf);

    public boolean useIdentityProjection() {
        return useIdentityProjection(pInstance.getValue());
    }

    public boolean useIdentityView() {
        return useIdentityView(pInstance.getValue());
    }

}
