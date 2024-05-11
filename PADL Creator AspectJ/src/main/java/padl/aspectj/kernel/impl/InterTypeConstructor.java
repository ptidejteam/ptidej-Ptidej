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

import padl.aspectj.kernel.IInterTypeConstructor;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.impl.Constructor;

/**
 * @author Jean-Yves Guyomarc'h
 */
public class InterTypeConstructor extends Constructor implements
		IInterTypeConstructor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 155947744126255879L;

	public InterTypeConstructor(final char[] anID) {
		super(anID);
	}

	//	public InterTypeConstructor(final MethodInfo aMethod) {
	//		super(aMethod);
	//	}

	public InterTypeConstructor(
		final char[] anID,
		final IMethod anAttachedMethod) {

		super(anID, anAttachedMethod);
	}

	public InterTypeConstructor(final IMethod attachedMethod) {
		super(attachedMethod);
	}

	//	public InterTypeConstructor(final String anID, final MethodInfo aMethod) {
	//		super(anID, aMethod);
	//	}

	public IFirstClassEntity getTargetEntity() {
		return null;
	}

	public void setTargetEntity(final IFirstClassEntity anEntity) {
	}

}
