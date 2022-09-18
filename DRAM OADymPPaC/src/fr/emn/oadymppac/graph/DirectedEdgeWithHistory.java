/*
 * Created on 3 juil. 2003
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package fr.emn.oadymppac.graph;

import salvo.jesus.graph.DirectedEdge;
import salvo.jesus.graph.Vertex;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 *
 * This class implements directed edges with history.
 */
public class DirectedEdgeWithHistory extends EdgeWithHistory implements
		DirectedEdge {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8394013376293131031L;

	/**
	 * @param a
	 * @param b
	 */
	public DirectedEdgeWithHistory(final Vertex a, final Vertex b) {
		super(a, b);
	}

	/**
	 * @see salvo.jesus.graph.DirectedEdge#getDirection()
	 */
	public int getDirection() {
		return DirectedEdge.DIRECTION_A_TO_B;
	}

	/**
	 * @see salvo.jesus.graph.DirectedEdge#getSink()
	 */
	public Vertex getSink() {
		return this.getVertexB();
	}

	/**
	 * @see salvo.jesus.graph.DirectedEdge#getSource()
	 */
	public Vertex getSource() {
		return this.getVertexA();
	}

}
