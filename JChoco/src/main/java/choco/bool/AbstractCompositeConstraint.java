// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractCompositeConstraint.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.bool;

import choco.AbstractConstraint;
import choco.Constraint;
import choco.Var;

/**
 * An abstract class for all implementations of listeners over search variables.
 */
public abstract class AbstractCompositeConstraint extends AbstractConstraint
		implements CompositeConstraint {
	Constraint[] subConstraints;
	int[] offsets;
	Var[] additionalVars;
	int[] additionalIndices;
}