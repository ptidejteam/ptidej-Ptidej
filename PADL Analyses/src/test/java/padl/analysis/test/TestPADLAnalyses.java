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
package padl.analysis.test;

import junit.framework.Test;
import padl.analysis.aac.test.Aggregation_CLASS_CLASS_FROM_FIELD_1_Test;
import padl.analysis.aac.test.Aggregation_CLASS_INSTANCE_FROM_FIELD_1_Test;
import padl.analysis.aac.test.Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test;
import padl.creator.classfile.test.example.Aggregation1;
import padl.creator.classfile.test.example.Aggregation10;
import padl.creator.classfile.test.example.Aggregation11;
import padl.creator.classfile.test.example.Aggregation13;
import padl.creator.classfile.test.example.Aggregations;
import padl.creator.classfile.test.example.Association_INSTANCE_INSTANCE_2;
import padl.creator.classfile.test.example.ChainOfMessages;
import padl.creator.classfile.test.example.Composite4AbstractDocument;
import padl.creator.classfile.test.example.CreationLink_INSTANCE_CREATION_1;
import padl.creator.classfile.test.example.CreationLink_INSTANCE_CREATION_3;
import padl.creator.classfile.test.example.DupTest;
import padl.creator.classfile.test.example.Ghost;
import padl.creator.classfile.test.example.Interfaces;
import padl.creator.classfile.test.example.ManyMethods;
import padl.creator.classfile.test.example.NewTest;
import padl.creator.classfile.test.example.Relationships;
import padl.creator.classfile.test.example.SuperEntitiesConnection;
import padl.creator.classfile.test.example.UseRelationship_CLASS_CLASS_1;
import padl.creator.classfile.test.example.UseRelationship_CLASS_CLASS_3;
import padl.creator.classfile.test.innerclasses.InnerClassesTest;
import padl.creator.classfile.test.innerclasses.MemberClassesTest;
import padl.creator.classfile.test.innerclasses.MemberClasses2Test;
import padl.creator.classfile.test.innerclasses.MemberClasses3Test;
import padl.creator.classfile.test.innerclasses.MemberClasses4Test;
import padl.creator.classfile.test.innerclasses.MemberClasses5Test;
import padl.creator.classfile.test.innerclasses.MemberEntitiesTest;
import padl.creator.classfile.test.innerclasses.MemberInterfacesTest;
import padl.creator.classfile.test.method.MethodInclusionTest;
import padl.creator.classfile.test.method.MethodInvocationTest;
import padl.creator.classfile.test.method.PrivateConstructorTest;
import padl.creator.classfile.test.syntheticBridge.SyntheticBridgeTest;
import padl.creator.classfile.test.visitor.Composite1;
import padl.test.clone.CloneTest;
import padl.test.clone.CopyInTest;
import padl.test.listeners.ListenersTest;
import padl.test.remove.ConstituentRemoveTest;
import padl.test.remove.RemoveTest;
import padl.test.remove.RemoveAndIteratorTest;
import padl.test.setter.GetterTest;
import padl.test.setter.SetterTest;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since 2004/11/11
 */
public final class TestPADLAnalyses extends junit.framework.TestSuite {
	public TestPADLAnalyses() {
	}

	public TestPADLAnalyses(final Class theClass) {
		super(theClass);
	}

	public TestPADLAnalyses(final String name) {
		super(name);
	}

	public static Test suite() {
		final TestPADLAnalyses suite = new TestPADLAnalyses();

		suite.addTestSuite(Aggregation_CLASS_CLASS_FROM_FIELD_1_Test.class);
		suite.addTestSuite(Aggregation_CLASS_INSTANCE_FROM_FIELD_1_Test.class);
		suite.addTestSuite(Aggregation_CLASS_INSTANCE_FROM_FIELD_2_Test.class);

		suite.addTestSuite(padl.analysis.packagebuilder.test.SanityTest.class);

		suite.addTestSuite(
				padl.analysis.plantUMLGenerator.test.SanityTest.class);

		suite.addTestSuite(padl.analysis.systematicuml.test.SanityTest.class);

		suite.addTestSuite(Aggregation_CLASS_INSTANCE_FROM_FIELD_1_Test.class);
		suite.addTestSuite(Aggregation1.class);
		suite.addTestSuite(Aggregation10.class);
		suite.addTestSuite(Aggregation11.class);
		suite.addTestSuite(Aggregation13.class);
		// TODO Fix and add back this test
		//	suite.addTestSuite(Aggregation14.class);
		suite.addTestSuite(Aggregations.class);
		suite.addTestSuite(Association_INSTANCE_INSTANCE_2.class);
		suite.addTestSuite(ChainOfMessages.class);
		suite.addTestSuite(Composite1.class);
		suite.addTestSuite(Composite4AbstractDocument.class);
		suite.addTestSuite(CreationLink_INSTANCE_CREATION_1.class);
		suite.addTestSuite(CreationLink_INSTANCE_CREATION_3.class);
		suite.addTestSuite(DupTest.class);
		suite.addTestSuite(Ghost.class);
		suite.addTestSuite(Interfaces.class);
		suite.addTestSuite(ManyMethods.class);
		suite.addTestSuite(NewTest.class);
		suite.addTestSuite(Relationships.class);
		suite.addTestSuite(SuperEntitiesConnection.class);
		suite.addTestSuite(UseRelationship_CLASS_CLASS_1.class);
		suite.addTestSuite(UseRelationship_CLASS_CLASS_3.class);

		suite.addTestSuite(InnerClassesTest.class);
		suite.addTestSuite(MemberClassesTest.class);
		suite.addTestSuite(MemberClasses2Test.class);
		suite.addTestSuite(MemberClasses3Test.class);
		suite.addTestSuite(MemberClasses4Test.class);
		suite.addTestSuite(MemberClasses5Test.class);
		suite.addTestSuite(MemberEntitiesTest.class);
		suite.addTestSuite(MemberInterfacesTest.class);

		suite.addTestSuite(MethodInclusionTest.class);
		suite.addTestSuite(MethodInvocationTest.class);
		suite.addTestSuite(PrivateConstructorTest.class);

		suite.addTestSuite(SyntheticBridgeTest.class);

		suite.addTestSuite(Composite1.class);

		suite.addTestSuite(CloneTest.class);
		suite.addTestSuite(CopyInTest.class);
		suite.addTestSuite(RemoveTest.class);

		// TODO Add this test back
		//	suite.addTestSuite(ListenersTest.class);

		suite.addTestSuite(padl.test.path.SanityTest.class);

		suite.addTestSuite(ConstituentRemoveTest.class);
		suite.addTestSuite(RemoveTest.class);
		suite.addTestSuite(RemoveAndIteratorTest.class);

		suite.addTestSuite(SetterTest.class);
		suite.addTestSuite(GetterTest.class);

		return suite;
	}
}
