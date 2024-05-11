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

public final class Reusability extends AbstractQualityAttribute implements
		IQualityAttribute, INumericQualityAttribute, IHelpURL {

	public double concretelyComputeNumValue(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entity) {

		final double result =
			(-0.25)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("DCC"))
						.compute(anAbstractModel, entity)
					+ (0.25)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("CAM"))
						.compute(anAbstractModel, entity)
					+ (0.5)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("CIS"))
						.compute(anAbstractModel, entity)
					+ (0.5)
					* ((IUnaryMetric) MetricsRepository
						.getInstance()
						.getMetric("DSC"))
						.compute(anAbstractModel, entity);
		return result;
	}
	public String getDefinition() {
		final String def =
			"Presence of object oriented design characteristics that allow a design to be re applied to a new problem without significant effort.";
		return def;
	}
	public String getHelpURL() {
		return "http://dl.acm.org/citation.cfm?id=513066";
	}
}
