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
package ptidej.solver.helper;

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
import org.apache.commons.lang.ArrayUtils;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ProxyDisk;

/**
 *  Take a directory full of ini files with
 *  occurrences of patterns/smells and
 *  extract a list of the (unique) entities
 *  playing some roles in these occurrences.
 *  
 *  @author Yann
 *  @since  2009/03/24
 */
public class EntitiesPlayingRolesExtractor {
	//	private static final String SourcePath =
	//		"D:/Documents/Work/2009/ICSM (Pattern Composition)/data raw/";
	//	private static final String DestinationFile =
	//		"D:/Documents/Work/2009/ICSM (Pattern Composition)/data validated/ListOfEntitiesPlayingSomeRoles.csv";
	//	private static final String SourcePath =
	//		"../Ptidej Solver Data/JSME'08 (bis)/All Occurrences of Command/";
	//	private static final String DestinationFile =
	//		"../Ptidej Solver Data/JSME'08 (bis)/All Occurrences of Command/ListOfEntitiesPlayingSomeRoles.csv";
	//	private static final String SourcePath =
	//		"../Ptidej Solver Data/JSME'08 (bis)/First Few Occurrences of Command/";
	//	private static final String DestinationFile =
	//		"../Ptidej Solver Data/JSME'08 (bis)/First Few Occurrences of Command/ListOfEntitiesPlayingSomeRoles.csv";
	//	private static final String SourcePath =
	//		"../Ptidej Solver Data/JSME'08 (bis)/First Occurrence of Command/";
	//	private static final String DestinationFile =
	//		"../Ptidej Solver Data/JSME'08 (bis)/First Occurrence of Command/ListOfEntitiesPlayingSomeRoles.csv";

	private static final String SourcePath =
		"../Ptidej Solver Data/JSME'08 (bis)/First Few Occurrences of Command/";
	private static final String DestinationFile =
		"../Ptidej Solver Data/JSME'08 (bis)/First Few Occurrences of Command/ListOfEntitiesPlayingSomeRoles.csv";

	public static void main(final String[] args) {
		final EntitiesPlayingRolesExtractor extractor =
			new EntitiesPlayingRolesExtractor();
		extractor.extract(
			EntitiesPlayingRolesExtractor.SourcePath,
			EntitiesPlayingRolesExtractor.DestinationFile);
	}
	public void extract(final String sourcePath, final String destinationFile) {
		final File sourcePathFile = new File(sourcePath);
		final OccurrenceBuilder builder = OccurrenceBuilder.getInstance();
		final String[] listOfIniFiles = sourcePathFile.list();
		final Set setOfEntities = new TreeSet();
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
					final Iterator components =
						occurrence.getComponents().iterator();
					while (components.hasNext()) {
						final OccurrenceComponent component =
							(OccurrenceComponent) components.next();
						final char[] value = component.getValue();
						// I only keep classes/interfaces, i.e., with a package name...
						if (ArrayUtils.indexOf(value, '.') > -1) {
							setOfEntities.add(String.valueOf(value));
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
			final Iterator iterator = setOfEntities.iterator();
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
