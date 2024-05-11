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

import padl.aspectj.kernel.IPointcut;
import padl.kernel.impl.Element;

/**
 * @author Jean-Yves Guyomarc'h
 *
 */
public class Pointcut extends Element implements IPointcut {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7274528226987106694L;

	public Pointcut(final char[] anID) {
		super(anID);
	}

}
