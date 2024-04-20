/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.occurrence.helper;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc 
 * @since  2007/03/06
 */
public class PrincipalComponentAnalysis {
	public static void main(final String[] args) {
		final PrincipalComponentAnalysis analysis =
			new PrincipalComponentAnalysis();

		//	PrincipalComponents.print(
		//		analysis.solutionAnalysis(
		//			"QuickUML 2001",
		//			"../Ptidej Solver Data/070303 - ConstraintResults in QuickUML 2001 for AbstractFactory.ini",
		//			"AbstractFactory",
		//			PrincipalComponents.AbstractFactory));
		//	PrincipalComponents
		//		.print(analysis
		//			.buildReducedOccurrences(
		//				"Xerces v2.7.0",
		//				"../SAD Tests/rsc/results/OOPSLA'07/OOPSLA - DetectionResults in Xerces v2.7.0 (dom2) for Blob.ini",
		//				"Blob",
		//				PrincipalComponents.Blob));
		PrincipalComponents
			.print(analysis
				.buildReducedOccurrences(
					"GanttProject v1.10.2",
					"../SAD Tests/rsc/results/OOPSLA'07/OOPSLA - DetectionResults in GanttProject v1.10.2 for Blob.ini",
					"Blob",
					PrincipalComponents.Blob));

		//	final String[] oopslaPrograms =
		//		new String[] {
		//			"ArgoUML v0.18.1",
		//			"Azureus v2.3.0.6",
		//			"GanttProject v1.10.2",
		//			"Log4J v1.2.1",
		//			"Lucene v1.4",
		//			"Nutch v0.7.1",
		//			"PMD v1.8",
		//			"QuickUML 2001",
		//			"Xerces v1.0.1",
		//			"Xerces v2.7.0 (dom2)" };
		//
		//	for (int i = 0; i < oopslaPrograms.length; i++) {
		//		final String oopslaProgram = oopslaPrograms[i];
		//
		//		//	PrincipalComponents.print(
		//		//	analysis.solutionAnalysis(
		//		//		oopslaProgram,
		//		//		"../SAD Tests/rsc/OOPSLA - DetectionResults in "
		//		//			+ oopslaProgram
		//		//			+ " for Blob.ini",
		//		//		"Blob",
		//		//		PrincipalComponents.Blob);
		//		//	PrincipalComponents.print(
		//		analysis.buildReducedOccurrences(
		//			oopslaProgram,
		//			"../SAD Tests/rsc/DetectionResults in "
		//				+ oopslaProgram
		//				+ " for FunctionalDecomposition.ini",
		//			"FunctionalDecomposition",
		//			PrincipalComponents.FunctionalDecomposition);
		//		//	PrincipalComponents.print(
		//		//	analysis.solutionAnalysis(
		//		//		oopslaProgram,
		//		//		"../SAD Tests/rsc/OOPSLA - DetectionResults in "
		//		//			+ oopslaProgram
		//		//			+ " for SpaghettiCode.ini",
		//		//		"SpaghettiCode",
		//		//		PrincipalComponents.SpaghettiCode);
		//		//	PrincipalComponents.print(
		//		//	analysis.solutionAnalysis(
		//		//		oopslaProgram,
		//		//		"../SAD Tests/rsc/OOPSLA - DetectionResults in "
		//		//			+ oopslaProgram
		//		//			+ " for SwissArmyKnife.ini",
		//		//		"SwissArmyKnife",
		//		//		PrincipalComponents.SwissArmyKnife);
		//	}
	}
	public ReducedOccurrence[] buildReducedOccurrences(
		final String aName,
		final String aSolutionFile,
		final String aSolutionName,
		final char[][][] aSetOfPrincipalComponents) {

		try {
			final Properties properties = new Properties();
			properties
				.load(new ReaderInputStream(new FileReader(aSolutionFile)));
			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();
			final Occurrence[] solutions =
				solutionBuilder.getCanonicalOccurrences(properties);

			System.out.println();
			System.out.print(solutions.length);
			System.out.print(" row solutions in ");
			System.out.print(aName);
			System.out.print(" for ");
			System.out.println(aSolutionName);

			final SortedSet reducedOccurrences = new TreeSet();
			for (int i = 0; i < solutions.length; i++) {
				final Occurrence occurrence = solutions[i];
				for (int j = 0; j < aSetOfPrincipalComponents.length; j++) {
					final ReducedOccurrence reducedOccurrence =
						this.reduceOccurrence(
							occurrence,
							aSetOfPrincipalComponents[j]);
					if (reducedOccurrence != null) {
						reducedOccurrences.add(reducedOccurrence);
					}
				}
			}

			System.out.print(reducedOccurrences.size());
			System.out.print(" solutions in ");
			System.out.print(aName);
			System.out.print(" for ");
			System.out.println(aSolutionName);

			final ReducedOccurrence[] sortedPrincipalComponents =
				new ReducedOccurrence[reducedOccurrences.size()];
			reducedOccurrences.toArray(sortedPrincipalComponents);
			return sortedPrincipalComponents;
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		return new ReducedOccurrence[0];
	}
	public ReducedOccurrence reduceOccurrence(
		final Occurrence anOccurrence,
		final char[][] somePrincipalComponents) {

		final ReducedOccurrence reducedOccurrence = new ReducedOccurrence();
		int isComplete = 0;
		for (int i = 0; i < somePrincipalComponents.length; i++) {
			final char[] principalComponent = somePrincipalComponents[i];
			final OccurrenceComponent component =
				anOccurrence.getComponent(principalComponent);
			if (component != null) {
				reducedOccurrence.addComponent(component.getName(), component
					.getValue());
				isComplete++;
			}
		}
		if (isComplete == somePrincipalComponents.length) {
			return reducedOccurrence;
		}
		return null;
	}
}
