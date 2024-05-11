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
package padl.creator.aspectj.test;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.aspectj.kernel.IAspectJFactory;
import padl.aspectj.kernel.exception.AspectCreationException;
import padl.aspectj.kernel.impl.AspectJFactory;
import padl.creator.aspectjlst.AspectCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFactory;

public class TestModelCreation extends TestCase {
	private static IAspectJFactory factory = (IAspectJFactory) AspectJFactory
			.getInstance();

	public static IFactory getFactory() {
		return TestModelCreation.factory;
	}

	public static void setFactory(final IAspectJFactory aFactory) {
		TestModelCreation.factory = aFactory;
	}

	private ICodeLevelModel codeLevelModel;

	//	private void indent(int nb) {
	//		for(int i = 0; i < nb; i++){
	//			System.out.print("\t");
	//		}
	//	}

	public TestModelCreation(String aName) {
		super(aName);
	}

	public ICodeLevelModel getCodeLevelModel() {
		return this.codeLevelModel;
	}

	protected void setUp() throws AspectCreationException {
		final String lst = "../PADL Creator AspectJ/target/test-classes/examples/AJ1.2Example/observer/files.lst";
		final AspectCreator ac = new AspectCreator(new String[] { lst });

		this.codeLevelModel = AspectJFactory.getInstance()
				.createCodeLevelModel(this.getName());

		ac.create(this.codeLevelModel);
	}

	public void testCreation() {
		Assert.assertEquals("CLM Creation", true,
				(this.getCodeLevelModel() != null));
	}
}
