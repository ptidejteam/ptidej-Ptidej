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
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;
import util.lang.Modifier;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class DAM extends AbstractMetric implements IMetric, IUnaryMetric, 
	IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final List Attrib = listOfElements(firstClassEntity);
		final int size = Attrib.size();

		if (size > 0) {
			double priv = 0;
			for (int i = 0; i < size; i++) {
				final IField aField = (IField) Attrib.get(i);
				if (aField.isPrivate()
						|| (aField.getVisibility() & Modifier.PROTECTED) == Modifier.PROTECTED) {

					priv = priv + 1;
				}

			}
			return priv / (double) size;
		}
		else
			return 0;

	}
	public String getDefinition() {
		final String def =
			"Ratio of the number of private and protected attributes to the total number of attributes declared in an entity.";
		return def;
	}
	private List listOfElements(final IFirstClassEntity firstClassEntity) {
		return super.classPrimitives.listOfImplementedFields(firstClassEntity);
	}
}
