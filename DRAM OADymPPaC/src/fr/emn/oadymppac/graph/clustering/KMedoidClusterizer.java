package fr.emn.oadymppac.graph.clustering;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Random;
import javax.swing.tree.TreeModel;
import fr.emn.oadymppac.graph.AdjacencyMatrixRowColumn;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This clusterizer works according to the 
 * k-medoid algorithm.
 */
public class KMedoidClusterizer extends AbstractMatrixClusterizer {

	public static final int DEFAULT_K_MEDOIDS = 7;

	int k; // number of medoids required
	float objective; // the value of the objective function
	boolean changePerformed = false; // the termination criterion

	/**
	 * Constructor for KMedoidClusterizer.
	 * @param row
	 * @param column
	 */
	public KMedoidClusterizer(
		final AdjacencyMatrixRowColumn row,
		final AdjacencyMatrixRowColumn column,
		final Metric m,
		final TreeModel t1,
		final TreeModel t2) {
		super(row, column, m, t1, t2);
		this.k = KMedoidClusterizer.DEFAULT_K_MEDOIDS;
	}

	/**
	 * Returns the total distance between the passed node
	 * and the nodes of the passed medoid.
	 */
	float computeDistanceToCluster(final StateNode node, final StateNode medoid) {
		final Enumeration e = medoid.children();
		int total = 0;
		while (e.hasMoreElements()) {
			total +=
				this.metric.distance(this.column, this.column
					.indexOf((String) node.getUserObject()), this.column
					.indexOf((String) ((StateNode) e.nextElement())
						.getUserObject()));
		}
		return total;
	}

	/**
	 * Computes the value of the objective function
	 * i.e. the sum of the distances between every point
	 * and the medoid of the cluster to which it belongs.
	 */
	float computeObjective(
		final AdjacencyMatrixRowColumn rc,
		final StateNode root) {
		StateNode tmpMedoid;
		StateNode tmpChild;
		float localobjective = 0;
		for (int i = 0; i < this.k; i++) {
			tmpMedoid = (StateNode) root.getChildAt(i);
			for (int j = 0; j < tmpMedoid.getChildCount(); j++) {
				tmpChild = (StateNode) tmpMedoid.getChildAt(j);
				localobjective +=
					this.metric.distance(
						rc,
						rc.indexOf((String) tmpMedoid.getUserObject()),
						rc.indexOf((String) tmpChild.getUserObject()));
			}
		}

		return localobjective;
	}

	public void doClusterize() {
		this.initMedoids();
		this.refine();
		this.updateRowColumn(this.column, (StateNode) this.getHorizontalRoot());
		this.updateRowColumn(this.row, (StateNode) this.getVerticalRoot());
	}

	/**
	 * Returns the k.
	 * @return int
	 */
	public int getK() {
		return this.k;
	}

	/**
	 * Returns the value of the medoid at the passed index.
	 */
	String getMedoidAt(final StateNode root, final int index) {
		return (String) ((StateNode) root.getChildAt(index)).getUserObject();
	}

	/**
	 * Returns the objective.
	 * @return float
	 */
	public float getObjective() {
		return this.objective;
	}

	/**
	 * This methods picks k-medoids at random 
	 * to initiate the algorithm and fits the remaining
	 * vertices around the best medoids. 
	 * The refinement process can then start.
	 */
	void initMedoids() {
		this.initMedoids(this.column, (StateNode) this.getHorizontalRoot());
		this.initMedoids(this.row, (StateNode) this.getVerticalRoot());
	}

	void initMedoids(final AdjacencyMatrixRowColumn rc, final StateNode root) {
		System.out.println("Initializing the medoids");
		System.out.println("Required k = " + this.k);
		final Random rnd = new Random();

		for (int i = 0; i < this.k; i++) {
			root.add(new StateNode(rc.getName(rnd.nextInt(rc.getCount()))));
		}

		String current;
		int bestMedoid = 0;
		float d = Float.MAX_VALUE;
		float tmp = d;
		final Iterator iter = rc.getVertices().iterator();
		while (iter.hasNext()) {
			current = (String) iter.next();

			for (int i = 0; i < this.k; i++) {
				tmp =
					this.metric.distance(
						rc,
						rc.indexOf(this.getMedoidAt(root, i)),
						rc.indexOf(current));
				if (d > tmp) {
					d = tmp;
					bestMedoid = i;
				}
			}

			((StateNode) root.getChildAt(bestMedoid))
				.add(new StateNode(current));
		}
	}

