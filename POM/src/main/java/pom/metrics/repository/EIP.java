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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * EIP - Number of External Inheritance as Provider
 * 
 * The following metric is related to packages, and is based
 * on the paper "Butterflies: A Visual Approach to Characterize Packages",
 * by Ducasse, Lanza and Ponisio.
 * 
 * @author Karim DHAMBRI
 * @since  2005/??/?? 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */
public class EIP extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final List classesOfAnalysedPackage = new ArrayList();
		final String packageName =
			super.classPrimitives.extractPackageName(anEntity);
		//		final String key = packageName + " " + "EIP";
		//
		//		if (super.cachedResults.containsKey(key)) {
		//			return ((Double) super.cachedResults.get(key)).doubleValue();
		//		}

		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity d = (IFirstClassEntity) iterator.next();
			// TODO: Replace continue by real test!!!
			if ((!(d instanceof IClass)) && (!(d instanceof IInterface)))
				continue;
			if (super.classPrimitives.extractPackageName(d).equals(packageName))
				classesOfAnalysedPackage.add(d);
		}

		// int nbrInheritanceRel = 0;
		final Set inheritedClasses = new HashSet();

		for (int i = 0; i < classesOfAnalysedPackage.size(); i++) {
			final Iterator children =
				((IFirstClassEntity) classesOfAnalysedPackage.get(i))
					.getIteratorOnInheritingEntities();
			while (children.hasNext()) {
				final IFirstClassEntity child =
					(IFirstClassEntity) children.next();
				if (!(super.classPrimitives.extractPackageName(child)
					.equals(packageName))) {
					// nbrInheritanceRel++;
					inheritedClasses.add(child.getID());
				}
			}
		}

		return inheritedClasses.size();
	}
	public String getDefinition() {
		final String def =
			"Number of inheritance relationships where the superclass is in the package containing entity and the subclass is in another package.";
		return def;
	}
}
