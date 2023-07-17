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

import java.io.PrintWriter;
import util.io.ProxyDisk;

/**
 * @author OlivierK
 *
 */
public class Logger {
	private int solverType;
	private int numberOfEntities;

	private long stringConstructionStartTime;
	private long stringMatchingStartTime;
	private long stringConstructionEndTime;
	private long stringMatchingEndTime;

	private int numberOfSolutions;
	private int numberOfGhostSolutions;

	private final String title;

	public Logger(final String identificationTitle) {
		super();
		this.title = identificationTitle;
	}

	private String convertTimeToString(final long totalSecondsInt) {
		int hours;
		int minutes;
		int seconds;

		hours = (int) totalSecondsInt / (60 * 60);
		minutes = (int) ((totalSecondsInt - hours * 60 * 60) / 60);
		seconds = (int) totalSecondsInt - hours * 60 * 60 - minutes * 60;

		String minutesString = new String(Integer.valueOf(minutes).toString());
		String secondsString = new String(Integer.valueOf(seconds).toString());

		if (minutes < 10) {
			minutesString = "0" + minutesString;
		}
		if (seconds < 10) {
			secondsString = "0" + secondsString;
		}

		if (hours == 0) {
			return new String(minutesString + ":" + secondsString);
		}
		else {
			final String hoursString =
				new String(Integer.valueOf(hours).toString());
			return new String(hoursString + ":" + minutesString + ":"
					+ secondsString);
		}
	}

	public String getIdentificationName() {
		return this.title;
	}
	public String getIdentificationTime() {
		return this
			.convertTimeToString((this.stringMatchingEndTime - this.stringMatchingStartTime) / 1000);
	}
	public int getNumberOfEntities() {
		return this.numberOfEntities;
	}
	public int getNumberOfSolutionsWithGhosts() {
		return this.numberOfGhostSolutions;
	}
	public int getNumberOfSolutionsWithoutGhost() {
		return this.numberOfSolutions - this.numberOfGhostSolutions;
	}
	public int getSolverType() {
		return this.solverType;
	}
	public String getSolverTypeString() {
		if (this.solverType == EPISolver.WITH_CONSTRAINTS) {
			return new String("Solver 1 - Optimised bit-vector algorithm");
		}
		else if (this.solverType == EPISolver.WITHOUT_CONSTRAINT) {
			return new String("Solver 2 - Bit-vector algorithm");
		}
		else {
			return new String("Solver 3 - Automaton algorithm");
		}
	}
	public String getStringConstructionTime() {
		return this
			.convertTimeToString((this.stringConstructionEndTime - this.stringConstructionStartTime) / 1000);
	}

	public void print() {
		final String fileName = "rsc/" + this.title + " (summary).txt";
		final PrintWriter out =
			new PrintWriter(ProxyDisk.getInstance().fileTempOutput(fileName));

		out.println(this.title);
		out.println();
		out.println("Solver Type: " + this.getSolverTypeString());

		out.println("Number of entities: " + this.numberOfEntities);
		out.println();

		out
			.print("String representation construction time: "
					+ this
						.convertTimeToString((this.stringConstructionEndTime - this.stringConstructionStartTime) / 1000));

		out.println();

		out
			.print("Design pattern identification time: "
					+ this
						.convertTimeToString((this.stringMatchingEndTime - this.stringMatchingStartTime) / 1000));

		out.println();

		out.println("Number of solutions: " + this.numberOfSolutions);
		out.println("\tNumber of solutions without ghost: "
				+ (this.numberOfSolutions - this.numberOfGhostSolutions));
		out.println("\tNumber of solutions with ghosts: "
				+ this.numberOfGhostSolutions);

		out.println();
		out.flush();
		out.close();
	}
	public void setNumberOfEntities(final int numberOfEntities) {
		this.numberOfEntities = numberOfEntities;
	}

	public void setNumberOfGhostSolutions(final int numberOfGhostSolutions) {
		this.numberOfGhostSolutions = numberOfGhostSolutions;
	}
	public void setNumberOfSolutions(final int numberOfSolutions) {
		this.numberOfSolutions = numberOfSolutions;
	}
	public void setSolverType(final int type) {
		this.solverType = type;
	}
	public void setStringConstructionEndTime(
		final long stringConstructionEndTime) {
		this.stringConstructionEndTime = stringConstructionEndTime;
	}
	public void setStringConstructionStartTime(
		final long stringConstructionStartTime) {
		this.stringConstructionStartTime = stringConstructionStartTime;
	}
	public void setStringMatchingEndTime(final long stringMatchingEndTime) {
		this.stringMatchingEndTime = stringMatchingEndTime;
	}

	public void setStringMatchingStartTime(final long stringMatchingStartTime) {
		this.stringMatchingStartTime = stringMatchingStartTime;
	}
}
