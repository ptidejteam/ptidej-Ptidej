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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import junit.framework.TestCase;
import org.eclipse.jdt.core.dom.Comment;
import parser.input.SourceInputsHolder;
import parser.input.impl.FileSystemJavaProject;
import parser.reader.impl.filesystem.FileNamedReaderFactory;
import parser.wrapper.JavaParser;
import parser.wrapper.NamedCompilationUnit;

public class JavaParserLineAndBlockCommentTest extends TestCase {
	public void testParse() throws Exception {
		final String[] classpathEntries = new String[] { "" };
		final String[] sourcepathEntries =
			new String[] { "../Java Parser Tests/resources/NestingClasses/src" };

		final SourceInputsHolder javaProject =
			new FileSystemJavaProject(
				Arrays.asList(classpathEntries),
				Arrays.asList(sourcepathEntries));

		final NamedCompilationUnit[] namedCompilationUnits =
			new JavaParser(javaProject).parse();

		final List<String> comments = new ArrayList<String>();

		for (final NamedCompilationUnit namedCompilationUnit : namedCompilationUnits) {
			for (final Comment comment : namedCompilationUnit
				.getLineAndBlockComment()) {
				comments.add(namedCompilationUnit.getCommentContent(
					comment,
					FileNamedReaderFactory.Instance
						.createNamedReaderFromFile(namedCompilationUnit
							.getJavaFilePath())));
			}
		}
		Assert.assertEquals(
			JavaParserLineAndBlockCommentTest.class.getName(),
			2,
			comments.size());
	}
}
