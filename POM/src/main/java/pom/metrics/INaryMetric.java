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
package pom.metrics;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;

/**
 * Interface for metrics that operate on multiple (N) entities.
 * N-ary metrics compute a value based on a collection of entities,
 * complementing IUnaryMetric (single entity) and IBinaryMetric (pair of entities).
 */
public interface INaryMetric extends IMetric {
	double compute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity[] entities);
}
