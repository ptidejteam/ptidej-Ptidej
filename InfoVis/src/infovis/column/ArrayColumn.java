/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column;

/**
 * Base class for columns implemented on top of an array.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class ArrayColumn extends AbstractColumn {
    protected int size;
    protected int reserve;
    protected transient boolean min_max_updated;

    /**
     * Constructor for ArrayColumn.
     *
     * @param name the column name
     * @param reserve the initial reserved sizeColumn.
     */
    protected ArrayColumn(String name, int reserve) {
	super(name);
	this.reserve = reserve;
	this.size = 0;
    }

    /**
     * @see infovis.Column#getRowCount()
     */
    public int getRowCount() {
	return this.size;
    }

    /**
     * @see infovis.Column#capacity()
     */
    public int capacity() {
	return this.reserve;
    }

    /**
     * Sets the sizeColumn of this column.
     *
     * If the new sizeColumn is greater than the current sizeColumn,
     * new <code>0</code> items are added to the end of the column. If
     * the new sizeColumn is less than the current sizeColumn, all
     * components at index <code>newSize</code> and greater are
     * discarded.
     *
     * @param   newSize   the new sizeColumn of this column.
     * @throws  ArrayIndexOutOfBoundsException if new sizeColumn is negative.
     */
    protected void setSize(int newSize) {
	assert(newSize >= 0);
	ensureCapacity(newSize);
	this.size = newSize;
    }

    /**
     * @see infovis.Column#clear()
     */
    public void clear() {
	if (this.size == 0)
	    return;
	setSize(0);
	modified();
    }
}
