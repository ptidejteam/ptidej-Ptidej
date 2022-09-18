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
package mendel.metric;

import mendel.model.IEntity;
import mendel.model.JClassEntity;
import mendel.model.JInterfaceEntity;


/*
 * Deviant Visitor, using RTTI rather than double-dispatch by Element-Visitor
 */
public abstract class DispatchMetric implements IEntityMetric {
	
	public String compute(IEntity entity) {
		if (entity instanceof JClassEntity)
			return compute((JClassEntity) entity);
		if (entity instanceof JInterfaceEntity)
			return compute((JInterfaceEntity) entity);
		return "";
	}


	public String compute(JClassEntity entity) {
		return "";
	}
	
	public String compute(JInterfaceEntity entity) {
		return "";
	}
	
}
