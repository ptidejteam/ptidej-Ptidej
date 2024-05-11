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
/**
 * CBO - Coupling Between Objects
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */
package pom.metrics.repository;

import java.util.Iterator;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IBinaryMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class FanOut extends AbstractMetric implements IMetric, IUnaryMetric,
		IBinaryMetric {

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
					this.compute(anAbstractModel, anEntity, otherEntity);
			}
		}
		return result;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity entityA,
		final IFirstClassEntity entityB) {

		final double cboValue =
			super.methodPrimitives.numberOfUsesByFieldsOrMethods(
				entityB,
				entityA);
		return cboValue;
	}
	public String getDefinition() {
		final String def = "Coupling between objects of an entity.";
		return def;
	}
}
