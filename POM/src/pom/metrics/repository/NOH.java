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
 */
public class NOH extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		double nbh = 0;

		final IFirstClassEntity object =
			(IFirstClassEntity) anAbstractModel
				.getTopLevelEntityFromID("java.lang.Object");
		if (object != null) {
			final Iterator inheritingEntities =
				object.getIteratorOnInheritingEntities();
			while (inheritingEntities.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) inheritingEntities.next();
				if (super.classPrimitives.getNumberOfChildren(firstClassEntity) > 0) {
					nbh = nbh + 1;
				}
			}
		}
		else {
			final Iterator topLevelEntities =
				anAbstractModel.getIteratorOnTopLevelEntities();
			while (topLevelEntities.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) topLevelEntities.next();
				if (super.classPrimitives.getNumberOfParents(firstClassEntity) == 0
						&& super.classPrimitives
							.getNumberOfChildren(firstClassEntity) > 0) {

					nbh = nbh + 1;
				}
			}
		}

		return nbh;
	}
	public String getDefinition() {
		final String def = "Number of class hierarchies in the model.";
		return def;
	}
}
