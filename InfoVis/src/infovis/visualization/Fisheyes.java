/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License
 * a copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.visualization;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Fisheyes manage space deformation to maintain focus+context views by
 * applying a space deformation.  See Sheelagh Carpendale's PhD for full
 * details.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class Fisheyes {
    /** constant value for setDistanceMetric to use a L1 distance */
    public static final short DISTANCE_L1 = 0;
    /** constant value for setDistanceMetric to use a L2 distance */
    public static final short DISTANCE_L2 = 1;
    /** constant value for setDistanceMetric to use a L infinity distance */
    public static final short DISTANCE_LINF = 2;
    /** constant value for setLensType to use a gaussian lens types */
    public static final short LENS_GAUSSIAN = 0;
    /** constant value for setLensType to use a cosine lens types */
    public static final short LENS_COSINE = 1;
    /** constant value for setLensType to use a hemisphere lens types */
    public static final short LENS_HEMISPHERE = 2;
    /** constant value for setLensType to use a linear lens types */
    public static final short LENS_LINEAR = 3;
    /** constant value for setLensType to use an inverse cosine lens types */
    public static final short LENS_INVERSE_COSINE = 4;
    /** The virtual camera height is 10.0f */
    public static final float referenceHeight = 10.0f;
    /** The virtual viewplane is located at this distance from the camera */
    public static final float distanceViewplance = 1.0f;
    transient Rectangle2D.Float bounds = new Rectangle2D.Float();
    transient Line2D.Double tmpLine = new Line2D.Double();
    float focusX;
    float focusY;
    float lensRadius;
    float focusRadius;
    float focalHeight;
    float tolerance = 1;
    short distanceMetric;
    Metric metric;
    short lensType;
    LensProfile lensProfile;

    /**
     * Constructor for Fisheyes.
     */
    public Fisheyes() {
        this(100, 0, 9);
    }

    /**
     * Creates a new Fisheye object.
     *
     * @param lensRadius the lens radius.
     * @param focusRadius DOCUMENT ME!
     * @param focalHeight the focal heigt (0 &lt;= 9)
     */
    public Fisheyes(
        float lensRadius,
        float focusRadius,
        float focalHeight) {
        setLensRadius(lensRadius);
        setFocusRadius(focusRadius);
        setFocalHeight(focalHeight);
        setDistanceMetric(DISTANCE_L2);
        setLensType(LENS_LINEAR);
    }

    public Rectangle2D getBounds() {
        if (this.bounds == null) {
            this.bounds =
                new Rectangle2D.Float(
                    this.focusX - this.focusRadius,
                    this.focusY - this.focusRadius,
                    2 * this.focusRadius,
                    2 * this.focusRadius);
        }
        return this.bounds;
    }

    /**
     * Returns true of point is transformed.
     *
     * @param x X coordinate
     * @param y Y coordinate
     *
     * @return true of point is transformed.
     */
    public boolean isTransformed(float x, float y) {
        return this.metric.compare(this.lensRadius, x - this.focusX, y - this.focusY) > 0;
    }

    /**
     * Returns true of point is transformed.
     *
     * @param x X coordinate
     * @param y Y coordinate
     *
     * @return true of point is transformed.
     */
    public boolean isTransformed(double x, double y) {
        return isTransformed((float) x, (float) y);
    }

    /**
     * Returns true of point is transformed.
     *
     * @param p the point
     *
     * @return true of point is transformed.
     */
    public boolean isTransformed(Point2D p) {
        return isTransformed(p.getX(), p.getY());
    }

    /**
     * DOCUMENT ME!
     *
     * @param bounds DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isTransformed(Rectangle2D bounds) {
        return bounds.intersects(getBounds());
    }

    /**
     * DOCUMENT ME!
     *
     * @param s DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public boolean isTransformed(Shape s) {
        return getBounds().intersects(s.getBounds2D());
    }

    /**
     * DOCUMENT ME!
     *
     * @param s DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Shape transform(Shape s) {
        if (!isTransformed(s))
            return s;

        GeneralPath p = new GeneralPath();
        float[] coords = { 0, 0, 0, 0, 0, 0 };
        float first_x = 0;
        float first_y = 0;
        float prev_x = 0;
        float prev_y = 0;
        for (PathIterator iter = s.getPathIterator(null, this.tolerance/getMaximumScale());
            !iter.isDone();
            iter.next()) {
            switch (iter.currentSegment(coords)) {
                case PathIterator.SEG_MOVETO :
                    prev_x = coords[0];
                    prev_y = coords[1];
                    first_x = prev_x;
                    first_y = prev_y;
                    transform(coords, 1);
                    p.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO :
                    {
                        float x = coords[0];
                        float y = coords[1];
                        subdivide(prev_x, prev_y, x, y, p);
                        prev_x = x;
                        prev_y = y;
                        break;
                    }
                case PathIterator.SEG_CLOSE :
                    {
                        subdivide(prev_x, prev_y, first_x, first_y, p);
                        p.closePath();
                        break;
                    }
            }
        }
        return p;
    }

    void addTransformed(float x, float y, GeneralPath p) {
        float scale = getScale(x, y);
        float tx;
        float ty;
        if (scale == 1) {
            return;
        }
        tx = transformX(x, scale);
        ty = transformY(y, scale);
        p.lineTo(tx, ty);
    }

    static float dist2(float dx, float dy) {
        return dx * dx + dy * dy;
    }

    public Line2D.Double clip(
        double x1,
        double y1,
        double x2,
        double y2) {
        int out1 = this.bounds.outcode(x1, y1);
        int out2 = this.bounds.outcode(x2, y2);
        if (out1 != 0 && out2 != 0 && (out1 & out2) != 0)
            return null;

        Line2D.Double l = new Line2D.Double();
        l.x1 = x1;
        l.y1 = y1;

        int cnt = 0;
        while (out1 != 0) {
            if (cnt++ > 2)
                throw new RuntimeException("too many loops in clip1");
            if ((out1 & out2) != 0) {
                return null;                
            }
            if ((out1 & (Rectangle2D.OUT_LEFT | Rectangle2D.OUT_RIGHT))
                != 0) {
                double x = this.bounds.x;
                if ((out1 & Rectangle2D.OUT_RIGHT) != 0) {
                    x += this.bounds.width;
                }
                y1 = y1 + (x - x1) * (y2 - y1) / (x2 - x1);
                x1 = x;
            }
            else {
                double y = this.bounds.y;
                if ((out1 & Rectangle2D.OUT_BOTTOM) != 0) {
                    y += this.bounds.height;
                }
                x1 = x1 + (y - y1) * (x2 - x1) / (y2 - y1);
                y1 = y;
            }
            out1 = this.bounds.outcode(x1, y1);
        }
        
        cnt = 0;
        while (out2 != 0) {
            if (cnt++ > 2)
                throw new RuntimeException("too many loops in clip2");
            if ((out2 & (Rectangle2D.OUT_LEFT | Rectangle2D.OUT_RIGHT))
                != 0) {
                double x = this.bounds.x;
                if ((out2 & Rectangle2D.OUT_RIGHT) != 0) {
                    x += this.bounds.width;
                }
                y2 = y2 + (x - x2) * (y2 - l.y1) / (x2 - l.x1);
                x2 = x;
            }
            else {
                double y = this.bounds.y;
                if ((out2 & Rectangle2D.OUT_BOTTOM) != 0) {
                    y += this.bounds.height;
                }
                x2 = x2 + (y - y2) * (x2 - l.x1) / (y2 - l.y1);
                y2 = y;
            }
            out2 = this.bounds.outcode(x2, y2);
        }
        l.x1 = x1; // reload because it could have been modified the first loop
        l.y1 = y1; 
        l.x2 = x2;
        l.y2 = y2;

        return l;
    }

    /**
     * Subdivide a line segment already clipped.
     *
     * @param x1 X coordinate of first point
     * @param y1 Y coordinate of first point
     * @param x2 X coordinate of second point
     * @param y2 Y coordinate of second point
     * @param p GeneralPath to fill
     */
    public void subdivideSegment(
        float x1, float y1,
        float tx1, float ty1,
        float x2, float y2,
        float tx2, float ty2,
        GeneralPath p,
        int depth) {
        if (depth > 20)
            throw new RuntimeException("too deep in subdivideSegment");
        if (x1 == x2 && y1 == y2) {
            p.lineTo(tx2, ty2);
            return;
        }
        if (this.lensRadius <= this.focusRadius) {
            p.lineTo(tx1, ty1);
            p.lineTo(tx2, ty2);
            p.lineTo(x2, y2);
            return;
        }
        
        float xm = (x1 + x2) / 2;
        float ym = (y1 + y2) / 2;
        float scale = getScale(xm, ym);
        float txm = transformX(xm, scale);
        float tym = transformY(ym, scale);

        // subdivide in small pieces to avoid losing the compression portion
        float maxLen = (this.lensRadius - this.focusRadius) / 3;
        if (
            (dist2(x2 - x1, y2 - y1) > maxLen*maxLen) ||
           (dist2(txm - (tx1 + tx2) / 2, tym - (ty1 + ty2) / 2)
            > this.tolerance)) {
            subdivideSegment(x1, y1, tx1, ty1, xm, ym, txm, tym, p, depth+1);
            subdivideSegment(xm, ym, txm, tym, x2, y2, tx2, ty2, p, depth+1);
        }
        else {
            p.lineTo(tx2, ty2);
        }
    }
    /**
     * Subdivide a line segment.
     *
     * @param x1 X coordinate of first point
     * @param y1 Y coordinate of first point
     * @param x2 X coordinate of second point
     * @param y2 Y coordinate of second point
     * @param p GeneralPath to fill
     */
    public void subdivide(
        float x1,
        float y1,
        float x2,
        float y2,
        GeneralPath p) {

        Line2D.Double l = clip(x1, y1, x2, y2);
        if (l == null) {
//            System.out.println(
//                "clip"+bounds+" with " +
//                x1 + ", " +
//                y1 + ", " +
//                x2 + ", " +
//                y2 + 
//                " == null");
            p.lineTo(x2, y2);
            return;
        }

        if (l.x1 != x1 || l.y1 != y1) {
            x1 = (float) l.x1;
            y1 = (float) l.y1;
            p.lineTo(x1, y1);
        }
        float scale = getScale(x1, y1);
        float tx1 = transformX(x1, scale);
        float ty1 = transformY(y1, scale);
        scale = getScale((float)l.x2, (float)l.y2);
        float tx2 = transformX((float)l.x2, scale);
        float ty2 = transformY((float)l.y2, scale);
        
        subdivideSegment(x1, y1, tx1, ty1, (float)l.x2, (float)l.y2, tx2, ty2, p, 0);
        if (l.x2 != x2 || l.y2 != y2) {
            p.lineTo(x2, y2);
        }
    }

    /**
     * Returns the height of a specified point.
     *
     * @param x X coordinate of the point
     * @param y Y coordinate of the point
     *
     * @return the height of the specified point.
     */
    public float pointHeight(float x, float y) {
        return height(distance(x, y));
    }

    /**
     * Sets for focus position
     *
     * @param x X coordinate of the position
     * @param y X coordinate of the position
     */
    public void setFocus(float x, float y) {
        this.focusX = x;
        this.focusY = y;
        this.bounds.x = x - this.lensRadius;
        this.bounds.y = y - this.lensRadius;
    }

    /**
     * Returns the distance of the specified point from the focus.
     *
     * @param x X coordinate of the point
     * @param y Y coordinate of the point
     *
     * @return the distance of the specified point from the focus.
     */
    public float distance(float x, float y) {
        return this.metric.distance(x - this.focusX, y - this.focusY);
    }

    /**
     * Returns the height at the specified distance from the focus
     *
     * @param dist the distance
     *
     * @return the height at the specified distance from the focus
     */
    public float height(float dist) {
        if (this.focalHeight == 0) {
            return 0;
        }

        float realFocus = this.focusRadius / getMaximumScale();
        if (dist > this.lensRadius) {
            return 0;
        }
        else if (dist <= realFocus) {
            return this.focalHeight;
        }
        else {
            float t = (dist - realFocus) / (this.lensRadius - realFocus);
            return Math.min(this.focalHeight * lens(t), this.focalHeight);
        }
    }

    /**
     * Returns the height at the specified normalized distance from the focus
     *
     * @param t the normalized distance from the focus
     *
     * @return the height at the specified normalized distance from the focus
     */
    public float lens(float t) {
        return this.lensProfile.profile(t);
    }

    /**
     * Returns the focusX.
     *
     * @return float
     */
    public float getFocusX() {
        return this.focusX;
    }

    /**
     * Returns the focusY.
     *
     * @return float
     */
    public float getFocusY() {
        return this.focusY;
    }

    /**
     * Sets the focusX.
     *
     * @param focusX The focusX to set
     */
    public void setFocusX(float focusX) {
        this.focusX = focusX;
        this.bounds.x = focusX - this.lensRadius;
    }

    /**
     * Returns the lensRadius.
     *
     * @return float
     */
    public float getLensRadius() {
        return this.lensRadius;
    }

    /**
     * Sets the focusY.
     *
     * @param focusY The focusY to set
     */
    public void setFocusY(float focusY) {
        this.focusY = focusY;
        this.bounds.y = focusY - this.lensRadius;
    }

    /**
     * Sets the lensRadius.
     *
     * @param radius The lensRadius to set
     */
    public void setLensRadius(float radius) {
        this.lensRadius = radius;
        if (this.focusRadius > radius)
            this.focusRadius = radius;
        this.bounds.width = 2 * radius;
        this.bounds.height = 2 * radius;
        this.bounds.x = this.focusX - this.lensRadius;
        this.bounds.y = this.focusY - this.lensRadius;
    }

    /**
     * Returns the focusRadius.
     *
     * @return float
     */
    public float getFocusRadius() {
        return this.focusRadius;
    }

    /**
     * Sets the focusRadius.
     *
     * @param focusRadius The focusRadius to set
     */
    public void setFocusRadius(float focusRadius) {
        this.focusRadius = focusRadius;
    }

    /**
     * DOCUMENT ME!
     *
     * @param focus DOCUMENT ME!
     * @param lens DOCUMENT ME!
     */
    public void setRadii(float focus, float lens) {
        if (lens < focus) {
            focus = lens;
        }
        this.focusRadius = focus;
        this.lensRadius = lens;
        this.bounds.width = 2 * this.lensRadius;
        this.bounds.height = 2 * this.lensRadius;
        this.bounds.x = this.focusX - this.lensRadius;
        this.bounds.y = this.focusY - this.lensRadius;
    }

    /**
     * Returns the focal height.
     *
     * @return float
     */
    public float getFocalHeight() {
        return this.focalHeight;
    }

    /**
     * Sets the focal height.
     *
     * @param focalHeight The focal height to set
     */
    public void setFocalHeight(float focalHeight) {
        if (focalHeight < 0) {
            focalHeight = 0;
        }
        else if (focalHeight > 9) {
            focalHeight = 9;
        }

        this.focalHeight = focalHeight;
    }

    /**
     * Change the maximum scale
     *
     * @param scale the new maximum scale
     */
    public void setMaximumScale(float scale) {
        if (scale == 0) {
            setFocalHeight(0);
        }
        else {
            setFocalHeight(10 - (10 / scale));
        }
    }

    /**
     * Returns the maximum scale
     *
     * @return the maximum scale
     */
    public float getMaximumScale() {
        return 10f / (10f - this.focalHeight);
    }

    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param y DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float getScale(float x, float y) {
        float height = pointHeight(x, y);
        return 10f / (10f - height);
    }

    /**
     * DOCUMENT ME!
     *
     * @param x DOCUMENT ME!
     * @param scale DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float transformX(float x, float scale) {
        return (x - this.focusX) * scale + this.focusX;
    }

    /**
     * DOCUMENT ME!
     *
     * @param y DOCUMENT ME!
     * @param scale DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public float transformY(float y, float scale) {
        return (y - this.focusY) * scale + this.focusY;
    }

    /**
     * DOCUMENT ME!
     *
     * @param coords DOCUMENT ME!
     */
    public void transform(float[] coords, int npoints) {
        for (int i = 0; i < npoints; i++) {
            float scale = getScale(coords[2 * i], coords[2 * i + 1]);
            if (scale != 1) {
                coords[2 * i] = transformX(coords[2 * i], scale);
                coords[2 * i + 1] =
                    transformY(coords[2 * i + 1], scale);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param src DOCUMENT ME!
     * @param dst DOCUMENT ME!
     */
    public void transform(Point2D.Float src, Point2D.Float dst) {
        float scale = getScale(src.x, src.y);
        if (scale != 1) {
            dst.x = transformX(src.x, scale);
            dst.y = transformY(src.y, scale);
        }
        else if (dst != src) {
            dst.x = src.x;
            dst.y = src.y;
        }
    }

    /**
     * Returns the distanceMetric.
     *
     * @return short the distanceMetric
     */
    public short getDistanceMetric() {
        return this.distanceMetric;
    }

    /**
     * Returns the lensType.
     *
     * @return short
     */
    public short getLensType() {
        return this.lensType;
    }

    /**
     * Sets the distanceMetric.
     *
     * @param distanceMetrics The distanceMetric to set
     */
    public void setDistanceMetric(short distanceMetrics) {
        this.distanceMetric = distanceMetrics;

        switch (distanceMetrics) {
            case DISTANCE_L1 :
                this.metric = new DistanceL1();
                break;
            case DISTANCE_L2 :
                this.metric = new DistanceL2();
                break;
            case DISTANCE_LINF :
                this.metric = new DistanceLInf();
                break;
        }
    }

    /**
     * Sets the lensType.
     *
     * @param lensType The lensType to set
     */
    public void setLensType(short lensType) {
        this.lensType = lensType;

        switch (lensType) {
            case LENS_GAUSSIAN :
                this.lensProfile = new ProfileGuassian();
                break;
            case LENS_COSINE :
                this.lensProfile = new ProfileCos();
                break;
            case LENS_HEMISPHERE :
                this.lensProfile = new ProfileCos();
                break;
            case LENS_INVERSE_COSINE :
                this.lensProfile = new ProfileInverse(new ProfileCos());
                break;
            case LENS_LINEAR :
                this.lensProfile = new ProfileLinear();
                break;
        }
    }

    public interface Metric {
        public float distance(float dx, float dy);
        public int compare(float dist, float dx, float dy);
    }

    public interface LensProfile {
        public float profile(float t);
    }

    public static class ProfileCos implements LensProfile {
        public float profile(float t) {
            return (float) Math.cos(t * Math.PI / 2);
        }
    }

    static class ProfileGuassian implements LensProfile {
        static final double ro = 0.1;
        static final double denom = 1 / (ro * Math.sqrt(2 * Math.PI));

        public float profile(float t) {
            return (float) Math.exp((-t * t) / ro);
        }
    }

    static class ProfileOneMinusSin implements LensProfile {
        public float profile(float t) {
            return 1 - (float) Math.sin(t);
        }
    }

    static class ProfileLinear implements LensProfile {
        public float profile(float t) {
            return 1 - t;
        }
    }

    static class ProfileInverse implements LensProfile {
        LensProfile profile;

        public ProfileInverse(LensProfile profile) {
            this.profile = profile;
        }

        public float profile(float t) {
            return 1 - this.profile.profile(1 - t);
        }
    }

    static class DistanceL1 implements Metric {
        public float distance(float dx, float dy) {
            return Math.abs(dx) + Math.abs(dy);
        }
        public int compare(float dist, float dx, float dy) {
            float d = dist - distance(dx, dy);
            if (d < 0)
                return -1;
            else if (d == 0)
                return 0;
            else
                return 1;
        }

    }

    static class DistanceL2 implements Metric {
        public float distance(float dx, float dy) {

            return (float) Math.sqrt((dx * dx) + (dy * dy));
        }
        public int compare(float dist, float dx, float dy) {
            float d = dist * dist - (dx * dx) + (dy * dy);
            if (d < 0)
                return -1;
            else if (d == 0)
                return 0;
            else
                return 1;
        }
    }

    static class DistanceLInf implements Metric {
        public float distance(float dx, float dy) {
            return Math.max(Math.abs(dx), Math.abs(dy));
        }

        public int compare(float dist, float dx, float dy) {
            float d = dist - distance(dx, dy);
            if (d < 0)
                return -1;
            else if (d == 0)
                return 0;
            else
                return 1;
        }
    }

    /**
     * Returns the tolerance.
     *
     * @return float
     */
    public float getTolerance() {
        return this.tolerance;
    }

    /**
     * Sets the tolerance.
     *
     * @param tolerance The tolerance to set
     */
    public void setTolerance(float tolerance) {
        if (tolerance < 1)
            tolerance = 1;
        this.tolerance = tolerance;
    }
}
