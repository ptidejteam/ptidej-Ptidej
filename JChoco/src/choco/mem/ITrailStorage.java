// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: ITrailStorage.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

/**
 * An interface for classes implementing trails of modifications to objects.
 *
 * Toutes les classes doivent implementer les fonctions de l'interface pour
 * permettre a l'environnement de deleguer la gestion des mondes pour chaque type
 * de donnee.
 */
public interface ITrailStorage {

	/**
	 * Retrieving the size of the trail (number of saved past values).
	 *
	 */

	public int getSize();

	/**
	 * increase the capacity of the environment to a given number of worlds
	 * @param newWorldCapacity
	 */
	public void resizeWorldCapacity(int newWorldCapacity);

	/**
	 * Comitting the current world: merging it with the previous one.
	 *
	 * Not used yet.
	 */

	public void worldCommit();

	/**
	 * Moving down to the previous world.
	 *
	 * Cette methode reattribute a la variable ou l'element d'un tableau sa valeur
	 * precedente.
	 */

	public void worldPop();

	/**
	 * Moving up to the next world.
	 *
	 * Cette methode doit garder l'etat de la variable avant la modification
	 * de sorte a la remettre en etat le cas echeant.
	 */

	public void worldPush();

}
