// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: Arithm.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.util;

import org.junit.Assert;

/**
 * Implements utilities for arithmetic (TODO: should disappear once I know Java better)
 */
public class Arithm {
	public static int divCeil(final int a, final int b) {
		Assert.assertTrue(b > 0);
		if (a > 0) {
			return (a + b - 1) / b;
		}
		else {
			return a / b;
		}
	}

	public static int divFloor(final int a, final int b) {
		Assert.assertTrue(b > 0);
		if (a > 0) {
			return a / b;
		}
		else {
			return (a - b + 1) / b;
		}
	}

	public static int max(final int a, final int b) {
		if (a >= b) {
			return a;
		}
		else {
			return b;
		}
	}

	public static int min(final int a, final int b) {
		if (a <= b) {
			return a;
		}
		else {
			return b;
		}
	}

}
