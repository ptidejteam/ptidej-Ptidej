/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;


/**
 * Iterator over table rows.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class TableIterator implements RowIterator {
    protected int row;
    protected int end;

    public TableIterator(int first, int end) {
        this.row = first;
        this.end = end;
    }

    public boolean hasNext() {
        return this.row < this.end;
    }

    public Object next() {
        return Integer.valueOf(nextRow());
    }

    public void remove() {
    }

    public int nextRow() {
        return this.row++;
    }

    public int peekRow() {
        return this.row;
    }
    /**
     * @see infovis.utils.RowIterator#copy()
     */
    public RowIterator copy() {
        return new TableIterator(this.row, this.end);
    }

}
