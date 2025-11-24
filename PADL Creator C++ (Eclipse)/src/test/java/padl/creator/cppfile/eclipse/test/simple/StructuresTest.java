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
import padl.creator.cppfile.eclipse.test.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;

public class StructuresTest extends TestCase {
	private static ICodeLevelModel CodeLevelModel;

	public StructuresTest(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (StructuresTest.CodeLevelModel == null) {
			StructuresTest.CodeLevelModel = ModelGenerator
					.generateModelFromCppFilesUsingEclipse("Funny",
							"../PADL Creator C++ (Eclipse)/target/test-classes/Structures/");
		}
	}

	public void testNumberOfTopLevelEntities() {
		Assert.assertNotNull("The idiom-level model is null!",
				StructuresTest.CodeLevelModel);
		Assert.assertEquals(3,
				StructuresTest.CodeLevelModel.getNumberOfTopLevelEntities());
	}

	public void testStructure() {
		final ICPPClass clazz = (ICPPClass) StructuresTest.CodeLevelModel
				.getTopLevelEntityFromID("SourceFiles");
		final IStructure structure = (IStructure) clazz
				.getConstituentFromID("SourceFiles$SourceFileNode");
		Assert.assertNotNull("No structure!?", structure);
	}
}
