/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
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

public class JHotDrawTest extends TestCase {
	private static IAbstractModel OriginalModel;
	private static IAbstractModel SerialisedModel;
	private static String SerialisedFileName;

	public JHotDrawTest(final String name) {
		super(name);
	}

	protected void setUp()
			throws CreationException, UnsupportedSourceModelException {

		if (JHotDrawTest.OriginalModel == null) {
			System.out.println("Creating model...");
			final long beginning = System.currentTimeMillis();

			JHotDrawTest.OriginalModel = Factory.getInstance()
					.createCodeLevelModel("JHotDraw v5.1");
			((ICodeLevelModel) JHotDrawTest.OriginalModel)
					.create(new CompleteClassFileCreator(new String[] {
							"../PADL Serialiser JOS/JHotDraw v5.1/bin/" },
							true));
			JHotDrawTest.OriginalModel = new AACRelationshipsAnalysis()
					.invoke(JHotDrawTest.OriginalModel);

			final long end = System.currentTimeMillis();
			System.out.print("Model created in ");
			System.out.print(end - beginning);
			System.out.println(" ms.");
			JHotDrawTest.SerialisedFileName = JOSSerialiser.getInstance()
					.serialiseWithAutomaticNaming(JHotDrawTest.OriginalModel,
							"../PADL Serialiser JOS Tests/rsc/");
			JHotDrawTest.SerialisedModel = JOSSerialiser.getInstance()
					.deserialise(JHotDrawTest.SerialisedFileName);
		}
	}

	protected void tearDown() {
		final File serialisedFile = new File(JHotDrawTest.SerialisedFileName);
		serialisedFile.delete();

		final File serialisedHelperFile = new File(
				JHotDrawTest.SerialisedFileName
						+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
		serialisedHelperFile.delete();
	}

	public void testNames() {
		Assert.assertEquals(JHotDrawTest.OriginalModel.getDisplayName(),
				JHotDrawTest.SerialisedModel.getDisplayName());
	}

	public void testComparator() {
		JHotDrawTest.OriginalModel
				.walk(new ModelComparator(JHotDrawTest.SerialisedModel));
	}
}
