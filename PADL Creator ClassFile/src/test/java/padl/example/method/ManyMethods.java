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
package padl.example.method;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/02/09
 */
public final class ManyMethods {
	public ManyMethods() {
		this(0);
	}
	public ManyMethods(final int i) {
		super();
	}
	public ManyMethods(final Object o) {
		super();
	}

	public String toString() {
		return "";
	}
	public String toString(final int tab) {
		return "" + tab;
	}
}
