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
package pom.helper;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import org.apache.commons.lang.ArrayUtils;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import pom.metrics.IUnaryMetric;
import pom.metrics.MetricsRepository;
import util.io.ProxyDisk;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/02/16
 */
public class MetricsGenerator {
	//	private static final String[] METRICS_TO_AVOID =
	//		new String[] { "CP", "EIC", "EIP", "NCP", "PIIR", "PP", "REIP", "RFP",
	//				"RPII", "RRFP", "RRTP", "RTP" };
	//	private static final String[] METRICS_TO_COMPUTE =
	//		new String[] { "VGsum", "TLOC", "MLOCsum", "LOC" };
	private static final String[] METRICS_TO_COMPUTE = new String[] { "NAD", "NMD" };

	//	private static final String[] NAMES =
	//		new String[] { "Eclipse v2.1.2 (JDT)", "JHotDraw v5.4b2",
	//				"Xalan v2.7.0", "Azureus v2.1.0.0", "ArgoUML v0.19.8",
	//				"Xerces v2.7.0 (No Interfaces)", "GanttProject v1.10.2",
	//				"Rhino v1.4R3 (LOC)", "Rhino v1.5R1 (LOC)",
	//				"Rhino v1.5R2 (LOC)", "Rhino v1.5R3 (LOC)",
	//				"Rhino v1.5R4 (LOC)", "Rhino v1.5R4.1 (LOC)",
	//				"Rhino v1.5R5 (LOC)", "Rhino v1.6R1 (LOC)",
	//				"Rhino v1.6R2 (LOC)", "Rhino v1.6R3 (LOC)",
	//				"Rhino v1.6R4 (LOC)", "Rhino v1.6R5 (LOC)",
	//				"Rhino v1.6R6 (LOC)", "Rhino v1.6R7 (LOC)",
	//				"Rhino v1.7R1 (LOC)", "DrJava v20020619", "DrJava v20020804",
	//				"DrJava v20030203", "DrJava v20040326", "JHotDraw v5.1",
	//				"JRefactory v2.6.24", "JUnit v3.7", "Lexi v0.1.1 alpha",
	//				"MapperXML v1.9.7", "Nutch v0.4", "QuickUML 2001", "SADPJ" };
	//	private static final String[] PATHS =
	//		new String[] {
	//				"D:/Software/P-MARt Workspace/Eclipse v2.1.2 (JDT)/bin/",
	//				"D:/Software/P-MARt Workspace/JHotDraw v5.4b2/bin/",
	//				"D:/Software/P-MARt Workspace/Xalan v2.7.0/bin/",
	//				"D:/Software/P-MARt Workspace/Azureus v2.1.0.0/bin/",
	//				"D:/Software/P-MARt Workspace/ArgoUML v0.19.8/bin/",
	//				"D:/Software/P-MARt Workspace/Xerces v2.7.0/bin/",
	//				"D:/Software/P-MARt Workspace/GanttProject v1.10.2/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.4R3/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.5R1/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.5R2/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.5R3/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.5R4/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.5R4.1/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.5R5/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R1/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R2/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R3/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R4/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R5/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R6/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.6R7/bin/",
	//				"D:/Software/P-MARt Workspace/Rhino v1.7R1/bin/",
	//				"D:/Software/P-MARt Workspace/DrJava v20020619/bin/",
	//				"D:/Software/P-MARt Workspace/DrJava v20020804/bin/",
	//				"D:/Software/P-MARt Workspace/DrJava v20030203/bin/",
	//				"D:/Software/P-MARt Workspace/DrJava v20040326/bin/",
	//				"D:/Software/P-MARt Workspace/JHotDraw v5.1/bin/",
	//				"D:/Software/P-MARt Workspace/JRefactory v2.6.24/bin/",
	//				"D:/Software/P-MARt Workspace/JUnit v3.7/bin/",
	//				"D:/Software/P-MARt Workspace/Lexi v0.1.1 alpha/bin/",
	//				"D:/Software/P-MARt Workspace/MapperXML v1.9.7/bin/",
	//				"D:/Software/P-MARt Workspace/Nutch v0.4/bin/",
	//				"D:/Software/P-MARt Workspace/QuickUML 2001/bin/",
	//				"D:/Software/P-MARt Workspace/SADPJ/bin/" };

