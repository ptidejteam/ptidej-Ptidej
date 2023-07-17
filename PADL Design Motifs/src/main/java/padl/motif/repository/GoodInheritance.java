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
package padl.motif.repository;

import padl.kernel.IClass;
import padl.motif.IDesignMotifModel;
import padl.motif.models.ArchitecturalQualityMotifModel;
import util.multilingual.MultilingualManager;

public final class GoodInheritance extends ArchitecturalQualityMotifModel
		implements Cloneable, IDesignMotifModel {
	private static final char[] GOOD_INHERITANCE = "GoodInheritance"
		.toCharArray();
	private static final long serialVersionUID = -53928198062516626L;
	private static final char[] SUBCLASS = "Subclass".toCharArray();
	private static final char[] SUPERCLASS = "Superclass".toCharArray();

	public GoodInheritance() {
		super(GoodInheritance.GOOD_INHERITANCE);

		final IClass superclass =
			this.getFactory().createClass(
				GoodInheritance.SUPERCLASS,
				GoodInheritance.SUPERCLASS);
		final IClass subclass =
			this.getFactory().createClass(
				GoodInheritance.SUBCLASS,
				GoodInheritance.SUBCLASS);
		subclass.addInheritedEntity(superclass);
		this.addConstituent(superclass);
		this.addConstituent(subclass);
	}

	public String getIntent() {
		return MultilingualManager.getString("INTENT", GoodInheritance.class);
	}

	public char[] getName() {
		return GoodInheritance.GOOD_INHERITANCE;
	}
}
