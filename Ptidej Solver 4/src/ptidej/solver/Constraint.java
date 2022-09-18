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

import java.util.Set;
import ptidej.solver.approximation.IApproximations;
import choco.ContradictionException;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public interface Constraint extends choco.Constraint {
	public void awakeOnRem(final int idx, final int index_e);
	public void awakeOnRestoreVal(final int idx, final int val)
			throws ContradictionException;
	public void doAwake() throws ContradictionException;
	// public void setListOfNextOrder(final String[] listOfNextOrder);
	public IApproximations getApproximations();
	public String getName();
	public String getNextConstraint();
	public Class getNextConstraintConstructor(final String nextConstraint);
	public String getSymbol();
	public int getWeight();
	public String getXCommand();
	public boolean isSatisfied();
	public void propagate();
	public Set whyIsFalse();
	public Set whyIsTrue();
}
