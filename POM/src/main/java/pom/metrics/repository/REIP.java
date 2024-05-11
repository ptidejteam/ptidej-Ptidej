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
 * REIP - Relative Number of External Inheritance as Provider
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

import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

public class REIP extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		final double eip =
			super.getUnaryMetricInstance("EIP").compute(
				anAbstractModel,
				firstClassEntity);
		final double piir =
			super.getUnaryMetricInstance("PIIR").compute(
				anAbstractModel,
				firstClassEntity);
		if (piir + eip == 0) {
			return 0;
		}
		else {
			return eip / (piir + eip);
		}
	}
	public String getDefinition() {
		final String def = "EIP divided by the sum of PIIR and EIP.";
		return def;
	}
	//	public List listOfElements(IEntity entity) {
	//		List list = new ArrayList();
	//
	//		list.addAll(super.getInstanceOfUnaryMetric("EIP").listOfElements(
	//				entity));
	//		list.addAll(super.getInstanceOfUnaryMetric("PIIR").listOfElements(
	//				entity));
	//
	//		return list;
	//	}
}
