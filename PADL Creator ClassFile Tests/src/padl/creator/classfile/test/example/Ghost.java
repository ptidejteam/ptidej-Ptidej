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
public class Ghost extends ClassFilePrimitive {
	private static IFirstClassEntity[] FirstClassEntities = null;
	private static IElement[] Elements = null;

	public Ghost(final String name) {
		super(name);
	}
	protected void setUp() throws CreationException,
			UnsupportedSourceModelException {

		if (Ghost.FirstClassEntities == null || Ghost.Elements == null) {
			final ICodeLevelModel codeLevelModel =
				ClassFilePrimitive.getFactory().createCodeLevelModel("Ghost");
			codeLevelModel
				.create(new CompleteClassFileCreator(
					new String[] { "../PADL Creator ClassFile Tests/bin/padl/example/ghost/Simple.class" }));

			final IIdiomLevelModel idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);

			Ghost.FirstClassEntities =
				Util.getArrayOfTopLevelEntities(idiomLevelModel);

			Ghost.Elements =
				Util.getArrayOfElements(Ghost.FirstClassEntities[0]);
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
			Ghost.FirstClassEntities.length);
		Assert.assertEquals(
			"public synchronized class Simple extends Object",
			Ghost.FirstClassEntities[8].toString());
		Assert.assertEquals(
			"public ghost Object;",
			Ghost.FirstClassEntities[2].toString());
		Assert.assertEquals(
			"public ghost A;",
			Ghost.FirstClassEntities[5].toString());
		Assert.assertEquals(
			"public ghost String;",
			Ghost.FirstClassEntities[3].toString());
		Assert.assertEquals(
			"public ghost B;",
			Ghost.FirstClassEntities[6].toString());
		Assert.assertEquals(
			"public ghost Class;",
			Ghost.FirstClassEntities[1].toString());
		Assert.assertEquals(
			"public ghost System;",
			Ghost.FirstClassEntities[4].toString());
		Assert.assertEquals(
			"public ghost PrintStream;",
			Ghost.FirstClassEntities[0].toString());
	}
}
