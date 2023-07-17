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
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class CAM extends AbstractMetric implements IMetric, IUnaryMetric,
		IDependencyIndependentMetric {
	public String getDefinition() {
		final String def =
			"Relateness among methods of the class based upon the parameter list of the methods. The metrics is computed using the summation of the intersection of parameters of a method with the maximum independant set of all parameter types in the class.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final Collection implementedMethods =
			this.classPrimitives
				.listOfOverriddenAndConcreteMethods(firstClassEntity);

		double camValue = 0;

		final Collection totalParam = new ArrayList();
		for (final Iterator iterMethod = implementedMethods.iterator(); iterMethod
			.hasNext();) {

			final IOperation m = (IOperation) iterMethod.next();

			for (final Iterator IterParam =
				m.getIteratorOnConstituents(IParameter.class); IterParam
				.hasNext();) {

				final IParameter p = (IParameter) IterParam.next();
				totalParam.add(p);
			}
		}
		if (totalParam.size() > 0) {
			for (final Iterator iterMethod = implementedMethods.iterator(); iterMethod
				.hasNext();) {

				final IOperation m = (IOperation) iterMethod.next();

				final Collection mParam = new ArrayList();
				for (final Iterator IterParam =
					m.getIteratorOnConstituents(IParameter.class); IterParam
					.hasNext();) {

					IParameter p = (IParameter) IterParam.next();
					mParam.add(p);
				}
				camValue =
					camValue
							+ ((double) (super.operators.intersection(
								mParam,
								totalParam)).size())
							/ (double) totalParam.size();
			}
			return camValue;
		}
		else {
			return 0;
		}
	}
}
