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
package padl.creator.javafile.eclipse.test.others;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IMethod;

public class CompleteCreatorTest extends TestCase {

	public CompleteCreatorTest(final String name) {
		super(name);
	}

	public void testCompleteCreatorTest1() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/clazz/" };
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		final IClass clazz =
			(IClass) model.getTopLevelEntityFromID("padl.example.clazz.TestB");
		Assert.assertNotNull(clazz);
		final IMethod m1 =
			(IMethod) clazz.getConstituentFromName("interfMethod1");
		Assert.assertNotNull(m1);
		final IMethod m2 =
			(IMethod) clazz.getConstituentFromName("interfMethod5");
		Assert.assertNull(m2);
	}

	public void testCompleteCreatorTest2() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/";

		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createCompleteJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry);
		final IClass clazz =
			(IClass) model.getTopLevelEntityFromID("padl.example.clazz.TestB");
		Assert.assertNotNull(clazz);
		final IMethod m1 =
			(IMethod) clazz.getConstituentFromName("interfMethod1");
		Assert.assertNotNull(m1);
		final IMethod m2 =
			(IMethod) clazz.getConstituentFromName("interfMethod5");
		Assert.assertNull(m2);

		final IClass clazz2 =
			(IClass) model.getTopLevelEntityFromID("padl.example.clazz2.Test");
		Assert.assertNotNull(clazz2);

	}
}
