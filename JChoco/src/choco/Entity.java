// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: Entity.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco;

/**
 * An interface for all objects from constraint programs.
 */
public interface Entity {
	/**
	 * Retrieves the problem of the entity.
	 */

	public Problem getProblem();

	/**
	 * pretty printing of the object. This String is not constant and may depend on the context.
	 * @return a readable string representation of the object
	 */
	public String pretty();

}
