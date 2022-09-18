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
package ptidej.solver;

import java.util.Arrays;
import org.apache.commons.lang.ArrayUtils;

/**
 * The SolutionComponent represents a part of a solution
 * to the problem of finding pattern in source code.
 * A SolutionComponent is of the form:
 *     <name> = <value>
 * where <name> is the name of the part and <value> the
 * name of the pattern constituent fullfilling the <name>.
 * 
 * @see ptidej.solver.Solution
 */
public final class OccurrenceComponent {
	public static String computeSimpleName(final String aName) {
		int indexOfMinusSign = aName.indexOf('-');
		if (indexOfMinusSign > 0) {
			return aName.substring(0, indexOfMinusSign);
		}
		else {
			return aName;
		}
	}
	public static char[] computeSimpleName(final char[] aName) {
		int indexOfMinusSign = ArrayUtils.indexOf(aName, '-');
		if (indexOfMinusSign > 0) {
			return ArrayUtils.subarray(aName, 0, indexOfMinusSign);
		}
		else {
			return aName;
		}
	}
	private final String displayName;
	private final String displayValue;
	private final char[] name;
	private final char[] value;

	/**
	 * Creates a part of a solution. A part has a name, a value,
	 * and a reference on the Entity related by this part.
	 */
	public OccurrenceComponent(final char[] name, final char[] value) {
		this.displayName = String.valueOf(name);
		this.displayValue = String.valueOf(value);
		this.name = name;
		this.value = value;
	}
	public boolean equals(final Object object) {
		if (object instanceof OccurrenceComponent) {
			final OccurrenceComponent other = (OccurrenceComponent) object;

			return Arrays.equals(this.getName(), other.getName())
					&& Arrays.equals(this.getValue(), other.getValue());
		}
		return false;
	}
	public String getDisplayName() {
		return this.displayName;
	}
	public String getDisplayValue() {
		return this.displayValue;
	}
	public char[] getName() {
		return this.name;
	}
	public char[] getValue() {
		return this.value;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.name);
		buffer.append(" = ");
		buffer.append(this.value);
		return buffer.toString();
	}
}
