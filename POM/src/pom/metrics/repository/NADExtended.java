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
 * NMA - Number of Methods A******  Ask Yann
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

/**
 * @param iEntity
 * @return double : number of attributes declared 
 *         + number of attributes of the member classes
 */
public class NADExtended extends AbstractMetric implements IMetric,
		IUnaryMetric, IDependencyIndependentMetric {

	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this.listOfElements(firstClassEntity).size();
	}
	private List listOfElements(final IFirstClassEntity firstClassEntity) {
		final List implementedFields =
			super.classPrimitives.listOfImplementedFields(firstClassEntity);

		// Yann 2007/03/14: Collection!
		// The method addAll() should be used!
		// not add() because it only add *list*
		// not the content of the list to the
		// results!? I guess this will change
		// the results...
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

		// Concat the list of attributes of the class and the list 
		// of attributes of all the member classes.
		// Yann 2007/03/14: Nope!
		// The following line is forbidden
		//		c.add(d);
		//		return c;
		// because you modify the list stored in the cache...
		// actually, this is a problem, we should protect
		// ourselves from such possible changes...
		// TODO: Return a clone of the list, not the list!
		// or better... an iterator :-) In the meantiem,
		// a quick fix:
		results.addAll(implementedFields);

		return results;
	}
	public String getDefinition() {
		return "Number of attributes declared in a class and in its member classes";
	}
}
