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
import pom.metrics.IMetric;
import pom.metrics.IUnaryMetric;

/**
 * @author Foutse Khomh
 * @since  2007/03/01
 */
public class DSC extends AbstractMetric implements IMetric, IUnaryMetric {
	protected double concretelyCompute(
		final IAbstractModel anAbstractModel,
		final IFirstClassEntity anEntity) {

		//       int numb=0;
		//       final Iterator iter =
		//			this.anAbstractModel.getIteratorOnTopLevelEntities(); 
		//       while (iter.hasNext()) {
		//			final IEntity anElement = (IEntity) iter.next();
		//			
		//			if(anElement instanceof IClass){
		//				numb=numb+1;
		//			}
		//			}
		//       
		//		return numb;

		return anAbstractModel.getNumberOfTopLevelEntities();
	}
	public String getDefinition() {
		final String def = "Total number of entities in a design.";
		return def;
	}
}
