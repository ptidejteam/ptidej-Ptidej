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

import org.junit.Assert;

import junit.framework.TestCase;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.ModelAnnotatorLOCAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.impl.Factory;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.Blob.BlobDetection;
import sad.designsmell.detection.repository.FunctionalDecomposition.FunctionalDecompositionDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import sad.designsmell.detection.repository.SwissArmyKnife.SwissArmyKnifeDetection;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since  2005/12/04
 */
public final class CodeSmellDetectionCalleronArgoUML extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	private static ModelAnnotatorLOCAnalysis Annotator;
	//	private static MetricRepository Metrics;
	private static final String PATH = "rsc/applications/ArgoUMLv0.18.1.jar";
	private static final String MAIN_PATH = "rsc/applications/";
	private static final String ProgramName = CodeSmellDetectionCalleronArgoUML.PATH
			.substring(CodeSmellDetectionCalleronArgoUML.MAIN_PATH.length());

	public CodeSmellDetectionCalleronArgoUML(final String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (CodeSmellDetectionCalleronArgoUML.IdiomLevelModel == null) {
			final ICodeLevelModel codeLevelModel = Factory.getInstance()
					.createCodeLevelModel(
							CodeSmellDetectionCalleronArgoUML.PATH);
			codeLevelModel.create(new CompleteClassFileCreator(
					new String[] { CodeSmellDetectionCalleronArgoUML.PATH },
					true));
			CodeSmellDetectionCalleronArgoUML.IdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
			//	DetectionAntipatternArgoUML018Test.Metrics = MetricRepository
			//		.getMetricRepository(DetectionAntipatternArgoUML018Test.IdiomLevelModel);
			CodeSmellDetectionCalleronArgoUML.Annotator = new ModelAnnotatorLOCAnalysis();
			CodeSmellDetectionCalleronArgoUML.Annotator.annotateFromJARs(
					new String[] { CodeSmellDetectionCalleronArgoUML.PATH },
					CodeSmellDetectionCalleronArgoUML.IdiomLevelModel);
		}
	}

	/** 
	 *  Test of the detection of Blobs
	 */
	public void testDetectBlobs() {
		IDesignSmellDetection ad;

		ad = new BlobDetection();
		((BlobDetection) ad).output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput("rsc/060224_Yann/"
						+ CodeSmellDetectionCalleronArgoUML.ProgramName
						+ "_Blob.ini")));

		ad = new FunctionalDecompositionDetection();
		((FunctionalDecompositionDetection) ad)
				.output(new PrintWriter(ProxyDisk.getInstance()
						.fileTempOutput("rsc/060224_Yann/"
								+ CodeSmellDetectionCalleronArgoUML.ProgramName
								+ "_FunctionalDecomposition.ini")));

		ad = new SpaghettiCodeDetection();
		((SpaghettiCodeDetection) ad)
				.output(new PrintWriter(ProxyDisk.getInstance()
						.fileTempOutput("rsc/060224_Yann/"
								+ CodeSmellDetectionCalleronArgoUML.ProgramName
								+ "_SpaghettiCodeDetection.ini")));

		ad = new SwissArmyKnifeDetection();
		((SwissArmyKnifeDetection) ad)
				.output(new PrintWriter(ProxyDisk.getInstance()
						.fileTempOutput("rsc/060224_Yann/"
								+ CodeSmellDetectionCalleronArgoUML.ProgramName
								+ "_SwissArmyKnifeDetection.ini")));

		// TODO This test does not do anything!?

		Assert.assertTrue(true);
	}
}
