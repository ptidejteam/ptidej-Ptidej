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
package sad.detection.test.classfile.xerces;

import java.io.PrintWriter;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.Blob.ControllerClassDetection;
import sad.codesmell.detection.repository.Blob.DataClassDetection;
import sad.codesmell.detection.repository.Blob.LargeClassDetection;
import sad.codesmell.detection.repository.Blob.LowCohesionDetection;
import sad.codesmell.detection.repository.FunctionalDecomposition.ClassOneMethodDetection;
import sad.codesmell.detection.repository.FunctionalDecomposition.FunctionClassDetection;
import sad.codesmell.detection.repository.FunctionalDecomposition.NoInheritanceDetection;
import sad.codesmell.detection.repository.FunctionalDecomposition.NoPolymorphismDetection;
import sad.codesmell.detection.repository.SpaghettiCode.ClassGlobalVariableDetection;
import sad.codesmell.detection.repository.SpaghettiCode.LongMethodDetection;
import sad.codesmell.detection.repository.SpaghettiCode.MethodNoParameterDetection;
import sad.codesmell.detection.repository.SwissArmyKnife.MultipleInterfaceDetection;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since 2005/12/04
 */
public final class VariousSmellsTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	//	private static ModelAnnotatorLOC Annotator;
	private static final String PATH = "../SAD/target/test-classes/Xercesv1.0.1.jar";
	private static final String MAIN_PATH = "../SAD/target/test-classes/";
	private static String SYSTEM_NAME = VariousSmellsTest.PATH
			.substring(VariousSmellsTest.MAIN_PATH.length());

	public VariousSmellsTest(final String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (VariousSmellsTest.IdiomLevelModel == null) {
			//	final ICodeLevelModel codeLevelModel =
			//		Factory.getInstance().createCodeLevelModel(PATH);
			//	codeLevelModel.create(new CompleteClassFileCreator(
			//		ConstituentRepository.getInstance(ClassFileRepository
			//			.getInstance(ConstituentRepository.class)),
			//		new String[] { DetectionCodeSmellTest.PATH },
			//		true));
			//	DetectionCodeSmellTest.IdiomLevelModel =
			//		(IIdiomLevelModel) new AACRelationshipsAnalysis()
			//			.invoke(codeLevelModel);
			//	DetectionCodeSmellTest.Annotator = new ModelAnnotatorLOC();
			//	Annotator.annotateFromJARs(
			//		new String[] { PATH },
			//		DetectionCodeSmellTest.IdiomLevelModel);

			VariousSmellsTest.IdiomLevelModel = ModelGenerator
					.generateModelFromClassFilesDirectories(
							new String[] { VariousSmellsTest.PATH });
		}
	}

	/** 
	 *  Test of the detection of classes that have class and global variables
	 */
	public void testDetectAllClassGlobalVariable() {
		final ICodeSmellDetection cs = new ClassGlobalVariableDetection();
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				VariousSmellsTest.SYSTEM_NAME + "_ClassGlobalVariable.ini")));
		Assert.assertEquals(
				"detectAllClassGlobalVariable: Incorrect Number of classes with no class and global variables",
				11, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of classes that do not use inheritance
	 */
	public void testDetectAllClassNoInheritance() {
		final ICodeSmellDetection cs = new NoInheritanceDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(VariousSmellsTest.SYSTEM_NAME
						+ "_NoInheritanceDetection.ini")));
		Assert.assertEquals(
				"detectAllClassNoInheritance: Incorrect Number of classes that do not use inheritance",
				189, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of classes that do not use polymorphism
	 */
	public void testDetectAllClassNoPolymorphism() {
		final ICodeSmellDetection cs = new NoPolymorphismDetection();
		cs.detect(VariousSmellsTest.IdiomLevelModel);

		cs.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(VariousSmellsTest.SYSTEM_NAME
						+ "_NoPolymorphismDetection.ini")));
		Assert.assertEquals(
				"detectAllClassNoPolymorphism: Incorrect Number of classes that do not use polymorphism",
				148, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of classes that have only one method
	 */
	public void testDetectAllClassOneMethod() {
		final ICodeSmellDetection cs = new ClassOneMethodDetection();
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				VariousSmellsTest.SYSTEM_NAME + "_ClassOneMethod.ini")));
		Assert.assertEquals(
				"detectAllClassOneMethod: Incorrect Number of classes that have only one method",
				11, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of classes that have only private fields
	 */
	//	public void testDetectAllClassPrivateField() {
	//		final ICodeSmellDetection cs =
	//			new ClassPrivateFieldDetection(
	//				DetectionCodeSmellTest.IdiomLevelModel);
	//
	//		cs.outputResults(this.nameSoftware + "_ClassPrivateField.ini");
	//
	//		DetectionCodeSmellTest.assertEquals(
	//			"detectAllClassPrivateField: Incorrect Number of classes with only private fields",
	//			38, cs.getSetOfSmells().size());
	//	}
	/** 
	 *  Test of the detection of controller classes
	 */
	public void testDetectAllControllerClass() {
		final ICodeSmellDetection cs = new ControllerClassDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(VariousSmellsTest.SYSTEM_NAME
						+ "_ControllerClassDetection.ini")));
		Assert.assertEquals(
				"detectControllerClass: Incorrect Number of controller classes",
				2, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of data classes
	 */
	public void testDetectAllDataClass() {
		final ICodeSmellDetection cs = new DataClassDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				VariousSmellsTest.SYSTEM_NAME + "_DataClassDetection.ini")));
		Assert.assertEquals(
				"detectAllDataClass: Incorrect Number of data classes", 80,
				cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of function classes
	 */
	public void testDetectAllFunctionClass() {
		final ICodeSmellDetection cs = new FunctionClassDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(VariousSmellsTest.SYSTEM_NAME
						+ "_FunctionClassDetection.ini")));
		Assert.assertEquals(
				"detectFunctionClass: Incorrect Number of function classes", 0,
				cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of large classes
	 */
	public void testDetectAllLargeClass() {
		final ICodeSmellDetection cs = new LargeClassDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				VariousSmellsTest.SYSTEM_NAME + "_LargeClassDetection.ini")));
		Assert.assertEquals(
				"detectAllLargeClass: Incorrect Number of large classes", 17,
				cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of leaf classes
	 */
	//	public void testDetectAllLeafClass() {
	//		final ICodeSmellDetection cs =
	//			new LeafClassDetection(DetectionCodeSmellTest.IdiomLevelModel);
	//
	//		cs.output(new PrintWriter(new FileWriter(this.nameSoftware + "_LeafClassDetection.ini")));
	//
	//		DetectionCodeSmellTest.assertEquals(
	//			"detectAllLeafClass: Incorrect Number of leaf classes",
	//			157, cs.getSetOfSmells().size());
	//	}
	/** 
	 *  Test of the detection of the long methods
	 */
	public void testDetectAllLongMethods() {
		final ICodeSmellDetection cs = new LongMethodDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				VariousSmellsTest.SYSTEM_NAME + "_LongMethodDetection.ini")));
		Assert.assertEquals(
				"detectLongMethods: Incorrect Number of long methods found", 18,
				cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of the low cohesion classes
	 */
	public void testDetectAllLowCohesion() {
		final ICodeSmellDetection cs = new LowCohesionDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				VariousSmellsTest.SYSTEM_NAME + "_LowCohesionDetection.ini")));
		Assert.assertEquals(
				"detectLowCohesion: Incorrect Number of low cohesion classes found",
				0, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of methods with no parameter
	 */
	public void testDetectAllMethodsNoParameter() {
		final ICodeSmellDetection cs = new MethodNoParameterDetection();
		// TODO Necessary?
		//	cs.setMetricsFileRepository(ClassFileRepository
		//		.getInstance(MetricRepository.class));
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(VariousSmellsTest.SYSTEM_NAME
						+ "_MethodsNoParameterDetection.ini")));
		Assert.assertEquals(
				"detectAllMethodsNoParameter: Incorrect Number of methods with no parameter",
				159, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of classes that have multiple interfaces
	 */
	public void testDetectAllMultipleInterface() {
		// Number of interfaces that are considered as multiple
		final ICodeSmellDetection cs = new MultipleInterfaceDetection();
		cs.detect(VariousSmellsTest.IdiomLevelModel);
		cs.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(VariousSmellsTest.SYSTEM_NAME
						+ "_MultipleInterfaceDetection.ini")));
		Assert.assertEquals(
				"detectAllMultipleInterface: Incorrect Number of multiple interfaces",
				6, cs.getCodeSmells().size());
	}

	/** 
	 *  Test of the detection of process classes
	 */
	//	public void testDetectAllClassProcessClass() {
	//
	//		final ICodeSmellDetection cs =
	//			new ProcessClassDetection(DetectionCodeSmellTest.IdiomLevelModel);
	//
	//		cs.output(new PrintWriter(new FileWriter(this.nameSoftware + "_ProcessClassDetection.ini")));
	//
	//		DetectionCodeSmellTest.assertEquals(
	//			"detectAllClassProcessClass: Incorrect number of process classes",
	//			0, cs.getSetOfSmells().size());
	//	}
	/** 
	 *  Test of the detection of small classes
	 */
	//	public void testDetectAllSmallClass() {
	//
	//		final ICodeSmellDetection cs =
	//			new SmallClassDetection(DetectionCodeSmellTest.IdiomLevelModel);
	//
	//		cs.output(new PrintWriter(new FileWriter(this.nameSoftware + "_SmallClassDetection.ini")));
	//
	//		DetectionCodeSmellTest.assertEquals(
	//			"detectAllSmallClass: Incorrect Number of small classes",
	//			0, cs.getSetOfSmells().size());
	//	}
	/** 
	 *  Test of the detection of stateless classes
	 */
	//	public void testDetectAllStatelessClass() {
	//
	//		final ICodeSmellDetection cs =
	//			new StatelessClassDetection(
	//				DetectionCodeSmellTest.IdiomLevelModel);
	//
	//		cs.output(new PrintWriter(new FileWriter(this.nameSoftware + "_StatelessClassDetection.ini")));
	//
	//		DetectionCodeSmellTest.assertEquals(
	//			"detectAllStatelessClass: Incorrect Number of stateless classes",
	//			65, cs.getSetOfSmells().size());
	//	}
	/** 
	 *  Test of the detection of classes that do not use polymorphism
	 */
	//	public void testDetectAllClassUsePolymorphism() {
	//
	//		final ICodeSmellDetection cs =
	//			new UsePolymorphismDetection(
	//				DetectionCodeSmellTest.IdiomLevelModel);
	//
	//		cs.output(new PrintWriter(new FileWriter(this.nameSoftware + "_UsePolymorphismDetection.ini")));
	//
	//		DetectionCodeSmellTest.assertEquals(
	//			"detectAllClassUsePolymorphism: Incorrect Number of classes that use polymorphism",
	//			41, cs.getSetOfSmells().size());
	//	}
	//	public static Test suite() {
	//		TestSuite suite = new TestSuite();
	//
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllClassGlobalVariable"));
	//		suite
	//			.addTest(new DetectionCodeSmellTest("testDetectAllClassOneMethod"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllClassPrivateField"));
	//		suite
	//			.addTest(new DetectionCodeSmellTest("testDetectAllControllerClass"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllDataClass"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllFunctionClass"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllLargeClass"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllLeafClass"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllLongMethods"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllLowCohesion"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllMethodsNoParameter"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllMultipleInterface"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllClassNoInheritance"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllClassNoPolymorphism"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllClassProcessClass"));
	//		suite.addTest(new DetectionCodeSmellTest("testDetectAllSmallClass"));
	//		suite
	//			.addTest(new DetectionCodeSmellTest("testDetectAllStatelessClass"));
	//		suite.addTest(new DetectionCodeSmellTest(
	//			"testDetectAllClassUsePolymorphism"));
	//
	//		return suite;
	//	}
}
