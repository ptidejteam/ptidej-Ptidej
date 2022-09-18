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
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.serialiser.JOSSerialiser;
import padl.serialiser.util.TransientFieldManager;
import padl.test.helper.ModelComparator;

public class TestJHotDraw extends TestCase {
	private static IAbstractModel OriginalModel;
	private static IAbstractModel SerialisedModel;
	private static String SerialisedFileName;

	public TestJHotDraw(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (TestJHotDraw.OriginalModel == null) {
			System.out.println("Creating model...");
			final long beginning = System.currentTimeMillis();

			TestJHotDraw.OriginalModel =
				Factory.getInstance().createCodeLevelModel("JHotDraw v5.1");
			((ICodeLevelModel) TestJHotDraw.OriginalModel)
				.create(new CompleteClassFileCreator(
					new String[] { "../../P-MARt Workspace/JHotDraw v5.1/bin/" },
					true));
			TestJHotDraw.OriginalModel =
				new AACRelationshipsAnalysis()
					.invoke(TestJHotDraw.OriginalModel);

			final long end = System.currentTimeMillis();
			System.out.print("Model created in ");
			System.out.print(end - beginning);
			System.out.println(" ms.");
			TestJHotDraw.SerialisedFileName =
				JOSSerialiser.getInstance().serialiseWithAutomaticNaming(
					TestJHotDraw.OriginalModel,
					"../PADL Serialiser JOS Tests/rsc/");
			TestJHotDraw.SerialisedModel =
				JOSSerialiser.getInstance().deserialise(
					TestJHotDraw.SerialisedFileName);
		}
	}
	protected void tearDown() {
		final File serialisedFile = new File(TestJHotDraw.SerialisedFileName);
		serialisedFile.delete();

		final File serialisedHelperFile =
			new File(TestJHotDraw.SerialisedFileName
					+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
		serialisedHelperFile.delete();
	}
	public void testNames() {
		Assert.assertEquals(
			TestJHotDraw.OriginalModel.getDisplayName(),
			TestJHotDraw.SerialisedModel.getDisplayName());
	}
	public void testComparator() {
		TestJHotDraw.OriginalModel.walk(new ModelComparator(
			TestJHotDraw.SerialisedModel));
	}
}
