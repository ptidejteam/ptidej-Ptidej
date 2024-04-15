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
package client;

import java.io.File;
import java.util.Date;

import org.eclipse.jdt.core.compiler.IProblem;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;

import common.tools.constants.Constants;
import common.tools.file.FileTools;
import common.tools.time.Elapse;
import parser.input.SourceInputsHolder;
import parser.reader.NamedReader;
import util.io.ProxyConsole;

public class WrapperClientWithLog extends WrapperClient {
	private final StringBuffer log;
	/**
	 * set to null if log is not needed.
	 */
	private final File logFile;
	/*
	 * Counter of how many java files have been parsed
	 */
	long javaFileNum;
	/*
	 * Output a message after how many java files have been parsed.
	 */
	long mileStone = 5000;
	long numOfErrors;

	/**
	 * If display compile warnings and errors.
	 */
	private boolean verbose = false;

	public WrapperClientWithLog(final SourceInputsHolder javaProject,
			final String logFilePath) {
		super(javaProject);

		this.logFile = new File(logFilePath);
		this.log = new StringBuffer();
	}

	public void displayAndLog(final String msg) {
		this.log(msg, true);
	}

	/*
	 * Close the project and write the log file.
	 */
	void flush() {
		if (this.logFile != null) {
			FileTools.Instance.writeTempFile(this.logFile,
					this.log.toString().toCharArray(), false);
		}
	}

	public long getMileStone() {
		return this.mileStone;
	}

	public void setMileStone(final long mileStone) {
		this.mileStone = mileStone;
	}

	/**
	 * @param javaFilePath Local absolute Java file path
	 * @return the relative Java file path to its source path entry and replace
	 * directory separators with slash.
	 */
	private String getUnifiedRelatvieJavaFileName(final String javaFilePath) {
		String qualifiedClassName = null;
		for (final NamedReader sourcepathEntry : this.javaProject
				.getSourcepathEntries()) {
			if (javaFilePath.contains(sourcepathEntry.getName())) {
				qualifiedClassName = javaFilePath
						.replace(
								sourcepathEntry.getName() + Constants.SEPARATOR,
								Constants.EMPTY_STR)
						.replace(Constants.SEPARATOR, Constants.SLASH);
				break;
			}
		}
		return qualifiedClassName;
	}

	public boolean isVerbose() {
		return this.verbose;
	}

	public WrapperClientWithLog setVerbose(final boolean verbose) {
		this.verbose = verbose;
		return this;
	}

	/**
	 * If verbose is true, also display msg. Otherwise, log it only.
	 *
	 * @param msg
	 */
	public void log(final String msg) {
		this.log(msg, this.verbose);
	}

	private void log(final String msg, final boolean verbose) {
		if (verbose) {
			ProxyConsole.getInstance().debugOutput().println(msg);
		}
		this.log.append(msg).append(Constants.NEW_LINE);
	}

	void logProblems(final IProblem[] problems, final String javaSourcePath) {
		for (final IProblem problem : problems) {
			this.log(this.getUnifiedRelatvieJavaFileName(javaSourcePath));
			this.log(problem + " at " + problem.getSourceLineNumber());
		}
	}

	@Override
	public void parse(final NamedReader[] compilationUnitList,
			final ASTVisitor[] visitors) {
		final Date startTime = new Date();
		for (final NamedReader compilationUnit : compilationUnitList) {
			this.parseJavaFile(compilationUnit, visitors);
		}
		final Date endTime = new Date();
		this.displayAndLog("Parsing " + this.javaFileNum + " java files took "
				+ new Elapse(startTime, endTime) + " with " + this.numOfErrors
				+ " errors.");
		this.flush();
	}

	private void parseJavaFile(final NamedReader javaSource,
			final ASTVisitor[] visitors) {
		final ASTNode node = this.parser.parseJavaSourceCode(javaSource);
		if (visitors != null) {
			for (final ASTVisitor visitor : visitors) {
				node.accept(visitor);
			}
		}
		if (node instanceof CompilationUnit) {
			final CompilationUnit compilationUnit = (CompilationUnit) node;
			if (compilationUnit.getProblems().length > 0) {
				this.numOfErrors += compilationUnit.getProblems().length;
				this.logProblems(compilationUnit.getProblems(),
						javaSource.getName());
			}
		}
		this.javaFileNum++;
		if (this.javaFileNum % this.mileStone == 0) {
			this.displayAndLog("Parsed " + this.javaFileNum + " java files");
		}
	}

}
