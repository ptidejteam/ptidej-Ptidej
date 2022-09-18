package fr.emn.oadymppac.graph.clustering;

import java.util.Iterator;
import java.util.Vector;
import javax.swing.tree.TreeModel;
import salvo.jesus.graph.Vertex;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.2 $
 *
 * A <code>DefaultMatrixClusterizer</code> performs permutations 
 * on the rows and columns of an adjacency matrix in order 
 * to reveal hidden clusters in the underlying graph.
 */
public class DefaultMatrixClusterizer extends AbstractMatrixClusterizer {

	public static final int SMALLER_FIRST = 0;
	public static final int BIGGER_FIRST = 1;
	public static final int RANDOM_ORDER = 2;
	public static final int FIRST_VERTEX_ORDER = 3;
	public static int DEFAULT_ORDER = DefaultMatrixClusterizer.RANDOM_ORDER;

	public static final boolean DEFAULT_TREE_EXPORT = false;

	int order = DefaultMatrixClusterizer.DEFAULT_ORDER;
	int pivotIndex = 0;
	boolean animationStopped = true;

	StateNode currentCluster1 = new StateNode("cluster"
			+ this.getHorizontalRoot().getChildCount());

	StateNode currentCluster2 = new StateNode("cluster"
			+ this.getVerticalRoot().getChildCount());

	/**
	 * Constructor for DefaultMatrixClusterizer.
	 */
	public DefaultMatrixClusterizer(
		final AdjacencyMatrixRowColumn row,
		final AdjacencyMatrixRowColumn column) {
		super(row, column);
	}

	/**
	 * Constructor for DefaultMatrixClusterizer.
	 */
	public DefaultMatrixClusterizer(
		final AdjacencyMatrixRowColumn row,
		final AdjacencyMatrixRowColumn column,
		final Metric m,
		final TreeModel t1,
		final TreeModel t2) {
		super(row, column, m, t1, t2);
	}

	/**
	 * This method clusters the matrix with bigger clusters first.
	 */
	void biggerFirst() {
		int tmp =
			this.findNextPivot(
				this.column,
				this.pivotIndex,
				DefaultMatrixClusterizer.BIGGER_FIRST);
		while (this.pivotIndex < this.column.getVertices().size()) {
			this.column.swap(this.pivotIndex, tmp);
			this.pivotIndex +=
				this.groupNeighbors(this.column, this.row, this.pivotIndex);
			if (this.pivotIndex < this.column.getVertices().size()) {
				tmp =
					this.findNextPivot(
						this.column,
						this.pivotIndex,
						DefaultMatrixClusterizer.BIGGER_FIRST);
			}
		}
	}

	/**
	 * This method clusterizes the matrix according to the given order.
	 * Possible orders are <code>SMALLER_FIRST</code> and <code>BIGGER_FIRST</code>.
	 */
	public void doClusterize() {
		this.currentCluster1 =
			new StateNode("cluster" + this.getHorizontalRoot().getChildCount());
		this.currentCluster1 =
			new StateNode("cluster" + this.getVerticalRoot().getChildCount());
		switch (this.order) {
			case SMALLER_FIRST :
				this.smallerFirst();
				break;
			case BIGGER_FIRST :
				this.biggerFirst();
				break;
			case RANDOM_ORDER :
				this.randomOrder();
				break;
			case FIRST_VERTEX_ORDER :
				this.firstVertexOrder();
				break;
			default :
				this.smallerFirst();
		}
		this.pivotIndex = 0;
	}

	/**
	 * This method returns any further vertex at random.
	 */
	int findAnyPivot(final AdjacencyMatrixRowColumn rc, final int beginIndex) {
		return (int) (Math.random() * (rc.getCount() - beginIndex - 1))
				+ beginIndex;
	}

	/**
	 * This method returns the index of the biggest row 
	 * or column in the given submatrix.
	 */
	int findBiggestPivot(final AdjacencyMatrixRowColumn rc, final int beginIndex) {
		final Vector vertices = rc.getVertices();
		final NamedGraphDelegator graph = rc.getGraph();
		String name = rc.getName(beginIndex);
		int result = beginIndex;
		int size = graph.getAdjacentVertices(graph.findVertex(name)).size();
		final Iterator iter =
			vertices.subList(beginIndex, vertices.size() - 1).iterator();
		while (iter.hasNext()) {
			name = (String) iter.next();
			if (graph.getAdjacentVertices(graph.findVertex(name)).size() > size) {
				size = graph.getAdjacentVertices(graph.findVertex(name)).size();
				result = rc.indexOf(name);
			}
		}
		return result;
	}
	/**
	 * This method returns <code>beginIndex</code> since 
	 * it could be as suitable as any further vertex taken at random.
	 */
	int findFirstPivot(final AdjacencyMatrixRowColumn rc, final int beginIndex) {
		return beginIndex;
	}

