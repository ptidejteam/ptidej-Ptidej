/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph;

import infovis.Graph;
import infovis.column.ColumnLink;
import infovis.column.IntColumn;
import infovis.utils.RowIterator;

/**
 * Column containing the number of incident edges of each vertex of the Graph.
 *
 * <p>This column is automatically maintained by the <code>ColumnLink</code>
 * mechanism.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class InDegree extends ColumnLink {
    /** Name of the optional Column containing the number of incident edges. */
    public static final String INDEGREE_COLUMN = "#inDegree";
    Graph graph;
    IntColumn inDegree;

    InDegree(Graph graph, IntColumn nextVertex, IntColumn inDegree) {
        super(nextVertex, inDegree);
        this.graph = graph;
        this.inDegree = inDegree;
    }

    protected void update() {
        try {
            this.inDegree.disableNotify();
            this.inDegree.clear();

            for (RowIterator iter = this.graph.getEdgeTable().iterator();
                iter.hasNext();
                ) {
                int edge = iter.nextRow();
                int in = this.graph.getOutVertex(edge);
                if (this.inDegree.isValueUndefined(in)) {
                    this.inDegree.setExtend(in, 1);
                }
                else {
                    this.inDegree.set(in, this.inDegree.get(in) + 1);
                }
            }
            int cnt = this.graph.getVerticesCount();
            for (int i = 0; i < cnt; i++) {
                if (this.inDegree.isValueUndefined(i))
                    this.inDegree.set(i, 0);
            }
        }
        finally {
            this.inDegree.enableNotify();
        }
    }

    /**
     * Returns the in degree column associated with a graph, creating it
     * if required.
     *
     * @param graph the graph.
     *
     * @return the in degree column associated with the graph.
     */
    public static IntColumn getColumn(Graph graph) {
        IntColumn inDegree =
            IntColumn.getColumn(graph.getVertexTable(), INDEGREE_COLUMN);
        if (inDegree == null) {
            inDegree = IntColumn.findColumn(graph.getVertexTable(), INDEGREE_COLUMN);
            InDegree degree =
                new InDegree(
                    graph,
                    IntColumn.findColumn(
                        graph.getEdgeTable(),
                        Graph.NEXTEDGE_COLUMN),
                    inDegree);
            degree.updateColumn();
        }
        return inDegree;
    }

}
