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
package padl.creator.classfile.test.example;

import org.junit.Assert;

import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author 	Yann-Gaël Guéhéneuc
 * @since	2004/02/09
 */
public class MethodsTest extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public MethodsTest(final String name) {
		super(name);
	}

	protected void setUp() throws CreationException {
		if (MethodsTest.FirstClassEntities == null
				|| MethodsTest.Elements == null) {
			final ICodeLevelModel codeLevelModel = ClassFilePrimitive
					.getFactory().createCodeLevelModel("ptidej.example.method");
			codeLevelModel.create(new CompleteClassFileCreator(new String[] {
					"../PADL Creator ClassFile/target/test-classes/padl/example/method/ManyMethods.class" }));

			MethodsTest.FirstClassEntities = Util
					.getArrayOfTopLevelEntities(codeLevelModel);

			MethodsTest.Elements = Util
					.getArrayOfElements(MethodsTest.FirstClassEntities[2]);
		}
	}

	public void testNumberOfMethods() {
		Assert.assertEquals(5, MethodsTest.Elements.length);
	}
}
