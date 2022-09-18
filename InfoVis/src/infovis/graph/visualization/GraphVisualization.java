/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph.visualization;

import infovis.Graph;
import infovis.Table;
import infovis.Visualization;
import infovis.column.ColumnLink;
import infovis.column.IntColumn;
import infovis.utils.RowIterator;

/**
 * Abstract base class for Graph Visualizations.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public abstract class GraphVisualization
    extends Visualization
    implements Graph {
    protected Graph graph;
    protected IntColumn outDegreeColumn;
    protected ColumnLink updateDegree;

    static {
        setControlPanelCreator(
            GraphVisualization.class,
            GraphControlPanel.class);
    }

    /**
     * Creates a new GraphVisualization object.
     *
     * @param table the Table.to pass down.
     * @param graph the Graph
     */
    public GraphVisualization(Graph graph, Table table) {
        super(table);
        this.graph = graph;
    }
    
    public GraphVisualization(Graph graph) {
        this(graph, graph.getEdgeTable());
    }

    /**
     * Returns the graph.
     * @return Graph
     */
    public Graph getGraph() {
        return this.graph;
    }

    public int addEdge(int v1, int v2) {
        return this.graph.addEdge(v1, v2);
    }

    public int addVertex() {
        return this.graph.addVertex();
    }

    public RowIterator edgeIterator(int vertex) {
        return this.graph.edgeIterator(vertex);
    }

    public int findEdge(int v1, int v2) {
        return this.graph.findEdge(v1, v2);
    }

    public int getDegree(int vertex) {
        return this.graph.getDegree(vertex);
    }

    public int getEdge(int v1, int v2) {
        return this.graph.getEdge(v1, v2);
    }

    public int getEdgesCount() {
        return this.graph.getEdgesCount();
    }

    public Table getEdgeTable() {
        return this.graph.getEdgeTable();
    }

    public int getFirstEdge(int vertex) {
        return this.graph.getFirstEdge(vertex);
    }

    public int getInVertex(int edge) {
        return this.graph.getInVertex(edge);
    }

    public int getLastEdge(int vertex) {
        return this.graph.getLastEdge(vertex);
    }

    public int getNextEdge(int edge) {
        return this.graph.getNextEdge(edge);
    }

    public int getOutVertex(int edge) {
        return this.graph.getOutVertex(edge);
    }

    public int getVerticesCount() {
        return this.graph.getVerticesCount();
    }

    public boolean isDirected() {
        return this.graph.isDirected();
    }

    public void setDirected(boolean directed) {
        this.graph.setDirected(directed);
    }

    public Table getVertexTable() {
        return this.graph.getVertexTable();
    }
    public int addOutVertex() {
        return this.graph.addOutVertex();
    }

    public int getOutDegree(int vertex) {
        return this.graph.getOutDegree(vertex);
    }

    public Table getOutVertexTable() {
        return this.graph.getOutVertexTable();
    }

    public int getOutVerticesCount() {
        return this.graph.getOutVerticesCount();
    }

}
