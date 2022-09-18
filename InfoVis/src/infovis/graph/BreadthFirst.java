/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/

package infovis.graph;

import infovis.Graph;



/**
 * Breadth first search algorithm for Graphs.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class BreadthFirst {
    protected Graph graph;
    public static final int WHITE = 0;
    public static final int GREY  = 1;
    public static final int BLACK = 2;
    /**
     * Constructor for BreadthFirst.
     */
    public BreadthFirst(Graph graph) {
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
	

}
