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

import padl.cpp.kernel.IDestructor;
import padl.kernel.IElementMarker;
import padl.kernel.IMethod;
import padl.kernel.impl.Constructor;

/**
 * @author Sébastien Robidoux
 * @since  2004/08/10
 * Simple Copy/Paste of Constructor
 */
class Destructor extends Constructor implements IElementMarker, IDestructor {
	private static final long serialVersionUID = 6658205364561982696L;

	public Destructor(final char[] anID, final char[] aName) {
		super(anID);
		this.setName(aName);
	}
	public Destructor(final char[] anID, final IMethod anAttachedMethod) {
		super(anID, anAttachedMethod);
	}
	public Destructor(final IMethod anAttachedMethod) {
		super(anAttachedMethod);
	}
}
