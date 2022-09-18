/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.metadata;

/**
 * interface AggregationConstants
 * 
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface AggregationConstants extends Constants {
    /**
     * Qualify how a column aggregates when it is hierarchical
     */
    public static final String AGGREGATION_TYPE = "AGGREGATION_TYPE";
    /** No aggregation */
    public static final String AGGREGATION_TYPE_NONE = "AGGREGATION_TYPE_NONE";
    /** Attributes only at leaves */
    public static final String AGGREGATION_TYPE_ATLEAF= "AGGREGATION_TYPE_ATLEAF";
    /** Aggregation additive, required for treemaps */
    public static final String AGGREGATION_TYPE_ADDITIVE = "AGGREGATION_TYPE_ADDITIVE";
    /** Aggreation by max value */
    public static final String AGGREGATION_TYPE_MAX = "AGGREGATION_TYPE_MAX";
    /** Aggregation by min value */
    public static final String AGGREGATION_TYPE_MIN = "AGGREGATION_TYPE_MIN";
    /** Aggregation by mean value */
    public static final String AGGREGATION_TYPE_MEAN = "AGGREGATION_TYPE_MEAN";
    /** Aggregation by string concatenation */
    public static final String AGGREGATION_TYPE_CONCAT = "AGGREGATION_TYPE_CONCAT";

    public static final short AGGREGATE_NO = 0;
    public static final short AGGREGATE_YES = 1;
    public static final short AGGREGATE_COMPATIBLE = -1;
}
