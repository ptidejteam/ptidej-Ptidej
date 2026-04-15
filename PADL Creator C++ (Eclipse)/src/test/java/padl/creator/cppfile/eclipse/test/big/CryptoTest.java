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

public class CryptoTest extends TestCase {
	public CryptoTest(String name) {
		super(name);
	}

	public void test1() {
		final ICodeLevelModel codeLevelModel = ModelGenerator
				.generateModelFromCppTestResources("Crypto",
						"crypto/");
		Assert.assertNotNull("The code-level model is null!", codeLevelModel);
		Assert.assertTrue(
				"Expected at least a minimal non-empty model for crypto input.",
				codeLevelModel.getNumberOfTopLevelEntities() >= 50);
	}
}
