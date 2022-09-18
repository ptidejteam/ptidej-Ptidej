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
 * Column containing the number of outgoing edges from each vertex of the Graph.
 *
 * <p>This column is automatically maintained by the <code>ColumnLink</code>
 * mechanism.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class OutDegree extends ColumnLink {
    /** Name of the optional Column containing the number of outgoing edges. */
    public static final String OUTDEGREE_COLUMN = "#outDegree";
    Graph graph;
    IntColumn outDegree;

    public OutDegree(
        Graph graph,
        IntColumn nextVertex,
        IntColumn outDegree) {
        super(nextVertex, outDegree);
        this.graph = graph;
        this.outDegree = outDegree;
    }

    protected void update() {
        try {
            this.outDegree.disableNotify();
            this.outDegree.clear();

            for (RowIterator iter = this.graph.getVertexTable().iterator();
                iter.hasNext();
                ) {
                int vertex = iter.nextRow();
                int cnt = 0;
                for (RowIterator eiter = this.graph.edgeIterator(vertex);
                    eiter.hasNext();
                    ) {
                    eiter.nextRow();
                    cnt++;
                }
                this.outDegree.setExtend(vertex, cnt);
            }
        }
        finally {
            this.outDegree.enableNotify();
        }
    }

    /**
     * Returns the out degree column associated with a graph, creating it
     * if required.
     *
     * @param graph the graph.
     *
     * @return the out degree column associated with the graph.
     */
    public static IntColumn getColumn(Graph graph) {
        IntColumn outDegree =
            IntColumn.getColumn(graph.getVertexTable(), OUTDEGREE_COLUMN);
        if (outDegree == null) {
            outDegree = IntColumn.findColumn(graph.getVertexTable(), OUTDEGREE_COLUMN);
            OutDegree degree =
                new OutDegree(
                    graph,
                    IntColumn.findColumn(
                        graph.getEdgeTable(),
                        Graph.NEXTEDGE_COLUMN),
                    outDegree);
            degree.updateColumn();
        }
        return outDegree;
    }
}
