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
package padl.analysis.packagebuilder.test;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.PackageIdentifierAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IPackage;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.Util;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2005/10/07
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
					"PackageBuilder Test 1");
			try {
				Test1.OriginalCodeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "target/test-classes/padl/analysis/systematicuml/data/" }));
				Test1.ResultingAbstractModel =
					new PackageIdentifierAnalysis().invoke(Test1.OriginalCodeLevelModel);
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
	public void testNumberOfPackages() {
		Assert.assertEquals(
			"Number of packages",
			5,
			Test1.ResultingAbstractModel.getNumberOfConstituents());
	}
	public void testNumberOfJavaLangEntities() {
		Assert.assertEquals("Name of package", "java.lang", Util
			.getArrayOfConstituents(Test1.ResultingAbstractModel)[1]
			.getDisplayName());
		Assert.assertEquals("Number of java.lang entities", 5, ((IPackage) Util
			.getArrayOfConstituents(Test1.ResultingAbstractModel)[1])
			.getNumberOfConstituents());
	}
	public void testNumberOfJavaUtilEntities() {
		Assert.assertEquals("Name of package", "java.util", Util
			.getArrayOfConstituents(Test1.ResultingAbstractModel)[2]
			.getDisplayName());
		Assert.assertEquals("Number of java.util entities", 2, ((IPackage) Util
			.getArrayOfConstituents(Test1.ResultingAbstractModel)[2])
			.getNumberOfConstituents());
	}
	public void testNumberOfDataEntities() {
		Assert.assertEquals(
			"Name of package",
			"padl.analysis.systematicuml.data",
			Util.getArrayOfConstituents(Test1.ResultingAbstractModel)[4]
				.getDisplayName());
		Assert.assertEquals(
			"Number of padl.analysis.systematicuml.data entities",
			14,
			((IPackage) Util
				.getArrayOfConstituents(Test1.ResultingAbstractModel)[4])
				.getNumberOfConstituents());
	}
}
