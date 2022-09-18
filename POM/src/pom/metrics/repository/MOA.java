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
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class MOA extends AbstractMetric implements IMetric, IUnaryMetric, 
	IDependencyIndependentMetric {
	public String getDefinition() {
		final String def =
			"Data declarations whose types are user-defined entities.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final List implantedFields =
			this.classPrimitives.listOfImplementedFields(firstClassEntity);

		double countMOA = 0;
		final Iterator iterField = implantedFields.iterator();
		while (iterField.hasNext()) {
			final IField field = (IField) iterField.next();
			if (!(anAbstractModel.getTopLevelEntityFromID(field.getType()) instanceof IGhost)) {

				countMOA = countMOA + 1;
			}
		}
		return countMOA;
	}
}
