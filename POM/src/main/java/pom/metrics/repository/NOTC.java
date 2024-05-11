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
 *NOTC: numbers of invocations of JUNIT assert methods that occur in the code of a test case.
 *only direct invocations because it can call also a method that contains the asserts ... in that case, we will not have the number of asserts--- recursively??? it will be long!!!
 * ref: Bruntink, M., Deursen, A.V.: Predicting class testability using object oriented metrics. In: Proceedings of the IEEE International Workshop on Source Code Analysis and Manipulation, pp. 136e145 (2004) 
 * @author Aminata Sabane
 * @since  2012/07/03
 * 
 */
package pom.metrics.repository;

import java.util.Iterator;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class NOTC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		int notc = 0;

		if (anEntity instanceof IClass) {
			final IClass clazz = (IClass) anEntity;

			final Iterator iteratorOnMethods =
				clazz.getIteratorOnConstituents(IMethod.class);
			while (iteratorOnMethods.hasNext()) {
				final IMethod method = (IMethod) iteratorOnMethods.next();
				final Iterator methodInvocationsIter =
					method
						.getConcurrentIteratorOnConstituents(IMethodInvocation.class);

				while (methodInvocationsIter.hasNext()) {
					final IMethodInvocation methodInvocation =
						(IMethodInvocation) methodInvocationsIter.next();
					final IOperation methodInvoked =
						methodInvocation.getCalledMethod();
					if (methodInvoked != null) {
						//Is this heuristic sufficient? or add another constraint?
						if (methodInvoked.getDisplayName().indexOf("assert") > -1) {

							notc++;
						}
					}
				}
			}
		}

		return notc;
	}
	public String getDefinition() {
		return "Number of invocations of JUnit assert methods that occur in the code of a test case.";
	}
}
