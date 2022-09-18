/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.metadata;

import infovis.Column;
import infovis.Tree;

/**
 * Interface Aggregation
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Aggregation extends AggregationConstants {
    
    public short isAggregating(Column col, Tree tree);
    /**
     * Computes the aggregation values of the specified source column into 
     * the destination column.
     * 
     * @param src the source column
     * @param tree the tree
     * @param dst the destination column or <code>null</code>.
     */
    public Column aggregate(Column src, Tree tree, Column dst);
    public String getType();
}
