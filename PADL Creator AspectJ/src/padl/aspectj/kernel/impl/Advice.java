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

import padl.aspectj.kernel.IAdvice;
import padl.kernel.IFirstClassEntity;
import padl.kernel.impl.Element;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public class Advice extends Element implements IAdvice {
	private static final long serialVersionUID = -8098735233777241698L;

	public Advice(final char[] anID) {
		super(anID);
	}

	/* (non-Javadoc)
	 * @see padl.kernel.IAdvice#getTargets()
	 */
	public IFirstClassEntity[] getTargets() {
		return null;
	}

	/* (non-Javadoc)
	 * @see padl.kernel.IAdvice#setTargets(padl.kernel.IEntity[])
	 */
	public void setTargets(final IFirstClassEntity[] entities) {

	}

}
