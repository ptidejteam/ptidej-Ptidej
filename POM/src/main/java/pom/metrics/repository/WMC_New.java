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
 * WMC - Weighted Methods Per Class Computed as the sum of VG of all methods implemented by the class - VG(m) is McCabe's cyclomatic complexity number.
 * It is given by the size of the basis set of paths through the control flow graph of function m. For a single entry, single-exit control flow graph consisting of e edges and n nodes, VG(m) =e-m+n 
 * [T. McCabe]
 * VG(m) can be computed as the number of decisions node +1
 * ref: Bruntink, M., Deursen, A.V.: Predicting class testability using object oriented metrics. In: Proceedings of the IEEE International Workshop on Source Code Analysis and Manipulation, pp. 136e145 (2004) 
 * @author Aminata Sabane
 * @since  2012/06/05
 * 
 */
package pom.metrics.repository;

import java.util.Iterator;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import padl.statement.kernel.IConditional;
import padl.statement.kernel.IIfInstruction;
import padl.statement.kernel.ISwitchInstruction;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class WMC_New extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		double value = 0;

		final Iterator iterOnMethods =
			this.classPrimitives
				.listOfDeclaredMethods(firstClassEntity)
				.iterator();
		while (iterOnMethods.hasNext()) {
			//the complexity of a method is the number of branch decisions +1
			//then the complexity of a method without any branch decision is 1
			double methodCyclomaticComplexity = 1;
			final IOperation operation = (IOperation) iterOnMethods.next();
			final Iterator iteratorOnConditional =
				operation
					.getConcurrentIteratorOnConstituents(IConditional.class);
			while (iteratorOnConditional.hasNext()) {
				final IConditional conditionStatement =
					(IConditional) iteratorOnConditional.next();
				if (conditionStatement instanceof IIfInstruction) {
					methodCyclomaticComplexity++;
				}
				else {
					methodCyclomaticComplexity +=
						((ISwitchInstruction) conditionStatement)
							.getNumberOfCases();
				}
			}

			//aggregate method complexity into a class
			value += methodCyclomaticComplexity;
		}

		return value;
	}
	public String getDefinition() {
		final String def =
			"Weight of an entity considering the complexity of each of its method as being 1. (Default constructors are considered even if not explicitely declared.)";
		return def;
	}
}
