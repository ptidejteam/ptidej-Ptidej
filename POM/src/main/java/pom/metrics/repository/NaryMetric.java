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
package pom.metrics.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.INaryMetric;

/**
 * Default implementation of INaryMetric.
 * N-ary metrics compute values based on multiple entities.
 * Subclasses MUST override concretelyCompute(model, entities).
 */
public class NaryMetric extends AbstractMetric implements INaryMetric {
	public String getDefinition() {
		return "N-ary metric over multiple entities.";
	}

	// N-ary metrics do not compute values for a single entity.
	// This method is required by AbstractMetric but always returns 0
	// for n-ary metrics. Subclasses compute via the n-ary overload.
	@Override
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		return 0;
	}

	@Override
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity[] entities) {

		// Default implementation. Subclasses should override
		// to provide actual n-ary metric computation.
		return 0;
	}
}
