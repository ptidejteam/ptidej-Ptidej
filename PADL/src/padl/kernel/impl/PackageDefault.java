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

import padl.kernel.Constants;
import padl.kernel.IGhost;
import padl.kernel.IPackageDefault;
import padl.kernel.IPackageGhost;

public class PackageDefault extends Package implements IPackageDefault {
	private static final long serialVersionUID = 5993857112698114446L;

	public PackageDefault() {
		super(Constants.DEFAULT_PACKAGE_ID);
		this.setName(new char[0]);
	}
	public void addConstituent(final IGhost aGhost) {
		super.addConstituent(aGhost);
	}
	public void addConstituent(final IPackageGhost aPackageGhost) {
		super.addConstituent(aPackageGhost);
	}

	public String toString(final int tab) {
		final StringBuffer codeEq = new StringBuffer();
		codeEq.append(super.toString(tab));
		codeEq.append('<');
		codeEq.append(this.getID());
		codeEq.append('>');
		return codeEq.toString();
	}
}
