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
package padl.cpp.util;

import java.util.Iterator;

public class EmptyIterator<E> implements Iterator<E> {
	@Override
	public boolean hasNext() {
		return false;
	}
	@Override
	public E next() {
		return null;
	}
	@Override
	public void remove() {
	}
}
