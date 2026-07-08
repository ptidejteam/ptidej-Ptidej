package org.ogre4j;

public final class Common {
    /** No fog. */
    public static final int FOG_NONE = 0;

    /**
     * Fog density increases exponentially from the camera (fog = 1/e^(distance *
     * density)).
     */
    public static final int FOG_EXP = 1;

    /**
     * Fog density increases at the square of FOG_EXP, i.e. even quicker (fog =
     * 1/e^(distance * density)^2).
     */
    public static final int FOG_EXP2 = 2;

    /** Fog density increases linearly between the start and end distances. */
    public static final int FOG_LINEAR = 3;

    /** Only points are rendered. */
    public static final int SDL_POINTS = 0;

    /** Wireframe models are rendered. */
    public static final int SDL_WIREFRAME = 1;

    /** Solid polygons are rendered. */
    public static final int SDL_SOLID = 2;

    /** Equal to: min=FO_POINT, mag=FO_POINT, mip=FO_NONE. */
    public static final int TFO_NONE = 0;

    /** Equal to: min=FO_LINEAR, mag=FO_LINEAR, mip=FO_POINT. */
    public static final int TFO_BILINEAR = 1;

    /** Equal to: min=FO_LINEAR, mag=FO_LINEAR, mip=FO_LINEAR. */
    public static final int TFO_TRILINEAR = 2;

    /** Equal to: min=FO_ANISOTROPIC, max=FO_ANISOTROPIC, mip=FO_LINEAR. */
    public static final int TFO_ANISOTROPIC = 3;

}
