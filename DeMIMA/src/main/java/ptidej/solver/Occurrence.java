/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import util.io.ProxyConsole;

/**
 * Class Solution represents a complete or distorted solution
 * to the problem of finding a pattern (design or defect) in
 * source code.
 * A solution is made of:
 * - A pattern name: This name identifies which pattern was
 *   being searched in the source code.
 * - A solution number: This number identifies uniquely this
 *   (complete or distorted) solution among its peers for a
 *   given pattern and a given source code.
 * - A percentage: This percentage represents the degree of
 *   accuracy for this solution (100 means, it is a complete
 *   solution; less, it is a distorted solution).
 * - A set of SolutionComnonent which identifies the roles and
 *   the entities of the source code associated with those
 *   roles, plus a XCommand if the solution is distorted.
 */
public final class Occurrence {
	public static final char[] COMMAND = "XCommand".toCharArray();
	public static final char[] NAME = "Name".toCharArray();
	public static final char[] SIGN = "Sign".toCharArray();
	private static final char[] UNKNOWN_PATTERN = "Unknown Motif".toCharArray();

	// Jean-Yves 5/11/04: Printing!
	// Added to print out an array of Solution.
	public static void print(final Occurrence[] solutions, final Writer out) {
		try {
			for (int i = 0; i < solutions.length; i++) {
				out.write("# Micro-architecture ");
				out.write(solutions[i].getNumber() + "");
				out.write(" similar at ");
				out.write(solutions[i].getConfidence() + "");
				out.write("% with ");
				out.write(solutions[i].getDisplayName());
				out.write('\n');

				final Iterator allComponents =
					solutions[i].getComponents().iterator();
				while (allComponents.hasNext()) {
					out.write(solutions[i].getNumber() + "");
					out.write(".");
					out.write(solutions[i].getConfidence() + "");
					out.write(".");
					out.write(((OccurrenceComponent) allComponents.next())
						.toString());
					out.write('\n');
				}
				out.write('\n');
			}

			out.flush();
			out.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	private List components = new ArrayList();
	private final int number;
	private int confidence;

	/**
	 * Creates a solution (complete or distorted) to the problem
	 * of finding a pattern (design or defect) in source code.
	 */
	public Occurrence(final int number, final int percentage) {
		this.number = number;
		this.confidence = percentage;
	}

	/**
	 * Adds a part to the solution.
	 */
	public void addComponent(final OccurrenceComponent sc) {
		// I add this new component in alphabetical order!
		this.components.add(sc);
		Collections.sort(this.components, new Comparator() {
			public int compare(final Object o1, final Object o2) {
				return String
					.valueOf(((OccurrenceComponent) o1).getName())
					.compareToIgnoreCase(
						String.valueOf(((OccurrenceComponent) o2).getName()));
			}
		});
	}

	/**
	 * Returns if two solutions are equal
	 * (same name, same percentage, same components).
	 */
	// TODO: Should have two equal methods! This one should be "equal()",
	// the following one should be "lightEqual()" or "componentEqual()"...
	// See with Jean-Yves were his code is impacted.
	public boolean equals(final Object object) {
		if (object instanceof Occurrence) {
			final Occurrence solution = (Occurrence) object;
			// return this.toString().equals(solution.toString());
			return this.equals(solution);
		}
		return false;
	}

	/**
	 * Returns if two solutions are equal
	 * (same components).
	 */
	public boolean equals(final Occurrence solution) {
		boolean equal = true;
		final Iterator iterator = this.components.iterator();

		while (iterator.hasNext() && equal) {
			boolean belongsTo = false;
			final OccurrenceComponent currentComponent =
				(OccurrenceComponent) iterator.next();
			final Iterator solutionComponents =
				solution.getComponents().iterator();

			while (solutionComponents.hasNext() && !belongsTo) {
				final OccurrenceComponent solutionComponent =
					(OccurrenceComponent) solutionComponents.next();

				if (currentComponent.equals(solutionComponent)) {
					belongsTo = true;
				}
			}

			equal = belongsTo;
		}

		return equal;
	}

	/**
	 * Gets the part of the solution with non-enumerated name <name>.
	 */
	public OccurrenceComponent getComponent(final char[] aName) {
		final Iterator e = this.components.iterator();
		while (e.hasNext()) {
			final OccurrenceComponent c = (OccurrenceComponent) e.next();
			char[] cName = c.getName();
			final int indexOfLastDash = ArrayUtils.lastIndexOf(cName, '-');
			if (indexOfLastDash > 0) {
				try {
					Integer.parseInt(String.valueOf(ArrayUtils.subarray(
						cName,
						indexOfLastDash + 1,
						cName.length)));
					cName = ArrayUtils.subarray(cName, 0, indexOfLastDash);
				}
				catch (final NumberFormatException nfe) {
				}
			}
			if (Arrays.equals(cName, aName)) {
				return c;
			}
		}
		return null;
	}
	/**
	 * Returns the list of all parts of the solution.
	 */
	public List getComponents() {
		return this.components;
	}

	public String getDisplayName() {
		return String.valueOf(this.getName());
	}

	/**
	 * Returns the name of the solution.
	 */
	public char[] getName() {
		final OccurrenceComponent solutionComponent =
			this.getComponent(Occurrence.NAME);
		/*		final SolutionComponent solutionComponent = this.getComponent(
					MultilanguageManager.getStrResource(
						"PROJECT_NAME",
						"Ptidej UI Viewer Standalone::ProjectViewerPanel::"
					)
				);
		*/
		if (solutionComponent == null) {
			return Occurrence.UNKNOWN_PATTERN;
		}
		return solutionComponent.getValue();
	}

	/**
	 * Returns the unique number of this solution.
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * Returns the percentage of the solution.
	 */
	public int getConfidence() {
		return this.confidence;
	}

	/**
	 * Returns the percentage of the solution.
	 * @since 2009/03/19
	 */
	public void setConfidence(final int aPercentage) {
		this.confidence = aPercentage;
	}

	/**
	 * Returns a string representing the solution.
	 */
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Micro-architecture ");
		buffer.append(this.getNumber());
		buffer.append(" similar to ");
		buffer.append(this.getName());
		buffer.append(" with a confidence of ");
		buffer.append(this.getConfidence());
		buffer.append('\n');

		final Iterator allComponents = this.components.iterator();
		while (allComponents.hasNext()) {
			buffer.append('\t');
			buffer.append(((OccurrenceComponent) allComponents.next())
				.toString());
			buffer.append('\n');
		}

		return buffer.toString();
	}
}