	/**
	 * This method returns the index of the columns that qualifies to be next pivot.
	 */
	int findNextPivot(
		final AdjacencyMatrixRowColumn rc,
		final int beginIndex,
		final int order) {
		int index = beginIndex;
		switch (order) {
			case SMALLER_FIRST :
				index = this.findSmallestPivot(rc, beginIndex);
				break;
			case BIGGER_FIRST :
				index = this.findBiggestPivot(rc, beginIndex);
				break;
			case RANDOM_ORDER :
				index = this.findAnyPivot(rc, beginIndex);
				break;
			case FIRST_VERTEX_ORDER :
				index = this.findFirstPivot(rc, beginIndex);
				break;
			default :
				index = this.findAnyPivot(rc, beginIndex);
		}
		return index;
	}

	/**
	 * This method returns the index of the smallest row 
	 * or column in the given submatrix.
	 */
	int findSmallestPivot(
		final AdjacencyMatrixRowColumn rc,
		final int beginIndex) {
		final Vector vertices = rc.getVertices();
		final NamedGraphDelegator graph = rc.getGraph();
		String name = rc.getName(beginIndex);
		int result = beginIndex;
		int size = graph.getAdjacentVertices(graph.findVertex(name)).size();
		final Iterator iter =
			vertices.subList(beginIndex, vertices.size() - 1).iterator();
		while (iter.hasNext()) {
			name = (String) iter.next();
			if (graph.getAdjacentVertices(graph.findVertex(name)).size() < size) {
				result = rc.indexOf(name);
				size = graph.getAdjacentVertices(graph.findVertex(name)).size();
			}
		}
		return result;
	}

	/**
	 * This method picks the next available pivot.
	 */
	void firstVertexOrder() {
		int tmp =
			this.findNextPivot(
				this.column,
				this.pivotIndex,
				DefaultMatrixClusterizer.FIRST_VERTEX_ORDER);
		while (this.pivotIndex < this.column.getVertices().size()) {
			this.column.swap(this.pivotIndex, tmp);
			this.pivotIndex +=
				this.groupNeighbors(this.column, this.row, this.pivotIndex);
			if (this.pivotIndex < this.column.getVertices().size()) {
				tmp =
					this.findNextPivot(
						this.column,
						this.pivotIndex,
						DefaultMatrixClusterizer.FIRST_VERTEX_ORDER);
			}
		}
	}

