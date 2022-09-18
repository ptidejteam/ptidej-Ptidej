/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NullRowIterator implements RowIterator, Cloneable {
    static NullRowIterator sharedInstance = new NullRowIterator();
    
    public static NullRowIterator sharedInstance() {
        return sharedInstance;
    }
    
    /**
     * @see infovis.utils.RowIterator#nextRow()
     */
    public int nextRow() {
        return -1;
    }

    /**
     * @see infovis.utils.RowIterator#peekRow()
     */
    public int peekRow() {
        return -1;
    }

    /**
     * @see java.util.Iterator#hasNext()
     */
    public boolean hasNext() {
        return false;
    }

    /**
     * @see java.util.Iterator#next()
     */
    public Object next() {
        return null;
    }

    /**
     * @see java.util.Iterator#remove()
     */
    public void remove() {
    }
    
    /**
     * @see infovis.utils.RowIterator#copy()
     */
    public RowIterator copy() {
        return sharedInstance();
    }



}
