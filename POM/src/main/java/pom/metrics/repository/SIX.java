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
 * SIX - Specialisation IndeX
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

public class SIX extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity firstClassEntity) {

		double nmoTimesDit =
			super.getUnaryMetricInstance("NMO").compute(
				anAbstractModel,
				firstClassEntity)
					* super.getUnaryMetricInstance("DIT").compute(
						anAbstractModel,
						firstClassEntity);
		double nbMethodsOfEntity =
			super.classPrimitives.listOfAllMethods(firstClassEntity).size();
		if (nbMethodsOfEntity == 0)
			nbMethodsOfEntity = 1;
		return nmoTimesDit / nbMethodsOfEntity;
	}
	public String getDefinition() {
		String def = "Specialisation indeX of an entity.";
		return def;
	}
	//	public List listOfElements(IEntity entity)
	//	{
	//		List list1 = new ArrayList();
	//		
	//		list1.addAll(super.getInstanceOfUnaryMetric("NMO").listOfElements(entity));
	//		list1.addAll(super.getInstanceOfUnaryMetric("DIT").listOfElements(entity));
	//		
	//		List list2 = new ArrayList();
	//		list2.addAll(super.classPrimitives
	//				.allEntityMethods(entity));
	//		
	//		List list = new ArrayList();
	//		
	//		
	//		for(int i = 0; i < list2.size(); i ++)
	//		{
	//			boolean unique = true;
	//			for(int j = 0; j < list1.size(); j++)
	//			{
	//				if(list1.get(j).equals(list2.get(i)))
	//				unique = false;
	//			}
	//			
	//			if(unique)
	//				list.add(list2.get(i));
	//		}
	//		
	//		return list;
	//	}
}
