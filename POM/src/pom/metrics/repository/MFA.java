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

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class MFA extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		final String def =
			"Ratio of the number of methods inherited by an entity to the number of methods accessible by member methods of the entity.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		if ((super.classPrimitives
			.listOfDeclaredMethods(firstClassEntity)
			.size() + super.classPrimitives.listOfInheritedMethods(
			firstClassEntity).size()) > 0) {

			return (double) (super.classPrimitives
				.listOfInheritedMethods(firstClassEntity).size())
					/ (double) (super.classPrimitives.listOfDeclaredMethods(
						firstClassEntity).size() + super.classPrimitives
						.listOfInheritedMethods(firstClassEntity)
						.size());
		}
		else {
			return 0;
		}
	}
}
