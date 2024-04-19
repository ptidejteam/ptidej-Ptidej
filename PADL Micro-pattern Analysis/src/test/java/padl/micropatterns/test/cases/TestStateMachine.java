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

/**
 * @author tanterij
 */
public class TestStateMachine extends TestCase {

	private static MicroPatternDetector detector;
	private MicroPatternDetector currentDetector;

	public TestStateMachine(String arg0) {
		super(arg0);
	}

	protected void setUp() {

		if (TestStateMachine.detector == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.innerclasses");
			try {
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../PADL Micro-pattern Analysis/target/test-classes/padl/micropatterns/examples/StateMachine.class", }));
			}
			catch (Exception e) {
				// TODO: handle exception
			}

			this.currentDetector = new MicroPatternDetector(codeLevelModel);
		}
	}

	public void testStateMachine() {
		Assert.assertEquals(
			"State Machine",
			1,
			this.currentDetector.getNumberOfStateMachine());
	}
}
