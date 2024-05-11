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
import jct.kernel.IJCTCharacterLiteral;
import jct.kernel.IJCTDoubleLiteral;
import jct.kernel.IJCTFloatLiteral;
import jct.kernel.IJCTLongLiteral;
import jct.kernel.IJCTMethod;
import jct.kernel.IJCTStringLiteral;
import jct.kernel.IJCTThrow;
import jct.tools.JCTCreatorFromSourceCode;
import jct.tools.JCTMap;
import util.io.ProxyConsole;

/**
 * Not in the filter :
 *  Literals in getSourceCode methods
 *  Literals in throw statements
 *  Null, int and boolean literals
 */

public final class JCTFilterTester extends JCTMap<Void, Void> {
	public static void main(final String args[]) throws IOException {
		JCTCreatorFromSourceCode.createJCT(
			"JCTFilterTester",
			false,
			null,
			FilterTester__CONSTANTS.options,
			FilterTester__CONSTANTS.impl_files).accept(new JCTFilterTester());
	}

	@Override
	public Void visitCharacterLiteral(final IJCTCharacterLiteral t, final Void v) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("char : " + t.getPath() + " @@ " + t.getValue());
		return null;
	}

	@Override
	public Void visitDoubleLiteral(final IJCTDoubleLiteral t, final Void v) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("double : " + t.getPath() + " @@ " + t.getValue());
		return null;
	}

	@Override
	public Void visitFloatLiteral(final IJCTFloatLiteral t, final Void v) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("float : " + t.getPath() + " @@ " + t.getValue());
		return null;
	}

	@Override
	public Void visitLongLiteral(final IJCTLongLiteral t, final Void v) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("long : " + t.getPath() + " @@ " + t.getValue());
		return null;
	}

	@Override
	public Void visitStringLiteral(final IJCTStringLiteral t, final Void v) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("String : " + t.getPath() + " @@ " + t.getValue());
		return null;
	}

	@Override
	public Void visitMethod(final IJCTMethod m, final Void v) {
		if ("getSourceCode".equals(m.getName())
				|| "getCompoundOperator".equals(m.getName())
				|| "getOperator".equals(m.getName())
				|| "getKeyword".equals(m.getName()))
			return null;
		else
			return super.visitMethod(m, v);
	}

	@Override
	public Void visitThrow(final IJCTThrow t, final Void v) {
		return null;
	}
}

final class FilterTester__CONSTANTS {
	public static final String project_dir =
		"/home/swoog/docs/boulot/UdeM/Maitrise";

	public static final String bin_dir = FilterTester__CONSTANTS.project_dir
			+ "/JCT-Impl/bin/";

	public static final Iterable<String> options = Arrays.asList(new String[] {
			"-classpath",
			System.getenv("CLASSPATH") + ":"
					+ FilterTester__CONSTANTS.project_dir + "JCT-Utils/bin/"
					+ ":" + FilterTester__CONSTANTS.project_dir + "JCT/bin/"
					+ ":" + FilterTester__CONSTANTS.project_dir
					+ "JCT-Impl/bin/" + ":"
					+ FilterTester__CONSTANTS.project_dir + "JCT-Tools/bin/",
			"-d", FilterTester__CONSTANTS.bin_dir });

	public static final String impl_source_directory =
		FilterTester__CONSTANTS.project_dir + "/JCT-Impl/src";
	public static final String interface_source_directory =
		FilterTester__CONSTANTS.project_dir + "/JCT/src";

