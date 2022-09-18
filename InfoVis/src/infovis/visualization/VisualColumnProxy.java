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

/**
 * Class VisualColumnProxy
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class VisualColumnProxy implements VisualColumnDescriptor {
    VisualColumnDescriptor vc;
    
    public VisualColumnProxy(VisualColumnDescriptor vc) {
        this.vc = vc;
    }

    public boolean isInvalidate() {
        return this.vc.isInvalidate();
    }

    public Column getColumn() {
        return this.vc.getColumn();
    }

    public ColumnFilter getFilter() {
        return this.vc.getFilter();
    }

    public void setInvalidate(boolean b) {
        this.vc.setInvalidate(b);
    }

    public void setFilter(ColumnFilter filter) {
        this.vc.setFilter(filter);
    }

    public void setColumn(Column column) {
        this.vc.setColumn(column);
    }

}
