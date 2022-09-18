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
package padl.serialiser.test;

import java.io.File;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.serialiser.DB4OSerialiser;
import padl.test.helper.ModelComparator;
import padl.util.ModelStatistics;

public class TestDB4OSerialiserArgoUML020 extends TestCase {
	private static IAbstractLevelModel AbstractLevelModel;
	private static IAbstractLevelModel SerialisedAbstractLevelModel;
	private static String SerialisedFileName;

	public TestDB4OSerialiserArgoUML020(String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (TestDB4OSerialiserArgoUML020.AbstractLevelModel == null) {
			System.out.println("Creating model...");
			long beginning = System.currentTimeMillis();

			final ModelStatistics statistics = new ModelStatistics();
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel("ArgoUML v0.20");
			codeLevelModel.addModelListener(statistics);
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { "../../P-MARt Workspace/ArgoUML v0.20/bin/" },
				true));

			TestDB4OSerialiserArgoUML020.AbstractLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			long end = System.currentTimeMillis();
			System.out.println(statistics);
			System.out.print("Model created in ");
			System.out.print(end - beginning);
			System.out.println(" ms.");
			beginning = System.currentTimeMillis();

			TestDB4OSerialiserArgoUML020.SerialisedFileName =
				DB4OSerialiser.getInstance().serialiseWithAutomaticNaming(
					TestDB4OSerialiserArgoUML020.AbstractLevelModel);

			end = System.currentTimeMillis();
			System.out.print("Model serialised in ");
			System.out.print(end - beginning);
			System.out.println(" ms.");
			beginning = System.currentTimeMillis();

			TestDB4OSerialiserArgoUML020.SerialisedAbstractLevelModel =
				(IAbstractLevelModel) DB4OSerialiser.getInstance().deserialise(
					TestDB4OSerialiserArgoUML020.SerialisedFileName);

			end = System.currentTimeMillis();
			System.out.print("Model deserialised in ");
			System.out.print(end - beginning);
			System.out.println(" ms.");
		}
	}
	protected void tearDown() {
		final File serialisedFile =
			new File(TestDB4OSerialiserArgoUML020.SerialisedFileName);
		serialisedFile.delete();
	}
	public void testNames() {
		Assert.assertEquals(
			TestDB4OSerialiserArgoUML020.AbstractLevelModel.getDisplayName(),
			TestDB4OSerialiserArgoUML020.SerialisedAbstractLevelModel
				.getDisplayName());
	}
	public void testComparator() {
		TestDB4OSerialiserArgoUML020.AbstractLevelModel
			.walk(new ModelComparator(
				TestDB4OSerialiserArgoUML020.SerialisedAbstractLevelModel));
	}
}
