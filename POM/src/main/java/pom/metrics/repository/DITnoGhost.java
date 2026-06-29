package pom.metrics.repository;

import java.util.List;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;

public class DITnoGhost extends DIT {
	
	@Override
	public String getDefinition() {
		final String def =
			"Depth of inheritance tree of an entity, calculated without using ghosts. Uses a recursive algorithm to calculate it.";
		return def;
	}
	
	@Override
	protected double maxDIT(
			final IAbstractModel anAbstractModel,
			final List list) {
		
		final int size = list.size();
		final double[] resultDITs = new double[size];
		for (int i = 0; i < size; i++) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) list.get(i);
			if (firstClassEntity instanceof IGhost) {
				resultDITs[i] = 0.0;	// Stop the computation when we encounter a ghost
			}
			else {
				resultDITs[i] = compute(anAbstractModel, firstClassEntity);
			}
		}
		return this.maxValue(resultDITs);
	}
}
