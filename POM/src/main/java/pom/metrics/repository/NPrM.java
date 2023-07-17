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
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Alban Tiberghien
 * @since 2008//08/04
 */
public class NPrM extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		final String def = "Number protected members of an entity.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		int cpt = 0;

		final List protectedMembers = new ArrayList();
		protectedMembers.addAll(super.classPrimitives
			.listOfImplementedFields(firstClassEntity));
		protectedMembers.addAll(super.classPrimitives
			.listOfDeclaredMethods(firstClassEntity));

		final Iterator iterator = protectedMembers.iterator();
		while (iterator.hasNext()) {
			final IConstituent c = (IConstituent) iterator.next();
			if (c.isProtected()) {
				cpt++;
			}
		}

		return cpt;
	}
}
