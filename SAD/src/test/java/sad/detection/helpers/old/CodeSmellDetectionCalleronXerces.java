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
package sad.detection.helpers.old;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @since 2006/02/03
 */
public class CodeSmellDetectionCalleronXerces {
	private static void detectDesignDefects(final String programName,
			final String path, final String resultsPath) {

		System.gc();

		/************** Creation of the program model ************************/

		System.err.println("Building the code-level model...");
		long startTime = System.currentTimeMillis();
		final ICodeLevelModel originalCodeLevelModel = Factory.getInstance()
				.createCodeLevelModel(path);
		final ModelStatistics statistics = new ModelStatistics();
		originalCodeLevelModel.addModelListener(statistics);
		try {
			originalCodeLevelModel.create(
					new CompleteClassFileCreator(new String[] { path }, true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		System.err.println(statistics);
		System.err.println(System.currentTimeMillis() - startTime);

		System.err.println("Building the idiom-level model...");
		startTime = System.currentTimeMillis();
		try {
			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(originalCodeLevelModel);
			System.err.println(statistics);

			startTime = System.currentTimeMillis();
			final ModelAnnotatorLOCAnalysis annotator = new ModelAnnotatorLOCAnalysis();
			annotator.annotateFromJARs(new String[] { path }, idiomLevelModel);
			System.err.println(System.currentTimeMillis() - startTime);

			/************** Detection of Blobs ***********************************/

			System.err.println("Detecting Blobs...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad4 = new BlobDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((BlobDetection) ad4).output(new PrintWriter(ProxyDisk.getInstance()
					.fileTempOutput(resultsPath + programName + "_Blob.ini")));

			/************** Detection of Functional Decompositions ***************/

			System.err.println("Detecting Functional Decompositions...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad5 = new FunctionalDecompositionDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((FunctionalDecompositionDetection) ad5).output(new PrintWriter(
					ProxyDisk.getInstance().fileTempOutput(resultsPath
							+ programName + "FunctionalDecomposition.ini")));

			/************** Detection of SpaghettiCodes **************************/

			System.err.println("Detecting Spaghetti Codes...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad1 = new SpaghettiCodeDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((SpaghettiCodeDetection) ad1).output(
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
							resultsPath + programName + "_SpaghettiCode.ini")));

			/************** Detection of SwissArmyKnives *************************/

			System.err.println("Detecting Swiss Army Knives...");
			startTime = System.currentTimeMillis();
			final IDesignSmellDetection ad2 = new SwissArmyKnifeDetection();
			System.err.println(System.currentTimeMillis() - startTime);
			((SwissArmyKnifeDetection) ad2).output(new PrintWriter(
					ProxyDisk.getInstance().fileTempOutput(resultsPath
							+ programName + "_SwissArmyKnife.ini")));

			//	/************** Detection of Poltergeists ****************************/
			//
			//	startTime = System.currentTimeMillis();
			//	final IAntiPatternDetection ad3 =
			//		new PoltergeistDetection(idiomLevelModel);
			//	System.err.println(System.currentTimeMillis() - startTime);
			//	((PoltergeistDetection) ad3).outputResults(
			//		resultsPath + programName + "_Poltergeist.ini");

			/************** Generation of some statistics ************************/

			System.err.println("Done.");
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
			//			outFile.print("Number of Poltergeists found: ");
			//			outFile.println(ad3.getSetOfAntiPatterns().size());
			outFile.print("Number of Blobs found: ");
			outFile.println(ad4.getDesignSmells().size());
			outFile.print("Number of FunctionalDecompositions found: ");
			outFile.println(ad5.getDesignSmells().size());
			outFile.print("Number of SpaghettiCodes found: ");
			outFile.println(ad1.getDesignSmells().size());
			outFile.print("Number of Swiss army knives found: ");
			outFile.println(ad2.getDesignSmells().size());
			outFile.close();
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public static void main(final String[] args) {
		final String programsPath = "../SAD Tests/rsc/applications/";
		final String resultsPath = "../SAD Tests/rsc/060424 Xerces - Threshold/";

		final String[] path = {
				//			"rsc/applications/Xercesv1.0.1.jar",
				//			"rsc/applications/Xercesv1.2.0.jar",
				//			"rsc/applications/Xercesv1.2.1.jar",
				//			"rsc/applications/Xercesv1.3.0.jar",
				"../SAD Tests/rsc/applications/Xercesv1.3.1.jar",
				//			"rsc/applications/Xercesv1.4.0.jar",
				//			"rsc/applications/Xercesv1.4.1.jar",
				"../SAD Tests/rsc/applications/Xercesv1.4.2.jar",
				//			"rsc/applications/Xercesv1.4.3.jar",
				//			"rsc/applications/Xercesv2.0.0.jar",
				//			"rsc/applications/Xercesv2.1.0.jar",
				//			"rsc/applications/Xercesv2.2.0.jar",
				//			"rsc/applications/Xercesv2.3.0.jar",
				//			"rsc/applications/Xercesv2.4.0.jar",
				//			"rsc/applications/Xercesv2.5.0.jar",
				//			"rsc/applications/Xercesv2.6.0.jar",
				//			"rsc/applications/Xercesv2.7.0.jar"
		};

		for (int i = 0; i < path.length; i++) {
			final String programName = path[i].substring(programsPath.length());

			System.err.print("Detecting design defects in ");
			System.err.print(programName);
			System.err.println("...");

			CodeSmellDetectionCalleronXerces.detectDesignDefects(programName,
					path[i], resultsPath);
		}
		System.err.println("Done.");

		// TODO This test does not do anything?!
	}
}
