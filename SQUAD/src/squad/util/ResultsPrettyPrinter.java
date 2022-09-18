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
/**
 * @author Alban Tiberghien
 * @since 2008/08/01
 * 
 */

package squad.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComponent;
import util.io.ReaderInputStream;

public class ResultsPrettyPrinter {
	private HashMap<String, ProgramDefectsRepository> allDefectsPerProgram;

	private String getDefectName(final String iniFileName) {
		final String substring1 = " for ";
		final String substring2 = ".ini";
		final int begin = iniFileName.indexOf(substring1);
		final int end = iniFileName.indexOf(substring2);
		final String aSystemName =
			iniFileName.substring(begin + substring1.length(), end);
		return aSystemName;
	}

	private String getProgramName(final String iniFileName) {
		final String substring1 = " in ";
		final String substring2 = " v";
		//final String substring2 = "-";
		final int begin = iniFileName.indexOf(substring1);
		final int end = iniFileName.indexOf(substring2);
		final String aProgramName =
			iniFileName.substring(begin + substring1.length(), end);
		return aProgramName;
	}

	private String getProgramVersion(final String iniFileName) {
		final String substring1 = " v";
		//final String substring1 = "-";
		final String substring2 = " for ";
		final int begin = iniFileName.indexOf(substring1);
		final int end = iniFileName.indexOf(substring2);
		final String aProgramVersion =
			iniFileName.substring(begin + substring1.length(), end);
		return aProgramVersion;
	}

