package fr.emn.oadymppac.widgets;

import java.util.HashMap;
import java.util.HashSet;
import javax.swing.BoundedRangeModel;
import salvo.jesus.graph.DirectedGraph;
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
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 *
 * This class manages a network of variables 
 * as a means of monitoring explanations. Variables belonging to
 * common constraints are linked to each other. When an explanation
 * states two or more constraints, all related variables
 * are linked together if necessary and the link's history is updated.
 * Thus we can assess which pairs of variables interact more 
 * often.
 */
public class ExplanationVariableAdjacencyMatrixManager implements
		NewVariableListener, ReduceListener, ConstraintChangedListener {

	AdjacencyMatrixRowColumn row;
	AdjacencyMatrixRowColumn column;
	NamedGraphDelegator graph;
	int maxWeight;
	BoundedRangeModel maxModel;
	BoundedRangeModel visibleEdgeModel;
	private final HashMap consvar = new HashMap();

	public ExplanationVariableAdjacencyMatrixManager(
		final AdjacencyMatrixRowColumn row,
		final AdjacencyMatrixRowColumn column) {
		this.row = row;
		this.column = column;
		this.graph = row.getGraph();
	}

	public ExplanationVariableAdjacencyMatrixManager(
		final NamedGraphDelegator graph) {
		this(new AdjacencyMatrixRowColumn(graph), new AdjacencyMatrixRowColumn(
			graph));
	}

	/**
	 * This method adds the passed constraint to the manager
	 * and updates the related vertices in the graph.
	 * 
	 * @param c
	 * @param n
	 */
	public void addConstraint(final Constraint c, final int n) {
		final String cname = c.getCName();
		if (this.consvar.get(cname) == null) {
			this.consvar.put(cname, new HashSet());
		}
		final HashSet vars = (HashSet) this.consvar.get(cname);
		for (int i = 0; i < c.getVariableCount(); i++) {
			vars.add(c.getVariableAt(i));
		}
		this.updateConstraint(cname, n);
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

	/**
	 * When a new variable is declared, the corresponding 
	 * vertex is added in the graph.
	 * 
	 * @see fr.emn.oadymppac.event.NewVariableListener#newVariable(NewVariableEvent)
	 */
	public void newVariable(final NewVariableEvent event) {
		final String vname = event.getVName();
		final VertexImpl vertex = new VertexImpl();
		vertex.setLabel(vname);
		synchronized (this.graph) {
			try {
				this.graph.add(vertex);
			}
			catch (final Exception e) {
				return;
			}
			this.column.add(vname);
			this.row.add(vname);
		}
	}

	/**
	 * When a <code>ReduceEvent</code> is received,
	 * the constraints involved in the associated
	 * explanation are updated i.e. their respective 
	 * variables are cross-linked.
	 * 
	 * @see fr.emn.oadymppac.event.ReduceListener#reduce(ReduceEvent)
	 */
	public void reduce(final ReduceEvent event) {
		final Explanation exp = event.getExplanation();
		final String[] clist = exp.getConstraintList();
		for (int i = 0; i < clist.length; i++) {
			for (int j = i; j < clist.length; j++) {
				this.updateConstraint(clist[i], clist[j], event.getN());
			}
		}
	}

	public void register(final Solver solver) {
		final ConstraintManager mgr =
			ConstraintManager.getConstraintManager(solver);
		mgr.addConstraintChangedListener(this);
		solver.addNewVariableListener(this, Solver.FILTER_NONE);
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

	/**
	 * This method links together the variables belonging
	 * to the same constraint i.e. the variables linked
	 * together in the modelisation of the problem.
	 * 
	 * @param cname
	 * @param n
	 */
	public synchronized void updateConstraint(final String cname, final int n) {
		if (this.consvar.get(cname) == null) {
			this.consvar.put(cname, new HashSet());
		}
		final HashSet vars = (HashSet) this.consvar.get(cname);

		String[] vlist = new String[vars.size()];
		vlist = (String[]) vars.toArray(vlist);

		EdgeWithHistory edge;
		final boolean directed =
			this.graph.getUnderlyingGraph() instanceof DirectedGraph;
		for (int i = 0; i < vlist.length; i++) {
			for (int j = i; j < vlist.length; j++) {
				edge =
					(EdgeWithHistory) this.graph.getEdge(
						this.graph.getVertex(vlist[i]),
						this.graph.getVertex(vlist[j]));
				if (edge == null) {
					try {
						edge =
							directed ? new DirectedEdgeWithHistory(
								this.graph.findVertex(vlist[i]),
								this.graph.findVertex(vlist[j]))
									: new EdgeWithHistory(
										this.graph.findVertex(vlist[i]),
										this.graph.findVertex(vlist[j]));
						this.graph.addEdge(edge);
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
				}
				edge.add(n);
				this.updateMax(edge.getHistoryLength());
			}
		}
		this.updateVisible();
	}

	/**
	 * This method cross-links the variables of two
	 * constraints appearing in the same explanation.
	 * 
	 * @param cname
	 * @param othercons
	 * @param n
	 */
	public synchronized void updateConstraint(
		final String cname,
		final String othercons,
		final int n) {
		if (this.consvar.get(cname) == null) {
			this.consvar.put(cname, new HashSet());
		}
		if (this.consvar.get(othercons) == null) {
			this.consvar.put(othercons, new HashSet());
		}

		final HashSet vars1 = (HashSet) this.consvar.get(cname);
		final HashSet vars2 = (HashSet) this.consvar.get(othercons);

		String[] vlist = new String[vars1.size()];
		vlist = (String[]) vars1.toArray(vlist);

		String[] vlist2 = new String[vars2.size()];
		vlist2 = (String[]) vars2.toArray(vlist2);

		EdgeWithHistory edge;
		final boolean directed =
			this.graph.getUnderlyingGraph() instanceof DirectedGraph;
		for (int i = 0; i < vlist.length; i++) {
			for (int j = 0; j < vlist2.length; j++) {
				edge =
					(EdgeWithHistory) this.graph.getEdge(
						this.graph.getVertex(vlist[i]),
						this.graph.getVertex(vlist2[j]));
				if (edge == null) {
					try {
						edge =
							directed ? new DirectedEdgeWithHistory(
								this.graph.findVertex(vlist[i]),
								this.graph.findVertex(vlist2[j]))
									: new EdgeWithHistory(
										this.graph.findVertex(vlist[i]),
										this.graph.findVertex(vlist2[j]));
						this.graph.addEdge(edge);
					}
					catch (final Exception e) {
						e.printStackTrace();
					}
				}
				edge.add(n);
				this.updateMax(edge.getHistoryLength());
			}
		}
		this.updateVisible();
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
