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
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.analysis.repository.SystematicUMLAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/11/11
 */
public class SanityTest extends TestCase {
	private static ICodeLevelModel OriginalCodeLevelModel;
	private static IIdiomLevelModel OriginalIdiomLevelModel;
	private static IAbstractModel ResultingAbstractModel;

	public SanityTest(final String testName) {
		super(testName);
	}

	protected void setUp() {
		if (SanityTest.ResultingAbstractModel == null) {
			SanityTest.OriginalCodeLevelModel = Factory.getInstance()
					.createCodeLevelModel("SystematicUML Test 1");
			try {
				SanityTest.OriginalCodeLevelModel
						.create(new CompleteClassFileCreator(new String[] {
								"../PADL Analyses/target/test-classes/padl/analysis/systematicuml/data/" }));
				SanityTest.OriginalIdiomLevelModel = (IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(SanityTest.OriginalCodeLevelModel);
				SanityTest.ResultingAbstractModel = new SystematicUMLAnalysis()
						.invoke(SanityTest.OriginalIdiomLevelModel);
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
		Assert.assertEquals("Number of entities",
				SanityTest.OriginalCodeLevelModel.getNumberOfTopLevelEntities(),
				SanityTest.ResultingAbstractModel
						.getNumberOfTopLevelEntities());
	}

	public void testEnumeration() {
		final IFirstClassEntity entity = SanityTest.ResultingAbstractModel
				.getTopLevelEntityFromID(
						"padl.analysis.systematicuml.data.ClassH_Enumeration");
		Assert.assertEquals("ClassH_Enumeration",
				"<<enumeration>>\nClassH_Enumeration", entity.getDisplayName());
	}
}
