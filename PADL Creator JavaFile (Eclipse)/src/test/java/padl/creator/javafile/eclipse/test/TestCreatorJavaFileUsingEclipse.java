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
/* (c) Copyright 2009 and following years, Aminata SABANE,
 * Ecole Polytechnique de Montr̩al.
 * 
 * Use and copying of this software and preparation of derivative works
 * based upon this software are permitted. Any copy of this software or
 * of any derivative work must include the above copyright notice of
 * the author, this paragraph and the one after it.
 * 
 * This software is made available AS IS, and THE AUTHOR DISCLAIMS
 * ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE, AND NOT WITHSTANDING ANY OTHER PROVISION CONTAINED HEREIN,
 * ANY LIABILITY FOR DAMAGES RESULTING FROM THE SOFTWARE OR ITS USE IS
 * EXPRESSLY DISCLAIMED, WHETHER ARISING IN CONTRACT, TORT (INCLUDING
 * NEGLIGENCE) OR STRICT LIABILITY, EVEN IF THE AUTHOR IS ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 * 
 * All Rights Reserved.
 */
package padl.creator.javafile.eclipse.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import padl.creator.javafile.eclipse.test.advanced.DuplicationTest;
import padl.creator.javafile.eclipse.test.advanced.ExclusionTest;
import padl.creator.javafile.eclipse.test.advanced.FieldsAndReturnTypesTest;
import padl.creator.javafile.eclipse.test.advanced.SanityTest;
import padl.creator.javafile.eclipse.test.advanced.WeirdTest;
import padl.creator.javafile.eclipse.test.annotator.ConditionalModelAnnotatorTest;
import padl.creator.javafile.eclipse.test.annotator.LOCModelAnnotatorTest;
import padl.creator.javafile.eclipse.test.basic.ClassTest;
import padl.creator.javafile.eclipse.test.basic.IndirectSourcePathTest;
import padl.creator.javafile.eclipse.test.basic.InterfaceTest;
import padl.creator.javafile.eclipse.test.basic.LightModelCreatorTest;
import padl.creator.javafile.eclipse.test.basic.ManyClassesInOneFileTest;
import padl.creator.javafile.eclipse.test.basic.MemberGhostTest;
import padl.creator.javafile.eclipse.test.basic.PackagesTest;
import padl.creator.javafile.eclipse.test.basic.ParametrizedTypesTest;
import padl.creator.javafile.eclipse.test.basic.RingAndroidJavaClientTest;
import padl.creator.javafile.eclipse.test.basic.WithAndWithoutSourcePathTest;
import padl.creator.javafile.eclipse.test.comparator.ClassAndFileConventionTest;
import padl.creator.javafile.eclipse.test.comparator.LightModelsWithModelComparatorTest;
import padl.creator.javafile.eclipse.test.comparator.LightModelsWithStatisticVisitorTest;
import padl.creator.javafile.eclipse.test.comparator.ModelsDifferencesReporterTest;
import padl.creator.javafile.eclipse.test.methodinvocation.InvocationTargetExceptionTest;
import padl.creator.javafile.eclipse.test.methodinvocation.JavaFilesModelMethodInvocationExplorerTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_ChainOfMessagesTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_ClassClass1Test;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_ClassClass2Test;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_ClassInstanceFieldTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_ClassInstanceTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_Composite4AbstractDocumentTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_FieldAccessTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_InstanceClassTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_InstanceClass2Test;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_InstanceCreationTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_InstanceInstanceFieldTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MI_InstanceInstanceTest;
import padl.creator.javafile.eclipse.test.methodinvocation.MethodInvocationExplorerTest;
import padl.creator.javafile.eclipse.test.methodinvocation.PooyaTest;
import padl.creator.javafile.eclipse.test.others.CompleteCreatorTest;
import padl.creator.javafile.eclipse.test.others.NesrineTest;
import padl.creator.javafile.eclipse.test.others.PADLParserUtilsTest;
import padl.kernel.impl.MethodInvocationTest;

public class TestCreatorJavaFileUsingEclipse extends TestSuite {
	public static Test suite() {
		final TestSuite suite = new TestSuite();

		suite.addTestSuite(DuplicationTest.class);
		suite.addTestSuite(ExclusionTest.class);
		suite.addTestSuite(FieldsAndReturnTypesTest.class);
		suite.addTestSuite(SanityTest.class);
		suite.addTestSuite(WeirdTest.class);

		suite.addTestSuite(ConditionalModelAnnotatorTest.class);
		suite.addTestSuite(LOCModelAnnotatorTest.class);

		// suite.addTestSuite(padl.creator.javafile.eclipse.test.basic.ArgoUMLTest.class);
		suite.addTestSuite(ClassTest.class);
		suite.addTestSuite(IndirectSourcePathTest.class);
		suite.addTestSuite(InterfaceTest.class);
		// suite.addTestSuite(JProfilerTest.class);
		suite.addTestSuite(LightModelCreatorTest.class);
		suite.addTestSuite(ManyClassesInOneFileTest.class);
		suite.addTestSuite(MemberGhostTest.class);
		suite.addTestSuite(PackagesTest.class);
		suite.addTestSuite(ParametrizedTypesTest.class);
		suite.addTestSuite(RingAndroidJavaClientTest.class);
		suite.addTestSuite(WithAndWithoutSourcePathTest.class);

		suite.addTestSuite(ClassAndFileConventionTest.class);
		suite.addTestSuite(LightModelsWithModelComparatorTest.class);
		suite.addTestSuite(LightModelsWithStatisticVisitorTest.class);
		suite.addTestSuite(ModelsDifferencesReporterTest.class);

		// suite.addTestSuite(padl.creator.javafile.eclipse.test.methodinvocation.ArgoUMLTest.class);
		suite.addTestSuite(CompleteCreatorTest.class);
		suite.addTestSuite(InvocationTargetExceptionTest.class);
		suite.addTestSuite(JavaFilesModelMethodInvocationExplorerTest.class);
		suite.addTestSuite(MethodInvocationExplorerTest.class);
		suite.addTestSuite(MethodInvocationTest.class);
		suite.addTestSuite(MI_ChainOfMessagesTest.class);
		suite.addTestSuite(MI_ClassClass1Test.class);
		suite.addTestSuite(MI_ClassClass2Test.class);
		suite.addTestSuite(MI_ClassInstanceFieldTest.class);
		suite.addTestSuite(MI_ClassInstanceTest.class);
		suite.addTestSuite(MI_Composite4AbstractDocumentTest.class);
		suite.addTestSuite(MI_FieldAccessTest.class);
		suite.addTestSuite(MI_InstanceClassTest.class);
		suite.addTestSuite(MI_InstanceClass2Test.class);
		suite.addTestSuite(MI_InstanceCreationTest.class);
		suite.addTestSuite(MI_InstanceInstanceFieldTest.class);
		suite.addTestSuite(MI_InstanceInstanceTest.class);
		suite.addTestSuite(PooyaTest.class);

		// suite.addTestSuite(padl.creator.javafile.eclipse.test.others.ArgoUMLTest.class);
		suite.addTestSuite(CompleteCreatorTest.class);
		suite.addTestSuite(NesrineTest.class);
		suite.addTestSuite(PADLParserUtilsTest.class);

		return suite;
	}
}
