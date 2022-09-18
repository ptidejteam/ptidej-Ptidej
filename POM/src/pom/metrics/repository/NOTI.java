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
 * NOTI - Number Of Transitive Invacation
 * 
 * @author Alban Tiberghien
 * @since  2008/08/13 
 */
package pom.metrics.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class NOTI extends AbstractMetric implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		int result = -1;

		final Collection methods =
			super.classPrimitives
				.listOfOverriddenAndConcreteMethods(firstClassEntity);
		final Iterator iter = methods.iterator();
		while (iter.hasNext()) {
			final IConstructor method = (IConstructor) iter.next();
			result =
				Math.max(result, this.computeInvocation(
					firstClassEntity,
					method,
					new ArrayList(),
					0));
		}

		return result;

	}
	public String getDefinition() {
		final String def =
			"Highest number of transitive invocation among methods of a class. See the Law of Demeter for a definition.";
		return def;
	}
	private int computeInvocation(
		final IFirstClassEntity currentEntity,
		final IOperation currentOperation,
		final List visitedEntities,
		final int transitiveInvocation) {

		/**
		* Alban 2008/09/08 As this metrics is not used with a boxplot and the
		* threshold defined by the literature is 4, I limited the return value
		* to 20. It's a temporary fix and I have to find why this $*&%$ algo
		* don't stop !
		*/
		visitedEntities.add(currentEntity.getDisplayID());
		if (transitiveInvocation >= 20) {
			return 20;
		}

		int cpt = -1;

		final Iterator iterator =
			currentOperation.getIteratorOnConstituents(IMethodInvocation.class);
		iterator.hasNext();
		while (iterator.hasNext()) {
			final IMethodInvocation mi = (IMethodInvocation) iterator.next();
			final IOperation calledOperation = mi.getCalledMethod();

			/**
			 * Alban 2008/09/08: Infinite loop! 
			 * It misses again a stop condition 
			 * TODO Find which one !
			 * Yann 2010/03/04: Visited Entities
			 * I added a list of visited entities to make
			 * sure that we do not visit the same entities
			 * many times around jumping from one method
			 * to the others...
			 */
			if (calledOperation != null) {
				final IFirstClassEntity targetEntity = mi.getTargetEntity();
				if (targetEntity != null
						&& !visitedEntities.contains(targetEntity
							.getDisplayID())) {

					cpt =
						Math.max(cpt, this.computeInvocation(
							targetEntity,
							(IOperation) calledOperation,
							visitedEntities,
							transitiveInvocation + 1));
				}
			}
		}

		return cpt + 1;
	}
}
