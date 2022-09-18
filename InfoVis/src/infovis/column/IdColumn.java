/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column;

import java.text.ParseException;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class IdColumn extends NumberColumn {
    /**
     * Constructor for IdColumn.
     * @param name
     */
    public IdColumn(String name) {
	super(name, 0);
	this.size = 0;
    }

    /**
     * Returns the element at the specified position in this column.
     * 
     * @param index index of element to return.
     * 
     * @return the index itelf.
     */
    public final int get(int index) {
	assert((index >= 0));
	return index;
    }

    /**
     * See AbstractColumn#reallocate(int)
     */
    protected void reallocate(int size) {
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
    	this.size = Math.max(i+1, this.size);
    }

    /**
     * @see infovis.Column#addValue(String)
     */
    public void addValue(String v) throws ParseException {
    	this.size++;
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
     * Returns the maximum value of the column.
     * 
     * @return int the maximum value of the column.
     */
    public int getMax() {
        return this.size;
    }

    /**
     * Returns the minimum value of the column.
     * 
     * @return int the minimum value of the column.
     */
    public int getMin() {
        return 0;
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
    }

    /**
     * @see infovis.column.NumberColumn#setIntAt(int, int)
     */
    public void setIntAt(int row, int v) {
    }

    /**
     * @see infovis.column.NumberColumn#setLongAt(int, long)
     */
    public void setLongAt(int row, long v) {
    }

    public int getIntMin() {
	return getMin();
    }
	
    public int getIntMax() {
	return getMax();
    }
    
    /**
     * @see infovis.column.NumberColumn#round(double)
     */
    public double round(double value) {
        return Math.round(value);
    }
}
