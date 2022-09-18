/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization.color;

import infovis.Column;
import infovis.visualization.ColorVisualization;

import java.awt.Color;

import javax.swing.event.ChangeEvent;


/**
 * Color nominal columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NominalColor extends ColorVisualization {
    private Column column;
    private int[]  category;
    int fixedCategories = 0;

    /**
     * Creates a new NominalColor object.
     *
     * @param column the Column,
     * @param numcategories number of categories.
     */
    public NominalColor(Column column, int numcategories) {
        super(column);
        computeCategories(numcategories, 0, 1, 1);
    }
    
    public NominalColor(Column column) {
        this(column, 30);
    }

    /**
     * DOCUMENT ME!
     *
     * @param numcategories DOCUMENT ME!
     * @param startHue DOCUMENT ME!
     * @param saturation DOCUMENT ME!
     * @param value DOCUMENT ME!
     */
    public void computeCategories(int numcategories, float startHue,
                                  float saturation, float value) {
        this.category = new int[numcategories];
        for (int i = 0; i < numcategories; i++) {
            this.category[i] = Color.HSBtoRGB((float)i / numcategories+startHue, saturation, value);
        }
    }

    /**
     * @see infovis.visualization.ColorVisualization#getColorValue(int)
     */
    public int getColorValue(int row) {
        return this.category[row % this.category.length];
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        int numcategories = Math.min(30, this.column.getRowCount());
        
        if (numcategories != this.category.length) {
            computeCategories(numcategories, 0, 1, 1);
        }
    }
}
