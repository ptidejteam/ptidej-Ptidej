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
package padl.creator.classfile.test.fieldaccess;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.compare.Compare;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.Files;

public final class FieldAccess extends TestCase {
	private ICodeLevelModel codeLevelModel;

	public FieldAccess(String name) {
		super(name);
	}

	protected void setUp() throws CreationException {
		this.codeLevelModel = Factory.getInstance().createCodeLevelModel("FieldAccess");

		this.codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { "../PADL Creator ClassFile/target/test-classes/padl/example/fieldAccess/Test.class" }));
	}

	public void testFieldAccess() {
		final IFirstClassEntity entity = this.codeLevelModel.getTopLevelEntityFromID("padl.example.fieldaccess.Test");
		final IMethod method = (IMethod) entity.getConstituentFromID("rien()");
		Assert.assertTrue(method.doesContainConstituentWithID("Method Invocation_>PADL<_1".toCharArray()));
	}
}
