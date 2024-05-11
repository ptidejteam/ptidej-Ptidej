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
package epi.solver;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import padl.kernel.IAbstractModel;
import padl.kernel.IGhost;

/**
 * @author OlivierK
 *
 * Class Solution represents a complete or distorted solution
 * to the problem of finding a pattern (design or defect) in
 * source code.
 * 
 */
public final class Solution implements Cloneable {

	public static Solution[] getSolutionsWithoutGhost(
		final Solution[] solutions,
		final IAbstractModel abstractModel) {
		final ArrayList sol = new ArrayList();

		for (int i = 0; i < solutions.length; i++) {
			boolean ghosted = false;
			final Iterator componentIter =
				solutions[i].getComponents().values().iterator();
			while (componentIter.hasNext() && !ghosted) {
				final String component = componentIter.next().toString();
				if (abstractModel.getTopLevelEntityFromID(component) instanceof IGhost) {
					ghosted = true;
				}
			}
			if (!ghosted) {
				sol.add(solutions[i]);
			}
		}
		final Solution[] solTmp = new Solution[sol.size()];
		for (int i = 0; i < sol.size(); i++) {
			solTmp[i] = (Solution) sol.get(i);
		}
		return solTmp;
	}
	// Print out an array of Solution.
	public static void print(final Solution[] solutions, final Writer out) {
		try {
			for (int i = 0; i < solutions.length; i++) {
				out.write("# Micro-architecture ");
				out.write(solutions[i].getNumber() + "");
				out.write(" similar at ");
				out.write(solutions[i].getPercentage() + "");
				out.write("% with ");
				out.write(solutions[i].getName() + "");
				out.write('\n');

				final Enumeration allComponents =
					solutions[i].getComponents().keys();
				while (allComponents.hasMoreElements()) {
					final String key = allComponents.nextElement().toString();
					out.write(solutions[i].getNumber() + "");
					out.write('.');
					out.write(solutions[i].getPercentage() + "");
					out.write('.');
					out.write(key + " = "
							+ solutions[i].getComponents().get(key).toString());
					out.write('\n');
				}
				out.write('\n');
			}
			out.flush();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	private Hashtable components = new Hashtable();
	private int solutionNumber = 0;

	private static int solutionCounter = 0;

	private String name;

	public Solution() {
		this("unknown pattern");
	}

	/**
	 * Creates a solution (complete or distorted) to the problem
	 * of finding a pattern (design or defect) in source code.
	 */
	public Solution(final String name) {
		Solution.solutionCounter++;
		this.solutionNumber = Solution.solutionCounter;
		this.name = name;
	}

	/**
	 * Adds a part to the solution.
	 */
	public void addComponent(final Object key, final Object value) {
		this.components.put(key, value);
	}

	public Object clone() //throws CloneNotSupportedException
	{
		final Solution copy = new Solution(this.name);
		copy.components = (Hashtable) this.components.clone();
		copy.name = this.name;

		return copy;
	}

	public boolean containsComponent(final Object component) {
		return this.components.containsKey(component);
	}
	/**
	 * Returns if two solutions are equal
	 * (same name, same components).
	 */
	public boolean equals(final Object object) {
		if (object instanceof Solution) {
			final Solution solution = (Solution) object;
			return this.equals(solution);
		}
		return false;
	}

	/**
	 * Returns if two solutions are equal
	 * (same components).
	 */
	public boolean equals(final Solution solution) {
		if (this.name == solution.getName()
				&& this.components.equals(solution.getComponents())) {
			final Enumeration componentsEnum = this.components.keys();
			while (componentsEnum.hasMoreElements()) {
				final Object component = componentsEnum.nextElement();
				if (this.components.get(component) != solution
					.getComponent(component)) {
					return false;
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public Object getComponent(final Object role) {
		return this.components.get(role);
	}

	/**
	 * Returns the name of the solution.
	 */
	public Hashtable getComponents() {
		return this.components;
	}

	/**
	 * Returns the name of the solution.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Returns the unique number of this solution.
	 */
	public int getNumber() {
		return this.solutionNumber;
	}

	/**
	 * Returns the percentage of the solution.
	 */
	public int getPercentage() {
		return 100;
	}

	/**
	 * Removes a part to the solution.
	 */
	public void removeComponent(final Object key) {
		this.components.remove(key);
	}

	/**
	 * Sets the name of the solution.
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns a string representing the solution.
	 */
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Micro-architecture ");
		buffer.append(this.getNumber());
		buffer.append(" similar at ");
		buffer.append(this.getPercentage());
		buffer.append("% with ");
		buffer.append(this.getName());
		buffer.append('\n');

		//append components
		return buffer.toString();
	}
}
