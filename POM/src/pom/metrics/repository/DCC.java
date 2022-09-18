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

import java.util.Iterator;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 * @author Yann
 */
public class DCC extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		final String def =
			"Number of classes that a class is directly related to (by attribute declarations and message passing).";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		double result = 0;

		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity otherEntity =
				(IFirstClassEntity) iterator.next();

			if (!otherEntity.equals(anEntity)) {
				result +=
					this.methodPrimitives.numberOfUsesByFieldsOrMethods(
						anEntity,
						otherEntity)
							+ this.methodPrimitives
								.numberOfUsesByFieldsOrMethods(
									otherEntity,
									anEntity);
			}
		}

		return result;
	}
}
