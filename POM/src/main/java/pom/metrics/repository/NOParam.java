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
import java.util.List;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Alban Tiberghien
 * @since 2008//08/04
 */
public class NOParam extends AbstractMetric implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	public String getDefinition() {
		final String def =
			"Maximum mumber of parameters of the methods of an entity.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		int max = 0;

		final List methods =
			super.classPrimitives.listOfDeclaredMethods(firstClassEntity);
		final Iterator iteratorOnMethods = methods.iterator();
		while (iteratorOnMethods.hasNext()) {
			final IOperation m = (IOperation) iteratorOnMethods.next();
			max = Math.max(max, m.getNumberOfConstituents(IParameter.class));
		}

		return max;
	}
}
