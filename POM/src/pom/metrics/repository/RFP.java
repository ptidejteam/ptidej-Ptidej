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
 * RFP - Number of Provider Packages
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
package pom.metrics.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class RFP extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		final List entitiesOfAnalysedPackage = new ArrayList();
		final String packageName =
			super.classPrimitives.extractPackageName(anEntity);

		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity otherEntity =
				(IFirstClassEntity) iterator.next();
			if (!(otherEntity instanceof IClass)
					&& !(otherEntity instanceof IInterface)) {
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

		double result = 0;
		for (int i = 0; i < entitiesOfAnalysedPackage.size(); i++) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) entitiesOfAnalysedPackage.get(i);
			for (Iterator it = allOtherEntities.iterator(); it.hasNext();) {
				final IFirstClassEntity otherEntity =
					(IFirstClassEntity) it.next();
				if (!(otherEntity instanceof IClass)
						&& !(otherEntity instanceof IInterface)) {
					continue;
				}
				if (!(super.classPrimitives.extractPackageName(otherEntity)
					.equals(packageName))) {

					if (super.methodPrimitives.numberOfUsesByFieldsOrMethods(
						otherEntity,
						firstClassEntity) > 0) {

						result++;
					}
				}
			}
		}

		return result;
	}
	public String getDefinition() {
		final String def =
			"Number of entities referenced from the entities belonging to other packages than the package containing the entity.";
		return def;
	}
}
