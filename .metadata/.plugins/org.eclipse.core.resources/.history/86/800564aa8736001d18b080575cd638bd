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
package padl.kernel.cpp.antlr.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IEntityMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.cpp.antlr.IEnum;
import padl.kernel.impl.FirstClassEntity;
import com.ibm.toad.cfparse.utils.Access;

/**
 * @author Sébastien Robidoux 
 * @author Ward Flores
 * @since 2004/08/18
 */
class Enum extends FirstClassEntity implements IEntityMarker, IEnum {
	private static final long serialVersionUID = 1124498017343286793L;

	private boolean forceAbstract = false;
	private final List listOfSuperInterfaces = new ArrayList();

	public Enum(final char[] anID) {
		super(anID);
	}
	public boolean isForceAbstract() {
		return this.forceAbstract;
	}
	public List listOfImplementedEntities() {
		return this.listOfSuperInterfaces;
	}
	public void setAbstract(final boolean aBoolean) {
		this.forceAbstract = aBoolean;
		super.setAbstract(aBoolean);
	}
	public void setVisibility(final int visibility) {
		super.setVisibility(this.isForceAbstract() ? visibility
				| Access.ACC_ABSTRACT : visibility);
	}
	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString());
		codeEq.append(" Enum ");
		codeEq.append(this.getName());
		Iterator iterator = this.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			codeEq.append(" extends ");
			while (iterator.hasNext()) {
				codeEq.append(((IFirstClassEntity) iterator.next()).getName());
				if (iterator.hasNext()) {
					codeEq.append(", ");
				}
			}
		}
		iterator = this.listOfSuperInterfaces.iterator();
		if (iterator.hasNext()) {
			codeEq.append(" implements ");
			while (iterator.hasNext()) {
				codeEq.append(((IFirstClassEntity) iterator.next()).getName());
				if (iterator.hasNext()) {
					codeEq.append(", ");
				}
			}
		}
		return codeEq.toString();
	}
}
