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
package padl.generator.test.cases;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.generator.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;

public class SanityTest extends TestCase {
	public SanityTest(final String aName) {
		super(aName);
	}
	public void testGenerateModelFromAOLCodeFiles() {
		final ICodeLevelModel codeLevelModel =
			ModelGenerator
				.generateModelFromAOLCodeFiles(
					"Model",
					new String[] { "../PADL Generator Tests/rsc/test/AOL/Test1.aol" });
		Assert.assertEquals(
			"Number of classes",
			18,
			codeLevelModel.getNumberOfTopLevelEntities());
	}
	public void testGenerateModelFromCppFilesUsingEclipse() {
		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator.generateModelFromCppFilesUsingEclipse(
				"Model",
				new String[] { "../PADL Generator Tests/rsc/test/C++/" });
		Assert.assertEquals(
			"Number of classes",
			9,
			idiomLevelModel.getNumberOfTopLevelEntities());
	}
}
