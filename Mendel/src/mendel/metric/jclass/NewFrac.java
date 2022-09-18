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
package mendel.metric.jclass;

import mendel.metric.DispatchMetric;
import mendel.model.JClassEntity;
import mendel.model.JClassEntity.Category;

public class NewFrac extends DispatchMetric {

//	public String compute(IEntity entity) {
//		boolean flag = entity.recursiveLookup(true);
//		String res = super.compute(entity);
//		entity.recursiveLookup(flag);
//		return res;
//	}

	public String getName() {
		return "F New/Total";
	}

	@Override
	public String compute(JClassEntity entity) {
		return Float.valueOf( entity.frac(Category.NEW) ).toString();
	}

}
