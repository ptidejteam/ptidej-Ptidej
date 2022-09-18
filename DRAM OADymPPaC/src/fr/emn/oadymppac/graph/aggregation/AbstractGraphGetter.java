package fr.emn.oadymppac.graph.aggregation;

import java.util.HashMap;
import javax.swing.tree.TreeModel;
import salvo.jesus.graph.Graph;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * This abstract class implements <code>AggregatedGraphGetter</code>.
 * It implements common methods required for mapping handling.
 */
public abstract class AbstractGraphGetter implements AggregatedGraphGetter {

	/**
	 * A map that holds a hierarchy of successive 
	 * aggregated graphs. 
	 */
	HashMap graphMap = new HashMap();

	/**
	 * Two cluster hierarchies are usually obtained 
	 * from the clustering of a graph represented
	 * as an adjacency matrix.
	 */
	TreeModel treeModel1;

	/**
	 * Two cluster hierarchies are usually obtained 
	 * from the clustering of a graph represented
	 * as an adjacency matrix.
	 */
	TreeModel treeModel2;

	/**
	 * The current graph.
	 */
	Graph graph;

	AggregationFunction function;

	/**
	 * Maps the given graph to the passed level.
	 */
	public void addMapping(final int level, final Graph g) {
		this.graphMap.put(Integer.valueOf(level), g);
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AttributeAggregator#aggregate(Object[])
	 */
	public Object aggregate(final Object[] args) {
		if (this.function == null) {
			this.function =
				DefaultAggregationFunctionFactory
					.sharedInstance()
					.getFunctions(Object.class)[0];
			//return null;
		}
		return this.function.compute(args);
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AttributeAggregator#getAggregationFunction()
	 */
	public AggregationFunction getAggregationFunction() {
		return this.function;
	}

	/**
	 * Checks whether the map is empty.
	 */
	public boolean isEmpty() {
		return this.graphMap.isEmpty();
	}

	/**
	 * Removes all mappings.
	 */
	public void removeAllMappings() {
		this.graphMap.clear();
	}

	/**
	 * Removes the mapping for the passed level.
	 */
	public void removeMapping(final int level) {
		this.graphMap.remove(Integer.valueOf(level));
	}

	/**
	 * @see fr.emn.oadymppac.graph.aggregation.AttributeAggregator#setAggregationFunction(AggregationFunction)
	 */
	public void setAggregationFunction(final AggregationFunction f) {
		this.function = f;
	}
}
