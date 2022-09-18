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

import padl.kernel.IGhost;
import padl.kernel.IPackageGhost;

public class PackageGhost extends Package implements IPackageGhost {
	private static final long serialVersionUID = 5993857112698114446L;
	public PackageGhost(final char[] anID) {
		super(anID);
	}

	public void addConstituent(final IGhost aGhost) {
		super.addConstituent(aGhost);
	}
	public void addConstituent(final IPackageGhost aPackageGhost) {
		super.addConstituent(aPackageGhost);
	}
}
