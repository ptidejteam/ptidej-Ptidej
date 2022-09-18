package org.ogre4j;

public class Vector3 {

    public static final Vector3 NEGATIVE_UNIT_X = new Vector3(-1.0f, 0.0f, 0.0f);

    public static final Vector3 NEGATIVE_UNIT_Y = new Vector3(0.0f, -1.0f, 0.0f);

    public static final Vector3 NEGATIVE_UNIT_Z = new Vector3(0.0f, 0.0f, -1.0f);

    public static final Vector3 UNIT_SCALE = new Vector3(1.0f, 1.0f, 1.0f);

    public static final Vector3 UNIT_X = new Vector3(1.0f, 0.0f, 0.0f);

    public static final Vector3 UNIT_Y = new Vector3(0.0f, 1.0f, 0.0f);

    public static final Vector3 UNIT_Z = new Vector3(0.0f, 0.0f, 1.0f);

    public static final Vector3 ZERO = new Vector3(0.f, 0.0f, 0.0f);

    public float x;

    public float y;

    public float z;

    public Vector3() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3(float[] arr) {
        this.x = arr[0];
        this.y = arr[1];
        this.z = arr[2];
    }

    public Vector3(Vector3 rhs) {
        this(rhs.x, rhs.y, rhs.z);
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void normalise() {
        // TODO Auto-generated method stub
    }

    public int dotProduct(Vector3 rkPoint) {
        // TODO Auto-generated method stub
        return 0;
    }

    public Vector3 crossProduct(Vector3 edge2) {
        // TODO Auto-generated method stub
        return null;
    }

    public Vector3 sub(Vector3 rkPoint0) {
        // TODO Auto-generated method stub
        return null;
    }

}
