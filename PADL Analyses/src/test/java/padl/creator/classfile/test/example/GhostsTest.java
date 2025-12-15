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
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IElement;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.util.Util;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/01/23
 */
public class GhostsTest extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public GhostsTest(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (GhostsTest.FirstClassEntities == null || GhostsTest.Elements == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel("Ghost");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile/target/test-classes/padl/example/ghost/Simple.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			GhostsTest.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			GhostsTest.Elements =
				Util.getArrayOfElements(GhostsTest.FirstClassEntities[0]);
		}
	}
	public void testGhost() {
		/*
		 * public synchronized class padl.example.ghost.Simple extends java.lang.Object
		 * public ghost java.lang.Object;
		 * public ghost padl.example.ghost.A;
		 * public ghost java.lang.String;
		 * public ghost padl.example.ghost.B;
		 * public ghost java.lang.System;
		 * public ghost java.io.PrintStream;
		 */
		Assert.assertEquals(
			"Number of ghosts",
			9,
			GhostsTest.FirstClassEntities.length);
		Assert.assertEquals(
			"public /*super*/ synchronized class Simple extends Object",
			GhostsTest.FirstClassEntities[8].toString());
		Assert.assertEquals(
			"public ghost Object;",
			GhostsTest.FirstClassEntities[2].toString());
		Assert.assertEquals(
			"public ghost A;",
			GhostsTest.FirstClassEntities[5].toString());
		Assert.assertEquals(
			"public ghost String;",
			GhostsTest.FirstClassEntities[3].toString());
		Assert.assertEquals(
			"public ghost B;",
			GhostsTest.FirstClassEntities[6].toString());
		Assert.assertEquals(
			"public ghost Class;",
			GhostsTest.FirstClassEntities[1].toString());
		Assert.assertEquals(
			"public ghost System;",
			GhostsTest.FirstClassEntities[4].toString());
		Assert.assertEquals(
			"public ghost PrintStream;",
			GhostsTest.FirstClassEntities[0].toString());
	}
}
