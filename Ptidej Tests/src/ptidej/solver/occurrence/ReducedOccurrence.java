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
package ptidej.solver.occurrence;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/09
 */
public class ReducedOccurrence implements Comparable {
	private Map nameValueMap;
	public ReducedOccurrence() {
		this.nameValueMap = new HashMap();
	}
	public void addComponent(
		final char[] aComponentName,
		final char[] aComponentValue) {

		this.nameValueMap.put(String.valueOf(aComponentName), String
			.valueOf(aComponentValue));
	}
	public Set getComponentNames() {
		return this.nameValueMap.keySet();
	}
	public Set getComponentValues() {
		return this.nameValueMap.entrySet();
	}
	public char[] getComponentValue(final char[] aComponentName) {
		return ((String) this.nameValueMap.get(aComponentName)).toCharArray();
	}
	public String getKey() {
		final StringBuffer buffer = new StringBuffer();
		final Iterator keys = this.nameValueMap.keySet().iterator();
		while (keys.hasNext()) {
			final String key = (String) keys.next();
			buffer.append(this.nameValueMap.get(key));
			if (keys.hasNext()) {
				buffer.append('+');
			}
		}
		return buffer.toString();
	}
	public boolean equals(final Object anObject) {
		if (anObject instanceof ReducedOccurrence) {
			return this.equals((ReducedOccurrence) anObject);
		}
		return false;
	}
	public boolean equals(final ReducedOccurrence aPrincipalComponent) {
		return this.nameValueMap.equals(aPrincipalComponent.nameValueMap);
	}
	public int compareTo(final Object anObject) {
		if (anObject instanceof ReducedOccurrence) {
			return this.compareTo((ReducedOccurrence) anObject);
		}
		return 0;
	}
	public int compareTo(final ReducedOccurrence aPrincipalComponent) {
		return this.getKey().compareTo(aPrincipalComponent.getKey());
	}
	public String toString() {
		return this.getKey();
	}
}
