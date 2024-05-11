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
package padl.creator.javafile.eclipse.test.basic;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;

public class ManyClassesInOneFileTest extends TestCase {

	public ManyClassesInOneFileTest(final String aName) {
		super(aName);

	}

	/**
	 * One public class in a file and 2 other classes which are package classes, not internal to the first one
	 */
	public void testManyClassesInOneFile() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/example/manyClassesInOneFile/ManyClassesInOneFile.java" };
		final String classPathEntry = "";

		ICodeLevelModel model = Factory.getInstance().createCodeLevelModel("");
		model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);

		final IClass clazz =
			(IClass) model
				.getTopLevelEntityFromID("padl.example.manyClassesInOneFile.ManyClassesInOneFile");

		Assert.assertNotNull(clazz);

		final IClass clazz1 =
			(IClass) model
				.getTopLevelEntityFromID("padl.example.manyClassesInOneFile.SecondClass");

		Assert.assertNotNull(clazz1);

		final IClass clazz2 =
			(IClass) model
				.getTopLevelEntityFromID("padl.example.manyClassesInOneFile.ThirdClass");

		Assert.assertNotNull(clazz2);

		final IClass clazz3 =
			(IClass) model
				.getTopLevelEntityFromID("padl.example.manyClassesInOneFile.FirstClass");

		Assert.assertNull(clazz3);

		//model.walk(new PadlPrinterVisitor());

	}
}
