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

public class Scalability extends AbstractQualityAttribute implements
		IQualityAttribute, INominalQualityAttribute {

	public String concretelyComputeNomValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {

		final double Noc =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NOC"))
				.compute(anAbstractModel, entity);
		final double Aid =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("AID"))
				.compute(anAbstractModel, entity);
		final double AcMic =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("ACMIC"))
				.compute(anAbstractModel, entity);
		final double DcMec =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("DCMEC"))
				.compute(anAbstractModel, entity);
		final double Nma =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("NMA"))
				.compute(anAbstractModel, entity);
		final double connectivity =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric(
				"connectivity")).compute(anAbstractModel, entity);
		final double Lcom5 =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric("LCOM5"))
				.compute(anAbstractModel, entity);
		final double ICHClass =
			((IUnaryMetric) MetricsRepository.getInstance().getMetric(
				"ICHClass")).compute(anAbstractModel, entity);

		if ((DcMec >= 0.1) && (Noc <= 0.65) && (DcMec <= 0.2)) {
			return "G";
		}
		else if ((Aid >= 2.83) && (Aid <= 2.92)) {
			return "G";
		}
		else if ((connectivity <= 0.66) && (Noc >= 0.75) && (AcMic <= 0.2)) {
			return "B";
		}
		else if ((Lcom5 <= 0.63) && (ICHClass >= 0.25) && (Nma <= 5.88)) {
			return "B";
		}
		else {
			return "N";
		}
	}
	public String getDefinition() {
		final String def =
			"Degree to which the system can cope with large amount of data and computation at runtime.";
		return def;
	}

}
