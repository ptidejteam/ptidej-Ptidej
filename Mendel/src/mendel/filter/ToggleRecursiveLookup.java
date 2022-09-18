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
 * 
 */
package mendel.filter;

import mendel.model.IEntity;

public class ToggleRecursiveLookup extends EntityFilter {
	
	private boolean recursive;
	
	public ToggleRecursiveLookup(boolean recursive) {
		this.recursive = recursive;
	}
	
	public boolean accept(IEntity entity) {
		entity.recursiveLookup(this.recursive);
		return true;
	}
	public String toString() {
		return "Toggle Recursive Lookup " + this.recursive;
	}
}
