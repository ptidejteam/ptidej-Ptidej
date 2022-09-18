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
 * ACAIC - Ancestor Class-Attribute Import Coupling
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
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class ACAIC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this.listOfElements(firstClassEntity).size();
	}
	public String getDefinition() {
		String def = "Ancestor class-attribute import coupling of an entity.";
		return def;
	}
	private List listOfElements(final IFirstClassEntity firstClassEntity) {
		final List acaicList = new ArrayList();

		final List entityFields =
			super.classPrimitives.listOfImplementedFields(firstClassEntity);
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

		for (final Iterator iterField = entityFields.iterator(); iterField
			.hasNext();) {
			final IField element = (IField) iterField.next();
			if (ancestorsNames.contains(element.getType()))
				acaicList.add(element);
		}

		return acaicList;
	}
}
