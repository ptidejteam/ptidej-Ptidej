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
package sad.detection.generators.old;

import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.ModelAnnotatorLOCAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;
import sad.designsmell.detection.repository.FunctionalDecomposition.FunctionalDecompositionDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import sad.designsmell.detection.repository.SwissArmyKnife.SwissArmyKnifeDetection;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/03
 */
public class CodeSmellDetectionCalleronEclipse {
	public static void main(final String[] args) {
		final String programName = "Eclipse v3.1.2";
		final String pluginsPath = "E:/Programs/Eclipse 3/plugins/";
		final String resultsPath = "rsc/";

		final Writer writer = ProxyDisk.getInstance().fileTempOutput(
				resultsPath + "DetectionResults in " + programName + ".log");
		ProxyConsole.getInstance().setNormalOutput(writer);
		ProxyConsole.getInstance().setErrorOutput(writer);
		ProxyConsole.getInstance().setDebugOutput(writer);

		ProxyConsole.getInstance().normalOutput()
				.println("Listing JAR files...");
		final List listOfPlugins = new ArrayList();
		final File pluginDirectory = new File(pluginsPath);
		final String[] allFiles = pluginDirectory.list();
		for (int i = 0; i < allFiles.length; i++) {
			if (allFiles[i].endsWith(".jar") && allFiles[i].indexOf(".ui") > -1
					&& allFiles[i].indexOf(".jdt") == -1) {

				listOfPlugins.add(pluginsPath + allFiles[i]);
			}
		}
		final String[] arrayOfPlugins = new String[listOfPlugins.size()];
		listOfPlugins.toArray(arrayOfPlugins);

		/************** Creation of the program model ************************/

		ProxyConsole.getInstance().normalOutput()
				.println("Building the code-level model...");
		long startTime = System.currentTimeMillis();
		final ICodeLevelModel originalCodeLevelModel = Factory.getInstance()
				.createCodeLevelModel("");
		final ModelStatistics statistics = new ModelStatistics();
		originalCodeLevelModel.addModelListener(statistics);
		try {
			originalCodeLevelModel
					.create(new CompleteClassFileCreator(arrayOfPlugins, true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().normalOutput());
		}
		ProxyConsole.getInstance().normalOutput().println(statistics);
		ProxyConsole.getInstance().normalOutput().print("in ");
		ProxyConsole.getInstance().normalOutput()
				.print(System.currentTimeMillis() - startTime);
		ProxyConsole.getInstance().normalOutput().println(" milliseconds");

		ProxyConsole.getInstance().normalOutput()
				.println("Annotating the code-level model...");
		startTime = System.currentTimeMillis();
		final ModelAnnotatorLOCAnalysis annotator = new ModelAnnotatorLOCAnalysis();
		annotator.annotateFromJARs(arrayOfPlugins, originalCodeLevelModel);
		ProxyConsole.getInstance().normalOutput().print("in ");
		ProxyConsole.getInstance().normalOutput()
				.print(System.currentTimeMillis() - startTime);
		ProxyConsole.getInstance().normalOutput().println(" milliseconds");

		ProxyConsole.getInstance().normalOutput()
				.println("Building the idiom-level model...");
		startTime = System.currentTimeMillis();
		try {
			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(originalCodeLevelModel);
			ProxyConsole.getInstance().normalOutput().println(statistics);
			ProxyConsole.getInstance().normalOutput().print("in ");
			ProxyConsole.getInstance().normalOutput()
					.print(System.currentTimeMillis() - startTime);
			ProxyConsole.getInstance().normalOutput().println(" milliseconds");

			/************** Detection of Blobs ***********************************/

			ProxyConsole.getInstance().normalOutput()
					.println("Detecting Blobs...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad4 = new BlobDetection();
			ad4.detect(idiomLevelModel);
			ProxyConsole.getInstance().normalOutput()
					.println(System.currentTimeMillis() - startTime);
			((BlobDetection) ad4).output(new PrintWriter(ProxyDisk.getInstance()
					.fileTempOutput(resultsPath + "DetectionResults in "
							+ programName + " for Blob.ini")));

			/************** Detection of Functional Decompositions ***************/

			ProxyConsole.getInstance().normalOutput()
					.println("Detecting Functional Decompositions...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad5 = new FunctionalDecompositionDetection();
			ad5.detect(idiomLevelModel);
			ProxyConsole.getInstance().normalOutput()
					.println(System.currentTimeMillis() - startTime);
			((FunctionalDecompositionDetection) ad5)
					.output(new PrintWriter(ProxyDisk.getInstance()
							.fileTempOutput(resultsPath + "DetectionResults in "
									+ programName
									+ " for FunctionalDecomposition.ini")));

			/************** Detection of SpaghettiCodes **************************/

			ProxyConsole.getInstance().normalOutput()
					.println("Detecting Spaghetti Codes...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad1 = new SpaghettiCodeDetection();
			ad1.detect(idiomLevelModel);
			ProxyConsole.getInstance().normalOutput()
					.println(System.currentTimeMillis() - startTime);
			((SpaghettiCodeDetection) ad1)
					.output(new PrintWriter(ProxyDisk.getInstance()
							.fileTempOutput(resultsPath + "DetectionResults in "
									+ programName + " for SpaghettiCode.ini")));

			/************** Detection of SwissArmyKnives *************************/

			ProxyConsole.getInstance().normalOutput()
					.println("Detecting Swiss Army Knives...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad2 = new SwissArmyKnifeDetection();
			ad2.detect(idiomLevelModel);
			ProxyConsole.getInstance().normalOutput()
					.println(System.currentTimeMillis() - startTime);
			((SwissArmyKnifeDetection) ad2)
					.output(new PrintWriter(ProxyDisk.getInstance()
							.fileTempOutput(resultsPath + "DetectionResults in "
									+ programName
									+ " for SwissArmyKnife.ini")));

			/************** Generation of some statistics ************************/

			final PrintWriter outFile = new PrintWriter(
					new BufferedWriter(ProxyDisk.getInstance().fileTempOutput(
							resultsPath + programName + ".stats.txt")));
			outFile.println();
			outFile.println("###### Statistics #####");
			final Date today = new Date();
			final SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
			final String timeStamp = formatter.format(today);
			outFile.println(timeStamp);
			outFile.print("Number of Blobs found: ");
			outFile.println(ad4.getDesignSmells().size());
			outFile.print("Number of FunctionalDecompositions found: ");
			outFile.println(ad5.getDesignSmells().size());
			outFile.print("Number of SpaghettiCodes found: ");
			outFile.println(ad1.getDesignSmells().size());
			outFile.print("Number of Swiss army knives found: ");
			outFile.println(ad2.getDesignSmells().size());
			outFile.close();

			/*************************************** Done ************************/

			ProxyConsole.getInstance().normalOutput().println("Done.");
			ProxyConsole.getInstance().debugOutput().close();
			ProxyConsole.getInstance().errorOutput().close();
			ProxyConsole.getInstance().normalOutput().close();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
