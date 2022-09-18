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

public class Singleton extends BehaviouralMotifModel implements Cloneable,
		IDesignMotifModel {
	private static final long serialVersionUID = -1693694069786129452L;
	private static final char[] SINGLETON = "Singleton".toCharArray();

	public Singleton() {
		super(Singleton.SINGLETON);
	}
	public String getIntent() {
		return MultilingualManager.getString("INTENT", Singleton.class);
	}
	public char[] getName() {
		return Singleton.SINGLETON;
	}
}
