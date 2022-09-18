package fr.emn.oadymppac.widgets;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.BoundedRangeModel;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.VertexImpl;
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.ConstraintManager;
import fr.emn.oadymppac.Explanation;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.event.ConstraintChangedEvent;
import fr.emn.oadymppac.event.ConstraintChangedListener;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.ReduceListener;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.DirectedEdgeWithHistory;
import fr.emn.oadymppac.graph.EdgeWithHistory;
import fr.emn.oadymppac.graph.NamedGraphDelegator;

/**
 * @author Mohammad Ghoniem
 *
 * This class manages a network of constraints 
 * as a means of monitoring explanations. Constraints with
 * common variables are linked to each other. When an explanation
 * states two or more constraints, all possible pairs of constraints
 * are linked together if necessary and the link's history is updated.
 * Thus we can assess which pairs of constraints appear together more 
 * often.
 */
public class ExplanationAdjacencyMatrixManager implements
		ConstraintChangedListener, ReduceListener {

	AdjacencyMatrixRowColumn row;
	AdjacencyMatrixRowColumn column;
	NamedGraphDelegator graph;
	int maxWeight;
	BoundedRangeModel maxModel;
	BoundedRangeModel visibleEdgeModel;
	private final HashMap varcons = new HashMap();

	/**
	 * Constructor for ExplanationAdjacencyMatrixManager.
	 */
	public ExplanationAdjacencyMatrixManager(
		final AdjacencyMatrixRowColumn row,
		final AdjacencyMatrixRowColumn column) {
		this.row = row;
		this.column = column;
		this.graph = row.getGraph();
	}

	public ExplanationAdjacencyMatrixManager(final NamedGraphDelegator graph) {
		this(new AdjacencyMatrixRowColumn(graph), new AdjacencyMatrixRowColumn(
			graph));
	}

	/**
	 * Adds the passed constraint to the graph.
	 */
	public synchronized void addConstraint(final Constraint c, final int n) {
		final String cname = c.getCName();
		final VertexImpl vertex = new VertexImpl();
		vertex.setLabel(cname);
		synchronized (this.graph) {
			try {
				this.graph.add(vertex);
			}
			catch (final Exception e) {
				return;
			}
			this.column.add(cname);
			this.row.add(cname);
			for (int i = 0; i < c.getVariableCount(); i++) {
				this.addConstraint(c.getVariableAt(i), cname, n);
			}
		}
	}

	/**
	 * Adds edges between the passed constraint and 
	 * the other constraints related to the passed variable.
	 */
	private synchronized void addConstraint(
		final String vname,
		final String cname,
		final int n) {
		if (this.varcons.get(vname) == null) {
			this.varcons.put(vname, new HashSet());
		}
		final HashSet cons = (HashSet) this.varcons.get(vname);
		final Iterator iter = cons.iterator();
		final boolean directed =
			this.graph.getUnderlyingGraph() instanceof DirectedGraph;
		EdgeWithHistory edge;
		while (iter.hasNext()) {
			try {
				edge =
					directed ? new DirectedEdgeWithHistory(
						this.graph.getVertex(cname),
						this.graph.getVertex((String) iter.next()))
							: new EdgeWithHistory(
								this.graph.getVertex(cname),
								this.graph.getVertex((String) iter.next()));
				edge.add(n);
				this.graph.addEdge(edge);
				this.updateMax(1);
				this.updateVisible();
			}
			catch (final Exception e) {
				e.printStackTrace();
			}

		}
		cons.add(cname);
	}

	/**
	 * @see fr.emn.oadymppac.event.ConstraintChangedListener#constraintChanged(ConstraintChangedEvent)
	 */
	public void constraintChanged(final ConstraintChangedEvent ev) {
		switch (ev.getAction()) {
			case ConstraintChangedEvent.ADDED :
				this.addConstraint(ev.getConstraint(), ev.getN());
				break;
			case ConstraintChangedEvent.REMOVED :
				this.removeConstraint(ev.getConstraint(), ev.getN());
				break;
		}
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

	//	/**
	//	 * Removes the passed constraint from the set of constraints
	//	 * involved with vname.
	//	 */
	//	private void removeConstraint(String vname, String cname){
	//		if (varcons.get(vname)==null)
	//			return;
	//		
	//		((HashSet)varcons.get(vname)).remove(cname);
	//		if (((HashSet)varcons.get(vname)).size()==0)
	//			varcons.remove(vname);
	//	}

	/**
	 * All the constraints appearing in the same explanation are
	 * linked together.
	 * 
	 * @see fr.emn.oadymppac.event.ReduceListener#reduce(ReduceEvent)
	 */
	public synchronized void reduce(final ReduceEvent event) {
		final Explanation exp = event.getExplanation();
		if (exp != null) {
			final String[] clist = exp.getConstraintList();
			EdgeWithHistory edge;
			final boolean directed =
				this.graph.getUnderlyingGraph() instanceof DirectedGraph;
			if (clist.length > 0) {
				for (int i = 0; i < clist.length; i++) {
					for (int j = i + 1; j < clist.length; j++) {
						edge =
							(EdgeWithHistory) this.graph.getEdge(
								this.graph.findVertex(clist[i]),
								this.graph.findVertex(clist[j]));
						if (edge == null) {
							try {
								edge =
									directed ? new DirectedEdgeWithHistory(
										this.graph.findVertex(clist[i]),
										this.graph.findVertex(clist[j]))
											: new EdgeWithHistory(
												this.graph.findVertex(clist[i]),
												this.graph.findVertex(clist[j]));
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
				this.updateVisible();
			}
		}
	}
	public void register(final Solver solver) {
		final ConstraintManager mgr =
			ConstraintManager.getConstraintManager(solver);
		mgr.addConstraintChangedListener(this);
		solver.addReduceListener(this, Solver.FILTER_NONE);
	}

	public synchronized void removeConstraint(final Constraint c, final int n) {
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
