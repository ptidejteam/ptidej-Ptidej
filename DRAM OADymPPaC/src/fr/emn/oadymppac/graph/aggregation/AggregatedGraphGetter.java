package fr.emn.oadymppac.graph.aggregation;

import salvo.jesus.graph.Graph;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * An interface for handling graph aggregation.
 */
public interface AggregatedGraphGetter extends AttributeAggregator {

	/**
	 * Maps the given graph to the passed level.
	 */
	public void addMapping(int level, Graph graph);

	/**
	 * Returns the aggregated graph for the passed level.
	 */
	public Graph getGraphForLevel(int level);

	/**
	 * Checks whether the map is empty.
	 */
	public boolean isEmpty();

	/**
	 * Removes all mappings.
	 */
	public void removeAllMappings();

	/**
	 * Removes the mapping for the passed level.
	 */
	public void removeMapping(int level);
}
