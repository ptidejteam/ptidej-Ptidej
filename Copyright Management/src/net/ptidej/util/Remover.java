/*******************************************************************************
 * Copyright (c) 2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package net.ptidej.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;

public class Remover {
	public static void main(final String[] args) {
		String filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/CPL/src/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/CPL Tests/src/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/DRAM/src/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/DRAM Examples/src/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/DRAM Libraries/src/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/EPI/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/EPI Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Java Parser/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Java Parser Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Mendel/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/MoDeC Bytecode Instrumentation/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/MoDeC Solver/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/MoDeC Solver Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Analyses/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Analyses Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator AOL/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator AOL Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator AspectJ/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator AspectJ Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator C# v1/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator C++/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator C++ (Eclipse)/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator C++ (Eclipse) Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator ClassFile/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator ClassFile Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator JavaFile (Eclipse)/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator JavaFile (Eclipse) Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator JavaFile-ClassFile Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Creator MSE/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Design Motifs/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Generator/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Generator PageRank/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Generator PageRank/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Generator Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Micro-pattern Analysis/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Micro-pattern Analysis Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Refactorings/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Refactorings Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Serialiser DB4O Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Serialiser JOS Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Serialiser NeoDatis/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Serialiser Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Statements/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Statements Creator AOL/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Statements Creator ClassFile/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/PADL Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/POM/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/POM Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Reporting/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Solver 3/Ptidej/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Solver 4/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Solver Metrics/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Solver Metrics Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Solver Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Analyses/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI AspectJ/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI AspectJ Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI C++/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Layouts/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Primitives AWT/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Primitives SWT/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Viewer/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Viewer AWT/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Viewer Extensions/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/Ptidej UI Viewer Standalone Swing/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/SAD/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/SAD Rules Creator/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/SAD Tests/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/SQUAD/src/";
		filesPath =
			"D:/Shared/Dropbox/Ptidej Workspaces/v5.2/SQUAD Tests/src/";

		final String filesExtension = ".java";
		final String[] copyrightFiles = new File("data/").list();
		Remover.removeCopyrightTexts(filesPath, filesExtension, copyrightFiles);
	}
	public Remover() {
	}
	private static final void removeCopyrightTexts(
		final String filesPath,
		final String filesExtension,
		final String[] copyrightFiles) {

		final File file = new File(filesPath);
		final String[] subfolders = file.list();
		for (final String folder : subfolders) {
			final String newsubfolder = filesPath + '/' + folder;
			final File newfile = new File(newsubfolder);
			if (newfile.isFile()) {
				if (folder.endsWith(filesExtension)) {
					Remover.removeCopyrightTexts(newfile, copyrightFiles);
				}
				else {
					// Skip this file.
				}
			}
			else {
				Remover.removeCopyrightTexts(
					newsubfolder,
					filesExtension,
					copyrightFiles);
			}
		}
	}
	private static void removeCopyrightTexts(
		final File file,
		final String[] copyrightFiles) {

		System.out.print("Analysing ");
		System.out.println(file);
		for (final String copyrightFile : copyrightFiles) {
			try {
				final LineNumberReader copyrightFileReader =
					new LineNumberReader(
						new FileReader("data/" + copyrightFile));
				final String firstCopyrightLine =
					copyrightFileReader.readLine();
				boolean firstCopyrightLineFound = false;
				String copyrightFileLine;

				final LineNumberReader fileReader =
					new LineNumberReader(new FileReader(file));
				String fileLine;

				final StringBuffer newFile = new StringBuffer();

				// Is first line of copyright found in the file?
				while ((fileLine = fileReader.readLine()) != null
						&& !firstCopyrightLineFound) {

					if (Remover.areLinesEqual(firstCopyrightLine, fileLine)) {
						firstCopyrightLineFound = true;
					}
					else {
						newFile.append(fileLine);
						newFile.append('\n');
					}
				}

				if (firstCopyrightLineFound) {
					System.out.println("\tFirst line of copyright found");

					boolean consecutiveCopyrightLinesFound = true;
					// Is the lines of copyright found in the file consecutively?
					while ((copyrightFileLine = copyrightFileReader.readLine()) != null
							&& consecutiveCopyrightLinesFound) {

						if (!Remover.areLinesEqual(copyrightFileLine, fileLine)) {
							consecutiveCopyrightLinesFound = false;
						}
						fileLine = fileReader.readLine();
					}

					if (consecutiveCopyrightLinesFound) {
						System.out
							.println("\tRest of the copyright found consecutively");
						// I found the first and following lines, I store the last line read from the file...
						newFile.append(fileLine);
						newFile.append('\n');
						// ... and I keep reading the file and only store the rest.
						while ((fileLine = fileReader.readLine()) != null) {
							newFile.append(fileLine);
							newFile.append('\n');
						}

						copyrightFileReader.close();
						fileReader.close();

						// Write the new file *without* the copyright text!
						System.out.println("\tWriting new file...");
						final Writer writer =
							new BufferedWriter(new FileWriter(file));
						writer.write(newFile.toString());
						writer.close();
						System.out.println("\tDone!");
					}
					else {
						System.out
							.println("\tRest of the copyright not found consecutively");
					}
				}
				else {
					System.out.println("\tFirst line of copyright not found.");

				}

				copyrightFileReader.close();
				fileReader.close();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}
	private static boolean areLinesEqual(
		final String copyrightLine,
		final String fileLine) {

		final String trimmedCopyrightLine = copyrightLine.trim();
		final String trimmedFileLine = fileLine.trim();

		if (trimmedCopyrightLine.contains("$$$")
				&& trimmedFileLine.startsWith(trimmedCopyrightLine.substring(
					0,
					trimmedCopyrightLine.length() - 3))) {
			return true;
		}
		else {
			return trimmedFileLine.equals(trimmedCopyrightLine);
		}
	}
}
