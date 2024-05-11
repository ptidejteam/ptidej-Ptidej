/**
 * @author Mathieu Lemoine
 * @created 2008-08-18 (月)
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

package jct.test.rsc.jct.kernel;

/**
 * This interface is a defaut for a Visiting facility
 *
 * @author Mathieu Lemoine
 */
public interface IJCTVisitor<R, P> 
{
    /**
     * Visits a root node.
     */
    public R visitRootNode(final IJCTRootNode rootNodeElement, final P additionalParameter);
    
    /**
     * Visits a primitive type.
     */
    public R visitPrimitiveType(final IJCTPrimitiveType primitiveTypeElement, final P additionalParameter);
    
    /**
     * Visits a array type.
     */
    public R visitArrayType(final IJCTArrayType arrayTypeElement, final P additionalParameter);
    
    /**
     * Visits a class type.
     */
    public R visitClassType(final IJCTClassType classTypeElement, final P additionalParameter);
    
    /**
     * Visits a intersection type.
     */
    public R visitIntersectionType(final IJCTIntersectionType intersectionTypeElement, final P additionalParameter);
    
    /**
     * Visits a package.
     */
    public R visitPackage(final IJCTPackage packageElement, final P additionalParameter);
    
    /**
     * Visits a compilation unit.
     */
    public R visitCompilationUnit(final IJCTCompilationUnit compilationUnitElement, final P additionalParameter);
    
    /**
     * Visits a comment.
     */
    public R visitComment(final IJCTComment commentElement, final P additionalParameter);
    
    /**
     * Visits a import.
     */
    public R visitImport(final IJCTImport importElement, final P additionalParameter);
    
    /**
     * Visits a class.
     */
    public R visitClass(final IJCTClass classElement, final P additionalParameter);
    
    /**
     * Visits a method.
     */
    public R visitMethod(final IJCTMethod methodElement, final P additionalParameter);
    
    /**
     * Visits a method invocation.
     */
    public R visitMethodInvocation(final IJCTMethodInvocation methodInvocationElement, final P additionalParameter);
    
    /**
     * Visits a variable.
     */
    public R visitVariable(final IJCTVariable variableElement, final P additionalParameter);
    
    /**
     * Visits a parameter.
     */
    public R visitParameter(final IJCTParameter parameterElement, final P additionalParameter);
    
    /**
     * Visits a field.
     */
    public R visitField(final IJCTField fieldElement, final P additionalParameter);
    
    /**
     * Visits a block.
     */
    public R visitBlock(final IJCTBlock blockElement, final P additionalParameter);
    
    /**
     * Visits a assert.
     */
    public R visitAssert(final IJCTAssert assertElement, final P additionalParameter);
    
    /**
     * Visits a break.
     */
    public R visitBreak(final IJCTBreak breakElement, final P additionalParameter);
    
    /**
     * Visits a continue.
     */
    public R visitContinue(final IJCTContinue continueElement, final P additionalParameter);
    
    /**
     * Visits a label.
     */
    public R visitLabel(final IJCTLabel labelElement, final P additionalParameter);
    
    /**
     * Visits a do while.
     */
    public R visitDoWhile(final IJCTDoWhile doWhileElement, final P additionalParameter);
    
    /**
     * Visits a while.
     */
    public R visitWhile(final IJCTWhile whileElement, final P additionalParameter);
    
    /**
     * Visits a for.
     */
    public R visitFor(final IJCTFor forElement, final P additionalParameter);
    
    /**
     * Visits a enhanced for.
     */
    public R visitEnhancedFor(final IJCTEnhancedFor enhancedForElement, final P additionalParameter);
    
    /**
     * Visits a if.
     */
    public R visitIf(final IJCTIf ifElement, final P additionalParameter);
    
    /**
     * Visits a return.
     */
    public R visitReturn(final IJCTReturn returnElement, final P additionalParameter);
    
    /**
     * Visits a switch.
     */
    public R visitSwitch(final IJCTSwitch switchElement, final P additionalParameter);
    
    /**
     * Visits a case.
     */
    public R visitCase(final IJCTCase caseElement, final P additionalParameter);
    
    /**
     * Visits a synchronized.
     */
    public R visitSynchronized(final IJCTSynchronized synchronizedElement, final P additionalParameter);
    
    /**
     * Visits a throw.
     */
    public R visitThrow(final IJCTThrow throwElement, final P additionalParameter);
    
    /**
     * Visits a try.
     */
    public R visitTry(final IJCTTry tryElement, final P additionalParameter);
    
