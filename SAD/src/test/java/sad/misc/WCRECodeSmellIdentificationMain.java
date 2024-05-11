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
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.ModelAnnotatorLOCAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;
import sad.designsmell.detection.repository.FunctionalDecomposition.FunctionalDecompositionDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import sad.designsmell.detection.repository.SwissArmyKnife.SwissArmyKnifeDetection;
import sad.kernel.ICodeSmell;
import sad.kernel.IDesignSmell;
import sad.kernel.impl.CodeSmell;
import sad.kernel.impl.CodeSmellComposite;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Pierre Leduc
 * @since 2006/05/06
 * 
 * This class identify all the CodeSmell associated with a class
 */
public class WCRECodeSmellIdentificationMain {

	/**
	 * Creation of the model
	 * 
	 * @return Set codeSmells
	 */
	public static IIdiomLevelModel createModel(final String resultsPath,
			final String nameSoftware, final String path) {

		System.err.println("Building the code-level model...");
		final ICodeLevelModel originalCodeLevelModel = Factory.getInstance()
				.createCodeLevelModel(path);
		try {
			originalCodeLevelModel.create(
					new CompleteClassFileCreator(new String[] { path }, true));
			final IIdiomLevelModel idiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(originalCodeLevelModel);

			final ModelAnnotatorLOCAnalysis annotator = new ModelAnnotatorLOCAnalysis();
			annotator.annotateFromJARs(new String[] { path }, idiomLevelModel);

			return idiomLevelModel;
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		return null;
	}

	/**
	 * Extract the information from the CodeSmell
	 * 
	 * @return String csvRow
	 *				  Return the information about the class in CSV format
	 */
	public static String extractData(final IClass aClass, final Set codeSmells,
			final String[] codeSmellToDetect) {
		final int values[] = new int[codeSmellToDetect.length];
		final StringBuffer buffer = new StringBuffer();

		//  Initialisation (Just to be sure)
		for (int i = 0; i < codeSmellToDetect.length; i++) {
			values[i] = 0;
		}

		// Check each CodeSmell
		for (int i = 0; i < codeSmellToDetect.length; i++) {

			final Iterator iter = codeSmells.iterator();
			while (iter.hasNext()) {
				if (WCRECodeSmellIdentificationMain.findClass(aClass,
						codeSmellToDetect[i], (ICodeSmell) iter.next())) {
					values[i] = 1;
				}
			}
		}

		// Fromat the outpu in CSV format
		buffer.append(aClass.getID());
		for (int i = 0; i < codeSmellToDetect.length; i++) {
			buffer.append(", " + values[i]);
		}

		buffer.append("\n");
		return buffer.toString();
	}

	/**
	 * Find if the Class is associated present in the CodeSmell
	 * 
	 * @return boolean isFound
	 *				Return if the Class have been detected in this CodeSmell  
	 */
	private static boolean findClass(final IClass aClass, final String csName,
			final ICodeSmell cs) {
		boolean isFound = false;

		if (cs instanceof CodeSmell) {
			// Search the current CodeSmell for the corresponding class
			if (cs.getName().equals(csName)
					&& cs.getIClassID().equals(aClass.getDisplayID())) {
				isFound = true;
			}

		}
		else if (cs instanceof CodeSmellComposite) {

			// recursive call, if the CodeSmell is Composite
			final Iterator iter = ((CodeSmellComposite) cs)
					.getSetOfCodeSmellsOfGeneric().iterator();
			while (iter.hasNext() && !isFound) {
				isFound = WCRECodeSmellIdentificationMain.findClass(aClass,
						csName, (ICodeSmell) iter.next());
			}
		}

		return isFound;
	}

	private static Set identifyCodeSmell(final IIdiomLevelModel idiomLevelModel,
			final String resultsPath, final String nameSoftware) {

		final Set codeSmells = new HashSet();
		long startTime = System.currentTimeMillis();

		/************** Detection of Blobs *********************/

		System.err.println("Detecting Blobs...");
		startTime = System.currentTimeMillis();
		final IDesignSmellDetection ad1 = new BlobDetection();
		final Set blobSet = ((BlobDetection) ad1).getDesignSmells();

		((BlobDetection) ad1).output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(resultsPath + nameSoftware + "_Blob.ini")));

