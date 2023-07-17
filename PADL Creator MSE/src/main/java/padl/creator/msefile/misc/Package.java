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
package padl.creator.msefile.misc;

import java.util.Map;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/06/10
 */
public class Package {
	private final String name;
	private final String enclosingPackageRef;

	public Package(final String aName) {
		this.name = aName;
		this.enclosingPackageRef = null;
	}
	public Package(final String aName, final String anEnclosingPackageRef) {
		this.name = aName;
		this.enclosingPackageRef = anEnclosingPackageRef;
	}
	public String getFullName(final Map aMapOfRefedPackages) {
		return this.getFullName0(aMapOfRefedPackages, this.enclosingPackageRef);
	}
	private String getFullName0(
		final Map aMapOfRefedPackages,
		final String anEnclosingPackageRef) {

		final Package enclosingPackage =
			(Package) aMapOfRefedPackages.get(anEnclosingPackageRef);
		if (enclosingPackage.enclosingPackageRef == null) {
			return enclosingPackage.getSimpleName();
		}
		else {
			return this.getFullName0(
				aMapOfRefedPackages,
				enclosingPackage.enclosingPackageRef)
					+ '.' + enclosingPackage.getSimpleName();
		}
	}
	public String getSimpleName() {
		return this.name;
	}
	public String toString() {
		return this.name;
	}
}
