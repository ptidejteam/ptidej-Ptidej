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
package mendel.metric.branch;

import mendel.metric.DispatchMetric;
import mendel.model.JClassEntity;
import mendel.model.JClassEntity.Group;

public class NoveltyIndex extends DispatchMetric {

	private BranchMeanSet meanSet = new BranchMeanSet();
	
//		public String compute(IEntity entity) {
//		boolean flag = entity.recursiveLookup(true);
//		String res = super.compute(entity);
//		entity.recursiveLookup(flag);
//		return res;
//	}

	public String getName() {
		return "NVI";
	}

	@Override
	public String compute(JClassEntity entity) {
		if( entity.isRootClass() ) {
			return Float.valueOf(1).toString();
		} else {
			Float mean = Float.valueOf(this.meanSet.compute(entity.getSuperClass()));
			return Float.valueOf( entity.count(Group.LOCAL) / mean ).toString();			
		}
	}

}
