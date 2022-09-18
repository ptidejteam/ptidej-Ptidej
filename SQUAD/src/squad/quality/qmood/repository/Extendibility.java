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
package squad.quality.qmood.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import squad.quality.AbstractQualityAttribute;
import squad.quality.INumericQualityAttribute;
import squad.quality.IQualityAttribute;
import util.help.IHelpURL;

public final class Extendibility extends AbstractQualityAttribute implements
		IQualityAttribute, INumericQualityAttribute, IHelpURL {

	public double concretelyComputeNumValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {
		final double result =
			(0.5)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("ANA"))
						.compute(anAbstractModel, entity)
					- (0.5)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("DCC"))
						.compute(anAbstractModel, entity)
					+ (0.5)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("MFA"))
						.compute(anAbstractModel, entity)
					+ (0.5)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("NOPM")).compute(
						anAbstractModel,
						entity);
		return result;
	}
	public String getDefinition() {
		final String def =
			"Presence and usage of properties in an existing design that allow for the incorporation of new requirements in the design.";
		return def;
	}
	public String getHelpURL() {
		return "http://dl.acm.org/citation.cfm?id=513066";
	}
}
