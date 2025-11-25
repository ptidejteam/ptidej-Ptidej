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
package ptidej.solver.java.repair;

import java.util.ArrayList;

import choco.AbstractConstraint;
import ptidej.solver.java.Problem;
import ptidej.solver.java.Repair;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayeal Skhiri
 */
public abstract class MemoryRepair extends Repair {
	private ArrayList userRelaxedConstraints;

	public MemoryRepair(final Problem problem) {
		super(problem);
		this.userRelaxedConstraints = new ArrayList();
	}
	public final void remove(final AbstractConstraint constraint) {
		this.userRelaxedConstraints.add(constraint);
	}
	public ArrayList getRemovedConstraints() {
		return this.userRelaxedConstraints;
	}
	public void setRemoveConstraint(final ArrayList removedList) {
		this.userRelaxedConstraints = removedList;
	}
}
