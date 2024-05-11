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
package padl.aspectj.kernel.impl;

import padl.aspectj.kernel.IInterTypeDeclareParents;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Element;

/**
 * @author Jean-Yves
 */
public class InterTypeDeclareParents extends Element implements
		IInterTypeDeclareParents {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7447403607229450032L;
	private IFirstClassEntity target;
	private IFirstClassEntity declaredParent;

	public InterTypeDeclareParents(final char[] anID) {
		super(anID);
		this.target = null;
		this.declaredParent = null;
	}

	public IFirstClassEntity getDeclaredParent() {
		return this.declaredParent;
	}

	public IFirstClassEntity getTargetEntity() {
		return this.target;
	}

	public void setDeclaredParent(final IFirstClassEntity anEntity) {
		this.declaredParent = anEntity;

	}

	public void setTargetEntity(final IFirstClassEntity anEntity) {
		this.target = anEntity;

	}

}
