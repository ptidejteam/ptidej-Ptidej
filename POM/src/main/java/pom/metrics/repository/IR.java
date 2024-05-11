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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import padl.kernel.IAbstractModel;
import padl.kernel.IConstituent;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Alban Tiberghien
 * @since 2008/08/08
 */
public class IR extends AbstractMetric implements IMetric, IUnaryMetric {
	public String getDefinition() {
		final String def = "Inheritance ratio.";
		return def;
	}
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final Set protectedMembers = new HashSet();
		final List parents =
			super.classPrimitives.listOfAllDirectParents(anEntity);
		final Iterator iterator = parents.iterator();
		while (iterator.hasNext()) {
			final IFirstClassEntity parent =
				(IFirstClassEntity) iterator.next();
			if (!(parent instanceof IGhost)) {
				final List allMembers = new ArrayList();
				allMembers.addAll(super.classPrimitives
					.listOfImplementedFields(parent));
				allMembers.addAll(super.classPrimitives
					.listOfDeclaredMethods(parent));

				final Iterator iterator2 = allMembers.iterator();
				while (iterator2.hasNext()) {
					final IConstituent c = (IConstituent) iterator2.next();
					if ((c instanceof IField && c.isProtected())
							|| (c instanceof IOperation && !c.isPrivate())) {
						protectedMembers.add(c.getDisplayName());
					}
				}
			}
		}
		int acceptedBequest = 0;

		final Collection allMethods =
			super.classPrimitives.listOfAllMethods(anEntity);
		for (Iterator iter = allMethods.iterator(); iter.hasNext();) {
			final IOperation method = (IOperation) iter.next();
			final Iterator iter2 =
				method.getIteratorOnConstituents(IMethodInvocation.class);
			if (!iter2.hasNext()) {
				acceptedBequest--;
			}
			else {
				if (protectedMembers.contains(method.getDisplayName())) {
					acceptedBequest++;
				}
				while (iter2.hasNext()) {
					final IMethodInvocation mi =
						(IMethodInvocation) iter2.next();
					final IOperation calledMethod = mi.getCalledMethod();
					final IField calledField = mi.getFirstCallingField();
					if ((calledMethod != null && protectedMembers
						.contains(calledMethod.getDisplayName()))
							|| (calledField != null && protectedMembers
								.contains(calledField.getDisplayName()))) {
						acceptedBequest++;
					}
				}
			}
		}
		return acceptedBequest;
	}
}
