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
package sad.detection.generators.old;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.ModelAnnotatorLOCAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since  2006/02/03
 */
@SuppressWarnings("deprecation")
public class SpaghettiCodeDetectionCalleronXercesv101 {
	public static void main(final String[] args) {
		final ICodeLevelModel codeLevelModel =
			Factory
				.getInstance()
				.createCodeLevelModel("Model of Xerces v1.0.1");
		try {
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../SAD Tests/rsc/applications/Xercesv1.0.1.jar" },
					true));
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}

		try {
			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			final ModelAnnotatorLOCAnalysis annotator =
				new ModelAnnotatorLOCAnalysis();
			annotator.annotateFromJARs(
				new String[] { "rsc/applications/Xercesv1.0.1.jar" },
				idiomLevelModel);

			final IDesignSmellDetection ad1 = new SpaghettiCodeDetection();
			((SpaghettiCodeDetection) ad1)
				.output(new PrintWriter(
					ProxyDisk
						.getInstance()
						.fileTempOutput(
							"rsc/060310 Yann/CandidateSpaghettiCodeClassesXerces1.0.1.ini")));

			final PrintWriter outFile =
				new PrintWriter(
					ProxyDisk
						.getInstance()
						.fileTempOutput(
							"rsc/060310 Yann/CandidateSpaghettiCodeClassesXerces1.0.1.stats.txt"));
			outFile.println();
			outFile.println("###### Statistics #####");
			final Date today = new Date();
			final SimpleDateFormat formatter =
				new SimpleDateFormat("yyyy'/'MM'/'dd' Heure ' hh':'mm':'ss");
			final String timeStamp = formatter.format(today);
			outFile.println(timeStamp);
			//			outFile.print("Number of Poltergeists found: ");
			//			outFile.println(ad3.getSetOfAntiPatterns().size());
			outFile.print("Number of SpaghettiCodes found: ");
			outFile.println(ad1.getDesignSmells().size());
			outFile.close();
			
			// TODO Does this test do anything?!
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}
}
