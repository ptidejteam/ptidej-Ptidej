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
package parser.declarations;

import java.util.Arrays;
import parser.input.SourceInputsHolder;
import parser.input.impl.FileListJavaProject;
import parser.visitor.FromalAndActualParameter;
import parser.wrapper.JavaParser;

public class Main {

	public static void main(final String[] args) {
		final String[] classpathEntries = new String[] { "" };
		final String[] sourcepathEntries =
			new String[] { "./rsc/Declarations/" };
		//final String[] sourcepathEntries = new String[] { "./repository/software/FileLevelCommits/tomcat/" };

		SourceInputsHolder javaProject = null;
		try {
			javaProject =
				new FileListJavaProject(
					Arrays.asList(classpathEntries),
					Arrays.asList(sourcepathEntries),
					args[0]);
		}
		catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Start parsing and visiting .....");
		//new JavaParser(javaProject).parse(new Declaration(false));

		//	new JavaParser(javaProject).parse(new VariableAssignment());
		//	try {
		//		VariableAssignment.out.close();
		//	}
		//	catch (IOException e) {
		//		System.out.println("problem with VariableAssignment.out.close()");
		//	}

		//new JavaParser(javaProject).parse(new ComparisonStatement());
		//new JavaParser(javaProject).parse(new MethodInvocationANDActualParam());

		//new JavaParser(javaProject).parse(new ReturnStatementAndReturnTypeAndMethodName());
		new JavaParser(javaProject).parse(new FromalAndActualParameter());

	}
}
