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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2007/08/09
 * 
 * @modified by Naouel Moha : 2008/07/17
 * 
 * This class displays the results of the files .ini in two different ways:
 * 
 * 1) There is one file as output
 * ArgoUML, v1, v2, v3
 * Blob, nbDefect_in_v1, nbDefect_in_v2, nbDefect_in_v3
 * SpaghettiCode, nbDefect_in_v1, nbDefect_in_v2, nbDefect_in_v3
 * 
 * 2) There are several output files corresponding to each version of the system 
 * ArgoUML_v1, Blob, SpagehttiCode
 * Class1, boolean, boolean   
 * 
 */
public class EvolutionAnalyser {
	private static final String CAPTION = "Antipatterns";
	private static final String FILE_ROOT =
		"rsc/evolutionWithBugs/resultsOfDefects/Results for ";
	private static final String MAIN_PATH =
		"../SAD Tests/rsc/evolutionWithBugs/resultsOfDefects/test/";

	/**
	 * Extract from the name of file the name of the system. Example:
	 * 'DetectionResults in Jmeter v.2.1 for Blob.ini'
	 */
	private static String getAntipatternName(final String fileName) {
		final String substring1 = " for ";
		final String substring2 = ".ini";
		final int begin = fileName.indexOf(substring1);
		final int end = fileName.indexOf(substring2);
		final String aSystemName =
			fileName.substring(begin + substring1.length(), end);
		return aSystemName;
	}

	private static String getProgramName(final String fileName) {
		final String substring1 = " in ";
		final String substring2 = " v";
		final int begin = fileName.indexOf(substring1);
		final int end = fileName.indexOf(substring2);
		final String aProgramName =
			fileName.substring(begin + substring1.length(), end);
		return aProgramName;
	}

	private static String getProgramVersion(final String fileName) {
		final String substring1 = " v";
		final String substring2 = " for ";
		final int begin = fileName.indexOf(substring1);
		final int end = fileName.indexOf(substring2);
		final String aProgramVersion =
			fileName.substring(begin + substring1.length(), end);
		return aProgramVersion;
	}

	public static void main(final String[] args) {
		final EvolutionAnalyser ea = new EvolutionAnalyser();
		ea.buildMatrices();
	}

	private final Map matrixProgramsbyVersionsbyAntipatterns = new HashMap();

