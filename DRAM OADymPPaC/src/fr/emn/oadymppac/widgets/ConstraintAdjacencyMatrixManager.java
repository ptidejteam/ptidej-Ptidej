package fr.emn.oadymppac.widgets;

import java.util.HashMap;
import java.util.HashSet;
import javax.swing.BoundedRangeModel;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.VertexImpl;
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.ConstraintManager;
import fr.emn.oadymppac.Explanation;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.event.ConstraintChangedEvent;
import fr.emn.oadymppac.event.ConstraintChangedListener;
import fr.emn.oadymppac.event.NewVariableEvent;
import fr.emn.oadymppac.event.NewVariableListener;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.ReduceListener;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.DirectedEdgeWithHistory;
import fr.emn.oadymppac.graph.EdgeWithHistory;
import fr.emn.oadymppac.graph.NamedGraphDelegator;

/**
 * @author Jean-Daniel Fekete
 * @date 12 sept. 2002
 */
public class ConstraintAdjacencyMatrixManager implements NewVariableListener,
		ConstraintChangedListener, ReduceListener {
	AdjacencyMatrixRowColumn row;
	AdjacencyMatrixRowColumn column;
	NamedGraphDelegator graph;
	int maxWeight;
	BoundedRangeModel maxModel;
	BoundedRangeModel visibleEdgeModel;
	private final HashMap consvar = new HashMap();

	public ConstraintAdjacencyMatrixManager(
		final AdjacencyMatrixRowColumn row,
		final AdjacencyMatrixRowColumn column) {
		this.row = row;
		this.column = column;
		this.graph = row.getGraph();
		this.maxWeight = 0;
	}

	public ConstraintAdjacencyMatrixManager(final NamedGraphDelegator graph) {
		this(new AdjacencyMatrixRowColumn(graph), new AdjacencyMatrixRowColumn(
			graph));
	}

	public synchronized void activateConstraint(final Constraint c, final int n) {
		final String name = c.getCName();
		final Vertex v1 = this.graph.findVertex(name);
		EdgeWithHistory edge;
		final boolean directed =
			this.graph.getUnderlyingGraph() instanceof DirectedGraph;
		for (int i = 0; i < c.getVariableCount(); i++) {
			final String vname = c.getVariableAt(i);
			final Vertex v2 = this.graph.findVertex(vname);
			edge = (EdgeWithHistory) this.graph.getEdge(v1, v2);
			if (edge == null) {
				try {
					edge =
						directed ? new DirectedEdgeWithHistory(v1, v2)
								: new EdgeWithHistory(v1, v2);
					this.graph.addEdge(edge);
				}
				catch (final Exception e) {
					e.printStackTrace();
				}
			}
			edge.add(n);
			this.updateMax(edge.getHistoryLength());
		}
		this.updateVisible();
	}

	/**
	 * This method adds the constraint as a column in the matrix, adds the given
	 * constraint as a vertex of the graph and links it to the related variables,
	 * previously inserted in the graph. It also adds an entry in
	 * <code>nameVertex</code>.
	 */
	public synchronized void addConstraint(final Constraint c, final int n) {
		final String name = c.getCName();
		final VertexImpl vertex = new VertexImpl();
		vertex.setLabel(name);

		synchronized (this.graph) {
			try {
				this.graph.add(vertex);
			}
			catch (final Exception e) {
				return;
			}

			if (this.consvar.get(name) == null) {
				this.consvar.put(name, new HashSet());
			}

			final HashSet vars = (HashSet) this.consvar.get(name);

			this.column.add(name);
			String vname;
			EdgeWithHistory edge;
			final boolean directed =
				this.graph.getUnderlyingGraph() instanceof DirectedGraph;

			for (int i = 0; i < c.getVariableCount(); i++) {
				vars.add(c.getVariableAt(i));
				vname = c.getVariableAt(i);
				final Vertex v = this.graph.findVertex(vname);

				try {
					edge =
						directed ? new DirectedEdgeWithHistory(vertex, v)
								: new EdgeWithHistory(vertex, v);
					edge.add(n);
					this.graph.addEdge(edge);
					this.updateMax(1);
					this.updateVisible();
				}
				catch (final Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	/**
	 * @see fr.emn.oadymppac.event.ConstraintChangedListener#constraintChanged(ConstraintChangedEvent)
	 */
	public synchronized void constraintChanged(final ConstraintChangedEvent ev) {
		switch (ev.getAction()) {
			case ConstraintChangedEvent.ADDED :
				this.addConstraint(ev.getConstraint(), ev.getN());
				break;
			case ConstraintChangedEvent.REMOVED :
				this.removeConstraint(ev.getConstraint(), ev.getN());
				break;
			case ConstraintChangedEvent.ACTIVATED :
				this.activateConstraint(ev.getConstraint(), ev.getN());
				break;
			case ConstraintChangedEvent.DEACTIVATED :
				this.deactivateConstraint(ev.getConstraint(), ev.getN());
				break;
		}
	}

	public synchronized void deactivateConstraint(
		final Constraint c,
		final int n) {
		this.activateConstraint(c, n);
	}

	/**
	 * Returns the maxModel.
	 * @return BoundedRangeModel
	 */
	public BoundedRangeModel getMaxModel() {
		return this.maxModel;
	}

	/**
	 * Returns the visibleEdgeModel.
	 * @return BoundedRangeModel
	 */
	public BoundedRangeModel getVisibleEdgeModel() {
		return this.visibleEdgeModel;
	}

	/**
	 * @see fr.emn.oadymppac.event.NewVariableListener#newVariable(NewVariableEvent)
	 */
	public synchronized void newVariable(final NewVariableEvent e) {
		final String name = e.getVName();
		this.row.add(name);
		final VertexImpl vertex = new VertexImpl();
		vertex.setLabel(name);
		try {
			this.graph.add(vertex);
		}
		catch (final Exception ev) {
		}
	}

	/**
	 * We link each constraint with the variables of 
	 * its fellow constraints appearing in the same
	 * explanation.
	 * 
	 * @see fr.emn.oadymppac.event.ReduceListener#reduce(fr.emn.oadymppac.event.ReduceEvent)
	 */
	public synchronized void reduce(final ReduceEvent event) {
		final Explanation exp = event.getExplanation();

		if (exp != null) {
			final String[] clist = exp.getConstraintList();
			String[] vlist;
			EdgeWithHistory edge;
			HashSet vars;
			final boolean directed =
				this.graph.getUnderlyingGraph() instanceof DirectedGraph;
			if (clist.length > 0) {
				for (int i = 0; i < clist.length; i++) {
					if (this.consvar.get(clist[i]) == null) {
						this.consvar.put(clist[i], new HashSet());
					}

					vars = (HashSet) this.consvar.get(clist[i]);

					vlist = new String[vars.size()];
					vlist = (String[]) vars.toArray(vlist);

					for (int j = 0; j < clist.length; j++) {
						if (j == i) {
							continue;
						}
						for (int k = 0; k < vlist.length; k++) {
							edge =
								(EdgeWithHistory) this.graph.getEdge(
									this.graph.findVertex(clist[j]),
									this.graph.findVertex(vlist[k]));
							if (edge == null) {
								try {
									edge =
										directed ? new DirectedEdgeWithHistory(
											this.graph.findVertex(clist[j]),
											this.graph.findVertex(vlist[k]))
												: new EdgeWithHistory(
													this.graph.findVertex(clist[j]),
													this.graph
														.findVertex(vlist[k]));
									this.graph.addEdge(edge);
								}
								catch (final Exception e) {
									e.printStackTrace();
								}
							}

							edge.add(event.getN());
							this.updateMax(edge.getHistoryLength());
						}
					}
				}
				this.updateVisible();
			}
		}
	}
	public void register(final Solver solver) {
		//this.solver = solver;
		final ConstraintManager mgr =
			ConstraintManager.getConstraintManager(solver);
		solver.addNewVariableListener(this, Solver.FILTER_NONE);
		solver.addReduceListener(this, Solver.FILTER_NONE);
		mgr.addConstraintChangedListener(this);
	}

	public synchronized void removeConstraint(final Constraint c, final int n) {
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

	/**
	 * Sets the maxModel.
	 * @param maxModel The maxModel to set
	 */
	public void setMaxModel(final BoundedRangeModel maxModel) {
		this.maxModel = maxModel;
	}

	/**
	 * Sets the visibleEdgeModel.
	 * @param visibleEdgeModel The visibleEdgeModel to set
	 */
	public void setVisibleEdgeModel(final BoundedRangeModel visibleEdgeModel) {
		this.visibleEdgeModel = visibleEdgeModel;
	}

	public void updateMax(final int m) {
		if (this.maxWeight < m) {
			this.maxWeight = m;
			if (this.maxModel != null) {
				this.maxModel.setMaximum(m);
			}
		}
	}

	public void updateVisible() {
		if (this.visibleEdgeModel != null) {
			this.visibleEdgeModel.setMaximum(this.graph.getEdgesCount());
		}
	}

}
