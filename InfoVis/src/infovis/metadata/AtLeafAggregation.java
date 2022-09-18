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
import infovis.tree.DepthFirst;

/**
 * Class AtLeafAggregation
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AtLeafAggregation implements Aggregation {
    static AtLeafAggregation sharedInstance = new AtLeafAggregation();
    
    public static AtLeafAggregation sharedInstance() {
        return sharedInstance;
    }
    
    public short isAggregating(final Column col, final Tree tree) {
        String aggr = (String)col.getMetadata().get(AGGREGATION_TYPE);
        
        if (aggr != null ) {
            if (aggr.equals(AGGREGATION_TYPE_ATLEAF))
                return AGGREGATE_YES;
            return AGGREGATE_NO;
        }
        try {
            DepthFirst.visit(tree, new DepthFirst.Visitor() {
                public boolean preorder(int node) {
                    if (tree.isLeaf(node))
                        return true;
                    if (col.isValueUndefined(node))
                        return true;
                    throw new RuntimeException("NO"); 
                }
    
                public void inorder(int node) {
                }
    
                public void postorder(int node) {
                }
            });
        }
        catch(RuntimeException e) {
            return AGGREGATE_NO;
        }
        col.getMetadata().put(AGGREGATION_TYPE, AGGREGATION_TYPE_ATLEAF);
        return AGGREGATE_YES;
    }

    public Column aggregate(Column src, Tree tree, Column dst) {
        return null; // don't compute anything here
    }

    public String getType() {
        return AGGREGATION_TYPE_ATLEAF;
    }

    public static final short isAtLeaf(Column col, Tree tree) {
        String aggr = 
            AggregationCategory.sharedInstance().getAggregationType(col, tree);
        return aggr.equals(AGGREGATION_TYPE_ATLEAF) ? AGGREGATE_YES : AGGREGATE_NO;
    }
}
