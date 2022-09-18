package org.ogre4j;

import java.util.Vector;

public class OverlayElement extends StringInterface implements Renderable {

    protected OverlayElement(InstancePointer pInstance) {
        super(pInstance);
        renderable = new RenderableImpl(pInstance);
    }

    private Renderable renderable;

    public boolean getCastsShadows() {
        return renderable.getCastsShadows();
    }

    public Vector4 getCustomParameter(long index) {
        return renderable.getCustomParameter(index);
    }

    public Material getMaterial() {
        return renderable.getMaterial();
    }

    public boolean getNormaliseNormals() {
        return renderable.getNormaliseNormals();
    }

    public int getRenderDetail() {
        return renderable.getRenderDetail();
    }

    public Vector<Light> getLights() {
        return renderable.getLights();
    }

    public void setRenderDetailOverrideable(boolean override) {
        renderable.setRenderDetailOverrideable(override);
    }

    public boolean getRenderDetailOverrideable() {
        return renderable.getRenderDetailOverrideable();
    }

    public short getNumWorldTransforms() {
        return renderable.getNumWorldTransforms();
    }

    public void _updateCustomGpuParameter() {

        renderable._updateCustomGpuParameter();
    }

    public void getRenderOperation(RenderOperation op) {
        renderable.getRenderOperation(op);
    }

    public Vector<MovablePlane> getClipPlanes() {
        return renderable.getClipPlanes();
    }

    public float getSquaredViewDepth(Camera cam) {
        return renderable.getSquaredViewDepth(cam);
    }

    public Technique getTechnique() {
        return renderable.getTechnique();
    }

    public Quaternion getWorldOrientation() {
        return renderable.getWorldOrientation();
    }

    public Vector3 getWorldPosition() {
        return renderable.getWorldPosition();
    }

    public void getWorldTransforms(Matrix4 xform) {
        renderable.getWorldTransforms(xform);
    }

    public void setCustomParameter(long index, Vector4 value) {
        renderable.setCustomParameter(index, value);
    }

    public boolean useIdentityProjection() {
        return renderable.useIdentityProjection();
    }

    public boolean useIdentityView() {
        return renderable.useIdentityView();
    }

    /**
     * Sets the caption on elements that support it.
     * 
     * @param text
     */
    public void setCaption(String text) {
        setCaption(pInstance.getValue(), text);
    }

    private static native void setCaption(int ptrSelf, String text);

}
