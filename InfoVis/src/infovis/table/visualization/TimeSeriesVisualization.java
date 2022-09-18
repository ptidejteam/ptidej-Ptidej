/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.table.visualization;

import infovis.Column;
import infovis.Table;
import infovis.Visualization;
import infovis.column.NumberColumn;
import infovis.utils.RowIterator;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.event.ChangeEvent;


/**
 * BasicVisualization component for Time Series
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TimeSeriesVisualization extends Visualization {
    private transient Point2D.Double dataBounds;
    protected ArrayList              columns = new ArrayList();

    /**
     * Creates a new TimeSeriesVisualization object.
     *
     * @param table the Table to visualize.
     */
    public TimeSeriesVisualization(Table table) {
        super(table);
        //setDefaultColor(new Color(0, 0, 0.6f, 0.5f));
        addAllDataColumns();
    }

    /**
     * Adds a column.
     *
     * @param c The column.
     */
    public void addDataColumn(NumberColumn c) {
        this.columns.add(c);
        changeManagedColumn(null, null, c);
    }

    /**
     * Removes a column.
     *
     * @param c The column.
     */
    public void removeDataColumn(NumberColumn c) {
        if (this.columns.remove(c)) {
            changeManagedColumn(null, c, null);
        }
    }

    /**
     * Adds all data columns.
     */
    public void addAllDataColumns() {
        this.columns.clear();
        for (int col = 0; col < this.table.getColumnCount(); col++) {
            Column c = this.table.getColumnAt(col);

            if ((c == null) || c.isInternal() ||
                    !(c instanceof NumberColumn)) {
                continue;
            }
            NumberColumn n = (NumberColumn)c;
            addDataColumn(n);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param index DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public NumberColumn getNumberColumnAt(int index) {
        return (NumberColumn)this.columns.get(index);
    }

    protected Point2D.Double getDataBounds() {
        if (this.dataBounds == null) {
            double  min = 0;
            double  max = 0;
            boolean first = true;

            for (int i = 0; i < this.columns.size(); i++) {
                NumberColumn col = getNumberColumnAt(i);

                if (first) {
                    min = col.getDoubleMin();
                    max = col.getDoubleMax();
                    first = false;
                } else {
                    min = Math.min(min, col.getDoubleMin());
                    max = Math.max(max, col.getDoubleMax());
                }
            }

            this.dataBounds = new Point2D.Double(min, max);
        }

        return this.dataBounds;
    }

    /**
     * @see infovis.Visualization#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        this.dataBounds = null;
        super.stateChanged(e);
    }

    /**
     * @see infovis.Visualization#paintShape(Graphics2D, int)
     */
    public void paintShape(Graphics2D graphics, int row) {
        Shape s = getShapeAt(row);
        if (s == null)
            return;
        s = transformShape(s);
        if (this.selection != null && this.selection.isSelectedIndex(row)) {
            graphics.setColor(this.selectedColor);
        } else {
            setColorFor(graphics, row);
        }
        this.displayedItems++;
        graphics.draw(s);
        this.displayedItems++;
    }

    /**
     * @see infovis.Visualization#computeShapes(Rectangle2D)
     */
    public void computeShapes(Rectangle2D bounds) {
        Point2D.Double db = getDataBounds();

        double         sx = bounds.getWidth() / (this.columns.size()-1);
        double         sy = -bounds.getHeight() / (db.getY() - db.getX());
        double         ty = -db.getX();

        for (RowIterator iter = iterator(); iter.hasNext(); ) {
            int i = iter.nextRow();
            GeneralPath p = (GeneralPath)getShapeAt(i);
            if (p == null) {
                p = new GeneralPath();
            }
            else {
                p.reset();
            }
            boolean     first = true;
            for (int col = 0; col < this.columns.size(); col++) {
                NumberColumn n = getNumberColumnAt(col);
                float        x = (float)(sx * col + bounds.getX());
                float        y = (float)(bounds.getHeight() + sy * (n.getDoubleAt(i) + ty) +
                                 bounds.getY());

                if (first) {
                    p.moveTo(x, y);
                    first = false;
                } else {
                    p.lineTo(x, y);
                }
            }
            setShapeAt(i, p);
        }
    }
}
