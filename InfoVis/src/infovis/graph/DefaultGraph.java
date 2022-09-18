/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.graph;

import infovis.Graph;
import infovis.Table;
import infovis.column.IntColumn;
import infovis.table.DefaultTable;
import infovis.table.TableProxy;
import infovis.utils.RowIterator;
import infovis.utils.TableIterator;


/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DefaultGraph extends TableProxy implements Graph {
    /** The in vertex table */
    protected Table vertexTable;
    
    /** The out vertex table */
    protected Table outVertexTable;
    
    /** The first edge of each "in" vertex. */
    protected IntColumn vertexFirstEdge;

    /** The last edge of each "in" vertex */
    protected IntColumn vertexLastEdge;

    /** The in vertex of each edge */
    protected IntColumn edgeInVertex;

    /** The out vertex of each edge */
    protected IntColumn edgeOutVertex;

    /** The next edge for an "in" vertex */
    protected IntColumn nextEdge;
    
    protected IntColumn outVertexFirstEdge;
    protected IntColumn outVertexLastEdge;
    protected IntColumn outNextEdge;
    
    public DefaultGraph(Table edgeTable, Table vertexTable, Table outVertexTable) {
        super(edgeTable);
        if (vertexTable == null) {
            vertexTable = (Table)getMetadata().get(VERTEX_TABLE_METADATA);
            if (vertexTable== null) {
                vertexTable = new DefaultTable();
                getMetadata().put(VERTEX_TABLE_METADATA, vertexTable);
            }
        }
        this.vertexTable = vertexTable;
        if (outVertexTable == null) {
            outVertexTable = vertexTable;
        }
        this.outVertexTable = outVertexTable;
        this.vertexFirstEdge = IntColumn.findColumn(vertexTable, FIRSTEDGE_COLUMN);
        this.vertexLastEdge = IntColumn.findColumn(vertexTable, LASTEDGE_COLUMN);
        this.edgeInVertex = IntColumn.findColumn(this, INVERTEX_COLUMN);
        this.edgeOutVertex = IntColumn.findColumn(this, OUTVERTEX_COLUMN);
        this.nextEdge = IntColumn.findColumn(this, NEXTEDGE_COLUMN);
        this.outVertexFirstEdge = IntColumn.findColumn(outVertexTable, OUT_FIRSTEDGE_COLUMN);
        this.outVertexLastEdge = IntColumn.findColumn(outVertexTable, OUT_LASTEDGE_COLUMN);
        this.outNextEdge = IntColumn.findColumn(this, OUT_NEXTEDGE_COLUMN);
        setDirected(true);
        
    }

    /**
     * Constructor for Graph.
     *
     * @param table the underlying <code>Table</code>
     */
    public DefaultGraph(Table table) {
        this(table, null, null);
    }
    
    public DefaultGraph(Table table, Table vertices) {
        this(table, vertices, null);
    }

    /**
     * Constructor for Graph.
     */
    public DefaultGraph() {
        this(new DefaultTable());
    }

    public boolean isDirected() {
        String graphType = (String)getMetadata().get(GRAPH_TYPE);
        if (graphType == null )
            return true;
         return graphType.equals(GRAPH_TYPE_DIRECTED); 
    }
    
    public void setDirected(boolean directed) {
        getMetadata().put(GRAPH_TYPE, GRAPH_TYPE_DIRECTED);
    }


    /**
     * Returns the number of vertices in the graph
     *
     * @return        The number of vertices in the graph.
     */
    public int getVerticesCount() {
        return this.vertexFirstEdge.getRowCount();
    }
    
    public int getOutVerticesCount() {
        return this.outVertexFirstEdge.getRowCount();
    }

    /**
     * Adds a new vertex to the graph.
     *
     * @return the vertex number.
     */
    public int addVertex() {
        if (this.vertexTable == this.outVertexTable)
            return addOutVertex();
        this.vertexFirstEdge.add(Graph.NIL);
        this.vertexLastEdge.add(Graph.NIL);
        return this.vertexFirstEdge.getRowCount() - 1;
    }
    
    public int addOutVertex() {
        this.outVertexFirstEdge.add(Graph.NIL);
        this.outVertexLastEdge.add(Graph.NIL);
        if (this.vertexTable == this.outVertexTable) {
            this.vertexFirstEdge.add(Graph.NIL);
            this.vertexLastEdge.add(Graph.NIL);
        }
        return this.outVertexFirstEdge.getRowCount()-1;
    }

    /**
     * Returns the number of edges in the graph.
     *
     * @return the number of edges in the graph.
     */
    public int getEdgesCount() {
        return this.edgeOutVertex.getRowCount();
    }

    /**
     * Adds a new edge between two vertices.
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     *
     * @return the new edge index.
     */
    public int addEdge(int v1, int v2) {
        assert(! (v1 < 0 || v1 >= getVerticesCount()));
        assert(! (v2 < 0 || v2 >= getOutVerticesCount()));
        int edge = this.edgeInVertex.getRowCount();
        this.edgeInVertex.add(v1);
        this.edgeOutVertex.setExtend(edge, v2);
        int last = this.vertexLastEdge.get(v1);
        this.vertexLastEdge.set(v1, edge);
        if (last == Graph.NIL) {
            this.vertexFirstEdge.set(v1, edge);
        } else {
            this.nextEdge.setExtend(last, edge);
        }
        this.nextEdge.setExtend(edge, Graph.NIL);
        
        last = this.outVertexLastEdge.get(v2);
        this.outVertexLastEdge.set(v2, edge);
        if (last == Graph.NIL) {
            this.outVertexFirstEdge.set(v2, edge);
        }
        else {
            this.outNextEdge.setExtend(last, edge);
        }
        this.outNextEdge.setExtend(edge, Graph.NIL);
        return edge;
    }

    /**
     * Returns the "in" vertex of an edge.
     *
     * @param edge the edge.
     *
     * @return the "in" vertex of an edge or NIL.
     */
    public int getInVertex(int edge) {
        return this.edgeInVertex.get(edge);
    }

    /**
     * Returns the "out" vertex of an edge.
     *
     * @param edge the edge.
     *
     * @return the "out" vertex of an edge.
     */
    public int getOutVertex(int edge) {
        return this.edgeOutVertex.get(edge);
    }

    /**
     * Returns the first edge of a specified vertex.
     *
     * @param vertex the vertex,
     *
     * @return the first edge of the specified vertex or NIL if none exists.
     */
    public int getFirstEdge(int vertex) {
        return this.vertexFirstEdge.get(vertex);
    }

    /**
     * Returns the last edge of a specified vertex.
     *
     * @param vertex the vertex,
     *
     * @return the last edge of the specified vertex or NIL if none exists.
     */
    public int getLastEdge(int vertex) {
        return this.vertexLastEdge.get(vertex);
    }

    /**
     * Returns the edge following a specified edge starting at a vertex.
     *
     * @param edge the edge.
     *
     * @return the edge following a given edge starting at the vertex
     *  or NIL if the specified edge is the last of the "in" vertex.
     */
    public int getNextEdge(int edge) {
        return this.nextEdge.get(edge);
    }

    /**
     * Returns an edge between two specified vertices.
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     *
     * @return an edge between two specified vertices
     *  or NIL if none exists.
     */
    public int getEdge(int v1, int v2) {
        for (int e = getFirstEdge(v1); e != Graph.NIL; e = getNextEdge(e)) {
            if (getOutVertex(e) == v2) {
                return e;
            }
        }
        return Graph.NIL;
    }

    /**
     * Returns an edge between two specified vertices.
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     *
     * @return an edge between two specified vertices
     *  creating one if none exists.
     */
    public int findEdge(int v1, int v2) {
        int e = getEdge(v1, v2);
        if (e == Graph.NIL)
            return addEdge(v1, v2);
        return e;
    }

    /**
     * Returns the degree of the vertex, which is simply the number of edges
     * of the vertex.
     *
     * @param vertex the vertex.
     * @return  The degree of the vertex.
     */
    public int getDegree(int vertex) {
        int cnt = 0;
        for (int edge = this.vertexFirstEdge.get(vertex); edge != -1;
                 edge = this.nextEdge.get(edge)) {
            cnt++;
        }
        return cnt;
    }
    
    /**
     * Returns the degree of the vertex, which is simply the number of edges
     * of the vertex.
     *
     * @param vertex the vertex.
     * @return  The degree of the vertex.
     */
    public int getOutDegree(int vertex) {
        int cnt = 0;
        for (int edge = this.outVertexFirstEdge.get(vertex); edge != -1;
                 edge = this.outNextEdge.get(edge)) {
            cnt++;
        }
        return cnt;
    }

    /**
     * Returns an iterator over the edges of a specified vertex.
     *
     * @param vertex the vertex.
     *
     * @return the iterator over the edges of the vertex.
     */
    public RowIterator edgeIterator(int vertex) {
        return new TableIterator(this.vertexFirstEdge.get(vertex), getRowCount()) {
                public int nextRow() {
                    int ret = this.row;
                    this.row = DefaultGraph.this.nextEdge.get(this.row);
                    return ret;
                }

                /**
                 * @see infovis.Table.Iterator#hasNext()
                 */
                public boolean hasNext() {
                    return this.row != Graph.NIL;
                }
            };
    }
    
    public RowIterator vertexIterator() {
        return getVertexTable().iterator();
    }

    /**
     * Returns the edgeTable.
     * @return DefaultTable
     */
    public Table getEdgeTable() {
        return this;
    }
    
    public Table getVertexTable() {
        return this.vertexTable;
    }
    
    public Table getOutVertexTable() {
        return this.outVertexTable;
    }

    
}
