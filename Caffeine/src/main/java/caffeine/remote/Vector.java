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

import java.util.List;
import java.util.RandomAccess;

/**
 * @version 	0.2
 * @author		Yann-Gaël Guéhéneuc
 */
public final class Vector extends java.util.Vector implements List,
		RandomAccess, Cloneable, java.io.Serializable {

	private static final long serialVersionUID = 2432101711764574488L;

	public static synchronized java.util.Vector newInstance() {
		return Vector.newInstance(10);
	}
	public static synchronized java.util.Vector newInstance(
		final int initialCapacity) {

		return Vector.newInstance(initialCapacity, 0);
	}
	public static synchronized java.util.Vector newInstance(
		final int initialCapacity,
		final int capacityIncrement) {

		return new Vector(initialCapacity, capacityIncrement);
	}

	private Vector(final int initialCapacity, final int capacityIncrement) {
		super(initialCapacity, capacityIncrement);
	}

	public void add(final int index, final Object element) {
		super.add(index, element);
	}
	public synchronized boolean add(final Object o) {
		return super.add(o);
	}
	//	public synchronized boolean addAll(Collection c) {
	//		return super.addAll(c);
	//	}
	//	public synchronized boolean addAll(int index, Collection c) {
	//		return super.addAll(index, c);
	//	}
	public synchronized void addElement(final Object obj) {
		super.addElement(obj);
	}
}
