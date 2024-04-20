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
package ptidej.solver.java;

import java.io.PrintWriter;
import java.util.List;
import choco.integer.var.IntDomain;
import choco.palm.search.PalmAbstractBranching;
import choco.util.IntIterator;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class Branching extends PalmAbstractBranching {
	private String message = "# Solution with all constraints\n";
	private int weight = 100;
	private int solutionNumber = 0;
	private String xCommand = "No source transformation needed";

	public boolean checkAcceptable(final List csts) {
		return false;
	}
	public String getMessage() {
		return this.message;
	}
	public Object getNextBranch(Object branchingItem, Object previousBranch) {

		return null;
	}
	public int getWeight() {
		return this.weight;
	}
	public int getSolutionNumber() {
		return this.solutionNumber;
	}
	public String getXCommand() {
		return this.xCommand;
	}
	public void learnFromRejection() {
	}
	public void printOutSolution(
		final Problem pb,
		final String solutionMessage,
		final int solutionNumber,
		final int solutionWeight,
		final String xCommand) {

		final int percentage;
		if (pb.getGlobalWeight() > 0) {
			percentage = solutionWeight * 100 / pb.getGlobalWeight();
		}
		else {
			percentage = 100;
		}
		final PrintWriter out = pb.getWriter();
		final List listAllEntity = pb.getAllEntities();
		final StringBuffer output = new StringBuffer();

		output.append(solutionMessage);
		output.append(solutionNumber);
		output.append(".");
		output.append(percentage);
		output.append(".Name = ");
		output.append(pb.getName());
		output.append('\n');

		output.append(solutionMessage);
		output.append(solutionNumber);
		output.append(".");
		output.append(percentage);
		output.append(".Sign = Positive\n");

		output.append(solutionNumber);
		output.append(".");
		output.append(percentage);
		output.append(".XCommand = ");
		output.append(xCommand);
		output.append('\n');

		for (int i = 0; i < pb.getVars().length; i++) {
			if (pb.getIntVar(i).isInstantiated()) {
				output.append(solutionNumber);
				output.append(".");
				output.append(percentage);
				output.append(".");
				output.append(pb.getIntVar(i).toString());
				output.append(" = ");
				output.append(listAllEntity.get(pb.getIntVar(i).getValue()));
				output.append('\n');
			}
			// Yann 2004/11/09: Hack!
			// This seems mandatory when using rules...
			// TODO: Remove this hack!!!
			else if (pb.getIntVar(i).getDomain().getSize() == 1) {
				output.append(solutionNumber);
				output.append(".");
				output.append(percentage);
				output.append(".");
				output.append(pb.getIntVar(i).toString());
				output.append(" = ");
				output.append(listAllEntity.get(pb.getIntVar(i).getInf()));
				output.append('\n');
			}
			else {
				final IntDomain domain = pb.getIntVar(i).getDomain();
				// Yann 2004/10/16: Fingerprints
				// It is possible when defining domain using fingerprints
				// that the domain of a variable be empty. Then, I don't
				// print out anything.
				if (domain.getSize() > 0) {
					final IntIterator iterator = domain.getIterator();

					int nbROTW = 0;
					while (iterator.hasNext()) {
						nbROTW++;
						output.append(solutionNumber);
						output.append(".");
						output.append(percentage);
						output.append(".");
						output.append(pb.getIntVar(i).toString());
						output.append("-");
						output.append(nbROTW);
						output.append(" = ");
						output.append(listAllEntity.get(iterator.next()));
						output.append('\n');
					}
				}
			}
		}

		// System.out.println(output);

		out.print(output.toString());
		out.println();

		// Yann 2010/07/20: Performance
		// Flushing a print stream is quite inefficient
		// because, in the general case, it requires
		// actually printing out data in some media. So,
		// I remove it... Crossing my fingers that nothing
		// crashes and that all the data would not be lost
		// in such an event.
		//	out.flush();
	}
	public Object selectBranchingObject() {
		return null;
	}
	public Object selectFirstBranch(final Object item) {
		return null;
	}
	public void setMessage(final String m) {
		this.message = m;
	}
	public void setWeight(final int p) {
		this.weight = p;
	}
	public void setSolutionNumber(final int n) {
		this.solutionNumber = n;
	}
	public void setXCommand(final String c) {
		this.xCommand = c;
	}
}
