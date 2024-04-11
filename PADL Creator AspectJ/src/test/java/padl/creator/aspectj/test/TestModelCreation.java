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

import java.util.Iterator;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.aspectj.kernel.IAspect;
import padl.aspectj.kernel.IAspectJFactory;
import padl.aspectj.kernel.exception.AspectCreationException;
import padl.aspectj.kernel.impl.AspectJFactory;
import padl.creator.aspectjlst.AspectCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituent;
import padl.kernel.IContainer;
import padl.kernel.IFactory;
import util.io.ProxyConsole;

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

	private void iterate(final IContainer container, int indent) {
		//Iterator iterate = container.listOfConstituents().iterator();
		@SuppressWarnings("unchecked")
		final Iterator<IConstituent> iterate = container
				.getIteratorOnConstituents();

		while (iterate.hasNext()) {
			IConstituent c = iterate.next();
			if (c instanceof IAspect) {
				// this.indent(indent);
				ProxyConsole.getInstance().debugOutput()
						.println("Aspect: " + c.getDisplayName());
				//IAspect aspect = (IAspect) c;
				//this.iterate(aspect, indent + 1);
			}
			else {
				ProxyConsole.getInstance().debugOutput()
						.println("Non-AJ: " + c.getDisplayName());
			}
			//	if (c instanceof IAspectElement) {
			//		this.indent(indent);
			//		System.out.println("AElement: " + c.getDisplayName());
			//	}
			//	else {
			//		this.indent(indent);
			//		System.out.println(c.getClass().getName() + ": " + c.getDisplayName());
			//	}

		}
	}

	protected void setUp() throws AspectCreationException {
		final String lst = "../PADL Creator AspectJ/target/test-classes/examples/AJ1.2Example/observer/files.lst";
		final AspectCreator ac = new AspectCreator(new String[] { lst });

		this.codeLevelModel = AspectJFactory.getInstance()
				.createCodeLevelModel(this.getName());

		ac.create(this.codeLevelModel);

		this.iterate(this.codeLevelModel, 0);
	}

	public void testCreation() {
		Assert.assertEquals("CLM Creation", true,
				(this.getCodeLevelModel() != null));
	}
}
