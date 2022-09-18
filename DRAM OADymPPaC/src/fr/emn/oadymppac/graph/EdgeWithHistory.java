/*
 * $Id: EdgeWithHistory.java,v 1.3 2006/08/11 23:11:03 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac.graph;

import salvo.jesus.graph.EdgeImpl;
import salvo.jesus.graph.Vertex;
import salvo.jesus.graph.WeightedEdge;
import fr.emn.oadymppac.utils.IntStack;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: This class provides an implementation of a graph edge
 * with a history.
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * 
 * <p>
 * Company: Ecole des Mines de Nantes
 * </p>
 * 
 * @version $Revision: 1.3 $
 * @author Mohammad Ghoniem
 */
public class EdgeWithHistory extends EdgeImpl implements WeightedEdge {
	/**
	 * 
	 */
	private static final long serialVersionUID = -557107962891212880L;
	/**
	 * DOCUMENT ME!
	 *
	 * @param a DOCUMENT ME!
	 * @param key DOCUMENT ME!
	 * @param low DOCUMENT ME!
	 * @param high DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public static int binarySearch(
		final int[] a,
		final int key,
		int low,
		int high) {
		while (low <= high) {
			final int mid = low + high >> 1;
			final int midVal = a[mid];

			if (midVal < key) {
				low = mid + 1;
			}
			else if (midVal > key) {
				high = mid - 1;
			}
			else {
				return mid; // key found
			}
		}

		return -(low + 1); // key not found.
	}

	public static int upperBound(
		final int[] a,
		final int key,
		int first,
		final int last) {
		int len = last - first;
		int half;
		int middle;

		while (len > 0) {
			half = len / 2;
			middle = first;
			middle += half;
			if (key < a[middle]) {
				len = half;
			}
			else {
				first = middle;
				++first;
				len = len - half - 1;
			}
		}
		return first;
	}

	IntStack history;

	/**
	 * Creates an edge with a history whose length is
	 * <code>len</code>. All the elements of this array are
	 * initialized to <code>Integer.MAX_VALUE</code> for proper
	 * execution of binary search.
	 * @param a DOCUMENT ME!
	 * @param b DOCUMENT ME!
	 */
	public EdgeWithHistory(final Vertex a, final Vertex b) {
		super(a, b);
		this.history = new IntStack();
	}

	/**
	 * If the array is not full, it adds <code>n</code> to the array.
	 * Otherwise, an array with double capacity is created before
	 * <code>n</code> is added.
	 * @param n DOCUMENT ME!
	 */
	public void add(final int n) {
		this.history.push(n);
	}

	/**
	 * Returns the number of elements between <code>start</code> and
	 * the last element in the array.
	 * @param start DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getDelta(final int start) {
		return this.getHistoryLength() - this.getDelta(start);
	}

	/**
	 * Returns the number of elements between <code>start</code> and
	 * <code>end</code>.
	 * @param start DOCUMENT ME!
	 * @param end DOCUMENT ME!
	 * 
	 * @return DOCUMENT ME!
	 */
	public int getDelta(final int start, final int end) {
		return this.getWeightAt(end) - this.getWeightAt(start);

	}

	/**
	 * Returns the history.
	 * @return IntStack
	 */
	public int[] getHistory() {
		return this.history.getData();
	}

	/**
	 * Returns the number of events in the history.
	 *
	 * @return DOCUMENT ME!
	 */
	public int getHistoryLength() {
		return this.history.size();
	}

	/**
	 * @see salvo.jesus.graph.WeightedEdge#getWeight()
	 */
	public double getWeight() {
		return this.getHistoryLength();
	}

	/**
	 * Returns the index of n in the array or its insertion point if
	 * it is not there.
	 * 
	 * @see Arrays.binarySearch(int, int)
	 */
	public int getWeightAt(final int n) {
		return this.search(n);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param key DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public final int search(final int key) {
		return EdgeWithHistory.upperBound(
			this.history.getData(),
			key,
			0,
			this.history.size());
	}

	/**
	 * Sets the history.
	 * @param history The history to set
	 */
	public void setHistory(final int[] hist) {
		final IntStack stack = new IntStack();
		for (int i = 0; i < hist.length; i++) {
			stack.push(hist[i]);
		}
		this.history = stack;
	}
	/**
	 * @see salvo.jesus.graph.WeightedEdge#setWeight(double)
	 */
	public void setWeight(final double weight) {
	}

	public String toString() {
		return "EdgeWithHistory:" + this.getVertexA() + "->"
				+ this.getVertexB();
	}

}