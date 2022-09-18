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
package epi.test.helper;

import java.io.File;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.aolfile.AOLCreator;
import padl.creator.aolfile.invocations.MethodInvocationAnalyser;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import epi.solver.Approximation;
import epi.solver.EPISolver;
import epi.solver.OptimisedBitVectorSolver;
import epi.solver.Solution;
import epi.solver.StringBuilder;


/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/28
 */
public class ALOEPICaller {
	private static final String[] MOTIFS = new String[] { "Adapter",
			"Composite", "Abstract Factory", "Observer" };
	//	private static final String[] SODALIA_PATHS =
	//		new String[] {
	//			"E:/Temp/Sodalia/Method Invocations/IMS/Admin_2.3.2/Admin_2.3.2-concat_des_2007-01-28113522.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/Admin_2.3.3/Admin_2.3.3-concat_des_2007-01-28113620.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/Common_2.5.1/Common_2.5.1-concat_des_2007-01-28113715.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/DB_2.1.2/DB_2.1.2-concat_des_2007-01-28113731.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/ED_2.1.0/ED_2.1.0-concat_des_2007-01-28113750.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/HDOgui_2.4.4/HDOgui_2.4.4-concat_des_2007-01-28113806.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/HDOgui_2.4.5/HDOgui_2.4.5-concat_des_2007-01-28113922.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/HDOgui_2.4.6/HDOgui_2.4.6-concat_des_2007-01-28114038.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/Meta_2.1.0/Meta_2.1.0-concat_des_2007-01-28114140.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/NMI_2.2.0/NMI_2.2.0-concat_des_2007-01-28114144.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/OSSI_2.0.2/OSSI_2.0.2-concat_des_2007-01-28114206.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/PM_2.0.3/PM_2.0.3-concat_des_2007-01-28114213.aol",
	//			"E:/Temp/Sodalia/Method Invocations/IMS/TM_2.2.0/TM_2.2.0-concat_des_2007-01-28114245.aol" };
	//	private static final String[] SODALIA_NAMES =
	//		new String[] {
	//			"Admin_2.3.2.solutions.txt",
	//			"Admin_2.3.3.solutions.txt",
	//			"DB_2.1.2.solutions.txt",
	//			"Common_2.5.1.solutions.txt",
	//			"ED_2.1.0.solutions.txt",
	//			"HDOgui_2.4.4.solutions.txt",
	//			"HDOgui_2.4.5.solutions.txt",
	//			"HDOgui_2.4.6.solutions.txt",
	//			"Meta_2.1.0.solutions.txt",
	//			"NMI_2.2.0.solutions.txt",
	//			"OSSI_2.0.2.solutions.txt",
	//			"PM_2.0.3.solutions.txt",
	//			"TM_2.2.0.solutions.txt" };

