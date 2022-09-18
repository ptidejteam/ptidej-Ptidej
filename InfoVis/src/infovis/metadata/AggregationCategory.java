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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Class AggregationCategory manages hierarchical aggregation in columns. 
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class AggregationCategory implements AggregationConstants {
    static AggregationCategory sharedInstance = new AggregationCategory();
    HashMap aggregationMap = new HashMap();
    
    public static AggregationCategory sharedInstance() {
        return sharedInstance;
    }
    
    static {
        sharedInstance.putAggregationType(
            AGGREGATION_TYPE_ADDITIVE,
            AdditiveAggregation.sharedInstance());
    }
    
    public Aggregation getAggregation(String type) {
        return (Aggregation) this.aggregationMap.get(type);
    }
    
    public Aggregation getAggregation(Column col, Tree tree) {
        return getAggregation(getAggregationType(col, tree));
    }
    
    public String getAggregationType(Column col, Tree tree) {
        String type = (String)col.getMetadata().get(AGGREGATION_TYPE);
        if (type == null) {
            type = guessAggregationType(col, tree);
        }
        return type;
    }
    
    public String guessAggregationType(Column col, Tree tree) {
        String compType = null;
        // Special case for ATLEAF
        if (AtLeafAggregation.sharedInstance().isAggregating(col, tree) == AGGREGATE_YES)
            return AGGREGATION_TYPE_ATLEAF;
        for (Iterator iter = iterator(); iter.hasNext(); ) {
            Map.Entry entry = (Map.Entry)iter.next();
            Aggregation aggr = (Aggregation)entry.getValue();
            switch(aggr.isAggregating(col, tree)) {
            case AggregationConstants.AGGREGATE_YES:
                return (String)entry.getKey();
            case AggregationConstants.AGGREGATE_COMPATIBLE:
                compType = (String)entry.getKey();
            }
            // Continue anyway
        }
        if (compType == null) {
            compType = AGGREGATION_TYPE_NONE;
        }
            // insures we stick with this aggregation for now
        col.getMetadata().put(AGGREGATION_TYPE, compType);
        
        return compType;
    }
    
    public Aggregation putAggregationType(
        String type, 
        Aggregation aggr) {
        return (Aggregation)this.aggregationMap.put(type, aggr);
    }
    
    public Iterator iterator() {
        return this.aggregationMap.entrySet().iterator();
    }
}
