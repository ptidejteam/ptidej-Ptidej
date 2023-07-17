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
package padl.creator.javafile.eclipse.helper;

import java.util.Arrays;
import padl.creator.javafile.eclipse.astVisitors.CompleteVisitor;
import parser.input.SourceInputsHolder;
import parser.input.impl.FileSystemJavaProject;
import parser.wrapper.JavaParser;

public class Main {
	// TODO: This class should not exist, should be a test case.
	public static void main(final String[] args) throws ClassNotFoundException {
		//the folder of the source code to analyse well organized like a project
		//final String sourcePathEntry = "./rsc/src/";
		final String sourcePathEntry = "./../PADL/src/";

		//using librairies?
		final String classPathEntry = "";
		final String[] sourcePathEntries = new String[] { sourcePathEntry };

		//using librairies?

		final String[] classpathEntries = new String[] { classPathEntry };

		try {
			SourceInputsHolder javaProject =
				new FileSystemJavaProject(
					Arrays.asList(classpathEntries),
					Arrays.asList(sourcePathEntries));

			final JavaParser eclipseSourceCodeParser =
				new JavaParser(javaProject);

			final CompleteVisitor completeVisitor = new CompleteVisitor();
			eclipseSourceCodeParser.parse(completeVisitor);

		}
		catch (Exception e) {

			e.printStackTrace();
		}

		//		final ICodeLevelModel padlModelFromJavaFiles =
		//			Factory.getInstance().createCodeLevelModel("");
		//		try {
		//			padlModelFromJavaFiles.create(new CompleteJavaFileCreator(
		//				sourcePathEntry,
		//				classPathEntry));
		//		}
		//		catch (final CreationException e) {
		//			e.printStackTrace(Output.getInstance().errorOutput());
		//		}
		//		padlModelFromJavaFiles.walk(new PadlPrinterVisitor(false));
	}
}