	/**
	 * After the k clusters are initialized, this method looks 
	 * for a more appropriate medoid in each cluster. When the 
	 * most suitable medoid is found, the objective function 
	 * is updated accordingly and, if necessary, nodes in the 
	 * other clusters are relocated.
	 */
	void refine() {
		System.out.println("refining on columns");
		this.setObjective(this.computeObjective(
			this.column,
			(StateNode) this.getHorizontalRoot()));
		this.refine(this.column, (StateNode) this.getHorizontalRoot());
		System.out.println("refining on rows");
		this.setObjective(this.computeObjective(
			this.row,
			(StateNode) this.getVerticalRoot()));
		this.refine(this.row, (StateNode) this.getVerticalRoot());
	}

	void refine(final AdjacencyMatrixRowColumn rc, final StateNode root) {
		StateNode tmpMedoid = null;
		StateNode tmpChild;
		float distanceToCluster;
		float newDistance;
		for (int i = 0; i < this.k; i++) {
			tmpMedoid = (StateNode) root.getChildAt(i);
			distanceToCluster =
				this.computeDistanceToCluster(tmpMedoid, tmpMedoid);
			for (int j = 0; j < tmpMedoid.getChildCount(); j++) {
				tmpChild = (StateNode) tmpMedoid.getChildAt(j);
				newDistance =
					this.computeDistanceToCluster(tmpChild, tmpMedoid);
				if (distanceToCluster > newDistance) {
					// change the element taken as medoid 
					// update the objective function
					// and propagate to other clusters
					tmpMedoid.setUserObject(tmpChild.getUserObject());
					this.setObjective(this.objective - distanceToCluster
							+ newDistance);
					this.changePerformed = true;
					distanceToCluster = newDistance;
				}
			}

			// if a medoid is replaced, we need not check the remaining clusters
			//			if (changePerformed)
			//				break;
		}

		if (this.changePerformed) {
			this.changePerformed = false;
			// update the clusters and restart the process
			this.updateClusters(tmpMedoid, rc, root);
			this.refine(rc, root);
		}

	}

	/**
	 * Sets the k.
	 * @param k The k to set
	 */
	public void setK(final int k) {
		this.k = k;
	}

	/**
	 * Sets the objective.
	 * @param objective The objective to set
	 */
	public void setObjective(final float objective) {
		System.out.println("Objective function = " + objective);
		this.objective = objective;
	}

	/**
	 * Relocates nodes between clusters when necessary 
	 * and updates the objective function.
	 */
	void updateClusters(
		final StateNode newMedoid,
		final AdjacencyMatrixRowColumn rc,
		final StateNode root) {
		StateNode tmpMedoid;
		float distanceToCluster;
		float distanceToMedoid;
		for (int i = 0; i < this.k; i++) {
			tmpMedoid = (StateNode) root.getChildAt(i);
			// should throw an exception if either strings is null
			if (((String) tmpMedoid.getUserObject()).equals(newMedoid
				.getUserObject())) {
				continue;
			}
			for (int j = 0; j < tmpMedoid.getChildCount(); j++) {
				distanceToCluster =
					this.metric.distance(rc, rc.indexOf((String) tmpMedoid
						.getUserObject()), rc
						.indexOf((String) ((StateNode) tmpMedoid.getChildAt(j))
							.getUserObject()));
				distanceToMedoid =
					this.metric.distance(rc, rc.indexOf((String) newMedoid
						.getUserObject()), rc
						.indexOf((String) ((StateNode) tmpMedoid.getChildAt(j))
							.getUserObject()));
				if (distanceToCluster > distanceToMedoid) {
					// move the node to the other cluster
					newMedoid.add((StateNode) tmpMedoid.getChildAt(j));
					this.changePerformed = true;
				}
			}
		}
		if (this.changePerformed) {
			this.setObjective(this.computeObjective(rc, root));
		}
		this.changePerformed = false;
	}

	void updateRowColumn(final AdjacencyMatrixRowColumn rc, final StateNode root) {
		final StateNode[] leaves = root.getLeaves();
		final String[] names = new String[leaves.length];

		for (int i = 0; i < leaves.length; i++) {
			names[i] = (String) leaves[i].getUserObject();
		}

		rc.removeAll();
		rc.populateMatrix(names);
	}
}
