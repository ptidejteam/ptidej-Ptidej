/**
 * @author Mathieu Lemoine
 * @created 2009-03-16 (月)
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

package jct.test.cases;

import jct.kernel.IJCTAnd;
import jct.kernel.IJCTAndAssignment;
import jct.kernel.IJCTArrayAccess;
import jct.kernel.IJCTArrayType;
import jct.kernel.IJCTAssert;
import jct.kernel.IJCTAssignment;
import jct.kernel.IJCTBitwiseComplement;
import jct.kernel.IJCTBlock;
import jct.kernel.IJCTBooleanLiteral;
import jct.kernel.IJCTBreak;
import jct.kernel.IJCTCase;
import jct.kernel.IJCTCast;
import jct.kernel.IJCTCatch;
import jct.kernel.IJCTCharacterLiteral;
import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTClassType;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTConditionalAnd;
import jct.kernel.IJCTConditionalOperator;
import jct.kernel.IJCTConditionalOr;
import jct.kernel.IJCTContinue;
import jct.kernel.IJCTDivide;
import jct.kernel.IJCTDivideAssignment;
import jct.kernel.IJCTDoWhile;
import jct.kernel.IJCTDoubleLiteral;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTEmptyStatement;
import jct.kernel.IJCTEnhancedFor;
import jct.kernel.IJCTEqualTo;
import jct.kernel.IJCTErroneousExpression;
import jct.kernel.IJCTErroneousSelector;
import jct.kernel.IJCTExpressionStatement;
import jct.kernel.IJCTField;
import jct.kernel.IJCTFloatLiteral;
import jct.kernel.IJCTFor;
import jct.kernel.IJCTGreaterThan;
import jct.kernel.IJCTGreaterThanOrEqual;
import jct.kernel.IJCTIdentifiable;
import jct.kernel.IJCTIf;
import jct.kernel.IJCTImport;
import jct.kernel.IJCTInstanceOf;
import jct.kernel.IJCTIntegerLiteral;
import jct.kernel.IJCTIntersectionType;
import jct.kernel.IJCTLabel;
import jct.kernel.IJCTLeftShift;
import jct.kernel.IJCTLeftShiftAssignment;
import jct.kernel.IJCTLessThan;
import jct.kernel.IJCTLessThanOrEqual;
import jct.kernel.IJCTLogicalComplement;
import jct.kernel.IJCTLongLiteral;
import jct.kernel.IJCTMemberSelector;
import jct.kernel.IJCTMethod;
import jct.kernel.IJCTMethodInvocation;
import jct.kernel.IJCTMinus;
import jct.kernel.IJCTMinusAssignment;
import jct.kernel.IJCTMultiply;
import jct.kernel.IJCTMultiplyAssignment;
import jct.kernel.IJCTNewArray;
import jct.kernel.IJCTNewClass;
import jct.kernel.IJCTNotEqualTo;
import jct.kernel.IJCTNullLiteral;
import jct.kernel.IJCTOr;
import jct.kernel.IJCTOrAssignment;
import jct.kernel.IJCTPackage;
import jct.kernel.IJCTParenthesis;
import jct.kernel.IJCTPlus;
import jct.kernel.IJCTPlusAssignment;
import jct.kernel.IJCTPostfixDecrement;
import jct.kernel.IJCTPostfixIncrement;
import jct.kernel.IJCTPrefixDecrement;
import jct.kernel.IJCTPrefixIncrement;
import jct.kernel.IJCTPrimitiveType;
import jct.kernel.IJCTRemainder;
import jct.kernel.IJCTRemainderAssignment;
import jct.kernel.IJCTReturn;
import jct.kernel.IJCTRightShift;
import jct.kernel.IJCTRightShiftAssignment;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSimpleSelector;
import jct.kernel.IJCTStringLiteral;
import jct.kernel.IJCTSwitch;
import jct.kernel.IJCTSynchronized;
import jct.kernel.IJCTThrow;
import jct.kernel.IJCTTry;
import jct.kernel.IJCTUnaryMinus;
import jct.kernel.IJCTUnaryPlus;
import jct.kernel.IJCTUnsignedRightShift;
import jct.kernel.IJCTUnsignedRightShiftAssignment;
import jct.kernel.IJCTVariable;
import jct.kernel.IJCTVisitor;
import jct.kernel.IJCTWhile;
import jct.kernel.IJCTXor;
import jct.kernel.IJCTXorAssignment;
import jct.kernel.impl.JCTFactory;
import jct.tools.JCTMap;
import junit.framework.TestCase;
import util.io.ProxyConsole;

public final class JCTFactoryInitializer extends TestCase {
	private IJCTRootNode rootNode;

	public JCTFactoryInitializer(final String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		this.rootNode = JCTFactory.createJCT("test");
	}

	public void testFactoryInitializer() {
		this.rootNode.accept(new InternalMapper());
	}

	public void testFactoryInitializerWithPath() {
		this.rootNode.accept(new InternalMapperWithPath());
	}

	private static class InternalMapperWithPath extends InternalMapper {
		@Override
		public void visit(final IJCTElement e, final Void additionalParameter) {
			ProxyConsole.getInstance().debugOutput().println(e.getPath());
		}
	}

	private static class InternalMapper extends JCTMap<Void, Void>
			implements IJCTVisitor<Void, Void> {

		public void visit(final IJCTElement e, final Void additionalParameter) {
			ProxyConsole.getInstance().debugOutput().print(e);
			ProxyConsole.getInstance().debugOutput().print(" ");
			ProxyConsole.getInstance().debugOutput().print(additionalParameter);
		}

		@Override
		public Void visitAnd(final IJCTAnd andElement,
				final Void additionalParameter) {
			this.visit(andElement, additionalParameter);
			return super.visitAnd(andElement, additionalParameter);
		}

		@Override
		public Void visitAndAssignment(
				final IJCTAndAssignment andAssignmentElement,
				final Void additionalParameter) {
			this.visit(andAssignmentElement, additionalParameter);
			return super.visitAndAssignment(andAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitArrayAccess(final IJCTArrayAccess arrayAccessElement,
				final Void additionalParameter) {
			this.visit(arrayAccessElement, additionalParameter);
			return super.visitArrayAccess(arrayAccessElement,
					additionalParameter);
		}

		@Override
		public Void visitArrayType(final IJCTArrayType arrayTypeElement,
				final Void additionalParameter) {
			this.visit(arrayTypeElement, additionalParameter);
			return super.visitArrayType(arrayTypeElement, additionalParameter);
		}

		@Override
		public Void visitAssert(final IJCTAssert assertElement,
				final Void additionalParameter) {
			this.visit(assertElement, additionalParameter);
			return super.visitAssert(assertElement, additionalParameter);
		}

		@Override
		public Void visitAssignment(final IJCTAssignment assignmentElement,
				final Void additionalParameter) {
			this.visit(assignmentElement, additionalParameter);
			return super.visitAssignment(assignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitBitwiseComplement(
				final IJCTBitwiseComplement bitwiseComplementElement,
				final Void additionalParameter) {
			this.visit(bitwiseComplementElement, additionalParameter);
			return super.visitBitwiseComplement(bitwiseComplementElement,
					additionalParameter);
		}

		@Override
		public Void visitBlock(final IJCTBlock blockElement,
				final Void additionalParameter) {
			this.visit(blockElement, additionalParameter);
			return super.visitBlock(blockElement, additionalParameter);
		}

		@Override
		public Void visitBooleanLiteral(
				final IJCTBooleanLiteral booleanLiteralElement,
				final Void additionalParameter) {
			this.visit(booleanLiteralElement, additionalParameter);
			return super.visitBooleanLiteral(booleanLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitBreak(final IJCTBreak breakElement,
				final Void additionalParameter) {
			this.visit(breakElement, additionalParameter);
			return super.visitBreak(breakElement, additionalParameter);
		}

		@Override
		public Void visitField(final IJCTField fieldElement,
				final Void additionalParameter) {
			return super.visitField(fieldElement, additionalParameter);
		}

		@Override
		public Void visitCase(final IJCTCase caseElement,
				final Void additionalParameter) {
			this.visit(caseElement, additionalParameter);
			return super.visitCase(caseElement, additionalParameter);
		}

		@Override
		public Void visitCast(final IJCTCast castElement,
				final Void additionalParameter) {
			this.visit(castElement, additionalParameter);
			return super.visitCast(castElement, additionalParameter);
		}

		@Override
		public Void visitCatch(final IJCTCatch catchElement,
				final Void additionalParameter) {
			this.visit(catchElement, additionalParameter);
			return super.visitCatch(catchElement, additionalParameter);
		}

		@Override
		public Void visitCharacterLiteral(
				final IJCTCharacterLiteral characterLiteralElement,
				final Void additionalParameter) {
			this.visit(characterLiteralElement, additionalParameter);
			return super.visitCharacterLiteral(characterLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitClass(final IJCTClass classElement,
				final Void additionalParameter) {
			this.visit(classElement, additionalParameter);
			return super.visitClass(classElement, additionalParameter);
		}

		@Override
		public Void visitClassType(final IJCTClassType classTypeElement,
				final Void additionalParameter) {
			this.visit(classTypeElement, additionalParameter);
			return super.visitClassType(classTypeElement, additionalParameter);
		}

		@Override
		public Void visitCompilationUnit(
				final IJCTCompilationUnit compilationUnitElement,
				final Void additionalParameter) {
			this.visit(compilationUnitElement, additionalParameter);
			return super.visitCompilationUnit(compilationUnitElement,
					additionalParameter);
		}

		@Override
		public Void visitConditionalAnd(
				final IJCTConditionalAnd conditionalAndElement,
				final Void additionalParameter) {
			this.visit(conditionalAndElement, additionalParameter);
			return super.visitConditionalAnd(conditionalAndElement,
					additionalParameter);
		}

		@Override
		public Void visitConditionalOperator(
				final IJCTConditionalOperator conditionalOperatorElement,
				final Void additionalParameter) {
			this.visit(conditionalOperatorElement, additionalParameter);
			return super.visitConditionalOperator(conditionalOperatorElement,
					additionalParameter);
		}

		@Override
		public Void visitConditionalOr(
				final IJCTConditionalOr conditionalOrElement,
				final Void additionalParameter) {
			this.visit(conditionalOrElement, additionalParameter);
			return super.visitConditionalOr(conditionalOrElement,
					additionalParameter);
		}

		@Override
		public Void visitContinue(final IJCTContinue continueElement,
				final Void additionalParameter) {
			this.visit(continueElement, additionalParameter);
			return super.visitContinue(continueElement, additionalParameter);
		}

		@Override
		public Void visitDivide(final IJCTDivide divideElement,
				final Void additionalParameter) {
			this.visit(divideElement, additionalParameter);
			return super.visitDivide(divideElement, additionalParameter);
		}

		@Override
		public Void visitDivideAssignment(
				final IJCTDivideAssignment divideAssignmentElement,
				final Void additionalParameter) {
			this.visit(divideAssignmentElement, additionalParameter);
			return super.visitDivideAssignment(divideAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitDoWhile(final IJCTDoWhile doWhileElement,
				final Void additionalParameter) {
			this.visit(doWhileElement, additionalParameter);
			return super.visitDoWhile(doWhileElement, additionalParameter);
		}

		@Override
		public Void visitDoubleLiteral(
				final IJCTDoubleLiteral doubleLiteralElement,
				final Void additionalParameter) {
			this.visit(doubleLiteralElement, additionalParameter);
			return super.visitDoubleLiteral(doubleLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitEmptyStatement(
				final IJCTEmptyStatement emptyStatementElement,
				final Void additionalParameter) {
			this.visit(emptyStatementElement, additionalParameter);
			return super.visitEmptyStatement(emptyStatementElement,
					additionalParameter);
		}

		@Override
		public Void visitEnhancedFor(final IJCTEnhancedFor enhancedForElement,
				final Void additionalParameter) {
			this.visit(enhancedForElement, additionalParameter);
			return super.visitEnhancedFor(enhancedForElement,
					additionalParameter);
		}

		@Override
		public Void visitEqualTo(final IJCTEqualTo equalToElement,
				final Void additionalParameter) {
			this.visit(equalToElement, additionalParameter);
			return super.visitEqualTo(equalToElement, additionalParameter);
		}

		@Override
		public Void visitErroneousExpression(
				final IJCTErroneousExpression erroneousExpressionElement,
				final Void additionalParameter) {
			this.visit(erroneousExpressionElement, additionalParameter);
			return super.visitErroneousExpression(erroneousExpressionElement,
					additionalParameter);
		}

		@Override
		public Void visitErroneousSelector(
				final IJCTErroneousSelector erroneousSelectorElement,
				final Void additionalParameter) {
			this.visit(erroneousSelectorElement, additionalParameter);
			return super.visitErroneousSelector(erroneousSelectorElement,
					additionalParameter);
		}

		@Override
		public Void visitExpressionStatement(
				final IJCTExpressionStatement expressionStatementElement,
				final Void additionalParameter) {
			this.visit(expressionStatementElement, additionalParameter);
			return super.visitExpressionStatement(expressionStatementElement,
					additionalParameter);
		}

		@Override
		public Void visitFloatLiteral(
				final IJCTFloatLiteral floatLiteralElement,
				final Void additionalParameter) {
			this.visit(floatLiteralElement, additionalParameter);
			return super.visitFloatLiteral(floatLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitFor(final IJCTFor forElement,
				final Void additionalParameter) {
			this.visit(forElement, additionalParameter);
			return super.visitFor(forElement, additionalParameter);
		}

		@Override
		public Void visitGreaterThan(final IJCTGreaterThan greaterThanElement,
				final Void additionalParameter) {
			this.visit(greaterThanElement, additionalParameter);
			return super.visitGreaterThan(greaterThanElement,
					additionalParameter);
		}

		@Override
		public Void visitGreaterThanOrEqual(
				final IJCTGreaterThanOrEqual greaterThanOrEqualElement,
				final Void additionalParameter) {
			this.visit(greaterThanOrEqualElement, additionalParameter);
			return super.visitGreaterThanOrEqual(greaterThanOrEqualElement,
					additionalParameter);
		}

		@Override
		public Void visitIf(final IJCTIf ifElement,
				final Void additionalParameter) {
			this.visit(ifElement, additionalParameter);
			return super.visitIf(ifElement, additionalParameter);
		}

		@Override
		public Void visitImport(final IJCTImport importElement,
				final Void additionalParameter) {
			this.visit(importElement, additionalParameter);
			return super.visitImport(importElement, additionalParameter);
		}

		@Override
		public Void visitInstanceOf(final IJCTInstanceOf instanceOfElement,
				final Void additionalParameter) {
			this.visit(instanceOfElement, additionalParameter);
			return super.visitInstanceOf(instanceOfElement,
					additionalParameter);
		}

		@Override
		public Void visitIntegerLiteral(
				final IJCTIntegerLiteral integerLiteralElement,
				final Void additionalParameter) {
			this.visit(integerLiteralElement, additionalParameter);
			return super.visitIntegerLiteral(integerLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitIntersectionType(
				final IJCTIntersectionType intersectionTypeElement,
				final Void additionalParameter) {
			this.visit(intersectionTypeElement, additionalParameter);
			return super.visitIntersectionType(intersectionTypeElement,
					additionalParameter);
		}

		@Override
		public Void visitLabel(final IJCTLabel labelElement,
				final Void additionalParameter) {
			this.visit(labelElement, additionalParameter);
			return super.visitLabel(labelElement, additionalParameter);
		}

		@Override
		public Void visitLeftShift(final IJCTLeftShift leftShiftElement,
				final Void additionalParameter) {
			this.visit(leftShiftElement, additionalParameter);
			return super.visitLeftShift(leftShiftElement, additionalParameter);
		}

		@Override
		public Void visitLeftShiftAssignment(
				final IJCTLeftShiftAssignment leftShiftAssignmentElement,
				final Void additionalParameter) {
			this.visit(leftShiftAssignmentElement, additionalParameter);
			return super.visitLeftShiftAssignment(leftShiftAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitLessThan(final IJCTLessThan lessThanElement,
				final Void additionalParameter) {
			this.visit(lessThanElement, additionalParameter);
			return super.visitLessThan(lessThanElement, additionalParameter);
		}

		@Override
		public Void visitLessThanOrEqual(
				final IJCTLessThanOrEqual lessThanOrEqualElement,
				final Void additionalParameter) {
			this.visit(lessThanOrEqualElement, additionalParameter);
			return super.visitLessThanOrEqual(lessThanOrEqualElement,
					additionalParameter);
		}

		@Override
		public Void visitLogicalComplement(
				final IJCTLogicalComplement logicalComplementElement,
				final Void additionalParameter) {
			this.visit(logicalComplementElement, additionalParameter);
			return super.visitLogicalComplement(logicalComplementElement,
					additionalParameter);
		}

		@Override
		public Void visitLongLiteral(final IJCTLongLiteral longLiteralElement,
				final Void additionalParameter) {
			this.visit(longLiteralElement, additionalParameter);
			return super.visitLongLiteral(longLiteralElement,
					additionalParameter);
		}

		@Override
		public <Member extends IJCTClassMember> Void visitMemberSelector(
				final IJCTMemberSelector<Member> memberSelectorElement,
				final Void additionalParameter) {
			this.visit(memberSelectorElement, additionalParameter);
			return super.visitMemberSelector(memberSelectorElement,
					additionalParameter);
		}

		@Override
		public Void visitMethod(final IJCTMethod methodElement,
				final Void additionalParameter) {
			this.visit(methodElement, additionalParameter);
			return super.visitMethod(methodElement, additionalParameter);
		}

		@Override
		public Void visitMethodInvocation(
				final IJCTMethodInvocation methodInvocationElement,
				final Void additionalParameter) {
			this.visit(methodInvocationElement, additionalParameter);
			return super.visitMethodInvocation(methodInvocationElement,
					additionalParameter);
		}

		@Override
		public Void visitMinus(final IJCTMinus minusElement,
				final Void additionalParameter) {
			this.visit(minusElement, additionalParameter);
			return super.visitMinus(minusElement, additionalParameter);
		}

		@Override
		public Void visitMinusAssignment(
				final IJCTMinusAssignment minusAssignmentElement,
				final Void additionalParameter) {
			this.visit(minusAssignmentElement, additionalParameter);
			return super.visitMinusAssignment(minusAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitMultiply(final IJCTMultiply multiplyElement,
				final Void additionalParameter) {
			this.visit(multiplyElement, additionalParameter);
			return super.visitMultiply(multiplyElement, additionalParameter);
		}

		@Override
		public Void visitMultiplyAssignment(
				final IJCTMultiplyAssignment multiplyAssignmentElement,
				final Void additionalParameter) {
			this.visit(multiplyAssignmentElement, additionalParameter);
			return super.visitMultiplyAssignment(multiplyAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitNewArray(final IJCTNewArray newArrayElement,
				final Void additionalParameter) {
			this.visit(newArrayElement, additionalParameter);
			return super.visitNewArray(newArrayElement, additionalParameter);
		}

		@Override
		public Void visitNewClass(final IJCTNewClass newClassElement,
				final Void additionalParameter) {
			this.visit(newClassElement, additionalParameter);
			return super.visitNewClass(newClassElement, additionalParameter);
		}

		@Override
		public Void visitNotEqualTo(final IJCTNotEqualTo notEqualToElement,
				final Void additionalParameter) {
			this.visit(notEqualToElement, additionalParameter);
			return super.visitNotEqualTo(notEqualToElement,
					additionalParameter);
		}

		@Override
		public Void visitNullLiteral(final IJCTNullLiteral nullLiteralElement,
				final Void additionalParameter) {
			this.visit(nullLiteralElement, additionalParameter);
			return super.visitNullLiteral(nullLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitOr(final IJCTOr orElement,
				final Void additionalParameter) {
			this.visit(orElement, additionalParameter);
			return super.visitOr(orElement, additionalParameter);
		}

		@Override
		public Void visitOrAssignment(
				final IJCTOrAssignment orAssignmentElement,
				final Void additionalParameter) {
			this.visit(orAssignmentElement, additionalParameter);
			return super.visitOrAssignment(orAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitPackage(final IJCTPackage packageElement,
				final Void additionalParameter) {
			this.visit(packageElement, additionalParameter);
			return super.visitPackage(packageElement, additionalParameter);
		}

		@Override
		public Void visitParenthesis(final IJCTParenthesis parenthesisElement,
				final Void additionalParameter) {
			this.visit(parenthesisElement, additionalParameter);
			return super.visitParenthesis(parenthesisElement,
					additionalParameter);
		}

		@Override
		public Void visitPlus(final IJCTPlus plusElement,
				final Void additionalParameter) {
			this.visit(plusElement, additionalParameter);
			return super.visitPlus(plusElement, additionalParameter);
		}

		@Override
		public Void visitPlusAssignment(
				final IJCTPlusAssignment plusAssignmentElement,
				final Void additionalParameter) {
			this.visit(plusAssignmentElement, additionalParameter);
			return super.visitPlusAssignment(plusAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitPostfixDecrement(
				final IJCTPostfixDecrement postfixDecrementElement,
				final Void additionalParameter) {
			this.visit(postfixDecrementElement, additionalParameter);
			return super.visitPostfixDecrement(postfixDecrementElement,
					additionalParameter);
		}

		@Override
		public Void visitPostfixIncrement(
				final IJCTPostfixIncrement postfixIncrementElement,
				final Void additionalParameter) {
			this.visit(postfixIncrementElement, additionalParameter);
			return super.visitPostfixIncrement(postfixIncrementElement,
					additionalParameter);
		}

		@Override
		public Void visitPrefixDecrement(
				final IJCTPrefixDecrement prefixDecrementElement,
				final Void additionalParameter) {
			this.visit(prefixDecrementElement, additionalParameter);
			return super.visitPrefixDecrement(prefixDecrementElement,
					additionalParameter);
		}

		@Override
		public Void visitPrefixIncrement(
				final IJCTPrefixIncrement prefixIncrementElement,
				final Void additionalParameter) {
			this.visit(prefixIncrementElement, additionalParameter);
			return super.visitPrefixIncrement(prefixIncrementElement,
					additionalParameter);
		}

		@Override
		public Void visitPrimitiveType(
				final IJCTPrimitiveType primitiveTypeElement,
				final Void additionalParameter) {
			this.visit(primitiveTypeElement, additionalParameter);
			return super.visitPrimitiveType(primitiveTypeElement,
					additionalParameter);
		}

		@Override
		public Void visitRemainder(final IJCTRemainder remainderElement,
				final Void additionalParameter) {
			this.visit(remainderElement, additionalParameter);
			return super.visitRemainder(remainderElement, additionalParameter);
		}

		@Override
		public Void visitRemainderAssignment(
				final IJCTRemainderAssignment remainderAssignmentElement,
				final Void additionalParameter) {
			this.visit(remainderAssignmentElement, additionalParameter);
			return super.visitRemainderAssignment(remainderAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitReturn(final IJCTReturn returnElement,
				final Void additionalParameter) {
			this.visit(returnElement, additionalParameter);
			return super.visitReturn(returnElement, additionalParameter);
		}

		@Override
		public Void visitRightShift(final IJCTRightShift rightShiftElement,
				final Void additionalParameter) {
			this.visit(rightShiftElement, additionalParameter);
			return super.visitRightShift(rightShiftElement,
					additionalParameter);
		}

		@Override
		public Void visitRightShiftAssignment(
				final IJCTRightShiftAssignment rightShiftAssignmentElement,
				final Void additionalParameter) {
			this.visit(rightShiftAssignmentElement, additionalParameter);
			return super.visitRightShiftAssignment(rightShiftAssignmentElement,
					additionalParameter);
		}

		@Override
		public Void visitRootNode(final IJCTRootNode rootNodeElement,
				final Void additionalParameter) {
			this.visit(rootNodeElement, additionalParameter);
			return super.visitRootNode(rootNodeElement, additionalParameter);
		}

		@Override
		public <Identifiable extends IJCTIdentifiable> Void visitSimpleSelector(
				final IJCTSimpleSelector<Identifiable> simpleSelectorElement,
				final Void additionalParameter) {
			this.visit(simpleSelectorElement, additionalParameter);
			return super.visitSimpleSelector(simpleSelectorElement,
					additionalParameter);
		}

		@Override
		public Void visitStringLiteral(
				final IJCTStringLiteral stringLiteralElement,
				final Void additionalParameter) {
			this.visit(stringLiteralElement, additionalParameter);
			return super.visitStringLiteral(stringLiteralElement,
					additionalParameter);
		}

		@Override
		public Void visitSwitch(final IJCTSwitch switchElement,
				final Void additionalParameter) {
			this.visit(switchElement, additionalParameter);
			return super.visitSwitch(switchElement, additionalParameter);
		}

		@Override
		public Void visitSynchronized(
				final IJCTSynchronized synchronizedElement,
				final Void additionalParameter) {
			this.visit(synchronizedElement, additionalParameter);
			return super.visitSynchronized(synchronizedElement,
					additionalParameter);
		}

		@Override
		public Void visitThrow(final IJCTThrow throwElement,
				final Void additionalParameter) {
			this.visit(throwElement, additionalParameter);
			return super.visitThrow(throwElement, additionalParameter);
		}

		@Override
		public Void visitTry(final IJCTTry tryElement,
				final Void additionalParameter) {
			this.visit(tryElement, additionalParameter);
			return super.visitTry(tryElement, additionalParameter);
		}

		@Override
		public Void visitUnaryMinus(final IJCTUnaryMinus unaryMinusElement,
				final Void additionalParameter) {
			this.visit(unaryMinusElement, additionalParameter);
			return super.visitUnaryMinus(unaryMinusElement,
					additionalParameter);
		}

		@Override
		public Void visitUnaryPlus(final IJCTUnaryPlus unaryPlusElement,
				final Void additionalParameter) {
			this.visit(unaryPlusElement, additionalParameter);
			return super.visitUnaryPlus(unaryPlusElement, additionalParameter);
		}

		@Override
		public Void visitUnsignedRightShift(
				final IJCTUnsignedRightShift unsignedRightShiftElement,
				final Void additionalParameter) {
			this.visit(unsignedRightShiftElement, additionalParameter);
			return super.visitUnsignedRightShift(unsignedRightShiftElement,
					additionalParameter);
		}

		@Override
		public Void visitUnsignedRightShiftAssignment(
				final IJCTUnsignedRightShiftAssignment unsignedRightShiftAssignmentElement,
				final Void additionalParameter) {
			this.visit(unsignedRightShiftAssignmentElement,
					additionalParameter);
			return super.visitUnsignedRightShiftAssignment(
					unsignedRightShiftAssignmentElement, additionalParameter);
		}

		@Override
		public Void visitVariable(final IJCTVariable variableElement,
				final Void additionalParameter) {
			this.visit(variableElement, additionalParameter);
			return super.visitVariable(variableElement, additionalParameter);
		}

		@Override
		public Void visitWhile(final IJCTWhile whileElement,
				final Void additionalParameter) {
			this.visit(whileElement, additionalParameter);
			return super.visitWhile(whileElement, additionalParameter);
		}

		@Override
		public Void visitXor(final IJCTXor xorElement,
				final Void additionalParameter) {
			this.visit(xorElement, additionalParameter);
			return super.visitXor(xorElement, additionalParameter);
		}

		@Override
		public Void visitXorAssignment(
				final IJCTXorAssignment xorAssignmentElement,
				final Void additionalParameter) {
			this.visit(xorAssignmentElement, additionalParameter);
			return super.visitXorAssignment(xorAssignmentElement,
					additionalParameter);
		}
	}
}
