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
package padl.creator.cppfile.eclipse.test.big;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.cppfile.eclipse.test.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;

public class ChromeTest extends TestCase {
	public ChromeTest(String name) {
		super(name);
	}

	public void test1() {
		final ICodeLevelModel codeLevelModel = ModelGenerator
				.generateModelFromCppFilesUsingEclipse("ChromeTest",
						"../PADL Creator C++ (Eclipse) Tests/data/Chrome v1.0.154.53/");
		Assert.assertNotNull("The code-level model is null!", codeLevelModel);
		Assert.assertTrue(codeLevelModel.getNumberOfTopLevelEntities() > 5000);
	}
}
