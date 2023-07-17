// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IIntListener.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.model;

import choco.ContradictionException;

/**
 * An interface for all the search variable listeners.
 */
public interface IIntListener extends IVarListener {

	/**
	 * Default propagation on improved lower bound: propagation on domain revision.
	 */

	public void awakeOnInf(int varIdx) throws ContradictionException;

	/**
	 * Default propagation on instantiation: full constraint re-propagation.
	 */

	public void awakeOnInst(int varIdx) throws ContradictionException;

	/**
	 * Default propagation on one value removal: propagation on domain revision.
	 */

	public void awakeOnRem(int varIdx, int val) throws ContradictionException;

	/**
	 * Default propagation on improved upper bound: propagation on domain revision.
	 */

	public void awakeOnSup(int varIdx) throws ContradictionException;
}
