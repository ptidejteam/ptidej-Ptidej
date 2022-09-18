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
import java.util.Comparator;


/**
 * A Column of Strings.
 *
 * @version $Revision: 1.1 $
 * @author fekete
 */
public class ObjectColumn extends ArrayColumn {
    private Object[]   value;
    private Object     min;
    private Object     max;
    private Comparator order;

    /**
     * Creates a new StringColumn object.
     *
     * @param name the column name.
     */
    public ObjectColumn(String name) {
	this(name, 10);
    }

    /**
     * Creates a new ObjectColumn object.
     *
     * @param name the column name.
     * @param reserve the initial capacity.
     */
    public ObjectColumn(String name, int reserve) {
	super(name, reserve);

	if (reserve != 0) {
	    this.value = new Object[reserve];
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
    public final Object get(int index) {
        assert((index >= 0) && (index < getRowCount()));
        return this.value[index];
    }
    
    /**
     * Returns the element at the specified position or
     * <code>null</code> if it is undefined or invalid.
     * 
     * @param index the index.
     * @return the element at the specified position or
     * <code>null</code> if it is undefined or invalid.
     */
    public final Object getObjectAt(int index) {
        if ((index >= 0) && (index < getRowCount()))
            return this.value[index];
        return null;
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void set(int index, Object element) {
	assert((index >= 0) && (index < getRowCount()));
	this.value[index] = element;
	this.min_max_updated = false;
	modified();
    }

    public void setValueUndefined(int index, boolean undef) {
	if (index < 0 || index >= getRowCount())
	    return;
	if (undef)
	    this.value[index] = null;
        // don't know what to store instead
//	else if (value[index] == null)
//	    value[index] = "";
    }

    public boolean isValueUndefined(int index) {
	if (index < 0 || index >= getRowCount())
	    return true;
	return this.value[index] == null;
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element, growing the column if necessary.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void setExtend(int index, Object element) {
	assert(index >= 0);
	if (index >= getRowCount()) {
	    setSize(index + 1);
	}

	this.value[index] = element;
	this.min_max_updated = false;
	modified();
    }

    /**
     * Adds a new element in the column,
     *
     * @param element the element.
     */
    public final void add(Object element) {
	ensureCapacity(this.size + 1);
	this.value[this.size++] = element;
	this.min_max_updated = false;
	modified();
    }

    protected void reallocate(int size) {
	if (size <= this.reserve) {
	    return;
	}

	if (this.value == null) {
	    this.reserve = size;
	    this.value = new Object[this.reserve];
	} else {
	    int		 new_reserve = size;
	    Object[] new_value = new Object[new_reserve];

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
    public Object parse(String v) throws ParseException {
	if (v == null)
	    return v;
	try {
	    if (getFormat() != null) {
		return (Object)getFormat().parseObject(v);
	    }
	} catch (Exception e) {
	    throw new ParseException(e.getMessage(), 0);
	}
	return v;
    }


    /**
     * Returns the string representation of a value according to the current format.
     *
     * @param v the value
     *
     * @return the string representation.
     */
    public String format(Object v) {
	if (getFormat() != null) {
	    return getFormat().format(v);
	}

	return v.toString();
    }

    public String getValueAt(int i) {
	if (i < 0 || i >= getRowCount() || isValueUndefined(i))
	    return "";
	return format(get(i));
    }

    public void setValueAt(int i, String v) throws ParseException {
	setExtend(i, parse(v));
    }

    public void addValue(String v) throws ParseException {
	add(parse(v));
    }


    public final int compare(Object s1, Object s2) {
	if (this.order != null)
	    return this.order.compare(s1, s2);
	return s1.hashCode() - s2.hashCode();
    }

    protected void updateMinMax() {
	if (this.min_max_updated) {
	    return;
	}

	this.max = null;
	this.min = null;

	for (int i = 0; i < this.size; i++) {
	    if (!isValueUndefined(i)) {
		Object v = this.value[i];

		if (this.min == null || compare(this.min, v) < 0) {
		    this.min = v;
		}

		if (this.max == null || compare(v, this.max) > 0) {
		    this.max = v;
		}
	    }
	}
	this.min_max_updated = true;
    }

    public String minValue() {
	return format(getMin());
    }

    public String maxValue() {
	return format(getMax());
    }

    /**
     * Returns a column as a <code>ObjectColumn</code> from an
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>ObjectColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>ObjectColumn</code>.
     */
    public static ObjectColumn getColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof ObjectColumn) {
	    return (ObjectColumn)c;
	} else {
	    return null;
	}
    }
	
    /**
     * Returns a column as a <code>ObjectColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>ObjectColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>ObjectColumn</code>.
     */
    public static ObjectColumn getColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c instanceof ObjectColumn) {
	    return (ObjectColumn)c;
	} else {
	    return null;
	}
    }
	
    /**
     * Returns a column as a <code>ObjectColumn</code> from a table,
     * creating it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>ObjectColumn</code> from a table,
     */
    public static ObjectColumn findColumn(Table t, String name) {
	Column c = t.getColumn(name);
	if (c == null) {
	    c = new ObjectColumn(name);
	    t.addColumn(c);
	}
	return (ObjectColumn)c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return String the maximum value of the column.
     */
    public Object getMax() {
	updateMinMax();

	return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return Object the minimum value of the column.
     */
    public Object getMin() {
	updateMinMax();

	return this.min;
    }

    /**
     * Returns the order used to find the min and max for the strings.
     * @return RowComparator
     */
    public Comparator getOrder() {
	return this.order;
    }

    /**
     * Sets the order used to find the min and max for the strings.
     * @param order The order to set
     */
    public void setOrder(Comparator order) {
	this.order = order;
    }

    public Class getValueClass() {
	return Object.class;
    }

    public int compare(int row1, int row2) {
        int ret = super.compare(row1, row2);
        if (ret != 0)
            return ret;
	Object o1 = get(row1);
	Object o2 = get(row2);
	return compare(o1, o2);
    }
}
