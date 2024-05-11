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
import padl.creator.cppfile.eclipse.test.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;

public class Simple3Test extends TestCase {
	public Simple3Test(String name) {
		super(name);
	}

	public void test1() {
		final ICodeLevelModel codeLevelModel = ModelGenerator
				.generateModelFromCppFilesUsingEclipse("Funny",
						"../PADL Creator C++ (Eclipse) Tests/data/Simple3/");
		Assert.assertNotNull("The idiom-level model is null!", codeLevelModel);
		Assert.assertEquals(34, codeLevelModel.getNumberOfTopLevelEntities());
	}
}