	public ResultsPrettyPrinter(String IniFileDir)
			throws FileNotFoundException, IOException {

		this.allDefectsPerProgram =
			new HashMap<String, ProgramDefectsRepository>();
		File inputDir = new File(IniFileDir);
		String[] iniFiles = inputDir.list(new IniFileFilter());

		for (String iniFileName : iniFiles) {

			String progName = this.getProgramName(iniFileName);
			String antipatternName = getDefectName(iniFileName);
			String version = getProgramVersion(iniFileName);

			ProgramDefectsRepository currentRepo =
				this.allDefectsPerProgram.get(progName);

			if (currentRepo == null) {
				currentRepo = new ProgramDefectsRepository();
				this.allDefectsPerProgram.put(progName, currentRepo);
			}

			currentRepo.versions.add(version);

			final Properties properties = new Properties();
			properties.load(new ReaderInputStream(new FileReader(IniFileDir
					+ iniFileName)));

			final OccurrenceBuilder solutionBuilder =
				OccurrenceBuilder.getInstance();
			final Occurrence[] occurrences =
				solutionBuilder.getCanonicalOccurrences(properties);

			for (Occurrence occurrence : occurrences) {

				List<OccurrenceComponent> allComponents =
					(List<OccurrenceComponent>) occurrence.getComponents();

				for (OccurrenceComponent solutionComponent : allComponents) {

					String compName = String.valueOf(solutionComponent.getName());
					int i2 = compName.indexOf('-');
					int i1 = compName.indexOf('.');

					if (i1 == -1 && !compName.equals("Name")) {

						if (i2 > 0) {
							compName = compName.substring(0, i2);
						}

						compName = antipatternName + "_" + compName;
						currentRepo.defectsTypes.add(compName);
						String key = version + "--" + compName;
						TreeSet<String> listOfDefects =
							currentRepo.allDefectsPerVersionPerTypes.get(key);

						if (listOfDefects == null) {
							listOfDefects = new TreeSet<String>();
							currentRepo.allDefectsPerVersionPerTypes.put(
								key,
								listOfDefects);
						}

						listOfDefects.add(String.valueOf(solutionComponent.getValue()));

						currentRepo.involvedClasses.add(String.valueOf(solutionComponent
							.getValue()));
					}
				}

			}
		}
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
				File output =
					new File(outputDir + "Results of " + progName + ".csv");
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

			ProgramDefectsRepository currentRepo =
				this.allDefectsPerProgram.get(progName);

			System.out.println(progName + "," + currentRepo.versionsAsString());

			//System.out.println("Class," + currentRepo.versionsAsString());

			for (Iterator<String> iter1 = currentRepo.defectsTypes.iterator(); iter1
				.hasNext();) {

				String defect = iter1.next();
				System.out.print(defect + ",");

				for (Iterator<String> iter2 = currentRepo.versions.iterator(); iter2
					.hasNext();) {

					String key = iter2.next() + "--" + defect;
					TreeSet<String> listOfDefects =
						currentRepo.allDefectsPerVersionPerTypes.get(key);

					String nb =
						listOfDefects == null ? "0" : listOfDefects.size() + "";
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

			ProgramDefectsRepository currentRepo =
				this.allDefectsPerProgram.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output =
						new File(outputDir + "Results of " + progName + ".v"
								+ version + ".csv");
					if (output.exists()) {
						System.err.println("Deleting ..." + output.getName());
						output.delete();
					}

					output.createNewFile();

					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				//	System.out.println(progName + ".v" + version + ","+ currentRepo.defectsTypesAsString());
				System.out.println("Class,"
						+ currentRepo.defectsTypesAsString());

				for (String involvedClass : currentRepo.involvedClasses) {
					System.out.print(involvedClass + ",");

					for (Iterator<String> iter =
						currentRepo.defectsTypes.iterator(); iter.hasNext();) {

						String versionedDefect = version + "--" + iter.next();
						TreeSet<?> defectClasses =
							currentRepo.allDefectsPerVersionPerTypes
								.get(versionedDefect);

						int test = 0;

						if (defectClasses != null) {
							test =
								defectClasses.contains(involvedClass) ? 1 : 0;
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

			ProgramDefectsRepository currentRepo =
				this.allDefectsPerProgram.get(progName);

			for (String version : currentRepo.versions) {

				try {
					File output =
						new File(outputDir + "NbDefectsPerClasses in"
								+ progName + ".csv");

					if (!output.exists()) {
						output.createNewFile();
					}
					System.setOut(new PrintStream(output));
				}
				catch (IOException e) {
					e.printStackTrace();
					System.exit(0);
				}

				//System.out.println("#"+progName + ".v" + version + ", nb_defects, involved_defects");
				System.out.println("Class, nb_defects, involved_defects");

				for (String involvedClass : currentRepo.involvedClasses) {

					System.out.print(involvedClass + ",");

					int nbDefects = 0;
					String defectsNames = "";

					for (Iterator<String> iter =
						currentRepo.defectsTypes.iterator(); iter.hasNext();) {

						String defect = iter.next();
						String versionedDefect = version + "--" + defect;

						TreeSet<?> defectClasses =
							currentRepo.allDefectsPerVersionPerTypes
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

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		//		ResultsPrettyPrinter evol = new ResultsPrettyPrinter("rsc/defects/GanttProject v1.10.2/");
		//		evol.output3("rsc/bugs/output_examples/");

		//"rsc/ArgoUML - DECOR Results/ArgoUML-" + args[0] + "/";
		String dirName1 = "D:/Software/Workspace3/SQUAD/rsc/AdditionEMSE2.1/Smells/";
		//"rsc/Eclipse2.1.1/test/";
		//String dirout="rsc/Eclipse2.1.1/test";
		ResultsPrettyPrinter evol = new ResultsPrettyPrinter(dirName1);
		evol.output2(dirName1);

	}

	public Set<String> getInvolvedClassesPerProgramPerVersion(
		String versionedProgramName) {

		String[] tab = versionedProgramName.split(" v");
		Set<String> res = new HashSet<String>();

		ProgramDefectsRepository repo = this.allDefectsPerProgram.get(tab[0]);
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

			this.allDefectsPerVersionPerTypes =
				new HashMap<String, TreeSet<String>>();
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

}
