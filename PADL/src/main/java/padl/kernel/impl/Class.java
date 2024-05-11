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

import padl.kernel.IClass;
import padl.kernel.IEntityMarker;

// Yann 2013/07/17: Accesses!
// Must be public for subclasses in other projects
public class Class extends AbstractClass implements IEntityMarker, IClass {
	private static final long serialVersionUID = 7309059468835692115L;

	public Class(final char[] anID, final char[] aName) {
		super(anID, aName);
	}
}
