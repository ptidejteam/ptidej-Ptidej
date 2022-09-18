/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.column;

import infovis.utils.IntSortedMap;

import java.util.Comparator;

/**
 * Base class for sparse columns, i.e. columns containing few elements.
 * 
 * A sparse column is implemented with an {@link infovis.utils.IntSortedMap}.
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class SparseColumn extends AbstractColumn
    implements Comparator {
    IntSortedMap map;
    protected transient boolean min_max_updated;
	
    /**
     * Constructor for SparseColumn.
     * @param name the colum name.
     */
    public SparseColumn(String name) {
	super(name);
	this.map = new IntSortedMap();
    }

    /**
     * @see infovis.Column#capacity()
     */
    public int capacity() {
	return Integer.MAX_VALUE;
    }

    /**
     * @see infovis.Column#clear()
     */
    public void clear() {
	this.map.clear();
    }

    /**
     * @see infovis.Column#ensureCapacity(int)
     */
    public void ensureCapacity(int minCapacity) {
    }

    /**
     * @see infovis.Column#getRowCount()
     */
    public int getRowCount() {
	return this.map.size();
    }

    /**
     * @see infovis.Column#isValueUndefined(int)
     */
    public boolean isValueUndefined(int i) {
	return this.map.containsKey(i);
    }

    /**
     * See AbstractColumn#setSize(int)
     */
    protected void setSize(int newSize) {
    }

    /**
     * @see infovis.Column#setValueUndefined(int, boolean)
     */
    public void setValueUndefined(int i, boolean undef) {
	if (undef) {
	    this.map.remove(i);
	}
    }
}
