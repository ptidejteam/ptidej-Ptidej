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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IEntity;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.io.ProxyDisk;

public class PrintSystemClasses {

	/**
	 * @param args
	 */

	private static Pattern pattern;
	private static Matcher matcher;

	public static void main(String[] args) throws FileNotFoundException,
			CorruptIndexException, IOException, ParseException {
		// TO USE THIS CLASS, ONCE THE CSV ARE GENERATED WITH ResultPrettyPrinter
		// USE indesFiles() TO BUILD DATABASE FROM THESE FILE, DO IT JUST ONE TIME, AND THEN 
		//YOU CAN USE csvoutput() THE GENERATE csv FOR EACH RELEASE OF THE SYSTEM

		//		String dirName = "rsc/eclipse-SDK-" + args[0] + "-win32/plugins";
		//
		//		final String t =
		//			"org.eclipse.equinox(.[a-z]*)+|org.eclipse.core(.[a-z]*)+|org.eclipse.platform(.[a-z]*)+";
		//		List files = new ArrayList();

		//parse files from ArgoUML versions

		//		String path = "rsc/ArgoUML - DECOR Results/DataBases1";
		//		final Directory index =
		//			FSDirectory.getDirectory(path + "/index" + args[0] + ".dat");
		//		String fileName =
		//			"rsc/ArgoUML - DECOR Results/ArgoUML-" + args[0]
		//					+ "/Results of ArgoUML.v" + args[0] + ".csv";
		//		String destFile =
		//			"rsc/ArgoUML - DECOR Results/ArgoUML-" + args[0]
		//					+ "/TotalResults of ArgoUML.v" + args[0] + ".csv";
		//
		//		String classNamesFile =
		//			"rsc/ArgoUML - DECOR Results/ArgoUML-" + args[0]
		//					+ "/ClassNames.csv";
		//
		//		//indexFiles(path);
		//		csvoutput(index, args[0], fileName, destFile, classNamesFile, path);

		String path = "rsc/Yann-DECOR/DataBases";
		final Directory index =
			FSDirectory.getDirectory(path + "/index" + args[0] + ".dat");
		String fileName =
			"rsc/Yann-DECOR/DataBases/Results of Azureus.v" + args[0] + ".csv";
		String destFile =
			"rsc/Yann-DECOR/Results/TotalResults of Azureus.v" + args[0]
					+ ".csv";

		String classNamesFile = "rsc/Yann-DECOR/Classes/ClassNamesAzureus.csv";

		//indexFiles(path);
		csvoutput(index, args[0], fileName, destFile, classNamesFile, path);

	}

	/**
	 * This method prints csv files for each version of a system while taking into account its history
	 */

	public static void csvoutput(
		Directory index,
		String version,
		String fileName,
		String destFile,
		String classNamesFile,
		String path) throws IOException, ParseException {
		LineNumberReader fr = null;
		try {

			Vector<String> locFields = new Vector<String>();

			fr = new LineNumberReader(new FileReader(fileName));
			StringTokenizer st1 = new StringTokenizer(fr.readLine(), ",");
			while (st1.hasMoreTokens())
				locFields.add(st1.nextToken());

			Object[] eltFields = getColumNames(path);
			final Writer out =
				ProxyDisk.getInstance().fileTempOutput(destFile, false);

			out.write("Class");
			out.flush();
			for (int j = 0; j < eltFields.length; ++j) {

				out.write("," + eltFields[j]);
				out.flush();
			}
			out.write("\n");
			out.flush();

			final Analyzer analyser = new StandardAnalyzer();

			List<String> totalCl = aListofClassesFromFile(classNamesFile);

			IndexSearcher s = new IndexSearcher(index);

			for (int i = 0; i < totalCl.size(); i++) {

				String st = (String) totalCl.get(i);
				out.write(st);
				out.flush();
				Query q = new QueryParser("Class", analyser).parse(st.trim());

				Hits hits = s.search(q);
				int tab[];

				tab = new int[eltFields.length];
				if (hits.length() > 0) {
					for (int k = 0; k < hits.length(); ++k) {

						for (int j = 0; j < eltFields.length; ++j) {

							if (locFields.contains(eltFields[j])) {

								tab[j] =
									tab[j]
											+ Integer.parseInt((hits.doc(k)
												.get((String) eltFields[j]))
												.trim());
							}
							else {

								tab[j] = tab[j] + 0;
							}

						}

					}

					for (int j = 0; j < eltFields.length; ++j) {

						out.write("," + tab[j]);
						out.flush();

					}
					out.write("\n");
					out.flush();
				}
				else {

					for (int j = 0; j < eltFields.length; ++j) {

						out.write(",0");
						out.flush();

					}
					out.write("\n");
					out.flush();
				}

			}

			out.flush();
			out.close();
			s.close();
		}
		catch (Exception e) {
			System.out.println("Could not append file: " + fileName);
		}
		finally {
			fr.close();

		}

	}

