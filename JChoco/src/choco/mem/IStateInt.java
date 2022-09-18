// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IStateInt.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.mem;

/**
 * Describes an search with states (describing some history of the data structure).
 */
public interface IStateInt {
	/**
	 * Value for an unknown search.
	 */

	public static final int UNKNOWN_INT = Integer.MAX_VALUE;

	/**
	 * Minimum value an search can be equal to.
	 */

	public static final int MININT = Integer.MIN_VALUE;

	/**
	 * Maximum value an search can be equal to.
	 */

	public static final int MAXINT = Integer.MAX_VALUE - 1;

	/**
	 * modifying a StoredInt by an increment
	 * @param delta
	 */
	public void add(int delta);

	/**
	 * Returns the current value.
	 */

	public int get();

	/**
	 * Retrieving the environment
	 */

	public Environment getEnvironment();

	/**
	 * Checks if a value is currently stored.
	 */

	public boolean isKnown();

	/**
	 * Modifies the value and stores if needed the former value on the
	 * trailing stack.
	 */

	public void set(int y);

}
