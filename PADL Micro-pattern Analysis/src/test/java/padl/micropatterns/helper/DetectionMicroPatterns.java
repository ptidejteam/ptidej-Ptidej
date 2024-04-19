/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.micropatterns.helper;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.Files;
import util.io.ProxyDisk;

public class DetectionMicroPatterns {
	public static void main(final String[] args) {
		final String programsPath = "../PADL Micro-pattern Analysis/target/test-classes/";
		final String resultsPath =
			"../PADL Micro-pattern Analysis/target/test-classes/results/";

		final String[] jarPath = {
		//"rsc/JRE_1.4.2_04",
		//"rsc/Scala",
		//"rsc/MJC",
		//"rsc/ant",
		//"rsc/JEdit",
		//"rsc/Tomcat",
		"../PADL Micro-pattern Analysis/target/test-classes/poseidon",
		//"rsc/JBoss",
		//				"rsc/Shared",
		//				"rsc/JRE_1.5.0",
			};

		for (int i = 0; i < jarPath.length; i++) {
			final String programName =
				jarPath[i].substring(programsPath.length());

			System.err.println("Listing JAR files...");
			final List listOfJars = new ArrayList();
			final File JarDirectory = new File(jarPath[i]);
			final String[] allFiles = JarDirectory.list();
			for (int j = 0; j < allFiles.length; j++) {
				if (allFiles[j].endsWith(".jar")) {
					listOfJars.add(jarPath[i] + "/" + allFiles[j]);
				}
			}
			final String[] arrayOfJars = new String[listOfJars.size()];
			listOfJars.toArray(arrayOfJars);

			/************** Creation of the program model ************************/

			System.err.println("Building the code-level model...");
			long startTime = System.currentTimeMillis();
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel("");
			try {
				codeLevelModel.create(new CompleteClassFileCreator(
					arrayOfJars,
					true));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
			System.err.println(System.currentTimeMillis() - startTime);

			/************** Detection of Micro-Patterns ***********************************/

			System.err.println("Detecting Micro-Patterns...");
			final MicroPatternDetector microPatternsDetector =
				new MicroPatternDetector(codeLevelModel);

			/************** Write Results ***********************************/

			System.err.println("Done.");
			try {
				final Date today = new Date();
				final SimpleDateFormat dateFormatter =
					new SimpleDateFormat("yyyyMMdd");
				final String dateStamp = dateFormatter.format(today);

				final PrintWriter outFile =
					new PrintWriter(ProxyDisk.getInstance().fileAbsoluteOutput(
						resultsPath + dateStamp + "_" + programName + ".txt"));
				outFile.println();
				outFile.println("###### Results #####");

				final SimpleDateFormat timeFormatter =
					new SimpleDateFormat("yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
				final String timeStamp = timeFormatter.format(today);
				outFile.println(timeStamp);

				final double nbOfClass =
					microPatternsDetector.getNumberOfClass();

				double nbDesignator =
					(microPatternsDetector.getNumberOfDesignator() * 100 / nbOfClass);
				double nbTaxonormy =
					(microPatternsDetector.getNumberOfTaxonomy() * 100 / nbOfClass);
				double nbJoiner =
					(microPatternsDetector.getNumberOfJoiner() * 100 / nbOfClass);
				double nbPool =
					(microPatternsDetector.getNumberOfPool() * 100 / nbOfClass);

				double nbSink =
					(microPatternsDetector.getNumberOfSink() * 100 / nbOfClass);
				double nbRecord =
					(microPatternsDetector.getNumberOfRecord() * 100 / nbOfClass);
				double nbDataManager =
					(microPatternsDetector.getNumberOfDataManager() * 100 / nbOfClass);

				double nbFunctionPointer =
					(microPatternsDetector.getNumberOfFunctionPointer() * 100 / nbOfClass);
				double nbFunctionObject =
					(microPatternsDetector.getNumberOfFunctionObject() * 100 / nbOfClass);
				double nbCobolLike =
					(microPatternsDetector.getNumberOfCobolLike() * 100 / nbOfClass);

				double nbStateless =
					(microPatternsDetector.getNumberOfStateless() * 100 / nbOfClass);
				double nbCommonState =
					(microPatternsDetector.getNumberOfCommonState() * 100 / nbOfClass);
				double nbCanopy =
					(microPatternsDetector.getNumberOfCanopy() * 100 / nbOfClass);
				double nbImmutable =
					(microPatternsDetector.getNumberOfImmutable() * 100 / nbOfClass);

				double nbBox =
					(microPatternsDetector.getNumberOfBox() * 100 / nbOfClass);
				double nbCompoundBox =
					(microPatternsDetector.getNumberOfCompoundBox() * 100 / nbOfClass);

				double nbImplementor =
					(microPatternsDetector.getNumberOfImplementor() * 100 / nbOfClass);
				double nbOverrider =
					(microPatternsDetector.getNumberOfOverrider() * 100 / nbOfClass);
				double nbExtender =
					(microPatternsDetector.getNumberOfExtender() * 100 / nbOfClass);

				double nbOutline =
					(microPatternsDetector.getNumberOfOutLine() * 100 / nbOfClass);
				double nbTrait =
					(microPatternsDetector.getNumberOfTrait() * 100 / nbOfClass);
				double nbStateMachine =
					(microPatternsDetector.getNumberOfStateMachine() * 100 / nbOfClass);
				double nbPureType =
					(microPatternsDetector.getNumberOfPureType() * 100 / nbOfClass);
				double nbAugmentedType =
					(microPatternsDetector.getNumberOfAugmentedType() * 100 / nbOfClass);
				double nbPseudoClass =
					(microPatternsDetector.getNumberOfPseudoClass() * 100 / nbOfClass);

				double nbSampler =
					(microPatternsDetector.getNumberOfSampler() * 100 / nbOfClass);
				double nbRestrictedCreation =
					(microPatternsDetector.getNumberOfRestrictedCreation() * 100 / nbOfClass);

				outFile.println("Designator\t" + nbDesignator);
				outFile.println("Taxonormy\t" + nbTaxonormy);
				outFile.println("Joiner\t" + nbJoiner);
				outFile.println("Pool\t" + nbPool);
				outFile.println("");
				outFile.println("Sink\t" + nbSink);
				outFile.println("Record\t" + nbRecord);
				outFile.println("DataManager\t" + nbDataManager);
				outFile.println("");
				outFile.println("Function Pointer\t" + nbFunctionPointer);
				outFile.println("Function Object\t" + nbFunctionObject);
				outFile.println("Cobol Like\t" + nbCobolLike);
				outFile.println("");
				outFile.println("State Less\t" + nbStateless);
				outFile.println("Common State\t" + nbCommonState);
				outFile.println("Canopy\t" + nbCanopy);
				outFile.println("Immutable\t" + nbImmutable);
				outFile.println("");
				outFile.println("Box\t" + nbBox);
				outFile.println("Compound Box\t" + nbCompoundBox);
				outFile.println("");
				outFile.println("Implementor\t" + nbImplementor);
				outFile.println("Overrider\t" + nbOverrider);
				outFile.println("Extender\t" + nbExtender);
				outFile.println("");
				outFile.println("Outline\t" + nbOutline);
				outFile.println("Trait\t" + nbTrait);
				outFile.println("State Machine\t" + nbStateMachine);
				outFile.println("PureType\t" + nbPureType);
				outFile.println("AugmentedType\t" + nbAugmentedType);
				outFile.println("PseudoClass\t" + nbPseudoClass);
				outFile.println("");
				outFile.println("Sampler\t" + nbSampler);
				outFile.println("Restricted Creation\t" + nbRestrictedCreation);
				outFile.println("");

				double CombinedTotal =
					nbDesignator + nbTaxonormy + nbJoiner + nbPool + nbSink
							+ nbRecord + nbDataManager + nbFunctionPointer
							+ nbFunctionObject + nbCobolLike + nbStateless
							+ nbCommonState + nbCanopy + nbImmutable + nbBox
							+ nbCompoundBox + nbImplementor + nbOverrider
							+ nbExtender + nbOutline + nbTrait + nbStateMachine
							+ nbPureType + nbAugmentedType + nbPseudoClass
							+ nbSampler + nbRestrictedCreation;

				outFile.println("Combined total\t" + CombinedTotal);
				outFile.println("Coverage\t"
						+ microPatternsDetector.getCoverage());
				outFile.println("");

				outFile.println("\nNb Class\t" + nbOfClass);
				outFile.close();
			}
			catch (final NumberFormatException nfe) {
				nfe.printStackTrace();
			}
		}
	}
}
