/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph;

import infovis.Graph;
import infovis.utils.RowComparator;
import infovis.utils.RowIterator;
import infovis.utils.Sort;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class Algorithms {

    public static int[] sortEdges(Graph graph, int vertex, RowComparator comp) {
        int size = graph.getDegree(vertex);
        int[] sorted = new int[size];
        int i = 0;
        
        for (RowIterator iter = graph.edgeIterator(vertex); iter.hasNext(); ) {
            int edge = iter.nextRow();
            sorted[i++] = edge;
        }
        Sort.sort(sorted, comp);
        
        return sorted;
    }

}
