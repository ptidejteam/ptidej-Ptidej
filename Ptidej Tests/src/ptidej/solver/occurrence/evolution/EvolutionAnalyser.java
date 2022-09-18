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
package ptidej.solver.occurrence.evolution;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import ptidej.solver.occurrence.PrincipalComponentAnalysis;
import ptidej.solver.occurrence.PrincipalComponents;
import ptidej.solver.occurrence.ReducedOccurrence;
import util.io.ReaderInputStream;


/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/09
 */
public class EvolutionAnalyser {
	public static void main(final String[] args) {
		final String MAIN_PATH = "../Ptidej Solver Data/Azureus Versions/";

		final File directory = new File(MAIN_PATH);
		final String[] list = directory.list();
		//	final FileWriter writer =
		//		new FileWriter("../Ptidej Solver Data/Azureus Versions/Composite.csv");
		final SortedMap fileVsReducedOccurrences = new TreeMap();
		for (int i = 0; i < list.length; i++) {
			final String file = list[i];
			if (file.endsWith("for Composite.ini")) {
				final ReducedOccurrence[] reducedOccurrences =
					new PrincipalComponentAnalysis().buildReducedOccurrences(
						"Azureus",
						directory.getAbsolutePath() + '/' + file,
						"Composite",
						PrincipalComponents.Composite);
				fileVsReducedOccurrences.put(file, reducedOccurrences);
				//	writer.write(file + ',');
				//	for (int j = 0; j < reducedOccurrences.length; j++) {
				//		final ReducedOccurrence pc = reducedOccurrences[j];
				//		writer.write(pc.getKey());
				//		if (j < reducedOccurrences.length) {
				//			writer.write(',');
				//		}
				//	}
				//	writer.write('\n');
			}
		}
		//	writer.close();

		final Iterator iterator = fileVsReducedOccurrences.keySet().iterator();
		while (iterator.hasNext()) {
			final String file1 = (String) iterator.next();
			final ReducedOccurrence[] reducedOccurrencesV1 =
				(ReducedOccurrence[]) fileVsReducedOccurrences.get(file1);
			if (iterator.hasNext()) {
				final String file2 = (String) iterator.next();
				final ReducedOccurrence[] reducedOccurrencesV2 =
					(ReducedOccurrence[]) fileVsReducedOccurrences.get(file2);

				System.out.println("\nComparing");
				System.out.print('\t');
				System.out.println(file1);
				System.out.print('\t');
				System.out.println(file2);

				for (int j = 0; j < reducedOccurrencesV1.length; j++) {
					final ReducedOccurrence ro1 = reducedOccurrencesV1[j];

					boolean found = false;
					for (int k = 0; k < reducedOccurrencesV2.length; k++) {
						final ReducedOccurrence ro2 = reducedOccurrencesV2[k];
						if (ro1.equals(ro2)) {
							found = true;
						}
					}
					if (!found) {
						System.out.print("Deleted: ");
						System.out.println(ro1);
					}
					else {
						System.out.print("Same: ");
						System.out.println(ro1);
						final Occurrence occurrenceV1 =
							findMatchingOccurrence(MAIN_PATH + file1, ro1);
						final Occurrence occurrenceV2 =
							findMatchingOccurrence(MAIN_PATH + file2, ro1);

						if (occurrenceV1.getComponents().size() != occurrenceV2
							.getComponents()
							.size()) {

							System.err.print(ro1);
							System.err
								.println(" has different number of roles!");
						}
						else {
							final Iterator iteratorOnComponents =
								occurrenceV1.getComponents().iterator();
							while (iteratorOnComponents.hasNext()) {
								final char[] component =
									((OccurrenceComponent) iteratorOnComponents
										.next()).getName();

								if (!occurrenceV1
									.getComponent(component)
									.equals(
										occurrenceV2.getComponent(component))) {

									System.err.print(ro1);
									System.err
										.println(" has different classes playing same roles!");
								}
							}
						}
					}
				}

				for (int j = 0; j < reducedOccurrencesV2.length; j++) {
					final ReducedOccurrence ro1 = reducedOccurrencesV2[j];

					boolean found = false;
					for (int k = 0; k < reducedOccurrencesV1.length; k++) {
						final ReducedOccurrence ro2 = reducedOccurrencesV1[k];
						if (ro1.equals(ro2)) {
							found = true;
						}
					}
					if (!found) {
						System.out.print("Added: ");
						System.out.println(ro1);
					}
				}
			}
		}
	}
	public static Occurrence findMatchingOccurrence(
		final String aFileName,
		final ReducedOccurrence aReducedOccurrence) {

		try {
			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();

			final Properties properties = new Properties();
			properties.load(new ReaderInputStream(new FileReader(aFileName)));
			final Occurrence[] occurrences =
				solutionBuilder.getCanonicalOccurrences(properties);
			for (int i = 0; i < occurrences.length; i++) {
				final Occurrence occurrence = occurrences[i];
				final Iterator iterator1 =
					aReducedOccurrence.getComponentNames().iterator();
				boolean candidate = true;
				while (iterator1.hasNext() && candidate) {
					final char[] principalComponent = (char[]) iterator1.next();

					if (!occurrence
						.getComponent(principalComponent)
						.getValue()
						.equals(
							aReducedOccurrence
								.getComponentValue(principalComponent))) {
						candidate = false;
					}
				}
				if (candidate) {
					return occurrence;
				}
			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
