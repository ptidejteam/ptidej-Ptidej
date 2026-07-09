package org.ogre4j;

public class Quaternion {

    public static final Quaternion IDENTITY = new Quaternion(1.0f, 0.0f, 0.0f,
            0.0f);

    public float w;

    public float x;

    public float y;

    public float z;

    public Quaternion(float w, float x, float y, float z) {
        this.w = w;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Quaternion(float[] arr) {
        this.w = arr[0];
        this.x = arr[1];
        this.y = arr[2];
        this.z = arr[3];
    }
}