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

public class Reusability extends AbstractQualityAttribute implements
		IQualityAttribute, INominalQualityAttribute {

	public String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {
		double Noc =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOC"))
				.compute(anAbstractModel, entity);
		double Nma =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMA"))
				.compute(anAbstractModel, entity);
		double Aid =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("AID"))
				.compute(anAbstractModel, entity);

		if ((Noc <= 0.23) && (Aid >= 1)) {
			return "N";
		}
		else if (Nma >= 16) {
			return "N";
		}
		else {
			return "G";
		}
	}
	public String getDefinition() {
		final String def =
			"Degree to which a piece of design can be reused in another design.";
		return def;
	}
}
