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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import org.apache.lucene.queryParser.ParseException;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import util.io.ProxyDisk;

public class TrackChanges {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) throws FileNotFoundException,
			IOException, ParseException {
		// TODO Auto-generated method stub
		List<?> files = new ArrayList<Object>();
		TreeSet<String> Classes = new TreeSet<String>();

		TrackChanges trk = new TrackChanges();

		LineNumberReader fr = null;
		try {
			fr =
				new LineNumberReader(new FileReader(
					"D:/Documents/Workspace/changes/classes.txt"));
			String line;
			while ((line = fr.readLine()) != null) {

				Classes.add(line.toString().trim());
				System.out.println(line.toString().trim());
			}
		}
		catch (Exception e) {
			System.out.println("Could not add lines");
		}
		finally {
			fr.close();
		}

		trk.outputPast(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/",
			"D:/Documents/Workspace/changes/argouml/argouml-svn-mirror",
			"org",
			"argouml v0.19.8",
			"2005-08-11",
			Classes);

		trk.outputFutur(
			"D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/",
			"D:/Documents/Workspace/changes/argouml/argouml-svn-mirror",
			"org",
			"argouml v0.19.8",
			"2005-08-11",
			Classes);
		//trk.outputFutur("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/", "D:/Documents/Workspace/changes/xalan_java/xalan_java-svn-mirror","org" ,"xalan", "2006-03-01");

		//trk.output("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/", "D:/Documents/Workspace/changes/argouml/argouml-svn-mirror","org/", "argouml");
		//trk.output("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/", "D:/Documents/Workspace/changes/xalan_java/xalan_java-svn-mirror","org/" ,"xalan");
		//	trk.outputFutur("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/", "D:/Documents/Workspace/changes/xerces_java/xerces_java_1-svn-mirror","org" ,"xerces","2003-10-13");
		//	trk.outputCVSPast("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/", "D:/Documents/Workspace/changes/azureus2","org" ,"azureus2", "2007-08-25");
		//trk.outputCVSPast("D:/Software/Tests Workspace/foutsekh-program-BugDefects/rsc/", "D:/Documents/Workspace/changes/org.eclipse.jdt","org" ,"eclipseJDT", "2003-11-03");

		/*System.out.println(extractDate("2007-09-20T21:45:37.677035Z").getTime()-extractDate("1999-11-16T05:19:36.919536Z").getTime());
		String root="D:/Documents/Workspace/changes/xalan_java/xalan_java-svn-mirror/src/org/apache/xalan/xslt/Process.java.xlg";
		Date dt = getdateFirstrevision("D:/Documents/Workspace/changes/xalan_java/xalan_java-svn-mirror/src/org/apache/xalan/xslt/Process.java.xlg");
		int x=getNbRevisionsBeforeDate(root, "1999-11-16T05:19:36.919536Z");
		String root1="D:/Documents/Workspace/changes/org.eclipse.jdt/org.eclipse.jdt.core/compiler/org/eclipse/jdt/internal/compiler/classfmt/ClassFileStruct.java.xlg";
		
		System.out.println(dt.toString());
		System.out.println(getdateFirstrevisionCVS(root1));
		*/
	}
	static org.jdom2.Document document;

	static Element racine;

	/*
	 *  get change periods of a class (extraction from SVN)
	 */

	public static List<String> getChangePeriodOfClass(String xlgfile) {
		List<String> dates = new ArrayList<String>();

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();
		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren("logentry");

		Iterator<?> it = logentries.iterator();
		while (it.hasNext()) {

			Element courant = (Element) it.next();
			dates.add(courant.getChildText("date"));
			//System.out.println( courant.getChildText("date"));

		}

		return dates;
	}
	/*
	 * get earliest date
	 * 
	 */
	/*
	 *  get change periods of a class (extraction from SVN)
	 */

	public static Date getdateFirstrevision(String xlgfile) {
		List<Date> dates = new ArrayList<Date>();
		Date firstDate = new Date();
		//firstDate = extractDate(versionDate);

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();
		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren("logentry");

		Iterator<?> it = logentries.iterator();
		while (it.hasNext()) {

			Element courant = (Element) it.next();
			dates.add(extractDate(courant.getChildText("date")));

		}

		firstDate = (Date) (dates.get(0));

		for (int i = 0; i < dates.size(); i++) {

			if (((Date) (dates.get(i))).before(firstDate)) {

				firstDate = (Date) dates.get(i);
			}
		}

		return firstDate;
	}
	/*
	 *  get first revision date CVS files
	 * 
	 */
	public static Date getdateFirstrevisionCVS(String xlgfile) {
		List<Date> dates = new ArrayList<Date>();
		Date firstDate = new Date();
		//firstDate = extractDate(versionDate);

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();

		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren();
		Iterator<?> it = logentries.iterator();

		while (it.hasNext()) {

			Element courant = (Element) it.next();

			//courant.getContent(1).getValue() permet d'acceder au champ date
			if (((String) courant.getName()).equalsIgnoreCase("entry")) {
				dates.add(extractDate(courant.getContent(1).getValue()));
			}

		}

		firstDate = (Date) (dates.get(0));

		for (int i = 0; i < dates.size(); i++) {

			if (((Date) (dates.get(i))).before(firstDate)) {

				firstDate = (Date) dates.get(i);
			}
		}

		return firstDate;
	}

	/*
	 * get last revision date
	 * 
	 */
	public static Date getdateLastrevision(String xlgfile) {
		List<Date> dates = new ArrayList<Date>();
		Date lastDate = new Date();
		//firstDate = extractDate(versionDate);

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();
		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren("logentry");

		Iterator<?> it = logentries.iterator();
		while (it.hasNext()) {

			Element courant = (Element) it.next();
			dates.add(extractDate(courant.getChildText("date")));

		}

		lastDate = (Date) (dates.get(0));

		for (int i = 0; i < dates.size(); i++) {

			if (((Date) (dates.get(i))).after(lastDate)) {

				lastDate = (Date) dates.get(i);
			}
		}

		return lastDate;
	}
	/*
	 *  get first revision date CVS files
	 * 
	 */
	public static Date getdateLastrevisionCVS(String xlgfile) {
		List<Date> dates = new ArrayList<Date>();
		Date lastDate = new Date();
		//firstDate = extractDate(versionDate);

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();

		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren();
		Iterator<?> it = logentries.iterator();

		while (it.hasNext()) {

			Element courant = (Element) it.next();

			//courant.getContent(1).getValue() permet d'acceder au champ date
			if (((String) courant.getName()).equalsIgnoreCase("entry")) {
				dates.add(extractDate(courant.getContent(1).getValue()));
			}

		}

		lastDate = (Date) (dates.get(0));

		for (int i = 0; i < dates.size(); i++) {

			if (((Date) (dates.get(i))).after(lastDate)) {

				lastDate = (Date) dates.get(i);
			}
		}

		return lastDate;
	}

	public static Date extractDate(String s) {

		final String bugdate = s.substring(0, 10);

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

	/*
	 * get number of revisions after given date
	 * 
	 */
	public static int getNbRevisionsBeforeDate(String xlgfile, String VersionDt) {

		Date dt = extractDate(VersionDt);
		List<Date> dates = new ArrayList<Date>();

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();
		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren("logentry");

		Iterator<?> it = logentries.iterator();
		while (it.hasNext()) {

			Element courant = (Element) it.next();
			Date det = extractDate(courant.getChildText("date"));
			if (det.before(dt)) {

				dates.add(det);
			}
		}

		return dates.size();
	}

	/*
	 * get number of revision before date in case of CVS
	 * 
	 */

	public static int getNbRevisionsBeforeDateCVS(
		String xlgfile,
		String VersionDt) {
		Date dt = extractDate(VersionDt);

		int numEntries = 0;
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();

		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren();
		Iterator<?> it = logentries.iterator();

		while (it.hasNext()) {

			Element courant = (Element) it.next();

			//courant.getContent(1).getValue() permet d'acceder au champ date
			if (((String) courant.getName()).equalsIgnoreCase("entry") &&

			(extractDate(courant.getContent(1).getValue())).before(dt)) {
				numEntries = numEntries + 1;
				//	System.out.println(courant.getContent(1).getValue());
			}

		}

		return numEntries;
	}

	/*
	 * get number of revisions after given date
	 * 
	 */
	public static int getNbRevisionsAfterDate(String xlgfile, String VersionDt) {

		Date dt = extractDate(VersionDt);
		List<Date> dates = new ArrayList<Date>();

		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();
		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren("logentry");

		Iterator<?> it = logentries.iterator();
		while (it.hasNext()) {

			Element courant = (Element) it.next();
			Date det = extractDate(courant.getChildText("date"));
			if (det.after(dt)) {

				dates.add(det);
			}
		}

		return dates.size();
	}

	/*
	 * get number of revision before date in case of CVS
	 * 
	 */

	public static int getNbRevisionsAfterDateCVS(
		String xlgfile,
		String VersionDt) {
		Date dt = extractDate(VersionDt);

		int numEntries = 0;
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();

		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren();
		Iterator<?> it = logentries.iterator();

		while (it.hasNext()) {

			Element courant = (Element) it.next();

			//courant.getContent(1).getValue() permet d'acceder au champ date
			if (((String) courant.getName()).equalsIgnoreCase("entry") &&

			(extractDate(courant.getContent(1).getValue())).after(dt)) {
				numEntries = numEntries + 1;
				//	System.out.println(courant.getContent(1).getValue());
			}

		}

		return numEntries;
	}

	/*
	 *  get change periods of a class (extraction from CVS)
	 */

	public static int getNumEntriesForClassCVS(String xlgfile) {
		int numEntries = 0;
		SAXBuilder sxb = new SAXBuilder();
		try {
			document = sxb.build(new File(xlgfile));
			racine = document.getRootElement();

		}
		catch (Exception e) {
		}
		;
		List<?> logentries = racine.getChildren();
		Iterator<?> it = logentries.iterator();

		while (it.hasNext()) {

			Element courant = (Element) it.next();
			//System.out.println(courant.getName());
			if (((String) courant.getName()).equalsIgnoreCase("entry")) {
				numEntries = numEntries + 1;

				//	Iterator it1=courant.getContent(0);
				//	while(it1.hasNext()){
				System.out.println(courant.getContent(1).getValue());
				//	}
			}

		}
		System.out.println(numEntries);

		return numEntries;
	}

	/*
	 * Extractor of xlgfiles from directories recursively
	 * 
	 */
	public List<String> extractXlgFromDir(String path, List<String> xlgfiles) {
		final File pathFile = new File(path);
		final String[] subPaths = pathFile.list();
		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = path + "/" + subPaths[i];
			final File file = new File(fileName);
			if (file.isDirectory()) {
				this.extractXlgFromDir(fileName, xlgfiles);
			}
			else {

				if (fileName.endsWith(".xlg") == true) {
					xlgfiles.add(fileName);
					//j++;
					//  System.out.println(fileName);
				}
			}

		}
		//System.out.println(jarfiles.size());

		return xlgfiles;
	}

	private String getFileName(final String xlgFileName, final String delimiter) {
		final String substring1 = delimiter;
		//+ "/src/";
		final String substring2 = ".xlg";
		final int begin = xlgFileName.indexOf(substring1);
		final int end = xlgFileName.indexOf(substring2);
		final String aFileName =
			xlgFileName.substring(begin + substring1.length(), end);
		return delimiter + aFileName;
	}

	/*
	 * output of changes when coming from SVN
	 * 
	 */

	public void outputPast(
		String outputDir,
		String inputDir,
		final String delimiter,
		String progName,
		String versionDt,
		TreeSet<String> Classes) throws IOException, ParseException {

		try {
			final Writer output =
				ProxyDisk.getInstance().fileTempOutput(outputDir + "NbofRevisionBeforeRelPerClasses in"
						+ progName + ".csv");
			output
				.write("#" + progName + ", nb_changes, nb_changes_per_month ");
			output.flush();
			List<String> files = new ArrayList<String>();

			List<String> xlgfiles = this.extractXlgFromDir(inputDir, files);
			for (int i = 0; i < xlgfiles.size(); i++) {
				if (Classes.contains(this.getFileName(
					(String) xlgfiles.get(i),
					delimiter).replace('/', '.'))) {
					//List<String> dat = this.getChangePeriodOfClass(xlgfiles.get(i));
					float period =
						extractDate(versionDt).getTime()
								- getdateFirstrevision((String) xlgfiles.get(i))
									.getTime();
					float fq =
						(2629800000.0f) * (getNbRevisionsBeforeDate(
							(String) xlgfiles.get(i),
							versionDt) / period);

					output.write(this.getFileName(
						(String) xlgfiles.get(i),
						delimiter).replace('/', '.')
							+ ","
							+ getNbRevisionsBeforeDate(
								(String) xlgfiles.get(i),
								versionDt) + "," + fq);
					output.flush();
				}
			}
			output.close();
		}
		catch (IOException e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}
	/*
	 * output futur
	 * 
	 */

	public void outputFutur(
		String outputDir,
		String inputDir,
		final String delimiter,
		String progName,
		String versionDt,
		TreeSet<String> Classes) throws IOException, ParseException {

		try {
			final Writer output =
				ProxyDisk.getInstance().fileTempOutput(
					outputDir + "NbofRevisionAfterRelPerClasses in" + progName
							+ ".csv");
			output
				.write("#" + progName + ", nb_changes, nb_changes_per_month ");
			output.flush();
			List<String> files = new ArrayList<String>();

			List<String> xlgfiles = this.extractXlgFromDir(inputDir, files);
			for (int i = 0; i < xlgfiles.size(); i++) {
				if (Classes.contains(this.getFileName(
					(String) xlgfiles.get(i),
					delimiter).replace('/', '.'))) {

					//List<String> dat = this.getChangePeriodOfClass(xlgfiles.get(i));
					float period =
						getdateLastrevision((String) xlgfiles.get(i)).getTime()
								- extractDate(versionDt).getTime();
					float fq =
						(2629800000.0f) * (getNbRevisionsAfterDate(
							(String) xlgfiles.get(i),
							versionDt) / period);

					output.write(this.getFileName(
						(String) xlgfiles.get(i),
						delimiter).replace('/', '.')
							+ ","
							+ getNbRevisionsAfterDate(
								(String) xlgfiles.get(i),
								versionDt) + "," + fq);
					output.flush();

				}
			}
			output.close();

		}
		catch (IOException e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

	/*
	 * output futur CVS
	 * 
	 */

	public void outputCVSFutur(
		String outputDir,
		String inputDir,
		final String delimiter,
		String progName,
		String versionDt,
		TreeSet<?> Classes) throws IOException, ParseException {

		try {
			final Writer output =
				ProxyDisk.getInstance().fileTempOutput(
					outputDir + "NbofRevisionAfterRelPerClasses in" + progName
							+ ".csv");
			output
				.write("#" + progName + ", nb_changes, nb_changes_per_month ");
			output.flush();
			List<String> files = new ArrayList<String>();

			List<String> xlgfiles = this.extractXlgFromDir(inputDir, files);
			for (int i = 0; i < xlgfiles.size(); i++) {

				//List<String> dat = this.getChangePeriodOfClass(xlgfiles.get(i));
				float period =
					getdateLastrevisionCVS((String) xlgfiles.get(i)).getTime()
							- extractDate(versionDt).getTime();
				float fq =
					(2629800000.0f) * (getNbRevisionsAfterDateCVS(
						(String) xlgfiles.get(i),
						versionDt) / period);

				output.write(this.getFileName(
					(String) xlgfiles.get(i),
					delimiter).replace('/', '.')
						+ ","
						+ getNbRevisionsAfterDateCVS(
							(String) xlgfiles.get(i),
							versionDt) + "," + fq);
				output.flush();

			}
			output.close();

		}
		catch (IOException e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

	/*
	 * output of changes when coming from a cvs
	 * 
	 */
	public void outputCVSPast(
		String outputDir,
		String inputDir,
		final String delimiter,
		String progName,
		String versionDt,
		TreeSet<?> Classes) throws IOException, ParseException {

		try {
			final Writer output =
				ProxyDisk.getInstance().fileTempOutput(
					outputDir + "NbofRevisionBeforeRelPerClasses in" + progName
							+ ".csv");
			output
				.write("#" + progName + ", nb_changes, nb_changes_per_month ");
			output.flush();
			List<String> files = new ArrayList<String>();

			List<String> xlgfiles = this.extractXlgFromDir(inputDir, files);
			for (int i = 0; i < xlgfiles.size(); i++) {

				// List<Element> dat = this.getNumEntriesForClassCVS(xlgfiles.get(i));
				if (Classes.contains(this.getFileName(
					(String) xlgfiles.get(i),
					delimiter).replace('/', '.'))) {

					float period =
						extractDate(versionDt).getTime()
								- getdateFirstrevisionCVS(
									(String) xlgfiles.get(i)).getTime();
					float fq =
						(2629800000.0f) * (getNbRevisionsBeforeDateCVS(
							(String) xlgfiles.get(i),
							versionDt) / period);

					output.write(this.getFileName(
						(String) xlgfiles.get(i),
						delimiter).replace('/', '.')
							+ ","
							+ getNbRevisionsBeforeDateCVS(
								(String) xlgfiles.get(i),
								versionDt) + "," + fq);
					output.flush();
				}
			}
			output.close();

		}
		catch (IOException e) {
			e.printStackTrace();
			//System.exit(0);
		}
	}

}
