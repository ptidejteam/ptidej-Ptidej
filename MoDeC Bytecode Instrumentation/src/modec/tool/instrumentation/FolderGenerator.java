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
package modec.tool.instrumentation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author ngjanice
 */
public class FolderGenerator {
	public static void main(final String[] args) throws IOException {
		// final String sourcePathname = "../QuickUML 2001/bin/";
		// final String targetPathname = "../MoDeC Bytecode Instrumentation Tests/instrumented/";

		FolderGenerator.copyFolder(
			"../QuickUML 2001/bin/",
			"../QuickUML 2001/bin/",
			"../MoDeC Bytecode Instrumentation Tests/instrumented/");

	}

	public static void copyFolder(
		final String originalSouracePathname,
		final String localSourcePathname,
		final String targetPathname) throws IOException {

		final File targetFolder = new File(targetPathname);
		targetFolder.mkdirs();

		// make sure that the target folder exists before copying any files to it
		final File targetDirectory = new File(localSourcePathname);
		final File[] localFolder = targetDirectory.listFiles();

		if (localFolder.length == 0) {
			return;
		}
		else {
			for (int i = 0; i < localFolder.length; i++) {
				final File f = localFolder[i];
				final String newPathname =
					targetPathname
							+ f.getPath().substring(
								originalSouracePathname.length()).replace(
								'\\',
								'/');
				final File destFile = new File(newPathname);
				if (f.isDirectory()) {
					destFile.mkdirs();
					FolderGenerator.copyFolder(originalSouracePathname, f
						.getPath(), targetPathname);
				}
				else if (f.isFile()) {
					//						&& !(f
					//							.getName()
					//							.substring(f.getName().lastIndexOf("."))
					//							.equals(".class"))
					try {
						CopyFile.copyFile(f, destFile);
					}
					catch (final FileNotFoundException e) {
						// A FileNotFoundException could be raised
						// when attempting to copy locked files,
						// for example f*g SNV entries file.
						if (!f.getAbsolutePath().endsWith("\\.svn\\entries")) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
}
