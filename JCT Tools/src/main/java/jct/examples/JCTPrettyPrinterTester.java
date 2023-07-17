/**
 * @author Mathieu Lemoine
 * @created 2008-11-01 (土)
 *
 * Licensed under 3-clause BSD License:
 * Copyright © 2009, Mathieu Lemoine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *  * Neither the name of Mathieu Lemoine nor the
 *    names of contributors may be used to endorse or promote products
 *    derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY Mathieu Lemoine ''AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL Mathieu Lemoine BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package jct.examples;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import jct.tools.JCTCreatorFromSourceCode;
import jct.tools.JCTPrettyPrinter;

public final class JCTPrettyPrinterTester {
	public static void main(final String args[]) throws IOException {
		/*
		IJCTRootNode root = JCTFactory.createJCT("initializingTest");

		System.out.println("\n\n");
		//*/

		/*
		  System.out.println(root.getType(JCTPrimitiveTypes.INTEGER));
		//*/

		/*
		for(IJCTPackage p : root.getPackages())
		{
		    System.out.println("package : " + p);
		    for(IJCTCompilationUnit cu : p.getEnclosedElements())
		    {
		        System.out.println("cu : " + cu);
		        if(null != cu)
		            for(IJCTClass c : cu.getClasses())
		            {
		                System.out.println("class : " + c.getFQN() + " extends " + c.getExtends() + "                [" + c + "]");
		                for(IJCTVariable v : c.getFields(null, true))
		                    System.out.println("v : " + v.getType() + " " + v.getName() + "                [" + v + "]");
		            }
		    }
		}
		//*/

		//*
		JCTCreatorFromSourceCode.createJCT(
			"JCTPrettyPrinterTest",
			false,
			null,
			PPT_CONSTANTS.options,
			PPT_CONSTANTS.java_files).accept(
			new JCTPrettyPrinter(PPT_CONSTANTS.new_source_dir));
		//*/
	}
}

final class PPT_CONSTANTS {
	public static final File new_source_dir = new File(
		"/tmp/test-javac-aast-pretty-printer/");

	public static final String bin_dir =
		"/home/swoog/docs/boulot/UdeM/Maitrise/Javac-AAST/eclipse-project/bin/";

	public static final Iterable<String> options =
		Arrays
			.asList(new String[] {
					"-classpath",
					System.getenv("CLASSPATH")
							+ ":/home/swoog/docs/boulot/UdeM/Maitrise/cvs/guehene-program-PADL/PADL.jar:/home/swoog/docs/boulot/UdeM/Maitrise/cvs/guehene-program-CPL/CPL.jar:/home/swoog/docs/boulot/UdeM/Maitrise/cvs/guehene-program-PADL-ClassFile-Creator/PADL_Creator_ClassFile.jar:/home/swoog/docs/boulot/UdeM/Maitrise/cvs/guehene-program-PADL-Analyses/PADL_Analyses.jar:/home/swoog/docs/boulot/UdeM/Maitrise/Javac-AAST/src:/home/swoog/docs/boulot/UdeM/Maitrise/Javac-AAST/lib/javac.jar:/home/swoog/docs/boulot/UdeM/Maitrise/Javac-AAST/eclipse-project/bin/",
					"-d", PPT_CONSTANTS.bin_dir });

	public static final String source_directory =
		"/home/swoog/docs/boulot/UdeM/Maitrise/Javac-AAST/src";

	public static final File[] java_files =
		new File[] {
				new File(PPT_CONSTANTS.source_directory
						+ "/util/NullableReference.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/util/StrongReference.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/util/ListOfUnique.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/util/NotNullableReference.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/util/IndirectCollection.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/tests/JCTPrettyPrinterTester.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/tools/JCTPrettyPrinter.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/tools/JCTDistordedCreatorFromSourceCode.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTMethod.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTBlock.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTLabel.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTElement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTLiteral.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTVisitor.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTThrow.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTElementContainer.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTCompilationUnit.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTVariable.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTIf.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/JCTKind.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTPrimitiveType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTRootNode.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTImportable.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTParenthesis.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTNewClass.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTArrayType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTBinaryOp.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTAssignement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTSynchronized.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTSimpleSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTClassMember.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTCast.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTDoWhile.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTIntersectionType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTFor.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTClassType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTRenamedRootNode.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTPrimitiveType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTReturn.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTBinaryOp.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTErroneousExpression.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTVariable.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTNewArray.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTElement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTEnhancedFor.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTBreakContinue.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTClass.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTUnaryOp.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTLabel.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTPath.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTElementContainer.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTImport.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTCatch.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTSynchronized.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTSwitch.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTAssignement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTIf.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTTry.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTAssert.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTMethodInvocation.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTArrayType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTPathPart.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTLiteral.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTParenthesis.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTConditionalOperator.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTCase.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTPackage.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTCompilationUnit.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTBlock.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTErroneousSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTArrayAccess.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTMethod.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTRootNode.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTExpressionStatement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTSimpleSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTFactory.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTNewClass.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTEmptyStatement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTThrow.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTMemberSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/impl/JCTInstanceOf.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTFor.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTEnhancedFor.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTErroneousSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTIntersectionType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTPackage.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTCase.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTSwitch.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/JCTPrimitiveTypes.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTPathPart.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTCatch.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTTry.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTExpression.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTExpressionStatement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTPath.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTReturn.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTClassType.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTArrayAccess.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTClassMember.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTNewArray.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTBreakContinue.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTUnaryOp.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTDoWhile.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTConditionalOperator.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTFactory.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTMemberSelector.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTErroneousExpression.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/JCTModifiers.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTStatement.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTMethodInvocation.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTIdentifiable.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTCast.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTClass.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTImport.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTInstanceOf.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTAssert.java"),
				new File(PPT_CONSTANTS.source_directory
						+ "/jct/kernel/IJCTContainer.java"), };
}
