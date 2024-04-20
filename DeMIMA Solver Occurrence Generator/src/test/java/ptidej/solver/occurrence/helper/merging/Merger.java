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
package ptidej.solver.occurrence.helper.merging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

public class Merger {
	public static void main(final String[] args) {
		final Merger merger = new Merger();
		merger.mergeClassesRolesFiles(
			"../SAD Tests/rsc/ArgoUML/ArgoUML-",
			"../Ptidej Solver Data/ArgoUML Versions/",
			"ConstraintResults in ArgoUML v",
			"rsc/AP + DP/ArgoUML v");
		merger.mergeClassesRolesFiles(
			"../SAD Tests/rsc/Mylyn/mylyn-",
			"../Ptidej Solver Data/Mylyn Versions/",
			"ConstraintResults in Mylyn v",
			"rsc/AP + DP/Mylyn v");
		merger.mergeClassesRolesFiles(
			"../SAD Tests/rsc/Rhino/rhino-",
			"../Ptidej Solver Data/Rhino Versions/",
			"ConstraintResults in Rhino v",
			"rsc/AP + DP/Rhino v");
	}

	private final VersionClassRolesHolder versionClassRoles;
	public Merger() {
		this.versionClassRoles = new VersionClassRolesHolder();
	}
	private void mergeClassesRolesFiles(
		final String anAPPath,
		final String aDPPath,
		final String aDPFilePrefix,
		final String aDestinationPrefixFileName) {

		final String[] fileNames = new File(aDPPath).list();
		String versionName = "@"; // Impossible character :-)
		for (int i = 0; i < fileNames.length; i++) {
			// ConstraintResults in Mylyn v3.0.4 20090122-1000-e3.3 for TemplateMethod.ini
			// There are many file with the same version (one for each design pattern),
			// therefore, I must skip them once I have done one of them!
			final String fileName = fileNames[i];
			if (fileName.indexOf(versionName) == -1) {
				try {
					versionName = fileName.substring(aDPFilePrefix.length());
					int index = versionName.indexOf('-');
					if (index == -1) {
						index = versionName.indexOf(' ');
					}
					versionName = versionName.substring(0, index);

					this.populateVersionsClassRoles(versionName, aDPPath, true);
					// I must try twice because some data does not have 
					// the same version name as expected :-( !?
					this.populateVersionsClassRoles(versionName, anAPPath
							+ versionName + "/", false);
					this.populateVersionsClassRoles(
						versionName.replaceAll("\\.", ""),
						anAPPath + versionName.replaceAll("\\.", "") + "/",
						false);

					final Writer writer =
						ProxyDisk.getInstance().fileAbsoluteOutput(
							aDestinationPrefixFileName + versionName + ".csv");
					//	System.out.println(merger.versionClassRoles
					//		.getNumberOfClassesWithPositiveAndNegativeRoles());
					//	System.out.println(merger.versionClassRoles
					//		.getClassesWithPositiveAndNegativeRoles());
					writer.write(this.versionClassRoles
						.getClassesWithPositiveAndNegativeRoles());
					writer.close();
				}
				catch (final Exception e) {
					System.err.println(fileName);
					System.err.println(versionName);
					e.printStackTrace();
				}
			}
		}
	}
	private void populateVersionsClassRoles(
		final String aVersionUniqueName,
		final String aPathToSomeOccurrences,
		final boolean isPositive) {

		final String[] fileNames = new File(aPathToSomeOccurrences).list();
		if (fileNames == null) {
			System.err.print(aPathToSomeOccurrences);
			System.err.println(" is empty?!");
			return;
		}
		for (int i = 0; i < fileNames.length; i++) {
			final String fileName = fileNames[i];
			//	boolean doesMatchAUniqueName = false;
			//	for (int j = 0; j < aVersionUniqueNames.length
			//			|| doesMatchAUniqueName; j++) {
			//		final String aVersionUniqueName = aVersionUniqueNames[j];
			//		doesMatchAUniqueName =
			//			fileName.toLowerCase().indexOf(
			//				aVersionUniqueName.toLowerCase()) > -1;
			//	}
			if (fileName
				.toLowerCase()
				.indexOf(aVersionUniqueName.toLowerCase()) > -1) {

				System.out.print("Merging: ");
				System.out.println(aPathToSomeOccurrences + fileName);

				this.populateVersionClassRoles(aPathToSomeOccurrences
						+ fileName, isPositive);
			}
			//	else {
			//		System.err.print("Rejecting file: ");
			//		System.err.print(fileName);
			//		System.err.print(" because it does not match ");
			//		System.err.println(aVersionUniqueName);
			//	}
		}
	}
	private void populateVersionClassRoles(
		final String anOccurrencesFile,
		final boolean isPositive) {

		try {
			System.out.println("\tLoading occurrences...");
			final Properties properties = new Properties();
			properties.load(new ReaderInputStream(new FileReader(
				anOccurrencesFile)));

			System.out.println("\tBuilding occurrences...");
			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();
			final Occurrence[] occurrences =
				solutionBuilder.getCanonicalOccurrences(properties);

			System.out.print("\tMerging ");
			System.out.print(occurrences.length);
			System.out.println("...");

			for (int i = 0; i < occurrences.length; i++) {
				final Occurrence occurrence = occurrences[i];
				final Iterator iteratorOnComponents =
					occurrence.getComponents().iterator();
				while (iteratorOnComponents.hasNext()) {
					final OccurrenceComponent component =
						(OccurrenceComponent) iteratorOnComponents.next();
					if (isPositive) {
						this.versionClassRoles.addDPRole(
							component.getDisplayValue(),
							occurrence.getDisplayName(),
							component.getDisplayName());
					}
					else {
						this.versionClassRoles.addAPRole(
							component.getDisplayValue(),
							occurrence.getDisplayName(),
							component.getDisplayName());
					}
				}
			}
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.versionClassRoles.toString());
		return buffer.toString();
	}
}
