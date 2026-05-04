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
package padl.test.visitors;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.analysis.plantUMLGenerator.PlantUMLGenerator;
import padl.analysis.selective.ServiceClassFilter;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import padl.visitor.BFVisitor;
import padl.visitor.FilteringVisitor;
import padl.visitor.IGenerator;

public class BFVisitorTest extends TestCase {
	private static final String PATH = "../PADL Analyses/target/test-classes/padl/test/";
	private ICodeLevelModel codeLevelModel;
	private IGenerator selectiveVisitor;
	private IGenerator bfVisitor;

	public BFVisitorTest(final String aName) {
		super(aName);
	}

	public void setUp() throws Exception {
		super.setUp();

		this.codeLevelModel = Factory.getInstance()
				.createCodeLevelModel("Model for BFVisitorTest");
		this.codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { BFVisitorTest.PATH }, true));

		final PlantUMLGenerator generator = new PlantUMLGenerator();

		this.selectiveVisitor = new FilteringVisitor(generator,
				new ServiceClassFilter());
		this.bfVisitor = new BFVisitor(generator);
	}

	public void testSelectiveVisit() {
		this.codeLevelModel.generate(this.selectiveVisitor);
		Assert.assertEquals(
				"\n@startuml\n\nclass CloneTest {\n\n}\n\nclass CopyInTest {\n\n}\n\nclass RemoveTest {\n\n}\n\nclass ListenersTest {\n\n}\n\nclass SanityTest {\n\n}\n\nclass ConstituentRemoveTest {\n\n}\n\nclass RemoveAndIteratorTest {\n\n}\n\nclass RemoveTest {\n\n}\n\nclass GetterTest {\n\n}\n\nclass SetterTest {\n\n}\n\nclass BFVisitorTest {\n\n}\n\n\nTestCase --^ CloneTest\nTestCase --^ CopyInTest\nTestCase --^ RemoveTest\nTestCase --^ ListenersTest\nTestCase --^ SanityTest\nTestCase --^ ConstituentRemoveTest\nTestCase --^ RemoveAndIteratorTest\nTestCase --^ RemoveTest\nTestCase --^ GetterTest\nTestCase --^ SetterTest\nTestCase --^ BFVisitorTest\n@enduml",
				this.selectiveVisitor.getCode());
	}

	public void testBFVisit() {
		// TODO Is this test correct? The PlantUML file doesn't seem properly terminated
		this.codeLevelModel.generate(this.bfVisitor);
		Assert.assertEquals(
				"\n@startuml\n\nclass CloneTest {\n\n}\n\nclass CopyInTest {\n\n}\n\nclass RemoveTest {\n\n}\n\nclass ListenersTest {\n\n}\n\nclass SanityTest {\n\n}\n\nclass ConstituentRemoveTest {\n\n}\n\nclass RemoveAndIteratorTest {\n\n}\n\nclass RemoveTest {\n\n}\n\nclass GetterTest {\n\n}\n\nclass SetterTest {\n\n}\n\nclass BFVisitorTest {\n\n}\n",
				this.bfVisitor.getCode());
	}
}
