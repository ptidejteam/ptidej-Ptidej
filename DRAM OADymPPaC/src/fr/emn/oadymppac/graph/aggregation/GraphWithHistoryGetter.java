package fr.emn.oadymppac.graph.aggregation;

import java.util.HashSet;
import java.util.Set;
import javax.swing.tree.TreeModel;
import salvo.jesus.graph.DirectedGraph;
import salvo.jesus.graph.Edge;
import salvo.jesus.graph.Graph;
import salvo.jesus.graph.Vertex;
import dram.utils.salvo.MyDirectedGraphImpl;
import dram.utils.salvo.MyGraphImpl;
import fr.emn.oadymppac.graph.EdgeWithHistory;
import fr.emn.oadymppac.graph.NamedGraphDelegator;
import fr.emn.oadymppac.tree.StateNode;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.3 $
 *
 * This class supports aggregation of graphs having edges with history.
 */
public class GraphWithHistoryGetter extends AbstractGraphGetter {

	Set edges = new HashSet();

	public GraphWithHistoryGetter(
		final TreeModel t1,
		final TreeModel t2,
		final NamedGraphDelegator g0) {
		this.treeModel1 = t1;
		this.treeModel2 = t2;
		this.graph = g0;
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AggregatedGraphGetter#getGraphForLevel(int)
	 */
	public Graph getGraphForLevel(final int level) {
		if (this.graphMap.containsKey(Integer.valueOf(level))) {
			return (NamedGraphDelegator) this.graphMap.get(Integer.valueOf(level));
		}

		NamedGraphDelegator g;
		if (((NamedGraphDelegator) this.graph).getUnderlyingGraph() instanceof DirectedGraph) {
			g = new NamedGraphDelegator(new MyDirectedGraphImpl());
		}
		else {
			g = new NamedGraphDelegator(new MyGraphImpl());
		}

		EdgeWithHistory edge;

		final StateNode root1 = (StateNode) this.treeModel1.getRoot();
		final StateNode root2 = (StateNode) this.treeModel2.getRoot();
		final StateNode[] clusters1 = root1.getNodesAtLevel(level);
		final StateNode[] clusters2 = root2.getNodesAtLevel(level);

		int edgeCount = 0;
		Edge[] edgeArray;
		Edge tmpEdge;
		StateNode[] leaves1;
		StateNode[] leaves2;
		Vertex v1, v2;
		int[][] histories;
		Object history = null;

		for (int i = 0; i < clusters1.length; i++) {
			leaves1 = clusters1[i].getLeaves();
			for (int j = 0; j < clusters2.length; j++) {
				leaves2 = clusters2[j].getLeaves();
				for (int k = 0; k < leaves1.length; k++) {
					v1 =
						((NamedGraphDelegator) this.graph)
							.getVertex((String) leaves1[k].getUserObject());
					for (int h = 0; h < leaves2.length; h++) {
						v2 =
							((NamedGraphDelegator) this.graph)
								.getVertex((String) leaves2[h].getUserObject());
						tmpEdge =
							((NamedGraphDelegator) this.graph).getEdge(v1, v2);
						if (tmpEdge != null) {
							this.edges.add(tmpEdge);
						}
					}
				}
				edgeCount = this.edges.size();
				if (edgeCount > 0) {
					edgeArray = new Edge[edgeCount];
					this.edges.toArray(edgeArray);
					histories = new int[edgeCount][];
					if (edgeArray[0] instanceof EdgeWithHistory) {
						for (int k = 0; k < edgeArray.length; k++) {
							histories[k] =
								((EdgeWithHistory) edgeArray[k]).getHistory();
						}
					}
					else {
						System.err
							.println("bad edge type, expected: EdgeWithHistory.");
					}
					if (g.getEdge(
						g.findVertex((String) clusters1[i].getUserObject()),
						g.findVertex((String) clusters2[j].getUserObject())) == null) {
						edge =
							new EdgeWithHistory(
								g.findVertex((String) clusters1[i]
									.getUserObject()),
								g.findVertex((String) clusters2[j]
									.getUserObject()));
						try {
							history = this.aggregate(histories);
							edge.setHistory((int[]) history);
						}
						catch (final ClassCastException cce) {
							System.out.println("cannot cast from "
									+ history.getClass());
						}
						try {
							g.addEdge(edge);
						}
						catch (final Exception ex) {
							System.out.println("problème d'insertion");
						}
					}
				}
				edgeCount = 0;
				this.edges.clear();
			}
		}
		System.out
			.println("new graph has" + g.getVerticesCount() + " vertices");
		System.out.println("new graph has" + g.getEdgesCount() + " edges");

		this.addMapping(level, g);
		return g;
	}

}
