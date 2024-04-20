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
package ptidej.solver.fingerprint.helper;

import java.io.PrintWriter;
import java.util.ArrayList;
import ptidej.solver.fingerprint.Rule;
import util.io.ProxyDisk;

/**
 * @author Jean-Yves Guyomarc'h
 * @since  2004/09/19
 */
public class Logger {
	private class VarLog {
		final private String name;
		final private double reduction;
		final private int size;

		public VarLog(final String name, final int size, final double reduction) {
			this.name = name;
			this.size = size;
			this.reduction = reduction;
		}
		public String getName() {
			return this.name;
		}
		public double getReduction() {
			return this.reduction;
		}
		public int getSize() {
			return this.size;
		}
	}
	public static final int WITH_RULES = 0;
	public static final int WITHOUT_RULES = 1;

	private int[] domainSize = new int[2];
	private long[] endTime = new long[2];
	private int included;
	private int[] numberOfSolutions = new int[2];
	private int[] numberOfSolutionsWithGhosts = new int[2];
	private long[] problemCreationTime = new long[2];
	private Rule rule;
	private int ruleMode;
	private long[] startTime = new long[2];
	private String title;
	private ArrayList[] variables = new ArrayList[2];

	public Logger(final String testTitle) {
		super();
		this.title = testTitle;
		this.variables[0] = new ArrayList();
		this.variables[1] = new ArrayList();
		this.ruleMode = 0;
		this.rule = null;
		this.included = 0;
	}
	public void addVar(final String name, final int size, final double reduction) {

		this.variables[this.ruleMode].add(new VarLog(name, size, reduction));
	}
	public int getMode() {
		return this.ruleMode;
	}
	private String getTime(final long start, final long end) {
		final long t = end - start;
		final long min = (t / 1000) / 60;
		final long sec = (t / 1000) - (60 * min);

		return min + "'" + sec;
	}
	public void print() {
		final String fileName = "rsc/" + this.title + " (summary).txt";
		final PrintWriter out =
			new PrintWriter(ProxyDisk.getInstance().fileTempOutput(fileName));

		out.println(this.title);
		out.println();

		out.println(this.rule);
		out.println();

		out.println("With rules");
		out.print("\tProblem creation: ");
		out.println(this
			.getTime(this.startTime[0], this.problemCreationTime[0]));
		out.print("\tSolving: ");
		out.println(this.getTime(this.problemCreationTime[0], this.endTime[0]));
		out.print("\tTotal: ");
		out.println(this.getTime(this.startTime[0], this.endTime[0]));
		out.print("\tTotal number of solutions: ");
		out.println(this.numberOfSolutions[0]);
		out.print("\tNumber of solutions with Ghosts: ");
		out.println(this.numberOfSolutionsWithGhosts[0]);
		out.print("\tNumber of solutions without Ghosts: ");
		out.println(this.numberOfSolutions[0]
				- this.numberOfSolutionsWithGhosts[0]);

		out.println();

		out.println("Without rule");
		out.print("\tProblem creation: ");
		out.println(this
			.getTime(this.startTime[1], this.problemCreationTime[1]));
		out.print("\tSolving: ");
		out.println(this.getTime(this.problemCreationTime[1], this.endTime[1]));
		out.print("\tTotal: ");
		out.println(this.getTime(this.startTime[1], this.endTime[1]));
		out.print("\tTotal number of solutions: ");
		out.println(this.numberOfSolutions[1]);
		out.print("\tNumber of solutions with Ghosts: ");
		out.println(this.numberOfSolutionsWithGhosts[1]);
		out.print("\tNumber of solutions without Ghosts: ");
		out.println(this.numberOfSolutions[1]
				- this.numberOfSolutionsWithGhosts[1]);

		out.println();

		for (int i = 0; i < this.variables[0].size(); i++) {
			final VarLog vr = (VarLog) this.variables[0].get(i);
			final VarLog vnr = (VarLog) this.variables[1].get(i);

			out.print("Variable \"");
			out.print(vr.getName());
			out.println('\"');
			out.print("\tDomain size without rule: ");
			out.println(vnr.getSize());
			out.print("\tDomain size with rules: ");
			out.println(vr.getSize());
			out.print("\tDomain reduction: ");
			out.print(vr.getReduction());
			out.println('%');
		}

		out.println();

		out.println("Inclusion:");
		out.print(this.included);
		out.print(" solutions included of ");
		out.println(this.numberOfSolutions[0]
				- this.numberOfSolutionsWithGhosts[0]);

		out.flush();
		out.close();
	}
	public void setDomainSize(final int i) {
		this.domainSize[this.ruleMode] = i;
	}
	public void setEndProcess(final long l) {
		this.endTime[this.ruleMode] = l;
	}
	public void setGhostedSolutions(final int i) {
		this.numberOfSolutionsWithGhosts[this.ruleMode] = i;
	}
	public void setIncluded(final int included) {
		this.included = included;
	}
	public void setMode(final int ruleMode) {
		this.ruleMode = ruleMode;
	}
	public void setNumSolutions(final int i) {
		this.numberOfSolutions[this.ruleMode] = i;
	}
	public void setProblemCreation(final long l) {
		this.problemCreationTime[this.ruleMode] = l;
	}
	public void setRule(final Rule rule) {
		this.rule = rule;
	}
	public void setStart(final long rootTime) {
		this.startTime[this.ruleMode] = rootTime;
	}
}