	private static void computeMetrics(
		final IIdiomLevelModel idiomLevelModel,
		final String anOutputFileName) {
		try {
			final MetricsRepository metricsRepository =
				MetricsRepository.getInstance();
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(
					anOutputFileName + ".metrics");

			// Header.
			{
				final IUnaryMetric[] metrics =
					metricsRepository.getUnaryMetrics();
				for (int i = 0; i < metrics.length; i++) {
					final IUnaryMetric unaryMetric = metrics[i];
					final String metricName = unaryMetric.getName();
					//	if (!ArrayUtils.contains(
					//		MetricsGenerator.METRICS_TO_AVOID,
					//		metricName)) {
					if (ArrayUtils.contains(
						MetricsGenerator.METRICS_TO_COMPUTE,
						metricName)) {

						writer.write(metricName);
						if (i < metrics.length - 1) {
							writer.write(';');
						}
					}
				}
				writer.write(";Entities\n");
			}

			// Metric Values.
			final Iterator entityIterator =
				idiomLevelModel.getIteratorOnTopLevelEntities();
			while (entityIterator.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) entityIterator.next();

				if (!(firstClassEntity instanceof IGhost)) {
					//	&& !(firstClassEntity instanceof IInterface)) {

					//	System.out.print("Computing metrics for: ");
					//	System.out.println(firstClassEntity.getName());
					//	System.out.print('\t');

					final IUnaryMetric[] metrics =
						metricsRepository.getUnaryMetrics();
					for (int i = 0; i < metrics.length; i++) {
						final IUnaryMetric unaryMetric = metrics[i];
						final String metricName = unaryMetric.getName();
						//	if (!ArrayUtils.contains(
						//		MetricsGenerator.METRICS_TO_AVOID,
						//		metricName)) {
						if (ArrayUtils.contains(
							MetricsGenerator.METRICS_TO_COMPUTE,
							metricName)) {

							//	System.out.print(metricName);
							try {
								final double value =
									unaryMetric.compute(
										idiomLevelModel,
										firstClassEntity);
								//	System.out.print("");
								//	System.out.print(value);
								writer.write(String.valueOf(value));
							}
							catch (final Exception e) {
								writer.write("N/C");
							}

							if (i < metrics.length - 1) {
								//	System.out.print(", ");
								writer.write(';');
							}
						}
					}

					//	System.out.println();
					writer.write(';');
					writer.write(firstClassEntity.getID());
					if (entityIterator.hasNext()) {
						writer.write('\n');
					}
					writer.flush();
				}
			}

			writer.close();
		}
		catch (final IOException ioe) {
			ioe.printStackTrace();
		}
	}
	//	private static void computeMetricsFromJARs(
	//		final String[] someJARs,
	//		final String anOutputFileName) {
	//
	//		System.out.println("Analysing");
	//		for (int i = 0; i < someJARs.length; i++) {
	//			final String aJAR = someJARs[i];
	//			System.out.print('\t');
	//			System.out.print(aJAR);
	//			if (i < someJARs.length - 1) {
	//				System.err.println(',');
	//			}
	//		}
	//		System.out.println("...");
	//
	//		final IIdiomLevelModel idiomLevelModel =
	//			ModelGenerator.generateModelFromJARs(someJARs);
	//		if (idiomLevelModel == null
	//				|| idiomLevelModel.getNumberOfConstituents() == 0) {
	//
	//			System.err.println("--");
	//			System.err.println("Problem with ");
	//			for (int i = 0; i < someJARs.length; i++) {
	//				final String aJAR = someJARs[i];
	//				System.err.print('\t');
	//				System.err.print(aJAR);
	//				if (i < someJARs.length - 1) {
	//					System.err.println(',');
	//				}
	//			}
	//			System.err.println("--");
	//		}
	//		else {
	//			MetricsGenerator.computeMetrics(idiomLevelModel, anOutputFileName);
	//		}
	//	}
	private static void computeMetricsFromDirectory(
		final String aSourceFilePath,
		final String anOutputFileName) {

		System.out.print("Analysing ");
		System.out.print(aSourceFilePath);
		System.out.println("...");

		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator
				.generateModelFromClassFilesDirectory(aSourceFilePath);
		if (idiomLevelModel == null
				|| idiomLevelModel.getNumberOfConstituents() == 0) {

			System.err.println("--");
			System.err.print("Problem with: ");
			System.err.println(aSourceFilePath);
			System.err.println("--");
		}
		else {
			MetricsGenerator.computeMetrics(idiomLevelModel, anOutputFileName);
		}
	}
	private static void computeMetricsFromEclipse(
		final String aPluginPath,
		final String anOutputFileName) {

		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator.generateModelFromJAR(aPluginPath);
		if (idiomLevelModel == null
				|| idiomLevelModel.getNumberOfConstituents() == 0) {

			System.err.println("--");
			System.err.print("Problem with: ");
			System.err.println(aPluginPath);
			System.err.println("--");
		}
		else {
			MetricsGenerator.computeMetrics(idiomLevelModel, anOutputFileName);
		}

	}
	public static void main(final String[] args) {
		if (args.length == 1) {
			System.err
				.println("Usage: MetricsGenerator <path to bin directory (exclusing \"bin/\"> <name of the program>");
			MetricsGenerator.computeMetricsFromDirectory(
				"../../P-MARt Workspace/" + args[0] + "/bin/",
				args[0]);
		}
		else {
			//	final String root = "/repository/software/Versions/Eclipse/";
			//	final String[] list = new File(root).list(new FilenameFilter() {
			//		public boolean accept(final File dir, final String name) {
			//			return name.startsWith("eclipse-SDK")
			//					&& !name.endsWith(".zip");
			//		}
			//	});
			//	Arrays.sort(list);
			//	for (int i = 0; i < list.length; i++) {
			//		final String directory = list[i];
			//		MetricsGenerator.computeMetricsFromEclipse(root + directory
			//				+ "/plugins/", directory);
			//	}

			//	MetricsGenerator.computeMetricsFromEclipse(
			//		"D:/Software/P-MARt Workspace/Mylyn v1.0.1e3/plugins/",
			//		"Mylyn v1.0.1e3");

			//	System.out.print("Reading directory...");
			//	final String path = "../../P-MARt Workspace/";
			//	final String[] folders = new File(path).list();
			//	System.out.print(" found ");
			//	System.out.print(folders.length);
			//	System.out.println(" folders.");
			//	//	for (int i = 0; i < folders.length; i++) {
			//	//		if (folders[i].toLowerCase().startsWith("rhino v")) {
			//	//			final String programName = folders[i];
			//	//			final String programPath = path + programName + "/bin/";
			//	//			MetricsGenerator.computeMetricsFromDirectory(
			//	//				programPath,
			//	//				programName);
			//	//			System.out.println("Done computing metrics.");
			//	//		}
			//	//	}
			//	for (int i = 0; i < folders.length; i++) {
			//		if (folders[i].toLowerCase().startsWith("mylyn v")) {
			//			final String programName = folders[i];
			//			final String programPath = path + programName;
			//			MetricsGenerator.computeMetricsFromEclipse(
			//				programPath,
			//				programName);
			//			System.out.println("Done computing metrics.");
			//		}
			//	}

			//	MetricsGenerator.computeMetricsFromDirectory(
			//		"../../P-MARt Workspace/Pooka v2.0/bin/",
			//		"Pooka v2.0");
			//	MetricsGenerator.computeMetricsFromDirectory(
			//		"../../P-MARt Workspace/SIP Communicator v1.0-draft/bin/",
			//		"SIP Communicator v1.0-draft");

			//		System.out.print("Reading directory...");
			//		final String path = "../../P-MARt Workspace/";
			//		final String[] folders = new File(path).list();
			//		System.out.print(" found ");
			//		System.out.print(folders.length);
			//		System.out.println(" folders.");
			//		for (int i = 0; i < folders.length; i++) {
			//			if (folders[i].toLowerCase().startsWith("argouml-")) {
			//				final String programName = folders[i];
			//				final String programPath = path + programName;
			//				MetricsGenerator.computeMetricsFromEclipse(
			//					programPath,
			//					programName);
			//				System.out.print("Done computing metrics.");
			//			}
			//		}

		}

		//	MetricsGenerator.computeMetricsFromDirectory(
		//		"../../P-MARt Workspace/GanttProject v1.10.2/bin/",
		//		"GanttProject v1.10.2");

		//	MetricsGenerator.computeMetricsFromDirectory(
		//		"../../P-MARt Workspace/Z - Pooka/bin/",
		//		"Pooka");
		//	MetricsGenerator.computeMetricsFromDirectory(
		//		"../../P-MARt Workspace/Z - SIP-communicator-1-0-draft/bin/",
		//		"SIP-communicator-1-0-draft");

		//	for (int i = 0; i < MetricsGenerator.PATHS.length; i++) {
		//		MetricsGenerator.computeMetricsFromDirectory(
		//			MetricsGenerator.PATHS[i],
		//			MetricsGenerator.NAMES[i]);
		//	}

		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-2.0.1-win32/plugins/",
		//			"rsc/Eclipse v2.0.1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-2.0.2-win32/plugins/",
		//			"rsc/Eclipse v2.0.2");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-2.1-win32/plugins/",
		//			"Eclipse v2.1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/repository/software/Versions/Eclipse/eclipse-SDK-3.0-win32/plugins/",
		//			"Eclipse v3.0");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.1.1-win32/plugins/",
		//			"rsc/Eclipse v3.1.1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.1.2-win32/plugins/",
		//			"rsc/Eclipse v3.1.2");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.1-win32/plugins/",
		//			"rsc/Eclipse v3.1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.3.1.1-win32/plugins/",
		//			"rsc/Eclipse v3.3.1.1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.3.2-win32/plugins/",
		//			"rsc/Eclipse v3.3.2");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M1-win32/plugins/",
		//			"rsc/Eclipse v3.5M1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M2-win32/plugins/",
		//			"rsc/Eclipse v3.5M2");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M3-win32/plugins/",
		//			"rsc/Eclipse v3.5M3");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M4-win32/plugins/",
		//			"rsc/Eclipse v3.5M4");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M5-win32/plugins/",
		//			"rsc/Eclipse v3.5M5");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M6-win32/plugins/",
		//			"rsc/Eclipse v3.5M6");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5M7-win32/plugins/",
		//			"rsc/Eclipse v3.5M7");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5RC1-win32/plugins/",
		//			"rsc/Eclipse v3.5RC1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5RC2-win32/plugins/",
		//			"rsc/Eclipse v3.5RC2");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5RC3-win32/plugins/",
		//			"rsc/Eclipse v3.5RC3");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.5RC4-win32/plugins/",
		//			"rsc/Eclipse v3.5RC4");

		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.1-win32/plugins/",
		//			"rsc/Eclipse v3.1");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.2-win32/plugins/",
		//			"rsc/Eclipse v3.2");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.3-win32/plugins/",
		//			"rsc/Eclipse v3.3");
		//	MetricsGenerator
		//		.computeMetricsFromEclipse(
		//			"/home/repository-software/Eclipse_versions/eclipse-SDK-3.4-win32/plugins/",
		//			"rsc/Eclipse v3.4");
		//	MetricsGenerator.computeMetricsFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v3.1/plugins/",
		//		"rsc/Eclipse JDT v3.1");
		//	MetricsGenerator.computeMetricsFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v3.3/plugins/",
		//		"rsc/Eclipse JDT v3.3");
		//	MetricsGenerator.computeMetricsFromEclipse(
		//		"../../P-MARt Workspace/Eclipse JDT v3.4/plugins/",
		//		"rsc/Eclipse JDT v3.4");

		//		MetricsGenerator.computeMetricsFromEclipse(
		//			"../../P-MARt Workspace/Eclipse JDT v2.0/plugins/",
		//			"Eclipse JDT v2.0");
		MetricsGenerator.computeMetricsFromEclipse(
			"../../P-MARt Workspace/Eclipse JDT v2.1/plugins/",
			"Eclipse JDT v2.1");
		//		MetricsGenerator.computeMetricsFromEclipse(
		//			"../../P-MARt Workspace/Mylyn v2.0.0-e3.3/plugins/",
		//			"Mylyn v2.0.0-e3.3");
		//		MetricsGenerator.computeMetricsFromEclipse(
		//			"../../P-MARt Workspace/Mylyn v2.1-e3.3/plugins/",
		//			"Mylyn v2.1-e3.3");
		//		MetricsGenerator.computeMetricsFromDirectory(
		//			"../../P-MARt Workspace/Rhino v1.4R3/bin/",
		//			"Rhino v1.4R3");
		//		MetricsGenerator.computeMetricsFromDirectory(
		//			"../../P-MARt Workspace/Rhino v1.5R1/bin/",
		//			"Rhino v1.5R1");
	}
}
