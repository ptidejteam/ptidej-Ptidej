/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.column;

import infovis.Column;

/**
 * Interface for filtering out Columns.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface ColumnFilter {
    /**
     * Returns <code>true</code> if the <code>Column</code>
     * should be filtered out.
     * 
     * @return <code>true</code> if the <code>Column</code>
     * should be filtered out.
     */
    boolean filter(Column column);
}
