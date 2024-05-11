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
import padl.creator.cppfile.eclipse.test.helper.ModelGenerator;
import padl.kernel.ICodeLevelModel;

public class TypeNameQualifiers extends TestCase {
	private static ICodeLevelModel CodeLevelModel;

	public TypeNameQualifiers(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (TypeNameQualifiers.CodeLevelModel == null) {
			TypeNameQualifiers.CodeLevelModel = ModelGenerator
					.generateModelFromCppFilesUsingEclipse("Funny",
							"../PADL Creator C++ (Eclipse) Tests/data/TypeNameQualifiers/");
		}
	}

	public void testNumberOfTopLevelEntities() {
		Assert.assertNotNull("The idiom-level model is null!",
				TypeNameQualifiers.CodeLevelModel);
		Assert.assertEquals(5, TypeNameQualifiers.CodeLevelModel
				.getNumberOfTopLevelEntities());
	}

	public void testStructure() {
		final IGlobalFunction function = (IGlobalFunction) TypeNameQualifiers.CodeLevelModel
				.getTopLevelEntityFromID(
						"js_TraceRuntime(ProblemType *, ProblemType)");
		Assert.assertNotNull("No global function!?", function);
	}
}
