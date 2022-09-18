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
package padl.kernel.impl;

import padl.kernel.IConstructor;
import padl.kernel.IElementMarker;
import padl.kernel.IMethod;

// Angelino Picone 21/08/2012:
// Changed the visibility to public in order to allow other project to extend from this class in the particular case of Eclipse bundle loader (avoid IllegalAccessError).
// TODO Can we do without this ugly hack?
public class Constructor extends Operation implements IElementMarker,
		IConstructor {

	private static final long serialVersionUID = -3313404261410898384L;

	public Constructor(final char[] anID) {
		super(anID);
	}
	public Constructor(final char[] anID, final IMethod anAttachedMethod) {
		super(anID, anAttachedMethod);
	}
	public Constructor(final IMethod anAttachedMethod) {
		super(anAttachedMethod);
	}
}
