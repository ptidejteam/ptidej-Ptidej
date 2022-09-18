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

import padl.aspectj.kernel.IInterTypeMethod;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.impl.Method;

/**
 * @author Jean-Yves Guyomarc'h
 */
public class InterTypeMethod extends Method implements IInterTypeMethod {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6216609307716703816L;
	private IFirstClassEntity target = null;
	private IMethod method = null;

	public InterTypeMethod(final char[] anID) {
		super(anID);
	}

	//	public InterTypeMethod(MethodInfo aMethod) {
	//		super(aMethod);
	//	}

	public InterTypeMethod(final char[] anID, final IMethod anAttachedMethod) {
		super(anID, anAttachedMethod);
	}

	public InterTypeMethod(final IMethod attachedMethod) {
		super(attachedMethod);
	}

	//	public InterTypeMethod(String anID, MethodInfo aMethod) {
	//		super(anID, aMethod);
	//	}

	public IMethod getMethod() {
		return this.method;
	}

	public IFirstClassEntity getTargetEntity() {
		return this.target;
	}

	public void setMethod(final IMethod method) {
		this.method = method;
	}

	public void setTargetEntity(final IFirstClassEntity anEntity) {
		this.target = anEntity;

	}

}
