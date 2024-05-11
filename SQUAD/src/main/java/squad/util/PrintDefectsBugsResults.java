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
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ReaderInputStream;

public class PrintDefectsBugsResults {

	/**
	 * @author Foutse Khomh
	 * @since 2008/12/06
	 * 
	 */

	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException,
		IOException, ParseException {
		// TODO Auto-generated method stub
		final Directory index = FSDirectory
			.getDirectory("rsc/Bugs/raw_bugs/index.dat");
		final Directory indexbug = FSDirectory
			.getDirectory("rsc/Bugs/raw_bugs/indexbug.dat");

		/*System.out.print(getBugStatusofClass(
		"TestImpl.java",
		"D:/Documents/Workspace/ToDo/products.xml"));

		System.out
		.print(getFileShortname("com.ibm.icu.impl.CollectionUtilities.java"));
		  */

		PrintDefectsBugsResults evol = new PrintDefectsBugsResults(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Smells/");

		//evol.output4("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse3.2.1/Stats/","D:/Documents/Workspace/ToDo/products.xml");
		//	evol.output5("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"+args[0]+"/Stats/",index,indexbug);
		evol
			.output2("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/");
		
		//evol.printResults("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"+args[0]+"/Stats/",index);
		
//		PrintDefectsBugsResults evol = new PrintDefectsBugsResults(
//			"D:/Documents/Work/2009/Experiments/Defects in JUnit v3.7 & JHotDraw v5.1/");
//
//		
//		
//		evol
//		.output3("D:/Documents/Work/2009/Experiments/Defects in JUnit v3.7 & JHotDraw v5.1/");
//	
		
		/*
		 1.0: 2001-11-07
		 2.0: 2002-06-27
		 2.1.1: 2003-06-27
		 2.1.2: 2003-11-03		 
		 2.1.3: 2004-03-10		 
		 3.0: 2004-06-25		 
		 3.0.1: 2004-09-16		 
		 3.0.2: 2005-03-11		 
		 3.2: 2006-06-29		 
		 3.2.1: 2006-09-21		 
		 3.2.2: 2007-02-12		 		 
		 3.3: 2007-06-25		 
		 3.3.1: 2007-09-21		 
		 3.4: 2008-06-17
		
		*/
		
	/*	
		String datVersion="2008-06-17";
		String datPrecVersion="2007-09-21";
		
		evol.printResultsPast(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/",
			index,
			datVersion);

		evol.printResultsFuture(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/",
			index,
			datVersion);
		evol.printResults(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/",
			index,
			datPrecVersion,
			datVersion);

		// Results not containing classes with "internal"	in names

		evol.printResultsPastNointernal(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/",
			index,
			datVersion);

		evol.printResultsFutureNointernal(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/",
			index,
			datVersion);
		evol.printResultsNointernal(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
				+ args[0] + "/Stats/",
			index,
			datPrecVersion,
			datVersion);
*/
		/*	evol.printResults(
				"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/Eclipse"
					+ args[0] + "/Stats/",
				index,
				"2007-06-25",
				"2007-09-21");

		*/

		//String st = "date: 2008-12-15 12:30:18 +0000;  author: jeromel;  state: Exp;  lines: +13 -0;  commitid: 29f1f49464dd84567;";
		//System.out.println(containsOrgEclipse("org.eclipse.debug.internal.core.LaunchConfigurationWorkingCopy.java"));
		//System.out
		//	.println(notContainingInternal("org.eclipse.debug.core.model.DebugElement.java"));
	}

	private HashMap<String, ProgramDefectsRepository> allDefectsPerProgram;
	static org.jdom2.Document document;

	static Element racine;
	private static Pattern pattern;
	private static Matcher matcher;

	private String getDefectName(final String iniFileName) {
		final String substring1 = " for ";
		final String substring2 = ".ini";
		final int begin = iniFileName.indexOf(substring1);
		final int end = iniFileName.indexOf(substring2);
		// Ajout de l' indice	
		final String aSystemName = iniFileName.substring(begin
			+ substring1.length(), end);
		return aSystemName;
	}

	private String getProgramName(final String iniFileName) {
		final String substring1 = " in ";
		final String substring2 = " v";
		final int begin = iniFileName.indexOf(substring1);
		final int end = iniFileName.indexOf(substring2);
		final String aProgramName = iniFileName.substring(begin
			+ substring1.length(), end);
		return aProgramName;
	}

	private String getProgramVersion(final String iniFileName) {
		final String substring1 = " v";
		final String substring2 = " for ";
		final int begin = iniFileName.indexOf(substring1);
		final int end = iniFileName.indexOf(substring2);
		final String aProgramVersion = iniFileName.substring(begin
			+ substring1.length(), end);
		return aProgramVersion;
	}

	public PrintDefectsBugsResults(String IniFileDir)
		throws FileNotFoundException, IOException {

		this.allDefectsPerProgram = new HashMap<String, ProgramDefectsRepository>();
		File inputDir = new File(IniFileDir);
		String[] iniFiles = inputDir.list(new IniFileFilter());

		for (String iniFileName : iniFiles) {

			String progName = this.getProgramName(iniFileName);
			String antipatternName = getDefectName(iniFileName);
			String version = getProgramVersion(iniFileName);

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			if (currentRepo == null) {
				currentRepo = new ProgramDefectsRepository();
				this.allDefectsPerProgram.put(progName, currentRepo);
			}

			currentRepo.versions.add(version);

			final Properties properties = new Properties();
			properties.load(new ReaderInputStream(new FileReader(IniFileDir
				+ iniFileName)));

			final OccurrenceBuilder solutionBuilder = OccurrenceBuilder
				.getInstance();
			final Occurrence[] occurrences = solutionBuilder
				.getCanonicalOccurrences(properties);

			for (Occurrence occurrence : occurrences) {

				List<OccurrenceComponent> allComponents = (List<OccurrenceComponent>) occurrence
					.getComponents();

				for (OccurrenceComponent solutionComponent : allComponents) {

					String compName = solutionComponent.getName().toString();
					int i2 = compName.indexOf('-');
					int i1 = compName.indexOf('.');

					if (i1 == -1 && !compName.equals("Name")) {

						if (i2 > 0) {
							compName = compName.substring(0, i2);
						}

						compName = antipatternName + "_" + compName;
						currentRepo.defectsTypes.add(compName);
						String key = version + "--" + compName;
						TreeSet<String> listOfDefects = currentRepo.allDefectsPerVersionPerTypes
							.get(key);

						if (listOfDefects == null) {
							listOfDefects = new TreeSet<String>();
							currentRepo.allDefectsPerVersionPerTypes.put(
								key,
								listOfDefects);
						}

						listOfDefects.add(solutionComponent.getValue().toString());

						currentRepo.involvedClasses.add(solutionComponent
							.getValue().toString());
					}
				}

			}
		}
	}

	public static Date extractDate(String s) {

		final String substring1 = "date: ";
		final int begin = s.indexOf(substring1);

		final String bugdate = s.substring(
			begin + substring1.length(),
			begin + substring1.length() + 10);

		Date newDate = new Date();
		// String inputDate = "1994-02-14";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			newDate = formatter.parse(bugdate);
		}
		catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newDate;

	}

