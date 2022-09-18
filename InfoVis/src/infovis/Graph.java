/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis;

import infovis.utils.RowIterator;


/**
 * A Graph class for Information Visualization.
 *
 * A Graph references one or two vertex tables and is an edge table.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public interface Graph extends Table {
    public static final int NIL = Table.NIL;
    /** Metadata key for the graph type */
    public static final String GRAPH_TYPE = "GRAPH_TYPE";
    /** Metadata value for a directed graph */
    public static final String GRAPH_TYPE_DIRECTED = "directed";
    /** Metadata value for an undirected graph */
    public static final String GRAPH_TYPE_UNDIRECTED = "undirected";
    /** Name of the column containing the index of the first out edge. */
    public static final String FIRSTEDGE_COLUMN = "#FirstEdge";
    /** Name of the column containing the index of the last out edge. */
    public static final String LASTEDGE_COLUMN = "#LastEdge";
    /** Name of the column containing the "in" vertex in the edge table. */
    public static final String INVERTEX_COLUMN = "#InVertex";
    /** Name of the column containing the "out" vertex in the edge table. */
    public static final String OUTVERTEX_COLUMN = "#OutVertex";
    /** Name of the column containing the next edge in the edge table. */
    public static final String NEXTEDGE_COLUMN = "#NextEdge";
    /** The Metadata key of the out vertex table in the underlying table. */
    public static final String OUT_VERTEX_TABLE_METADATA = "OUT_VERTEX_TABLE_METADATA";
    /** The Metadata key of the in vertex table in the underlying table. */
    public static final String IN_VERTEX_TABLE_METADATA = "IN_VERTEX_TABLE_METADATA";
    /** The Metadata key of the vertex table in the underlying table. */
    public static final String VERTEX_TABLE_METADATA = IN_VERTEX_TABLE_METADATA;
    /** Name of the column containing the index of the first int edge. */
    public static final String OUT_FIRSTEDGE_COLUMN = "#outFirstEdge";
    /** Name of the column containing the index of the last out edge. */
    public static final String OUT_LASTEDGE_COLUMN = "#outLastEdge";
    /** Name of the column containing the next out edge in the edge table. */
    public static final String OUT_NEXTEDGE_COLUMN = "#outNextEdge";

    /**
     * Returns true if the graph is directed.
     * 
     * @return true if the graph is directed.
     */
    public boolean isDirected();
    
    /**
     * Sets the graph to directed or undirected.
     * 
     * @param directed boolean specifying whether the graph is directed or not.
     */
    public void setDirected(boolean directed);

    /**
     * Returns the number of vertices in the graph
     *
     * @return        The number of vertices in the graph.
     */
    public int getVerticesCount();
    public int getOutVerticesCount();

    /**
     * Adds a new "in" vertex to the graph.
     *
     * @return the "in" vertex number.
     */
    public int addVertex();
    
    /**
     * Adds a new "out" vertex to the graph.
     *
     * @return the "out" vertex number.
     */
    public int addOutVertex();

    /**
     * Returns the number of edges in the graph.
     *
     * @return the number of edges in the graph.
     */
    public int getEdgesCount();

    /**
     * Adds a new edge between two vertices.
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     *
     * @return the new edge index.
     */
    public int addEdge(int v1, int v2);

    /**
     * Returns the "in" vertex of an edge.
     *
     * @param edge the edge.
     *
     * @return the "in" vertex of an edge or NIL.
     */
    public int getInVertex(int edge);

    /**
     * Returns the "out" vertex of an edge.
     *
     * @param edge the edge.
     *
     * @return the "out" vertex of an edge.
     */
    public int getOutVertex(int edge);

    /**
     * Returns the first edge of a specified "in" vertex.
     *
     * @param vertex the vertex.
     *
     * @return the first edge of the specified "in" vertex or NIL if none exists.
     */
    public int getFirstEdge(int vertex);

    /**
     * Returns the last edge of a specified "in" vertex.
     *
     * @param vertex the vertex.
     *
     * @return the last edge of the specified "in" vertex or NIL if none exists.
     */
    public int getLastEdge(int vertex);

    /**
     * Returns the edge following a specified edge starting at a vertex.
     *
     * @param edge the edge.
     *
     * @return the edge following a given edge starting at the vertex
     * 	or NIL if the specified edge is the last of the "in" vertex.
     */
    public int getNextEdge(int edge);

    /**
     * Returns an edge between two specified vertices.
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     *
     * @return an edge between two specified vertices
     * 	or NIL if none exists.
     */
    public int getEdge(int v1, int v2);

    /**
     * Returns an edge between two specified vertices.
     *
     * @param v1 the first vertex.
     * @param v2 the second vertex.
     *
     * @return an edge between two specified vertices
     * 	creating one if none exists.
     */
    public int findEdge(int v1, int v2);

    /**
     * Returns the degree of the vertex, which is simply the number of edges
     * of the vertex.
     *
     * @param vertex the vertex.
     * @return  The degree of the vertex.
     */
    public int getDegree(int vertex);
    
    public int getOutDegree(int vertex);

    /**
     * Returns an iterator over the edges of a specified vertex.
     *
     * @param vertex the vertex.
     *
     * @return the iterator over the edges of the vertex.
     */
    public RowIterator edgeIterator(int vertex);
    
    public RowIterator vertexIterator();
    /**
     * Returns the edgeTable.
     * @return DefaultTable
     */
    public Table getEdgeTable();
    
    /**
     * Returns the vertex Table 
     * @return the vertex Table 
     */
    public Table getVertexTable();
    
    public Table getOutVertexTable();
}
