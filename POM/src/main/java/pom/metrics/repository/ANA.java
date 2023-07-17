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

import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since 2007/03/01
 */
public class ANA extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final Iterator iter =
			anAbstractModel.getIteratorOnTopLevelEntities();

		// we need just classes
		double nba = 0;
		double nbofClassInterf = 0;
		while (iter.hasNext()) {
			final IFirstClassEntity anElement = (IFirstClassEntity) iter.next();

			if (anElement instanceof IClass) {
				nbofClassInterf = nbofClassInterf + 1;
				final List parent = listOfElements(anElement);
				nba = nba + parent.size();
			}
			else if (anElement instanceof IInterface) {
				nbofClassInterf = nbofClassInterf + 1;
			}
		}
		return (nba / nbofClassInterf);
		// return (nba / (double) (this.anAbstractModel
		// .getNumberOfConstituents(IClass.class) + this.anAbstractModel
		// .getNumberOfConstituents(IInterface.class)));
	}
	public String getDefinition() {
		final String def =
			"Average number of classes from which a class inherits information.";
		return def;
	}
	private List listOfElements(final IFirstClassEntity firstClassEntity) {
		return this.classPrimitives.listOfAllDirectParents(firstClassEntity);
	}
}
