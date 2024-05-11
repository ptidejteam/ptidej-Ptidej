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

package jct.kernel;

import java.io.File;

/**
 * This interface is a factory for creating the elements of a Javac AST. All the
 * elements of a JCT must be created by its associated factory or a compatible
 * one. The implementation of this interface must provide facilities to create
 * and rename a JCT (IJCTRootNode). The recommended solution being to have two
 * public static methods : {@code public static IJCTRootNode createJCT(String
 * name)} and {@code public static IJCTRootNode renameJCT(IJCTRootNode jct,
 * String new_name)}. The rename method should respect : "Returns the version of
 * {@literal jct} with the new name. The two JCT use compatible factories. Any
 * modification applied to any of the both JCT is propagated to the other (as if
 * they were using the same backstore)."
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTFactory {
	/**
	 * Returns the JCT associated with this factory
	 */
	public IJCTRootNode getJCT();

	/**
	 * Test factory compatibility
	 */
	public boolean equals(final Object o);

	/**
	 * Returns the hash code following the strict contract of
	 * {@link java.lang.Object#hashCode()}. I.e. : two factories a and b has the
	 * same hashcode iff a.equals(b) returns true
	 */
	public int hashCode();

	/**
	 * Returns a new primitive type.
	 */
	public IJCTPrimitiveType createPrimitiveType(
		final JCTPrimitiveTypes aPrimitiveTypeConstant);

	/**
	 * Returns a new class type.
	 */
	public IJCTClassType createClassType(
		final IJCTSelector<IJCTClass> classDeclaration);

	/**
	 * Returns a new package.
	 */
	public IJCTPackage createPackage(final String name, final boolean isGhost);

	/**
	 * Returns a new compilation unit.
	 */
	public IJCTCompilationUnit createCompilationUnit(final File sourceFile);

	/**
	 * Returns a new comment.
	 */
	public IJCTComment createComment(
		final boolean isEndOfLine,
		final String text);

	/**
	 * Returns a new import.
	 */
	public IJCTImport createImport(
		final IJCTImportable importedElement,
		final boolean isStatic,
		final boolean isOnDemand);

	/**
	 * Returns a new class.
	 */
	public IJCTClass createClass(
		final String name,
		final boolean isInterface,
		final boolean isGhost);

	/**
	 * Returns a new method.
	 */
	public IJCTMethod createMethod(final String name);

	/**
	 * Returns a new method invocation.
	 */
	public IJCTMethodInvocation createMethodInvocation(
		final IJCTSelector<IJCTMethod> methodSelector);

	/**
	 * Returns a new variable.
	 */
	public IJCTVariable createVariable(final String name);

	/**
	 * Returns a new parameter.
	 */
	public IJCTParameter createParameter(final String name);

	/**
	 * Returns a new field.
	 */
	public IJCTField createField(final String name);

	/**
	 * Returns a new block.
	 */
	public IJCTBlock createBlock();

	/**
	 * Returns a new assert.
	 */
	public IJCTAssert createAssert(final IJCTExpression condition);

	/**
	 * Returns a new break.
	 */
	public IJCTBreak createBreak();

	/**
	 * Returns a new continue.
	 */
	public IJCTContinue createContinue();

	/**
	 * Returns a new label.
	 */
	public IJCTLabel createLabel(
		final String name,
		final IJCTStatement statement);

	/**
	 * Returns a new do while.
	 */
	public IJCTDoWhile createDoWhile(final IJCTExpression condition);

	/**
	 * Returns a new while.
	 */
	public IJCTWhile createWhile(final IJCTExpression condition);

	/**
	 * Returns a new for.
	 */
	public IJCTFor createFor(
		final IJCTExpression condition,
		final IJCTStatement body);

	/**
	 * Returns a new enhanced for.
	 */
	public IJCTEnhancedFor createEnhancedFor(
		final IJCTVariable variable,
		final IJCTExpression iterable,
		final IJCTStatement body);

	/**
	 * Returns a new if.
	 */
	public IJCTIf createIf(
		final IJCTExpression condition,
		final IJCTStatement thenStatement);

	/**
	 * Returns a new return.
	 */
	public IJCTReturn createReturn();

	/**
	 * Returns a new switch.
	 */
	public IJCTSwitch createSwitch(final IJCTExpression expression);

	/**
	 * Returns a new case.
	 */
	public IJCTCase createCase();

	/**
	 * Returns a new synchronized.
	 */
	public IJCTSynchronized createSynchronized(
		final IJCTExpression synchronizedObject);

	/**
	 * Returns a new throw.
	 */
	public IJCTThrow createThrow(final IJCTExpression thrownException);

	/**
	 * Returns a new try.
	 */
	public IJCTTry createTry();

	/**
	 * Returns a new catch.
	 */
	public IJCTCatch createCatch(final IJCTVariable variable);

	/**
	 * Returns a new array access.
	 */
	public IJCTArrayAccess createArrayAccess(
		final IJCTExpression array,
		final IJCTExpression index);

	/**
	 * Returns a new expression statement.
	 */
	public IJCTExpressionStatement createExpressionStatement(
		final IJCTExpression expression);

	/**
	 * Returns a new assignment.
	 */
	public IJCTAssignment createAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new and assignment.
	 */
	public IJCTAndAssignment createAndAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new divide assignment.
	 */
	public IJCTDivideAssignment createDivideAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new left shift assignment.
	 */
	public IJCTLeftShiftAssignment createLeftShiftAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new minus assignment.
	 */
	public IJCTMinusAssignment createMinusAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new multiply assignment.
	 */
	public IJCTMultiplyAssignment createMultiplyAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new or assignment.
	 */
	public IJCTOrAssignment createOrAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new plus assignment.
	 */
	public IJCTPlusAssignment createPlusAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new remainder assignment.
	 */
	public IJCTRemainderAssignment createRemainderAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new right shift assignment.
	 */
	public IJCTRightShiftAssignment createRightShiftAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new unsigned right shift assignment.
	 */
	public IJCTUnsignedRightShiftAssignment createUnsignedRightShiftAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new xor assignment.
	 */
	public IJCTXorAssignment createXorAssignment(
		final IJCTExpression variable,
		final IJCTExpression value);

	/**
	 * Returns a new bitwise complement.
	 */
	public IJCTBitwiseComplement createBitwiseComplement(
		final IJCTExpression operand);

	/**
	 * Returns a new unary minus.
	 */
	public IJCTUnaryMinus createUnaryMinus(final IJCTExpression operand);

	/**
	 * Returns a new unary plus.
	 */
	public IJCTUnaryPlus createUnaryPlus(final IJCTExpression operand);

	/**
	 * Returns a new prefix decrement.
	 */
	public IJCTPrefixDecrement createPrefixDecrement(
		final IJCTExpression operand);

	/**
	 * Returns a new prefix increment.
	 */
	public IJCTPrefixIncrement createPrefixIncrement(
		final IJCTExpression operand);

	/**
	 * Returns a new postfix decrement.
	 */
	public IJCTPostfixDecrement createPostfixDecrement(
		final IJCTExpression operand);

	/**
	 * Returns a new postfix increment.
	 */
	public IJCTPostfixIncrement createPostfixIncrement(
		final IJCTExpression operand);

	/**
	 * Returns a new logical complement.
	 */
	public IJCTLogicalComplement createLogicalComplement(
		final IJCTExpression operand);

	/**
	 * Returns a new equal to.
	 */
	public IJCTEqualTo createEqualTo(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new not equal to.
	 */
	public IJCTNotEqualTo createNotEqualTo(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new conditional and.
	 */
	public IJCTConditionalAnd createConditionalAnd(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new conditional or.
	 */
	public IJCTConditionalOr createConditionalOr(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new less than.
	 */
	public IJCTLessThan createLessThan(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new less than or equal.
	 */
	public IJCTLessThanOrEqual createLessThanOrEqual(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new greater than.
	 */
	public IJCTGreaterThan createGreaterThan(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new greater than or equal.
	 */
	public IJCTGreaterThanOrEqual createGreaterThanOrEqual(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new and.
	 */
	public IJCTAnd createAnd(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new divide.
	 */
	public IJCTDivide createDivide(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new left shift.
	 */
	public IJCTLeftShift createLeftShift(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new minus.
	 */
	public IJCTMinus createMinus(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new multiply.
	 */
	public IJCTMultiply createMultiply(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new or.
	 */
	public IJCTOr createOr(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new plus.
	 */
	public IJCTPlus createPlus(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new remainder.
	 */
	public IJCTRemainder createRemainder(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new right shift.
	 */
	public IJCTRightShift createRightShift(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new unsigned right shift.
	 */
	public IJCTUnsignedRightShift createUnsignedRightShift(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new xor.
	 */
	public IJCTXor createXor(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand);

	/**
	 * Returns a new conditional operator.
	 */
	public IJCTConditionalOperator createConditionalOperator(
		final IJCTExpression condition,
		final IJCTExpression thenExpression,
		final IJCTExpression elseExpression);

	/**
	 * Returns a new instance of.
	 */
	public IJCTInstanceOf createInstanceOf(
		final IJCTExpression operand,
		final IJCTType type);

	/**
	 * Returns a new cast.
	 */
	public IJCTCast createCast(final IJCTType type, final IJCTExpression operand);

	/**
	 * Returns a new new array.
	 */
	public IJCTNewArray createNewArray(final IJCTType elementType);

	/**
	 * Returns a new new class.
	 */
	public IJCTNewClass createNewClass(final IJCTClassType classType);

	/**
	 * Returns a new boolean literal.
	 */
	public IJCTBooleanLiteral createBooleanLiteral(final boolean value);

	/**
	 * Returns a new double literal.
	 */
	public IJCTDoubleLiteral createDoubleLiteral(final double value);

	/**
	 * Returns a new float literal.
	 */
	public IJCTFloatLiteral createFloatLiteral(final float value);

	/**
	 * Returns a new integer literal.
	 */
	public IJCTIntegerLiteral createIntegerLiteral(final int value);

	/**
	 * Returns a new long literal.
	 */
	public IJCTLongLiteral createLongLiteral(final long value);

	/**
	 * Returns a new character literal.
	 */
	public IJCTCharacterLiteral createCharacterLiteral(final char value);

	/**
	 * Returns a new string literal.
	 */
	public IJCTStringLiteral createStringLiteral(final String value);

	/**
	 * Returns a new null literal.
	 */
	public IJCTNullLiteral createNullLiteral();

	/**
	 * Returns a new parenthesis.
	 */
	public IJCTParenthesis createParenthesis(final IJCTExpression expression);

	/**
	 * Returns a new empty statement.
	 */
	public IJCTEmptyStatement createEmptyStatement();

	/**
	 * Returns a new member selector.
	 */
	public <Member extends IJCTClassMember> IJCTMemberSelector<Member> createMemberSelector(
		final IJCTExpression qualifyingExpression,
		final Member member);

	/**
	 * Returns a new simple selector.
	 */
	public <Identifiable extends IJCTIdentifiable> IJCTSimpleSelector<Identifiable> createSimpleSelector(
		final Identifiable anIdentifiable);

	/**
	 * Returns a new erroneous selector.
	 */
	public IJCTErroneousSelector<?> createErroneousSelector(
		final String anIdentifier);

	/**
	 * Returns a new erroneous expression.
	 */
	public IJCTErroneousExpression createErroneousExpression();

}
