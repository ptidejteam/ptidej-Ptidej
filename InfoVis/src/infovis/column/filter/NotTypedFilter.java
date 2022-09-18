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
 * Class NotTypedFilter
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class NotTypedFilter implements ColumnFilter {
    Class type;
    
    public NotTypedFilter(Class type) {
        this.type = type;
    }
    public boolean filter(Column column) {
        return column != null && !this.type.isAssignableFrom(column.getClass());
    }
}
