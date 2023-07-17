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
package parser.test;

import java.io.File;
import org.junit.Assert;
import junit.framework.TestCase;
import org.eclipse.jdt.core.dom.ASTVisitor;
import parser.client.WrapperClient;
import common.tools.constants.Constants;
import common.tools.file.FileTools;

/**
 * 
 */
public abstract class AbstractParseTest extends TestCase {
	private String resultFilePath;
	private String oracleFilePath;
	private String testCaseName;
	private WrapperClient parserClient;
	private ASTVisitor visitor;

	public AbstractParseTest(final String aName) {
		super(aName);
	}
	protected void init(
		final String resultFilePath,
		final String oracleFilePath,
		final String testCaseName,
		final WrapperClient wrapper,
		final ASTVisitor visitor) {

		this.resultFilePath = resultFilePath;
		this.oracleFilePath = oracleFilePath;
		this.testCaseName = testCaseName;
		this.parserClient = wrapper;
		this.visitor = visitor;
	}

	/**
	 * Test method for
	 * {@link parser.wrapper.jdt.EclipseJDTParserWrapper#parse()}.
	 */
	protected void testParse() {
		this.parserClient
			.parseAllJavaSources(new ASTVisitor[] { this.visitor });

		final String STR = " java files took ";

		String oracle =
			new String(FileTools.Instance.readFile(new File(
				this.oracleFilePath))).replace(
				Constants.CR_LF,
				Constants.NEW_LINE);
		oracle = oracle.substring(1, oracle.lastIndexOf(STR));

		String result =
			new String(FileTools.Instance.readTempFile(new File(
				this.resultFilePath))).replace(
				Constants.CR_LF,
				Constants.NEW_LINE);
		result = result.substring(1, result.lastIndexOf(STR));

		Assert.assertEquals(this.testCaseName, oracle, result);
	}
}
