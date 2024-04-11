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
import padl.kernel.IConstructor;
import padl.kernel.IPackage;
import padl.kernel.IParameter;
import padl.kernel.impl.Factory;
import util.io.Files;

public class ParametrizedTypesTest extends TestCase {

	public ParametrizedTypesTest(final String name) {
		super(name);

	}

	public void testParametrizedType() {
		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse)/target/test-classes//PADL testdata/padl/example/collection/" };
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
				.getTopLevelEntityFromID("padl.example.collection.CollectionExamples");

		Assert.assertNotNull(clazz);
		final IConstructor constructor =
			(IConstructor) clazz.getConstituentFromName("CollectionExamples");
		Assert.assertNotNull(constructor);

		final IParameter p =
			(IParameter) constructor.getConstituentFromName("l");
		Assert.assertNotNull(p);

		final IPackage packaje1 = (IPackage) model.getConstituentFromID("java");
		final IPackage packaje2 =
			(IPackage) packaje1.getConstituentFromID("util");
		final IPackage packaje3 =
			(IPackage) model.getConstituentFromID("java.util");

		Assert.assertNotNull(packaje1);
		Assert.assertNotNull(packaje2);
		Assert.assertNull(packaje3);

		Assert.assertEquals("java.util.List", p.getDisplayTypeName());

	}

}
