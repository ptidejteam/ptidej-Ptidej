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
import infovis.utils.IntSortedMap;


/**
 * Abstract class for all columns containing numeric values.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class NumberColumn extends ArrayColumn {
    /**
     * Sets of indexes with undefined values, for sparse columns.
     * null by default
     */
    IntSortedMap undefined;
	
    /**
     * Constructor for NumberColumn.
     *
     * @param name
     * @param reserve
     */
    public NumberColumn(String name, int reserve) {
	super(name, reserve);
    }
	
	
    /**
     * @see infovis.Column#isValueUndefined(int)
     */
    public boolean isValueUndefined(int i) {
	if (i >= getRowCount())
	    return true;
	if (this.undefined == null)
	    return false;
	return this.undefined.containsKey(i);
    }

    /**
     * @see infovis.Column#clear()
     */
    public void clear() {
	this.undefined = null;
	super.clear();
    }
    /**
     * @see infovis.Column#setValueUndefined(int,boolean)
     */
    public void setValueUndefined(int i, boolean undef) {
	if (this.undefined == null) {
	    if (!undef)
		return;
	    this.undefined = new IntSortedMap();
	}
	if (undef) {
	    this.undefined.put(i, this.undefined);
	} else {
	    this.undefined.remove(i);
	}
	this.min_max_updated = false;
	modified();
    }
	
    /**
     * @see infovis.column.ArrayColumn#setSize(int)
     */
    protected void setSize(int newSize) {
	int oldSize = this.size;
	super.setSize(newSize);
	if (oldSize != this.size) {
	    disableNotify();
	    try {
		// Assume we will set value newSize-1 right after that
		if (newSize > oldSize) {
		    for (int i = oldSize; i < (newSize-1); i++) {
			setValueUndefined(i, true);
		    }
		}
	    }
	    finally {
		enableNotify();
	    }
	}
    }

    /**
     * Returns the value as an int
     *
     * @param row the row
     *
     * @return the value as an int
     */
    public abstract int getIntAt(int row);

    /**
     * Returns the value as a float
     *
     * @param row the row
     *
     * @return the value as a float
     */
    public float getFloatAt(int row) {
	return getIntAt(row);
    }

    /**
     * Returns the value as a long
     *
     * @param row the row
     *
     * @return the value as a long
     */
    public long getLongAt(int row) {
	return getIntAt(row);
    }

    /**
     * Returns the value as a double
     *
     * @param row the row
     *
     * @return the value as a double
     */
    public double getDoubleAt(int row) {
	return getIntAt(row);
    }

    /**
     * Sets the value as an int
     *
     * @param row the row
     * @param v the value as an int
     */
    public void setIntAt(int row, int v) {
	setLongAt(row, v);
    }

    /**
     * Sets the value as a float
     *
     * @param row the row
     * @param v the value as a float
     */
    public void setFloatAt(int row, float v) {
	setDoubleAt(row, v);
    }

    /**
     * Sets the value as a long
     *
     * @param row the row
     * @param v the value as a long
     */
    public void setLongAt(int row, long v) {
	setDoubleAt(row, v);
    }

    /**
     * Sets the value as a double
     *
     * @param row the row
     * @param v the value as a double
     */
    public abstract void setDoubleAt(int row, double v);
	
    /**
     * Returns the minimun value as an int.
     * 
     * @return the minimun value as an int.
     */
    public abstract int getIntMin();
	
    /**
     * Returns the maximum value as an int.
     * 
     * @return the maximum value as an int.
     */
    public abstract int getIntMax();
	

    public float getFloatMin() {
	return getIntMin();
    }
	
    public long getLongMin() {
	return getIntMin();
    }
	
    public long getLongMax() {
	return getIntMax();
    }
		
    public float getFloatMax() {
	return getIntMax();
    }
	
    public double getDoubleMin() {
	return getFloatMin();
    }
	
    public double getDoubleMax() {
	return getFloatMax();
    }
    /**
     * Returns a column as a <code>NumberColumn</code> from an
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>NumberColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>NumberColumn</code>.
     */
    public static NumberColumn getNumberColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof NumberColumn) {
	    return (NumberColumn)c;
	} else {
	    return null;
	}
    }

    /**
     * @see infovis.Column#compare(int,int)
     */
    public int compare(int row1, int row2) {
        if (row1 == row2)
            return 0;
        int ret = super.compare(row1, row2);
        if (ret != 0)
            return ret;
	double d = (getDoubleAt(row1) - getDoubleAt(row2));
	if (d == 0)
	    return 0;
	else if (d < 0)
	    return -1;
	else
	    return 1;
    }
    
    public double round(double value) {
        return value;
    }
}
