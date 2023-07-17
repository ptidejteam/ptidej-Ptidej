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

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * WMC - Weight of Methods Computed
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */
public class WMC extends AbstractMetric implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		//JYves 2006/02/24:
		// weight initialisï¿½ ï¿½ 0 car une classe sans aucune 
		// mï¿½thodes (ni constructeur par dï¿½faut) a un WMC de 0.
		//		double weight = 1;
		double weight = 0;

		final Iterator iterAbstractMethod =
			anEntity.getIteratorOnConstituents(IOperation.class);
		while (iterAbstractMethod.hasNext()) {
			final IOperation method = (IOperation) iterAbstractMethod.next();
			weight +=
				method.getNumberOfConstituents(IMethodInvocation.class) + 1;
		}

		return weight;
	}
	public String getDefinition() {
		final String def =
			"Weight of an entity as the number of method invocations in each method. (Default constructors are considered even if not explicitely declared.)";
		return def;
	}
}
