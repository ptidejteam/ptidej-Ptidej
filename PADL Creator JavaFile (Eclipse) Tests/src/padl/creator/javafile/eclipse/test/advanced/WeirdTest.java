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
package padl.creator.javafile.eclipse.test.advanced;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.javafile.eclipse.test.util.Utils;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IPackage;
import padl.test.helper.ModelComparator;

public class WeirdTest extends TestCase {

	public WeirdTest(final String name) {
		super(name);

	}

	public void testWeirdClass() {

		final String sourcePath =
			"../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/";
		final String[] javaFiles =
			new String[] { "../PADL Creator JavaFile (Eclipse) Tests/rsc/PADL testdata/padl/example/weird/Weird.java" };
		final String classPathEntry = "";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourcePath,
				classPathEntry,
				javaFiles);
		final IFirstClassEntity clazz = model.getTopLevelEntityFromID("Weird");
		//	final IFirstClassEntity ghost =
		//		model.getTopLevelEntityFromID("java.lang.Object");
		final IPackage defaultPackage =
			(IPackage) model.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID);
		final IFirstClassEntity clazz1 =
			(IFirstClassEntity) defaultPackage.getConstituentFromID("Weird");
		Assert.assertTrue(clazz.equals(clazz1));
		Assert.assertEquals(3, model.getNumberOfTopLevelEntities());
		// Weird
		// java.lang.Object
		// java.lang.String
		Assert.assertEquals(3, clazz.getNumberOfConstituents());
		// Weird$IntHolder
		// main(java.lang.String[])
		// <init>()

		model.walk(new ModelComparator(model));
		// (Problem with the comparator,
		// I don't know why... the model seems correct) - resolve now; due to the analysis that delete methods without instructions

	}

}
