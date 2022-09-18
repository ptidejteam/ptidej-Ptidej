// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntDomain.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:16 $
// Last Contributor:   $Author: guehene $

package choco.integer.var;

import choco.Entity;
import choco.util.IntIterator;

/**
 * An interface for all domains of search variables
 */
public interface IntDomain extends Entity {

	/**
	 * cleans the data structure implementing the delta domain
	 */
	public void clearDeltaDomain();

	/**
	 * Testing whether an search value is contained within the domain.
	 */

	public boolean contains(int x);

	/**
	 * The delta domain container is "frozen" (it can no longer accept new value removals)
	 * so that this set of values can be iterated as such
	 */
	public void freezeDeltaDomain();

	/**
	 * Returns an getIterator over the set of values that have been removed from the domain since the last propagation
	 */
	public IntIterator getDeltaIterator();

	/**
	 * Access the minimal value stored in the domain.
	 */

	public int getInf();

	/**
	 * Retrieve an getIterator for traversing the sequence of values contained in the domain
	 */

	public IntIterator getIterator();

	/**
	 * Accessing the smallest value stored in the domain and strictly greater
	 * than <i>x</i>.
	 * Does not require <i>x</i> to be in the domain.
	 */

	public int getNextValue(int x);

	/**
	 * Accessing the largest value stored in the domain and strictly smaller
	 * than <i>x</i>.
	 * Does not require <i>x</i> to be in the domain.
	 */

	public int getPrevValue(int x);

	/**
	 * Draws a value at random from the domain.
	 */

	public int getRandomValue();

	/**
	 * checks whether the delta domain has indeed been released (ie: chechks that no domain updates are pending)
	 */
	public boolean getReleasedDeltaDomain();

	/**
	 * Access the total number of values stored in the domain.
	 */

	public int getSize();

	/**
	 * Access the maximal value stored in the domain/
	 */

	public int getSup();

	/**
	 * Testing whether there are values in the domain that are strictly greater
	 * than <i>x</i>.
	 * Does not require <i>x</i> to be in the domain.
	 */

	public boolean hasNextValue(int x);

	/**
	 * Testing whether there are values in the domain that are strictly smaller
	 * than <i>x</i>.
	 * Does not require <i>x</i> to be in the domain.
	 */

	public boolean hasPrevValue(int x);

	public boolean isEnumerated();

	/**
	 * after an iteration over the delta domain, the delta domain is reopened again.
	 * @return true iff the delta domain is reopened empty (no updates have been made to the domain
	 * while it was frozen, false iff the delta domain is reopened with pending value removals (updates
	 * were made to the domain, while the delta domain was frozen).
	 */
	public boolean releaseDeltaDomain();

	/**
	 * Removing a single value from the domain.
	 */

	public boolean remove(int x);

	/**
	 * Restricting the domain to a singleton
	 */

	public void restrict(int x);

	/**
	 * Augment the minimal value stored in the domain.
	 * returns the new lower bound (<i>x</i> or more, in case <i>x</i> was
	 * not in the domain)
	 */

	public int updateInf(int x);

	/**
	 * Diminish the maximal value stored in the domain.
	 * returns the new upper bound (<i>x</i> or more, in case <i>x</i> was
	 * not in the domain).
	 */

	public int updateSup(int x);

}
