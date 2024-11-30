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
package pom.test.classfile.general;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Assert;
import junit.framework.TestCase;
import pom.operators.Operators;

/**
 * @author Farouk ZAIDI - 2004-02-12
 * zaidifar-program-MetricsByPrimitives
 *
 */
/**
 * @author Farouk ZAIDI - 2004-02-12
 * zaidifar-program-MetricsByPrimitives
 * 
 * Two lists of numbers allow to test operators.
 * 
 */
public class TestOperators extends TestCase {

	/** Instance of an operator*/
	final Operators operators = Operators.getInstance();

	/** Size of the sets */
	final int sizeA = 15, sizeB = 9;

	ArrayList setA;
	ArrayList setB;

	//Arrays that will be inserted into sets A and B
	final int[] A = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14 };
	final int[] B = { 15, 9, 8, 7, 6, 5, 4, 3, 2, 1 };

	public TestOperators(final String aName) {
		super(aName);
	}

	protected void setUp() throws Exception {
		super.setUp();
		this.setA = new ArrayList(this.A.length);
		for (int i = 0; i < this.A.length; i++) {
			this.setA.add(i, new Integer(this.A[i]));
		}
		//		System.out.print("A:");		
		//		displayArrayList(setA);		
		//		System.out.println();

		this.setB = new ArrayList(this.B.length);
		for (int i = 0; i < this.B.length; i++) {
			this.setB.add(i, new Integer(this.B[i]));
		}
		//		System.out.print("B:");
		//		displayArrayList(setB);
		//		System.out.println();		
	}

	/**
	 * Test for union: a list that is the concatenation of B (firstly)
	 * and A (secondly) is compared to the union of B and A.
	 */
	public final void testUnion() {
		final List expectedValue = arrayToList(this.B);
		expectedValue.addAll(arrayToList(this.A));
		Assert.assertEquals(expectedValue, this.operators.union(
			this.setB,
			this.setA));
	}

	/**
	 * Test for intersection: a list that is a copy of B (firstly). The number 15 is removed
	 * This list is compared with the intersection of A and B.
	 */
	public final void testIntersection() {
		final int[] hopedResult = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		final ArrayList comparedValue = arrayToList(hopedResult);
		Assert.assertTrue(this.operators.equal(this.operators.intersection(
			this.setB,
			this.setA), comparedValue));
	}

	/**
	 * Test for difference:  the hoped result was inserted into a new empty list.
	 * This list is compared with the difference between A and B.
	 */
	public final void testDifference() {
		final int[] hopedResult = { 0, 10, 11, 12, 13, 14 };
		final ArrayList comparedValue = arrayToList(hopedResult);
		Assert.assertTrue(this.operators.equal(this.operators.difference(
			this.setA,
			this.setB), comparedValue));
	}

	public final void testSize() {
		Assert.assertTrue(this.operators.size(this.setA) == this.sizeA);
	}

	public final void testEquals() {
		Assert.assertTrue(!this.operators.equal(this.setA, this.setB));
	}

	public final void testDifferent() {
		Assert.assertTrue(this.operators.different(this.setA, this.setB));
	}

	public final void testBelongTo() {
		assertTrue(this.operators.belongTo(new Integer(7), this.setA));
		assertTrue(!this.operators.belongTo(new Integer(0), this.setB));
	}

	public final void testIncludeOrEqual() {
		int[] array = { 10, 11, 12, 13, 14 };
		this.setA.removeAll(arrayToList(array));
		this.setB.remove(new Integer(15));
		Assert.assertTrue(this.operators.includeOrEqual(this.setB, this.setA));
	}

	public final void testInclude() {
		this.setB.remove(new Integer(15));
		Assert.assertTrue(this.operators.include(this.setB, this.setA));
	}

	/**
	 * Displays a list
	 */
	void displayArrayList(ArrayList set) {
		for (Iterator iter = set.iterator(); iter.hasNext();) {
			Integer element = (Integer) iter.next();
			System.out.print(element.intValue() + " ");
		}
	}

	/**
	 * Transforms an array to a list
	 */
	ArrayList arrayToList(int[] array) {
		ArrayList result = new ArrayList();
		for (int i = 0; i < array.length; i++)
			result.add(i, new Integer(array[i]));
		return result;
	}

}
