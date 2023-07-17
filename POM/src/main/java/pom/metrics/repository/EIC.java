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
 * EIC - Number of External Inheritance as Client
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

public class EIC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this
			.listOfElements(anAbstractModel, firstClassEntity)
			.size();
	}
	public String getDefinition() {
		final String def =
			"Number of inheritance relationships in which superclasses are in external packages.";
		return def;
	}
	private List listOfElements(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {
		List eicList = new ArrayList();
		List classesOfAnalysedPackage = new ArrayList();
		String packageName =
			super.classPrimitives.extractPackageName(firstClassEntity);
		//		String key = packageName + " " + "EIC";
		/*
		 if (Metrics.cachedResults.containsKey(key))
		 return ((Double) Metrics.cachedResults.get(key)).doubleValue();
		 */

		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			IFirstClassEntity d = (IFirstClassEntity) iterator.next();
			if ((!(d instanceof IClass)) && (!(d instanceof IInterface)))
				continue;
			if (super.classPrimitives.extractPackageName(d).equals(packageName))
				classesOfAnalysedPackage.add(d);
		}

		//	int nbrInheritanceRel = 0;

		for (int i = 0; i < classesOfAnalysedPackage.size(); i++) {
			final Iterator parents =
				((IFirstClassEntity) classesOfAnalysedPackage.get(i))
					.getIteratorOnInheritedEntities();
			while (parents.hasNext()) {
				final IFirstClassEntity parent =
					(IFirstClassEntity) parents.next();
				if (!(super.classPrimitives.extractPackageName(parent)
					.equals(packageName))) {

					//	nbrInheritanceRel++;
					eicList.add(parent);
				}
			}
		}

		//	super.cachedResults.put(key, Double.valueOf(nbrInheritanceRel));

		return eicList;
	}
}
