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
package padl.creator.javafile.eclipse.test.comparator;

import junit.framework.TestCase;
import padl.creator.classfile.LightClassFileCreator;
import padl.creator.javafile.eclipse.LightJavaFileCreator;
import padl.event.IModelListener;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import util.io.ProxyConsole;

public class LightModelsWithStatisticVisitorTest extends TestCase {
	public LightModelsWithStatisticVisitorTest(final String aName) {
		super(aName);
	}

	public void testCompareSomeFiles() {
		final String sourcePath = "../PADL Creator JavaFile (Eclipse)/src/main/java/";
		final String classpath = "";
		final String[] javaFiles = new String[] {
				"./../PADL Creator JavaFile (Eclipse)/src/main/java/padl/creator/javafile/eclipse/LightJavaFileCreator.java" };
		final String binPath = "../PADL Creator JavaFile (Eclipse)/target/classes/padl/creator/javafile/eclipse/LightJavaFileCreator.class";

		// Create a model form source code and add it a statistic listener
		final ICodeLevelModel modelFromSourceCode = Factory.getInstance()
				.createCodeLevelModel("Model");
		final IModelListener statisticListenerJavaFile = new ModelStatistics();
		modelFromSourceCode.addModelListener(statisticListenerJavaFile);
		try {
			modelFromSourceCode.create(
					new LightJavaFileCreator(sourcePath, classpath, javaFiles));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// Create a model form .class and add it a statistic listener
		final ICodeLevelModel modelFromClassFiles = Factory.getInstance()
				.createCodeLevelModel("Model");
		final IModelListener statisticListenerClassFile = new ModelStatistics();
		modelFromClassFiles.addModelListener(statisticListenerClassFile);
		try {
			modelFromClassFiles.create(
					new LightClassFileCreator(new String[] { binPath }, true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// Print models summary
		//	Utils.printModelSummary(
		//		statisticListenerJavaFile,
		//		modelFromSourceCode,
		//		"./results/javaFilesModelFromOneFile.txt");

		//	Utils.printModelSummary(
		//		statisticListenerClassFile,
		//		modelFromClassFiles,
		//		"./results/classFilesModelFromOneFile.txt");

		// modelFromSourceCode.walk(new PadlPrinterVisitor());
		// modelFromClassFiles.walk(new PadlPrinterVisitor());
		// modelFromSourceCode
		//	.walk(new RelaxedModelComparator(modelFromClassFiles));
	}

	public void testCompareSourceFolder() {
		final String sourcePath = "../PADL Creator JavaFile (Eclipse)/src/main/java/";
		final String classpath = "";
		final String binPath = "../PADL Creator JavaFile (Eclipse)/target/classes/";

		//create a model form source code and add it a statistic listener
		final ICodeLevelModel modelFromSourceCode = Factory.getInstance()
				.createCodeLevelModel("javaFilesModel");

		final IModelListener statisticListener = new ModelStatistics();
		modelFromSourceCode.addModelListener(statisticListener);
		try {

			modelFromSourceCode
					.create(new LightJavaFileCreator(sourcePath, classpath));

		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		//create a model form .class and add it a statistic listener
		final ICodeLevelModel modelFromClassFiles = Factory.getInstance()
				.createCodeLevelModel("classFilesModel");
		final IModelListener statisticListener1 = new ModelStatistics();
		modelFromClassFiles.addModelListener(statisticListener1);
		try {
			modelFromClassFiles.create(
					new LightClassFileCreator(new String[] { binPath }, true));
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		// Print models summary
		//	Utils.printModelSummary(
		//		statisticListener,
		//		modelFromSourceCode,
		//		"./results/javaFilesModel.txt");

		//	Utils.printModelSummary(
		//		statisticListener1,
		//		modelFromClassFiles,
		//		"./results/classFilesModel.txt");

		//modelFromSourceCode
		//.walk(new RelaxedModelComparator(modelFromClassFiles));
	}
}
