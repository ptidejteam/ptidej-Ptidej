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
package padl.analysis.repository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.kernel.IAbstractModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.kernel.impl.Factory;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/10/02
 * @deprecated Because the PADL meta-model now supports packages natively. 
 */
public class PackageIdentifierAnalysis implements IAnalysis {
	private static String getPackageName(final IFirstClassEntity anEntity) {
		return anEntity.getDisplayID().substring(
			0,
			anEntity.getDisplayID().lastIndexOf('.'));
	}

	private final Map packageNames = new HashMap();

	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		// Yann 2005/10/07: Co-modification.
		// I copy the current list of actors to be allowed to
		// add/remove entities and packages from it during the
		// building of packages.
		// Yann 2005/10/12: Iterator!
		// I have now an iterator able to iterate over a
		// specified type of constituent of a list.
		final Iterator iterator =
			anAbstractModel.getIteratorOnTopLevelEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity firstClassEntity =
				(IFirstClassEntity) iterator.next();
			final String packageName =
				PackageIdentifierAnalysis.getPackageName(firstClassEntity);

			IPackage packaje = (IPackage) this.packageNames.get(packageName);
			if (packaje == null) {
				packaje =
					Factory.getInstance().createPackage(
						packageName.toCharArray());
				anAbstractModel.addConstituent(packaje);
				this.packageNames.put(packageName, packaje);
			}

			anAbstractModel
				.removeTopLevelEntityFromID(firstClassEntity.getID());
			packaje.addConstituent(firstClassEntity);
		}
		return anAbstractModel;
	}
	public String getName() {
		return "Package builder";
	}
}
