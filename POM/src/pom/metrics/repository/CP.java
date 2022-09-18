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
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * CP - Number of Client Packages
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
public class CP extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final Set packages = new HashSet();
		final List entitiesOfAnalysedPackage = new ArrayList();
		final String packageName =
			super.classPrimitives.extractPackageName(anEntity);

		final Iterator iteratorOnEntities =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iteratorOnEntities.hasNext()) {
			final IFirstClassEntity otherEntity =
				(IFirstClassEntity) iteratorOnEntities.next();

			if ((!(otherEntity instanceof IClass))
					&& (!(otherEntity instanceof IInterface))) {
				continue;
			}
			if (super.classPrimitives.extractPackageName(otherEntity).equals(
				packageName)) {
				entitiesOfAnalysedPackage.add(otherEntity);
			}
		}

		final Collection allOtherEntities =
			super.operators.difference(
				anAbstractModel.getIteratorOnTopLevelEntities(),
				entitiesOfAnalysedPackage);

		for (int i = 0; i < entitiesOfAnalysedPackage.size(); i++) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) entitiesOfAnalysedPackage.get(i);
			final Iterator iteratorOnOtherEntities =
				allOtherEntities.iterator();
			while (iteratorOnOtherEntities.hasNext()) {
				final IFirstClassEntity otherEntity =
					(IFirstClassEntity) iteratorOnOtherEntities.next();
				if (!(otherEntity instanceof IClass)
						&& !(otherEntity instanceof IInterface)) {
					continue;
				}
				if (super.methodPrimitives.numberOfUsesByFieldsOrMethods(
					otherEntity,
					firstClassEntity) > 0) {
					packages.add(super.classPrimitives
						.extractPackageName(otherEntity));
				}
			}
		}

		packages.remove(super.classPrimitives.extractPackageName(anEntity));

		return packages.size();
	}
	public String getDefinition() {
		final String def =
			"Number of packages that depend on the package containing entity.";
		return def;
	}
}
