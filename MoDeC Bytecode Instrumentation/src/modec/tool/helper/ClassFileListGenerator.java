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
 * @since  2007/11/11
 */
public class ClassFileListGenerator {
	public static void main(final String[] args) {
		// ClassFileListGenerator.printOutClassFiles("../Gombos/bin/");
		//	ClassFileListGenerator
		//		.printOutClassFiles("../../P-MARt Workspace/JHotDraw v5.4b1/bin/");
		//	ClassFileListGenerator
		//		.printOutClassFiles("../../P-MARt Workspace/JHotDraw v5.1/bin/");
		//	ClassFileListGenerator
		//		.printOutClassFiles("../../P-MARt Workspace/JEdit v4.2/");
		ClassFileListGenerator
			.printOutClassFiles("../../P-MARt Workspace/ArgoUML v0.19.8/");
		//	ClassFileListGenerator
		//		.printOutClassFiles("../../P-MARt Workspace/JFreeChart v1.0.13/");
	}
	private static void printOutClassFiles(final String aRootDirectory) {
		final File rootDirectoryFile = new File(aRootDirectory);
		final String[] list = rootDirectoryFile.list();
		for (int i = 0; i < list.length; i++) {
			final String stringFile =
				rootDirectoryFile.getAbsolutePath() + '/' + list[i];
			final File fileFile = new File(stringFile);
			if (fileFile.isFile()) {
				if (stringFile.endsWith(".class")) {
					final String filePath = stringFile.replace('\\', '/');
					System.out.println(filePath);
				}
			}
			else {
				ClassFileListGenerator.printOutClassFiles(stringFile);
			}
		}
	}
}
