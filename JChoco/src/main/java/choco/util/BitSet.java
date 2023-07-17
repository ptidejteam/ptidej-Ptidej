// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: BitSet.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.util;

/**
 * Implements utilities for handling bit sets
 */
public class BitSet {

	/**
	 *  tests whether the i-th bit of an search is on or off
	 */
	public static boolean getBit(final int n, final int i) {
		return (n >> i & 1) == 1;
	}

	/**
	 *  returns the heaviest bit on (-1 if none)
	 */
	public static int getHeavierBit(final int n) {
		for (int i = 31; i >= 0; i--) {
			if (BitSet.getBit(n, i)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 *  sets the i-th bit of an search (does nothing if it is already on)
	 */
	public static int setBit(int n, final int i) {
		if (!BitSet.getBit(n, i)) {
			n += 1 << i;
		}
		return n;
	}

	/**
	  *  sets the i-th bit of an search (does nothing if it is already on)
	  */
	public static int unsetBit(int n, final int i) {
		if (BitSet.getBit(n, i)) {
			n -= 1 << i;
		}
		return n;
	}

}