	/*	print from lucene index :
	 * One output file per versions of program
	 * #Program.version, nb_defects, involved_defects, Id of issues related
	 * class1, 2, LazyClass_FewMethods LazyClass_NotComplexClass, BugId
	 */

	public void printResultsPast(
		String outputDir,
		Directory index,
		String versionDate,
		Boolean before) throws IOException, ParseException {

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date verDate = new Date();
		verDate = extractDate("date: " + versionDate);

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues for" + progName + " v"
						+ version + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {

					System.out.print(involvedClass + ".java" + ",");

					int nbDefects = 0;
					String defectsNames = "";

					for (Iterator<String> iter = currentRepo.defectsTypes
						.iterator(); iter.hasNext();) {

						String defect = iter.next();
						String versionedDefect = version + "--" + defect;

						TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
							.get(versionedDefect);

						if (defectClasses.contains(involvedClass)) {
							nbDefects++;
							defectsNames += defect + " ";
						}

					}

					String modClassName = involvedClass.replace(".", "/");
					Query q = new QueryParser("filename", analyser)
						.parse(modClassName + ".java");

					File file = new File(modClassName + ".java");

					Query q1 = new QueryParser("filename", analyser)
						.parse("Attic/" + file.getName());

					IndexSearcher s = new IndexSearcher(index);

					Hits hits = s.search(q);
					Hits hits1 = s.search(q1);
					String ids = "BugIDs: ";
					String revisions = "Revisions: ";

					int count = 0;

					if (before == true) { // issues before given date	

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if (extractDate(st).before(verDate)) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}
						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if (extractDate(st1).before(verDate)) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
					}
					else { // issues after given date
						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if (extractDate(st).after(verDate)) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}
						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if (extractDate(st1).after(verDate)) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}

					}
					/*System.out.println(nbDefects + "," + defectsNames + ","
						+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
					*/
					System.out.println(nbDefects + "," + defectsNames + ","
						+ count + "," + ids + "," + revisions);

					s.close();

				}
			}
		}
		System.setOut(System.out);
	}

	/*
	 * print results of  period  date1-date2
	 * 
	 */

	public void printResults(
		String outputDir,
		Directory index,
		String Date1,
		String Date2) throws IOException, ParseException {
		float Clcount = 0.f;
		float IssueCount = 0.f;

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date date1 = new Date();
		Date date2 = new Date();
		date1 = extractDate("date: " + Date1);
		date2 = extractDate("date: " + Date2);

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues for" + progName + " v"
						+ version + "btw" + Date1 + "_" + Date2 + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {
					if (containsOrgEclipse(involvedClass)) {

						Clcount = Clcount + 1;
						System.out.print(involvedClass + ".java" + ",");

						int nbDefects = 0;
						String defectsNames = "";

						for (Iterator<String> iter = currentRepo.defectsTypes
							.iterator(); iter.hasNext();) {

							String defect = iter.next();
							String versionedDefect = version + "--" + defect;

							TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

							if (defectClasses.contains(involvedClass)) {
								nbDefects++;
								defectsNames += defect + " ";
							}

						}

						String modClassName = involvedClass
							.replace(".", "/");
						Query q = new QueryParser("filename", analyser)
							.parse(modClassName + ".java");

						File file = new File(modClassName + ".java");

						Query q1 = new QueryParser("filename", analyser)
							.parse("Attic/" + file.getName());

						IndexSearcher s = new IndexSearcher(index);

						Hits hits = s.search(q);
						Hits hits1 = s.search(q1);
						String ids = "BugIDs: ";
						String revisions = "Revisions: ";

						int count = 0;

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if ((extractDate(st).after(date1))
								&& (extractDate(st).before(date2))) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}
						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if ((extractDate(st1).after(date1))
								&& (extractDate(st1).before(date2))) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
						if (count > 0) {
							IssueCount = IssueCount + 1;

						}

						/*System.out.println(nbDefects + "," + defectsNames + ","
						+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
						*/
						System.out.println(nbDefects + "," + defectsNames
							+ "," + count + "," + ids + "," + revisions);

						s.close();

					}
				}

			}
			System.out.println("\n" + IssueCount / Clcount);

		}
		System.setOut(System.out);
	}

	/*
	 * One output file per programs
	 * ArgoUML, v1, v2, v3 Blob,
	 * nbDefect_in_v1, nbDefect_in_v2, nbDefect_in_v3 SpaghettiCode,
	 * nbDefect_in_v1, nbDefect_in_v2, nbDefect_in_v3
	 */
	public void output1(String outputDir) {

		Set<String> progNames = this.allDefectsPerProgram.keySet();

		for (String progName : progNames) {
			try {
				File output = new File(outputDir + "Results of " + progName
					+ ".csv");
				if (output.exists()) {
					output.delete();
				}
				output.createNewFile();

				System.setOut(new PrintStream(output));
			}
			catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			System.out.println(progName + ","
				+ currentRepo.versionsAsString());

			for (Iterator<String> iter1 = currentRepo.defectsTypes
				.iterator(); iter1.hasNext();) {

				String defect = iter1.next();
				System.out.print(defect + ",");

				for (Iterator<String> iter2 = currentRepo.versions
					.iterator(); iter2.hasNext();) {

					String key = iter2.next() + "--" + defect;
					TreeSet<String> listOfDefects = currentRepo.allDefectsPerVersionPerTypes
						.get(key);

					String nb = listOfDefects == null ? "0" : listOfDefects
						.size()
						+ "";
					if (iter2.hasNext()) {
						nb += ",";
					}
					else {
						nb += "\n";
					}

					System.out.print(nb);

				}
			}
		}
		System.setOut(System.out);
	}

	/*	
	 * One output file per versions of program
	 * ArgoUML_v1, Blob, SpagehttiCode 
	 * Class1, {0,1}, {0,1}
	 * Class2, {0,1}, {0,1}
	 */
	public void output2(String outputDir) {

		Set<String> progNames = this.allDefectsPerProgram.keySet();

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir + "Results of "
						+ progName + ".v" + version + ".csv");
					if (output.exists()) {
						System.err
							.println("Deleting ..." + output.getName());
						output.delete();
					}

					output.createNewFile();

					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out.println(progName + ".v" + version + ","
					+ currentRepo.defectsTypesAsString());

				for (String involvedClass : currentRepo.involvedClasses) {
					System.out.print(involvedClass + ",");

					for (Iterator<String> iter = currentRepo.defectsTypes
						.iterator(); iter.hasNext();) {

						String versionedDefect = version + "--"
							+ iter.next();
						TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
							.get(versionedDefect);

						int test = 0;

						if (defectClasses != null) {
							test = defectClasses.contains(involvedClass)
								? 1
								: 0;
						}

						System.out.print(test);
						if (iter.hasNext()) {
							System.out.print(",");
						}
					}
					System.out.println("");
				}
			}
		}
		System.setOut(System.out);
	}

	/*	
	 * One output file per versions of program
	 * #Program.version, nb_defects, involved_defects
	 * class1, 2, LazyClass_FewMethods LazyClass_NotComplexClass
	 */
	public void output3(String outputDir) {

		Set<String> progNames = this.allDefectsPerProgram.keySet();

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "NbDefectsPerClasses in" + progName + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out.println("#" + progName + ".v" + version
					+ ", nb_defects, involved_defects");

				for (String involvedClass : currentRepo.involvedClasses) {

					System.out.print(involvedClass + ",");

					int nbDefects = 0;
					String defectsNames = "";

					for (Iterator<String> iter = currentRepo.defectsTypes
						.iterator(); iter.hasNext();) {

						String defect = iter.next();
						String versionedDefect = version + "--" + defect;

						TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
							.get(versionedDefect);

						if (defectClasses.contains(involvedClass)) {
							nbDefects++;
							defectsNames += defect + " ";
						}

					}
					System.out.println(nbDefects + "," + defectsNames);
				}
			}
		}
		System.setOut(System.out);
	}

	public static String getFileShortname(String involvedClass) {

		final String t = "([A-Z]*[a-z]*)+\\.java";
		StringTokenizer ic = new StringTokenizer(involvedClass);

		while (ic.hasMoreTokens()) {

			pattern = Pattern.compile(t);
			matcher = pattern.matcher(ic.nextToken());
			if (matcher.find()) {

				//System.out.println(matcher.group());
				return matcher.group().toString();

			}
			else {
				return "Not File name!";
			}
		}

		return "Not File name!";
	}

	public static String getBugStatusofClass(
		String involvedClass,
		String XMLBugfile) {

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(XMLBugfile));
			racine = document.getRootElement();
		}
		catch (Exception e) {
		};
		List<?> className = racine.getChildren("product");

		Iterator<?> it = className.iterator();
		while (it.hasNext()) {

			Element courant = (Element) it.next();

			//System.out.println(courant.getChildText("filename"));

			StringTokenizer st = new StringTokenizer((String) courant
				.getChildText("filename"));
			while (st.hasMoreTokens()) {

				pattern = Pattern.compile(involvedClass);
				matcher = pattern.matcher(st.nextToken());
				if (matcher.find()) {
					return courant.getChildText("type");

				}

			}

		}

		return "Not Found!";
	}

	/*	print from lucene index :
	 * One output file per versions of program
	 * #Program.version, nb_defects, involved_defects, BugStatus
	 * class1, 2, LazyClass_FewMethods LazyClass_NotComplexClass, Nb Bug
	 */

	public void output5(String outputDir, Directory index, Directory indexbug)
		throws IOException, ParseException {

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "NbDefectsandIssuesPerClasses in" + progName
						+ " v" + version + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {

					//	System.out.print(involvedClass + ",");

					System.out.print(involvedClass + ".java" + ",");

					int nbDefects = 0;
					String defectsNames = "";

					for (Iterator<String> iter = currentRepo.defectsTypes
						.iterator(); iter.hasNext();) {

						String defect = iter.next();
						String versionedDefect = version + "--" + defect;

						TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
							.get(versionedDefect);

						if (defectClasses.contains(involvedClass)) {
							nbDefects++;
							defectsNames += defect + " ";
						}

					}
					//	System.out.println(nbDefects + "," + defectsNames);
					/*Query q = new QueryParser("filename", analyser).parse(getFileShortname(involvedClass
						+ ".java"));*/
					//primitive name
					String modClassName = involvedClass.replace(".", "/");
					Query q = new QueryParser("filename", analyser)
						.parse(modClassName + ".java");

					IndexSearcher s = new IndexSearcher(index);
					//	IndexSearcher s1 = new IndexSearcher(indexbug);

					Hits hits = s.search(q);
					//String time = new String("Date : ");
					String ids = new String("BugIDs: ");
					String revisions = new String("Revisions: ");

					//int count=0;

					for (int i = 0; i < hits.length(); ++i) {
						//Query q1 = new QueryParser("BUGID", analyser)
						//.parse(hits.doc(i).get("id"));
						//Hits bughits = s1.search(q1);
						//System.out.println(bughits.length());
						//&&(bughits.doc(0).get("CLASS")=="YES")
						//if( (bughits.length()>0)) {
						//System.out.println(bughits.doc(0).get("CLASS"));
						//  if((bughits.doc(0).get("CLASS").toString()).equalsIgnoreCase("YES")){
						//System.out.println("j'ai trouve un non bug");
						//	  count=count+1;
						//time = time + "[" + hits.doc(i).get("time") + "] ";
						ids = ids + (hits.doc(i).get("bugIDs"));
						revisions = revisions + (hits.doc(i).get("version"))
							+ "; ";

						// }
						//}

					}
					System.out.println(nbDefects + "," + defectsNames + ","
						+ hits.length() + "," + ids + "," + revisions);

					/*System.out.println(nbDefects + "," + defectsNames + ","
					+ count + "," + time);
					*/
					//s1.close();
					s.close();

				}
			}
		}
		System.setOut(System.out);
	}

	/*	
	 * One output file per versions of program
	 * #Program.version, nb_defects, involved_defects, BugStatus
	 * class1, 2, LazyClass_FewMethods LazyClass_NotComplexClass, Bug
	 */
	public void output4(String outputDir, String XMLBugfile) {

		Set<String> progNames = this.allDefectsPerProgram.keySet();

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "NbDefectsBugsPerClasses in" + progName + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out.println("#" + progName + ".v" + version
					+ ", nb_defects, involved_defects, bug_status");

				for (String involvedClass : currentRepo.involvedClasses) {

					//	System.out.print(involvedClass + ",");

					System.out.print(involvedClass + ".java" + ",");

					int nbDefects = 0;
					String defectsNames = "";

					for (Iterator<String> iter = currentRepo.defectsTypes
						.iterator(); iter.hasNext();) {

						String defect = iter.next();
						String versionedDefect = version + "--" + defect;

						TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
							.get(versionedDefect);

						if (defectClasses.contains(involvedClass)) {
							nbDefects++;
							defectsNames += defect + " ";
						}

					}
					//	System.out.println(nbDefects + "," + defectsNames);
					System.out.println(nbDefects
						+ ","
						+ defectsNames
						+ ","
						+ getBugStatusofClass(getFileShortname(involvedClass
							+ ".java"), XMLBugfile));

				}
			}
		}
		System.setOut(System.out);
	}

	public Set<String> getInvolvedClassesPerProgramPerVersion(
		String versionedProgramName) {

		String[] tab = versionedProgramName.split(" v");
		Set<String> res = new HashSet<String>();

		ProgramDefectsRepository repo = this.allDefectsPerProgram
			.get(tab[0]);
		Set<String> s = repo.allDefectsPerVersionPerTypes.keySet();

		for (String versionedDefect : s) {
			if (versionedDefect.startsWith(tab[1])) {
				res.addAll(repo.allDefectsPerVersionPerTypes
					.get(versionedDefect));
			}
		}
		return res;
	}

	private class IniFileFilter implements FilenameFilter {
		public boolean accept(File dir, String file) {
			return file.endsWith(".ini");
		}
	}

	private class ProgramDefectsRepository {

		TreeSet<String> versions;
		TreeSet<String> defectsTypes;

		//all involved classes in ALL versions
		TreeSet<String> involvedClasses;

		// key : "version--defect"
		Map<String, TreeSet<String>> allDefectsPerVersionPerTypes;

		public ProgramDefectsRepository() {
			this.versions = new TreeSet<String>();
			this.defectsTypes = new TreeSet<String>();
			this.involvedClasses = new TreeSet<String>();

			this.allDefectsPerVersionPerTypes = new HashMap<String, TreeSet<String>>();
		}

		public String defectsTypesAsString() {
			String res = "";
			for (Iterator<String> iter = this.defectsTypes.iterator(); iter
				.hasNext();) {
				res += iter.next();
				if (iter.hasNext()) {
					res += ",";
				}
			}
			return res;
		}

		public String versionsAsString() {
			String res = "";
			for (Iterator<String> iter = this.versions.iterator(); iter
				.hasNext();) {
				res += iter.next();
				if (iter.hasNext()) {
					res += ",";
				}
			}
			return res;
		}

		public String toString() {
			return this.versions + "\n" + this.defectsTypes + "\n"
				+ this.allDefectsPerVersionPerTypes + "\n"
				+ this.involvedClasses.toString();
		}

	}
	/*
	 * Check if contains org.eclipse in the file name
	 * 
	 */

	public static Boolean containsOrgEclipse(String className) {
		final String t = "org.eclipse(.[a-z]*)+";
		Boolean bool = false;
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

	/*
	 * Check if doesn't contains internal in the file name
	 * 
	 */

	public static Boolean notContainingInternal(String className) {
		final String t = "internal(.[a-z]*)+";
		Boolean bool = true;
		StringTokenizer st = new StringTokenizer(className);
		while (st.hasMoreTokens()) {

			pattern = Pattern.compile(t);
			matcher = pattern.matcher(st.nextToken());
			if (matcher.find()) {

				bool = false;
			}
		}
		return bool;

	}
	/*
	 * print results for classes with org.eclipse past
	 * 
	 */
	public void printResultsPast(
		String outputDir,
		Directory index,
		String versionDate) throws IOException, ParseException {
		float Clcount = 0.f;
		float IssueCount = 0.f;

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date verDate = new Date();
		verDate = extractDate("date: " + versionDate);

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues_past for" + progName + " v"
						+ version + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {

					if (containsOrgEclipse(involvedClass)) {

						Clcount = Clcount + 1;

						System.out.print(involvedClass + ".java" + ",");

						int nbDefects = 0;
						String defectsNames = "";

						for (Iterator<String> iter = currentRepo.defectsTypes
							.iterator(); iter.hasNext();) {

							String defect = iter.next();
							String versionedDefect = version + "--" + defect;

							TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

							if (defectClasses.contains(involvedClass)) {
								nbDefects++;
								defectsNames += defect + " ";
							}

						}

						String modClassName = involvedClass
							.replace(".", "/");
						Query q = new QueryParser("filename", analyser)
							.parse(modClassName + ".java");

						File file = new File(modClassName + ".java");

						Query q1 = new QueryParser("filename", analyser)
							.parse("Attic/" + file.getName());

						IndexSearcher s = new IndexSearcher(index);

						Hits hits = s.search(q);
						Hits hits1 = s.search(q1);
						String ids = "BugIDs: ";
						String revisions = "Revisions: ";

						int count = 0;

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if (extractDate(st).before(verDate)) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}

						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if (extractDate(st1).before(verDate)) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
						if (count > 0) {
							IssueCount = IssueCount + 1;

						}

						/*System.out.println(nbDefects + "," + defectsNames + ","
						+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
						*/
						System.out.println(nbDefects + "," + defectsNames
							+ "," + count + "," + ids + "," + revisions);

						s.close();

					}
				}

			}
			System.out.println("\n" + IssueCount / Clcount);

		}
		System.setOut(System.out);
	}

	/*
	 * print results for classes org.eclipse   future
	 * 
	 */

	public void printResultsFuture(
		String outputDir,
		Directory index,
		String versionDate) throws IOException, ParseException {
		float Clcount = 0.f;
		float IssueCount = 0.f;

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date verDate = new Date();
		verDate = extractDate("date: " + versionDate);
		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues_future for" + progName
						+ " v" + version + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {

					if (containsOrgEclipse(involvedClass)) {
						Clcount = Clcount + 1;
						System.out.print(involvedClass + ".java" + ",");

						int nbDefects = 0;
						String defectsNames = "";

						for (Iterator<String> iter = currentRepo.defectsTypes
							.iterator(); iter.hasNext();) {

							String defect = iter.next();
							String versionedDefect = version + "--" + defect;

							TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

							if (defectClasses.contains(involvedClass)) {
								nbDefects++;
								defectsNames += defect + " ";
							}

						}

						String modClassName = involvedClass
							.replace(".", "/");
						Query q = new QueryParser("filename", analyser)
							.parse(modClassName + ".java");

						File file = new File(modClassName + ".java");

						Query q1 = new QueryParser("filename", analyser)
							.parse("Attic/" + file.getName());

						IndexSearcher s = new IndexSearcher(index);

						Hits hits = s.search(q);
						Hits hits1 = s.search(q1);
						String ids = "BugIDs: ";
						String revisions = "Revisions: ";

						int count = 0;

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if (extractDate(st).after(verDate)) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}
						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if (extractDate(st1).after(verDate)) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
						if (count > 0) {
							IssueCount = IssueCount + 1;

						}

						/*System.out.println(nbDefects + "," + defectsNames + ","
							+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
						*/
						System.out.println(nbDefects + "," + defectsNames
							+ "," + count + "," + ids + "," + revisions);

						s.close();

					}
				}
			}
			System.out.println("\n" + IssueCount / Clcount);

		}
		System.setOut(System.out);
	}

	/*
	 * print results between dates but without internal classes
	 * 
	 */

	public void printResultsNointernal(
		String outputDir,
		Directory index,
		String Date1,
		String Date2) throws IOException, ParseException {
		float Clcount = 0.f;
		float IssueCount = 0.f;

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date date1 = new Date();
		Date date2 = new Date();
		date1 = extractDate("date: " + Date1);
		date2 = extractDate("date: " + Date2);

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues_no_internal for" + progName
						+ " v" + version + "btw" + Date1 + "_" + Date2
						+ ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {
					if ((containsOrgEclipse(involvedClass))
						&& (notContainingInternal(involvedClass))) {

						Clcount = Clcount + 1;
						System.out.print(involvedClass + ".java" + ",");

						int nbDefects = 0;
						String defectsNames = "";

						for (Iterator<String> iter = currentRepo.defectsTypes
							.iterator(); iter.hasNext();) {

							String defect = iter.next();
							String versionedDefect = version + "--" + defect;

							TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

							if (defectClasses.contains(involvedClass)) {
								nbDefects++;
								defectsNames += defect + " ";
							}

						}

						String modClassName = involvedClass
							.replace(".", "/");
						Query q = new QueryParser("filename", analyser)
							.parse(modClassName + ".java");

						File file = new File(modClassName + ".java");

						Query q1 = new QueryParser("filename", analyser)
							.parse("Attic/" + file.getName());

						IndexSearcher s = new IndexSearcher(index);

						Hits hits = s.search(q);
						Hits hits1 = s.search(q1);
						String ids = "BugIDs: ";
						String revisions = "Revisions: ";

						int count = 0;

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if ((extractDate(st).after(date1))
								&& (extractDate(st).before(date2))) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}
						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if ((extractDate(st1).after(date1))
								&& (extractDate(st1).before(date2))) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
						if (count > 0) {
							IssueCount = IssueCount + 1;

						}

						/*System.out.println(nbDefects + "," + defectsNames + ","
						+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
						*/
						System.out.println(nbDefects + "," + defectsNames
							+ "," + count + "," + ids + "," + revisions);

						s.close();

					}
				}

			}
			System.out.println("\n" + IssueCount / Clcount);

		}
		System.setOut(System.out);
	}

	/*
	 * print results for classes with org.eclipse past
	 * 
	 */
	public void printResultsPastNointernal(
		String outputDir,
		Directory index,
		String versionDate) throws IOException, ParseException {
		float Clcount = 0.f;
		float IssueCount = 0.f;

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date verDate = new Date();
		verDate = extractDate("date: " + versionDate);

		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues_past_no_internal for"
						+ progName + " v" + version + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {

					if ((containsOrgEclipse(involvedClass))
						&& (notContainingInternal(involvedClass))) {

						Clcount = Clcount + 1;

						System.out.print(involvedClass + ".java" + ",");

						int nbDefects = 0;
						String defectsNames = "";

						for (Iterator<String> iter = currentRepo.defectsTypes
							.iterator(); iter.hasNext();) {

							String defect = iter.next();
							String versionedDefect = version + "--" + defect;

							TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

							if (defectClasses.contains(involvedClass)) {
								nbDefects++;
								defectsNames += defect + " ";
							}

						}

						String modClassName = involvedClass
							.replace(".", "/");
						Query q = new QueryParser("filename", analyser)
							.parse(modClassName + ".java");

						File file = new File(modClassName + ".java");

						Query q1 = new QueryParser("filename", analyser)
							.parse("Attic/" + file.getName());

						IndexSearcher s = new IndexSearcher(index);

						Hits hits = s.search(q);
						Hits hits1 = s.search(q1);
						String ids = "BugIDs: ";
						String revisions = "Revisions: ";

						int count = 0;

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if (extractDate(st).before(verDate)) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}

						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if (extractDate(st1).before(verDate)) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
						if (count > 0) {
							IssueCount = IssueCount + 1;

						}

						/*System.out.println(nbDefects + "," + defectsNames + ","
						+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
						*/
						System.out.println(nbDefects + "," + defectsNames
							+ "," + count + "," + ids + "," + revisions);

						s.close();

					}
				}

			}
			System.out.println("\n" + IssueCount / Clcount);

		}
		System.setOut(System.out);
	}

	/*
	 * print results for classes org.eclipse   future
	 * 
	 */

	public void printResultsFutureNointernal(
		String outputDir,
		Directory index,
		String versionDate) throws IOException, ParseException {
		float Clcount = 0.f;
		float IssueCount = 0.f;

		Set<String> progNames = this.allDefectsPerProgram.keySet();
		final Analyzer analyser = new StandardAnalyzer();
		Date verDate = new Date();
		verDate = extractDate("date: " + versionDate);
		for (final String progName : progNames) {

			ProgramDefectsRepository currentRepo = this.allDefectsPerProgram
				.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output = new File(outputDir
						+ "ResultsDefectsIssues_future_no_internal for"
						+ progName + " v" + version + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				System.out
					.println("#"
						+ progName
						+ ".v"
						+ version
						+ ", nb_defects, involved_defects, nb_bug, bugIDs, revisions");

				for (String involvedClass : currentRepo.involvedClasses) {

					if ((containsOrgEclipse(involvedClass))
						&& (notContainingInternal(involvedClass))) {
						Clcount = Clcount + 1;
						System.out.print(involvedClass + ".java" + ",");

						int nbDefects = 0;
						String defectsNames = "";

						for (Iterator<String> iter = currentRepo.defectsTypes
							.iterator(); iter.hasNext();) {

							String defect = iter.next();
							String versionedDefect = version + "--" + defect;

							TreeSet<?> defectClasses = currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

							if (defectClasses.contains(involvedClass)) {
								nbDefects++;
								defectsNames += defect + " ";
							}

						}

						String modClassName = involvedClass
							.replace(".", "/");
						Query q = new QueryParser("filename", analyser)
							.parse(modClassName + ".java");

						File file = new File(modClassName + ".java");

						Query q1 = new QueryParser("filename", analyser)
							.parse("Attic/" + file.getName());

						IndexSearcher s = new IndexSearcher(index);

						Hits hits = s.search(q);
						Hits hits1 = s.search(q1);
						String ids = "BugIDs: ";
						String revisions = "Revisions: ";

						int count = 0;

						for (int i = 0; i < hits.length(); ++i) {

							final String st = hits.doc(i).get("datedetails");

							if (extractDate(st).after(verDate)) {
								ids = ids + (hits.doc(i).get("bugIDs"));
								revisions = revisions
									+ (hits.doc(i).get("version")) + "; ";
								count = count + 1;
							}

						}
						for (int j = 0; j < hits1.length(); ++j) {
							final String st1 = hits1.doc(j).get(
								"datedetails");
							if (extractDate(st1).after(verDate)) {

								ids = ids + (hits1.doc(j).get("bugIDs"));
								revisions = revisions
									+ (hits1.doc(j).get("version")) + "; ";
								count = count + 1;

							}
						}
						if (count > 0) {
							IssueCount = IssueCount + 1;

						}

						/*System.out.println(nbDefects + "," + defectsNames + ","
							+ hits.length()+ hits1.length() + "," + ids + "," + revisions);
						*/
						System.out.println(nbDefects + "," + defectsNames
							+ "," + count + "," + ids + "," + revisions);

						s.close();

					}
				}
			}
			System.out.println("\n" + IssueCount / Clcount);

		}
		System.setOut(System.out);
	}

}
