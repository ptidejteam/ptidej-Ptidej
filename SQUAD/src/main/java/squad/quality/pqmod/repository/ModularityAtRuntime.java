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
package squad.quality.pqmod.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import squad.quality.AbstractQualityAttribute;
import squad.quality.INominalQualityAttribute;
import squad.quality.IQualityAttribute;

public class ModularityAtRuntime extends AbstractQualityAttribute implements
		IQualityAttribute, INominalQualityAttribute {

	public String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {

		final double Lcom5 =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("LCOM5"))
				.compute(anAbstractModel, entity);
		final double Dcaec =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("DCAEC"))
				.compute(anAbstractModel, entity);
		final double Ncm =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NCM"))
				.compute(anAbstractModel, entity);
		final double Noa =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOA"))
				.compute(anAbstractModel, entity);

		if ((Lcom5 >= 1.02) && (Dcaec <= 0)) {
			return "N";
		}
		else if ((Ncm >= 62.78) || (Noa <= 2)) {
			return "N";
		}
		else {
			return "G";
		}
	}
	public String getDefinition() {
		final String def =
			"Degree to which the functions of a system are independent from one another at runtime.";
		return def;
	}
}
