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
package ptidej.solver;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/06
 */
public class OccurrenceComparator {
	private static OccurrenceComparator UniqueInstance;
	public boolean belongsTo(
		final Occurrence aSolution,
		final Occurrence[] solutions) {

		boolean belongsTo = false;
		for (int i = 0; i < solutions.length && !belongsTo; i++) {
			if (aSolution.equals(solutions[i])) {
				belongsTo = true;
			}
		}
		return belongsTo;
	}
	public static OccurrenceComparator getInstance() {
		if (OccurrenceComparator.UniqueInstance == null) {
			OccurrenceComparator.UniqueInstance = new OccurrenceComparator();
		}
		return OccurrenceComparator.UniqueInstance;
	}
	public static void main(final String[] args) {
	}

	/**
	 * Compute the difference between the first array of solutions with
	 * the second array of solutions if |someSolutionA| >= |someSolutionB|
	 * or the other way around if |someSolutionB| > |someSolutionA|.
	 */
	public Occurrence[] difference(
		final Occurrence[] someSolutionsA,
		final Occurrence[] someSolutionsB) {

		final Occurrence[] longerArray;
		final Occurrence[] shorterArray;
		if (someSolutionsB.length > someSolutionsA.length) {
			longerArray = someSolutionsB;
			shorterArray = someSolutionsA;
		}
		else {
			longerArray = someSolutionsA;
			shorterArray = someSolutionsB;
		}

		final List list = new ArrayList();
		for (int i = 0; i < longerArray.length; i++) {
			if (!this.belongsTo(longerArray[i], shorterArray)) {
				list.add(longerArray[i]);
			}
		}

		final Occurrence[] differentSolutions = new Occurrence[list.size()];
		list.toArray(differentSolutions);
		return differentSolutions;
	}
	/**
	 * Compute the difference between the first array of solutions with
	 * the second array of solutions if |someSolutionA| >= |someSolutionB|
	 * or the other way around if |someSolutionB| > |someSolutionA|.
	 */
	public Occurrence[] intersection(
		final Occurrence[] someSolutionsA,
		final Occurrence[] someSolutionsB) {

		final Occurrence[] longerArray;
		final Occurrence[] shorterArray;
		if (someSolutionsB.length > someSolutionsA.length) {
			longerArray = someSolutionsB;
			shorterArray = someSolutionsA;
		}
		else {
			longerArray = someSolutionsA;
			shorterArray = someSolutionsB;
		}

		final List list = new ArrayList();
		for (int i = 0; i < longerArray.length; i++) {
			if (this.belongsTo(longerArray[i], shorterArray)) {
				list.add(longerArray[i]);
			}
		}

		final Occurrence[] differentSolutions = new Occurrence[list.size()];
		list.toArray(differentSolutions);
		return differentSolutions;
	}
}
