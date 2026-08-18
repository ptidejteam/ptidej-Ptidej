/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
/**
 * AverageCohesion - Average Lack of Cohesion Across All Entities in a Model
 * 
 * An n-ary metric that computes the average LCOM1 (Lack of Cohesion in Methods)
 * across all top-level entities in a model. Lower values indicate better cohesion
 * (methods share more fields), higher values indicate poorer cohesion.
 */
package pom.metrics.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IUnaryMetric;
import pom.metrics.INaryMetric;

public class AverageCohesion extends AbstractMetric implements INaryMetric {

	public String getDefinition() {
		return "Average lack of cohesion across all entities in a model, "
			+ "computed as the mean LCOM1 value of all top-level entities.";
	}

	// N-ary metrics do not compute values for a single entity.
	// Required by AbstractMetric but returns 0.
	@Override
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		return 0;
	}

	/**
	 * Computes the average LCOM1 value across all entities in the model.
	 * Iterates over all entities, computes LCOM1 for each, and returns the mean.
	 * Uses the LCOM1 unary metric via the MetricsRepository.
	 * Returns 0 if the model has no entities.
	 */
	@Override
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity[] entities) {

		if (entities.length == 0) {
			return 0;
		}

		final IUnaryMetric lcom1 =
			super.getUnaryMetricInstance("LCOM1");
		double totalLCOM = 0;

		for (int i = 0; i < entities.length; i++) {
			totalLCOM += lcom1.compute(
				anAbstractModel,
				entities[i]);
		}

		return totalLCOM / entities.length;
	}
}
