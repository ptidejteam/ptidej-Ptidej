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
package squad.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang3.ArrayUtils;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ProxyDisk;

public class ParseIniFiles {

	/**
	 * @param args
	 */

	private static final String SourcePath =
		"D:/Software/Workspace3/SAD Tests/rsc/results/OOPSLA'07/";
	private static final String DestinationFile =
		"D:/Software/Workspace3/SQUAD/rsc/JSS/ListOfEntitiesPlayingSomeCodeSmell.csv";

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		final ParseIniFiles extractor = new ParseIniFiles();
		extractor.extract(
			ParseIniFiles.SourcePath,
			ParseIniFiles.DestinationFile);

	}

	public void extract(final String sourcePath, final String destinationFile) {
		final File sourcePathFile = new File(sourcePath);
		final OccurrenceBuilder builder = OccurrenceBuilder.getInstance();
		final String[] listOfIniFiles = sourcePathFile.list();
		final Set<String> setOfEntities = new TreeSet<String>();
		for (int i = 0; i < listOfIniFiles.length; i++) {
			final String file =
				sourcePathFile.getAbsolutePath() + File.separatorChar
						+ listOfIniFiles[i];
			System.out.print("Analysing ");
			System.out.println(listOfIniFiles[i]);
			try {
				final InputStream inputStream = new FileInputStream(file);
				final Properties properties = new Properties();
				properties.load(inputStream);
				inputStream.close();

				final Occurrence[] occurrences =
					builder.getAllOccurrences(properties);

				for (int j = 0; j < occurrences.length; j++) {
					final Occurrence occurrence = occurrences[j];

					//System.out.println("Occurence: " + occurrence.getName());
					final Iterator<?> components =
						occurrence.getComponents().iterator();
					while (components.hasNext()) {
						final OccurrenceComponent component =
							(OccurrenceComponent) components.next();

						//System.out.println("Component: " + component.getName());

						final char[] value = (component.getValue());
						String compName = component.getName().toString();
						if ((component.getName()).toString().indexOf('-') > -1) {

							compName =
								component
									.getName()
									.toString()
									.substring(
										0,
										component
											.getName()
											.toString()
											.indexOf('-'));
						}
						// I only keep classes/interfaces, i.e., with a package name...
						if (ArrayUtils.indexOf(value, '.') > -1) {
							setOfEntities.add(compName + ","
									+ String.valueOf(value));
						}

					}
				}
			}
			catch (final FileNotFoundException e) {
				System.err.println("File: " + file);
				e.printStackTrace();
			}
			catch (final IOException e) {
				System.err.println("File: " + file);
				e.printStackTrace();
			}
		}

		System.out.println("Writing the list...");
		try {
			final Writer writer =
				ProxyDisk.getInstance().fileTempOutput(destinationFile);
			writer.write("CodesmellName, Classname");
			writer.write('\n');
			final Iterator<?> iterator = setOfEntities.iterator();
			while (iterator.hasNext()) {
				final String entityName = (String) iterator.next();
				writer.write(entityName);
				writer.write('\n');
			}
			writer.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		System.out.println("Done.");

	}

}
