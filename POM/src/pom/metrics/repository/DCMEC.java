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
 * DCMEC - Descendants Class-Method Export Coupling
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
import java.util.Arrays;
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

public class DCMEC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this.listOfElements(firstClassEntity).size();
	}
	public String getDefinition() {
		final String def =
			"Descendants class-method export coupling of an entity.";
		return def;
	}
	private List listOfElements(IFirstClassEntity firstClassEntity) {
		final List dcmecList = new ArrayList();
		final char[] entityName = firstClassEntity.getID();

		final List descendents =
			super.classPrimitives.listOfDescendents(firstClassEntity);
		for (final Iterator iterDesc = descendents.iterator(); iterDesc
			.hasNext();) {
			final IFirstClassEntity aDesc = (IFirstClassEntity) iterDesc.next();
			final Collection newMethods =
				super.classPrimitives.listOfNewMethods(aDesc);
			for (final Iterator iter = newMethods.iterator(); iter.hasNext();) {
				final IConstructor method = (IConstructor) iter.next();
				for (final Iterator iterator =
					method.getIteratorOnConstituents(); iterator.hasNext();) {
					final IConstituentOfOperation element =
						(IConstituentOfOperation) iterator.next();
					if (element instanceof IParameter) {
						final IParameter parameter = (IParameter) element;
						if (Arrays.equals(parameter.getTypeName(), entityName)) {
							dcmecList.add(iterator);
						}
					}
				}
			}
		}
		return dcmecList;
	}
}