	/** This method is for Eclipse, where I analyze the kernel (equinoxe) with plugins one at the time
	 */
	public static void printTotalClasses(
		String dirName,
		final String aName,
		List<String> files,
		String t,
		String file) {

		final CallSmellDetection detection = new CallSmellDetection();
		TreeSet<?> cls = new TreeSet<Object>();

		List<?> jarfiles = detection.extractJarsFromDir(dirName, files);

		List<String> equinoxfiles = new ArrayList<String>();
		List<String> elsefiles = new ArrayList<String>();

		for (int i = 0; i < jarfiles.size(); i++) {
			StringTokenizer st = new StringTokenizer((String) jarfiles.get(i));
			while (st.hasMoreTokens()) {

				pattern = Pattern.compile(t);
				matcher = pattern.matcher(st.nextToken());
				if (matcher.find()) {
					equinoxfiles.add((String) jarfiles.get(i));
				}
				else {

					elsefiles.add((String) jarfiles.get(i));

				}

			}
		}
		if (elsefiles.size() > 0) {
			for (int i = 0; i < elsefiles.size(); i++) {

				printCodelevelModel(
					CallSmellDetection.createArrayOfJars(equinoxfiles, elsefiles.get(i)),
					aName,
					file);
				//// printing metrics values
				//				printCodelevelModelMetrics(detection.createArrayOfJars(
				//					equinoxfiles,
				//					elsefiles.get(i)), aName, file,cls);	
				//				

			}
		}
		else {
			printCodelevelModel(
				CallSmellDetection.createArrayOfJars(equinoxfiles),
				aName,
				file);

			//			// printing metrics values
			//			printCodelevelModelMetrics(
			//				detection.createArrayOfJars(equinoxfiles),
			//				aName,
			//				file,cls);
		}

	}

	/**
	 * This method prints classes from a model in a file
	 * @param someJARFiles
	 * @param aName
	 * @param file
	 */

	public static void printCodelevelModel(
		final String[] someJARFiles,
		final String aName,
		String file) {
		try {
			ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));

			Iterator<?> iter = codeLevelModel.getIteratorOnTopLevelEntities();

