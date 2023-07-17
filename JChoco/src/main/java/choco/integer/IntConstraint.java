// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntConstraint.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:16 $
// Last Contributor:   $Author: guehene $

package choco.integer;

import choco.Constraint;
import choco.ContradictionException;
import choco.model.IIntListener;
import choco.util.IntIterator;

/**
 * An interface for all implementations of listeners using search variables.
 */
public interface IntConstraint extends Constraint, IIntListener {

	public void awakeOnBounds(int varIdx) throws ContradictionException;

	public void awakeOnRemovals(int varIdx, IntIterator deltaDomain)
			throws ContradictionException;

	/**
	 * <i>Network management:</i>
	 *    Accessing the i-th search variable of a constraint.
	 *    @param i index of the variable among all search variables in the constraint. Numbering start from 0 on.
	 *    @return the variable, or null when no such variable is found
	 */

	public IntVar getIntVar(int i);

}
