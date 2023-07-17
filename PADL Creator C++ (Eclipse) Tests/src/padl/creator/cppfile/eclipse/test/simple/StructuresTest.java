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
import padl.cpp.kernel.IStructure;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;

public class StructuresTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public StructuresTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		if (StructuresTest.IdiomLevelModel == null) {
			StructuresTest.IdiomLevelModel =
				ModelGenerator
					.generateModelFromCppFilesUsingEclipse(
						"Funny",
						new String[] { "../PADL Creator C++ (Eclipse) Tests/data/Structures/" });
		}
	}
	public void testNumberOfTopLevelEntities() {
		Assert.assertNotNull(
			"The idiom-level model is null!",
			StructuresTest.IdiomLevelModel);
		Assert.assertEquals(
			3,
			StructuresTest.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
	public void testStructure() {
		final ICPPClass clazz =
			(ICPPClass) StructuresTest.IdiomLevelModel
				.getTopLevelEntityFromID("SourceFiles");
		final IStructure structure =
			(IStructure) clazz
				.getConstituentFromID("SourceFiles$SourceFileNode");
		Assert.assertNotNull("No structure!?", structure);
	}
}
