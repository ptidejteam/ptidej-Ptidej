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
package padl.cpp.kernel.impl;

import java.util.Iterator;

import com.ibm.toad.cfparse.utils.Access;

import padl.cpp.kernel.IUnion;
import padl.kernel.IEntityMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.FirstClassEntity;

/**
 * @author Sebastien Robidoux 
 * @author Ward Flores
 * @since 2004/08/10
 */
class Union extends FirstClassEntity implements IEntityMarker, IUnion {
	private static final long serialVersionUID = -4945913029224539105L;

	private boolean forceAbstract = false;

	public Union(final char[] anID) {
		super(anID);
	}

	public boolean isForceAbstract() {
		return this.forceAbstract;
	}

	public void setAbstract(final boolean aBoolean) {
		this.forceAbstract = aBoolean;
		super.setAbstract(aBoolean);
	}

	public void setVisibility(final int visibility) {
		super.setVisibility(
				this.isForceAbstract() ? visibility | Access.ACC_ABSTRACT
						: visibility);
	}

	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString());
		codeEq.append(" Union ");
		codeEq.append(this.getName());
		final Iterator iterator = this.getIteratorOnInheritedEntities();
		if (iterator.hasNext()) {
			codeEq.append(" extends ");
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
