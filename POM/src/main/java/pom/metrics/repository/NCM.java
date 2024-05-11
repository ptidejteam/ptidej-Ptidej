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
 * NCM - Number of Changed Methods
 * 
 * @author Farouk ZAIDI
 * @since  2004/01/31 
 * 
 * @author Duc-Loc Huynh
 * @since  2005/08/18
 * 
 * Modifications made to fit the new architecture
 */

package pom.metrics.repository;

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class NCM extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		return super.getUnaryMetricInstance("NMA").compute(
			anAbstractModel,
			firstClassEntity)
				+ super.getUnaryMetricInstance("NMO").compute(
					anAbstractModel,
					firstClassEntity)
				+ super.getUnaryMetricInstance("NMI").compute(
					anAbstractModel,
					firstClassEntity);
	}
	public String getDefinition() {
		final String def = "Number of changed methods of in an entity.";
		return def;
	}
	//	public List listOfElements(IEntity entity) {
	//		List list = new ArrayList();
	//
	//		list.addAll(super.getInstanceOfUnaryMetric("NMA").listOfElements(
	//				entity));
	//		list.addAll(super.getInstanceOfUnaryMetric("NMO").listOfElements(
	//				entity));
	//		list.addAll(super.getInstanceOfUnaryMetric("NMI").listOfElements(
	//				entity));
	//
	//		return list;
	//	}
}
