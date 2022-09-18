/*
 * $Id: JConstraintAdjacencyMatrix.java,v 1.3 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */

package fr.emn.oadymppac.widgets;

import salvo.jesus.graph.Graph;
import salvo.jesus.graph.GraphAddEdgeEvent;
import salvo.jesus.graph.GraphAddVertexEvent;
import salvo.jesus.graph.GraphListener;
import salvo.jesus.graph.GraphRemoveEdgeEvent;
import salvo.jesus.graph.GraphRemoveVertexEvent;
import salvo.jesus.graph.Vertex;
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.event.ConstraintChangedEvent;
import fr.emn.oadymppac.event.ConstraintChangedListener;
import fr.emn.oadymppac.event.NewVariableEvent;
import fr.emn.oadymppac.event.NewVariableListener;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Ecole des Mines de Nantes</p>
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 */

public class JConstraintAdjacencyMatrix extends JAdjacencyMatrix implements
		NewVariableListener, ConstraintChangedListener, GraphListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6563249034105335913L;

	public JConstraintAdjacencyMatrix(final Graph g) {
		super(g);
	}

	public void activateConstraint(final Constraint c) {
	}

	/**
	 * This method adds the constraint as a column in the matrix, adds the given
	 * constraint as a vertex of the graph and links it to the related variables,
	 * previously inserted in the graph. It also adds an entry in
	 * <code>nameVertex</code>.
	 */
	public void addConstraint(final Constraint c) {
		final String name = c.getCName();
		this.addColumn(name);
		final Vertex vertex = this.findVertex(name);
		synchronized (this.graph) {
			for (int i = 0; i < c.getVariableCount(); i++) {
				final Vertex other = this.getVertex(c.getVariableAt(i));
				if (other != null) {
					try {
						this.graph.addEdge(vertex, other);
					}
					catch (final Exception e) {
					}
				}
			}
		}
		this.repaint();
	}

	public void afterEdgeAdded(final GraphAddEdgeEvent e) {
		this.repaint();
	}

	public void afterEdgeRemoved(final GraphRemoveEdgeEvent e) {
		this.repaint();
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#afterVertexAdded(GraphAddVertexEvent)
	 */
	public void afterVertexAdded(final GraphAddVertexEvent event) {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#afterVertexRemoved(GraphRemoveVertexEvent)
	 */
	public void afterVertexRemoved(final GraphRemoveVertexEvent event) {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeEdgeAdded(GraphAddEdgeEvent)
	 */
	public void beforeEdgeAdded(final GraphAddEdgeEvent event) throws Exception {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeEdgeRemoved(GraphRemoveEdgeEvent)
	 */
	public void beforeEdgeRemoved(final GraphRemoveEdgeEvent event)
			throws Exception {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeVertexAdded(GraphAddVertexEvent)
	 */
	public void beforeVertexAdded(final GraphAddVertexEvent event)
			throws Exception {
	}

	/**
	 * @see salvo.jesus.graph.GraphListener#beforeVertexRemoved(GraphRemoveVertexEvent)
	 */
	public void beforeVertexRemoved(final GraphRemoveVertexEvent event)
			throws Exception {
	}

	public void constraintChanged(final ConstraintChangedEvent ev) {
		switch (ev.getAction()) {
			case ConstraintChangedEvent.ADDED :
				this.addConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.REMOVED :
				this.removeConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.ACTIVATED :
				this.activateConstraint(ev.getConstraint());
				break;
			case ConstraintChangedEvent.DEACTIVATED :
				this.deactivateConstraint(ev.getConstraint());
				break;
		}
	}

	public void deactivateConstraint(final Constraint c) {
	}

	/**
	 * This method adds the new variable as a row of the matrix and as a vertex
	 * of the graph. It also adds an entry in <code>nameVertex</code>.
	 */
	public void newVariable(final NewVariableEvent e) {
		final String name = e.getVName();
		System.out.println("adding variable " + name);
		this.addRow(name);
		this.findVertex(name);
		this.repaint();
	}

	public void removeConstraint(final Constraint c) {
		/*String name = c.getCName();
		Vertex vertex = (Vertex)nameVertex.get(name);
		removeColumn(name);
		nameVertex.remove(name);
		synchronized(graph){
		    try {
		        graph.removeEdges(vertex);
		        graph.remove(vertex);
		    } catch (Exception e){}
		}*/
	}

}