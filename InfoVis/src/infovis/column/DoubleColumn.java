/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column;

import infovis.Column;
import infovis.Table;

import java.text.ParseException;

/**
 * Class DoubleColumn
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DoubleColumn extends NumberColumn {
    private double[] value;
    private double min;
    private double max;

    /**
     * Creates a new DoubleColumn object.
     *
     * @param name the column name.
     */
    public DoubleColumn(String name) {
        this(name, 10);
    }

    /**
     * Creates a new DoubleColumn object.
     *
     * @param name the column name.
     * @param reserve the initial capacity.
     */
    public DoubleColumn(String name, int reserve) {
        super(name, reserve);

        if (reserve != 0) {
            this.value = new double[reserve];
        }

        this.size = 0;
    }

    /**
     * Returns the element at the specified position in this column.
     *
     * @param index index of element to return.
     *
     * @return the element at the specified position in this column.
     */
    public final double get(int index) {
        assert((index >= 0) && (index < getRowCount()));
        return this.value[index];
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void set(int index, double element) {
        assert((index >= 0) && (index < getRowCount()));
        this.value[index] = element;
        setValueUndefined(index, false);
        this.min_max_updated = false;
        modified();
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element, growing the column if necessary.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void setExtend(int index, double element) {
        assert(index >= 0);
        if (index >= getRowCount()) {
            setSize(index + 1);
        }

        this.value[index] = element;
        setValueUndefined(index, false);
        this.min_max_updated = false;
        modified();
    }

    /**
     * Adds a new element in the column,
     *
     * @param element the element.
     */
    public final void add(double element) {
        ensureCapacity(this.size + 1);
        this.value[this.size++] = element;
        this.min_max_updated = false;
        modified();
    }

    /**
     * See AbstractColumn#reallocate(int)
     */
    protected void reallocate(int size) {
        if (size <= this.reserve) {
            return;
        }

        if (this.value == null) {
            this.reserve = size;
            this.value = new double[this.reserve];
        }
        else {
            int new_reserve = size;
            double[] new_value = new double[new_reserve];

            for (int i = 0; i < this.reserve; i++) {
                new_value[i] = this.value[i];
            }

            this.value = new_value;
            this.reserve = new_reserve;
        }
    }

    /**
     * Parse a string and return the value for the column.
     *
     * @param v the string representation of the value
     *
     * @return the value
     *
     * @throws ParseException if the value cannot be parsed
     */
    public double parse(String v) throws ParseException {
        if (v == null)
            return 0;
        try {
            if (getFormat() != null) {
                return ((Number) getFormat().parseObject(v))
                    .floatValue();
            }

            return Double.parseDouble(v);
        }
        catch (Exception e) {
            throw new ParseException(e.getMessage(), 0);
        }
    }

    /**
     * Returns the string representation of a value according to the current format.
     *
     * @param v the value
     *
     * @return the string representation.
     */
    public String format(double v) {
        if (getFormat() != null) {
            return getFormat().format(Double.valueOf(v));
        }

        return Double.toString(v);
    }

    /**
     * @see infovis.Column#getValueAt(int)
     */
    public String getValueAt(int i) {
        if (i < 0 || i >= getRowCount() || isValueUndefined(i))
            return null;
        return format(get(i));
    }

    /**
     * @see infovis.Column#setValueAt(int, String)
     */
    public void setValueAt(int i, String v) throws ParseException {
        setExtend(i, parse(v));
    }

    /**
     * @see infovis.Column#addValue(String)
     */
    public void addValue(String v) throws ParseException {
        add(parse(v));
    }

    /**
     * See AbstractColumn#updateMinMax()
     */
    protected void updateMinMax() {
        if (this.min_max_updated) {
            return;
        }

        this.max = Double.MIN_VALUE;
        this.min = Double.MAX_VALUE;

        for (int i = 0; i < this.size; i++) {
            if (!isValueUndefined(i)) {
                double v = this.value[i];

                if (v < this.min) {
                    this.min = v;
                }

                if (v > this.max) {
                    this.max = v;
                }
            }
        }
        this.min_max_updated = true;
    }

    /**
     * @see infovis.Column#minValue()
     */
    public String minValue() {
        return format(getMin());
    }

    /**
     * @see infovis.Column#maxValue()
     */
    public String maxValue() {
        return format(getMax());
    }

    /**
     * Returns a column as a <code>DoubleColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>DoubleColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>DoubleColumn</code>.
     */
    public static DoubleColumn getColumn(Table t, int index) {
        Column c = t.getColumnAt(index);

        if (c instanceof DoubleColumn) {
            return (DoubleColumn) c;
        }
        else {
            return null;
        }
    }

    /**
     * Returns a column as a <code>DoubleColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>DoubleColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>DoubleColumn</code>.
     */
    public static DoubleColumn getColumn(Table t, String name) {
        Column c = t.getColumn(name);

        if (c instanceof DoubleColumn) {
            return (DoubleColumn) c;
        }
        else {
            return null;
        }
    }

    /**
     * Returns a column as a <code>DoubleColumn</code> from a table,
     * creating it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>DoubleColumn</code> from a table,
     */
    public static DoubleColumn findColumn(Table t, String name) {
        Column c = t.getColumn(name);
        if (c == null) {
            c = new DoubleColumn(name);
            t.addColumn(c);
        }
        return (DoubleColumn) c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return double the maximum value of the column.
     */
    public double getMax() {
        updateMinMax();

        return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return double the minimum value of the column.
     */
    public double getMin() {
        updateMinMax();

        return this.min;
    }

    /**
     * @see infovis.Column#getValueClass()
     */
    public Class getValueClass() {
        return Double.class;
    }

    /**
     * @see infovis.column.NumberColumn#getDoubleAt(int)
     */
    public double getDoubleAt(int row) {
        return get(row);
    }

    /**
     * @see infovis.column.NumberColumn#getIntAt(int)
     */
    public int getIntAt(int row) {
        return (int) get(row);
    }

    /**
     * @see infovis.column.NumberColumn#getLongAt(int)
     */
    public long getLongAt(int row) {
        return (long) get(row);
    }

    /**
     * @see infovis.column.NumberColumn#setDoubleAt(int, double)
     */
    public void setDoubleAt(int row, double v) {
        setExtend(row, v);
    }

    public int getIntMin() {
        return (int) getMin();
    }
    
    public int getIntMax() {
        return (int) getMax();
    }

    public float getFloatMin() {
        return (float) getMin();
    }
    
    public float getFloatMax() {
        return (float) getMax();
    }

    public long getLongMin() {
        return (long) getMin();
    }

    public long getLongMax() {
        return (long) getMax();
    }

    public double getDoubleMin() {
        return getMin();
    }

    public double getDoubleMax() {
        return getMax();
    }

    public double round(double value) {
        return value;
    }
}
