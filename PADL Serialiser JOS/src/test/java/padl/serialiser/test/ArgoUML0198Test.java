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
import padl.creator.classfile.LightClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.serialiser.JOSSerialiser;
import padl.serialiser.util.TransientFieldManager;
import padl.test.helper.ModelComparator;
import util.io.ProxyConsole;

public class ArgoUML0198Test extends TestCase {
	private static IAbstractModel OriginalModel;
	private static IAbstractModel SerialisedModel;
	private static String SerialisedFileName;

	public ArgoUML0198Test(String name) {
		super(name);
	}
	protected void setUp() {
		if (ArgoUML0198Test.OriginalModel == null) {
			try {
				System.out.println("Creating model...");
				final long beginning = System.currentTimeMillis();

				ArgoUML0198Test.OriginalModel =
					Factory.getInstance().createCodeLevelModel(
						"ArgoUML v0.19.8");
				((ICodeLevelModel) ArgoUML0198Test.OriginalModel)
					.create(new LightClassFileCreator(
						new String[] { "../../P-MARt Workspace/ArgoUML v0.19.8/bin/" },
						true));
				ArgoUML0198Test.OriginalModel =
					new AACRelationshipsAnalysis()
						.invoke(ArgoUML0198Test.OriginalModel);

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

			ArgoUML0198Test.SerialisedFileName =
				JOSSerialiser.getInstance().serialiseWithAutomaticNaming(
					ArgoUML0198Test.OriginalModel,
					"../PADL Serialiser JOS Tests/rsc/");
			ArgoUML0198Test.SerialisedModel =
				JOSSerialiser.getInstance().deserialise(
					ArgoUML0198Test.SerialisedFileName);
		}
	}
	protected void tearDown() {
		final File serialisedFile =
			new File(ArgoUML0198Test.SerialisedFileName);
		serialisedFile.delete();

		final File serialisedHelperFile =
			new File(ArgoUML0198Test.SerialisedFileName
					+ TransientFieldManager.METHOD_INVOCATION_EXTENSION);
		serialisedHelperFile.delete();
	}
	public void testNames() {
		Assert.assertEquals(
			ArgoUML0198Test.OriginalModel.getDisplayName(),
			ArgoUML0198Test.SerialisedModel.getDisplayName());
	}
	public void testComparator() {
		ArgoUML0198Test.OriginalModel.walk(new ModelComparator(
			ArgoUML0198Test.SerialisedModel));
	}
}
