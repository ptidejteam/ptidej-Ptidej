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

import org.junit.Assert;

import junit.framework.TestCase;
import parser.input.SourceInputsHolder;
import parser.input.impl.FileListJavaProject;
import parser.wrapper.JavaParser;
import parser.wrapper.NamedCompilationUnit;

public class FileListJavaProjectTest extends TestCase {
	public void testParse() throws Exception {
		final SourceInputsHolder javaProject = new FileListJavaProject(null,
				null,
				"../PADL Creator JavaFile (Eclipse) Parser/target/test-classes/NestingClasses/FileList.txt");

		final NamedCompilationUnit[] namedCompilationUnits = new JavaParser(
				javaProject).parse();

		Assert.assertEquals("JavaParserTest", namedCompilationUnits.length, 3);
	}

	public void testParseAnyFile() throws Exception {
		final SourceInputsHolder javaProject = new FileListJavaProject(null,
				null,
				"../PADL Creator JavaFile (Eclipse) Parser/target/test-classes/NestingClasses/FileListNotJavaFile.txt");

		try {
			new JavaParser(javaProject).parse();
			Assert.assertTrue(false);
		}
		catch (final Exception e) {
			Assert.assertTrue(true);
		}

	}
}
