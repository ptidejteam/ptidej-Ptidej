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
package padl.util;

import java.io.Serializable;
import java.util.Comparator;

/**
 * 
 * @author Yann
 * @date   2010/06/21 
 *
 */
public class CharArrayComparator implements Comparator, Serializable {
	private static final long serialVersionUID = -3215432399567382906L;
	private static CharArrayComparator UniqueInstance;
	public static CharArrayComparator getInstance() {
		if (CharArrayComparator.UniqueInstance == null) {
			CharArrayComparator.UniqueInstance = new CharArrayComparator();
		}
		return CharArrayComparator.UniqueInstance;
	}

	private CharArrayComparator() {
	}
	public int compare(final Object o1, final Object o2) {
		final char[] s1 = (char[]) o1;
		final char[] s2 = (char[]) o2;
		final int n1 = s1.length, n2 = s2.length;
		for (int i1 = 0, i2 = 0; i1 < n1 && i2 < n2; i1++, i2++) {
			final char c1 = s1[i1];
			final char c2 = s2[i2];
			if (c1 != c2) {
				return c1 - c2;
			}
		}
		return n1 - n2;
	}
}
