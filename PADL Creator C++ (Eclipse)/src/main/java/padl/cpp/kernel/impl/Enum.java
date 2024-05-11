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

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import padl.cpp.kernel.IEnum;
import padl.kernel.IElement;
import padl.kernel.IEntityMarker;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.FirstClassEntity;

class Enum extends FirstClassEntity implements IEntityMarker, IEnum {
	private static final long serialVersionUID = 1124498017343286793L;

	private boolean forceAbstract = false;
	@SuppressWarnings("rawtypes")
	private final List<?> listOfSuperInterfaces = new ArrayList();

	public Enum(final char[] anID) {
		super(anID);
	}
	public boolean isForceAbstract() {
		return this.forceAbstract;
	}
	public List<?> listOfImplementedEntities() {
		return this.listOfSuperInterfaces;
	}
	public void setAbstract(final boolean aBoolean) {
		this.forceAbstract = aBoolean;
		super.setAbstract(aBoolean);
	}
	public void setVisibility(final int visibility) {
		super.setVisibility(this.isForceAbstract() ? visibility
				| Modifier.ABSTRACT : visibility);
	}
	public String toString() {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString());
		codeEq.append(" Enum ");
		codeEq.append(this.getName());

		// Can enum have super-classes?
		// TODO Remove?
		final Iterator<?> iterator = this.listOfSuperInterfaces.iterator();
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
	public void attachTo(final IElement anElement) {
	}
	public void detach() {
	}
	public IElement getAttachedElement() {
		return null;
	}
}
