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

import java.util.List;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class NMD extends AbstractMetric implements IMetric, IUnaryMetric, 
	IDependencyIndependentMetric {
	/**
	 * @param iEntity
	 * @return double : number of methods declared COMMENTS [warning] Number of
	 *         methods declared + CONSTRUCTOR of the class
	 */
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return this.listOfElements(firstClassEntity).size();
	}
	private List listOfElements(final IFirstClassEntity firstClassEntity) {
		final List c =
			super.classPrimitives.listOfDeclaredMethods(firstClassEntity);
		return c;
	}
	public String getDefinition() {
		return "Number of methods declared by an entity.";
	}
}
