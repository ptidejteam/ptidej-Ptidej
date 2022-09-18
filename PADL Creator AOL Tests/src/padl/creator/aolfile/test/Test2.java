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
package padl.creator.aolfile.test;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.aolfile.AOLCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;


/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2006/11/28
 */
public class Test2 extends TestCase {
	private static ICodeLevelModel CodeLevelModel;
	private static IIdiomLevelModel IdiomLevelModel;

	public Test2(final String name) {
		super(name);
	}
	public void setUp() {
		if (Test2.CodeLevelModel == null) {
			Test2.CodeLevelModel =
				Factory.getInstance().createCodeLevelModel("Test1");
			final AOLCreator aolCreator =
				new AOLCreator(
					new String[] { "../PADL Creator AOL Tests/rsc/Unit Tests/Test2.aol" });
			aolCreator.create(Test2.CodeLevelModel, true);
			try {
				Test2.IdiomLevelModel =
					(IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(Test2.CodeLevelModel);
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void testClasses() {
		Assert.assertEquals("Number of classes", 3, Test2.IdiomLevelModel
			.getNumberOfTopLevelEntities());
	}
}
