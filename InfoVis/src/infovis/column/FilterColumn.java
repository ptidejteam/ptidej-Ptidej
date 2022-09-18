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
import infovis.panel.DynamicQuery;
import infovis.utils.RowFilter;
import infovis.utils.RowIterator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.BitSet;


/**
 * Column managing <code>BitSet</code>s meant for filtering columns with
 *  dynamic queries.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class FilterColumn extends ArrayColumn implements RowFilter {
    private BitSet[]  value;
    private BitSet    min;
    private BitSet    max;
    private ArrayList dynamicQueries = new ArrayList();

    /**
     * Constructor for FilterColumn.
     * @param name
     */
    public FilterColumn(String name) {
	this(name, 10);
    }

    /**
     * Constructor for FilterColumn.
     * @param name
     * @param reserve
     */
    public FilterColumn(String name, int reserve) {
	super(name, reserve);

	if (reserve != 0) {
	    this.value = new BitSet[reserve];
	}
	this.size = 0;
    }

    /**
     * Returns the bitIndex for the specified dynamic query or -1 if it is not registered.
     *
     * @param query the dynamic query
     *
     * @return the bitIndex for the specified dynamic query or -1 if it is not registered.
     */
    public int getDynamicQueryIndex(DynamicQuery query) {
	return this.dynamicQueries.indexOf(query);
    }

    /**
     * Returns the bitIndex for the specified dynamic query
     *  creating it and adding the dynamic query if is not registered.
     *
     * @param query the dynamic query
     *
     * @return the bitIndex for the specified dynamic query
     *  creating it and adding the dynamic query if is not registered.
     */
    public int findDynamicQueryIndex(DynamicQuery query) {
	int ret = getDynamicQueryIndex(query);
	if (ret != -1)
	    return ret;
	return addDynamicQuery(query);
    }

    /**
     * Adds a dynamic query to the list of registered queries.
     *
     * @param query the dynamic query
     *
     * @return the bitIndex for the created query.
     */
    public int addDynamicQuery(DynamicQuery query) {
	// We shouldn't change the index.
	int i = this.dynamicQueries.indexOf(null);
	if (i == -1) {
	    this.dynamicQueries.add(query);
	    return this.dynamicQueries.size() - 1;
	}
	this.dynamicQueries.set(i, query);
	return i;
    }

    /**
     * Removes a dynamic query from the list of registered queries, also
     * clearing the associated bit from the BitSets.
     *
     * @param query the dynamic query
     *
     * @return the bitIndex for the specified dynamic query
     *  or -1 if it wasn't registered.
     */
    public int removeDynamicQuery(DynamicQuery query) {
	int i = getDynamicQueryIndex(query);
	if (i == -1)
	    return i;
	clearBit(i);
	return i;
    }
    
    public void applyDynamicQuery(DynamicQuery query, RowIterator iter) {
        int bitIndex = findDynamicQueryIndex(query);
        
        disableNotify();
        try {
            clearBit(bitIndex);
            while(iter.hasNext()) {
                int row = iter.nextRow();
                if (query.isFiltered(row)) {
                    setBit(row, bitIndex);
                }
            }
        }
        finally {
            enableNotify();
        }
    }

    /**
     * @see infovis.Column#setValueUndefined(int, boolean)
     */
    public void setValueUndefined(int index, boolean undef) {
	if (index < 0 || index >= getRowCount())
	    return;
	this.value[index] = null;
    }
	
    /**
     * @see infovis.Column#isValueUndefined(int)
     */
    public boolean isValueUndefined(int index) {
	if (index < 0 || index >= getRowCount())
	    return true;
	return this.value[index] == null;
    }

    /**
     * Returns the element at the specified position in this column.
     *
     * @param index index of element to return.
     *
     * @return the element at the specified position in this column.
     */
    public final BitSet get(int index) {
	assert((index >= 0) && (index < getRowCount()));
	return this.value[index];
    }

    /**
     * Returns true if the specified index is filtered.
     *
     * @param index the index.
     *
     * @return true if the specified index is filtered.
     */
    public boolean isFiltered(int index) {
	if ((index < 0) || (index >= getRowCount())) {
	    return false;
	}
	if (this.value[index] == null)
	    return false;
	return !this.value[index].isEmpty();
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void set(int index, BitSet element) {
	assert((index >= 0) && (index < getRowCount()));
	this.value[index] = element;
	setValueUndefined(index, false);
	this.min_max_updated = false;
	modified();
    }

    /**
     * Clears a specified bit from <code>BitSet</code> at specified index.
     *
     * @param index the index.
     * @param bitIndex the bit.
     */
    public final void clearBit(int index, int bitIndex) {
	if (index < 0 || index >= getRowCount())
	    return;

	BitSet bs = this.value[index];
	if (bs == null)
	    return;
	bs.clear(bitIndex);
	modified();
    }

    /**
     * Clears a specified bit from all the <code>BitSet</code>s
     *
     * @param bitIndex the bit.
     */
    public final void clearBit(int bitIndex) {
	for (int index = 0; index < getRowCount(); index++) {
	    if (this.value[index] != null) {
		this.value[index].clear(bitIndex);
	    }
	}
        modified();
    }

    /**
     * Sets a specified bit from <code>BitSet</code> at a specified index, creating
     * the <code>BitSet</code> if required.
     *
     * @param index DOCUMENT ME!
     * @param bitIndex DOCUMENT ME!
     */
    public final void setBit(int index, int bitIndex) {
	assert(index >= 0);
	BitSet bs;
	if (index >= getRowCount()) {
	    setSize(index + 1);
	    bs = this.value[index] = new BitSet(bitIndex);
	} else {
	    bs = this.value[index];
	    if (bs == null)
		bs = this.value[index] = new BitSet(bitIndex);
	}
	bs.set(bitIndex);
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
    public final void setExtend(int index, BitSet element) {
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
    public final void add(BitSet element) {
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
	    this.value = new BitSet[this.reserve];
	} else {
	    int		 new_reserve = size;
	    BitSet[] new_value = new BitSet[new_reserve];

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
    public BitSet parse(String v) throws ParseException {
	if (v == null)
	    return null;
	try {
	    if (getFormat() != null) {
		return (BitSet)getFormat().parseObject(v);
	    }
	} catch (Exception e) {
	    throw new ParseException(e.getMessage(), 0);
	}
	return null;
    }

    /**
     * Returns the string representation of a value according to the current format.
     *
     * @param v the value
     *
     * @return the string representation.
     */
    public String format(BitSet v) {
	if (getFormat() != null) {
	    return getFormat().format(v);
	}

	return v.toString();
    }

    /**
     * @see infovis.Column#getValueAt(int)
     */
    public String getValueAt(int i) {
	if (i < 0 || i >= getRowCount() || isValueUndefined(i))
	    return "";
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
     * @see infovis.Column#compare(int,int)
     */
    public final int compare(BitSet s1, BitSet s2) {
	if (s1.equals(s2))
	    return 0;

	return s1.hashCode() - s2.hashCode();
    }

    /**
     * See AbstractColumn#updateMinMax()
     */
    protected void updateMinMax() {
	if (this.min_max_updated) {
	    return;
	}

	this.max = null;
	this.min = null;

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
     * Returns a column as a <code>FilterColumn</code> from an
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param index index in the <code>Table</code>
     *
     * @return a <code>FilterColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>FilterColumn</code>.
     */
    public static FilterColumn getColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof FilterColumn) {
	    return (FilterColumn)c;
	} else {
	    return null;
	}
    }

    /**
     * Returns a column as a <code>FilterColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>FilterColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>FilterColumn</code>.
     */
    public static FilterColumn getColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c instanceof FilterColumn) {
	    return (FilterColumn)c;
	} else {
	    return null;
	}
    }

    /**
     * Returns a column as a <code>FilterColumn</code> from a table,
     * creating it if needed.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a column as a <code>FilterColumn</code> from a table,
     */
    public static FilterColumn findColumn(Table t, String name) {
	Column c = t.getColumn(name);
	if (c == null) {
	    c = new FilterColumn(name);
	    t.addColumn(c);
	}
	return (FilterColumn)c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return String the maximum value of the column.
     */
    public BitSet getMax() {
	updateMinMax();

	return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return BitSet the minimum value of the column.
     */
    public BitSet getMin() {
	updateMinMax();

	return this.min;
    }

    /**
     * @see infovis.Column#getValueClass()
     */
    public Class getValueClass() {
	return BitSet.class;
    }

    /**
     * @see infovis.Column#lastValidRow()
     */
    public int lastValidRow() {
	int v = super.lastValidRow();

	// adjust sizeColumn to reflect the last valid row
	if (this.size > (v + 1))
	    this.size = v + 1;
	return v;
    }

    /**
     * @see infovis.Column#compare(int, int)
     */
    public int compare(int row1, int row2) {
        
        int ret = super.compare(row1, row2);
        if (ret != 0)
            return ret;
	return compare(get(row1), get(row2));
    }
}
