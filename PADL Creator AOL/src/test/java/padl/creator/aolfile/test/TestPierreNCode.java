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
public class TestPierreNCode extends TestCase {
	private static ICodeLevelModel CodeLevelModel;
	private static IIdiomLevelModel IdiomLevelModel;

	public TestPierreNCode(final String name) {
		super(name);
	}
	public void setUp() {
		if (TestPierreNCode.CodeLevelModel == null) {
			TestPierreNCode.CodeLevelModel =
				Factory.getInstance().createCodeLevelModel("Test1");
			final AOLCreator aolCreator =
				new AOLCreator(
					new String[] { "../PADL Creator AOL Tests/rsc/Pierre-N/First/poly-class-code-concat_des_2010-01-29180648.aol" });
			aolCreator.create(TestPierreNCode.CodeLevelModel, true);
			try {
				TestPierreNCode.IdiomLevelModel =
					(IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(TestPierreNCode.CodeLevelModel);
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void testClasses() {
		Assert.assertEquals(
			"Number of classes",
			245,
			TestPierreNCode.IdiomLevelModel.getNumberOfTopLevelEntities());
	}
}
