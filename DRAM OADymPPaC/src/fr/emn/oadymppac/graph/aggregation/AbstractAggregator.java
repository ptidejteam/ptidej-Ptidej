package fr.emn.oadymppac.graph.aggregation;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 * 
 * This abstract class is the base class for
 * attribute aggregation support in clustering
 * processes. It delegates the computation of
 * the aggregated attribute to a dynamically 
 * assigned function.
 */
public abstract class AbstractAggregator implements AttributeAggregator {
	AggregationFunction function;

	/**
	 * Default constructor.
	 */
	public AbstractAggregator(final AggregationFunction f) {
		this.function = f;
	}

	public Object aggregate(final Object[] args) {
		if (this.function != null) {
			return this.function.compute(args);
		}
		System.err.println("aggregation function undefined");
		return null;
	}

	/**
	 * Returns the function.
	 * @return AggregationFunction
	 */
	public AggregationFunction getFunction() {
		return this.function;
	}

	/**
	 * Sets the function.
	 * @param function The function to set
	 */
	public void setFunction(final AggregationFunction function) {
		this.function = function;
	}

}
