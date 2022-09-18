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
public class ScalarProduct extends AbstractMetric {
    
    /* (non-Javadoc)
     * @see infovis.graph.clustering.Metric#distance(int[], int[])
     */
    public float distance(int[] row1, int[] row2) {
        float count = 0;
        if (row1.length > row2.length) {
            int[] tmp = row1;
            row1 = row2;
            row2 = tmp;
        }
        int low = 0;
        int high = row2.length - 1;
        for (int i = 0; i < row1.length && low <= high; i++) {
            low = contains(row1[i], row2, low, high);
            if (low < 0) {
                low = -low;
            }
            else {
                count++;
            }
        }
        
        return count;
    }


}
