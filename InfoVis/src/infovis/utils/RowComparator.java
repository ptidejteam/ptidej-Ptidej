/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;


/**
 * Interface for sorting according to row values.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface RowComparator {
    /**
     * Compare two rows.
     *
     * @param row1 index of row1
     * @param row2 index of row1
     *
     * @return 0 if row values are equal,<br>
     *   if a value is undefined, it is considered as <emph>higher</emph> than
     *   any defined value, otherwise<br>
     *   a negative value if the value of row1 is less than that of row2<br>
     *   a positive value if the value of row1 is greater than that of row2<br>
     */
    public int compare(int row1, int row2);
    
    /**
     * Returns <code>true</code> if the value at the specified row is undefined.
     * 
     * @param row the row.
     * 
     * @return <code>true</code> if the value at the specified row is undefined.
     */
    public boolean isValueUndefined(int row);
}
