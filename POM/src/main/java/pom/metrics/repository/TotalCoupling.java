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
 * TotalCoupling - Total Coupling Between All Objects in a Model
 * 
 * An n-ary metric that computes the sum of coupling between all pairs
 * of top-level entities in a model. This is a system-level metric
 * that aggregates CBO (Coupling Between Objects) across all entities.
 */
package pom.metrics.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IBinaryMetric;
import pom.metrics.INaryMetric;

public class TotalCoupling extends AbstractMetric implements INaryMetric {

	public String getDefinition() {
		return "Total coupling between all objects in a model, "
			+ "computed as the sum of CBO across all entity pairs.";
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
	 * Computes the total coupling across all entities in the model.
	 * Iterates over all pairs (i, j) with i < j and sums their CBO values.
	 * Uses the CBO binary metric via the MetricsRepository.
	 */
	@Override
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity[] entities) {

		if (entities.length < 2) {
			return 0;
		}

		final IBinaryMetric cbo =
			super.getBinaryMetricInstance("CBO");
		double totalCoupling = 0;

		for (int i = 0; i < entities.length; i++) {
			for (int j = i + 1; j < entities.length; j++) {
				totalCoupling += cbo.compute(
					anAbstractModel,
					entities[i],
					entities[j]);
			}
		}

		return totalCoupling;
	}
}
