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
 * AID - Average Inheritance Depth
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
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class AID extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {
		final List parentsOfEntity = listOfElements(firstClassEntity);

		return parentsOfEntity.size() == 0 ? 0 : this.average(
			anAbstractModel,
			parentsOfEntity);
	}
	public String getDefinition() {
		final String def =
			"Average inheritance depth of an entity. Uses a recursive algorithm to calculate it.";
		return def;
	}
	/**
	 * Returns the average of the AID of entities contained in the list.
	 * 
	 * @param parents
	 * @return the average of the AID of entities contained in the list
	 */
	private double average(
		final IAbstractModel anAbstractModel,
		final List parents) {

		double total = 0;

		for (final Iterator iterEntity = parents.iterator(); iterEntity
			.hasNext();) {

			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterEntity.next();
			total =
				total + 1
						+ this.compute(anAbstractModel, firstClassEntity);
		}

		return total / parents.size();
	}
	private List listOfElements(IFirstClassEntity firstClassEntity) {
		return super.classPrimitives.listOfAllDirectParents(firstClassEntity);
	}
}
