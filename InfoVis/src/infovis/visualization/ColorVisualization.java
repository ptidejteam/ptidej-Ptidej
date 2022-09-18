/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization;

import infovis.Column;

import java.awt.Color;

import javax.swing.event.ChangeListener;


/**
 * Interface for Color Visualizations.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class ColorVisualization implements ChangeListener {
    protected Column column;

    public ColorVisualization(Column column) {
        this.column = column;
    }

    /**
     * Returns the managed Column.
     *
     * @return the managed Column.
     */
    public Column getColumn() {
        return this.column;
    }

    //public void setColumn(Column c);

    /**
     * Returns the color value omputed for the specified row.
     *
     * <p>Color are coded as a 32bits integers.  If the
     * color is coded with red, green, blue and alpha
     * floating point values between 0 and 1, the
     * returned value is:
     * <code>
     *         ((((int)(a * 255.999)) & 0xFF) << 24) |
     *         ((((int)(r * 255.999)) & 0xFF) << 16) |
     *         ((((int)(g * 255.999)) & 0xFF) << 8) |
     *         ((((int)(b * 255.999)) & 0xFF) << 0);
     * </code>
     *
     * @return the color computed for the specified row.
     */
    public abstract int getColorValue(int row);
    
    /**
     * Returns a 
     */
    public Color getColor(int row) {
        return new Color(getColorValue(row), true);
    }

    /**
     * Convert r,g,b,a float values into an int.
     *
     * @param r DOCUMENT ME!
     * @param g DOCUMENT ME!
     * @param b DOCUMENT ME!
     * @param a DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static int computeColor(double r, double g, double b, double a) {
        return ((((int)(a * 255.999)) & 0xFF) << 24) |
               ((((int)(r * 255.999)) & 0xFF) << 16) |
               ((((int)(g * 255.999)) & 0xFF) << 8) |
               ((((int)(b * 255.999)) & 0xFF) << 0);
    }
    
    public static Color colorComplement(Color c) {
        return new Color(255-c.getRed(), 255-c.getGreen(), 255-c.getBlue());
    }
}
