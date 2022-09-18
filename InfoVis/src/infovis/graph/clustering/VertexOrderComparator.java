/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.clustering;

import infovis.Graph;
import infovis.utils.RowComparator;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class VertexOrderComparator implements RowComparator {
    Graph graph;
    
    public VertexOrderComparator(Graph graph) {
        this.graph = graph;
    }

    /* (non-Javadoc)
     * @see infovis.utils.RowComparator#compare(int, int)
     */
    public int compare(int edge1, int edge2) {
        return this.graph.getOutVertex(edge1) - this.graph.getOutVertex(edge2);
    }
    
    public boolean isValueUndefined(int row) {
        return false;
    }



}
