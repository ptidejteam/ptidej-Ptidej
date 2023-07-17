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

public class Expendability extends AbstractQualityAttribute implements
		IQualityAttribute, INominalQualityAttribute {

	public String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {

		final double Noc =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOC"))
				.compute(anAbstractModel, entity);
		final double Nma =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMA"))
				.compute(anAbstractModel, entity);
		final double Nop =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOP"))
				.compute(anAbstractModel, entity);

		if (Noc <= 0) {
			if (Nma <= 1) {
				return "N";
			}
			else {
				if (Nop <= 0.8) {
					return "G";
				}
				else {
					if (Nop <= 4.67) {
						return "B";
					}
					else {
						return "G";
					}
				}
			}
		}
		else {
			if (Nma <= 17) {
				return "G";
			}
			else {
				return "B";
			}
		}
	}
	public String getDefinition() {
		final String def =
			"Degree to which the design of a system can be extended.";
		return def;
	}
}
