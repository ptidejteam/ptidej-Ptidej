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

public class LightModelCreatorTest extends TestCase {
	public LightModelCreatorTest(final String aName) {
		super(aName);
	}

	//creation from some java files list
	public void testCreateModelFromJavaFilesList() {

		final String classPathEntry = "";
		final String sourceCodePath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/";

		final String[] listOfFiles =
			new String[] {
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/example/ghost/member1/MemberGhostExample.java",
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/example/ghost/member2/",
				"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/example/ghost/member3/MemberGhostExample.java" };

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourceCodePath,
				classPathEntry,
				listOfFiles);

		final int classesNumberExpected = 4;
		final int classesNumber =
			model.getNumberOfTopLevelEntities(IClass.class);

		Assert.assertEquals(classesNumberExpected, classesNumber);
	}

	//creation from all the source code
	public void testCreateModelFromJavaSourceCode() {

		final String classPathEntry = "";
		final String sourceCodePath =
			"../PADL Creator JavaFile (Eclipse)/target/test-classes/PADL testdata/padl/";

		final ICodeLevelModel model =
			Utils.createLightJavaFilesPadlModel(
				"",
				sourceCodePath,
				classPathEntry);

		final int minimalClassesNumberExpected = 5;
		final int classesNumber =
			model.getNumberOfTopLevelEntities(IClass.class);

		Assert.assertTrue(minimalClassesNumberExpected < classesNumber);

	}
}