	private void buildMatrices() {
		try {
			final File directory = new File(EvolutionAnalyser.MAIN_PATH);
			final String[] list = directory.list();
			for (int i = 0; i < list.length; i++) {
				final String fileName = list[i];
				if (fileName.endsWith(".ini")) {
					final String programName =
						EvolutionAnalyser.getProgramName(fileName);
					final String programVersion =
						EvolutionAnalyser.getProgramVersion(fileName);
					final String antipatternName =
						EvolutionAnalyser.getAntipatternName(fileName);

					final Properties properties = new Properties();
					properties.load(new ReaderInputStream(new FileReader(
						EvolutionAnalyser.MAIN_PATH + fileName)));

					final OccurrenceBuilder solutionBuilder =
						OccurrenceBuilder.getInstance();
					final Occurrence[] occurrences =
						solutionBuilder.getCanonicalOccurrences(properties);

					Set classesHavingAnAntipattern = new HashSet();
					classesHavingAnAntipattern =
						this.getClassNames(
							classesHavingAnAntipattern,
							occurrences);

					this.putInMatrix(
						programName,
						programVersion,
						antipatternName,
						classesHavingAnAntipattern);
				}
			}
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		this.printMatrix();
		// PrintWriter writerCSV;
		// writerCSV = new PrintWriter(new FileWriter(pathCSV));
		// writerCSV.println(
		// antipatternName
		// + ","
		// + programName
		// + ","
		// + occurrences.length);
		// writerCSV.close();
	}

	private Set getClassNames(
		final Set classesHavingAnAntipattern,
		final Occurrence[] occurrences) {
		// Get the list of classes having the antipattern
		final int nbAllOcc = occurrences.length;
		for (int j = 0; j < nbAllOcc; j++) {
			final Occurrence occ = occurrences[j];
			final List listOccComponents = occ.getComponents();
			if (!listOccComponents.isEmpty()) {
				final OccurrenceComponent solutionComponent =
					(OccurrenceComponent) listOccComponents.get(0);
				final String nameOfClass = solutionComponent.getDisplayValue();
				System.out.println(nameOfClass);
				if (!classesHavingAnAntipattern.contains(nameOfClass)) {
					classesHavingAnAntipattern.add(nameOfClass);
				}
			}
		}
		return classesHavingAnAntipattern;
	}

	private void printMatrix() {
		final Iterator iteratorOnPrograms =
			this.matrixProgramsbyVersionsbyAntipatterns.keySet().iterator();
		while (iteratorOnPrograms.hasNext()) {
			final String programName = (String) iteratorOnPrograms.next();
			PrintStream printStream = null;
			try {
				printStream =
					new PrintStream(new FileOutputStream(
						EvolutionAnalyser.FILE_ROOT + programName + ".csv"));
			}
			catch (final FileNotFoundException e) {
				e.printStackTrace();
			}
			// printStream.println(programName);

			final Map matrixVersionsbyAntipatterns =
				(Map) this.matrixProgramsbyVersionsbyAntipatterns
					.get(programName);

			this.printNameProgramVersions(
				programName,
				printStream,
				matrixVersionsbyAntipatterns);

			this.printNumberOfAntipatternsByVersion(
				printStream,
				matrixVersionsbyAntipatterns);

			printStream.println();
		}
	}

	private void printNameProgramVersions(
		final String programName,
		final PrintStream printStream,
		final Map matrixVersionsbyAntipatterns) {
		printStream.print(programName);
		printStream.print(',');
		final Iterator versions =
			matrixVersionsbyAntipatterns.keySet().iterator();
		while (versions.hasNext()) {
			final String versionName = (String) versions.next();
			if (!versionName.equals(EvolutionAnalyser.CAPTION)) {
				printStream.print(versionName);
				if (versions.hasNext()) {
					printStream.print(',');
				}
				else {
					printStream.println();
				}
			}
		}
	}

	private void printNumberOfAntipatternsByVersion(
		final PrintStream printStream,
		final Map matrixVersionsbyAntipatterns) {
		final Set legendList =
			(Set) matrixVersionsbyAntipatterns.get(EvolutionAnalyser.CAPTION);
		final String[] legendArray = new String[legendList.size()];
		legendList.toArray(legendArray);
		final int numberOfAntipatterns = legendArray.length;
		for (int index = 0; index < numberOfAntipatterns; index++) {
			printStream.print(legendArray[index]);
			printStream.print(',');
			final Iterator iteratorOnVersions =
				matrixVersionsbyAntipatterns.keySet().iterator();
			while (iteratorOnVersions.hasNext()) {
				final String versionName = (String) iteratorOnVersions.next();
				if (!versionName.equals(EvolutionAnalyser.CAPTION)) {
					final List count =
						(List) matrixVersionsbyAntipatterns.get(versionName);
					printStream.print(count.get(index));
					if (iteratorOnVersions.hasNext()) {
						printStream.print(',');
					}
				}
			}
			printStream.println();
		}
	}

	private void putInMatrix(
		final String aProgramName,
		final String aProgramVersion,
		final String anAntipattern,
		final Set classesHavingAnAntipattern) {

		System.out.print("Analysing ");
		System.out.print(aProgramName);
		System.out.print(" v");
		System.out.print(aProgramVersion);
		System.out.print(" for ");
		System.out.println(anAntipattern);

		Map matrixVersionbyAntipatterns;
		if (!this.matrixProgramsbyVersionsbyAntipatterns
			.containsKey(aProgramName)) {

			matrixVersionbyAntipatterns = new LinkedHashMap();
			this.matrixProgramsbyVersionsbyAntipatterns.put(
				aProgramName,
				matrixVersionbyAntipatterns);
		}
		matrixVersionbyAntipatterns =
			(Map) this.matrixProgramsbyVersionsbyAntipatterns.get(aProgramName);

		Set antipatternNames;
		if (!matrixVersionbyAntipatterns.containsKey(EvolutionAnalyser.CAPTION)) {

			antipatternNames = new HashSet();
			matrixVersionbyAntipatterns.put(
				EvolutionAnalyser.CAPTION,
				antipatternNames);
		}
		antipatternNames =
			(Set) matrixVersionbyAntipatterns.get(EvolutionAnalyser.CAPTION);
		antipatternNames.add(anAntipattern);

		List antipatternCounts;
		if (!matrixVersionbyAntipatterns.containsKey(aProgramVersion)) {
			antipatternCounts = new ArrayList();
			matrixVersionbyAntipatterns.put(aProgramVersion, antipatternCounts);
		}
		antipatternCounts =
			(List) matrixVersionbyAntipatterns.get(aProgramVersion);
		antipatternCounts.add(classesHavingAnAntipattern);
	}
}
