/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.serialiser.test;

import java.io.File;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.LightClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.serialiser.JOSSerialiser;
import padl.serialiser.util.TransientFieldManager;
import padl.test.helper.ModelComparator;
import util.io.ProxyConsole;

public class TestArgoUML0198 extends TestCase {
	private static IAbstractModel OriginalModel;
	private static IAbstractModel SerialisedModel;
	private static String SerialisedFileName;

	public TestArgoUML0198(String name) {
		super(name);
	}
	protected void setUp() {
		if (TestArgoUML0198.OriginalModel == null) {
			try {
				System.out.println("Creating model...");
				final long beginning = System.currentTimeMillis();

				TestArgoUML0198.OriginalModel =
					Factory.getInstance().createCodeLevelModel(
						"ArgoUML v0.19.8");
				((ICodeLevelModel) TestArgoUML0198.OriginalModel)
					.create(new LightClassFileCreator(
						new String[] { "../../P-MARt Workspace/ArgoUML v0.19.8/bin/" },
						true));
				TestArgoUML0198.OriginalModel =
					new AACRelationshipsAnalysis()
						.invoke(TestArgoUML0198.OriginalModel);

				final long end = System.currentTimeMillis();
				System.out.print("Model created in ");
				System.out.print(end - beginning);
				System.out.println(" ms.");
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}

			TestArgoUML0198.SerialisedFileName =
				JOSSerialiser.getInstance().serialiseWithAutomaticNaming(
					TestArgoUML0198.OriginalModel,
					"../PADL Serialiser JOS Tests/rsc/");
			TestArgoUML0198.SerialisedModel =
				JOSSerialiser.getInstance().deserialise(
					TestArgoUML0198.SerialisedFileName);
		}
	}
	protected void tearDown() {
		final File serialisedFile =
			new File(TestArgoUML0198.SerialisedFileName);
		serialisedFile.delete();

		final File serialisedHelperFile =
			new File(TestArgoUML0198.SerialisedFileName
					+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
		serialisedHelperFile.delete();
	}
	public void testNames() {
		Assert.assertEquals(
			TestArgoUML0198.OriginalModel.getDisplayName(),
			TestArgoUML0198.SerialisedModel.getDisplayName());
	}
	public void testComparator() {
		TestArgoUML0198.OriginalModel.walk(new ModelComparator(
			TestArgoUML0198.SerialisedModel));
	}
}
