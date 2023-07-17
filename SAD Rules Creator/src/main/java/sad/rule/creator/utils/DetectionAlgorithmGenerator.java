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
package sad.rule.creator.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import sad.rule.creator.RULECreator;
import util.io.ProxyConsole;
import com.sun.tools.javac.Main;

public class DetectionAlgorithmGenerator {
	public static void deleteDetectionAlgorithm(final String defectName) {
		// TODO: Add error check
		DetectionAlgorithmGenerator.deleteDir(new File(
			"../SAD/src/sad/antipattern/detection/" + defectName));
		DetectionAlgorithmGenerator.deleteDir(new File(
			"../SAD/src/sad/codesmell/detection/" + defectName));
	}
	// Deletes all files and subdirectories under dir.
	// Returns true if all deletions were successful.
	// If a deletion fails, the method stops attempting to delete and returns false.
	private static boolean deleteDir(final File file) {
		if (file.isDirectory()) {
			final String[] children = file.list();
			for (int i = 0; i < children.length; i++) {
				final boolean success =
					DetectionAlgorithmGenerator.deleteDir(new File(
						file,
						children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return file.delete();
	}
	public static String[] generateDetectionAlgorithm(final String ruleCardPath) {
		final String outputFileName = "../SAD/src/sad/codesmell/detection/";
		final String outputAntipatternName =
			"../SAD/src/sad/antipattern/detection/";

		// I create the Rule Card Parser.
		final RULECreator ruleCreator =
			new RULECreator(new String[] { ruleCardPath });

		// I parse the rule card.
		final String[] ruleCards = ruleCreator.parse();

		// Then, I compile the generated Java code.
		final List cmdLine = new ArrayList();
		for (int fileIndex = 0; fileIndex < ruleCards.length; fileIndex++) {
			// Set the classpath for compilation
			cmdLine.add("-classpath");
			// Yann 2007/02/18: Excelsior JET
			// I add to the compilation path
			// the paths to the JET Runtime
			// libraries to make sure I can
			// still compile even when using
			// JET installer.
			cmdLine.add("./bin" + File.pathSeparatorChar + "../CPL/bin/"
					+ File.pathSeparatorChar + "../SAD/bin/"
					+ File.pathSeparatorChar + "../PADL/bin/"
					+ File.pathSeparatorChar + "../PADL Creator ClassFile/bin/"
					+ File.pathSeparatorChar + "../POM/bin/"
					+ File.pathSeparatorChar + "../Binaries/bin/"
					+ File.pathSeparatorChar + "../Runtime/bin"
					+ File.pathSeparatorChar + "../Runtime/lib/rt.jar");
			cmdLine.add("-d");
			cmdLine.add("../SAD/bin");
			cmdLine.add("-g");

			cmdLine.add(outputAntipatternName + ruleCards[fileIndex] + "/"
					+ ruleCards[fileIndex] + "Detection.java");

			// Dynamicaly build the list of file to compile
			final File fileUtil =
				new File(outputFileName + ruleCards[fileIndex]);
			final File[] files = fileUtil.listFiles();
			int x = 0;

			while (x < files.length) {
				if (!files[x].isDirectory()) {
					cmdLine.add(outputFileName + ruleCards[fileIndex] + "/"
							+ files[x].getName());
				}
				x++;
			}

			final String[] cmdLineArray =
				(String[]) cmdLine.toArray(new String[cmdLine.size()]);
			Main.compile(cmdLineArray);
		}

		return ruleCards;
	}
	public static void outputRuleCard(
		final String ruleCardPath,
		final String content) {

		try {
			// Save the file
			final OutputStreamWriter writer =
				new OutputStreamWriter(new FileOutputStream(ruleCardPath));

			writer.write(content);
			writer.close();
		}
		catch (final IOException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	private DetectionAlgorithmGenerator() {
	}
}
