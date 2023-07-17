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
package padl.creator.cppfile.eclipse.test.simple;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.cpp.kernel.ICPPClass;
import padl.cpp.kernel.IGlobalFunction;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IGetter;
import padl.kernel.IIdiomLevelModel;

public class FieldAccessTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public FieldAccessTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		if (FieldAccessTest.IdiomLevelModel == null) {
			FieldAccessTest.IdiomLevelModel =
				ModelGenerator
					.generateModelFromCppFilesUsingEclipse(
						"Funny",
						new String[] { "../PADL Creator C++ (Eclipse) Tests/data/FieldAccess/" });
		}
	}
	public void testNumberOfTopLevelEntities() {
		Assert.assertNotNull(
			"The idiom-level model is null!",
			FieldAccessTest.IdiomLevelModel);
		Assert.assertEquals(
			9,
			FieldAccessTest.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
	public void testMainFunction() {
		final IGlobalFunction mainFunction =
			(IGlobalFunction) FieldAccessTest.IdiomLevelModel
				.getTopLevelEntityFromID("main()");
		Assert.assertNotNull("No main function!?", mainFunction);
		Assert.assertEquals(13, mainFunction.getNumberOfConstituents());
	}
	public void testGetter() {
		final ICPPClass clazz =
			(ICPPClass) FieldAccessTest.IdiomLevelModel
				.getTopLevelEntityFromID("FieldAccessTest");
		final IGetter getter =
			(IGetter) clazz.getConstituentFromID("getString()");
		Assert.assertNotNull("No getter!?", getter);
		Assert.assertEquals(1, getter.getNumberOfConstituents());
	}
}
