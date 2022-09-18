/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column.filter;

import infovis.Column;
import infovis.column.ColumnFilter;


/**
 * Filter for composing Filters.
 *
 * Since the Filter object returns <code>true</code> if a column is
 * filtered out, the composition should perform an <code>or</code>.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ComposeOrFilter implements ColumnFilter {
    protected ColumnFilter filter1;
    protected ColumnFilter filter2;

    /**
     * Creates a new ComposeOrFilter object.
     *
     * @param filter1 first filter.
     * @param filter2 second filter.
     */
    public ComposeOrFilter(ColumnFilter filter1, ColumnFilter filter2) {
        this.filter1 = filter1;
        this.filter2 = filter2;
    }

    /**
     * @see infovis.column.ColumnFilter#filter(Column)
     */
    public boolean filter(Column column) {
        return this.filter1.filter(column) || this.filter2.filter(column);
    }
}