	/**
	 * This method groups the neighbors of the chosen pivot 
	 * and returns their count.
	 */
	int groupNeighbors(
		final AdjacencyMatrixRowColumn rc1,
		final AdjacencyMatrixRowColumn rc2,
		final int beginIndex) {
		if (beginIndex > rc1.getCount() - 1) {
			return 0;
		}

		if (this.currentCluster1.getChildCount() > 0) {
			this.currentCluster1 =
				new StateNode("cluster"
						+ this.getHorizontalRoot().getChildCount());
		}

		if (this.currentCluster2.getChildCount() > 0) {
			this.currentCluster2 =
				new StateNode("cluster"
						+ this.getVerticalRoot().getChildCount());
		}

		Iterator iter =
			rc1.getVertices().subList(beginIndex, rc1.getCount()).iterator();
		int count = 0;
		String vname;
		int shift = 0;
		while (iter.hasNext()) {
			vname = (String) iter.next();
			if (this.distance(rc1, beginIndex, rc1.indexOf(vname)) < this.tolerance) {
				rc1.swap(rc1.indexOf(vname), beginIndex + count);
				shift =
					Math.max(
						shift,
						this.shiftToDiagonal(rc1, rc2, beginIndex + count));
				count++;
			}
		}

		// TODO : check what happens with non square matrices...
		if (shift > count) {
			// the cluster is higher than wide : more vertices should be added horizontally
			iter =
				rc1
					.getVertices()
					.subList(
						beginIndex,
						Math.min(beginIndex + shift, rc1.getCount()))
					.iterator();
			while (iter.hasNext()) {
				this.currentCluster1.add(new StateNode(iter.next()));
			}

			iter =
				rc2
					.getVertices()
					.subList(
						beginIndex,
						Math.min(beginIndex + shift, rc2.getCount()))
					.iterator();
			while (iter.hasNext()) {
				this.currentCluster2.add(new StateNode(iter.next()));
			}
		}
		else if (count > 0) {
			iter =
				rc1
					.getVertices()
					.subList(
						beginIndex,
						Math.min(beginIndex + count, rc1.getCount()))
					.iterator();
			while (iter.hasNext()) {
				this.currentCluster1.add(new StateNode(iter.next()));
			}

			// the cluster is wider than high : more vertices should be added vertically
			iter =
				rc2
					.getVertices()
					.subList(
						beginIndex,
						Math.min(beginIndex + count, rc2.getCount()))
					.iterator();
			while (iter.hasNext()) {
				this.currentCluster2.add(new StateNode(iter.next()));
			}
		}

		if (this.currentCluster1.getChildCount() > 0) {
			this.currentCluster1.setExpanded(true);
			((StateNode) this.getHorizontalRoot()).add(this.currentCluster1);
		}

		if (this.currentCluster2.getChildCount() > 0) {
			this.currentCluster2.setExpanded(true);
			((StateNode) this.getVerticalRoot()).add(this.currentCluster2);
		}

		// clusters may be of better quality if we took the min.
		return Math.max(count, shift);
	}

	/**
	 * This method clusters the matrix putting the clusters at random.
	 */
	void randomOrder() {
		int tmp =
			this.findNextPivot(
				this.column,
				this.pivotIndex,
				DefaultMatrixClusterizer.RANDOM_ORDER);
		while (this.pivotIndex < this.column.getVertices().size()) {
			this.column.swap(this.pivotIndex, tmp);
			this.pivotIndex +=
				this.groupNeighbors(this.column, this.row, this.pivotIndex);
			if (this.pivotIndex < this.column.getVertices().size()) {
				tmp =
					this.findNextPivot(
						this.column,
						this.pivotIndex,
						DefaultMatrixClusterizer.RANDOM_ORDER);
			}
		}
	}

	/**
	 * Sets the order of the clusters.
	 */
	public void setOrder(final int o) {
		this.order = o;
	}

	/**
	 * This method shifts the rows of the matrix so that the "ones" 
	 * in the column at <code>index</code> are accross the diagonal of 
	 * the matrix. The rows involved in previous clusters are left aside.
	 */
	int shiftToDiagonal(
		final AdjacencyMatrixRowColumn rc1,
		final AdjacencyMatrixRowColumn rc2,
		final int index) {
		String vname;
		int count = 0;
		int index1 = 0;
		int index2 = 0;
		final Iterator iter =
			rc1
				.getGraph()
				.getAdjacentVertices(
					rc1.getGraph().getVertex(rc1.getName(index)))
				.iterator();
		while (iter.hasNext()) {
			vname = ((Vertex) iter.next()).getLabel();
			index1 = rc1.indexOf(vname);
			index2 = rc2.indexOf(vname);
			if (index2 > this.pivotIndex + count) {
				count++;
				rc2.swap(index2, this.pivotIndex + count);
				if (index1 > this.pivotIndex + count) {
					rc1.swap(index1, this.pivotIndex + count);
				}
			}
		}

		return count;
	}

	/**
	 * This method clusters the matrix with smaller clusters first.
	 */
	void smallerFirst() {
		int tmp =
			this.findNextPivot(
				this.column,
				this.pivotIndex,
				DefaultMatrixClusterizer.SMALLER_FIRST);
		while (this.pivotIndex < this.column.getVertices().size()) {
			this.column.swap(this.pivotIndex, tmp);
			this.pivotIndex +=
				this.groupNeighbors(this.column, this.row, this.pivotIndex);
			if (this.pivotIndex < this.column.getVertices().size()) {
				tmp =
					this.findNextPivot(
						this.column,
						this.pivotIndex,
						DefaultMatrixClusterizer.SMALLER_FIRST);
			}
		}
	}
}