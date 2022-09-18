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
package sad.misc;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.ModelAnnotatorLOCAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;
import sad.kernel.IDesignSmell;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since 2006/02/12
 * 
 * This class enables to check the difference between two sets of antipatterns
 */

@SuppressWarnings("deprecation")
public class DifferenceAntipatternMain {
	// TODO: Reorganise the test project to distinguish tests 
	// from helper class such as this one.

	/**
	 * Creation of the model
	 * 
	 * @param String
	 *            nameSoftware : name of the software to be tested
	 * @param String
	 *            path : path of the software
	 * @return Set setOfAntipatterns
	 */
	public static Set createModel(
		final String resultsPath,
		final String nameSoftware,
		final String path) {

		System.err.println("Building the code-level model...");
		long startTime = System.currentTimeMillis();
		try {
			final ICodeLevelModel originalCodeLevelModel =
				Factory.getInstance().createCodeLevelModel(path);
			originalCodeLevelModel.create(new CompleteClassFileCreator(
				new String[] { path },
				true));
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(originalCodeLevelModel);

			final ModelAnnotatorLOCAnalysis annotator =
				new ModelAnnotatorLOCAnalysis();
			annotator.annotateFromJARs(new String[] { path }, idiomLevelModel);

			/************** Detection of Blobs *********************/

			System.err.println("Detecting Blobs...");
			startTime = System.currentTimeMillis();
			System.err.println(System.currentTimeMillis() - startTime);
			final IDesignSmellDetection ad4 = new BlobDetection();
			final Set blobSet = ((BlobDetection) ad4).getDesignSmells();

			((BlobDetection) ad4).output(new PrintWriter(ProxyDisk
				.getInstance()
				.fileTempOutput(resultsPath + nameSoftware + "_Blob.ini")));
			
			// TODO Does this test do anything?!

			return blobSet;
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return new HashSet();
	}

	/**
	 * Compute the difference between the first set with the second set
	 */
	public static Set difference(final Set setA, final Set setB) {

		final Set setDiff = new HashSet();
		final Iterator iter = setA.iterator();
		while (iter.hasNext()) {
			final IDesignSmell ant = (IDesignSmell) iter.next();
			if (!ant.contains(setB)) {
				setDiff.add(ant);
			}
		}
		return setDiff;
	}

	public static void displayResult(
		final String resultsPath,
		final String nameOfTwoVersion,
		final Set setFirst,
		final String pathFirst,
		final Set setSecond,
		final String pathSecond) {

		// We print the result in a file
		try {
			final PrintWriter outFile =
				new PrintWriter(new BufferedWriter(
					ProxyDisk
						.getInstance()
						.fileTempOutput(
							resultsPath + "Difference_" + nameOfTwoVersion
									+ ".txt")));

			outFile.println();
			outFile.println("#############################################");
			outFile.println("###### Statistics #####");
			outFile.println("#############################################");
			outFile.println();
			final Date today = new Date();
			final SimpleDateFormat formatter =
				new SimpleDateFormat("yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
			final String timeStamp = formatter.format(today);
			outFile.println(timeStamp);

			// someSolutionsA
			outFile.println("#############################################");
			outFile.println("################# First Set # " + pathFirst);
			outFile.println("#############################################");
			outFile.println();
			int count = 0;
			final Iterator iterFirst = setFirst.iterator();
			while (iterFirst.hasNext()) {
				count++;
				outFile.println("---> Antipattern " + count);
				final IDesignSmell ant = (IDesignSmell) iterFirst.next();
				outFile.println(ant.toString());
			}

			// someSolutionsB
			outFile.println("#############################################");
			outFile.println("################# Second Set # " + pathSecond);
			outFile.println("#############################################");
			outFile.println();
			int count2 = 0;
			final Iterator iterScd = setSecond.iterator();
			while (iterScd.hasNext()) {
				count2++;
				outFile.println("---> Antipattern " + count2);
				final IDesignSmell ant = (IDesignSmell) iterScd.next();
				outFile.println(ant.toString());
			}

			// We perform : difference(someSolutionsA, someSolutionsB)
			outFile.println("#############################################");
			outFile.println("################# Différence # " + pathFirst
					+ " - " + pathSecond);
			outFile
				.println("Things that appear in the first version that do not appear in the second one");
			outFile.println("#############################################");
			outFile.println();
			final Set differences =
				DifferenceAntipatternMain.difference(setFirst, setSecond);
			int count3 = 0;
			final Iterator iterDiff = differences.iterator();
			while (iterDiff.hasNext()) {
				final IDesignSmell ant = (IDesignSmell) iterDiff.next();
				count3++;
				outFile.println("---> Antipattern " + count3);
				outFile.println(ant.toString());
			}

			// We perform : difference(someSolutionsB, someSolutionsA)
			outFile.println("#############################################");
			outFile.println("################# Différence # " + pathSecond
					+ " - " + pathFirst);
			outFile
				.println("Things that appear in the second version that do not appear in the first one");
			outFile.println("#############################################");
			outFile.println();
			final Set differencesInv =
				DifferenceAntipatternMain.difference(setSecond, setFirst);
			final Iterator iterInv = differencesInv.iterator();
			int count4 = 0;
			while (iterInv.hasNext()) {
				final IDesignSmell ant = (IDesignSmell) iterInv.next();
				count4++;
				outFile.println("---> Antipattern " + count4);
				outFile.println(ant.toString());
			}

			// We perform : intersection(someSolutionsA, someSolutionsB)
			outFile.println("#############################################");
			outFile.println("################# Intersection # ");
			outFile.println("#############################################");
			final Set inter =
				DifferenceAntipatternMain.intersection(setFirst, setSecond);
			int count5 = 0;
			final Iterator iter = inter.iterator();
			while (iter.hasNext()) {
				final IDesignSmell ant = (IDesignSmell) iter.next();
				count5++;
				outFile.println("---> Antipattern " + count5);
				outFile.println(ant.toString());
			}

			outFile.close();
		}
		catch (final NumberFormatException e) {
			System.out.println("Format error in input file: " + e);
		}
	}

	/**
	 * Compute the intersection between the first set with the second set
	 */
	public static Set intersection(final Set setA, final Set setB) {

		final Set setInter = new HashSet();
		final Iterator iter = setA.iterator();
		while (iter.hasNext()) {
			final IDesignSmell ant = (IDesignSmell) iter.next();
			if (ant.contains(setB)) {
				setInter.add(ant);
			}
		}
		return setInter;
	}

	public static void main(final String[] args) {

		final String main_path = "rsc/applications/";
		final String resultsPath = "rsc/060424 Xerces - Report/";

		final String[] path =
			{
					//				"rsc/applications/Xercesv1.0.1.jar",
					//				"rsc/applications/Xercesv1.2.0.jar",
					//				"rsc/applications/Xercesv1.2.1.jar",
					//				"rsc/applications/Xercesv1.3.0.jar",
					//				"rsc/applications/Xercesv1.3.1.jar",
					//				"rsc/applications/Xercesv1.4.0.jar",
					//				"rsc/applications/Xercesv1.4.1.jar",
					//				"rsc/applications/Xercesv1.4.2.jar",
					//				"rsc/applications/Xercesv1.4.3.jar",
					//				"rsc/applications/Xercesv2.0.0.jar",
					//				"rsc/applications/Xercesv2.1.0.jar",
					"rsc/applications/Xercesv2.2.0.jar",
					"rsc/applications/Xercesv2.3.0.jar",
					"rsc/applications/Xercesv2.4.0.jar",
					"rsc/applications/Xercesv2.5.0.jar"
			//				"rsc/applications/Xercesv2.6.0.jar",
			//				"rsc/applications/Xercesv2.7.0.jar"
			};

		final Set[] models = new Set[path.length];
		final String[] version = new String[path.length];

		for (int i = 0; i < path.length; i++) {
			final String nameSoftware = path[i].substring(main_path.length());
			models[i] =
				DifferenceAntipatternMain.createModel(
					resultsPath,
					nameSoftware,
					path[i]);
			version[i] = nameSoftware;
		}

		for (int j = 0; j < path.length - 1; j++) {
			DifferenceAntipatternMain.displayResult(
				resultsPath,
				version[j] + version[j + 1],
				models[j],
				path[j],
				models[j + 1],
				path[j + 1]);
		}

	}
}
