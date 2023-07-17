/**
 * @author Mathieu Lemoine
 * @created 2009-05-06 (水)
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
import jct.kernel.IJCTEmptyStatement;
import jct.kernel.IJCTEnhancedFor;
import jct.kernel.IJCTEqualTo;
import jct.kernel.IJCTErroneousExpression;
import jct.kernel.IJCTErroneousSelector;
import jct.kernel.IJCTExpressionStatement;
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
import jct.tools.JCTMapReduce;

public final class JCTNodeCounter
        extends JCTMapReduce<Integer, Integer>
        implements IJCTVisitor<Integer, Integer>
{
    @Override
    protected Integer reduce(final Integer current, final Integer step)
    { return (null == current ? 0 : current) + (null == step ? 0 : step); }

    @Override
    public Integer visitRootNode(final IJCTRootNode rootNodeElement, final Integer additionalParameter)
    { return this.reduce(this.reduce(super.visitRootNode(rootNodeElement, null), 1), additionalParameter); }

    @Override
    public Integer visitPrimitiveType(final IJCTPrimitiveType primitiveTypeElement, final Integer additionalParameter)
    { return this.reduce(super.visitPrimitiveType(primitiveTypeElement, additionalParameter), 1); }

    @Override
    public Integer visitArrayType(final IJCTArrayType arrayTypeElement, final Integer additionalParameter)
    { return null; }

    @Override
    public Integer visitClassType(final IJCTClassType classTypeElement, final Integer additionalParameter)
    { return null; }

    @Override
    public Integer visitIntersectionType(final IJCTIntersectionType intersectionTypeElement, final Integer additionalParameter)
    { return null; }

    @Override
    public Integer visitPackage(final IJCTPackage packageElement, final Integer additionalParameter)
    { return this.reduce(super.visitPackage(packageElement, additionalParameter), 1); }

    @Override
    public Integer visitCompilationUnit(final IJCTCompilationUnit compilationUnitElement, final Integer additionalParameter)
    { return this.reduce(super.visitCompilationUnit(compilationUnitElement, additionalParameter), 1); }

    @Override
    public Integer visitImport(final IJCTImport importElement, final Integer additionalParameter)
    { return this.reduce(super.visitImport(importElement, additionalParameter), 1); }

    @Override
    public Integer visitClass(final IJCTClass classElement, final Integer additionalParameter)
    { return this.reduce(super.visitClass(classElement, additionalParameter), 1); }

    @Override
    public Integer visitMethod(final IJCTMethod methodElement, final Integer additionalParameter)
    { return this.reduce(super.visitMethod(methodElement, additionalParameter), 1); }

    @Override
    public Integer visitMethodInvocation(final IJCTMethodInvocation methodInvocationElement, final Integer additionalParameter)
    { return this.reduce(super.visitMethodInvocation(methodInvocationElement, additionalParameter), 1); }

    @Override
    public Integer visitVariable(final IJCTVariable variableElement, final Integer additionalParameter)
    { return this.reduce(super.visitVariable(variableElement, additionalParameter), 1); }

    @Override
    public Integer visitBlock(final IJCTBlock blockElement, final Integer additionalParameter)
    { return this.reduce(super.visitBlock(blockElement, additionalParameter), 1); }

    @Override
    public Integer visitAssert(final IJCTAssert assertElement, final Integer additionalParameter)
    { return this.reduce(super.visitAssert(assertElement, additionalParameter), 1); }

    @Override
    public Integer visitBreak(final IJCTBreak breakElement, final Integer additionalParameter)
    { return this.reduce(super.visitBreak(breakElement, additionalParameter), 1); }

    @Override
    public Integer visitContinue(final IJCTContinue continueElement, final Integer additionalParameter)
    { return this.reduce(super.visitContinue(continueElement, additionalParameter), 1); }

    @Override
    public Integer visitLabel(final IJCTLabel labelElement, final Integer additionalParameter)
    { return this.reduce(super.visitLabel(labelElement, additionalParameter), 1); }

    @Override
    public Integer visitDoWhile(final IJCTDoWhile doWhileElement, final Integer additionalParameter)
    { return this.reduce(super.visitDoWhile(doWhileElement, additionalParameter), 1); }

    @Override
    public Integer visitWhile(final IJCTWhile whileElement, final Integer additionalParameter)
    { return this.reduce(super.visitWhile(whileElement, additionalParameter), 1); }

    @Override
    public Integer visitFor(final IJCTFor forElement, final Integer additionalParameter)
    { return this.reduce(super.visitFor(forElement, additionalParameter), 1); }

    @Override
    public Integer visitEnhancedFor(final IJCTEnhancedFor enhancedForElement, final Integer additionalParameter)
    { return this.reduce(super.visitEnhancedFor(enhancedForElement, additionalParameter), 1); }

    @Override
    public Integer visitIf(final IJCTIf ifElement, final Integer additionalParameter)
    { return this.reduce(super.visitIf(ifElement, additionalParameter), 1); }

    @Override
    public Integer visitReturn(final IJCTReturn returnElement, final Integer additionalParameter)
    { return this.reduce(super.visitReturn(returnElement, additionalParameter), 1); }

    @Override
    public Integer visitSwitch(final IJCTSwitch switchElement, final Integer additionalParameter)
    { return this.reduce(super.visitSwitch(switchElement, additionalParameter), 1); }

    @Override
    public Integer visitCase(final IJCTCase caseElement, final Integer additionalParameter)
    { return this.reduce(super.visitCase(caseElement, additionalParameter), 1); }

    @Override
    public Integer visitSynchronized(final IJCTSynchronized synchronizedElement, final Integer additionalParameter)
    { return this.reduce(super.visitSynchronized(synchronizedElement, additionalParameter), 1); }

    @Override
    public Integer visitThrow(final IJCTThrow throwElement, final Integer additionalParameter)
    { return this.reduce(super.visitThrow(throwElement, additionalParameter), 1); }

    @Override
    public Integer visitTry(final IJCTTry tryElement, final Integer additionalParameter)
    { return this.reduce(super.visitTry(tryElement, additionalParameter), 1); }

    @Override
    public Integer visitCatch(final IJCTCatch catchElement, final Integer additionalParameter)
    { return this.reduce(super.visitCatch(catchElement, additionalParameter), 1); }

    @Override
    public Integer visitArrayAccess(final IJCTArrayAccess arrayAccessElement, final Integer additionalParameter)
    { return this.reduce(super.visitArrayAccess(arrayAccessElement, additionalParameter), 1); }

    @Override
    public Integer visitExpressionStatement(final IJCTExpressionStatement expressionStatementElement, final Integer additionalParameter)
    { return this.reduce(super.visitExpressionStatement(expressionStatementElement, additionalParameter), 1); }

    @Override
    public Integer visitAssignment(final IJCTAssignment assignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitAssignment(assignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitAndAssignment(final IJCTAndAssignment andAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitAndAssignment(andAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitDivideAssignment(final IJCTDivideAssignment divideAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitDivideAssignment(divideAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitLeftShiftAssignment(final IJCTLeftShiftAssignment leftShiftAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitLeftShiftAssignment(leftShiftAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitMinusAssignment(final IJCTMinusAssignment minusAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitMinusAssignment(minusAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitMultiplyAssignment(final IJCTMultiplyAssignment multiplyAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitMultiplyAssignment(multiplyAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitOrAssignment(final IJCTOrAssignment orAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitOrAssignment(orAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitPlusAssignment(final IJCTPlusAssignment plusAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitPlusAssignment(plusAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitRemainderAssignment(final IJCTRemainderAssignment remainderAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitRemainderAssignment(remainderAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitRightShiftAssignment(final IJCTRightShiftAssignment rightShiftAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitRightShiftAssignment(rightShiftAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitUnsignedRightShiftAssignment(final IJCTUnsignedRightShiftAssignment unsignedRightShiftAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitUnsignedRightShiftAssignment(unsignedRightShiftAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitXorAssignment(final IJCTXorAssignment xorAssignmentElement, final Integer additionalParameter)
    { return this.reduce(super.visitXorAssignment(xorAssignmentElement, additionalParameter), 1); }

    @Override
    public Integer visitBitwiseComplement(final IJCTBitwiseComplement bitwiseComplementElement, final Integer additionalParameter)
    { return this.reduce(super.visitBitwiseComplement(bitwiseComplementElement, additionalParameter), 1); }

    @Override
    public Integer visitUnaryMinus(final IJCTUnaryMinus unaryMinusElement, final Integer additionalParameter)
    { return this.reduce(super.visitUnaryMinus(unaryMinusElement, additionalParameter), 1); }

    @Override
    public Integer visitUnaryPlus(final IJCTUnaryPlus unaryPlusElement, final Integer additionalParameter)
    { return this.reduce(super.visitUnaryPlus(unaryPlusElement, additionalParameter), 1); }

    @Override
    public Integer visitPrefixDecrement(final IJCTPrefixDecrement prefixDecrementElement, final Integer additionalParameter)
    { return this.reduce(super.visitPrefixDecrement(prefixDecrementElement, additionalParameter), 1); }

    @Override
    public Integer visitPrefixIncrement(final IJCTPrefixIncrement prefixIncrementElement, final Integer additionalParameter)
    { return this.reduce(super.visitPrefixIncrement(prefixIncrementElement, additionalParameter), 1); }

    @Override
    public Integer visitPostfixDecrement(final IJCTPostfixDecrement postfixDecrementElement, final Integer additionalParameter)
    { return this.reduce(super.visitPostfixDecrement(postfixDecrementElement, additionalParameter), 1); }

    @Override
    public Integer visitPostfixIncrement(final IJCTPostfixIncrement postfixIncrementElement, final Integer additionalParameter)
    { return this.reduce(super.visitPostfixIncrement(postfixIncrementElement, additionalParameter), 1); }

    @Override
    public Integer visitLogicalComplement(final IJCTLogicalComplement logicalComplementElement, final Integer additionalParameter)
    { return this.reduce(super.visitLogicalComplement(logicalComplementElement, additionalParameter), 1); }

    @Override
    public Integer visitEqualTo(final IJCTEqualTo equalToElement, final Integer additionalParameter)
    { return this.reduce(super.visitEqualTo(equalToElement, additionalParameter), 1); }

    @Override
    public Integer visitNotEqualTo(final IJCTNotEqualTo notEqualToElement, final Integer additionalParameter)
    { return this.reduce(super.visitNotEqualTo(notEqualToElement, additionalParameter), 1); }

    @Override
    public Integer visitConditionalAnd(final IJCTConditionalAnd conditionalAndElement, final Integer additionalParameter)
    { return this.reduce(super.visitConditionalAnd(conditionalAndElement, additionalParameter), 1); }

    @Override
    public Integer visitConditionalOr(final IJCTConditionalOr conditionalOrElement, final Integer additionalParameter)
    { return this.reduce(super.visitConditionalOr(conditionalOrElement, additionalParameter), 1); }

    @Override
    public Integer visitLessThan(final IJCTLessThan lessThanElement, final Integer additionalParameter)
    { return this.reduce(super.visitLessThan(lessThanElement, additionalParameter), 1); }

    @Override
    public Integer visitLessThanOrEqual(final IJCTLessThanOrEqual lessThanOrEqualElement, final Integer additionalParameter)
    { return this.reduce(super.visitLessThanOrEqual(lessThanOrEqualElement, additionalParameter), 1); }

    @Override
    public Integer visitGreaterThan(final IJCTGreaterThan greaterThanElement, final Integer additionalParameter)
    { return this.reduce(super.visitGreaterThan(greaterThanElement, additionalParameter), 1); }

    @Override
    public Integer visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual greaterThanOrEqualElement, final Integer additionalParameter)
    { return this.reduce(super.visitGreaterThanOrEqual(greaterThanOrEqualElement, additionalParameter), 1); }

    @Override
    public Integer visitAnd(final IJCTAnd andElement, final Integer additionalParameter)
    { return this.reduce(super.visitAnd(andElement, additionalParameter), 1); }

    @Override
    public Integer visitDivide(final IJCTDivide divideElement, final Integer additionalParameter)
    { return this.reduce(super.visitDivide(divideElement, additionalParameter), 1); }

    @Override
    public Integer visitLeftShift(final IJCTLeftShift leftShiftElement, final Integer additionalParameter)
    { return this.reduce(super.visitLeftShift(leftShiftElement, additionalParameter), 1); }

    @Override
    public Integer visitMinus(final IJCTMinus minusElement, final Integer additionalParameter)
    { return this.reduce(super.visitMinus(minusElement, additionalParameter), 1); }

    @Override
    public Integer visitMultiply(final IJCTMultiply multiplyElement, final Integer additionalParameter)
    { return this.reduce(super.visitMultiply(multiplyElement, additionalParameter), 1); }

    @Override
    public Integer visitOr(final IJCTOr orElement, final Integer additionalParameter)
    { return this.reduce(super.visitOr(orElement, additionalParameter), 1); }

    @Override
    public Integer visitPlus(final IJCTPlus plusElement, final Integer additionalParameter)
    { return this.reduce(super.visitPlus(plusElement, additionalParameter), 1); }

    @Override
    public Integer visitRemainder(final IJCTRemainder remainderElement, final Integer additionalParameter)
    { return this.reduce(super.visitRemainder(remainderElement, additionalParameter), 1); }

    @Override
    public Integer visitRightShift(final IJCTRightShift rightShiftElement, final Integer additionalParameter)
    { return this.reduce(super.visitRightShift(rightShiftElement, additionalParameter), 1); }

    @Override
    public Integer visitUnsignedRightShift(final IJCTUnsignedRightShift unsignedRightShiftElement, final Integer additionalParameter)
    { return this.reduce(super.visitUnsignedRightShift(unsignedRightShiftElement, additionalParameter), 1); }

    @Override
    public Integer visitXor(final IJCTXor xorElement, final Integer additionalParameter)
    { return this.reduce(super.visitXor(xorElement, additionalParameter), 1); }

    @Override
    public Integer visitConditionalOperator(final IJCTConditionalOperator conditionalOperatorElement, final Integer additionalParameter)
    { return this.reduce(super.visitConditionalOperator(conditionalOperatorElement, additionalParameter), 1); }

    @Override
    public Integer visitInstanceOf(final IJCTInstanceOf instanceOfElement, final Integer additionalParameter)
    { return this.reduce(super.visitInstanceOf(instanceOfElement, additionalParameter), 1); }

    @Override
    public Integer visitCast(final IJCTCast castElement, final Integer additionalParameter)
    { return this.reduce(super.visitCast(castElement, additionalParameter), 1); }

    @Override
    public Integer visitNewArray(final IJCTNewArray newArrayElement, final Integer additionalParameter)
    { return this.reduce(super.visitNewArray(newArrayElement, additionalParameter), 1); }

    @Override
    public Integer visitNewClass(final IJCTNewClass newClassElement, final Integer additionalParameter)
    { return this.reduce(super.visitNewClass(newClassElement, additionalParameter), 1); }

    @Override
    public Integer visitBooleanLiteral(final IJCTBooleanLiteral booleanLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitBooleanLiteral(booleanLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitDoubleLiteral(final IJCTDoubleLiteral doubleLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitDoubleLiteral(doubleLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitFloatLiteral(final IJCTFloatLiteral floatLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitFloatLiteral(floatLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitIntegerLiteral(final IJCTIntegerLiteral integerLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitIntegerLiteral(integerLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitLongLiteral(final IJCTLongLiteral longLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitLongLiteral(longLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitCharacterLiteral(final IJCTCharacterLiteral characterLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitCharacterLiteral(characterLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitStringLiteral(final IJCTStringLiteral stringLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitStringLiteral(stringLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitNullLiteral(final IJCTNullLiteral nullLiteralElement, final Integer additionalParameter)
    { return this.reduce(super.visitNullLiteral(nullLiteralElement, additionalParameter), 1); }

    @Override
    public Integer visitParenthesis(final IJCTParenthesis parenthesisElement, final Integer additionalParameter)
    { return this.reduce(super.visitParenthesis(parenthesisElement, additionalParameter), 1); }

    @Override
    public Integer visitEmptyStatement(final IJCTEmptyStatement emptyStatementElement, final Integer additionalParameter)
    { return this.reduce(super.visitEmptyStatement(emptyStatementElement, additionalParameter), 1); }

    @Override
    public <Member extends IJCTClassMember> Integer visitMemberSelector(final IJCTMemberSelector<Member> memberSelectorElement, final Integer additionalParameter)
    { return this.reduce(super.visitMemberSelector(memberSelectorElement, additionalParameter), 1); }

    @Override
    public <Identifiable extends IJCTIdentifiable> Integer visitSimpleSelector(final IJCTSimpleSelector<Identifiable> simpleSelectorElement, final Integer additionalParameter)
    { return this.reduce(super.visitSimpleSelector(simpleSelectorElement, additionalParameter), 1); }

    @Override
    public Integer visitErroneousSelector(final IJCTErroneousSelector<?> erroneousSelectorElement, final Integer additionalParameter)
    { return this.reduce(super.visitErroneousSelector(erroneousSelectorElement, additionalParameter), 1); }

    @Override
    public Integer visitErroneousExpression(final IJCTErroneousExpression erroneousExpressionElement, final Integer additionalParameter)
    { return this.reduce(super.visitErroneousExpression(erroneousExpressionElement, additionalParameter), 1); }
}
