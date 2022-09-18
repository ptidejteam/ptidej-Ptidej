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
package ptidej.ui.layout.repository.sugiyama;

/**
 * @author: Mohamed Kahla  
 * @since: 02/05/2006
 */
// TODO: What is this class for?
// TODO: Should this class really be here?
public class IntegerCouple implements Comparable {
	private final int index;
	private final double value;

	public IntegerCouple(final int anIndex, final double aValue) {
		this.index = anIndex;
		this.value = aValue;
	}
	public int getIndex() {
		return this.index;
	}
	public double getValue() {
		return this.value;
	}
	public int compareTo(final Object o) {
		if (this.value < ((IntegerCouple) o).getValue())
			return -1;
		if (this.value > ((IntegerCouple) o).getValue())
			return 1;
		return 0; // they are equal
	}
}
