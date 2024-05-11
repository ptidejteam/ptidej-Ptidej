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

public class Generality extends AbstractQualityAttribute implements
		IQualityAttribute, INominalQualityAttribute {

	public String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {

		final double Noc =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOC"))
				.compute(anAbstractModel, entity);
		final double Acmic =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("ACMIC"))
				.compute(anAbstractModel, entity);
		final double Aid =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("AID"))
				.compute(anAbstractModel, entity);

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
			if (Acmic <= 1) {
				return "G";
			}
			else {
				return "N";
			}
		}
	}
	public String getDefinition() {
		final String def =
			"Degree to which a system provides a wide range of functions at runtime.";
		return def;
	}

}
