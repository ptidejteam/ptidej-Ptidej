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
import padl.cpp.kernel.IGlobalFunction;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;

public class TypeNameQualifiers extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	public TypeNameQualifiers(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		if (TypeNameQualifiers.IdiomLevelModel == null) {
			TypeNameQualifiers.IdiomLevelModel =
				ModelGenerator
					.generateModelFromCppFilesUsingEclipse(
						"Funny",
						new String[] { "../PADL Creator C++ (Eclipse) Tests/data/TypeNameQualifiers/" });
		}
	}
	public void testNumberOfTopLevelEntities() {
		Assert.assertNotNull(
			"The idiom-level model is null!",
			TypeNameQualifiers.IdiomLevelModel);
		Assert.assertEquals(
			5,
			TypeNameQualifiers.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
	public void testStructure() {
		final IGlobalFunction function =
			(IGlobalFunction) TypeNameQualifiers.IdiomLevelModel
				.getTopLevelEntityFromID("js_TraceRuntime(ProblemType *, ProblemType)");
		Assert.assertNotNull("No global function!?", function);
	}
}
