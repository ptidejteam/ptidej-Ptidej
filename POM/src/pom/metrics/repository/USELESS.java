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
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Alban Tiberghien
 * @since 2008/09/26
 */
public class USELESS extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		final String def = "Same value for any entity: 1.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return 1;
	}
}
