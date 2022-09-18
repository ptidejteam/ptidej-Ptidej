/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.combination;

import java.util.Vector;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public final class Combination {
	public static Vector C(int n, int p) {
		/*
		 * The number of combinations of p elements within n is equal to:
		 *        p     n!
		 *      C  = --------
		 *        n  p!(n-p)!
		 */
		final int numberOfPermutations =
			factorial(n) / (factorial(p) * factorial(n - p));

		/*
		 * (From Canada's SchoolNet website:
		 *  www.schoolnet.ca/vp-pv/ECOS/index.html)
		 * The computation of the combination of p elements within n
		 * is easily done using bitstring.
		 *
		 * The idea is that for two elements within four, the results
		 * are (in bit strings and sub-sets):
		 *
		 *     +------+--------+
		 *     | 0011 | {1, 2} |
		 *     | 0101 | {1, 3} |
		 *     | 0110 | {2, 3} |
		 *     | 1001 | {1, 4} |
		 *     | 1010 | {2, 4} |
		 *     | 1100 | {3, 4} |
		 *     +------+--------+
		 */

		final BitString bitString = new BitString(n);
		final Vector solutions = new Vector(numberOfPermutations);

		/*
		 * I compute the solutions by counting the number of
		 * ones in the bit string: If the number of ones is equal
		 * to p, then the current value of the bit string represents
		 * a solution. I obtain a solution from the bit string:
		 * The indexes of the ones compose the solution.
		 */
		while (solutions.size() != numberOfPermutations) {
			if (Combination.getNumberOfOnes(bitString.getString()) == p) {
				solutions.add(
					0,
					Combination.getIndexesOfOnes(bitString.getString()));
			}
			bitString.inc();
		}

		return solutions;
	}
	private static int factorial(final int n) {
		int factorial = 1;
		for (int i = 1; i <= n; i++) {
			factorial *= i;
		}
		return factorial;
	}
	private static Vector getIndexesOfOnes(final String bitString) {
		final Vector solution = new Vector();
		for (int i = 0; i < bitString.length(); i++) {
			if (bitString.charAt(i) == '1') {
				solution.add(Integer.valueOf(i + 1));
			}
		}
		return solution;
	}
	private static int getNumberOfOnes(final String bitString) {
		int result = 0;
		for (int i = 0; i < bitString.length(); i++) {
			if (bitString.charAt(i) == '1') {
				result++;
			}
		}
		return result;
	}
	public static void main(final String[] args) {
		final int NB_CONSTRAINTS = 4;
		for (int i = 1; i <= NB_CONSTRAINTS; i++) {
			System.out.println(Combination.C(NB_CONSTRAINTS, i));
		}
	}
}
