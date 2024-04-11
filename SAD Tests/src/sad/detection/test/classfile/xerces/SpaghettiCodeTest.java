/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package sad.detection.test.classfile.xerces;

import java.io.PrintWriter;
import org.junit.Assert;
import junit.framework.TestCase;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import sad.codesmell.detection.ICodeSmellDetection;
import sad.codesmell.detection.repository.SpaghettiCode.LongMethodDetection;
import sad.codesmell.detection.repository.SpaghettiCode.MethodNoParameterDetection;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import util.io.Files;
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since 2005/12/04
 */
public final class SpaghettiCodeTest extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	private static final String NAME = "Xercesv2.7.0.jar";
	private static final String PATH = "../SAD Tests/data/Xercesv2.7.0.jar";

	public SpaghettiCodeTest(final String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		if (SpaghettiCodeTest.IdiomLevelModel == null) {
			SpaghettiCodeTest.IdiomLevelModel =
				ModelGenerator
					.generateModelFromClassFilesDirectories(new String[] { SpaghettiCodeTest.PATH });
		}
	}
	public void testLongMethod() {
		final ICodeSmellDetection ad = new LongMethodDetection();
		ad.detect(SpaghettiCodeTest.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
			SpaghettiCodeTest.NAME + "_LongMethod.ini")));
		Assert.assertEquals("Incorrect number of long methods found", 67, ad
			.getCodeSmells()
			.size());
	}
	public void testMethodWithNoParameter() {
		final ICodeSmellDetection ad = new MethodNoParameterDetection();
		ad.detect(SpaghettiCodeTest.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
			SpaghettiCodeTest.NAME + "_MethodWithNoParameter.ini")));
		Assert.assertEquals(
			"Incorrect number of methods with no parameter found",
			520,
			ad.getCodeSmells().size());
	}
	public void testSpaghettiCode() {
		final IDesignSmellDetection ad = new SpaghettiCodeDetection();
		ad.detect(SpaghettiCodeTest.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
			SpaghettiCodeTest.NAME + "_SpaghettiCode.ini")));
		Assert.assertEquals("Incorrect number of spaghetti code found", 1, ad
			.getDesignSmells()
			.size());
	}
}
