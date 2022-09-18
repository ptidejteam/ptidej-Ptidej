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

import java.util.Collection;
import java.util.Iterator;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * connectivity
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2006/01/27
 * 
 * Modifications made to fit the new architecture
 */
public class connectivity extends AbstractMetric implements IMetric,
		IUnaryMetric {

	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final int cardImplementedMethods =
			this.classPrimitives.listOfOverriddenAndConcreteMethods(
				firstClassEntity).size();
		if (cardImplementedMethods < 3) {
			return 0;
		}
		final int linkedMethods = this.linkedMethods(firstClassEntity);
		final float connectivityValue =
			(linkedMethods - (cardImplementedMethods - 1))
					/ ((cardImplementedMethods - 1) * (cardImplementedMethods - 2));

		return connectivityValue;
	}
	// Equivalent to Ec(c) Edges of the graph
	// that represents the relationships
	// between methods of a same class. 
	private int linkedMethods(final IFirstClassEntity firstClassEntity) {
		if (!(firstClassEntity instanceof IClass)) {
			return 0;
		}

		// LCOM is the size of a set where are couples of method...
		// Why not just increment a local variable?
		int numberOfCouples = 0;

		// So we consider methods at the class level .
		final Collection methodsOfClass =
			this.classPrimitives
				.listOfOverriddenAndConcreteMethods(firstClassEntity);

		// For every method...
		for (Iterator iterMethod1 = methodsOfClass.iterator(); iterMethod1
			.hasNext();) {
			final IMethod method1 = (IMethod) iterMethod1.next();
			// For a given method, we analyze other methods.
			// We can then make couples.
			for (Iterator iterMethod2 = methodsOfClass.iterator(); iterMethod2
				.hasNext();) {
				final IMethod method2 = (IMethod) iterMethod2.next();

				// If m1 si invoked by m2 or m2 is invoked by m1 or
				// the intersection between AR(m1), AR(m2) and allAttributes(class) is empty,
				// then the condition is satisfied.
				if (this.operators.belongTo(
					method1,
					this.methodPrimitives.listOfSisterMethodCalledByMethod(
						firstClassEntity,
						method2))
						|| this.operators.belongTo(
							method2,
							this.methodPrimitives
								.listOfSisterMethodCalledByMethod(
									firstClassEntity,
									method1))
						|| this.operators
							.intersection(
								this.methodPrimitives.listOfFieldsUsedByMethod(
									firstClassEntity,
									method1),
								this.operators.intersection(
									this.methodPrimitives
										.listOfFieldsUsedByMethod(
											firstClassEntity,
											method2),
									this.classPrimitives
										.listOfImplementedFields(firstClassEntity)))
							.size() == 0) {

					// Incrementation of an index instead of 
					// storing the couple in a set.
					numberOfCouples++;
				}

			}
		}
		return numberOfCouples;
	}
	public String getDefinition() {
		return "Connectiviy of the methods of an entity.";
	}
}
