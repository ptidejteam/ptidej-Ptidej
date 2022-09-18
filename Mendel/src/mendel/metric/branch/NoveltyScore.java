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

public class NoveltyScore extends DispatchMetric {

//	private BranchMeanSet meanSet = new BranchMeanSet();
	
	private NoveltyIndex novelty = new NoveltyIndex();
	
	public String getName() {
		return "NVS";
	}

	@Override
	public String compute(JClassEntity entity) {
//		int localG = entity.count(Group.LOCAL);
		Float nvi = Float.valueOf(this.novelty.compute(entity));
//		Float mean = Float.valueOf(this.meanSet.compute(entity.getSuperEntity()));
		return Float.valueOf( entity.count(Group.LOCAL) * nvi ).toString();
	}

}
