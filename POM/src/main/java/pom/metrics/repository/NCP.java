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
 * NCP - Number of Classes in a Package
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
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class NCP extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		// String packageName = super.classPrimitives.extractPackageName(entity);
		// String key = packageName + " " + "NCP";
		//
		// if (!super.cachedResults.containsKey(key)) {
		// this.listOfElements(entity);
		// }
		//
		// final double result =
		// ((Double) super.cachedResults.get(key)).doubleValue();
		return this
			.listOfElements(anAbstractModel, firstClassEntity)
			.size();
	}
	public String getDefinition() {
		final String def = "Number of packages containing an entity.";
		return def;
	}
	private List listOfElements(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final List classesOfAnalysedPackage = new ArrayList();
		final String packageName =
			super.classPrimitives.extractPackageName(firstClassEntity);
		// final String key = packageName + " " + "NCP";

		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity d = (IFirstClassEntity) iterator.next();
			if ((d instanceof IClass) || (d instanceof IInterface)) {
				if (super.classPrimitives.extractPackageName(d).equals(
					packageName)) {
					classesOfAnalysedPackage.add(d);
				}
			}
		}

		// super.cachedResults.put(
		// key,
		// Double.valueOf(classesOfAnalysedPackage.size()));

		return classesOfAnalysedPackage;
	}
}
