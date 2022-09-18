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
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ComposeExceptFilter extends ComposeOrFilter {

    /**
     * Constructor for ComposeExceptFilter.
     * @param filter1
     * @param filter2
     */
    public ComposeExceptFilter(ColumnFilter filter1, ColumnFilter filter2) {
        super(filter1, filter2);
    }

    /**
     * @see infovis.column.filter.ComposeOrFilter#filter(Column)
     */
    public boolean filter(Column column) {
        if (! this.filter1.filter(column))
            return false;
        return this.filter2.filter(column);
    }

}
