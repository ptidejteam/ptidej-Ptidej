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
 * RFC - Response For Class The RFC of c is a count of the number of methods of c and
 * the number of methods of other classes that are invoked by
 * the methods of c.
 * ref: Bruntink, M., Deursen, A.V.: Predicting class testability using object oriented metrics. In: Proceedings of the IEEE International Workshop on Source Code Analysis and Manipulation, pp. 136Ð145 (2004) 
 * @author Aminata SabanŽ
 * @since  2012/06/05
 * 
 */
package pom.metrics.repository;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class RFC_New extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		return "Response for class: a count of the number of methods of an entity and the number of methods of other entities that are invoked by the methods of the entity.";
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final Set setOfCalledMethods = new HashSet();
		final Iterator iteratorOnDeclaredMethods =
			super.classPrimitives.listOfDeclaredMethods(anEntity).iterator();

		while (iteratorOnDeclaredMethods.hasNext()) {
			final IOperation operation =
				(IOperation) iteratorOnDeclaredMethods.next();
			final Iterator iteratorOnCalledMethods =
				operation.getIteratorOnConstituents(IMethodInvocation.class);
			while (iteratorOnCalledMethods.hasNext()) {
				final IMethodInvocation methodInvocation =
					(IMethodInvocation) iteratorOnCalledMethods.next();
				final IOperation calledMethod =
					methodInvocation.getCalledMethod();
				if (calledMethod != null) {
					//why get display name here
					//setOfCalledMethods.add(calledMethod.getDisplayName());
					setOfCalledMethods.add(calledMethod.getDisplayID());
				}
			}
		}
		return setOfCalledMethods.size()
				+ super.classPrimitives.listOfDeclaredMethods(anEntity).size();
	}
}
