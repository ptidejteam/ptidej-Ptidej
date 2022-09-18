/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization.color;

import infovis.Column;
import infovis.column.NumberColumn;
import infovis.visualization.ColorVisualization;

import java.awt.Color;

import javax.swing.event.ChangeEvent;


/**
 * Color BasicVisualization for Ordered values.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class OrderedColor extends ColorVisualization {
    NumberColumn column;
    double       startRed;
    double       startGreen;
    double       startBlue;
    double       endRed;
    double       endGreen;
    double       endBlue;
    double       scale;
    double       origin;

    /**
     * Constructor for OrderedColor.
     */
    public OrderedColor(NumberColumn column, Color start, Color end) {
       super(column);
       setColumn(column);
        setStart(start);
        setEnd(end);
    }

    /**
     * Creates a new OrderedColor object.
     *
     * @param column the managed column.
     */
    public OrderedColor(NumberColumn column) {
        this(column, Color.WHITE, Color.BLACK);
    }

    /**
     * Sets the managed column.
     * @param c the column.
     */
    public void setColumn(Column c) {
        NumberColumn col = (NumberColumn)c;
        if (col == this.column)
            return;

        if (this.column != null)
            this.column.removeChangeListener(this);
        this.column = col;
        if (this.column != null) {
            this.column.addChangeListener(this);
            stateChanged(null);
        }
    }

    /**
     * @see javax.swing.event.ChangeListener#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        this.origin = this.column.getDoubleMin();
        double range = this.column.getDoubleMax() - this.origin;
        this.scale = range == 0 ? 1 : 1.0 / range;
    }

    /**
     * @see infovis.visualization.ColorVisualization#getColumn()
     */
    public Column getColumn() {
        return this.column;
    }

    /**
     * @see infovis.visualization.ColorVisualization#getColor(int)
     */
    public int getColorValue(int row) {
        if (this.column.isValueUndefined(row))
            return 0;
        double t = (this.column.getDoubleAt(row) - this.origin) * this.scale;
        double red = (1 - t) * this.startRed + t * this.endRed;
        double green = (1 - t) * this.startGreen + t * this.endGreen;
        double blue = (1 - t) * this.startBlue + t * this.endBlue;

        return computeColor(red, green, blue, 1);
    }

    /**
     * Returns the start Color.
     * 
     * @return the start Color.
     */
    public Color getStart() {
        return new Color(computeColor(this.startRed, this.startGreen, this.startBlue, 1));
    }

    /**
     * Sets the start color.
     *
     * @param start the start Color.
     */
    public void setStart(Color start) {
        this.startRed = start.getRed() / 255.0;
        this.startGreen = start.getGreen() / 255.0;
        this.startBlue = start.getBlue() / 255.0;
    }
    
    /**
     * Returns the end Color.
     * 
     * @return the end Color.
     */
    public Color getEnd() {
        return new Color(computeColor(this.endRed, this.endGreen, this.endBlue, 1));
    }

    /**
     * Sets the end color.
     *
     * @param end the end Color.
     */
    public void setEnd(Color end) {
        this.endRed = end.getRed() / 255.0;
        this.endGreen = end.getGreen() / 255.0;
        this.endBlue = end.getBlue() / 255.0;
    }

}
