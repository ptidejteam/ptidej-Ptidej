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

public class Modularity extends AbstractQualityAttribute implements
		IQualityAttribute, INominalQualityAttribute {

	public String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {

		final double Noc =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOC"))
				.compute(anAbstractModel, entity);
		final double Ncm =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NCM"))
				.compute(anAbstractModel, entity);
		final double Nop =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOP"))
				.compute(anAbstractModel, entity);
		final double Aid =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("AID"))
				.compute(anAbstractModel, entity);

		if (Ncm <= 50.63) {
			if (Noc <= 0) {
				if (Aid <= 0.83) {
					if (Aid <= 0.11) {
						return "N";
					}
					else {
						return "G";
					}
				}
				else {
					return "N";
				}
			}
			else {
				return "G";
			}
		}
		else {
			if (Nop <= 1.65) {
				return "N";
			}
			else {
				return "G";
			}
		}
	}
	public String getDefinition() {
		final String def =
			"Degree to which the implementation of the functions of a system are independent from one another.";
		return def;
	}

}
