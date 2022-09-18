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
import infovis.utils.RowFilter;

import java.text.ParseException;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Column of booleans.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class BooleanColumn extends NumberColumn
    implements ListSelectionModel, RowFilter {
    private boolean[] value;
    private boolean	min;
    private boolean	max;
    private int minSelection = Integer.MAX_VALUE;
    private int maxSelection = -1;

    /**
     * Creates a new FloatColumn object.
     *
     * @param name the column name.
     */
    public BooleanColumn(String name) {
	this(name, 10);
    }

    /**
     * Creates a new FloatColumn object.
     *
     * @param name the column name.
     * @param reserve the initial capacity.
     */
    public BooleanColumn(String name, int reserve) {
	super(name, reserve);

	if (reserve != 0) {
	    this.value = new boolean[reserve];
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
    public final boolean get(int index) {
	assert((index>=0) && (index < getRowCount()));
	return this.value[index];
    }
    
    public boolean isFiltered(int row) {
        return isValueUndefined(row) ? false : this.value[row];
    }

    
    /**
     * Replaces the element at the specified position in this column
     * with the specified element.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void set(int index, boolean element) {
	assert((index>=0) && (index < getRowCount()));
	this.value[index] = element;
	setValueUndefined(index, false);
	this.min_max_updated = false;
	if (this.minSelection != Integer.MAX_VALUE && index < this.minSelection)
	    this.minSelection = index;
	if (this.maxSelection != -1 && index > this.maxSelection)
	    this.maxSelection = index;
	modified();
    }

    /**
     * Replaces the element at the specified position in this column
     * with the specified element, growing the column if necessary.
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     */
    public final void setExtend(int index, boolean element) {
	assert(index >=0);
	disableNotify();
	try {
	    if (index >= getRowCount()) {	
		setSize(index + 1);
	    }
	
	    this.value[index] = element;
	    setValueUndefined(index, false);
	    this.min_max_updated = false;
	    if (this.minSelection != Integer.MAX_VALUE && index < this.minSelection)
		this.minSelection = index;
	    if (this.maxSelection != -1 && index > this.maxSelection)
		this.maxSelection = index;
	}
	finally {
	    enableNotify();
	}
    }

    /**
     * Adds a new element in the column,
     *
     * @param element the element.
     */
    public final void add(boolean element) {
	int index = this.size;
	ensureCapacity(index+1);
	this.value[this.size++] = element;
	this.min_max_updated = false;
	if (this.minSelection != Integer.MAX_VALUE && index < this.minSelection)
	    this.minSelection = index;
	if (this.maxSelection != -1 && index > this.maxSelection)
	    this.maxSelection = index;
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
	    this.value = new boolean[this.reserve];
	} else {
	    int     new_reserve = size;
	    boolean[] new_value = new boolean[new_reserve];

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
    public boolean parse(String v) throws ParseException {
	if (v == null)
	    return false;
	try {
	    if (getFormat() != null) {
		return ((Number)getFormat().parseObject(v)).intValue() != 0;
	    }

	    return Boolean.getBoolean(v);
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
    public String format(boolean v) {
	if (getFormat() != null) {
	    return getFormat().format(Boolean.valueOf(v));
	}

	return Boolean.toString(v);
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

	this.max = false;
	this.min = true;
	this.minSelection = Integer.MAX_VALUE;
	this.maxSelection = -1;

	for (int i = 0; i < this.size; i++) {
	    if (!isValueUndefined(i)) {
		if (this.minSelection == Integer.MAX_VALUE)
		    this.minSelection = i;
		boolean v = this.value[i];

		if (v == false) {
		    this.min = false;
		}
		else {
		    this.max = true;
		}
		this.maxSelection = i;
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
    public static BooleanColumn getColumn(Table t, int index) {
	Column c = t.getColumnAt(index);

	if (c instanceof BooleanColumn) {
	    return (BooleanColumn)c;
	} else {
	    return null;
	}
    }
	
    /**
     * Returns a column as a <code>BooleanColumn</code> from a
     * <code>Table</code>.
     *
     * @param t the <code>Table</code>
     * @param name the column name.
     *
     * @return a <code>BooleanColumn</code> or null if no such column
     *         exists or the column is not a
     *         <code>BooleanColumn</code>.
     */
    public static BooleanColumn getColumn(Table t, String name) {
	Column c = t.getColumn(name);

	if (c instanceof BooleanColumn) {
	    return (BooleanColumn)c;
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
    public static BooleanColumn findColumn(Table t, String name) {
	Column c = t.getColumn(name);
	if (c == null) {
	    c = new BooleanColumn(name);
	    t.addColumn(c);
	}
	return (BooleanColumn)c;
    }

    /**
     * Returns the maximum value of the column.
     *
     * @return boolean the maximum value of the column.
     */
    public boolean getMax() {
	updateMinMax();

	return this.max;
    }

    /**
     * Returns the minimum value of the column.
     *
     * @return boolean the minimum value of the column.
     */
    public boolean getMin() {
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
     * @see infovis.Column#compare(int,int)
     */
    public int compare(int row1, int row2) {
        int ret = super.compare(row1, row2);
        if (ret != 0)
            return ret;
	int v1 = get(row1) ? 1 : 0;
	int v2 = get(row2) ? 1 : 0;
		
	return v1 - v2;
    }
    // ListSelectionModel
    private int anchorIndex = -1;
    private int leadIndex = -1;
    private boolean isAdjusting = false; 
	
    /**
     * @see javax.swing.ListSelectionModel#addListSelectionListener(ListSelectionListener)
     */
    public void addListSelectionListener(ListSelectionListener x) {
	this.eventListenerList.add(ListSelectionListener.class, x);
    }

    /**
     * @see javax.swing.ListSelectionModel#addSelectionInterval(int, int)
     */
    public void addSelectionInterval(int index0, int index1) {
	this.anchorIndex = index0;
	this.leadIndex = index1;
	disableNotify();
	try {
	    while (index0 <= index1) {
		setExtend(index0, true);
		index0++;
	    }
	}
	finally {
	    enableNotify();
	}
    }

    /**
     * @see javax.swing.ListSelectionModel#clearSelection()
     */
    public void clearSelection() { 
	this.anchorIndex = -1;
	this.leadIndex = -1;
	this.isAdjusting = false;
	clear();
    }

    /**
     * @see javax.swing.ListSelectionModel#getAnchorSelectionIndex()
     */
    public int getAnchorSelectionIndex() {
	return this.anchorIndex;
    }

    /**
     * @see javax.swing.ListSelectionModel#getLeadSelectionIndex()
     */
    public int getLeadSelectionIndex() {
	return this.leadIndex;
    }

    /**
     * @see javax.swing.ListSelectionModel#getMaxSelectionIndex()
     */
    public int getMaxSelectionIndex() {
	return lastValidRow();
    }

    /**
     * @see javax.swing.ListSelectionModel#getMinSelectionIndex()
     */
    public int getMinSelectionIndex() {
	return firstValidRow();
    }

    /**
     * @see javax.swing.ListSelectionModel#getSelectionMode()
     */
    public int getSelectionMode() {
	return MULTIPLE_INTERVAL_SELECTION;
    }

    /**
     * @see javax.swing.ListSelectionModel#getValueIsAdjusting()
     */
    public boolean getValueIsAdjusting() {
	return this.isAdjusting;
    }

    /**
     * @see javax.swing.ListSelectionModel#insertIndexInterval(int, int, boolean)
     */
    public void insertIndexInterval(int index, int length, boolean before) {
    }

    /**
     * @see javax.swing.ListSelectionModel#isSelectedIndex(int)
     */
    public boolean isSelectedIndex(int index) {
	return !isValueUndefined(index);
    }

    /**
     * @see javax.swing.ListSelectionModel#isSelectionEmpty()
     */
    public boolean isSelectionEmpty() {
	return isEmpty();
    }
	
    public int getSelectedCount() {
	int cnt = 0;
	for (int i = getMinSelectionIndex(); i <= getMaxSelectionIndex(); i++) {
	    if (isSelectedIndex(i))
		cnt++;
	}
	return cnt;
    }

    /**
     * @see javax.swing.ListSelectionModel#removeIndexInterval(int, int)
     */
    public void removeIndexInterval(int index0, int index1) {
    }

    /**
     * @see javax.swing.ListSelectionModel#removeListSelectionListener(ListSelectionListener)
     */
    public void removeListSelectionListener(ListSelectionListener x) {
	this.eventListenerList.remove(ListSelectionListener.class, x);
    }

    /**
     * @see javax.swing.ListSelectionModel#removeSelectionInterval(int, int)
     */
    public void removeSelectionInterval(int index0, int index1) {
	while (index0 <= index1) {
	    setValueUndefined(index0, true);
	    index0++;
	}
    }

    /**
     * @see javax.swing.ListSelectionModel#setAnchorSelectionIndex(int)
     */
    public void setAnchorSelectionIndex(int index) {
	this.anchorIndex = index;
    }

    /**
     * @see javax.swing.ListSelectionModel#setLeadSelectionIndex(int)
     */
    public void setLeadSelectionIndex(int index) {
	this.leadIndex = index;
    }

    /**
     * @see javax.swing.ListSelectionModel#setSelectionInterval(int, int)
     */
    public void setSelectionInterval(int index0, int index1) {
	clear();
	addSelectionInterval(index0, index1);
    }

    /**
     * @see javax.swing.ListSelectionModel#setSelectionMode(int)
     */
    public void setSelectionMode(int selectionMode) {
    }

    /**
     * @see javax.swing.ListSelectionModel#setValueIsAdjusting(boolean)
     */
    public void setValueIsAdjusting(boolean valueIsAdjusting) {
	this.isAdjusting = valueIsAdjusting;
    }
	
    private void fireValueChanged() {
	int firstChanged = firstValidRow();
	int lastChanged = lastValidRow();
	Object[] listeners = this.eventListenerList.getListenerList();
	ListSelectionEvent e = null;
	for (int i = listeners.length - 2; i >= 0; i -= 2) {
	    if (listeners[i] == ListSelectionListener.class) {
		if (e == null) {
		    e = new ListSelectionEvent(this, firstChanged, lastChanged, this.isAdjusting);
		}
		((ListSelectionListener)listeners[i+1]).valueChanged(e);
	    }
	}	
    }
	
    /**
     * @see AbstractColumn#modified()
     */
    protected boolean modified() {
	if (super.modified()) {
	    fireValueChanged();
	    return true;
	}
	return false;
    }

    /**
     * @see infovis.column.NumberColumn#getIntAt(int)
     */
    public int getIntAt(int row) {
	return get(row) ? 1 : 0;
    }
	
    /**
     * @see infovis.column.NumberColumn#setIntAt(int, int)
     */
    public void setDoubleAt(int row, double v) {
	set(row, v != 0);
    }

	
    /**
     * @see infovis.column.NumberColumn#getIntMin()
     */
    public int getIntMin() {
	return getMin() ? 1 : 0;
    }
	
    /**
     * @see infovis.column.NumberColumn#getIntMax()
     */
    public int getIntMax() {
	return getMax() ? 1 : 0;
    }

    /**
     * @see infovis.Column#firstValidRow()
     */
    public int firstValidRow() {
	if (this.minSelection == Integer.MAX_VALUE)
	    this.minSelection = super.firstValidRow();
	return this.minSelection;
    }
	
    /**
     * @see infovis.Column#lastValidRow()
     */
    public int lastValidRow() {
	if (this.maxSelection == -1)
	    this.maxSelection = super.lastValidRow();
	return this.maxSelection;
    }

}
