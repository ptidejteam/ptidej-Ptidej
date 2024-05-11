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
package padl.motif.models;

import padl.motif.IDesignMotifModel;
import padl.motif.util.adapter.DesignMotifModelAdapter;

public abstract class ArchitecturalQualityMotifModel extends
		DesignMotifModelAdapter {

	private static final long serialVersionUID = 4820278653444785437L;
	public ArchitecturalQualityMotifModel(final char[] aName) {
		super(aName);
	}
	public final int getClassification() {
		return IDesignMotifModel.DEFECT_MOTIF;
	}
}
