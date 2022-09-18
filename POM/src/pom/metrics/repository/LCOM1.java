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

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IDependencyIndependentMetric;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * LCOM1 - Lack of COhesion in Methods Version 1
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */
public class LCOM1 extends AbstractLCOM implements IMetric, IUnaryMetric, IDependencyIndependentMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		return super.pairsOfMethodNotSharingFields(anEntity);
	}
	public String getDefinition() {
		String def = "Lack of cohesion in the methods of an entity.";
		return def;
	}
}
