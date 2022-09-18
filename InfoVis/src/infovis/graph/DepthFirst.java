/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph;

import infovis.Graph;
import infovis.column.IntColumn;

/**
 * Depth first search algorithm for Graphs.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class DepthFirst {
    protected Graph graph;
    public static final int WHITE = 0;
    public static final int GREY  = 1;
    public static final int BLACK = 2;
    /**
     * Constructor for DepthFirst.
     */
    public DepthFirst(Graph graph) {
	this.graph = graph;
    }
	
    public interface Visitor {
	public void start(int vertex);
	public void discover(int vertex);
	public void examine(int edge);
	public void treeEdge(int edge);
	public void backEdge(int edge);
	public void forwardOrCross(int edge);
	public void finish(int vertex);
    }
	
    public void visit(Visitor visitor, int start, IntColumn colormap) {
	int vertex;
		
	colormap.ensureCapacity(this.graph.getVerticesCount());
		
	for (vertex = 0; vertex < this.graph.getVerticesCount(); vertex++) {
	    colormap.set(vertex, WHITE);
	}
	if (start != Graph.NIL) {
	    visitor.start(start);
	    visitDfs(visitor, start, colormap);
	}
	for (vertex = 0; vertex < this.graph.getVerticesCount(); vertex++) {
	    if (colormap.get(vertex) == WHITE) {
		visitor.start(vertex);
		visitDfs(visitor, vertex, colormap);
	    }
	}
    }

    void visitDfs(Visitor visitor, int vertex, IntColumn colormap) {
	colormap.set(vertex, GREY);
	for (int edge = this.graph.getFirstEdge(vertex);
	     edge != Graph.NIL; edge = this.graph.getNextEdge(edge)) {
	    visitor.examine(edge);
	    int v2 = this.graph.getOutVertex(edge);
	    int color = colormap.get(v2);
	    if (color == WHITE) {
		visitor.treeEdge(edge);
		visitDfs(visitor, v2, colormap);
	    }
	    else if(color == GREY) {
		visitor.backEdge(edge);
	    }
	    else { // color == BLACK
		visitor.forwardOrCross(edge);
	    }
	}
	colormap.set(vertex, BLACK);
    }
}
