// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IConstraintPlugin.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.model;

/**
 * An interface for all objects that can be plugged to a constraint.
 */
public interface IConstraintPlugin {
	public void activateListener();

	public void addListener();

	public void deactivateListener();

	public void deactivateListener(int varIndex);
}
