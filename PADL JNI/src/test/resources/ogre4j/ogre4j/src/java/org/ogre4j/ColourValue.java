/* This source file is part of ogre4j
 *     (The JNI bindings for OGRE)
 * For the latest info, see http://www.ogre4j.org/
 * 
 * Copyright (c) 2005 netAllied GmbH, Tettnang
 * Also see acknowledgements in Readme.html
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA, or go to
 * http://www.gnu.org/copyleft/lesser.txt.
 *
 *
 * ColourValue.java
 * 
 * Version Information
 * -------------------
 * $Revision: 1.3 $
 * $Date: 2005/08/15 07:34:14 $
 * $Author: earl3982 $
 */
package org.ogre4j;

/**
 * TODO ColourValue type/class description.
 * 
 * @author Kai Klesatschke <kai.klesatschke@ogre4j.org>
 */
public class ColourValue {

    public static ColourValue BLACK = new ColourValue(0, 0, 0, 0);

    public static ColourValue BLUE = new ColourValue(0, 0, 1, 0);

    public static ColourValue GREEN = new ColourValue(0, 1, 0, 0);

    public static ColourValue RED = new ColourValue(1, 0, 0, 0);

    public static ColourValue White = new ColourValue(1, 1, 1, 0);

    public float g, b, a;

    public float r;

    public ColourValue(float r, float g, float b) {
        this(r, g, b, 1.0f);
    }

    /**
     * @param f
     * @param h
     * @param i
     * @param j
     */
    public ColourValue(float r, float g, float b, float a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public ColourValue(float[] cv) {
        if (cv.length != 4) {
            this.r = 0;
            this.g = 0;
            this.b = 0;
            this.a = 0;
            return;
        }

        this.r = cv[0];
        this.g = cv[1];
        this.b = cv[2];
        this.a = cv[3];

    }

    public ColourValue() {
        this(1.0f, 1.0f, 1.0f, 1.0f);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#clone()
     */
    protected ColourValue clone() throws CloneNotSupportedException {
        ColourValue clone = new ColourValue();
        clone.r = this.r;
        clone.g = this.g;
        clone.b = this.b;
        clone.a = this.a;
        return clone;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(ColourValue colour) {
        if (colour.r != this.r)
            return false;
        else if (colour.g != this.g)
            return false;
        else if (colour.b != this.b)
            return false;
        else if (colour.a != this.a)
            return false;
        return true;
    }

}
