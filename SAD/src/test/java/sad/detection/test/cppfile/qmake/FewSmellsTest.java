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
package sad.detection.test.cppfile.qmake;

import java.io.PrintWriter;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.AntiSingleton.AntiSingletonDetection;
import sad.designsmell.detection.repository.BaseClassShouldBeAbstract.BaseClassShouldBeAbstractDetection;
import sad.designsmell.detection.repository.LargeClass.LargeClassDetection;
import sad.designsmell.detection.repository.LazyClass.LazyClassDetection;
import sad.designsmell.detection.repository.LongMethod.LongMethodDetection;
import sad.designsmell.detection.repository.LongParameterList.LongParameterListDetection;
import sad.designsmell.detection.repository.ManyFieldAttributesButNotComplex.ManyFieldAttributesButNotComplexDetection;
import sad.designsmell.detection.repository.MessageChains.MessageChainsDetection;
import sad.designsmell.detection.repository.RefusedParentBequest.RefusedParentBequestDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import util.io.ProxyDisk;

public class FewSmellsTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	private static final String NAME = "QMake";
	private static final String PATH = "../SAD Tests/data/QMake/";

	public FewSmellsTest(final String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
		if (FewSmellsTest.IdiomLevelModel == null) {
			FewSmellsTest.IdiomLevelModel =
				ModelGenerator.generateModelFromCppFilesUsingEclipse(
					FewSmellsTest.NAME,
					FewSmellsTest.PATH);
		}
	}
	public void testAntiSingletonDetection() {
		final IDesignSmellDetection ad = new AntiSingletonDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_AntiSingletonDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of anti-singletons found",
			0,
			ad.getDesignSmells().size());
	}
	public void testBaseClassShouldBeAbstractDetection() {
		final IDesignSmellDetection ad =
			new BaseClassShouldBeAbstractDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_BaseClassShouldBeAbstractDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of base-classes that should be abstract found",
			1,
			ad.getDesignSmells().size());
	}
	public void testLargeClassDetection() {
		final IDesignSmellDetection ad = new LargeClassDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_LargeClassDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of large classes found",
			0,
			ad.getDesignSmells().size());
	}
	public void testLazyClassDetection() {
		final IDesignSmellDetection ad = new LazyClassDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_LazyClassDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of lazy classes found",
			0,
			ad.getDesignSmells().size());
	}
	public void testLongMethod() {
		final IDesignSmellDetection ad = new LongMethodDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_LongMethodDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of long methods found",
			7,
			ad.getDesignSmells().size());
	}
	public void testLongParameterListDetection() {
		final IDesignSmellDetection ad = new LongParameterListDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_LongParameterListDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of long parameter lists found",
			6,
			ad.getDesignSmells().size());
	}
	public void testManyFieldAttributesButNotComplexDetection() {
		final IDesignSmellDetection ad =
			new ManyFieldAttributesButNotComplexDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_ManyFieldAttributesButNotComplexDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of classes with many fields but not complex found",
			4,
			ad.getDesignSmells().size());
	}
	public void testMessageChainsDetection() {
		final IDesignSmellDetection ad = new MessageChainsDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_MessageChainsDetection.ini")));
		Assert.assertEquals(
			"Incorrect number of message chains found",
			0,
			ad.getDesignSmells().size());
	}
	public void testRefusedParentBequestDetection() {
		final IDesignSmellDetection ad = new RefusedParentBequestDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME
							+ "_RefusedParentBequestDetection.ini")));
		// TODO Subsequent runs give different number: 2, 3, or 4! Why is that?!
		Assert.assertTrue(
			"Incorrect number of refused parent bequests found",
			ad.getDesignSmells().size() > 1);
	}
	public void testSpaghettiDetection() {
		final IDesignSmellDetection ad = new SpaghettiCodeDetection();
		ad.detect(FewSmellsTest.IdiomLevelModel);
		ad.output(
			new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(
					FewSmellsTest.NAME + "_SpaghettiCode.ini")));
		Assert.assertEquals(
			"Incorrect number of spaghetti code found",
			0,
			ad.getDesignSmells().size());
	}
}
