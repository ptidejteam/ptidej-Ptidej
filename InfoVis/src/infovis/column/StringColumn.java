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
public class StringColumn extends ArrayColumn {
    private String[]   value;
    private String     min;
    private String     max;
    private Comparator order;

    /**
     * Creates a new StringColumn object.
     *
     * @param name the column name.
     */
    public StringColumn(String name) {
	this(name, 10);
    }

    /**
     * Creates a new StringColumn object.
     *
     * @param name the column name.
     * @param reserve the initial capacity.
     */
    public StringColumn(String name, int reserve) {
	super(name, reserve);

	if (reserve != 0) {
	    this.value = new String[reserve];
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
    public final String get(int index) {
	assert((index >= 0) && (index < getRowCount()));

	return this.value[index];
    }

    /**
     * Replaces the element at the specified position in this column with the
     * specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void set(int index, String element) {
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
	else if (this.value[index] == null)
	    this.value[index] = "";
    }

    public boolean isValueUndefined(int index) {
	if (index < 0 || index >= getRowCount())
	    return true;
	return this.value[index] == null;
    }

    /**
     * Replaces the element at the specified position in this column with the
     * specified element, growing the column if necessary.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void setExtend(int index, String element) {
	assert(index >= 0);
	if (index >= getRowCount()) {
	    setSize(index + 1);
	}

	this.value[index] = element;
	this.min_max_updated = false;
	modified();
    }

    /**
     * Adds an element to the column.
     *
     * @param element the element.
     */
    public final void add(String element) {
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
	    this.value = new String[this.reserve];
	} else {
	    int		 new_reserve = size;
	    String[] new_value = new String[new_reserve];

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
    public String parse(String v) throws ParseException {
	if (v == null) {
	    return v;
	}

	try {
	    if (getFormat() != null) {
		return (String)getFormat().parseObject(v);
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
    public String format(String v) {
	if (getFormat() != null) {
	    return getFormat().format(v);
	}

	return v;
    }

    public String getValueAt(int i) {
	if (i < 0 || (i >= getRowCount()) || isValueUndefined(i)) {
	    return "";
	}

	return format(get(i));
    }

    public void setValueAt(int i, String v) throws ParseException {
	setExtend(i, parse(v));
    }

    public void addValue(String v) throws ParseException {
	add(parse(v));
    }

    /**
     * Comparator for elements of this column.
     *
     * @param s1 first element.
     * @param s2 second element.
     *
     * @return the comparison of the elements.
     */
    public final int compare(String s1, String s2) {
	if (this.order != null) {
	    return this.order.compare(s1, s2);
	}

	return s1.compareToIgnoreCase(s2);
    }

    protected void updateMinMax() {
	if (this.min_max_updated) {
	    return;
	}

	this.max = null;
	this.min = null;

	for (int i = 0; i < this.size; i++) {
	    if (!isValueUndefined(i)) {
		String v = this.value[i];

		if ((this.min == null) || (compare(this.min, v) < 0)) {
		    this.min = v;
		}

		if ((this.max == null) || (compare(v, this.max) > 0)) {
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
     * Returns a column as a <code>StringColumn</code> from an
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>StringColumn</code> or null if no such column exists
     *         or the column is not a <code>StringColumn</code>.
     */
    public static StringColumn getColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof StringColumn) {
	    return (StringColumn)c;
	} else {
	    return null;
	}
    }
	
    /**
     * Returns a column as a <code>StringColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>StringColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>StringColumn</code>.
     */
    public static StringColumn getColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c instanceof StringColumn) {
	    return (StringColumn)c;
	} else {
	    return null;
	}
    }

    /**
     * Returns a column as a <code>StringColumn</code> from a table, creating
     * it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>StringColumn</code> from a table,
     */
    public static StringColumn findColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c == null) {
	    c = new StringColumn(name);
	    t.addColumn(c);
	}

	return (StringColumn)c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return String the maximum value of the column.
     */
    public String getMax() {
	updateMinMax();

	return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return String the minimum value of the column.
     */
    public String getMin() {
	updateMinMax();

	return this.min;
    }

    /**
     * Returns the order used to find the min and max for the strings.
     *
     * @return RowComparator
     */
    public Comparator getOrder() {
	return this.order;
    }

    /**
     * Sets the order used to find the min and max for the strings.
     *
     * @param order The order to set
     */
    public void setOrder(Comparator order) {
	this.order = order;
    }

    public Class getValueClass() {
	return String.class;
    }

    public int compare(int row1, int row2) {
        int ret = super.compare(row1, row2);
        if (ret != 0)
            return ret;
	return compare(get(row1), get(row2));
    }
}
