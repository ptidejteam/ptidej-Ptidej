/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis;

import infovis.utils.RowComparator;
import infovis.utils.RowIterator;

import java.text.Format;
import java.text.ParseException;

import javax.swing.event.ChangeListener;


/**
 * A column is an indexed collection of values of homogeneous type.
 *
 * <p>Values can also be undefined, which is useful for some data sets
 * that lack some values.</p>
 *
 * <p>Each column class containing objects of type TYPE implements
 * type-specific methods with the same names (Java has no parametric
 * types so this has to be hand made). These methods are: <dl>
 * <dt>TYPE get(int index) <dd>Returns the element at the specified
 * position in this column.
 *
 * <dt>void set(int index, TYPE element)
 *
 * <dd>Replaces the element at the specified position in this column
 * with the specified element.
 *
 * <dt>void setExtend(int index, TYPE element)
 *
 * <dd>Replaces the element at the specified position in this column
 * with the specified element, growing the column if necessary.
 *
 * <dt>void add(TYPE element)
 * <dd>Add a new element in the column.
 *
 * <dt>TYPE getMin()
 * <dd>Return the minimum value of the column.
 *
 * <dt>TYPE getMax()
 * <dd>Return the maximum value of the column.
 *
 * <dt>TYPE parse(String v) throws ParseException
 * <dd>Parse a string and return the value for the column.
 *
 * <dt>String format(TYPE v)
 *
 * <dd>Returns the string representation of a value according to the
 * current format.
 *
 * <dt>TYPEColumn getColumn(Table t, String name)
 *
 * <dd>Return a column with the concrete type from an
 * <code>Table</code>.
 *
 * <dt>TYPEColumn getColumn(Table t, int index)
 *
 * <dd>Return a column with the concrete type from an
 * <code>Table</code>.
 * 
 * <dt>TYPEColumn findColumn(Table t, String name)
 *
 * <dd>Return a column with the concrete type from an
 * <code>Table</code>, creating it if needed.  </dl> </p>
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Column extends Metadata, RowComparator {
    /**
     * Returns the column name.
     * @return the column name.
     */
    public String getName();

    /**
     * Sets the column name.
     * @param name The name to set
     */
    public void setName(String name);

    /**
     * Returns true if the column name starts with a '#'.
     *
     * @return true if the column name starts with a '#'.
     */
    public boolean isInternal();

    /**
     * Returns true if the ith value is undefined.
     *
     * @param i the index
     * @return true if the ith value is undefined.
     */
    public boolean isValueUndefined(int i);

    /**
     * Sets the ith element to have an undefined value.
     *
     * @param i the index
     * @param undef true if the value should be undefined,
     *                 false otherwise.
     */
    public void setValueUndefined(int i, boolean undef);

    /**
     * Returns the format.
     * @return Format
     */
    public Format getFormat();

    /**
     * Sets the format.
     * @param format The format to set
     */
    public void setFormat(Format format);

    /**
     * Returns <tt>true</tt> if this column contains no elements.
     *
     * @return <tt>true</tt> if this column contains no elements.
     */
    public boolean isEmpty();

    /**
     * Returns the number of elements in this column.
     *
     * @return the number of elements in this column.
     */
    public int getRowCount();

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
    public void clear();

    /**
     * Increases the capacity of this column, if necessary, to ensure
     * that it can hold at least the number of values specified by the
     * minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity.
     */
    public void ensureCapacity(int minCapacity);

    /**
     * Returns the current capacity of this column.
     *
     * @return  the current capacity .
     */
    public abstract int capacity();

    /**
     * Returns the String representation of the element at the
     * specified position in this column.  The representation is
     * generated by the current format.
     *
     * @param index index of element representation to return.
     * @return the String representation of element at the specified position in this column.
     */
    public String getValueAt(int index);

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
    public void setValueAt(int index, String element)
	throws ParseException;

    /**
     * Replaces the element at the specified position in this column
     * with the element specified in its String representation or set
     * it undefined if the String cannot be parsed.
     *
     * @param v element to be appended to this column.
     *
     */
    public boolean setValueOrNullAt(int index, String v);
    
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
    public void addValue(String v) throws ParseException;

    /**
     * Appends the element specified in its String representation to
     * the end of this column or adds an undefined object if the
     * String cannot be parsed.
     *
     * @param v element to be appended to this column.
     *
     */
    public boolean addValueOrNull(String v);

    /**
     * Returns the String representation of the minimum value.
     *
     * @return the String representation of the minimum value.
     */
    public String minValue();

    /**
     * Returns the String representation of the maximum value.
     *
     * @return the String representation of the maximum value.
     */
    public String maxValue();

    /**
     * Returns the class of the elements.
     *
     * @return the class of the elements.
     */
    public Class getValueClass();

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
    public void disableNotify();

    /**
     * Re enable notifications, triggering eventListeners if
     * modifications occur.
     * 
     * @see #disableNotify()
     */
    public void enableNotify();

    /**
     * Adds a listener to the list that's notified each time a change occurs.
     *
     * @param listener the listener
     */
    public void addChangeListener(ChangeListener listener);

    /**
     * Removes a listener from the list that's notified each time a change occurs.
     *
     * @param listener the listener
     */
    public void removeChangeListener(ChangeListener listener);

    /**
     * Returns the index of the first row which has a defined value
     * 	or Integer.MAX_VALUE if there is none.
     * 
     * @return the index of the first row which has a defined value
     * 	or Integer.MAX_VALUE if there is none.
     */
    public int firstValidRow();
	
    /**
     * Returns the index of the last row which has a defined value
     * 	or -1 if there is none.
     * 
     * @return the index of the last row which has a defined value
     * 	or -1 if there is none.
     */
    public int lastValidRow();

	
    /**
     * Returns a RowIterator over all the valid rows of this column.
     * 
     * @return a RowIterator over all the valid rows of this column.
     */
    public RowIterator iterator();
}
