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

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import sad.designsmell.detection.IDesignSmellDetection;
import sad.designsmell.detection.repository.SpaghettiCode.SpaghettiCodeDetection;
import util.io.ProxyDisk;

/**
 * @author Yann
 * @since 2010/02/10
 */
public final class SpaghettiCodeTest2 extends TestCase {
	private static IIdiomLevelModel IdiomLevelModel;
	private static final String NAME = "Xercesv2.7.0.jar";
	private static final String PATH = "../SAD/target/test-classes/Xercesv2.7.0.jar";

	public SpaghettiCodeTest2(final String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		if (SpaghettiCodeTest2.IdiomLevelModel == null) {
			SpaghettiCodeTest2.IdiomLevelModel = ModelGenerator
					.generateModelFromClassFilesDirectories(
							new String[] { SpaghettiCodeTest2.PATH });
		}
	}

	public void testSpaghettiCode() throws IOException {
		final IDesignSmellDetection ad = new SpaghettiCodeDetection();
		ad.detect(SpaghettiCodeTest2.IdiomLevelModel);
		ad.output(new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
				SpaghettiCodeTest2.NAME + "_SpaghettiCode.ini")));
		Assert.assertEquals("Incorrect number of spaghetti code found", 1,
				ad.getDesignSmells().size());
	}
}
