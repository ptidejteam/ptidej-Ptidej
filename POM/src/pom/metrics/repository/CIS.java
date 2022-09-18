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

import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class CIS extends AbstractMetric implements IMetric, IUnaryMetric, 
	IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final List meth = listOfElements(firstClassEntity);
		final int size = meth.size();

		double pubMeth = 0;
		for (int i = 0; i < size; i++) {
			final IOperation m = (IOperation) meth.get(i);
			if (m.isPublic()) {
				pubMeth = pubMeth + 1;
			}

		}
		return pubMeth;

	}
	public String getDefinition() {
		final String def = "Number of public methods in an entity.";
		return def;
	}
	private List listOfElements(IFirstClassEntity firstClassEntity) {
		return super.classPrimitives.listOfDeclaredMethods(firstClassEntity);
	}
}
