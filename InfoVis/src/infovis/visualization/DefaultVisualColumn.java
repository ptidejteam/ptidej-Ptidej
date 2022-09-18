/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.visualization;

import infovis.Column;
import infovis.column.ColumnFilter;
import infovis.column.ColumnFilterException;

/**
 * Class DefaultVisualColumn
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */

    
public class DefaultVisualColumn implements VisualColumnDescriptor {
    Column column;
    boolean invalidate;
    ColumnFilter filter;
        
    public DefaultVisualColumn(boolean invalidate, ColumnFilter filter) {
        this.invalidate = invalidate;
        this.filter = filter;
    }
        
    public DefaultVisualColumn(boolean invalidate) {
        this(invalidate, null);
    }
        
    public DefaultVisualColumn() {
        this(true);
    }
        
    public void setColumn(Column column) {
        if (this.filter != null && this.filter.filter(column))
            throw new ColumnFilterException("Invalid column filter");
        this.column = column;
    }
        
    public Column getColumn() {
        return this.column;
    }
    public ColumnFilter getFilter() {
        return this.filter;
    }

    public boolean isInvalidate() {
        return this.invalidate;
    }

    public void setFilter(ColumnFilter filter) {
        this.filter = filter;
    }

    public void setInvalidate(boolean b) {
        this.invalidate = b;
    }

}