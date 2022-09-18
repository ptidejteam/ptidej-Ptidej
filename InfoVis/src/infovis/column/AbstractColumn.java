/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column;

import infovis.Column;
import infovis.utils.RowIterator;
import infovis.utils.TableIterator;

import java.text.Format;
import java.text.ParseException;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;


/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractColumn implements Column {
    /**
     * The column name.
     */
    protected String name;

    /**
     * Metadata associated with this column.
     */
    protected Map metadata;

    /**
     * User's client properties.
     */
    protected Map clientPropery;

    /**
     * Format used to convert from the internal representation to a
     * comprehensible readable user's representation.  When null, a
     * default format is used.
     */
    protected Format format;

    /**
     * The number of time this column has been modified.
     *
     * This field is used to trigger notifications.
     */
    protected int modCount = 0;

    /**
     * The number of time disableNotify() has been called minus the
     * number of time enableNotify() has been called.
     */
    protected int inhibitNotify = 0;

    /**
     * List of listeners registered to this column.
     */
    protected EventListenerList eventListenerList;
    protected ChangeEvent       changeEvent;

    /**
     * Constructor for Column
     */
    public AbstractColumn(String name) {
        this.name = name;
    }

    /**
     * Returns the name.
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     * @param name The name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns true if the column name starts with a '#'.
     *
     * @return true if the column name starts with a '#'.
     */
    public boolean isInternal() {
        return this.name.length() == 0 || this.name.charAt(0) == '#';
    }

    /**
     * Returns true if the ith value is undefined.
     *
     * @param i the index
     * @return true if the ith value is undefined.
     */
    public abstract boolean isValueUndefined(int i);

    /**
     * Sets the ith element to have an undefined value.
     *
     * @param i the index
     * @param undef true if the value should be undefined,
     *                 false otherwise.
     */
    public abstract void setValueUndefined(int i, boolean undef);

    /**
     * Returns the clientPropery.
     * @return Map
     */
    public Map getClientPropery() {
        if (this.clientPropery == null) {
            this.clientPropery = new HashMap();
        }
        return this.clientPropery;
    }

    /**
     * Returns the metadata.
     * @return Map
     */
    public Map getMetadata() {
        if (this.metadata == null) {
            this.metadata = new HashMap();
        }
        return this.metadata;
    }

    /**
     * Returns the format.
     * @return Format
     */
    public Format getFormat() {
        return this.format;
    }

    /**
     * Sets the format.
     * @param format The format to set
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * Returns <tt>true</tt> if this column contains no elements.
     *
     * @return <tt>true</tt> if this column contains no elements.
     */
    public boolean isEmpty() {
        return getRowCount() == 0;
    }

    /**
     * Returns the number of elements in this column.
     *
     * @return the number of elements in this column.
     */
    public abstract int getRowCount();

    /**
     * Sets the sizeColumn of this column. If the new sizeColumn is
     * greater than the current sizeColumn, new <code>0</code> items
     * are added to the end of the column. If the new sizeColumn is
     * less than the current sizeColumn, all components at index
     * <code>newSize</code> and greater are discarded.
     *
     * @param   newSize   the new sizeColumn of this column.
     * @throws  ArrayIndexOutOfBoundsException if new sizeColumn is negative.
     */
    protected abstract void setSize(int newSize);

    /**
     * Removes all of the elements from this column.  The Column will
     * be empty after this call returns.
     *
     * WARNING: if there is an associated format that maintains a
     * status, such as a {@link infovis.column.CategoricalFormat}, it
     * is not cleared so results may not be what you expected.  The
     * <code>CategoricalFormat</code> can be cleared explicitely if
     * needed.
     */
    public abstract void clear();

    /**
     * Increases the capacity of this column, if necessary, to ensure
     * that it can hold at least the number of values specified by the
     * minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity.
     */
    public void ensureCapacity(int minCapacity) {
        if (minCapacity < capacity())
            return;
        int newCapacity = capacity() * 2;
        if (newCapacity < minCapacity)
            newCapacity = minCapacity;
        reallocate(newCapacity);
    }

    /**
     * Returns the current capacity of this column.
     *
     * @return  the current capacity .
     */
    public abstract int capacity();

    /**
     * Low-level mechanism to reallocate the column.
     *
     * @param capacity the new capacity of the column.
     */
    protected abstract void reallocate(int capacity);

    /**
     * Returns the String representation of the element at the
     * specified position in this column.  The representation is
     * generated by the current format.
     *
     * @param index index of element representation to return.
     * @return the String representation of element at the specified position in this column.
     */
    public abstract String getValueAt(int index);

    /**
     * Replaces the element at the specified position in this column
     * with the element specified in its String representation.
     *
     * It will be read and translated using the Format.  A null value
     * stores the zero value of the type.  The column is extended if
     * needed.
     *
     *
     * @param index index of element to replace.
     * @param element element to be stored at the specified position.
     *
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    public abstract void setValueAt(int index, String element)
                             throws ParseException;

    /**
     * Replaces the element at the specified position in this column
     * with the element specified in its String representation or set
     * it undefined if the String cannot be parsed.
     *
     * @param index index where the element should be stored.
     * @param v element to be added to this column.
     *
     */
    public boolean setValueOrNullAt(int index, String v) {
        try {
            setValueAt(index, v);
        } catch (ParseException e) {
            setValueUndefined(index, true);
            return false;
        }
        return true;
    }

    /**
     * Appends the element specified in its String representation to
     * the end of this column. <p>
     *
     * Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some lists
     * will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any
     * restrictions on what elements may be added.
     *
     * @param v element to be appended to this column.
     *
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    public abstract void addValue(String v) throws ParseException;

    /**
     * Appends the element specified in its String representation to
     * the end of this column or adds an undefined object if the
     * String cannot be parsed.
     *
     * @param v element to be appended to this column.
     *
     */
    public boolean addValueOrNull(String v) {
        try {
            addValue(v);
        } catch (ParseException e) {
            try {
                addValue(null);
            } catch (ParseException pe) {
            }
            setValueUndefined(getRowCount() - 1, true);
            return false;
        }
        return true;
    }

    /**
     * Returns the String representation of the minimum value.
     *
     * @return the String representation of the minimum value.
     */
    public abstract String minValue();

    /**
     * Returns the String representation of the maximum value.
     *
     * @return the String representation of the maximum value.
     */
    public abstract String maxValue();

    /**
     * Returns the class of the elements.
     *
     * @return the class of the elements.
     */
    public abstract Class getValueClass();

    /**
     * Returns the modCount.
     * @return int
     */
    public int getModCount() {
        return this.modCount;
    }

    /**
     * Disables notification until enableNotify has been called.
     *
     * <p>This method is useful if a large number of modifications is
     * going to happen on the column and notifying each time would be
     * too time consuming.  The notification will be deferred until
     * enableNotify is called.</p>
     *
     * <p>Calls to disableNotify can be nested</p>
     * @see #enableNotify()
     */
    public void disableNotify() {
        this.inhibitNotify++;
    }

    /**
     * Re enable notifications, triggering eventListeners if
     * modifications occur.
     *
     * @see #disableNotify()
     */
    public void enableNotify() {
        this.inhibitNotify--;
        if (this.inhibitNotify <= 0) {
            this.inhibitNotify = 0;
            fireColumnChanged();
        }
    }

    /**
     * Fire the notification.
     */
    public void fireColumnChanged() {
        if (this.inhibitNotify > 0 || this.modCount == 0)
            return;
        if (this.eventListenerList != null) {
            EventListener[] list = this.eventListenerList.getListeners(ChangeListener.class);
            for (int i = list.length - 1; i >= 0; i--) {
                ((ChangeListener)list[i]).stateChanged(this.changeEvent);
            }
        }
        this.modCount = 0;
    }

    /**
     * Adds a listener to the list that's notified each time a change occurs.
     *
     * @param listener the listener
     */
    public void addChangeListener(ChangeListener listener) {
        if (this.eventListenerList == null) {
            this.eventListenerList = new EventListenerList();
            this.changeEvent = new ChangeEvent(this);
        }
        this.eventListenerList.add(ChangeListener.class, listener);
    }

    /**
     * Removes a listener from the list that's notified each time a change occurs.
     *
     * @param listener the listener
     */
    public void removeChangeListener(ChangeListener listener) {
        if (this.eventListenerList != null) {
            this.eventListenerList.remove(ChangeListener.class, listener);
        }
    }

    /**
     * Mark the column as modified.
     *
     * Call notifications if not disabled.
     *
     * @return true if notifications have been called.
     */
    protected boolean modified() {
        this.modCount++;
        if (this.eventListenerList != null && this.inhibitNotify == 0) {
            fireColumnChanged();
            return true;
        }
        return false;
    }

    /**
     * Returns the index of the first row which has a defined value
     *  or Integer.MAX_VALUE if there is none.
     *
     * @return the index of the first row which has a defined value
     *  or Integer.MAX_VALUE if there is none.
     */
    public int firstValidRow() {
        int i;
        for (i = 0; i < getRowCount(); i++) {
            if (!isValueUndefined(i))
                return i;
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Returns the index of the last row which has a defined value
     *  or -1 if there is none.
     *
     * @return the index of the last row which has a defined value
     *  or -1 if there is none.
     */
    public int lastValidRow() {
        int i;
        for (i = getRowCount() - 1; i >= 0; i--) {
            if (!isValueUndefined(i))
                return i;
        }
        return i;
    }

    /**
     * @see infovis.utils.RowComparator#compare(int, int)
     */
    public int compare(int row1, int row2) {
        // Undefined values are always set higher than defined values
        // so that their rank is always greater than defined
        // values.  When both are undefined, they are sorted in
        // ascending row order.
        if (isValueUndefined(row1)) {
            if (isValueUndefined(row2))
                return row1 - row2;
            else
                return 1;
        } else if (isValueUndefined(row2))
            return -1;
        return 0;
    }

    /**
     * Returns a RowIterator over all the valid rows of this column.
     *
     * @return a RowIterator over all the valid rows of this column.
     */
    public RowIterator iterator() {
        return new Iterator(this);
    }

    public static class Iterator extends TableIterator {
        protected Column column;

        public Iterator(Column c) {
            this(c, c.firstValidRow(), c.lastValidRow()+1);
        }

        public Iterator(Column c, int first, int last) {
            super(first, last);
            this.column = c;
        }

        public Object next() {
            return Integer.valueOf(nextRow());
        }

        public void remove() {
        }

        public int nextRow() {
            int ret = this.row;
            do {
                this.row++;
            } while (this.row <= this.end && this.column.isValueUndefined(this.row));

            return ret;
        }

        public int peekRow() {
            return this.row;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String toString() {
        return getName();
    }
}
