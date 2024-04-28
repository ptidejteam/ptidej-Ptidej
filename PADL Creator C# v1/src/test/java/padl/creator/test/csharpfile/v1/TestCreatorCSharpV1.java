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
package padl.creator.test.csharpfile.v1;

import java.io.IOException;

import org.antlr.runtime.RecognitionException;

import junit.framework.TestCase;
import padl.creator.csharpfile.v1.CSharpCreator;
import padl.csharp.kernel.impl.CSharpFactory;
import padl.kernel.IClass;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;

/**
 * TestCase for our CSharp to PADL parser/converter
 */
public class TestCreatorCSharpV1 extends TestCase {
	public TestCreatorCSharpV1(final String aName) {
		super(aName);
	}

	public void testParser()
			throws IOException, RecognitionException, CreationException {

		final ICodeLevelModel model = CSharpFactory.getInstance()
				.createCodeLevelModel("Test".toCharArray());
		final CSharpCreator creator = new CSharpCreator(new String[] {
				"../PADL Creator C# v1/target/test-classes/parser_oracles/drawdemo.cs",
				"../PADL Creator C# v1/target/test-classes/parser_oracles/drawingobject.cs",
				"../PADL Creator C# v1/target/test-classes/parser_oracles/interface_impl.cs",
				"../PADL Creator C# v1/target/test-classes/parser_oracles/interface.cs",
				"../PADL Creator C# v1/target/test-classes/parser_oracles/lcs.cs",
				"../PADL Creator C# v1/target/test-classes/parser_oracles/mystring.cs",
				"../PADL Creator C# v1/target/test-classes/parser_oracles/parameters.cs" });
		creator.create(model);

		// make sure we got our right number of classes
		assertEquals(10, model.getNumberOfConstituents());

		// make sure we got the 'Line' class
		assertNotNull(model.getConstituentFromName("Line"));
		// make sure the superclass was found
		// TODO Add inherited entity!
		//	assertNotNull(((IClass) model.getConstituentFromName("Line"))
		//		.getInheritedEntity("DrawingObject".toCharArray()));

		// make sure we got the interface
		assertNotNull(model.getConstituentFromName("IMyInterface"));
		// make sure the implementation was found
		// TODO Add inherited entity!
		//	assertNotNull(((IClass) model
		//		.getConstituentFromName("InterfaceImplementer"))
		//		.getImplementedInterface("IMyInterface".toCharArray()));

		// make sure we got the class member of Outputclass
		assertTrue(((IClass) model.getConstituentFromName("OutputClass"))
				.doesContainConstituentWithName("myString".toCharArray()));

		// make sure we got the method and parameter 'myChoice' of method makeDecision
		assertTrue(((IClass) model.getConstituentFromName("MethodParams"))
				.doesContainConstituentWithName("address".toCharArray()));
		// TODO Add elements properly!
		//	assertNotNull(((IMethod) ((IClass) model
		//		.getConstituentFromName("MethodParams"))
		//		.getConstituentFromName("makedecision"))
		//		.getConstituentFromName("myChoice"));

	}

}
