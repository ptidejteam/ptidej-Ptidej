package fr.emn.oadymppac.graph.aggregation;

import java.lang.reflect.Array;

/**
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 *
 * A default aggregation function factory. It creates the default
 * associations between default data types and default operations
 * relevant to them. Extend this class to add your own associations
 * by overriding <code>createOtherTypes()</code> method.
 */
public class DefaultAggregationFunctionFactory extends
		AbstractAggregationFunctionFactory {

	static DefaultAggregationFunctionFactory sharedInstance =
		new DefaultAggregationFunctionFactory();

	/**
	 * Returns the unique instance of the factory, 
	 * and creates it when necessary. 
	 */
	public static AbstractAggregationFunctionFactory sharedInstance() {
		if (DefaultAggregationFunctionFactory.sharedInstance == null) {
			DefaultAggregationFunctionFactory.sharedInstance =
				new DefaultAggregationFunctionFactory();
		}
		return DefaultAggregationFunctionFactory.sharedInstance;
	}

	protected DefaultAggregationFunctionFactory() {
		super();
		this.createDefaultTypes();
	}

	/**
	 * Associates the default operations to the default data types.
	 */
	protected void createDefaultTypes() {
		super.createDefaultTypes();
		this.addFunction(Number.class, NumberMax.class);
		this.addFunction(Number.class, NumberMin.class);
		this.addFunction(Number.class, NumberMean.class);
		this.addFunction(Number.class, NumberSum.class);
		this.addFunction(Array.class, ArrayUnion.class);
	}

}
