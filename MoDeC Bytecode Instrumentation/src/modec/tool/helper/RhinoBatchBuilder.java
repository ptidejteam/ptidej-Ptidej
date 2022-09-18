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
package modec.tool.helper;

import java.io.File;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/11/30
 */
public class RhinoBatchBuilder {
	private String mainPath;
	private String currentSection;
	public static void main(final String[] args) {
		final RhinoBatchBuilder rbb = new RhinoBatchBuilder();
		System.out.println("@ECHO OFF\n");
		rbb.mainPath =
			"D:/Software/P-MARt Workspace/Rhino v1.6R5/testdata/ecma";
		rbb.recurseInDirs(rbb.mainPath);
	}
	private void recurseInDirs(final String path) {
		final File pathFile = new File(path);
		final String[] elements = pathFile.list();
		for (int i = 0; i < elements.length; i++) {
			final String element = elements[i];
			final String newPath = path + '/' + element;
			final File newPathFile = new File(newPath);
			if (newPathFile.isFile()) {
				if (newPath.endsWith(".js")
					&& !newPath.endsWith("shell.js")
					&& !newPath.endsWith("browser.js")) {

					System.out.println(
						"JAVA   -classpath \"D:/Software/Ptidej Workspace/MoDeC Bytecode Instrumentation/bin\";\".\" org.mozilla.javascript.tools.shell.Main -f \"D:/Software/P-MARt Workspace/Rhino v1.6R5/testdata/shell.js\" -f \""
							+ this.mainPath
							+ "/shell.js\" -f \""
							+ this.mainPath
							+ "/"
							+ this.currentSection
							+ "/shell.js\" -f \""
							+ this.mainPath
							+ "/"
							+ this.currentSection
							+ "/"
							+ element
							+ "\"");
					System.out.println(
						"RENAME \"D:\\Software\\Ptidej Workspace\\MoDeC Bytecode Instrumentation Tests\\Instrumented Classes\\Rhino v1.6R5\\Trace.trace\" \"ecma - "
							+ this.currentSection
							+ " - "
							+ element.substring(0, element.indexOf(".js"))
							+ ".trace\"\n");
				}
			}
			else {
				this.currentSection = element;

				System.out.print("\n\n\nREM +-");
				for (int j = 0; j < this.currentSection.length(); j++) {
					System.out.print("-");
				}
				System.out.print("-+\nREM | ");
				System.out.print(this.currentSection);
				System.out.print(" |\nREM +-");
				for (int j = 0; j < this.currentSection.length(); j++) {
					System.out.print("-");
				}
				System.out.print("-+\n\n");

				this.recurseInDirs(newPath);
			}
		}
	}
}
