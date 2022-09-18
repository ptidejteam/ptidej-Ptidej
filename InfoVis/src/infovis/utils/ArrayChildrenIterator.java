/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;

/**
 * Class ArrayChildrenIterator
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */

public class ArrayChildrenIterator implements RowIterator {
    int index;
    int[] children;

    public ArrayChildrenIterator(int index, int[] children) {
        this.index = index;
        this.children = children;
    }

    public ArrayChildrenIterator(int[] children) {
        this(0, children);
    }

    public boolean hasNext() {
        return this.index < this.children.length;
    }

    public Object next() {
        return Integer.valueOf(nextRow());
    }

    public void remove() {
    }

    public int nextRow() {
        if (this.index >= this.children.length)
            return -1;
        return this.children[this.index++];
    }

    public int peekRow() {
        if (this.index >= this.children.length)
            return -1;
        return this.children[this.index];
    }
    /**
     * @see infovis.utils.RowIterator#copy()
     */
    public RowIterator copy() {
        return new ArrayChildrenIterator(this.index, this.children);
    }

}