/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc   and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc   and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.occurrence.helper.bug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ReaderInputStream;

/**
 * @author Yann-Gaël Guéhéneuc  
 * @since  2007/08/13
 */
public class RoleChangesCounter {
	public static void main(final String[] args) {
		final RoleChangesCounter rcc = new RoleChangesCounter();

		final String[] data =
			new String[] { "../Ptidej Solver Data/Azureus Versions/",
					"rsc/070814 - Azureus Bug Reports.xml" };
		//	final String[] data =
		//		new String[] {
		//			"../Ptidej Solver Data/Xalan Versions/",
		//			"rsc/070815 - Xalan Bug Reports.xml" };

		final Map classRolesMap = new HashMap();
		final File directory = new File(data[0]);
		final String[] list = directory.list();
		for (int i = 0; i < list.length; i++) {
			final String file = list[i];
			if (file.endsWith(".ini")) {
				final Map map =
					rcc.buildClassRolesMap(data[0] + file, file.substring(file
						.lastIndexOf(' ') + 1, file.lastIndexOf('.')));
				rcc.mergeClassRolesMaps(classRolesMap, map);
			}
		}
		System.out.println("Number of classes: " + classRolesMap.size());
		System.out.println();

		final Map classAppearances =
			rcc.countClassAppearances(classRolesMap.keySet(), data[1]);
		System.out.println("Number of classes: " + classAppearances.size());

		final Map roleAppearances =
			rcc.countRoleAppearances(classRolesMap, classAppearances);
		System.out.println("\nNumber of roles: " + roleAppearances.size());
		final Iterator iterator = roleAppearances.keySet().iterator();
		while (iterator.hasNext()) {
			final String role = (String) iterator.next();
			System.out.println(role + ", " + roleAppearances.get(role));
		}
	}
	public Map buildClassRolesMap(
		final String aSolutionFile,
		final String aDesignMotifName) {

		try {
			final Properties properties = new Properties();
			properties
				.load(new ReaderInputStream(new FileReader(aSolutionFile)));
			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();
			final Occurrence[] occurrences =
				solutionBuilder.getCanonicalOccurrences(properties);

			final Map classRolesMap = new HashMap();
			for (int i = 0; i < occurrences.length; i++) {
				final Occurrence occurrence = occurrences[i];
				final Iterator iteratorOnComponents =
					occurrence.getComponents().iterator();
				while (iteratorOnComponents.hasNext()) {
					final OccurrenceComponent component =
						(OccurrenceComponent) iteratorOnComponents.next();
					final char[] componentName = component.getName();
					final char[] componentValue = component.getValue();

					// Yann 2007/08/15: Components!
					// TODO: I should to have to test on the name of the component!
					if (!Arrays.equals(componentName, Occurrence.NAME)
							&& !Arrays
								.equals(componentName, Occurrence.COMMAND)
							&& !Arrays.equals(componentName, Occurrence.SIGN)) {

						final Set setOfRoles;
						if (!classRolesMap.containsKey(componentValue)) {
							setOfRoles = new HashSet();
							classRolesMap.put(componentValue, setOfRoles);
						}
						else {
							setOfRoles =
								(Set) classRolesMap.get(componentValue);
						}
						final int index =
							ArrayUtils.indexOf(componentName, '-');
						if (index > -1) {
							setOfRoles.add(aDesignMotifName
									+ '.'
									+ String.valueOf(ArrayUtils.subarray(
										componentName,
										0,
										index)));
						}
						else {
							setOfRoles.add(aDesignMotifName + '.'
									+ String.valueOf(componentName));
						}
					}
				}
			}
			return classRolesMap;
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public Map countClassAppearances(
		final Set aSetOfClasses,
		final String aBugReportFile) {
		final Map map = new HashMap();
		try {
			final LineNumberReader fileReader =
				new LineNumberReader(new FileReader(aBugReportFile));
			final StringBuffer fileBuffer = new StringBuffer();
			String line;
			while ((line = fileReader.readLine()) != null) {
				fileBuffer.append(line);
			}
			fileReader.close();
			final String fileContent = fileBuffer.toString();

			final Iterator classes = aSetOfClasses.iterator();
			int numberOfAppearingClasses = 0;
			while (classes.hasNext()) {
				final String className = (String) classes.next();
				final String classShortName =
					className.substring(className.lastIndexOf('.'), className
						.length());
				final int delta = classShortName.length();

				int count = 0;
				int index = -delta;
				while ((index =
					fileContent.indexOf(classShortName, index + delta)) != -1) {

					count++;
				}
				map.put(className, Integer.valueOf(count));
				if (count > 0) {
					numberOfAppearingClasses++;
					System.out.println(className + ", " + count);
				}
			}
			System.out.println("\nNumber of appearing classes:"
					+ numberOfAppearingClasses);
			System.out.println("Percentage of appearing classes: "
					+ numberOfAppearingClasses * 100.0 / aSetOfClasses.size());
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map countRoleAppearances(
		final Map aClassRolesMap,
		final Map aClassAppearancesMap) {

		final Map roleAppearancesMap = new HashMap();
		final Iterator iteratorOnClasses = aClassRolesMap.keySet().iterator();
		while (iteratorOnClasses.hasNext()) {
			final String clazz = (String) iteratorOnClasses.next();
			final Set roles = (Set) aClassRolesMap.get(clazz);
			final Iterator iteratorOnRoles = roles.iterator();
			while (iteratorOnRoles.hasNext()) {
				final String role = (String) iteratorOnRoles.next();
				if (!roleAppearancesMap.containsKey(role)) {
					roleAppearancesMap.put(role, aClassAppearancesMap
						.get(clazz));
				}
				else {
					final int previousCount =
						((Integer) roleAppearancesMap.get(role)).intValue();
					final int newCount =
						((Integer) aClassAppearancesMap.get(clazz)).intValue();
					roleAppearancesMap.put(role, Integer.valueOf(previousCount
							+ newCount));
				}
			}
		}
		return roleAppearancesMap;
	}
	public void mergeClassRolesMaps(
		final Map aResultMap,
		final Map aClassRolesMap2) {

		final Iterator iterator = aClassRolesMap2.keySet().iterator();
		while (iterator.hasNext()) {
			final String clazz = (String) iterator.next();
			final Set setOfRoles;
			if (!aResultMap.containsKey(clazz)) {
				setOfRoles = new HashSet();
				aResultMap.put(clazz, setOfRoles);
			}
			else {
				setOfRoles = (Set) aResultMap.get(clazz);
			}
			setOfRoles.addAll((Set) aClassRolesMap2.get(clazz));
		}
	}
}
