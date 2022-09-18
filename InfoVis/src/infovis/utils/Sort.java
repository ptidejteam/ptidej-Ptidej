/*****************************************************************************
 * Copyright (C) 2003 Jean-Daniel Fekete and INRIA, France                   *
 * ------------------------------------------------------------------------- *
 * This software is published under the terms of the QPL Software License    *
 * a copy of which has been included with this distribution in the           *
 * license-infovis.txt file.                                                 *
 *****************************************************************************/
package infovis.utils;


/**
 * Utilities for sorting int tables.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class Sort {
    /**
    * Sorts a table of integers according to a comparator
    *
    * @param nodes the table of integers.
    * @param comp the comparator.
    */
    public static void sort(int[] nodes, RowComparator comp) {
	sort(nodes, 0, nodes.length, comp);
    }

    public static void sort(int[] x, int off, int len, RowComparator comp) {
	// Insertion sort on smallest arrays
	if (len < 7) {
	    for (int i = off; i < (len + off); i++)
		for (int j = i;
		     (j > off) && (comp.compare(x[j], x[j - 1]) < 0);
		     j--)
		    swap(x, j, j - 1);

	    return;
	}

	// Choose a partition element, v
	int m = off + (len >> 1); // Small arrays, middle element

	if (len > 7) {
	    int l = off;
	    int n = (off + len) - 1;

	    if (len > 40) { // Big arrays, pseudomedian of 9
		int s = len / 8;
		l = med3(x, l, l + s, l + (2 * s), comp);
		m = med3(x, m - s, m, m + s, comp);
		n = med3(x, n - (2 * s), n - s, n, comp);
	    }

	    m = med3(x, l, m, n, comp); // Mid-sizeColumn, med of 3
	}

	int v = x[m];

	// Establish Invariant: v* (<v)* (>v)* v*
	int a = off;

	// Establish Invariant: v* (<v)* (>v)* v*
	int b = a;

	// Establish Invariant: v* (<v)* (>v)* v*
	int c = (off + len) - 1;

	// Establish Invariant: v* (<v)* (>v)* v*
	int d = c;

	while (true) {
	    while ((b <= c) && (comp.compare(x[b], v) <= 0)) {
		if (comp.compare(x[b], v) == 0) {
		    swap(x, a++, b);
		}

		b++;
	    }

	    while ((c >= b) && (comp.compare(x[c], v) >= 0)) {
		if (comp.compare(x[c], v) == 0) {
		    swap(x, c, d--);
		}

		c--;
	    }

	    if (b > c) {
		break;
	    }

	    swap(x, b++, c--);
	}

	// Swap partition elements back to middle
	int s;

	// Swap partition elements back to middle
	int n = off + len;
	s = Math.min(a - off, b - a);
	vecswap(x, off, b - s, s);
	s = Math.min(d - c, n - d - 1);
	vecswap(x, b, n - s, s);

	// Recursively sort non-partition-elements
	if ((s = b - a) > 1) {
	    sort(x, off, s, comp);
	}

	if ((s = d - c) > 1) {
	    sort(x, n - s, s, comp);
	}
    }

    /**
     * Swaps x[a] with x[b].
     *
     * @param x the int table
     * @param a the firt index
     * @param b the second index
     */
    private static void swap(int[] x, int a, int b) {
	int t = x[a];
	x[a] = x[b];
	x[b] = t;
    }

    /**
     * Swaps x[a .. (a+n-1)] with x[b .. (b+n-1)].
     *
     * @param x the int table
     * @param a the firt index
     * @param b the second index
     * @param n the offset.
     */
    private static void vecswap(int[] x, int a, int b, int n) {
	for (int i = 0; i < n; i++, a++, b++)
	    swap(x, a, b);
    }

    /**
     * Returns the index of the median of the three indexed integers.
     *
     * @param x the int table
     * @param a the firt index
     * @param b the second index
     * @param c the third index
     * @param comp the comparator
     *
     * @return the index of the median of the three indexed integers.
     */
    private static int med3(int[] x, int a, int b, int c,
			    RowComparator comp) {
	return ((comp.compare(x[a], x[b]) < 0)
		? ((comp.compare(x[b], x[c]) < 0) ? b
		   : (comp.compare(x[a], x[c]) < 0) ? c : a)
		: ((comp.compare(x[b], x[c]) < 0) ? b
		   : (comp.compare(x[a], x[c]) > 0) ? c : a));
    }

	
}
