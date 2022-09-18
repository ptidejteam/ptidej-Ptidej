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
 * A Column of floats.
 *
 * @version $Revision: 1.1 $
 * @author fekete
 */
public class FloatColumn extends NumberColumn {
    private float[] value;
    private float   min;
    private float   max;

    /**
     * Creates a new FloatColumn object.
     *
     * @param name the column name.
     */
    public FloatColumn(String name) {
	this(name, 10);
    }

    /**
     * Creates a new FloatColumn object.
     *
     * @param name the column name.
     * @param reserve the initial capacity.
     */
    public FloatColumn(String name, int reserve) {
	super(name, reserve);

	if (reserve != 0) {
	    this.value = new float[reserve];
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
    public final float get(int index) {
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
    public final void set(int index, float element) {
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
    public final void setExtend(int index, float element) {
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
    public final void add(float element) {
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
	    this.value = new float[this.reserve];
	} else {
	    int     new_reserve = size;
	    float[] new_value = new float[new_reserve];

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
    public float parse(String v) throws ParseException {
	if (v == null)
	    return 0;
	try {
	    if (getFormat() != null) {
		return ((Number)getFormat().parseObject(v)).floatValue();
	    }

	    return Float.parseFloat(v);
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
    public String format(float v) {
	if (getFormat() != null) {
	    return getFormat().format(Float.valueOf(v));
	}

	return Float.toString(v);
    }

    /**
     * @see infovis.Column#getValueAt(int)
     */
    public String getValueAt(int i) {
	if ( i < 0 || i >= getRowCount() || isValueUndefined(i))
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

	this.max = Float.MIN_VALUE;
	this.min = Float.MAX_VALUE;

	for (int i = 0; i < this.size; i++) {
	    if (!isValueUndefined(i)) {
		float v = this.value[i];

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
     * Returns a column as a <code>FloatColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>FloatColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>FloatColumn</code>.
     */
    public static FloatColumn getColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof FloatColumn) {
	    return (FloatColumn)c;
	} else {
	    return null;
	}
    }
	
    /**
     * Returns a column as a <code>FloatColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>FloatColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>FloatColumn</code>.
     */
    public static FloatColumn getColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c instanceof FloatColumn) {
	    return (FloatColumn)c;
	} else {
	    return null;
	}
    }


    /**
     * Returns a column as a <code>FloatColumn</code> from a table,
     * creating it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>FloatColumn</code> from a table,
     */
    public static FloatColumn findColumn(Table t, String name) {
	Column c = t.getColumn(name);
	if (c == null) {
	    c = new FloatColumn(name);
	    t.addColumn(c);
	}
	return (FloatColumn)c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return float the maximum value of the column.
     */
    public float getMax() {
	updateMinMax();

	return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return float the minimum value of the column.
     */
    public float getMin() {
	updateMinMax();

	return this.min;
    }

    /**
     * @see infovis.Column#getValueClass()
     */
    public Class getValueClass() {
	return Float.class;
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
	return (int)get(row);
    }

    /**
     * @see infovis.column.NumberColumn#getLongAt(int)
     */
    public long getLongAt(int row) {
	return (long)get(row);
    }

    /**
     * @see infovis.column.NumberColumn#setDoubleAt(int, double)
     */
    public void setDoubleAt(int row, double v) {
	setExtend(row, (float)v);
    }

    /**
     * @see infovis.column.NumberColumn#setFloatAt(int, float)
     */
    public void setFloatAt(int row, float v) {
	setExtend(row, v);
    }
	
    /**
     * @see infovis.column.NumberColumn#getIntMin()
     */
    public int getIntMin() {
	return (int)getMin();
    }
	
    /**
     * @see infovis.column.NumberColumn#getIntMax()
     */
    public int getIntMax() {
	return (int)getMax();
    }
	
    /**
     * @see infovis.column.NumberColumn#getFloatMin()
     */
    public float getFloatMin() {
	return getMin();
    }
	
    /**
     * @see infovis.column.NumberColumn#getFloatMax()
     */
    public float getFloatMax() {
	return getMax();
    }
    
    /**
     * @see infovis.column.NumberColumn#round(double)
     */
    public double round(double value) {
        return (float)value;
    }
}
