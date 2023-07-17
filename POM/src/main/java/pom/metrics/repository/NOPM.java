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
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class NOPM extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		final String def =
			"Number of methods that can exhibit polymorphic behavior. (A method can exhibit polymorphic behaviour if it is overridden by one or more descendent classes.)";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		double result = 0;

		final Collection Methods =
			this.classPrimitives.listOfDeclaredMethods(anEntity);
		final List descendent =
			super.classPrimitives.listOfDescendents(anEntity);
		final Iterator iterMethod = Methods.iterator();
		while (iterMethod.hasNext()) {
			final IOperation method = (IOperation) iterMethod.next();
			final List overidden = new ArrayList();
			final Iterator iterdest = descendent.iterator();
			while (iterdest.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iterdest.next();
				if (super.operators.belongTo(method, this.classPrimitives
					.listOfOverriddenMethods(firstClassEntity))) {

					overidden.add(firstClassEntity);
				}
			}
			if (overidden.size() > 0) {
				result = result + 1;
			}
		}

		return result;
	}
}
