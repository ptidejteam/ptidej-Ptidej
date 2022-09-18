/**
 * @author Mathieu Lemoine
 * @created 2008-08-10 (日)
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

import java.io.IOException;
import java.io.Writer;
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
import jct.kernel.IJCTComment;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTConditionalAnd;
import jct.kernel.IJCTConditionalOperator;
import jct.kernel.IJCTConditionalOr;
import jct.kernel.IJCTContinue;
import jct.kernel.IJCTDivide;
import jct.kernel.IJCTDivideAssignment;
import jct.kernel.IJCTDoWhile;
import jct.kernel.IJCTDoubleLiteral;
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
import jct.kernel.IJCTParameter;
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
import jct.tools.JCTMap;

public class JCTPathPrinter extends JCTMap<Void, Void> implements IJCTVisitor<Void, Void> {

	private final Writer writer;

    public JCTPathPrinter(final Writer writer)
    { this.writer = writer; }
	
	@Override
	public Void visitAnd(IJCTAnd andElement, Void additionalParameter) {
        try { this.writer.write(andElement.getPath().toString() + "\n"); } catch (IOException e) { }
        return super.visitAnd(andElement, additionalParameter);
	}

	@Override
	public Void visitAndAssignment(
		IJCTAndAssignment andAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(andAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitAndAssignment(andAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitArrayAccess(
		IJCTArrayAccess arrayAccessElement,
		Void additionalParameter) {
		try { this.writer.write(arrayAccessElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitArrayAccess(arrayAccessElement, additionalParameter);
	}

	@Override
	public Void visitArrayType(
		IJCTArrayType arrayTypeElement,
		Void additionalParameter) {
		try { this.writer.write(arrayTypeElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitArrayType(arrayTypeElement, additionalParameter);
	}

	@Override
	public Void visitAssert(IJCTAssert assertElement, Void additionalParameter) {
		try { this.writer.write(assertElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitAssert(assertElement, additionalParameter);
	}

	@Override
	public Void visitAssignment(
		IJCTAssignment assignmentElement,
		Void additionalParameter) {
		try { this.writer.write(assignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitAssignment(assignmentElement, additionalParameter);
	}

	@Override
	public Void visitBitwiseComplement(
		IJCTBitwiseComplement bitwiseComplementElement,
		Void additionalParameter) {
		try { this.writer.write(bitwiseComplementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitBitwiseComplement(bitwiseComplementElement, additionalParameter);
	}

	@Override
	public Void visitBlock(IJCTBlock blockElement, Void additionalParameter) {
		try { this.writer.write(blockElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitBlock(blockElement, additionalParameter);
	}

	@Override
	public Void visitBooleanLiteral(
		IJCTBooleanLiteral booleanLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(booleanLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitBooleanLiteral(booleanLiteralElement, additionalParameter);
	}

	@Override
	public Void visitBreak(IJCTBreak breakElement, Void additionalParameter) {
		try { this.writer.write(breakElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitBreak(breakElement, additionalParameter);
	}

	@Override
	public Void visitCase(IJCTCase caseElement, Void additionalParameter) {
		try { this.writer.write(caseElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitCase(caseElement, additionalParameter);
	}

	@Override
	public Void visitCast(IJCTCast castElement, Void additionalParameter) {
		try { this.writer.write(castElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitCast(castElement, additionalParameter);
	}

	@Override
	public Void visitCatch(IJCTCatch catchElement, Void additionalParameter) {
		try { this.writer.write(catchElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitCatch(catchElement, additionalParameter);
	}

	@Override
	public Void visitCharacterLiteral(
		IJCTCharacterLiteral characterLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(characterLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitCharacterLiteral(characterLiteralElement, additionalParameter);
	}

	@Override
	public Void visitClass(IJCTClass classElement, Void additionalParameter) {
		try { this.writer.write(classElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitClass(classElement, additionalParameter);
	}

	@Override
	public Void visitClassType(
		IJCTClassType classTypeElement,
		Void additionalParameter) {
		try { this.writer.write(classTypeElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitClassType(classTypeElement, additionalParameter);
	}

	@Override
	public Void visitComment(
		IJCTComment commentElement,
		Void additionalParameter) {
		try { this.writer.write(commentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitComment(commentElement, additionalParameter);
	}

	@Override
	public Void visitCompilationUnit(
		IJCTCompilationUnit compilationUnitElement,
		Void additionalParameter) {
		try { this.writer.write(compilationUnitElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitCompilationUnit(compilationUnitElement, additionalParameter);
	}

	@Override
	public Void visitConditionalAnd(
		IJCTConditionalAnd conditionalAndElement,
		Void additionalParameter) {
		try { this.writer.write(conditionalAndElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitConditionalAnd(conditionalAndElement, additionalParameter);
	}

	@Override
	public Void visitConditionalOperator(
		IJCTConditionalOperator conditionalOperatorElement,
		Void additionalParameter) {
		try { this.writer.write(conditionalOperatorElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitConditionalOperator(conditionalOperatorElement, additionalParameter);
	}

	@Override
	public Void visitConditionalOr(
		IJCTConditionalOr conditionalOrElement,
		Void additionalParameter) {
		try { this.writer.write(conditionalOrElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitConditionalOr(conditionalOrElement, additionalParameter);
	}

	@Override
	public Void visitContinue(
		IJCTContinue continueElement,
		Void additionalParameter) {
		try { this.writer.write(continueElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitContinue(continueElement, additionalParameter);
	}

	@Override
	public Void visitDivide(IJCTDivide divideElement, Void additionalParameter) {
		try { this.writer.write(divideElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitDivide(divideElement, additionalParameter);
	}

	@Override
	public Void visitDivideAssignment(
		IJCTDivideAssignment divideAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(divideAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitDivideAssignment(divideAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitDoWhile(
		IJCTDoWhile doWhileElement,
		Void additionalParameter) {
		try { this.writer.write(doWhileElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitDoWhile(doWhileElement, additionalParameter);
	}

	@Override
	public Void visitDoubleLiteral(
		IJCTDoubleLiteral doubleLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(doubleLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitDoubleLiteral(doubleLiteralElement, additionalParameter);
	}

	@Override
	public Void visitEmptyStatement(
		IJCTEmptyStatement emptyStatementElement,
		Void additionalParameter) {
		try { this.writer.write(emptyStatementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitEmptyStatement(emptyStatementElement, additionalParameter);
	}

	@Override
	public Void visitEnhancedFor(
		IJCTEnhancedFor enhancedForElement,
		Void additionalParameter) {
		try { this.writer.write(enhancedForElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitEnhancedFor(enhancedForElement, additionalParameter);
	}

	@Override
	public Void visitEqualTo(
		IJCTEqualTo equalToElement,
		Void additionalParameter) {
		try { this.writer.write(equalToElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitEqualTo(equalToElement, additionalParameter);
	}

	@Override
	public Void visitErroneousExpression(
		IJCTErroneousExpression erroneousExpressionElement,
		Void additionalParameter) {
		try { this.writer.write(erroneousExpressionElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitErroneousExpression(erroneousExpressionElement, additionalParameter);
	}

	@Override
	public Void visitErroneousSelector(
		IJCTErroneousSelector erroneousSelectorElement,
		Void additionalParameter) {
		try { this.writer.write(erroneousSelectorElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitErroneousSelector(erroneousSelectorElement, additionalParameter);
	}

	@Override
	public Void visitExpressionStatement(
		IJCTExpressionStatement expressionStatementElement,
		Void additionalParameter) {
		try { this.writer.write(expressionStatementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitExpressionStatement(expressionStatementElement, additionalParameter);
	}

	@Override
	public Void visitField(IJCTField fieldElement, Void additionalParameter) {
		try { this.writer.write(fieldElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitField(fieldElement, additionalParameter);
	}

	@Override
	public Void visitFloatLiteral(
		IJCTFloatLiteral floatLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(floatLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitFloatLiteral(floatLiteralElement, additionalParameter);
	}

	@Override
	public Void visitFor(IJCTFor forElement, Void additionalParameter) {
		try { this.writer.write(forElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitFor(forElement, additionalParameter);
	}

	@Override
	public Void visitGreaterThan(
		IJCTGreaterThan greaterThanElement,
		Void additionalParameter) {
		try { this.writer.write(greaterThanElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitGreaterThan(greaterThanElement, additionalParameter);
	}

	@Override
	public Void visitGreaterThanOrEqual(
		IJCTGreaterThanOrEqual greaterThanOrEqualElement,
		Void additionalParameter) {
		try { this.writer.write(greaterThanOrEqualElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitGreaterThanOrEqual(greaterThanOrEqualElement, additionalParameter);
	}

	@Override
	public Void visitIf(IJCTIf ifElement, Void additionalParameter) {
		try { this.writer.write(ifElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitIf(ifElement, additionalParameter);
	}

	@Override
	public Void visitImport(IJCTImport importElement, Void additionalParameter) {
		try { this.writer.write(importElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitImport(importElement, additionalParameter);
	}

	@Override
	public Void visitInstanceOf(
		IJCTInstanceOf instanceOfElement,
		Void additionalParameter) {
		try { this.writer.write(instanceOfElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitInstanceOf(instanceOfElement, additionalParameter);
	}

	@Override
	public Void visitIntegerLiteral(
		IJCTIntegerLiteral integerLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(integerLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitIntegerLiteral(integerLiteralElement, additionalParameter);
	}

	@Override
	public Void visitIntersectionType(
		IJCTIntersectionType intersectionTypeElement,
		Void additionalParameter) {
		try { this.writer.write(intersectionTypeElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitIntersectionType(intersectionTypeElement, additionalParameter);
	}

	@Override
	public Void visitLabel(IJCTLabel labelElement, Void additionalParameter) {
		try { this.writer.write(labelElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLabel(labelElement, additionalParameter);
	}

	@Override
	public Void visitLeftShift(
		IJCTLeftShift leftShiftElement,
		Void additionalParameter) {
		try { this.writer.write(leftShiftElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLeftShift(leftShiftElement, additionalParameter);
	}

	@Override
	public Void visitLeftShiftAssignment(
		IJCTLeftShiftAssignment leftShiftAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(leftShiftAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLeftShiftAssignment(leftShiftAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitLessThan(
		IJCTLessThan lessThanElement,
		Void additionalParameter) {
		try { this.writer.write(lessThanElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLessThan(lessThanElement, additionalParameter);
	}

	@Override
	public Void visitLessThanOrEqual(
		IJCTLessThanOrEqual lessThanOrEqualElement,
		Void additionalParameter) {
		try { this.writer.write(lessThanOrEqualElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLessThanOrEqual(lessThanOrEqualElement, additionalParameter);
	}

	@Override
	public Void visitLogicalComplement(
		IJCTLogicalComplement logicalComplementElement,
		Void additionalParameter) {
		try { this.writer.write(logicalComplementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLogicalComplement(logicalComplementElement, additionalParameter);
	}

	@Override
	public Void visitLongLiteral(
		IJCTLongLiteral longLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(longLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitLongLiteral(longLiteralElement, additionalParameter);
	}

	@Override
	public <Member extends IJCTClassMember> Void visitMemberSelector(
		IJCTMemberSelector<Member> memberSelectorElement,
		Void additionalParameter) {
        try { this.writer.write(memberSelectorElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMemberSelector(memberSelectorElement, additionalParameter);
	}

	@Override
	public Void visitMethod(IJCTMethod methodElement, Void additionalParameter) {
		try { this.writer.write(methodElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMethod(methodElement, additionalParameter);
	}

	@Override
	public Void visitMethodInvocation(
		IJCTMethodInvocation methodInvocationElement,
		Void additionalParameter) {
		try { this.writer.write(methodInvocationElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMethodInvocation(methodInvocationElement, additionalParameter);
	}

	@Override
	public Void visitMinus(IJCTMinus minusElement, Void additionalParameter) {
		try { this.writer.write(minusElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMinus(minusElement, additionalParameter);
	}

	@Override
	public Void visitMinusAssignment(
		IJCTMinusAssignment minusAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(minusAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMinusAssignment(minusAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitMultiply(
		IJCTMultiply multiplyElement,
		Void additionalParameter) {
		try { this.writer.write(multiplyElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMultiply(multiplyElement, additionalParameter);
	}

	@Override
	public Void visitMultiplyAssignment(
		IJCTMultiplyAssignment multiplyAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(multiplyAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitMultiplyAssignment(multiplyAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitNewArray(
		IJCTNewArray newArrayElement,
		Void additionalParameter) {
		try { this.writer.write(newArrayElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitNewArray(newArrayElement, additionalParameter);
	}

	@Override
	public Void visitNewClass(
		IJCTNewClass newClassElement,
		Void additionalParameter) {
		try { this.writer.write(newClassElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitNewClass(newClassElement, additionalParameter);
	}

	@Override
	public Void visitNotEqualTo(
		IJCTNotEqualTo notEqualToElement,
		Void additionalParameter) {
		try { this.writer.write(notEqualToElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitNotEqualTo(notEqualToElement, additionalParameter);
	}

	@Override
	public Void visitNullLiteral(
		IJCTNullLiteral nullLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(nullLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitNullLiteral(nullLiteralElement, additionalParameter);
	}

	@Override
	public Void visitOr(IJCTOr orElement, Void additionalParameter) {
		try { this.writer.write(orElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitOr(orElement, additionalParameter);
	}

	@Override
	public Void visitOrAssignment(
		IJCTOrAssignment orAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(orAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitOrAssignment(orAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitPackage(
		IJCTPackage packageElement,
		Void additionalParameter) {
		try { this.writer.write(packageElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPackage(packageElement, additionalParameter);
	}

	@Override
	public Void visitParameter(
		IJCTParameter parameterElement,
		Void additionalParameter) {
		try { this.writer.write(parameterElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitParameter(parameterElement, additionalParameter);
	}

	@Override
	public Void visitParenthesis(
		IJCTParenthesis parenthesisElement,
		Void additionalParameter) {
		try { this.writer.write(parenthesisElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitParenthesis(parenthesisElement, additionalParameter);
	}

	@Override
	public Void visitPlus(IJCTPlus plusElement, Void additionalParameter) {
		try { this.writer.write(plusElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPlus(plusElement, additionalParameter);
	}

	@Override
	public Void visitPlusAssignment(
		IJCTPlusAssignment plusAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(plusAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPlusAssignment(plusAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitPostfixDecrement(
		IJCTPostfixDecrement postfixDecrementElement,
		Void additionalParameter) {
		try { this.writer.write(postfixDecrementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPostfixDecrement(postfixDecrementElement, additionalParameter);
	}

	@Override
	public Void visitPostfixIncrement(
		IJCTPostfixIncrement postfixIncrementElement,
		Void additionalParameter) {
		try { this.writer.write(postfixIncrementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPostfixIncrement(postfixIncrementElement, additionalParameter);
	}

	@Override
	public Void visitPrefixDecrement(
		IJCTPrefixDecrement prefixDecrementElement,
		Void additionalParameter) {
		try { this.writer.write(prefixDecrementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPrefixDecrement(prefixDecrementElement, additionalParameter);
	}

	@Override
	public Void visitPrefixIncrement(
		IJCTPrefixIncrement prefixIncrementElement,
		Void additionalParameter) {
		try { this.writer.write(prefixIncrementElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPrefixIncrement(prefixIncrementElement, additionalParameter);
	}

	@Override
	public Void visitPrimitiveType(
		IJCTPrimitiveType primitiveTypeElement,
		Void additionalParameter) {
		try { this.writer.write(primitiveTypeElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitPrimitiveType(primitiveTypeElement, additionalParameter);
	}

	@Override
	public Void visitRemainder(
		IJCTRemainder remainderElement,
		Void additionalParameter) {
		try { this.writer.write(remainderElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitRemainder(remainderElement, additionalParameter);
	}

	@Override
	public Void visitRemainderAssignment(
		IJCTRemainderAssignment remainderAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(remainderAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitRemainderAssignment(remainderAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitReturn(IJCTReturn returnElement, Void additionalParameter) {
		try { this.writer.write(returnElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitReturn(returnElement, additionalParameter);
	}

	@Override
	public Void visitRightShift(
		IJCTRightShift rightShiftElement,
		Void additionalParameter) {
		try { this.writer.write(rightShiftElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitRightShift(rightShiftElement, additionalParameter);
	}

	@Override
	public Void visitRightShiftAssignment(
		IJCTRightShiftAssignment rightShiftAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(rightShiftAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitRightShiftAssignment(rightShiftAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitRootNode(
		IJCTRootNode rootNodeElement,
		Void additionalParameter) {
		try { this.writer.write(rootNodeElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitRootNode(rootNodeElement, additionalParameter);
	}

	@Override
	public <Identifiable extends IJCTIdentifiable> Void visitSimpleSelector(
		IJCTSimpleSelector<Identifiable> simpleSelectorElement,
        Void additionalParameter) {
		try { this.writer.write(simpleSelectorElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitSimpleSelector(simpleSelectorElement, additionalParameter);
	}

	@Override
	public Void visitStringLiteral(
		IJCTStringLiteral stringLiteralElement,
		Void additionalParameter) {
		try { this.writer.write(stringLiteralElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitStringLiteral(stringLiteralElement, additionalParameter);
	}

	@Override
	public Void visitSwitch(IJCTSwitch switchElement, Void additionalParameter) {
		try { this.writer.write(switchElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitSwitch(switchElement, additionalParameter);
	}

	@Override
	public Void visitSynchronized(
		IJCTSynchronized synchronizedElement,
		Void additionalParameter) {
		try { this.writer.write(synchronizedElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitSynchronized(synchronizedElement, additionalParameter);
	}

	@Override
	public Void visitThrow(IJCTThrow throwElement, Void additionalParameter) {
		try { this.writer.write(throwElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitThrow(throwElement, additionalParameter);
	}

	@Override
	public Void visitTry(IJCTTry tryElement, Void additionalParameter) {
		try { this.writer.write(tryElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitTry(tryElement, additionalParameter);
	}

	@Override
	public Void visitUnaryMinus(
		IJCTUnaryMinus unaryMinusElement,
		Void additionalParameter) {
		try { this.writer.write(unaryMinusElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitUnaryMinus(unaryMinusElement, additionalParameter);
	}

	@Override
	public Void visitUnaryPlus(
		IJCTUnaryPlus unaryPlusElement,
		Void additionalParameter) {
		try { this.writer.write(unaryPlusElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitUnaryPlus(unaryPlusElement, additionalParameter);
	}

	@Override
	public Void visitUnsignedRightShift(
		IJCTUnsignedRightShift unsignedRightShiftElement,
		Void additionalParameter) {
		try { this.writer.write(unsignedRightShiftElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitUnsignedRightShift(unsignedRightShiftElement, additionalParameter);
	}

	@Override
	public Void visitUnsignedRightShiftAssignment(
		IJCTUnsignedRightShiftAssignment unsignedRightShiftAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(unsignedRightShiftAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitUnsignedRightShiftAssignment(unsignedRightShiftAssignmentElement, additionalParameter);
	}

	@Override
	public Void visitVariable(
		IJCTVariable variableElement,
		Void additionalParameter) {
		try { this.writer.write(variableElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitVariable(variableElement, additionalParameter);
	}

	@Override
	public Void visitWhile(IJCTWhile whileElement, Void additionalParameter) {
		try { this.writer.write(whileElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitWhile(whileElement, additionalParameter);
	}

	@Override
	public Void visitXor(IJCTXor xorElement, Void additionalParameter) {
		try { this.writer.write(xorElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitXor(xorElement, additionalParameter);
	}

	@Override
	public Void visitXorAssignment(
		IJCTXorAssignment xorAssignmentElement,
		Void additionalParameter) {
		try { this.writer.write(xorAssignmentElement.getPath() + "\n"); } catch (IOException e) { }
		return super.visitXorAssignment(xorAssignmentElement, additionalParameter);
	}
}