		Iterator iter = blobSet.iterator();
		while (iter.hasNext()) {
			codeSmells.addAll(((IDesignSmell) iter.next()).listOfCodeSmells());
		}

		System.err
				.println("Time : " + (System.currentTimeMillis() - startTime));
		System.err.println("# Blob : " + blobSet.size() + "\n");

		/************** Detection of Spaghetti Code *********************/

		System.err.println("Detecting Spaghetti Code...");
		startTime = System.currentTimeMillis();
		final IDesignSmellDetection ad2 = new SpaghettiCodeDetection();
		final Set spagSet = ((SpaghettiCodeDetection) ad2).getDesignSmells();

		((SpaghettiCodeDetection) ad2)
				.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						resultsPath + nameSoftware + "_SpaghettiCode.ini")));

		iter = spagSet.iterator();
		while (iter.hasNext()) {
			codeSmells.addAll(((IDesignSmell) iter.next()).listOfCodeSmells());
		}

		System.err
				.println("Time : " + (System.currentTimeMillis() - startTime));
		System.err.println("# Spaghetti : " + spagSet.size() + "\n");

		/************** Detection of Functional Decomposition *********************/

		System.err.println("Detecting Functional Decomposition...");
		startTime = System.currentTimeMillis();
		final IDesignSmellDetection ad3 = new FunctionalDecompositionDetection();
		final Set funcSet = ((FunctionalDecompositionDetection) ad3)
				.getDesignSmells();

		((FunctionalDecompositionDetection) ad3).output(new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(resultsPath
						+ nameSoftware + "_FunctionalDecomposition.ini")));

		iter = funcSet.iterator();
		while (iter.hasNext()) {
			codeSmells.addAll(((IDesignSmell) iter.next()).listOfCodeSmells());
		}

		System.err
				.println("Time : " + (System.currentTimeMillis() - startTime));
		System.err.println("# Fuct Decomp : " + funcSet.size() + "\n");

		/************** Detection of Swiss Army Knife *********************/

		System.err.println("Detecting Swiss Army Knife...");
		startTime = System.currentTimeMillis();
		final IDesignSmellDetection ad4 = new SwissArmyKnifeDetection();
		final Set armySet = ((SwissArmyKnifeDetection) ad4).getDesignSmells();

		((SwissArmyKnifeDetection) ad4)
				.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						resultsPath + nameSoftware + "_SwissArmyKnife.ini")));

		iter = armySet.iterator();
		while (iter.hasNext()) {
			codeSmells.addAll(((IDesignSmell) iter.next()).listOfCodeSmells());
		}

		System.err
				.println("Time : " + (System.currentTimeMillis() - startTime));
		System.err.println("# Swiss Army : " + armySet.size() + "\n");

		//	/************** Detection of Poltergeist *********************/
		//	
		//	System.err.println("Detecting Poltergeist...");
		//	startTime = System.currentTimeMillis();
		//	final IAntiPatternDetection ad5 =
		//		new PoltergeistDetection(idiomLevelModel);
		//	Set poltSet = ((PoltergeistDetection) ad5).getSetOfAntiPatterns();
		//	((PoltergeistDetection) ad5).writeFile(
		//			resultsPath + nameSoftware + "_Poltergeist.ini");
		//	
		//	iter = poltSet.iterator();
		//	while (iter.hasNext())
		//		codeSmells.addAll(((IAntiPattern)iter.next()).getSetOfCodeSmells());
		//	
		//	System.err.println("Time : " + (System.currentTimeMillis() - startTime));
		//	System.err.println("# Poltergeist : " + poltSet.size());

		System.err.println("Total # of CS : " + codeSmells.size() + "\n");
		return codeSmells;

		// TODO Does this test do anything?!
	}

	public static void main(final String[] args) {

		final String main_path = "rsc/applications/";
		final String resultsPath = "rsc/060524 Xerces - WCRE/";

		final String[] codeSmellToDetect = {
				// Blob
				"DataClass", "NMD_NAD", // LargeClass
				"ControllerClass",

				// Spaghetti Code
				"No Inheritance", "Class and Gobal Variables", "Long Method",
				"Method with no parameters",

				// functional Decomposition
				"No Polymorphism", "No Inheritance", "ClassPrivateField",
				"ClassOneMethod",

				// Swiss Army Knife
				"MultipleInterface",

				//Poltergeist
				"StatelessClass", "LeafClass" };

		final String[] path = {
				//				"rsc/applications/Xerces-J_1_0_1.jar",
				//				"rsc/applications/Xerces-J_1_1_0.jar",
				//				"rsc/applications/Xerces-J_1_1_1.jar",
				//				"rsc/applications/Xerces-J_1_1_2.jar",
				//				"rsc/applications/Xerces-J_1_1_3.jar",
				//				"rsc/applications/Xerces-J_1_2_0.jar",
				//				"rsc/applications/Xerces-J_1_2_1.jar",
				//				"rsc/applications/Xerces-J_1_2_2.jar",
				//				"rsc/applications/Xerces-J_1_2_3.jar",
				//				"rsc/applications/Xerces-J_1_3_0.jar",
				//				"rsc/applications/Xerces-J_1_3_1.jar",
				//				"rsc/applications/Xerces-J_1_4_0.jar",
				//				
				//				"rsc/applications/Xerces-J_1_4_1.jar",
				//				"rsc/applications/Xerces-J_1_4_2.jar",
				//				"rsc/applications/Xerces-J_1_4_3.jar",
				//				"rsc/applications/Xerces-J_1_4_4.jar",
				//				"rsc/applications/Xerces-J_2_0_0.jar",
				//				"rsc/applications/Xerces-J_2_1_0.jar",
				//				"rsc/applications/Xerces-J_2_2_0.jar",
				//				"rsc/applications/Xerces-J_2_3_0.jar",
				//				"rsc/applications/Xerces-J_2_4_0.jar",
				//				
				//				"rsc/applications/Xerces-J_2_5_0.jar",
				//				"rsc/applications/Xerces-J_2_6_0.jar",
				//				"rsc/applications/Xerces-J_2_7_0.jar",
		};

		final IIdiomLevelModel[] models = new IIdiomLevelModel[path.length];
		final Set[] codeSmells = new Set[path.length];
		final String[] version = new String[path.length];

		// Build model and identify CodeSmell
		for (int i = 0; i < path.length; i++) {
			final String nameSoftware = path[i].substring(main_path.length());
			models[i] = WCRECodeSmellIdentificationMain.createModel(resultsPath,
					nameSoftware, path[i]);
			version[i] = nameSoftware;

			codeSmells[i] = WCRECodeSmellIdentificationMain
					.identifyCodeSmell(models[i], resultsPath, nameSoftware);
		}

		// Build csvHeader
		final StringBuffer csvHeader = new StringBuffer();
		csvHeader.append("ClassName");
		for (int j = 0; j < codeSmellToDetect.length; j++) {
			csvHeader.append("," + codeSmellToDetect[j]);
		}

		// Analyse each class of the model and the list of CodeSmell
		for (int j = 0; j < path.length; j++) {
			final StringBuffer csvData = new StringBuffer();
			csvData.append(csvHeader + "\n");

			long startTime = System.currentTimeMillis();
			startTime = System.currentTimeMillis();

			final Iterator iter = models[j]
					.getIteratorOnConstituents(IClass.class);
			while (iter.hasNext()) {
				final IClass currentClass = (IClass) iter.next();

				// Identify all CodeSmell associated with this class
				csvData.append(WCRECodeSmellIdentificationMain.extractData(
						currentClass, codeSmells[j], codeSmellToDetect));
			}

			// Write the CSV file
			final PrintWriter outFile = new PrintWriter(
					new BufferedWriter(ProxyDisk.getInstance().fileTempOutput(
							resultsPath + "CSV/" + version[j] + ".csv")));

			outFile.println(csvData.toString());
			outFile.close();

			System.err.println(System.currentTimeMillis() - startTime);
		}
	}
}
