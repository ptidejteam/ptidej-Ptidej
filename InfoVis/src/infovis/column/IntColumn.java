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
import infovis.utils.RowComparator;
import infovis.utils.Sort;

import java.text.ParseException;


/**
 * A Column of integer values.
 *
 * @version $Revision: 1.1 $
 * @author fekete
 */
public class IntColumn extends NumberColumn {
    private int[] value;
    private int   min;
    private int   max;

    /**
     * Creates a new IntColumn object.
     *
     * @param name the column name.
     */
    public IntColumn(String name) {
        this(name, 10);
    }

    /**
     * Creates a new IntColumn object.
     *
     * @param name the column name.
     * @param reserve the initial allocated size.
     */
    public IntColumn(String name, int reserve) {
        super(name, reserve);

        if (reserve != 0) {
            this.value = new int[reserve];
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
    public final int get(int index) {
        assert ((index >= 0) && (index < getRowCount()));

        return this.value[index];
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     *
     */
    public void set(int index, int element) {
        assert ((index >= 0) && (index < getRowCount()));

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
     *
     */
    public void setExtend(int index, int element) {
        assert (index >= 0);
        if (index >= getRowCount()) {
            setSize(index + 1);
        }

        this.value[index] = element;
        setValueUndefined(index, false);
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
            this.value = new int[this.reserve];
        } else {
            int   new_reserve = size;
            int[] new_value = new int[new_reserve];

            for (int i = 0; i < this.reserve; i++) {
                new_value[i] = this.value[i];
            }

            this.value = new_value;
            this.reserve = new_reserve;
        }
    }

    /**
     * Adds a new element in the column,
     *
     * @param element the element.
     */
    public final void add(int element) {
        ensureCapacity(this.size + 1);
        this.value[this.size++] = element;
        this.min_max_updated = false;
        modified();
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
    public int parse(String v) throws ParseException {
        if (v == null)
            return 0;
        try {
            if (getFormat() != null) {
                return ((Number)getFormat().parseObject(v)).intValue();
            }

            return Integer.parseInt(v);
        } catch (Exception e) {
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
    public String format(int v) {
        if (getFormat() != null) {
            return getFormat().format(Integer.valueOf(v));
        }

        return Integer.toString(v);
    }

    /**
     * @see infovis.Column#getValueAt(int)
     */
    public String getValueAt(int i) {
        if (i >= getRowCount() || isValueUndefined(i))
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

        this.max = Integer.MIN_VALUE;
        this.min = Integer.MAX_VALUE;

        for (int i = 0; i < this.size; i++) {
            if (!isValueUndefined(i)) {
                int v = this.value[i];

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
     * Returns a column as a <code>IntColumn</code> from an
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>IntColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>IntColumn</code>.
     */
    public static IntColumn getColumn(Table t, int index) {
        Column c = t.getColumnAt(index);

        if (c instanceof IntColumn) {
            return (IntColumn)c;
        } else {
            return null;
        }
    }

    /**
     * Returns a column as an <code>IntColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>IntColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>IntColumn</code>.
     */
    public static IntColumn getColumn(Table t, String name) {
        Column c = t.getColumn(name);

        if (c instanceof IntColumn) {
            return (IntColumn)c;
        } else {
            return null;
        }
    }

    /**
     * Returns a column as a <code>IntColumn</code> from a table,
     * creating it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>IntColumn</code> from a table,
     */
    public static IntColumn findColumn(Table t, String name) {
        Column c = t.getColumn(name);
        if (c == null) {
            c = new IntColumn(name);
            t.addColumn(c);
        }
        return (IntColumn)c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return int the maximum value of the column.
     */
    public int getMax() {
        updateMinMax();

        return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return int the minimum value of the column.
     */
    public int getMin() {
        updateMinMax();

        return this.min;
    }

    /**
     * @see infovis.Column#getValueClass()
     */
    public Class getValueClass() {
        return Integer.class;
    }

    /**
     * @see infovis.column.NumberColumn#getDoubleAt(int)
     */
    public double getDoubleAt(int row) {
        return get(row);
    }

    /**
     * @see infovis.column.NumberColumn#getFloatAt(int)
     */
    public float getFloatAt(int row) {
        return get(row);
    }

    /**
     * @see infovis.column.NumberColumn#getIntAt(int)
     */
    public int getIntAt(int row) {
        return get(row);
    }

    /**
     * @see infovis.column.NumberColumn#getLongAt(int)
     */
    public long getLongAt(int row) {
        return get(row);
    }

    /**
     * @see infovis.column.NumberColumn#setDoubleAt(int, double)
     */
    public void setDoubleAt(int row, double v) {
        setExtend(row, (int)Math.round(v));
    }

    /**
     * @see infovis.column.NumberColumn#setIntAt(int, int)
     */
    public void setIntAt(int row, int v) {
        setExtend(row, v);
    }

    /**
     * @see infovis.column.NumberColumn#setLongAt(int, long)
     */
    public void setLongAt(int row, long v) {
        setExtend(row, (int)v);
    }

    /**
     * @see infovis.column.NumberColumn#getIntMin()
     */
    public int getIntMin() {
        return getMin();
    }

    /**
     * @see infovis.column.NumberColumn#getIntMax()
     */
    public int getIntMax() {
        return getMax();
    }

    /**
     * @see infovis.column.NumberColumn#round(double)
     */
    public double round(double value) {
        return Math.round(value);
    }

    /**
     * Returns the array of integer values copied from the column.
     *
     * @param a an array of int with at least the column size
     *  or <code>null</code>.
     *
     * @return the array of integer values copied from the column.
     */
    public int[] toArray(int[] a) {
        if (a == null) {
            a = new int[getRowCount()];
        }
        System.arraycopy(this.value, 0, a, 0, getRowCount());
        return a;
    }
    
    public void sort(RowComparator comp) {
        if (comp == null)
            return;
        Sort.sort(this.value, 0, this.size, comp);
        fireColumnChanged();
    }
}
