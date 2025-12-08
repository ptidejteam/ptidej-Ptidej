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
package padl.micropatterns.test.cases;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import padl.micropatterns.helper.MicroPatternDetector;
import util.io.ProxyConsole;
import padl.kernel.exception.CreationException;
/**
 * @author tanterij
 */
public class SamplerTest extends TestCase {

	private static MicroPatternDetector detector;
	private MicroPatternDetector currentDetector;

	public SamplerTest(String arg0) {
		super(arg0);
	}

	protected void setUp() {

		if (SamplerTest.detector == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.innerclasses");
			try {
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../PADL Micro-pattern Analysis/target/test-classes/padl/micropatterns/examples/Sampler.class", }));
			}
			catch (CreationException e) {
				// TODO: handle exception
				// Added already created CreationException from padl.kernel.exception.CreationException;
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				}

			this.currentDetector = new MicroPatternDetector(codeLevelModel);
		}
	}

	public void testSampler() {
		Assert.assertEquals(
			"Sampler",
			1,
			this.currentDetector.getNumberOfSampler());
	}
}
