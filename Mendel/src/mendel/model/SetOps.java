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
package mendel.model;

import java.util.HashSet;
import java.util.Set;

public class SetOps {

	public static <E> Set<E> union(Set<E> s1, Set<E> s2) {
		Set<E> u = new HashSet<E>(s1);
		u.addAll(s2);
		return u;
	}
	
	public static <E> Set<E> inter(Set<E> s1, Set<E> s2) {
		Set<E> i = new HashSet<E>(s1);
		i.retainAll(s2);
		return i;
	}
	
	public static <E> Set<E> diff(Set<E> s1, Set<E> s2) {
		Set<E> d = new HashSet<E>(s1);
		d.removeAll(s2);
		return d;		
	}
	
	public static float jaccardIndex(Set s1, Set s2) {
		float unionSize = (float) union(s1, s2).size();
		if( unionSize==0 ) { // empty sets are equals! and avoid divide-by-zero error
			return 1;
		} else {
			return inter(s1, s2).size() / unionSize;
		}
	}
	
	public static float jaccardDistance(Set s1, Set s2) {
		return 1 - jaccardIndex(s1, s2);
	}
	
	public static float inclusionIndex(Set included, Set inclusive) {
		return inter(included, inclusive).size() / (float) included.size();
	}

}
