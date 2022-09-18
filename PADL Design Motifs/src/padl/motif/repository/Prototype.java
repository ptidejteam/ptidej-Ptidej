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

import padl.motif.IDesignMotifModel;
import padl.motif.models.BehaviouralMotifModel;
import util.multilingual.MultilingualManager;

public class Prototype extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final char[] PROTOTYPE = "Prototype".toCharArray();
	private static final long serialVersionUID = 520438833419263601L;

	public Prototype() {
		super(Prototype.PROTOTYPE);
	}
	public String getIntent() {
		return MultilingualManager.getString("INTENT", Prototype.class);
	}
	public char[] getName() {
		return Prototype.PROTOTYPE;
	}
}
