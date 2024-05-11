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
package ptidej.occurrences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ProxyDisk;

public class CombinatorPerProgramPerVersion {
	private static final String DATA_NAME = "Data  Name";
	private static final String CLASS_NAMES = "Class Names";
	private static final String DOES_PLAY_ROLE_DESIGN_PATTERN =
		"Play Role Design Pattern";
	private static final String NUMBER_OF_ROLES_DESIGN_PATTERN =
		"Number Roles Design Patterns";
	private static final String DOES_PLAY_ROLE_ANTI_PATTERN =
		"Play Role Antipattern";
	private static final String NUMBER_OF_ROLES_ANTI_PATTERN =
		"Number Roles Antipatterns";
	private static final String DeMIMA_HEADER = "ConstraintResults";
	private static final String DECOR_HEADER = "DetectionResults";
	private static final String METRIC_EXTENSION = "metrics";
	private static final String OCCURRENCE_EXTENSION = "ini";

	private static final String[] PROGRAM_NAMES = new String[] { "ArgoUML",
			"Azureus", "Mylyn", "Pooka", "Rhino", "SIP" };
	private static final String[][] PROGRAM_VERSIONS = new String[][] {
			new String[] { "0.10", "0.10.1", "0.12", "0.14", "0.15.1",
					"0.15.2", "0.15.3", "0.15.4", "0.15.5", "0.15.6", "0.16",
					"0.16.1", "0.17.1", "0.17.2", "0.17.3", "0.17.4", "0.17.5",
					"0.18", "0.18.1", "0.19.1", "0.19.2", "0.19.3", "0.19.4",
					"0.19.5", "0.19.6", "0.19.7", "0.19.8", "0.20", "0.21.1",
					"0.21.2", "0.21.3", "0.22", "0.23.1", "0.23.2", "0.23.4",
					"0.23.5", "0.24", "0.25.1", "0.25.2", "0.25.4-2", "0.25.5",
					"0.26", "0.26.1.BETA_1", "0.26.2", "0.27.1", "0.27.2",
					"0.27.3", "0.28", "0.28.1", "0.29.1" },
			new String[] { "v3.1.0.0", "v3.1.1.0", "v4.0.0.0", "v4.0.0.2",
					"v4.0.04", "v4.1.0.0", "v4.1.0.2", "v4.1.0.4", "v4.2.0.0",
					"v4.2.0.2", "v4.2.0.4", "v4.2.0.8", "v4.3.0.0", "v4.3.0.2",
					"v4.3.0.4", "v4.3.0.6", "v4.3.1.0", "v4.3.1.2", "v4.3.1.4",
					"v4.4.0.0", "v4.4.0.2", "v4.4.0.4", "v4.4.0.6" },
			new String[] { "1.0.1", "2.0.0", "2.0M1", "2.0M2", "2.0M3", "2.1",
					"2.2.0", "2.3.0", "2.3.1", "2.3.2", "3.0.0", "3.0.1",
					"3.0.2", "3.0.3", "3.0.4", "3.0.5", "3.1.0", "3.1.1",
					"3.2.0" },
			new String[] { "v2.0" },
			new String[] { "v1.4R3", "v1.5R1", "v1.5R2", "v1.5R3", "v1.5R4",
					"v1.5R5", "v1.5R41", "v1.6R1", "v1.6R2", "v1.6R3",
					"v1.6R4", "v1.6R5", "v1.6R6", "v1.6R7" },
			new String[] { "v1.0" } };