			try {
				final Writer out =
					ProxyDisk.getInstance().fileTempOutput(file, true);

				while (iter.hasNext()) {

					IEntity anElement = (IEntity) iter.next();

					if (containsOrgEclipse(anElement.getDisplayName()) == true) {
						out.write(anElement.getDisplayName());
						out.flush();
						out.write('\n');
						out.flush();
					}
				}

				out.close();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}

		}
		catch (final CreationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * This method prints Eclipse metrics values 
	 * @param someJARFiles
	 * @param aName
	 * @param file
	 * @param cls
	 */

	public static void printCodelevelModelMetrics(
		final String[] someJARFiles,
		final String aName,
		String file,
		TreeSet<String> cls) {
		ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel(aName);
		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}

		if (codeLevelModel.getNumberOfTopLevelEntities() == 0) {
			System.err.println("--");
			System.err.print("Problem with: ");
			System.err.println(aName);
			System.err.println("--");
		}
		else {
			final MetricsRepository metricsRepository =
				MetricsRepository.getInstance();
			final StringBuffer output = new StringBuffer();

			{
				final IUnaryMetric[] metrics =
					metricsRepository.getUnaryMetrics();
				for (int i = 0; i < metrics.length; i++) {
					final IUnaryMetric unaryMetric = metrics[i];
					final String metricName = unaryMetric.getName();
					output.append(metricName);
					if (i < metrics.length - 1) {
						output.append(',');
					}
				}
				output.append(",Entities\n");
			}

			// Metric Values.
			final Iterator<?> entityIterator =
				codeLevelModel.getIteratorOnTopLevelEntities();

			while (entityIterator.hasNext()) {
				final IFirstClassEntity entity =
					(IFirstClassEntity) entityIterator.next();

				if (!(entity instanceof IGhost)
						&& (containsOrgEclipse(entity.getDisplayName()) == true)
						&& (!(cls.contains(entity.getDisplayName())))) {

					cls.add(entity.getDisplayName());

					final IUnaryMetric[] metrics =
						metricsRepository.getUnaryMetrics();
					for (int i = 0; i < metrics.length; i++) {
						final IUnaryMetric metric = metrics[i];
						final String metricName = metric.getName();
						System.out.print(metricName);
						System.out.print(", ");
						try {
							final double value =
								((IUnaryMetric) metricsRepository
									.getMetric(metricName)).compute(
									codeLevelModel,
									entity);
							output.append(value);
						}
						catch (final Exception e) {
							output.append("N/C");
						}

						if (i < metrics.length - 1) {
							output.append(',');
						}
					}
					System.out.println();

					output.append(',');
					output.append(entity.getName());
					if (entityIterator.hasNext()) {
						output.append('\n');
					}
				}
			}

			try {
				final Writer out =
					ProxyDisk.getInstance().fileTempOutput(file, true);

				out.write(output.toString());
				out.flush();
				out.close();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * I build a list of classes from a file
	 * @param inputFilePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws CorruptIndexException
	 * @throws IOException
	 */
	public static List<String> aListofClassesFromFile(String inputFilePath)
			throws FileNotFoundException, CorruptIndexException, IOException {
		List<String> lcls = new ArrayList<String>();
		try {
			final LineNumberReader inputFileReader =
				new LineNumberReader(new FileReader(inputFilePath));
			String line = null;

			while ((line = inputFileReader.readLine()) != null) {

				if (!lcls.contains(line)) {

					lcls.add(line);
				}

			}

			inputFileReader.close();
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		return lcls;

	}

	/**
	 * From a directory of csv, I build lucene databases 
	 * @param path
	 * @throws IOException
	 * @throws ParseException
	 */

	private static void indexFiles(String path) throws IOException,
			ParseException {

		final File pathFile = new File(path);

		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = path + "/" + subPaths[i];

			if (fileName.endsWith(".csv") == true) {

				ResultsFileIndexer csvindex =
					new ResultsFileIndexer(path + "/index"
							+ getFileVersion(fileName) + ".dat");
				csvindex.indexFileOrDirectory(fileName);

			}
		}

	}

	/*
	 * This method prints full results from the history of a system
	 * 
	 */
	//	public static void printFullResults(Directory index, String version)
	//			throws IOException, ParseException {
	//		String fileName =
	//			"rsc/ArgoUML - DECOR Results/ArgoUML-" + version
	//					+ "/Results of ArgoUML.v" + version + ".csv";
	//		String destFile =
	//			"rsc/ArgoUML - DECOR Results/ArgoUML-" + version
	//					+ "/TotalResults of ArgoUML.v" + version + ".csv";
	//		String path = "rsc/ArgoUML - DECOR Results/DataBases";
	//
	//		LineNumberReader fr = null;
	//		try {
	//
	//			// ===================================================
	//			// add contents of file
	//			// ===================================================
	//
	//			Vector locFields = new Vector();
	//
	//			fr = new LineNumberReader(new FileReader(fileName));
	//			StringTokenizer st1 = new StringTokenizer(fr.readLine(), ",");
	//			while (st1.hasMoreTokens())
	//				locFields.add(st1.nextToken());
	//
	//			Object[] eltFields = getColumNames(path);
	//			FileWriter out = new FileWriter(destFile, false);
	//
	//			//BufferedWriter out = new BufferedWriter(fw);
	//			out.write("Class");
	//			out.flush();
	//			for (int j = 0; j < eltFields.length; ++j) {
	//
	//				out.write("," + eltFields[j]);
	//				out.flush();
	//			}
	//			out.write("\n");
	//			out.flush();
	//
	//			final Analyzer analyser = new StandardAnalyzer();
	//
	//			List totalCl =
	//				aListofClassesFromFile("rsc/ArgoUML - DECOR Results/ArgoUML-"
	//						+ version + "/ClassNames.csv");
	//			//System.out.println(totalCl.size());
	//			IndexSearcher s = new IndexSearcher(index);
	//
	//			for (int i = 0; i < totalCl.size(); i++) {
	//				//Iterator it = totalCl.iterator();
	//
	//				//while(it.hasNext()){
	//
	//				String st = (String) totalCl.get(i);
	//				out.write(st);
	//				out.flush();
	//				Query q = new QueryParser("Class", analyser).parse(st.trim());
	//
	//				Hits hits = s.search(q);
	//				int tab[];
	//
	//				tab = new int[eltFields.length];
	//				if (hits.length() > 0) {
	//					for (int k = 0; k < hits.length(); ++k) {
	//
	//						for (int j = 0; j < eltFields.length; ++j) {
	//
	//							//out.write(","+(hits.doc(i).get((String) eltFields.elementAt(j))));
	//							if (locFields.contains(eltFields[j])) {
	//
	//								tab[j] =
	//									tab[j]
	//											+ Integer.parseInt((hits.doc(k)
	//												.get((String) eltFields[j]))
	//												.trim());
	//							}
	//							else {
	//
	//								tab[j] = tab[j] + 0;
	//							}
	//
	//						}
	//
	//					}
	//
	//					for (int j = 0; j < eltFields.length; ++j) {
	//
	//						out.write("," + tab[j]);
	//						out.flush();
	//
	//					}
	//					out.write("\n");
	//					out.flush();
	//				}
	//				else {
	//
	//					for (int j = 0; j < eltFields.length; ++j) {
	//
	//						out.write(",0");
	//						out.flush();
	//
	//					}
	//					out.write("\n");
	//					out.flush();
	//				}
	//
	//				//System.out.println((String)it.next());
	//
	//			}
	//
	//			out.flush();
	//			out.close();
	//			s.close();
	//		}
	//		catch (Exception e) {
	//			System.out.println("Could not append file: " + fileName);
	//		}
	//		finally {
	//			fr.close();
	//
	//		}
	//
	//	}

	/*
	 * Check if contains org.eclipse in the file name
	 * 
	 */

	public static boolean containsOrgEclipse(String className) {
		final String t = "org.eclipse(.[a-z]*)+";
		boolean bool = false;
		StringTokenizer st = new StringTokenizer(className);
		while (st.hasMoreTokens()) {

			pattern = Pattern.compile(t);
			matcher = pattern.matcher(st.nextToken());
			if (matcher.find()) {

				bool = true;
			}
		}
		return bool;

	}

	private static String getFileVersion(final String FileName) {
		final String substring1 = "v";
		final String substring2 = ".csv";
		final int begin = FileName.indexOf(substring1);
		final int end = FileName.indexOf(substring2);
		final String aFileVersion =
			FileName.substring(begin + substring1.length(), end);
		return aFileVersion;
	}

	private static Object[] getColumNames(String path) throws IOException,
			ParseException {
		LineNumberReader fr = null;

		TreeSet<String> col = new TreeSet<String>();

		try {
			final File pathFile = new File(path);

			final String[] subPaths = pathFile.list();
			for (int i = 0; i < subPaths.length; i++) {
				final String fileName = path + "/" + subPaths[i];

				if (fileName.endsWith(".csv") == true) {
					fr = new LineNumberReader(new FileReader(fileName));
					StringTokenizer st1 =
						new StringTokenizer(fr.readLine(), ",");
					while (st1.hasMoreTokens())
						col.add(st1.nextToken());

				}
			}

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			fr.close();
		}

		col.remove("Class");
		return col.toArray();
	}

}
