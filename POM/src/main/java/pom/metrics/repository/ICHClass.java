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
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * @author Yann
 * 
 * Modifications made to fit the new architecture
 */
public class ICHClass extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		double ichclassValue = 0;

		final Collection list =
			super.classPrimitives.listOfOverriddenAndConcreteMethods(anEntity);
		final Iterator iterImplMethod = list.iterator();
		while (iterImplMethod.hasNext()) {
			final IConstructor implMethod =
				(IConstructor) iterImplMethod.next();
			ichclassValue += this.ICHmethod(implMethod, anEntity);
		}

		return ichclassValue;
	}
	public String getDefinition() {
		final String def =
			"Complexity of an entity as the sum of the complexities of its declared and inherited methods.";
		return def;
	}
	private double ICHmethod(
		final IOperation aMethod,
		final IFirstClassEntity anEntity) {

		final Collection newAndOverrMethods =
			super.operators.union(
				super.classPrimitives.listOfNewMethods(anEntity),
				super.classPrimitives.listOfOverriddenMethods(anEntity));

		double ichmethodValue = 0;
		final Iterator iterMethod = newAndOverrMethods.iterator();
		while (iterMethod.hasNext()) {
			final IOperation method = (IOperation) iterMethod.next();
			final double numberOfParameters =
				aMethod.getNumberOfConstituents(IParameter.class);
			final double npi =
				this.countNumberOfCallsFromTo(anEntity, method, aMethod);
			ichmethodValue += (1 + numberOfParameters) * npi;
		}

		return ichmethodValue;
	}
	/**
	 * Counts the number of times where the calling method calls a method (the first parameter).
	 * @param aCallingMethod
	 * @param aCalledMethod
	 * @return the number of times where the calling method calls a method.
	 */
	private final int countNumberOfCallsFromTo(
		final IFirstClassEntity anEntity,
		final IOperation aCallingMethod,
		final IOperation aCalledMethod) {

		int result = 0;
		final Iterator iteratorOnMethodInvocations =
			aCallingMethod.getIteratorOnConstituents(IMethodInvocation.class);
		while (iteratorOnMethodInvocations.hasNext()) {
			final IMethodInvocation mi =
				(IMethodInvocation) iteratorOnMethodInvocations.next();
			final IOperation invokedMethod = mi.getCalledMethod();
			final IFirstClassEntity targetEntity = mi.getTargetEntity();
			if (invokedMethod != null && invokedMethod.equals(aCalledMethod)
					&& anEntity.equals(targetEntity)) {
				result++;
			}
		}
		return result;
	}
}
