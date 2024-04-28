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
package padl.creator.classfile.test.inheritance;

import org.junit.Assert;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

public final class Inheritance extends ClassFilePrimitive {
	private ICodeLevelModel codeLevelModel;

	public Inheritance(String name) {
		super(name);
	}
	protected void setUp() throws CreationException {
		this.codeLevelModel =
			Factory.getInstance().createCodeLevelModel("Inheritance");

		this.codeLevelModel
			.create(new CompleteClassFileCreator(
				new String[] { "../PADL Creator ClassFile/target/test-classes/Inheritance/jdiui.jar" }));
	}

	public void testFieldAccess() {
		final IFirstClassEntity entity =
			(IFirstClassEntity) this.codeLevelModel
				.getTopLevelEntityFromID("org.eclipse.jdt.internal.debug.ui.snippeteditor.ScrapbookMain");
		Assert.assertTrue(entity.getIteratorOnInheritedEntities().hasNext());
	}
}
