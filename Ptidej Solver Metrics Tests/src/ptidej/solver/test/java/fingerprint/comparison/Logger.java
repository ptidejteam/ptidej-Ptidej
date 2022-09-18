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
package ptidej.solver.test.java.fingerprint.comparison;

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

	public static final int WITH_RULE = 0;
	public static final int WITHOUT_RULE = 1;

	public static void main(final String args[]) {
		final Logger test = new Logger("TestLog");
		test.setMode(0);
		test.setStart(0);
		test.setILMCreation(10000);
		test.setProblemCreation(30000);
		test.setEndProcess(60000);
		test.setRole(null);
		test.setDomainSize(200);
		test.setNumSolutions(2000);
		test.setGhostedSolutions(1500);
		test.addVar("Var1", 100, 50.26546);
		test.addVar("Var2", 50, 75.654654);
		test.setMode(1);
		test.setStart(0);
		test.setILMCreation(10000);
		test.setProblemCreation(30000);
		test.setEndProcess(100000);
		test.setRole(null);
		test.setDomainSize(200);
		test.setNumSolutions(5000);
		test.setGhostedSolutions(4500);
		test.addVar("Var1", 200, 0);
		test.addVar("Var2", 200, 0);
		test.print();
	}

	private int[] domainSize = new int[2];
	private long[] endProcess = new long[2];
	private int[] ghostSol = new int[2];
	private long[] ilmCreation = new long[2];
	private int[] numSol = new int[2];
	private long[] problemCreation = new long[2];
	private Rule role;
	private int ruleMode;
	private long[] start = new long[2];
	private String testTitle;
	private ArrayList[] var = new ArrayList[2];

	public Logger(final String testTitle) {
		this.testTitle = testTitle;
		this.var[0] = new ArrayList();
		this.var[1] = new ArrayList();
		this.ruleMode = 0;
		this.role = null;
	}
	public void addVar(final String name, final int size, final double reduction) {

		this.var[this.ruleMode].add(new VarLog(name, size, reduction));
	}
	private String computeTime(final long start, final long end) {
		final long t = end - start;
		final long min = (t / 1000) / 60;
		final long sec = (t / 1000) - (60 * min);

		final StringBuffer buffer = new StringBuffer();
		buffer.append(min);
		buffer.append('\'');
		buffer.append(sec);

		return buffer.toString();
	}
	public void print() {
		final StringBuffer fileName = new StringBuffer();
		fileName.append("rsc/");
		fileName.append(this.testTitle);
		fileName.append(".txt");
		final PrintWriter out =
			new PrintWriter(ProxyDisk.getInstance().fileAbsoluteOutput(
				fileName.toString()));

		out.println(this.testTitle);
		out.println();
		out.println(this.role);

		out.print("Step\tILMCreation\tProblemCreation\tSolver\tTotal\n");
		out.print("Ruled\t");
		out.print(this.computeTime(this.start[0], this.ilmCreation[0]));
		out.print('\t');
		out.print(this
			.computeTime(this.ilmCreation[0], this.problemCreation[0]));
		out.print('\t');
		out
			.print(this
				.computeTime(this.problemCreation[0], this.endProcess[0]));
		out.print('\t');
		out.println(this.computeTime(this.start[0], this.endProcess[0]));
		out.print("No Rule\t");
		out.print(this.computeTime(this.start[1], this.ilmCreation[1]));
		out.print('\t');
		out.print(this
			.computeTime(this.ilmCreation[1], this.problemCreation[1]));
		out.print('\t');
		out
			.print(this
				.computeTime(this.problemCreation[1], this.endProcess[1]));
		out.print('\t');
		out.println(this.computeTime(this.start[1], this.endProcess[1]));
		out.println();

		out.print("Var\tDomainNoRule\tDomainRuled\tReduction\n");
		for (int i = 0; i < this.var[0].size(); i++) {
			final VarLog vr = (VarLog) this.var[0].get(i);
			final VarLog vnr = (VarLog) this.var[1].get(i);

			out.print(vr.getName());
			out.print('\t');
			out.print(vnr.getSize());
			out.print('\t');
			out.print(vr.getSize());
			out.print('\t');
			out.print((int) vr.getReduction());
			out.print("%\n");
		}
		out.println();

		out.print("Solutions\tTotal\tGhosted\tValabe\n");
		out.print("Ruled\t");
		out.print(this.numSol[0]);
		out.print('\t');
		out.print(this.ghostSol[0]);
		out.print('\t');
		out.println(this.numSol[0] - this.ghostSol[0]);

		out.print("No rule\t");
		out.print(this.numSol[1]);
		out.print('\t');
		out.print(this.ghostSol[1]);
		out.print('\t');
		out.println(this.numSol[1] - this.ghostSol[1]);

		out.flush();
		out.close();
	}
	public void setDomainSize(final int i) {
		this.domainSize[this.ruleMode] = i;
	}
	public void setEndProcess(final long l) {
		this.endProcess[this.ruleMode] = l;
	}
	public void setGhostedSolutions(final int i) {
		this.ghostSol[this.ruleMode] = i;
	}
	public void setILMCreation(final long l) {
		this.ilmCreation[this.ruleMode] = l;
	}
	public void setMode(final int ruleMode) {
		this.ruleMode = ruleMode;
	}
	public void setNumSolutions(final int i) {
		this.numSol[this.ruleMode] = i;
	}
	public void setProblemCreation(final long l) {
		this.problemCreation[this.ruleMode] = l;
	}
	public void setRole(final Rule role) {
		this.role = role;
	}
	public void setStart(final long rootTime) {
		this.start[this.ruleMode] = rootTime;
	}
}
