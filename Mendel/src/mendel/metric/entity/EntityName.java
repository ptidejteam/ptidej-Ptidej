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
package mendel.metric.entity;

import mendel.metric.IEntityMetric;
import mendel.model.IEntity;

public class EntityName implements IEntityMetric {

	public String compute(IEntity entity) {
		return entity.getEntityName();
	}

	public String getName() {
		return "Entity name";
	}

}
