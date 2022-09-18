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
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import util.io.ProxyDisk;

public class parseRresultsFile {

	/**
	 * @param args
	 */
	private static Pattern pattern;
	private static Matcher matcher;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//		extractWilcoxValues(
		//			"rsc/composition/populationTestResults.txt",
		//			"rsc/composition/wilcoxonTable.csv");
		//		
		extractFromDir(
			"D:/Documents/Workspace/R Workspace",
			"rsc/composition/wilcoxonWithNonDEMIMATable.csv");

	}

	/*
	 * These methods are to be improved to automatically generate the p-values Tables......
	 * 
	 */

	public static void extractFromDir(String path, String outFileName) {

		final File pathFile = new File(path);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = path + "/" + subPaths[i];
			final File file = new File(fileName);

			if (fileName.endsWith(".Rout") == true) {
				try {
					final Writer out =
						ProxyDisk.getInstance().fileTempOutput(
							outFileName,
							true);
					out.write(file.getName());
					out.flush();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				extractWilcoxValues(fileName, outFileName);
			}
		}

	}

	public static void extractWilcoxValues(
		String imputfileName,
		String outfileName) {
		final String t = "Wilcoxon";
		String currentMetricName = "";
		String currentStatResults = "";
		try {
			final LineNumberReader inputFileReader =
				new LineNumberReader(new FileReader(imputfileName));

			String line = null;
			// create outputFile
			final Writer out =
				ProxyDisk.getInstance().fileTempOutput(outfileName, true);
			//out.write("Metric Names, p-values for Wilcoxon \n");
			//out.flush();
			//Read File Line By Line	 	

			while ((line = inputFileReader.readLine()) != null) {
				// start analysing the content of the line

				StringTokenizer st = new StringTokenizer(line);
				while (st.hasMoreTokens()) {

					pattern = Pattern.compile(t);
					matcher = pattern.matcher(st.nextToken());
					if (matcher.find()) {
						// we got a line containing wilcoxon and now we take the line with metrics name
						try {
							currentMetricName = inputFileReader.readLine();
							while (currentMetricName.trim().length() == 0) {
								currentMetricName = inputFileReader.readLine();

							}
							String substring1 = "data:";
							String substring2 = "by";
							int begin = currentMetricName.indexOf(substring1);
							int end = currentMetricName.indexOf(substring2);
							String aMetricName =
								currentMetricName.substring(
									begin + substring1.length(),
									end);

							currentStatResults = inputFileReader.readLine();

							while (currentStatResults.trim().length() == 0) {
								currentStatResults = inputFileReader.readLine();

							}

							String substring3 = "p-value =";
							int begin3 = currentStatResults.indexOf(substring3);
							String pValue =
								currentStatResults.substring(begin3
										+ substring3.length());

							// System.out.println ("Metric: "+aMetricName);
							out.write(aMetricName + ",");
							out.flush();
							// System.out.println ("p-value: "+pValue);
							out.write(pValue + "\n");
							out.flush();

						}
						catch (RuntimeException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

				//System.out.println (strLine);
			}
			//Close the input stream
			out.close();
			inputFileReader.close();
		}
		catch (Exception e) {//Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

}
