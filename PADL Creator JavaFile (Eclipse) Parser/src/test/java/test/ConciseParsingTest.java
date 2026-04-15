/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package test;

import java.util.Arrays;

import org.eclipse.jdt.core.dom.ASTVisitor;

import client.WrapperClientWithLog;
import parser.input.SourceInputsHolder;
import parser.input.impl.FileSystemJavaProject;
import test.visitor.MyVisitor;

/**
 *
 */
public class ConciseParsingTest extends AbstractParsing {
	public ConciseParsingTest() throws Exception {
		this("ConciseParseTest");
	}

	public ConciseParsingTest(final String aName) throws Exception {
		super(aName);

		final String[] classpathEntries = new String[] {
				"../PADL Creator JavaFile (Eclipse) Parser/target/test-classes/CodeAnalyser/libs/jaxb-api.jar",
				"../PADL Creator JavaFile (Eclipse) Parser/target/test-classes/CodeAnalyser/libs/tools.jar" };

		final String sourcePathEntry = "../PADL Creator JavaFile (Eclipse) Parser/target/test-classes/CodeAnalyser/src";
		final String[] sourcePathEntries = new String[] { sourcePathEntry };

		final String resultFilePath = "log_concise.txt";

		final SourceInputsHolder javaProject = new FileSystemJavaProject(
				Arrays.asList(classpathEntries),
				Arrays.asList(sourcePathEntries));

		final WrapperClientWithLog parserClient = new WrapperClientWithLog(
				javaProject, resultFilePath).setVerbose(false);

		final String oracleFilePath = "../PADL Creator JavaFile (Eclipse) Parser/target/test-classes/CodeAnalyser/log/log_oracle_concise.txt";

		final String testCaseName = "CodeAnalyser concise Test Case";

		final ASTVisitor visitor = new MyVisitor(parserClient);

		this.init(resultFilePath, oracleFilePath, testCaseName, parserClient,
				visitor);
	}

	public void testParse() {
		super.testParse();
	}
}