	public static void main(final String[] args) {
		final CombinatorPerProgramPerVersion combinatorPerProgramPerVersion =
			new CombinatorPerProgramPerVersion();

		for (int i = 0; i < PROGRAM_NAMES.length; i++) {
			final String programName = PROGRAM_NAMES[i];

			for (int j = 0; j < PROGRAM_VERSIONS[i].length; j++) {
				final String versionNumber = PROGRAM_VERSIONS[i][j];

				System.out.print("Processing ");
				System.out.print(programName);
				System.out.print(" ");
				System.out.print(versionNumber);
				System.out.println("...");

				final String[] arrayOfFilePaths =
					combinatorPerProgramPerVersion
						.findAllFiles(
							"D:/Documents/Papers/2011/WCRE (Yann and Giulio)/Data/",
							programName);

				final TableModel dataTable =
					combinatorPerProgramPerVersion.createDataTable();

				combinatorPerProgramPerVersion.populateTable(
					arrayOfFilePaths,
					versionNumber,
					dataTable);

				combinatorPerProgramPerVersion.writeTableToCSVFile(
					dataTable,
					"D:/Documents/Papers/2011/WCRE (Yann and Giulio)/Data/All Data for "
							+ programName + ' ' + versionNumber + ".csv");
			}
		}
	}
	private TableModel createDataTable() {
		final TableModel dataTable = new TableModel();

		// By default, the table must include one cell (one row, one column)...
		dataTable.addColumn(CombinatorPerProgramPerVersion.CLASS_NAMES);
		dataTable
			.addRow(new String[] { CombinatorPerProgramPerVersion.DATA_NAME });

		// Also, by default, I add columns summarising the numbers of
		// roles played by a class in occurrences of design motifs and
		// of antipatterns as well as two corresponding Boolean values
		dataTable
			.addColumn(CombinatorPerProgramPerVersion.DOES_PLAY_ROLE_ANTI_PATTERN);
		dataTable.setValueAt(
			CombinatorPerProgramPerVersion.DOES_PLAY_ROLE_ANTI_PATTERN,
			0,
			1);
		dataTable
			.addColumn(CombinatorPerProgramPerVersion.NUMBER_OF_ROLES_ANTI_PATTERN);
		dataTable.setValueAt(
			CombinatorPerProgramPerVersion.NUMBER_OF_ROLES_ANTI_PATTERN,
			0,
			2);
		dataTable
			.addColumn(CombinatorPerProgramPerVersion.DOES_PLAY_ROLE_DESIGN_PATTERN);
		dataTable.setValueAt(
			CombinatorPerProgramPerVersion.DOES_PLAY_ROLE_DESIGN_PATTERN,
			0,
			3);
		dataTable
			.addColumn(CombinatorPerProgramPerVersion.NUMBER_OF_ROLES_DESIGN_PATTERN);
		dataTable.setValueAt(
			CombinatorPerProgramPerVersion.NUMBER_OF_ROLES_DESIGN_PATTERN,
			0,
			4);

		return dataTable;
	}
	private void writeTableToCSVFile(
		final TableModel aDataTable,
		final String anOutputFile) {

		try {
			final CSVWriter csvWriter =
				new CSVWriter(ProxyDisk.getInstance().fileAbsoluteOutput(
					anOutputFile), ',');

			final List<List<String>> dataVector = aDataTable.getDataVector();
			final Iterator<List<String>> interatorOnRows = dataVector.iterator();
			while (interatorOnRows.hasNext()) {
				final List<String> rowVector = interatorOnRows.next();
				final String[] rowArray = new String[rowVector.size()];
				rowVector.toArray(rowArray);
				csvWriter.writeNext(rowArray);
			}
			csvWriter.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	private void populateTable(
		final String[] anArrayOfFilePaths,
		final String aVersionNumber,
		final TableModel aDataTable) {

		for (int i = 0; i < anArrayOfFilePaths.length; i++) {
			final String filePath = anArrayOfFilePaths[i];

			//	System.out.print("Analysing ");
			//	System.out.print(filePath);
			//	System.out.println("...");

			if (filePath.indexOf(aVersionNumber) > -1) {
				if (filePath
					.indexOf(CombinatorPerProgramPerVersion.DeMIMA_HEADER) > -1
						&& filePath
							.endsWith(CombinatorPerProgramPerVersion.OCCURRENCE_EXTENSION)) {

					this
						.addOccurrencesData(
							filePath,
							aDataTable,
							CombinatorPerProgramPerVersion.NUMBER_OF_ROLES_DESIGN_PATTERN,
							CombinatorPerProgramPerVersion.DOES_PLAY_ROLE_DESIGN_PATTERN);
				}
				else if (filePath
					.indexOf(CombinatorPerProgramPerVersion.DECOR_HEADER) > -1
						&& filePath
							.endsWith(CombinatorPerProgramPerVersion.OCCURRENCE_EXTENSION)) {

					this
						.addOccurrencesData(
							filePath,
							aDataTable,
							CombinatorPerProgramPerVersion.NUMBER_OF_ROLES_ANTI_PATTERN,
							CombinatorPerProgramPerVersion.DOES_PLAY_ROLE_ANTI_PATTERN);

				}
				else if (filePath
					.endsWith(CombinatorPerProgramPerVersion.METRIC_EXTENSION)) {
					this.addMetricsData(filePath, aDataTable);
				}
				else {
					System.err.print("I don't know what to do with ");
					System.err.println(filePath);
				}
			}
		}
	}
	private void addMetricsData(
		final String aFilePath,
		final TableModel aDataTable) {

		try {
			final CSVReader csvReader =
				new CSVReader(new FileReader(aFilePath), ';');

			final String[] metricNames = csvReader.readNext();

			String[] currentMetricValues;
			while ((currentMetricValues = csvReader.readNext()) != null) {
				final int numberOfMetrics = currentMetricValues.length - 1;

				final String rowName = currentMetricValues[numberOfMetrics];

				for (int i = 0; i < numberOfMetrics; i++) {
					final String metricValue = currentMetricValues[i];
					this.addValueToTable(
						aDataTable,
						metricNames[i],
						rowName,
						metricValue);
				}
			}

			csvReader.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
	public static boolean isValidJavaIdentifier(final String anIdentifier) {
		if (anIdentifier.length() == 0
				|| !Character.isJavaIdentifierStart(anIdentifier.charAt(0))) {
			return false;
		}
		for (int i = 1; i < anIdentifier.length(); i++) {
			final char c = anIdentifier.charAt(i);
			if (!Character.isJavaIdentifierPart(c) && c != '.') {
				return false;
			}
		}
		return true;
	}
	private void addOccurrencesData(
		final String aFilePath,
		final TableModel aDataTable,
		final String aColumnNameToCountRoles,
		final String aColumnNameToBooleanRole) {

		final Properties rawOccurrences = new Properties();
		try {
			rawOccurrences.load(new FileInputStream(aFilePath));
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		final OccurrenceBuilder builder = OccurrenceBuilder.getInstance();
		final Occurrence[] occurrences =
			builder.getCanonicalOccurrences(rawOccurrences);

		for (int j = 0; j < occurrences.length; j++) {
			final Occurrence occurrence = occurrences[j];

			final List<OccurrenceComponent> listOfComponents = occurrence.getComponents();
			for (int i = 0; i < listOfComponents.size(); i++) {
				final OccurrenceComponent component =
					(OccurrenceComponent) listOfComponents.get(i);

				// final String columnName = component.getDisplayName();
				final String columnName = occurrence.getDisplayName();
				final String rowName = component.getDisplayValue();

				// I assume that if the value includes a "." but is not a DECOR rule, then it is a class name...
				if (CombinatorPerProgramPerVersion
					.isValidJavaIdentifier(rowName)
						&& rowName.indexOf('.') > -1) {

					this.addValueToTable(
						aDataTable,
						columnName,
						rowName,
						"TRUE");

					final String value =
						(String) aDataTable.getValueAt(this
							.findRowNumberFromName(aDataTable, rowName), this
							.findColumnNumberFromName(
								aDataTable,
								aColumnNameToCountRoles));
					final int numberOfPlayedRoles;
					if (value == null) {
						numberOfPlayedRoles = 0;
					}
					else {
						numberOfPlayedRoles = Integer.valueOf(value).intValue();
					}
					this.addValueToTable(
						aDataTable,
						aColumnNameToCountRoles,
						rowName,
						Integer.toString(numberOfPlayedRoles + 1));
					this.addValueToTable(
						aDataTable,
						aColumnNameToBooleanRole,
						rowName,
						"TRUE");
				}
			}
		}
	}
	private void addValueToTable(
		final TableModel aDataTable,
		final String aColumnName,
		final String aRowName,
		final String aValue) {

		// This is the only method that writes into the table.

		int columnNumber =
			this.findColumnNumberFromName(aDataTable, aColumnName);
		if (columnNumber == -1) {
			aDataTable.addColumn(aColumnName);
			columnNumber = aDataTable.getColumnCount() - 1;
			aDataTable.setValueAt(aColumnName, 0, columnNumber);
		}

		int rowNumber = this.findRowNumberFromName(aDataTable, aRowName);
		if (rowNumber == -1) {
			aDataTable.addRow(new String[0]);
			rowNumber = aDataTable.getRowCount() - 1;
			aDataTable.setValueAt(aRowName, rowNumber, 0);
		}

		aDataTable.setValueAt(aValue, rowNumber, columnNumber);
	}
	private String[] findAllFiles(
		final String aRootPath,
		final String aProgramName) {
		final List<String> listOfFilePaths = new ArrayList<>();

		this.findAllFiles(
			aRootPath,
			aProgramName.toLowerCase(),
			listOfFilePaths);

		final String[] arrayOfFilePaths = new String[listOfFilePaths.size()];
		listOfFilePaths.toArray(arrayOfFilePaths);
		return arrayOfFilePaths;
	}
	private void findAllFiles(
		final String aRootPath,
		final String aLoweredCaseProgramName,
		final List<String> aListOfFilePaths) {

		final String[] fileNames = new File(aRootPath).list();
		for (int i = 0; i < fileNames.length; i++) {
			final String filePath = aRootPath + fileNames[i];
			final File file = new File(filePath);
			if (filePath.toLowerCase().indexOf(aLoweredCaseProgramName) > -1) {
				if (file.isDirectory()) {
					this.findAllFiles(
						filePath + '/',
						aLoweredCaseProgramName,
						aListOfFilePaths);
				}
				else {
					aListOfFilePaths.add(filePath);
				}
			}
		}
	}
	private int findColumnNumberFromName(
		final TableModel aDataTable,
		final String aColumnName) {

		return aDataTable.findColumn(aColumnName);
	}
	private int findRowNumberFromName(
		final TableModel aDataTable,
		final String aRowName) {

		final int rowCount = aDataTable.getRowCount();
		for (int i = 0; i < rowCount; i++) {
			final String rowValue = (String) aDataTable.getValueAt(i, 0);
			if (rowValue.equals(aRowName)) {
				return i;
			}
		}

		return -1;
	}
}
