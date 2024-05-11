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
 * ACMIC - Ancestors Class-Method Import Coupling
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */

package pom.metrics.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IParameter;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class ACMIC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return listOfElements(firstClassEntity).size();
	}
	public String getDefinition() {
		final String def =
			"Ancestors class-method import coupling of an entity.";
		return def;
	}
	private List listOfElements(final IFirstClassEntity firstClassEntity) {
		final List acmicList = new ArrayList();

		final Collection newMethods =
			super.classPrimitives.listOfNewMethods(firstClassEntity);
		final List ancestorsNames = new ArrayList();
		final List ancestors =
			super.classPrimitives.listOfAncestors(firstClassEntity);

		// Constructs a list of the entity names
		for (final Iterator iterAncestor = ancestors.iterator(); iterAncestor
			.hasNext();) {
			final IFirstClassEntity element =
				(IFirstClassEntity) iterAncestor.next();
			ancestorsNames.add(element.getID());
		}
		for (final Iterator iter = newMethods.iterator(); iter.hasNext();) {
			final IConstructor method = (IConstructor) iter.next();

			// TODO: final Iterator iteratorOnParameter

			for (final Iterator iterator = method.getIteratorOnConstituents(); iterator
				.hasNext();) {
				final IConstituentOfOperation element =
					(IConstituentOfOperation) iterator.next();
				if (element instanceof IParameter) {
					final IParameter parameter = (IParameter) element;
					if (ancestorsNames.contains(parameter.getTypeName())) {
						acmicList.add(element);
					}
				}
			}
		}
		return acmicList;
	}
}