	public static void main(final String[] args) {
		final ALOEPICaller epi = new ALOEPICaller();
		epi.analyseCodeLevelModels("E:/Temp/Sodalia/Method Invocations/");
		epi.analyseIdiomLevelModel(
			"E:/Temp/Sodalia/Design and Code/IMS/design/TM/TM.aol",
			"TM");
	}
	private void analyseIdiomLevelModel(
		final String anAOLIdiomLevelFilePath,
		final String aName) {

		final long startTime = System.currentTimeMillis();
		ProxyConsole.getInstance().debugOutput().print("Analysing ");
		ProxyConsole.getInstance().debugOutput().print(aName);
		ProxyConsole.getInstance().debugOutput().println("...");

		final IIdiomLevelModel idiomLevelModel =
			Factory.getInstance().createIdiomLevelModel(aName.toCharArray());
		final AOLCreator aolCreator =
			new AOLCreator(new String[] { anAOLIdiomLevelFilePath });
		aolCreator.create(idiomLevelModel, true, true);

		final String programString =
			StringBuilder.buildModelString(idiomLevelModel);

		for (int j = 0; j < ALOEPICaller.MOTIFS.length; j++) {
			final String motif = ALOEPICaller.MOTIFS[j];
			final String motifString = StringBuilder.buildPatternString(motif);
			final EPISolver solver =
				new OptimisedBitVectorSolver(
					programString,
					motifString,
					motif,
					new Approximation(),
					null);
			final Solution[] solutions = solver.computeSolutions();

			ProxyConsole.getInstance().debugOutput().print(solutions.length);
			ProxyConsole.getInstance().debugOutput().print(" solutions for ");
			ProxyConsole.getInstance().debugOutput().print(motif);
			ProxyConsole.getInstance().debugOutput().print(" in ");
			ProxyConsole
				.getInstance()
				.debugOutput()
				.print(System.currentTimeMillis() - startTime);
			ProxyConsole.getInstance().debugOutput().println(" ms.\n");

			Solution.print(
				solutions,
				ProxyDisk.getInstance().fileTempOutput(
					"rsc/Sodalia/" + aName + '.' + motif.toLowerCase()
							+ ".solutions.ini"));
		}
	}
	private void analyseCodeLevelModels(final String aPath) {
		final File pathFile = new File(aPath);
		final String[] subPaths = pathFile.list();

		for (int i = 0; i < subPaths.length; i++) {
			final String fileName = aPath + subPaths[i];
			final File file = new File(fileName);

			if (file.isDirectory()) {
				this.analyseCodeLevelModels(fileName + '/');
			}
			else if (fileName.indexOf("-concat_des_") > 0
					&& !fileName.endsWith(".filtered.aol")) {

				final String name =
					fileName.substring(
						fileName.lastIndexOf('/') + 1,
						fileName.indexOf('-'));
				final String cldFileName =
					fileName.replaceAll("concat_", "").replaceAll(
						".aol",
						".cld");

				try {
					final long startTime = System.currentTimeMillis();
					ProxyConsole
						.getInstance()
						.debugOutput()
						.print("Analysing ");
					ProxyConsole.getInstance().debugOutput().print(name);
					ProxyConsole.getInstance().debugOutput().println("...");

					ICodeLevelModel codeLevelModel =
						Factory.getInstance().createCodeLevelModel(name);
					codeLevelModel.create(new AOLCreator(
						new String[] { fileName }));

					IIdiomLevelModel idiomLevelModel = null;
					try {
						final MethodInvocationAnalyser methodInvocationAdder =
							new MethodInvocationAnalyser();
						methodInvocationAdder.setCLDFile(cldFileName);
						codeLevelModel =
							(ICodeLevelModel) methodInvocationAdder
								.invoke(codeLevelModel);

						idiomLevelModel =
							(IIdiomLevelModel) new AACRelationshipsAnalysis(
								false).invoke(codeLevelModel);
					}
					catch (final UnsupportedSourceModelException e) {
						e.printStackTrace(ProxyConsole
							.getInstance()
							.errorOutput());
					}

					final String programString =
						StringBuilder.buildModelString(idiomLevelModel);

					for (int j = 0; j < ALOEPICaller.MOTIFS.length; j++) {
						final String motif = ALOEPICaller.MOTIFS[j];
						final String motifString =
							StringBuilder.buildPatternString(motif);
						final EPISolver solver =
							new OptimisedBitVectorSolver(
								programString,
								motifString,
								motif,
								new Approximation(),
								null);
						final Solution[] solutions = solver.computeSolutions();

						ProxyConsole
							.getInstance()
							.debugOutput()
							.print(solutions.length);
						ProxyConsole
							.getInstance()
							.debugOutput()
							.print(" solutions for ");
						ProxyConsole.getInstance().debugOutput().print(motif);
						ProxyConsole.getInstance().debugOutput().print(" in ");
						ProxyConsole
							.getInstance()
							.debugOutput()
							.print(System.currentTimeMillis() - startTime);
						ProxyConsole
							.getInstance()
							.debugOutput()
							.println(" ms.\n");
						Solution.print(
							solutions,
							ProxyDisk.getInstance().fileTempOutput(
								"rsc/Sodalia/" + name + '.'
										+ motif.toLowerCase()
										+ ".solutions.ini"));
					}
				}
				catch (final CreationException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

//	final AOLCreator aolCreator =
//		new AOLCreator(new String[] { "rsc/Mozilla/moz-1.0.rel.n.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "rsc/Firefox/AddBookmark_subset1.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(new String[] { "rsc/Firefox/AddBookmark.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(new String[] { "rsc/mozilla-1.0-concat_des_2006-02-15114305.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/Admin_2.3.3/Admin_2.3.3.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/DB_2.1.2/DB_2.1.2.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/ED_2.1.0/ED_2.1.0.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/HDOgui_2.4.6/HDOgui_2.4.6.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/Meta_2.1.0/Meta_2.1.0.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/NMI_2.2.0/NMI_2.2.0.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/OSSI_2.0.2/OSSI_2.0.2.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/PM_2.0.3/PM_2.0.3.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/code/IMS_2.5.1/TM_2.2.0/TM_2.2.0.N.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/IMS/design/ADM/ADM.aol" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/REUSE/code/AlarmBrowser.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/REUSE/code/LOGLIB.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/DbConn.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/DistPublic.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/Error_Library.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/GUIBrowser.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/GUIconfig.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/GUIevents.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/Trace_Lib.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/VIPER_PROCESSING.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/VMCS_Processing.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VBS/code/VSPA_Processing.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/CollectorAcquisition.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/CollectorDistribution.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/CollectorFormatting.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/DispatcherDistribution.aol.raw" });
//	final AOLCreator aolCreator =
//		new AOLCreator(
//			new String[] { "C:/Temp/Giulio's AOL/VSPS/code/VP_FM_Filter.aol.raw" });
