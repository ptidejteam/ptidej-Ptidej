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
 * Filter out internal columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class InternalFilter implements ColumnFilter {
    static InternalFilter sharedInstance = new InternalFilter();

    /**
     * Avoid creating several instances since we need only one.
     *
     * @return the shared instance.
     */
    public static InternalFilter sharedInstance() {
        return sharedInstance;
    }

    /**
     * @see infovis.column.ColumnFilter#filter(Column)
     */
    public boolean filter(Column c) {
        return c.isInternal();
    }
}
