// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: ContradictionException.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco;

import choco.prop.PropagationEngine;

/**
 * An exception thrown when a contradiction achieved.
 */
public class ContradictionException extends Exception {
	/**
	 * An exception may have a local cause (the last variable
	 * / constraint responsible for the failure)
	 */

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	   * Constructs a new contradiction with the specified cause.
	   *  @param cause the the last object (variable, constraint) responsible
	   * for the failure of propagation
	   */

	public ContradictionException(final Entity cause) {
		final PropagationEngine pe = cause.getProblem().getPropagationEngine();
		pe.setContradictionCause(cause);
		pe.flushEvents();
	}

	/**
	 * Contradiction without any identifiable cause
	 */

	public ContradictionException(final Problem p) {
		final PropagationEngine pe = p.getPropagationEngine();
		pe.setNoContradictionCause();
		pe.flushEvents();
	}
}