	public static final File[] impl_files = new File[] {
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPostfixedUnaryOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTDoubleLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTAnd.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTClassMember.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLeftShift.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTCast.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTStringLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTDoWhile.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTIntersectionType.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTFor.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTUnaryMinus.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTUnaryPlus.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTClassType.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTRenamedRootNode.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPrimitiveType.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTReturn.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTSelector.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLongLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPathPartBuilder.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTErroneousExpression.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTVariable.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTType.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTNewArray.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTEnhancedFor.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTGreaterThan.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTBreakContinue.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTOrAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTClass.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTRemainder.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTUnsignedRightShiftAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLabel.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPath.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTContinue.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTNullLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPrefixedUnaryOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTDoWhileImpl.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLessThan.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMultiply.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTField.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTImport.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPlusAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTCatch.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTSynchronized.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTElement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTSwitch.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMinus.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTWhile.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTFileOffsetPath.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTUnresolvedSimpleSelector.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTElementContainer.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMultiplyAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTSourceCodePart.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTIf.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTTry.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTUnsignedRightShift.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTAssert.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMethodInvocation.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTIntegerLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTArrayType.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTNonPrimitiveType.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLeftShiftAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTXorAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPostfixDecrement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPrefixIncrement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTBinaryOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPathPart.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTRightShiftAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTParenthesis.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTOr.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTXor.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTBooleanLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTBitwiseComplement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLogicalComplement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPrefixDecrement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTRemainderAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPlus.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTConditionalOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTGreaterThanOrEqual.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLogicalBinaryOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTRightShift.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTCase.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTVariableImpl.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPackage.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTBreak.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTCompilationUnit.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTDivide.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTBlock.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTConditionalOr.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTErroneousSelector.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTArrayAccess.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMethod.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTArithmeticBinaryOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTRootNode.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTExpressionStatement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTEqualTo.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTPostfixIncrement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTSimpleSelector.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTFactory.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTAndAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTCharacterLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMinusAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTNewClass.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTParameter.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTDivideAssignment.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTNotEqualTo.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTUnaryOperator.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTFloatLiteral.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTLessThanOrEqual.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTEmptyStatement.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTConditionalAnd.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTThrow.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTMemberSelector.java"),
			new File(FilterTester__CONSTANTS.impl_source_directory
					+ "/jct/kernel/impl/JCTInstanceOf.java"), };

	public static final File[] interface_files = new File[] {
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMethod.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTRemainderAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTUnaryPlus.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMultiply.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTEqualTo.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTDivide.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTGreaterThan.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTBlock.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/Constants.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTOrAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLabel.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTGreaterThanOrEqual.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTAndAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPrefixDecrement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMinusAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTVisitor.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPlus.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTType.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTNotEqualTo.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTThrow.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLeftShiftAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTCompilationUnit.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTVariable.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTUnsignedRightShift.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTRemainder.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTIf.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTUnsignedRightShiftAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/package-info.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLessThan.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/JCTKind.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPrimitiveType.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPostfixDecrement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTBreak.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTRootNode.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTElementContainer.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTImportable.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTFloatLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTContinue.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTParenthesis.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTBitwiseComplement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTNewClass.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTArrayType.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPostfixIncrement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTParameter.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTOr.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMultiplyAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTBinaryOperator.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTSynchronized.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTSimpleSelector.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTWhile.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTNullLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPlusAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTCharacterLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTNonPrimitiveType.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTFor.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLeftShift.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTElement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTEnhancedFor.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTErroneousSelector.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTIntersectionType.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTStringLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTConditionalAnd.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTIntegerLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPackage.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTCase.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTSwitch.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/JCTPrimitiveTypes.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTUnaryOperator.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTCatch.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTTry.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLongLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTExpression.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLessThanOrEqual.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTConditionalOr.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTExpressionStatement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPath.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTReturn.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTField.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTClassType.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTArrayAccess.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTClassMember.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTXor.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTLogicalComplement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTNewArray.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTSelector.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTSourceCodePart.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTDoWhile.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTConditionalOperator.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTDivideAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTDoubleLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTFactory.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTXorAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMemberSelector.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTErroneousExpression.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/JCTModifiers.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTRightShift.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTStatement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTUnaryMinus.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTEmptyStatement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMethodInvocation.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTIdentifiable.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTMinus.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTBooleanLiteral.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTCast.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTClass.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTRightShiftAssignment.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTPrefixIncrement.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTImport.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTAnd.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTInstanceOf.java"),
			new File(FilterTester__CONSTANTS.interface_source_directory
					+ "/jct/kernel/IJCTAssert.java"), };
}
