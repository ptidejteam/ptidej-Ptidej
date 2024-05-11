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
package padl.creator.classfile.test.example;

import junit.framework.Test;
import junit.framework.TestSuite;

public final class TestClassFileCompleteCreator extends TestSuite {
	public TestClassFileCompleteCreator() {
	}
	public TestClassFileCompleteCreator(final Class theClass) {
		super(theClass);
	}
	public TestClassFileCompleteCreator(final String name) {
		super(name);
	}
	public static Test suite() {
		final TestClassFileCompleteCreator suite =
			new TestClassFileCompleteCreator();
		suite.addTest(new TestClassFileCompleteCreator(Aggregation1.class));
		suite.addTest(new TestClassFileCompleteCreator(Aggregation10.class));
		suite.addTest(new TestClassFileCompleteCreator(Aggregation11.class));
		suite.addTest(new TestClassFileCompleteCreator(Aggregation13.class));
		suite.addTest(new TestClassFileCompleteCreator(Aggregations.class));
		suite.addTest(new TestClassFileCompleteCreator(
			Aggregation_CLASS_INSTANCE_FROM_FIELD_1.class));
		suite.addTest(new TestClassFileCompleteCreator(
			Association_INSTANCE_INSTANCE_2.class));
		suite.addTest(new TestClassFileCompleteCreator(ChainOfMessages.class));
		suite.addTest(new TestClassFileCompleteCreator(Composite1.class));
		suite.addTest(new TestClassFileCompleteCreator(
			Composite4AbstractDocument.class));
		suite.addTest(new TestClassFileCompleteCreator(
			CreationLink_INSTANCE_CREATION_1.class));
		suite.addTest(new TestClassFileCompleteCreator(
			CreationLink_INSTANCE_CREATION_3.class));
		suite.addTest(new TestClassFileCompleteCreator(Interfaces.class));
		suite.addTest(new TestClassFileCompleteCreator(
			UseRelationship_CLASS_CLASS_1.class));
		suite.addTest(new TestClassFileCompleteCreator(
			UseRelationship_CLASS_CLASS_3.class));
		suite.addTest(new TestClassFileCompleteCreator(Relationships.class));
		suite.addTest(new TestClassFileCompleteCreator(
			SuperEntitiesConnection.class));
		suite.addTest(new TestClassFileCompleteCreator(Ghost.class));
		// TODO Add these tests back
		//		suite.addTest(new TestClassFileCompleteCreator(TestDup.class));
		//		suite.addTest(new TestClassFileCompleteCreator(TestNew.class));
		return suite;
	}
}
