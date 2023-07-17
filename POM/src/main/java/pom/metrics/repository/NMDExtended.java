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
 * NMD - Number of Methods Declared
 * 
 * @author Moha N. & Huynh D.L.
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */

package pom.metrics.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import padl.kernel.IMemberClass;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class NMDExtended extends AbstractMetric implements IMetric,
		IUnaryMetric, IDependencyIndependentMetric {

	/**
	 * @param iEntity
	 * @return double : number of methods declared COMMENTS [warning] Number of
	 *         methods declared + CONSTRUCTOR of the class
	 *         + number of methods of the member classes
	 */
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this.listOfElements(firstClassEntity).size();
	}
	private List listOfElements(IFirstClassEntity firstClassEntity) {
		final List implementedMethods =
			super.classPrimitives.listOfDeclaredMethods(firstClassEntity);

		final List results = new ArrayList();

		final Iterator iteratorOnMemberClasses =
			firstClassEntity.getIteratorOnConstituents(IMemberClass.class);
		while (iteratorOnMemberClasses.hasNext()) {
			final IMemberClass aMemberClass =
				(IMemberClass) iteratorOnMemberClasses.next();

			if (aMemberClass.getDisplayName().length() > 2) {
				results.addAll(this.listOfElements(aMemberClass));
			}
		}

		final Iterator iteratorOnMemberInterfaces =
			firstClassEntity.getIteratorOnConstituents(IInterface.class);
		while (iteratorOnMemberInterfaces.hasNext()) {
			final IInterface aInterface =
				(IInterface) iteratorOnMemberInterfaces.next();
			results.addAll(this.listOfElements(aInterface));
		}

		// concat the list of methods of the class and 
		// the list of methods of all the member classes.
		results.addAll(implementedMethods);
		return results;
	}
	public String getDefinition() {
		return "Number of methods declared in an entity and in its member entities.";
	}
}
