package org.ogre4j;

/**
 * 100% complete
 * 
 * @author Stephen Tyler
 * 
 */
public class Plane {

    public static final int NEGATIVE_SIDE = 2;

    public static final int NO_SIDE = 0;

    public static final int POSITIVE_SIDE = 1;

    public float d;

    public Vector3 normal;

    /**
     * Default constructor - sets everything to 0.
     * 
     */
    public Plane() {
        normal = new Vector3();
        d = 0.0f;
    }

    public Plane(Plane rhs) {
        this.normal = new Vector3(rhs.normal);
        this.d = rhs.d;
    }

    /**
     * Construct a plane through a normal, and a distance to move the plane
     * along the normal.
     * 
     * @param rkNormal
     * @param fConstant
     */
    public Plane(Vector3 rkNormal, float fConstant) {
        this.normal = new Vector3(rkNormal);
        this.d = -fConstant;
    }

    public Plane(Vector3 rkNormal, Vector3 rkPoint) {
        normal = rkNormal;
        d = -rkNormal.dotProduct(rkPoint);
    }

    public Plane(Vector3 rkPoint0, Vector3 rkPoint1, Vector3 rkPoint2) {
        redefine(rkPoint0, rkPoint1, rkPoint2);
    }

    /**
     * Comparison operator.
     */
    public boolean equals(Object o) {
        if (o instanceof Plane) {
            Plane that = (Plane) o;
            return this.normal.equals(that.normal) && this.d == that.d;
        }

        return false;
    }

    /**
     * This is a pseudodistance.
     * 
     * @param rkPoint
     * @return
     */
    public float getDistance(Vector3 rkPoint) {
        return normal.dotProduct(rkPoint) + d;
    }

    public int getSide(Vector3 rkPoint) {
        float fDistance = getDistance(rkPoint);

        if (fDistance < 0.0)
            return Plane.NEGATIVE_SIDE;

        if (fDistance > 0.0)
            return Plane.POSITIVE_SIDE;

        return Plane.NO_SIDE;
    }

    /**
     * Redefine this plane based on 3 points.
     * 
     */
    private void redefine(Vector3 rkPoint0, Vector3 rkPoint1, Vector3 rkPoint2) {
        Vector3 kEdge1 = rkPoint1.sub(rkPoint0);
        Vector3 kEdge2 = rkPoint2.sub(rkPoint0);
        normal = kEdge1.crossProduct(kEdge2);
        normal.normalise();
        d = -normal.dotProduct(rkPoint0);
    }

}
