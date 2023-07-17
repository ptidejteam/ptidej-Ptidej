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
import jct.kernel.IJCTCompilationUnit;
import jct.tools.JCTCreatorFromSourceCode;
import jct.tools.JCTMap;
import util.io.ProxyConsole;

public final class JCTFileLister extends JCTMap<Void, Void> {
	public static void main(final String args[]) throws IOException {
		for (final File f : FileLister__CONSTANTS.ptntt_files)
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(
					f + " :: " + f.exists() + " == " + f.getCanonicalFile()
							+ "/" + f.getCanonicalFile().exists());

		JCTCreatorFromSourceCode.createJCT(
			"JCTFileLister",
			false,
			null,
			FileLister__CONSTANTS.options,
			FileLister__CONSTANTS.ptntt_files).accept(new JCTFileLister());
	}

	@Override
	public Void visitCompilationUnit(final IJCTCompilationUnit cu, final Void p) {
		try {
			if (cu.getSourceFile().getName().endsWith(".java"))
				ProxyConsole
					.getInstance()
					.debugOutput()
					.println(
						cu.getSourceFile()
								+ " :: "
								+ cu.getSourceFile().exists()
								+ " == "
								+ cu.getSourceFile().getCanonicalFile()
								+ "/"
								+ cu
									.getSourceFile()
									.getCanonicalFile()
									.exists());

		}
		catch (final IOException e) {
			ProxyConsole
				.getInstance()
				.debugOutput()
				.println(
					"NO Absolute :: " + cu.getSourceFile().getAbsoluteFile());
		}
		return null;
	}
}

final class FileLister__CONSTANTS {
	public static final String bin_dir = "/tmp/";

	public static final Iterable<String> options = Arrays.asList(new String[] {
			"-d", FileLister__CONSTANTS.bin_dir });

	public static final String source_directory =
		"/home/swoog/docs/boulot/UdeM/Maitrise/PT&TT/src";

	public static final File[] ptntt_files =
		new File[] {
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/transformation/exception/package-info.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/exception/NotCommutableTransformationsException.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/exception/InvalidTransformableException.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/transformation/kernel/ITransformation.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/IWithOriginTransformation.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/transformation/kernel/package-info.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/IWithDestinationTransformation.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/transformation/kernel/ITransformable.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/TransformationManager.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/AbstractTransformationSequence.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/AbstractCommutator.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/AbstractTransformation.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/ReverseTransformationSequence.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/CommutedTransformation.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/AbstractTransformable.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/TransformationSequence.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/transformation/kernel/impl/IdentityTransformation.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/transformation/kernel/ICommutator.java"),
				new File(
					FileLister__CONSTANTS.source_directory
							+ "/ptntt/versionning/exception/TaggedTransformationException.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/versionning/exception/package-info.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/versionning/package-info.java"),
				new File(FileLister__CONSTANTS.source_directory
						+ "/ptntt/versionning/VersionManager.java") };

	public static final File[] impl_files = new File[] {
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTClassMember.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTCast.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTDoWhile.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTIntersectionType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTFor.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTClassType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTRenamedRootNode.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTPrimitiveType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTReturn.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTBinaryOp.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTErroneousExpression.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTVariable.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTNewArray.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTElement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTEnhancedFor.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTBreakContinue.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTClass.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTUnaryOp.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTLabel.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTPath.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTElementContainer.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTImport.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTCatch.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTSynchronized.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTSwitch.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTAssignement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTIf.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTTry.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTAssert.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTMethodInvocation.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTArrayType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTPathPart.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTLiteral.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTParenthesis.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTConditionalOperator.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTCase.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTPackage.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTCompilationUnit.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTBlock.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTErroneousSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTArrayAccess.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTMethod.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTRootNode.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTExpressionStatement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTSimpleSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTNewClass.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTEmptyStatement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTThrow.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTMemberSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/impl/JCTInstanceOf.java"), };

	public static final File[] interface_files = new File[] {
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTMethod.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTBlock.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTLabel.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTElement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTLiteral.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTVisitor.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTThrow.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTElementContainer.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTCompilationUnit.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTVariable.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTIf.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/JCTKind.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTPrimitiveType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTRootNode.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTImportable.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTParenthesis.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTNewClass.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTArrayType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTBinaryOp.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTAssignement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTSynchronized.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTSimpleSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTFor.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTEnhancedFor.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTErroneousSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTIntersectionType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTPackage.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTCase.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTSwitch.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/JCTPrimitiveTypes.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTPathPart.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTCatch.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTTry.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTExpression.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTExpressionStatement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTPath.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTReturn.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTClassType.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTArrayAccess.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTClassMember.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTNewArray.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTBreakContinue.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTUnaryOp.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTDoWhile.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTConditionalOperator.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTMemberSelector.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTErroneousExpression.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/JCTModifiers.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTStatement.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTMethodInvocation.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTIdentifiable.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTCast.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTClass.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTImport.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTInstanceOf.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTAssert.java"),
			new File(FileLister__CONSTANTS.source_directory
					+ "/jct/kernel/IJCTContainer.java") };
}
