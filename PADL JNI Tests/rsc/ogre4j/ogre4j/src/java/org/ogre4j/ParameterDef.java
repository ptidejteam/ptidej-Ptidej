package org.ogre4j;

public class ParameterDef {
    public static final int PT_BOOL = 0;

    public static final int PT_COLOURVALUE = 13;

    public static final int PT_INT = 2;

    public static final int PT_LONG = 6;

    public static final int PT_MATRIX3 = 10;

    public static final int PT_MATRIX4 = 11;

    public static final int PT_QUATERNION = 12;

    public static final int PT_REAL = 1;

    public static final int PT_SHORT = 4;

    public static final int PT_STRING = 8;

    public static final int PT_UNSIGNED_INT = 3;

    public static final int PT_UNSIGNED_LONG = 7;

    public static final int PT_UNSIGNED_SHORT = 5;

    public static final int PT_VECTOR3 = 9;

    public String name;

    public String description;

    public int paramType;

    public ParameterDef(String name, String description, int paramType) {
        this.name = name;
        this.description = description;
        this.paramType = paramType;
    }

}