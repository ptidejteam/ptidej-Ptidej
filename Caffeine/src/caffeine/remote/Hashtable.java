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
package caffeine.remote;

import java.util.Map;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 */
public final class Hashtable extends java.util.Hashtable {
	private static final long serialVersionUID = -8257844447163010732L;

	public static synchronized java.util.Hashtable newInstance() {
		return Hashtable.newInstance(11);
	}
	public static synchronized java.util.Hashtable newInstance(
		int initialCapacity) {

		return Hashtable.newInstance(initialCapacity, 0.75f);
	}
	public static synchronized java.util.Hashtable newInstance(
		final int initialCapacity,
		final float loadFactor) {

		return new Hashtable(initialCapacity, loadFactor);
	}
	public static synchronized java.util.Hashtable newInstance(final Map t) {
		return new Hashtable(t);
	}

	private Hashtable(final int initialCapacity, final float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	private Hashtable(final Map t) {
		super(t);
	}

	public synchronized Object put(final Object key, final Object value) {
		return super.put(key, value);
	}
}
