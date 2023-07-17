// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: AbstractPropagationEngine.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.prop;

import java.util.logging.Logger;
import choco.AbstractEntity;
import choco.Entity;
import choco.Problem;

/**
 * An abstract class for all implementations of propagation engines.
 */
public abstract class AbstractPropagationEngine extends AbstractEntity
		implements PropagationEngine {

	/**
	 * Storing the cause of the last contradiction.
	 */

	protected Entity contradictionCause;

	/**
	 * Reference to an object for logging trace statements related to propagation events (using the java.util.logging package)
	 */

	protected static Logger logger = Logger.getLogger("choco.prop");

	protected AbstractPropagationEngine(final Problem pb) {
		this.problem = pb;
	}

	/**
	 * Retrieving the cause of the last contradiction.
	 */

	public Entity getContradictionCause() {
		return this.contradictionCause;
	}

	/**
	 * Gets the next queue from which a var will be propagated.
	 */

	public EventQueue getNextActiveEventQueue() {
		return null;
	}

	/**
	 * Retrieving the overall problem.
	 */
	public Problem getProblem() {
		return this.problem;
	}

	/**
	 * Store the cause of the last contradiction.
	 */

	public void setContradictionCause(final Entity cause) {
		this.contradictionCause = cause;
	}

	/**
	 * Erase the cause of the last contradiction.
	 */

	public void setNoContradictionCause() {
		this.contradictionCause = null;
	}

}
