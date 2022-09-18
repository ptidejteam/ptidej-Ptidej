// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: ConstraintEvent.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import java.util.logging.Logger;
import choco.Constraint;
import choco.ContradictionException;

/**
 * A class for constraint revisions in the propagation process.
 */
public class ConstraintEvent implements PropagationEvent {

	/**
	 * The touched constraint.
	 */

	private final Constraint touchedConstraint;

	/**
	 * Specifies if the constraint should be initialized.
	 */

	private boolean initialized = false;

	/**
	 * Returns the priority of the var.
	 */

	private int priority = -1;

	/**
	 * Reference to an object for logging trace statements related to propagation events (using the java.util.logging package)
	 */

	private static Logger logger = Logger.getLogger("choco.prop");

	/**
	 * Constructs a new var with the specified values for the fileds.
	 */

	public ConstraintEvent(
		final Constraint constraint,
		final boolean init,
		final int prio) {
		this.touchedConstraint = constraint;
		this.initialized = init;
		this.priority = prio;
	}

	/**
	 * Clears the var. This should not be called with this kind of var.
	 */

	public void clear() {
		ConstraintEvent.logger
			.warning("Const Awake Event does not need to be cleared !");
	}

	public Object getModifiedObject() {
		return this.touchedConstraint;
	}

	/**
	 * Returns the priority of the var.
	 */

	public int getPriority() {
		return this.priority;
	}

	/**
	 * Testing whether an event is active in the propagation network
	 */

	public boolean isActive(final int idx) {
		return true;
	}

	/**
	 * Returns if the constraint is initialized.
	 */

	public boolean isInitialized() {
		return this.initialized;
	}

	/**
	 * Propagates the var: awake or propagate depending on the init status.
	 * @throws choco.ContradictionException
	 */

	public boolean propagateEvent() throws ContradictionException {
		if (this.initialized) {
			this.touchedConstraint.propagate();
		}
		else {
			this.touchedConstraint.awake();
		}
		return true;
	}

	/**
	 * Sets if the constraint is initialized.
	 */

	public void setInitialized(final boolean init) {
		this.initialized = init;
	}
}
