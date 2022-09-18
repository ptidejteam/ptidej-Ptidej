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
package padl.analysis.systematicuml.test;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.SystematicUMLAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/11/11
 */
public class Test1 extends TestCase {
	private static ICodeLevelModel OriginalCodeLevelModel;
	private static IAbstractModel ResultingAbstractModel;

	public Test1(final String testName) {
		super(testName);
	}
	protected void setUp() {
		if (Test1.ResultingAbstractModel == null) {
			Test1.OriginalCodeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"SystematicUML Test 1");
			try {
				Test1.OriginalCodeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../PADL Analyses Tests/bin/padl/analysis/systematicuml/data/" }));
				Test1.ResultingAbstractModel =
					new SystematicUMLAnalysis()
						.invoke(Test1.OriginalCodeLevelModel);
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void testNumberOfEntities() {
		Assert.assertEquals(
			"Number of entities",
			Test1.OriginalCodeLevelModel.getNumberOfTopLevelEntities(),
			Test1.ResultingAbstractModel.getNumberOfTopLevelEntities());
	}
	public void testEnumeration() {
		Assert
			.assertEquals(
				"ClassH_Enumeration",
				"<<enumeration>>\npadl.analysis.systematicuml.data.ClassH_Enumeration",
				Test1.ResultingAbstractModel
					.getTopLevelEntityFromID(
						"padl.analysis.systematicuml.data.ClassH_Enumeration")
					.getDisplayName());
	}
}
