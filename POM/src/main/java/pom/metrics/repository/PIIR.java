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
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * PIIR - Number of Internal Inheritance Relationships
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
 * @author Yann
 * 
 * Modifications made to fit the new architecture
 */
public class PIIR extends AbstractMetric implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	// TODO: Is this the best way to implement this metrics???
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final List entitiesOfAnalysedPackage = new ArrayList();
		final String packageName =
			super.classPrimitives.extractPackageName(anEntity);
		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();

			if (!(firstClassEntity instanceof IClass)
					&& !(firstClassEntity instanceof IInterface)) {
				continue;
			}
			if (super.classPrimitives
				.extractPackageName(firstClassEntity)
				.equals(packageName)) {

				entitiesOfAnalysedPackage.add(firstClassEntity);
			}
		}

		final Set inheritedClasses = new HashSet();
		for (int i = 0; i < entitiesOfAnalysedPackage.size(); i++) {
			final Iterator parents =
				((IFirstClassEntity) entitiesOfAnalysedPackage.get(i))
					.getIteratorOnInheritedEntities();
			while (parents.hasNext()) {
				final IFirstClassEntity parent =
					(IFirstClassEntity) parents.next();
				if (super.classPrimitives.extractPackageName(parent).equals(
					packageName))

					inheritedClasses.add(parent.getID());
			}
		}

		return inheritedClasses.size();
	}
	public String getDefinition() {
		final String def =
			"Number of inheritance relationships existing between entities in the package containing the entity.";
		return def;
	}
}
