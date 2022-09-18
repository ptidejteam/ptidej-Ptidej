/**
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France
 * -------------------------------------------------------------------------
 * This software is published under the terms of the QPL Software License a
 * copy of which has been included with this distribution in the
 * license-infovis.txt file.
 */
package infovis.table.visualization;

import infovis.Column;
import infovis.Table;
import infovis.Visualization;
import infovis.column.IdColumn;
import infovis.column.NumberColumn;
import infovis.column.filter.NotNumberFilter;
import infovis.panel.DoubleBoundedRangeModel;
import infovis.panel.NumberColumnBoundedRangeModel;
import infovis.utils.RowIterator;
import infovis.visualization.DefaultVisualColumn;

import java.awt.geom.Rectangle2D;

import javax.swing.event.ChangeEvent;

/**
 * Scatter plot visualization.
 * 
 * <p>
 * Visualize a table with a scatter plot representation.
 * </p>
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ScatterPlotVisualization extends Visualization {
    public static final String VISUAL_X_AXIS = "xAxis";
    public static final String VISUAL_Y_AXIS = "yAxis";
    protected NumberColumnBoundedRangeModel xAxisModel;
    protected NumberColumnBoundedRangeModel yAxisModel;
    protected NumberColumn xAxisColumn;
    protected NumberColumn yAxisColumn;
    protected int margin = 10;
    protected IdColumn idColumn;

    static {
        setControlPanelCreator(
            ScatterPlotVisualization.class,
            ScatterPlotControlPanel.class);
    }

    /**
     * Creates a new ScatterPlotVisualization object.
     *
     * @param table the table.
     * @param xAxisColumn the <code>NumberColumn</code> for the X Axis
     * @param yAxisColumn the <code>NumberColumn</code> for the Y Axis
     * @param size the <code>NumberColumn</code> for managing the size.
     * @param color the <code>Column</code> for managing the color.
     * @param shape the <code>Column</code> for managing the shape.
     */
    public ScatterPlotVisualization(
        Table table,
        NumberColumn xAxis,
        NumberColumn yAxis) {
        super(table);
        this.idColumn = new IdColumn("id");
        setXAxisColumn(xAxis);
        setYAxisColumn(yAxis);
    }

    /**
     * Creates a new ScatterPlotVisualization object.
     *
     * @param table the table.
     * @param xAxisColumn the name of the <code>NumberColumn</code> for the X Axis
     * @param yAxisColumn the name of the <code>NumberColumn</code> for the Y Axis
     * @param size the name of the <code>NumberColumn</code> for managing the
     *        fsize.
     * @param color the name of the <code>Column</code> for managing the
     *        color.
     * @param shape the name of the <code>Column</code> for managing the
     *        shape.
     */
    public ScatterPlotVisualization(
        Table table,
        String xAxis,
        String yAxis) {
        this(
            table,
            (NumberColumn) table.getColumn(xAxis),
            (NumberColumn) table.getColumn(yAxis));
    }

    /**
     * Creates a new ScatterPlotVisualization object. Required columns are
     * searched in the table, taking the first two
     * <code>NumberColumn</code>s as axes.
     *
     * @param table the table.
     */
    public ScatterPlotVisualization(Table table) {
        this(
            table,
            getNumberColumn(table, 0),
            getNumberColumn(table, 1));
    }

    protected void declareVisualColumns() {
        super.declareVisualColumns();
        putVisualColumn(
            VISUAL_X_AXIS,
            new DefaultVisualColumn(true, NotNumberFilter.sharedInstance()) {
            public void setColumn(Column column) {
                super.setColumn(column);
                ScatterPlotVisualization.this.xAxisColumn = (NumberColumn) column;
                setXAxisModel(null);
            }
        });
        putVisualColumn(
            VISUAL_Y_AXIS,
            new DefaultVisualColumn(true, NotNumberFilter.sharedInstance()) {
            public void setColumn(Column column) {
                super.setColumn(column);
                ScatterPlotVisualization.this.yAxisColumn = (NumberColumn) column;
                setYAxisModel(null);
            }
        });
    }

    /**
     * Returns the nth <code>NumberColumn</code> skipping internal columns.
     *
     * @param t the Table.
     * @param index the index of the column, skipping over internal column,
     *
     * @return the nth <code>NumberColumn</code> skipping internal columns.
     */
    public static NumberColumn getNumberColumn(Table t, int index) {
        NumberColumn ret = null;
        for (int i = 0; i < t.getColumnCount(); i++) {
            ret = NumberColumn.getNumberColumn(t, i);
            if (ret != null && !ret.isInternal() && index-- == 0)
                return ret;
        }
        return null;
    }

    public void computeShapes(Rectangle2D bounds) {
        double width = bounds.getWidth();
        double height = bounds.getHeight();
        double off = this.maxSize / 2 + this.margin;
        double margin2 = 2 * this.margin;
        NumberColumn x = (this.xAxisColumn == null) ? this.idColumn : this.xAxisColumn;
        NumberColumn y = (this.yAxisColumn == null) ? this.idColumn : this.yAxisColumn;
        if (width <= (this.maxSize + margin2)
            || height <= (this.maxSize + margin2))
            return;
        double xmin =
            this.xAxisModel == null
                ? x.getDoubleMin()
                : this.xAxisModel.getValue();
        double xmax =
            this.xAxisModel == null
                ? x.getDoubleMax()
                : xmin + this.xAxisModel.getExtent();
        double xscale =
            (xmax <= xmin)
                ? 1
                : (width - this.maxSize - margin2) / (xmax - xmin);
        double ymin =
            this.yAxisModel == null
                ? y.getDoubleMin()
                : this.yAxisModel.getValue();
        double ymax =
            this.yAxisModel == null
                ? y.getDoubleMax()
                : ymin + this.yAxisModel.getExtent();
        double yscale =
            (ymax <= ymin)
                ? 1
                : (height - this.maxSize - margin2) / (ymax - ymin);

        for (RowIterator iter = iterator(); iter.hasNext();) {
            int row = iter.nextRow();
            if (x.isValueUndefined(row) || y.isValueUndefined(row)) {
                setShapeAt(row, null);
                continue;
            }

            double xpos = (x.getDoubleAt(row) - xmin) * xscale + off;
            double ypos =
                height - ((y.getDoubleAt(row) - ymin) * yscale + off);
            double s = getSizeAt(row);
            double s2 = s / 2;
            Rectangle2D.Double rect =
                (Rectangle2D.Double) getShapeAt(row);
            if (rect == null) {
                rect =
                    new Rectangle2D.Double(xpos - s2, ypos - s2, s, s);
                setShapeAt(row, rect);
            }
            else {
                rect.setRect(xpos - s2, ypos - s2, s, s);
            }
        }
    }

    /**
     * Returns the xAxisColumn.
     *
     * @return Column
     */
    public Column getXAxisColumn() {
        return this.xAxisColumn;
    }

    /**
     * Returns the yAxisColumn.
     *
     * @return Column
     */
    public Column getYAxisColumn() {
        return this.yAxisColumn;
    }

    /*
     * Sets the xAxisColumn.
     * @param xAxisColumn The xAxisColumn to set.
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setXAxisColumn(NumberColumn xAxis) {
        return changeManagedColumn(
            VISUAL_X_AXIS,
            this.xAxisColumn,
            xAxis);
    }

    /**
     * Sets the yAxisColumn.
     *
     * @param yAxisColumn The yAxisColumn to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setYAxisColumn(NumberColumn yAxis) {
        return changeManagedColumn(
            VISUAL_Y_AXIS,
            this.yAxisColumn,
            yAxis);
    }

    /**
     * Returns the margin.
     *
     * @return int
     */
    public int getMargin() {
        return this.margin;
    }

    /**
     * Sets the margin.
     *
     * @param margin The margin to set
     */
    public void setMargin(int margin) {
        this.margin = margin;
        invalidate();
    }

    /**
     * Returns the xAxisModel.
     *
     * @return DoubleBoundedRangeModel
     */
    public DoubleBoundedRangeModel getXAxisModel() {
        return this.xAxisModel;
    }

    /**
     * Returns the yAxisModel.
     *
     * @return DoubleBoundedRangeModel
     */
    public DoubleBoundedRangeModel getYAxisModel() {
        return this.yAxisModel;
    }

    /**
     * Sets the xAxisModel.
     *
     * @param xAxisModel The xAxisModel to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setXAxisModel(NumberColumnBoundedRangeModel xAxisModel) {
        if (xAxisModel != null
            && xAxisModel.getColumn() != this.xAxisColumn)
            return false;
        if (this.xAxisModel != xAxisModel) {
            if (this.xAxisModel != null) {
                this.xAxisModel.removeChangeListener(this);
            }
            this.xAxisModel = xAxisModel;
            if (this.xAxisModel != null) {
                this.xAxisModel.addChangeListener(this);
            }
            return true;
        }
        return false;
    }

    /**
     * Sets the yAxisModel.
     *
     * @param yAxisModel The yAxisModel to set
     *
     * @return <code>true</code> if the column has been set.
     */
    public boolean setYAxisModel(NumberColumnBoundedRangeModel yAxisModel) {
        if (yAxisModel != null
            && yAxisModel.getColumn() != this.yAxisColumn)
            return false;
        if (this.yAxisModel != yAxisModel) {
            if (this.yAxisModel != null) {
                this.yAxisModel.removeChangeListener(this);
            }
            this.yAxisModel = yAxisModel;
            if (this.yAxisModel != null) {
                this.yAxisModel.addChangeListener(this);
            }
            return true;
        }
        return false;
    }

    /**
     * @see infovis.Visualization#stateChanged(ChangeEvent)
     */
    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == this.xAxisModel
            || e.getSource() == this.yAxisModel) {
            invalidate();
        }
        else {
            super.stateChanged(e);
        }
    }
}
