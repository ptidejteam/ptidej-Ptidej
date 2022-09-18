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
import mendel.model.JInterfaceEntity;

public class LeafOrNode extends DispatchMetric {

	public String getName() {
		return "Tree";
	}

	@Override
	public String compute(JClassEntity entity) {
		return entity.isLeaf() ? "L" : "N";
	}

	@Override
	public String compute(JInterfaceEntity entity) {
		return entity.hasSubentities() ? "L" : "N";
	}

}
