// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractIntConstraint.java,v $
// Version:            $Revision: 1.3 $
// Last Modification:  $Date: 2005/12/30 05:02:42 $
// Last Contributor:   $Author: guehene $

package choco.integer.constraints;

import java.util.logging.Logger;
import choco.AbstractConstraint;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.integer.IntConstraint;
import choco.util.IntIterator;

/**
 * An abstract class for all implementations of listeners over search variables.
 */
public abstract class AbstractIntConstraint extends AbstractConstraint
		implements IntConstraint {

	/**
	 * Reference to an object for logging trace statements related to constraints over integers (using the java.util.logging package)
	 */

	protected static Logger logger = Logger
		.getLogger("choco.integer.constraints");

	public void awakeOnBounds(final int varIndex) throws ContradictionException {
		this.awakeOnInf(varIndex);
		this.awakeOnSup(varIndex);
	}

	/**
	 * Default propagation on improved lower bound: propagation on domain revision.
	 */

	public void awakeOnInf(final int idx) throws ContradictionException {
		this.awakeOnVar(idx);
	}

	/**
	 * Default propagation on instantiation: full constraint re-propagation.
	 */

	public void awakeOnInst(final int idx) throws ContradictionException {
		this.propagate();
	}

	/**
	 * Default propagation on one value removal: propagation on domain revision.
	 */

	public void awakeOnRem(final int idx, final int x)
			throws ContradictionException {
		this.awakeOnVar(idx);
	}

	/**
	 * The default implementation of propagation when a variable has been modified
	 * consists in iterating all values that have been removed (the delta domain)
	 * and propagate them one after another, incrementally.
	 *
	 * @param idx
	 * @throws choco.ContradictionException
	 */
	public void awakeOnRemovals(final int idx, final IntIterator deltaDomain)
			throws ContradictionException {
		if (deltaDomain != null) {
			for (; deltaDomain.hasNext();) {
				final int val = deltaDomain.next();
				this.awakeOnRem(idx, val);
			}
		}
		else {
			this.awakeOnVar(idx);
		}
	}

	/**
	 * Default propagation on improved upper bound: propagation on domain revision.
	 */

	public void awakeOnSup(final int idx) throws ContradictionException {
		this.awakeOnVar(idx);
	}

	/**
	 * returns the (global) index of the constraint among all constraints of the problem
	 */
	public int getSelfIndex() {
		final Problem pb = this.getProblem();
		for (int i = 0; i < pb.getNbIntConstraints(); i++) {
			final Constraint c = pb.getIntConstraint(i);
			if (c == this) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Checks if all the variables are instantiated.
	 */

	public boolean isCompletelyInstantiated() {
		final int n = this.getNbVars();
		for (int i = 0; i < n; i++) {
			if (!this.getIntVar(i).isInstantiated()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * tests if the constraint is consistent with respect to the current state of domains
	 *
	 * @return true if the constraint is entailed (default approximate definition)
	 */
	public boolean isConsistent() {
		return this.isEntailed() == Boolean.TRUE;
	}

}
