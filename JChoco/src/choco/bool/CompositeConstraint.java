// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: CompositeConstraint.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.bool;

import choco.Constraint;
import choco.integer.IntConstraint;

/**
 * An interface for all implementations of listeners using search variables.
 */
public interface CompositeConstraint extends Constraint, IntConstraint {

	/**
	 * return the index of the subconstraint where the i-th variable is referenced
	 * @param varIdx the overall index of the variable (among all variables of the combination
	 * @return the index of the subconstraint involving the variable
	 *         or -1 if none can be found (which would be a definite bug !)
	 */
	public int getSubConstraintIdx(int varIdx);

}
