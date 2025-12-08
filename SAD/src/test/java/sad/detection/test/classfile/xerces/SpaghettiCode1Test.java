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
import util.io.ProxyDisk;

/**
 * @author Naouel Moha
 * @since 2005/12/04
 */
public final class SpaghettiCode1Test extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	private static final String NAME = "Xercesv2.7.0.jar";
	private static final String PATH = "../SAD/target/test-classes/Xercesv2.7.0.jar";

	public SpaghettiCode1Test(final String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (SpaghettiCode1Test.IdiomLevelModel == null) {
			SpaghettiCode1Test.IdiomLevelModel = ModelGenerator
					.generateModelFromClassFilesDirectories(
							new String[] { SpaghettiCode1Test.PATH });
		}
	}

	public void testLongMethod() {
		final ICodeSmellDetection ad = new LongMethodDetection();
		ad.detect(SpaghettiCode1Test.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput(SpaghettiCode1Test.NAME + "_LongMethod.ini")));
		Assert.assertEquals("Incorrect number of long methods found", 67,
				ad.getCodeSmells().size());
	}

	public void testMethodWithNoParameter() {
		final ICodeSmellDetection ad = new MethodNoParameterDetection();
		ad.detect(SpaghettiCode1Test.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				SpaghettiCode1Test.NAME + "_MethodWithNoParameter.ini")));
		Assert.assertEquals(
				"Incorrect number of methods with no parameter found", 520,
				ad.getCodeSmells().size());
	}

	public void testSpaghettiCode() {
		final IDesignSmellDetection ad = new SpaghettiCodeDetection();
		ad.detect(SpaghettiCode1Test.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				SpaghettiCode1Test.NAME + "_SpaghettiCode.ini")));
		Assert.assertEquals("Incorrect number of spaghetti code found", 1,
				ad.getDesignSmells().size());
	}
}
