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
 * A Column of longs.
 * 
 * @version $Revision: 1.1 $
 * @author fekete
 */
public class LongColumn extends NumberColumn {
    private long[] value;
    private long min;
    private long max;

    /**
     * Creates a new LongColumn object.
     * 
     * @param name the column name.
     */
    public LongColumn(String name) {
        this(name, 10);
    }

    /**
     * Creates a new LongColumn object.
     * 
     * @param name the column name.
     * @param reserve the initial capacity.
     */
    public LongColumn(String name, int reserve) {
        super(name, reserve);
        
        if (reserve != 0) {
            this.value = new long[reserve];
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
    public final long get(int index) {
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
    public final void set(int index, long element) {
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
  	
    public final void setExtend(int index, long element) {
	assert(index >= 0);
        if (index >= getRowCount()) {
	    setSize(index+1);
        }

        this.value[index] = element;
        setValueUndefined(index, false);
        this.min_max_updated = false;
        modified();
    }	
	
    /**
     * Adds a new element in the column.
     * 
     * @param element the element.
     */
    public final void add(long element) {
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
            this.value = new long[this.reserve];
        }
        else {
            int new_reserve = size;
            long[] new_value = new long[new_reserve];

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
    public long parse(String v) throws ParseException {
    	if (v == null) return 0;
    	try {
	    if (getFormat() != null) {
		return ((Number)getFormat().parseObject(v)).longValue();
	    }

	    return Long.parseLong(v);
        }
    	catch(Exception e) {
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
    public String format(long v) {
        if (getFormat() != null) {
            return getFormat().format(Long.valueOf(v));
        }

        return Long.toString(v);
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

        this.max = Long.MIN_VALUE;
        this.min = Long.MAX_VALUE;

        for (int i = 0; i < this.size; i++) {
            if (!isValueUndefined(i)) {
                long v = this.value[i];

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
     * Returns a column as a <code>LongColumn</code> from a
     * <code>Table</code>.
     * 
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     * 
     * @return a <code>LongColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>LongColumn</code>.
     */
    public static LongColumn getColumn(Table t, int index) {
        Column c = t.getColumnAt(index);

        if (c instanceof LongColumn) {
            return (LongColumn)c;
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a column as a <code>LongColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>LongColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>LongColumn</code>.
     */
    public static LongColumn getColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c instanceof LongColumn) {
	    return (LongColumn)c;
	} else {
	    return null;
	}
    }
	
    /**
     * Returns a column as a <code>LongColumn</code> from a table,
     * creating it if needed.
     * 
     * @param t the <code>Table</code>
     * @param name the column name.
     * 
     * @return a column as a <code>LongColumn</code> from a table,
     */
    public static LongColumn findColumn(Table t, String name) {
    	Column c = t.getColumn(name);
    	if (c == null) {
	    c = new LongColumn(name);
	    t.addColumn(c);
    	}
    	return (LongColumn)c;
    }
    /**
     * Returns the maximum value of the column.
     * 
     * @return long the maximum value of the column.
     */
    public long getMax() {
        updateMinMax();

        return this.max;
    }

    /**
     * Returns the minimum value of the column.
     * 
     * @return long the minimum value of the column.
     */
    public long getMin() {
        updateMinMax();

        return this.min;
    }
    /**
     * @see infovis.Column#getValueClass()
     */
    public Class getValueClass() {
	return Long.class;
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
	return get(row);
    }

    /**
     * @see infovis.column.NumberColumn#setDoubleAt(int, double)
     */
    public void setDoubleAt(int row, double v) {
	set(row, (long)Math.round(v));
    }

    /**
     * @see infovis.column.NumberColumn#setLongAt(int, long)
     */
    public void setLongAt(int row, long v) {
	setExtend(row, v);
    }
	
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
     * @see infovis.column.NumberColumn#getLongMin()
     */
    public long getLongMin() {
	return getMin();
    }
	
    /**
     * @see infovis.column.NumberColumn#getLongMax()
     */
    public long getLongMax() {
	return getMax();
    }
	
    /**
     * @see infovis.column.NumberColumn#getDoubleMin()
     */
    public double getDoubleMin() {
	return getMin();
    }
	
    /**
     * @see infovis.column.NumberColumn#getDoubleMax()
     */
    public double getDoubleMax() {
	return getMax();
    }
    
    /**
     * @see infovis.column.NumberColumn#round(double)
     */
    public double round(double value) {
        return Math.round(value);
    }

}
