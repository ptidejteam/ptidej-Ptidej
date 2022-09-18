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
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;

public class GetOrCreateTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public GetOrCreateTest(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		if (GetOrCreateTest.IdiomLevelModel == null) {
			GetOrCreateTest.IdiomLevelModel =
				ModelGenerator
					.generateModelFromCppFilesUsingEclipse(
						"Funny",
						new String[] { "../PADL Creator C++ (Eclipse) Tests/data/getOrCreate/" });
		}
	}
	public void testNumberOfTopLevelEntities() {
		Assert.assertNotNull(
			"The idiom-level model is null!",
			GetOrCreateTest.IdiomLevelModel);
		Assert.assertEquals(
			21,
			GetOrCreateTest.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
}
