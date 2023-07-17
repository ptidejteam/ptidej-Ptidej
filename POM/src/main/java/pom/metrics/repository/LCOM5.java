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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * LCOM5 - Lack of COhesion in Methods Version 5
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 * @bug Because of an entity can have less than two implemented methods, the
 *      value returned is 0. As it, the metric value has no impact. The
 *      semantic is kept. Other metrics can be considered for the evaluation
 *      of the entity
 */
public class LCOM5 extends AbstractLCOM implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final Collection implementedMethods =
			this.classPrimitives
				.listOfOverriddenAndConcreteMethods(firstClassEntity);
		final List implantedFields =
			this.classPrimitives.listOfImplementedFields(firstClassEntity);

		if (implementedMethods.size() < 2 || implantedFields.size() == 0) {
			return 0;
		}

		final List results = new ArrayList();
		final Iterator iterField = implantedFields.iterator();
		while (iterField.hasNext()) {
			final IField field = (IField) iterField.next();
			final Iterator iterMethod = implementedMethods.iterator();
			while (iterMethod.hasNext()) {
				final IOperation method = (IOperation) iterMethod.next();

				if (this.operators.belongTo(field, this.methodPrimitives
					.listOfFieldsUsedByMethod(firstClassEntity, method))) {
					results.add(method);
				}
			}
		}

		final double inverseAttrImpl = 1f / (float) implantedFields.size();
		final double num =
			implementedMethods.size() - inverseAttrImpl * results.size();
		final double den = implementedMethods.size() - 1;
		final double lcom5Value = num / den;

		return lcom5Value;
	}
	public String getDefinition() {
		final String def = "Lack of cohesion in methods of an entity.";
		return def;
	}
}