    /**
     * Visits a catch.
     */
    public R visitCatch(final IJCTCatch catchElement, final P additionalParameter);
    
    /**
     * Visits a array access.
     */
    public R visitArrayAccess(final IJCTArrayAccess arrayAccessElement, final P additionalParameter);
    
    /**
     * Visits a expression statement.
     */
    public R visitExpressionStatement(final IJCTExpressionStatement expressionStatementElement, final P additionalParameter);
    
    /**
     * Visits a assignment.
     */
    public R visitAssignment(final IJCTAssignment assignmentElement, final P additionalParameter);
    
    /**
     * Visits a and assignment.
     */
    public R visitAndAssignment(final IJCTAndAssignment andAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a divide assignment.
     */
    public R visitDivideAssignment(final IJCTDivideAssignment divideAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a left shift assignment.
     */
    public R visitLeftShiftAssignment(final IJCTLeftShiftAssignment leftShiftAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a minus assignment.
     */
    public R visitMinusAssignment(final IJCTMinusAssignment minusAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a multiply assignment.
     */
    public R visitMultiplyAssignment(final IJCTMultiplyAssignment multiplyAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a or assignment.
     */
    public R visitOrAssignment(final IJCTOrAssignment orAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a plus assignment.
     */
    public R visitPlusAssignment(final IJCTPlusAssignment plusAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a remainder assignment.
     */
    public R visitRemainderAssignment(final IJCTRemainderAssignment remainderAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a right shift assignment.
     */
    public R visitRightShiftAssignment(final IJCTRightShiftAssignment rightShiftAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a unsigned right shift assignment.
     */
    public R visitUnsignedRightShiftAssignment(final IJCTUnsignedRightShiftAssignment unsignedRightShiftAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a xor assignment.
     */
    public R visitXorAssignment(final IJCTXorAssignment xorAssignmentElement, final P additionalParameter);
    
    /**
     * Visits a bitwise complement.
     */
    public R visitBitwiseComplement(final IJCTBitwiseComplement bitwiseComplementElement, final P additionalParameter);
    
    /**
     * Visits a unary minus.
     */
    public R visitUnaryMinus(final IJCTUnaryMinus unaryMinusElement, final P additionalParameter);
    
    /**
     * Visits a unary plus.
     */
    public R visitUnaryPlus(final IJCTUnaryPlus unaryPlusElement, final P additionalParameter);
    
    /**
     * Visits a prefix decrement.
     */
    public R visitPrefixDecrement(final IJCTPrefixDecrement prefixDecrementElement, final P additionalParameter);
    
    /**
     * Visits a prefix increment.
     */
    public R visitPrefixIncrement(final IJCTPrefixIncrement prefixIncrementElement, final P additionalParameter);
    
    /**
     * Visits a postfix decrement.
     */
    public R visitPostfixDecrement(final IJCTPostfixDecrement postfixDecrementElement, final P additionalParameter);
    
    /**
     * Visits a postfix increment.
     */
    public R visitPostfixIncrement(final IJCTPostfixIncrement postfixIncrementElement, final P additionalParameter);
    
    /**
     * Visits a logical complement.
     */
    public R visitLogicalComplement(final IJCTLogicalComplement logicalComplementElement, final P additionalParameter);
    
    /**
     * Visits a equal to.
     */
    public R visitEqualTo(final IJCTEqualTo equalToElement, final P additionalParameter);
    
    /**
     * Visits a not equal to.
     */
    public R visitNotEqualTo(final IJCTNotEqualTo notEqualToElement, final P additionalParameter);
    
    /**
     * Visits a conditional and.
     */
    public R visitConditionalAnd(final IJCTConditionalAnd conditionalAndElement, final P additionalParameter);
    
    /**
     * Visits a conditional or.
     */
    public R visitConditionalOr(final IJCTConditionalOr conditionalOrElement, final P additionalParameter);
    
    /**
     * Visits a less than.
     */
    public R visitLessThan(final IJCTLessThan lessThanElement, final P additionalParameter);
    
    /**
     * Visits a less than or equal.
     */
    public R visitLessThanOrEqual(final IJCTLessThanOrEqual lessThanOrEqualElement, final P additionalParameter);
    
    /**
     * Visits a greater than.
     */
    public R visitGreaterThan(final IJCTGreaterThan greaterThanElement, final P additionalParameter);
    
    /**
     * Visits a greater than or equal.
     */
    public R visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual greaterThanOrEqualElement, final P additionalParameter);
    
    /**
     * Visits a and.
     */
    public R visitAnd(final IJCTAnd andElement, final P additionalParameter);
    
    /**
     * Visits a divide.
     */
    public R visitDivide(final IJCTDivide divideElement, final P additionalParameter);
    
    /**
     * Visits a left shift.
     */
    public R visitLeftShift(final IJCTLeftShift leftShiftElement, final P additionalParameter);
    
    /**
     * Visits a minus.
     */
    public R visitMinus(final IJCTMinus minusElement, final P additionalParameter);
    
    /**
     * Visits a multiply.
     */
    public R visitMultiply(final IJCTMultiply multiplyElement, final P additionalParameter);
    
    /**
     * Visits a or.
     */
    public R visitOr(final IJCTOr orElement, final P additionalParameter);
    
    /**
     * Visits a plus.
     */
    public R visitPlus(final IJCTPlus plusElement, final P additionalParameter);
    
    /**
     * Visits a remainder.
     */
    public R visitRemainder(final IJCTRemainder remainderElement, final P additionalParameter);
    
    /**
     * Visits a right shift.
     */
    public R visitRightShift(final IJCTRightShift rightShiftElement, final P additionalParameter);
    
    /**
     * Visits a unsigned right shift.
     */
    public R visitUnsignedRightShift(final IJCTUnsignedRightShift unsignedRightShiftElement, final P additionalParameter);
    
    /**
     * Visits a xor.
     */
    public R visitXor(final IJCTXor xorElement, final P additionalParameter);
    
    /**
     * Visits a conditional operator.
     */
    public R visitConditionalOperator(final IJCTConditionalOperator conditionalOperatorElement, final P additionalParameter);
    
    /**
     * Visits a instance of.
     */
    public R visitInstanceOf(final IJCTInstanceOf instanceOfElement, final P additionalParameter);
    
    /**
     * Visits a cast.
     */
    public R visitCast(final IJCTCast castElement, final P additionalParameter);
    
    /**
     * Visits a new array.
     */
    public R visitNewArray(final IJCTNewArray newArrayElement, final P additionalParameter);
    
    /**
     * Visits a new class.
     */
    public R visitNewClass(final IJCTNewClass newClassElement, final P additionalParameter);
    
    /**
     * Visits a boolean literal.
     */
    public R visitBooleanLiteral(final IJCTBooleanLiteral booleanLiteralElement, final P additionalParameter);
    
    /**
     * Visits a double literal.
     */
    public R visitDoubleLiteral(final IJCTDoubleLiteral doubleLiteralElement, final P additionalParameter);
    
    /**
     * Visits a float literal.
     */
    public R visitFloatLiteral(final IJCTFloatLiteral floatLiteralElement, final P additionalParameter);
    
    /**
     * Visits a integer literal.
     */
    public R visitIntegerLiteral(final IJCTIntegerLiteral integerLiteralElement, final P additionalParameter);
    
    /**
     * Visits a long literal.
     */
    public R visitLongLiteral(final IJCTLongLiteral longLiteralElement, final P additionalParameter);
    
    /**
     * Visits a character literal.
     */
    public R visitCharacterLiteral(final IJCTCharacterLiteral characterLiteralElement, final P additionalParameter);
    
    /**
     * Visits a string literal.
     */
    public R visitStringLiteral(final IJCTStringLiteral stringLiteralElement, final P additionalParameter);
    
    /**
     * Visits a null literal.
     */
    public R visitNullLiteral(final IJCTNullLiteral nullLiteralElement, final P additionalParameter);
    
    /**
     * Visits a parenthesis.
     */
    public R visitParenthesis(final IJCTParenthesis parenthesisElement, final P additionalParameter);
    
    /**
     * Visits a empty statement.
     */
    public R visitEmptyStatement(final IJCTEmptyStatement emptyStatementElement, final P additionalParameter);
    
    /**
     * Visits a member selector.
     */
    public <Member extends IJCTClassMember> R visitMemberSelector(final IJCTMemberSelector<Member> memberSelectorElement, final P additionalParameter);
    
    /**
     * Visits a simple selector.
     */
    public <Identifiable extends IJCTIdentifiable> R visitSimpleSelector(final IJCTSimpleSelector<Identifiable> simpleSelectorElement, final P additionalParameter);
    
    /**
     * Visits a erroneous selector.
     */
    public R visitErroneousSelector(final IJCTErroneousSelector erroneousSelectorElement, final P additionalParameter);
    
    /**
     * Visits a erroneous expression.
     */
    public R visitErroneousExpression(final IJCTErroneousExpression erroneousExpressionElement, final P additionalParameter);
    

}
