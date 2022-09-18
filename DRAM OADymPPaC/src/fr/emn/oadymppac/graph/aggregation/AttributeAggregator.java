package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * Interface for attribute aggregation.
 */
public interface AttributeAggregator {
	public Object aggregate(Object[] args);
	public AggregationFunction getAggregationFunction();
	public void setAggregationFunction(AggregationFunction f);
}
