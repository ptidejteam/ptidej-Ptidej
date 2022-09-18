/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.clustering;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class AbstractMetric implements Metric {
    public static int contains(int row, int[] rows, int low, int high) {
         while (low <= high) {
             int mid = (low + high) >> 1;
             int midVal = rows[mid];
    
             if (midVal < row)
             low = mid + 1;
             else if (midVal > row)
             high = mid - 1;
             else
             return mid; // key found
         }
         return -(low + 1);  // key not found.
    }
}
