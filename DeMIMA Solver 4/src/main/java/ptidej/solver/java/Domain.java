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
package ptidej.solver.java;

import choco.integer.var.IntDomainVar;
import choco.palm.integer.PalmBitSetIntDomain;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 * @since  2010/07/21
 */
public class Domain extends PalmBitSetIntDomain {
	//	private final TIntSet setOfValues;
	public Domain(final IntDomainVar v, final int a, final int b) {
		super(v, a, b);
		//	this.setOfValues = new TIntHashSet(b - a + 1);
		//	for (int i = a; i <= b; i++) {
		//		this.setOfValues.add(i);
		//	}
	}
	protected void addIndex(final int i) {
		super.addIndex(i);
		//	this.setOfValues.add(i);
	}
	protected void removeIndex(final int i) {
		super.removeIndex(i);
		//	this.setOfValues.remove(i);
	}
	//	public TIntSet getSetOfValues() {
	//		return this.setOfValues;
	//	}
}
