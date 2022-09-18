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
package mendel.part.tool;

import java.util.List;
import java.util.Set;
import java.util.Vector;

import mendel.family.Family;
import mendel.family.Prototype;
import mendel.model.JClassEntity;
import mendel.part.AbstractPart;


/**
 * @author Simon Denier
 * @since Sep 6, 2008
 *
 */
public abstract class AbstractPrototypeTool extends AbstractPart {

	public Object compute(Object entry) {
		Family family = ((Family) entry);
	//		if( children.size()>1 ) {
		Prototype proto = buildPrototype(family.parent());
		family.setPrototype(proto, "prototype");
		family.setPrototype(proto, getUniqueKey());
		return family;			
	//		} else {
	//			return null;
	//		}
	}

	public abstract String getUniqueKey();

	public List<Set<String>> getChildrenInterfaces(JClassEntity parent) {
		Vector<Set<String>> interfaces = new Vector<Set<String>>();
		for (JClassEntity child : parent.getChildren()) {
			interfaces.add(child.getLocalMethods());
		}
		return interfaces;
	}

	public Prototype buildPrototype(JClassEntity parent) {
		return new Prototype(buildPrototypeInterface(getChildrenInterfaces(parent)), getUniqueKey());
	}

	/**
	 * @param childrenInterfaces
	 * @return
	 */
	public abstract Set<String> buildPrototypeInterface(List<Set<String>> childrenInterfaces);
}
