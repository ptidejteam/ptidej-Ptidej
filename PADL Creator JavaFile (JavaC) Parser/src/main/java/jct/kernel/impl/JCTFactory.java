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

package jct.kernel.impl;

import java.io.File;
import jct.kernel.*;

/**
 * This interface is a factory for creating the elements of a Javac AST.
 * All the elements of a JCT must be created by its associated factory or a compatible one.
 * The implementation of this interface must provide facilities to create and rename a JCT (IJCTRootNode).
 * The recommended solution being to have two public static methods :
 *  {@code public static IJCTRootNode createJCT(String name)} and
 *  {@code public static IJCTRootNode renameJCT(IJCTRootNode jct, String new_name)}.
 * The rename method should respect : "Returns the version of {@literal jct} with the new name.
 * The two JCT use compatible factories. Any modification applied to any of the both
 * JCT is propagated to the other (as if they were using the same backstore)."
 *
 * @author Mathieu Lemoine
 */
public class JCTFactory implements IJCTFactory {
	/**
	 * original root node of this factory
	 */
	private final IJCTRootNode originalRootNode;

	/**
	 * associated root node of this factory
	 */
	private final IJCTRootNode associatedRootNode;

	JCTFactory(final IJCTRootNode originalRootNode) {
		this(originalRootNode, null);
	}

	JCTFactory(
		final IJCTRootNode originalRootNode,
		final IJCTRootNode associatedRootNode) {
		this.originalRootNode = originalRootNode;
		this.associatedRootNode = associatedRootNode;
	}

	/**
	 * Returns the JCT associated with this factory
	 */
	public IJCTRootNode getJCT() {
		return this.associatedRootNode;
	}

	/**
	 * Test factory compatibility
	 */
	@Override
	public boolean equals(final Object o) {
		if (!(o instanceof JCTFactory))
			return false;
		return this.originalRootNode == ((JCTFactory) o).originalRootNode;
	}

	/**
	 * Returns the hash code following the strict contract of {@link java.lang.Object#hashCode()}.
	 * I.e. : two factories a and b has the same hashcode iff a.equals(b) returns true
	 */
	@Override
	public int hashCode() {
		return this.originalRootNode.hashCode();
	}

	/**
	 * Returns a new primitive type.
	 */
	public IJCTPrimitiveType createPrimitiveType(
		final JCTPrimitiveTypes aPrimitiveTypeConstant) {
		return new JCTPrimitiveType(
			this.originalRootNode,
			aPrimitiveTypeConstant);
	}

	/**
	 * Returns a new class type.
	 */
	public IJCTClassType createClassType(
		final IJCTSelector<IJCTClass> classDeclaration) {
		return new JCTClassType(this.originalRootNode, classDeclaration);
	}

	/**
	 * Returns a new package.
	 */
	public IJCTPackage createPackage(final String name, final boolean isGhost) {
		final JCTPackage result =
			new JCTPackage(this.originalRootNode, name, isGhost);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new compilation unit.
	 */
	public IJCTCompilationUnit createCompilationUnit(final File sourceFile) {
		final JCTCompilationUnit result =
			new JCTCompilationUnit(this.originalRootNode, sourceFile);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new comment.
	 */
	public IJCTComment createComment(
		final boolean isEndOfLine,
		final String text) {
		final JCTComment result =
			new JCTComment(this.originalRootNode, isEndOfLine, text);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new import.
	 */
	public IJCTImport createImport(
		final IJCTImportable importedElement,
		final boolean isStatic,
		final boolean isOnDemand) {
		final JCTImport result =
			new JCTImport(
				this.originalRootNode,
				importedElement,
				isStatic,
				isOnDemand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new class.
	 */
	public IJCTClass createClass(
		final String name,
		final boolean isInterface,
		final boolean isGhost) {
		final JCTClass result =
			new JCTClass(this.originalRootNode, name, isInterface, isGhost);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new method.
	 */
	public IJCTMethod createMethod(final String name) {
		final JCTMethod result = new JCTMethod(this.originalRootNode, name);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new method invocation.
	 */
	public IJCTMethodInvocation createMethodInvocation(
		final IJCTSelector<IJCTMethod> methodSelector) {
		final JCTMethodInvocation result =
			new JCTMethodInvocation(this.originalRootNode, methodSelector);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new variable.
	 */
	public IJCTVariable createVariable(final String name) {
		final JCTVariable result = new JCTVariable(this.originalRootNode, name);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new parameter.
	 */
	public IJCTParameter createParameter(final String name) {
		final JCTParameter result =
			new JCTParameter(this.originalRootNode, name);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new field.
	 */
	public IJCTField createField(final String name) {
		final JCTField result = new JCTField(this.originalRootNode, name);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new block.
	 */
	public IJCTBlock createBlock() {
		final JCTBlock result = new JCTBlock(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new assert.
	 */
	public IJCTAssert createAssert(final IJCTExpression condition) {
		final JCTAssert result =
			new JCTAssert(this.originalRootNode, condition);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new break.
	 */
	public IJCTBreak createBreak() {
		final JCTBreak result = new JCTBreak(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new continue.
	 */
	public IJCTContinue createContinue() {
		final JCTContinue result = new JCTContinue(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new label.
	 */
	public IJCTLabel createLabel(
		final String name,
		final IJCTStatement statement) {
		final JCTLabel result =
			new JCTLabel(this.originalRootNode, name, statement);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new do while.
	 */
	public IJCTDoWhile createDoWhile(final IJCTExpression condition) {
		final JCTDoWhile result =
			new JCTDoWhile(this.originalRootNode, condition);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new while.
	 */
	public IJCTWhile createWhile(final IJCTExpression condition) {
		final JCTWhile result = new JCTWhile(this.originalRootNode, condition);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new for.
	 */
	public IJCTFor createFor(
		final IJCTExpression condition,
		final IJCTStatement body) {
		final JCTFor result =
			new JCTFor(this.originalRootNode, condition, body);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new enhanced for.
	 */
	public IJCTEnhancedFor createEnhancedFor(
		final IJCTVariable variable,
		final IJCTExpression iterable,
		final IJCTStatement body) {
		final JCTEnhancedFor result =
			new JCTEnhancedFor(this.originalRootNode, variable, iterable, body);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new if.
	 */
	public IJCTIf createIf(
		final IJCTExpression condition,
		final IJCTStatement thenStatement) {
		final JCTIf result =
			new JCTIf(this.originalRootNode, condition, thenStatement);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new return.
	 */
	public IJCTReturn createReturn() {
		final JCTReturn result = new JCTReturn(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new switch.
	 */
	public IJCTSwitch createSwitch(final IJCTExpression expression) {
		final JCTSwitch result =
			new JCTSwitch(this.originalRootNode, expression);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new case.
	 */
	public IJCTCase createCase() {
		final JCTCase result = new JCTCase(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new synchronized.
	 */
	public IJCTSynchronized createSynchronized(
		final IJCTExpression synchronizedObject) {
		final JCTSynchronized result =
			new JCTSynchronized(this.originalRootNode, synchronizedObject);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new throw.
	 */
	public IJCTThrow createThrow(final IJCTExpression thrownException) {
		final JCTThrow result =
			new JCTThrow(this.originalRootNode, thrownException);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new try.
	 */
	public IJCTTry createTry() {
		final JCTTry result = new JCTTry(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new catch.
	 */
	public IJCTCatch createCatch(final IJCTVariable variable) {
		final JCTCatch result = new JCTCatch(this.originalRootNode, variable);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new array access.
	 */
	public IJCTArrayAccess createArrayAccess(
		final IJCTExpression array,
		final IJCTExpression index) {
		final JCTArrayAccess result =
			new JCTArrayAccess(this.originalRootNode, array, index);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new expression statement.
	 */
	public IJCTExpressionStatement createExpressionStatement(
		final IJCTExpression expression) {
		final JCTExpressionStatement result =
			new JCTExpressionStatement(this.originalRootNode, expression);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new assignment.
	 */
	public IJCTAssignment createAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTAssignment result =
			new JCTAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new and assignment.
	 */
	public IJCTAndAssignment createAndAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTAndAssignment result =
			new JCTAndAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new divide assignment.
	 */
	public IJCTDivideAssignment createDivideAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTDivideAssignment result =
			new JCTDivideAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new left shift assignment.
	 */
	public IJCTLeftShiftAssignment createLeftShiftAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTLeftShiftAssignment result =
			new JCTLeftShiftAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new minus assignment.
	 */
	public IJCTMinusAssignment createMinusAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTMinusAssignment result =
			new JCTMinusAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new multiply assignment.
	 */
	public IJCTMultiplyAssignment createMultiplyAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTMultiplyAssignment result =
			new JCTMultiplyAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new or assignment.
	 */
	public IJCTOrAssignment createOrAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTOrAssignment result =
			new JCTOrAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new plus assignment.
	 */
	public IJCTPlusAssignment createPlusAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTPlusAssignment result =
			new JCTPlusAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new remainder assignment.
	 */
	public IJCTRemainderAssignment createRemainderAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTRemainderAssignment result =
			new JCTRemainderAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new right shift assignment.
	 */
	public IJCTRightShiftAssignment createRightShiftAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTRightShiftAssignment result =
			new JCTRightShiftAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new unsigned right shift assignment.
	 */
	public IJCTUnsignedRightShiftAssignment createUnsignedRightShiftAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTUnsignedRightShiftAssignment result =
			new JCTUnsignedRightShiftAssignment(
				this.originalRootNode,
				variable,
				value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new xor assignment.
	 */
	public IJCTXorAssignment createXorAssignment(
		final IJCTExpression variable,
		final IJCTExpression value) {
		final JCTXorAssignment result =
			new JCTXorAssignment(this.originalRootNode, variable, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new bitwise complement.
	 */
	public IJCTBitwiseComplement createBitwiseComplement(
		final IJCTExpression operand) {
		final JCTBitwiseComplement result =
			new JCTBitwiseComplement(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new unary minus.
	 */
	public IJCTUnaryMinus createUnaryMinus(final IJCTExpression operand) {
		final JCTUnaryMinus result =
			new JCTUnaryMinus(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new unary plus.
	 */
	public IJCTUnaryPlus createUnaryPlus(final IJCTExpression operand) {
		final JCTUnaryPlus result =
			new JCTUnaryPlus(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new prefix decrement.
	 */
	public IJCTPrefixDecrement createPrefixDecrement(
		final IJCTExpression operand) {
		final JCTPrefixDecrement result =
			new JCTPrefixDecrement(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new prefix increment.
	 */
	public IJCTPrefixIncrement createPrefixIncrement(
		final IJCTExpression operand) {
		final JCTPrefixIncrement result =
			new JCTPrefixIncrement(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new postfix decrement.
	 */
	public IJCTPostfixDecrement createPostfixDecrement(
		final IJCTExpression operand) {
		final JCTPostfixDecrement result =
			new JCTPostfixDecrement(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new postfix increment.
	 */
	public IJCTPostfixIncrement createPostfixIncrement(
		final IJCTExpression operand) {
		final JCTPostfixIncrement result =
			new JCTPostfixIncrement(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new logical complement.
	 */
	public IJCTLogicalComplement createLogicalComplement(
		final IJCTExpression operand) {
		final JCTLogicalComplement result =
			new JCTLogicalComplement(this.originalRootNode, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new equal to.
	 */
	public IJCTEqualTo createEqualTo(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTEqualTo result =
			new JCTEqualTo(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new not equal to.
	 */
	public IJCTNotEqualTo createNotEqualTo(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTNotEqualTo result =
			new JCTNotEqualTo(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new conditional and.
	 */
	public IJCTConditionalAnd createConditionalAnd(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTConditionalAnd result =
			new JCTConditionalAnd(
				this.originalRootNode,
				leftOperand,
				rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new conditional or.
	 */
	public IJCTConditionalOr createConditionalOr(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTConditionalOr result =
			new JCTConditionalOr(
				this.originalRootNode,
				leftOperand,
				rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new less than.
	 */
	public IJCTLessThan createLessThan(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTLessThan result =
			new JCTLessThan(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new less than or equal.
	 */
	public IJCTLessThanOrEqual createLessThanOrEqual(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTLessThanOrEqual result =
			new JCTLessThanOrEqual(
				this.originalRootNode,
				leftOperand,
				rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new greater than.
	 */
	public IJCTGreaterThan createGreaterThan(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTGreaterThan result =
			new JCTGreaterThan(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new greater than or equal.
	 */
	public IJCTGreaterThanOrEqual createGreaterThanOrEqual(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTGreaterThanOrEqual result =
			new JCTGreaterThanOrEqual(
				this.originalRootNode,
				leftOperand,
				rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new and.
	 */
	public IJCTAnd createAnd(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTAnd result =
			new JCTAnd(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new divide.
	 */
	public IJCTDivide createDivide(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTDivide result =
			new JCTDivide(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new left shift.
	 */
	public IJCTLeftShift createLeftShift(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTLeftShift result =
			new JCTLeftShift(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new minus.
	 */
	public IJCTMinus createMinus(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTMinus result =
			new JCTMinus(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new multiply.
	 */
	public IJCTMultiply createMultiply(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTMultiply result =
			new JCTMultiply(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new or.
	 */
	public IJCTOr createOr(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTOr result =
			new JCTOr(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new plus.
	 */
	public IJCTPlus createPlus(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTPlus result =
			new JCTPlus(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new remainder.
	 */
	public IJCTRemainder createRemainder(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTRemainder result =
			new JCTRemainder(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new right shift.
	 */
	public IJCTRightShift createRightShift(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTRightShift result =
			new JCTRightShift(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new unsigned right shift.
	 */
	public IJCTUnsignedRightShift createUnsignedRightShift(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTUnsignedRightShift result =
			new JCTUnsignedRightShift(
				this.originalRootNode,
				leftOperand,
				rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new xor.
	 */
	public IJCTXor createXor(
		final IJCTExpression leftOperand,
		final IJCTExpression rightOperand) {
		final JCTXor result =
			new JCTXor(this.originalRootNode, leftOperand, rightOperand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new conditional operator.
	 */
	public IJCTConditionalOperator createConditionalOperator(
		final IJCTExpression condition,
		final IJCTExpression thenExpression,
		final IJCTExpression elseExpression) {
		final JCTConditionalOperator result =
			new JCTConditionalOperator(
				this.originalRootNode,
				condition,
				thenExpression,
				elseExpression);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new instance of.
	 */
	public IJCTInstanceOf createInstanceOf(
		final IJCTExpression operand,
		final IJCTType type) {
		final JCTInstanceOf result =
			new JCTInstanceOf(this.originalRootNode, operand, type);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new cast.
	 */
	public IJCTCast createCast(final IJCTType type, final IJCTExpression operand) {
		final JCTCast result =
			new JCTCast(this.originalRootNode, type, operand);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new new array.
	 */
	public IJCTNewArray createNewArray(final IJCTType elementType) {
		final JCTNewArray result =
			new JCTNewArray(this.originalRootNode, elementType);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new new class.
	 */
	public IJCTNewClass createNewClass(final IJCTClassType classType) {
		final JCTNewClass result =
			new JCTNewClass(this.originalRootNode, classType);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new boolean literal.
	 */
	public IJCTBooleanLiteral createBooleanLiteral(final boolean value) {
		final JCTBooleanLiteral result =
			new JCTBooleanLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new double literal.
	 */
	public IJCTDoubleLiteral createDoubleLiteral(final double value) {
		final JCTDoubleLiteral result =
			new JCTDoubleLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new float literal.
	 */
	public IJCTFloatLiteral createFloatLiteral(final float value) {
		final JCTFloatLiteral result =
			new JCTFloatLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new integer literal.
	 */
	public IJCTIntegerLiteral createIntegerLiteral(final int value) {
		final JCTIntegerLiteral result =
			new JCTIntegerLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new long literal.
	 */
	public IJCTLongLiteral createLongLiteral(final long value) {
		final JCTLongLiteral result =
			new JCTLongLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new character literal.
	 */
	public IJCTCharacterLiteral createCharacterLiteral(final char value) {
		final JCTCharacterLiteral result =
			new JCTCharacterLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new string literal.
	 */
	public IJCTStringLiteral createStringLiteral(final String value) {
		final JCTStringLiteral result =
			new JCTStringLiteral(this.originalRootNode, value);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new null literal.
	 */
	public IJCTNullLiteral createNullLiteral() {
		final JCTNullLiteral result = new JCTNullLiteral(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new parenthesis.
	 */
	public IJCTParenthesis createParenthesis(final IJCTExpression expression) {
		final JCTParenthesis result =
			new JCTParenthesis(this.originalRootNode, expression);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new empty statement.
	 */
	public IJCTEmptyStatement createEmptyStatement() {
		final JCTEmptyStatement result =
			new JCTEmptyStatement(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new member selector.
	 */
	public <Member extends IJCTClassMember> IJCTMemberSelector<Member> createMemberSelector(
		final IJCTExpression qualifyingExpression,
		final Member member) {
		final JCTMemberSelector result =
			new JCTMemberSelector(
				this.originalRootNode,
				qualifyingExpression,
				member);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new simple selector.
	 */
	public <Identifiable extends IJCTIdentifiable> IJCTSimpleSelector<Identifiable> createSimpleSelector(
		final Identifiable anIdentifiable) {
		final JCTSimpleSelector result =
			new JCTSimpleSelector(this.originalRootNode, anIdentifiable);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new erroneous selector.
	 */
	public IJCTErroneousSelector createErroneousSelector(
		final String anIdentifier) {
		final JCTErroneousSelector result =
			new JCTErroneousSelector(this.originalRootNode, anIdentifier);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	/**
	 * Returns a new erroneous expression.
	 */
	public IJCTErroneousExpression createErroneousExpression() {
		final JCTErroneousExpression result =
			new JCTErroneousExpression(this.originalRootNode);
		this.originalRootNode.addOrphan(result);
		return result;
	}

	public static IJCTRootNode createJCT(
		final String name,
		final boolean initialized) {
		final IJCTRootNode root = new JCTRootNode(name);

		if (!initialized)
			return root;

		/// Initializing
		final IJCTFactory f = root.getFactory();

		final IJCTPackage javaLang =
			f.createPackage(Constants.PACKAGE_JAVA_LANG, true);
		root.addPackage(javaLang);

		// Create java.long.Object class
		final IJCTCompilationUnit objectCU =
			f.createCompilationUnit(new File(
				Constants.PATH_TO_PACKAGE_JAVA_LANG
						+ Constants.CLASSNAME_OBJECT
						+ Constants.CLASSFILE_EXTENSION));
		javaLang.addCompilationUnit(objectCU);

		final IJCTClass javaLangObject =
			f.createClass(Constants.CLASSNAME_OBJECT, false, true);
		objectCU.addClazz(javaLangObject);

		// Backpatch java.lang.Object extends clause and super field
		javaLangObject.setDirectSuperClass(javaLangObject.createClassType());
		javaLangObject
			.getSuperField()
			.setType(javaLangObject.createClassType());

		// Create java.lang.Class class
		final IJCTCompilationUnit classCU =
			f.createCompilationUnit(new File(
				Constants.PATH_TO_PACKAGE_JAVA_LANG + Constants.CLASSNAME_CLASS
						+ Constants.CLASSFILE_EXTENSION));
		javaLang.addCompilationUnit(classCU);

		final IJCTClass javaLangClass =
			f.createClass(Constants.CLASSNAME_CLASS, false, true);
		classCU.addClazz(javaLangClass);

		//Backpatch java.lang.Object and java.lang.Class class fields
		javaLangObject.getClassField().setType(javaLangClass.createClassType());
		javaLangClass.getClassField().setType(javaLangClass.createClassType());

		// Create java.lang.Void class
		final IJCTCompilationUnit voidCU =
			f.createCompilationUnit(new File(
				Constants.PATH_TO_PACKAGE_JAVA_LANG + Constants.CLASSNAME_VOID
						+ Constants.CLASSFILE_EXTENSION));
		javaLang.addCompilationUnit(voidCU);

		final IJCTClass javaLangVoid =
			f.createClass(Constants.CLASSNAME_VOID, false, true);
		voidCU.addClazz(javaLangVoid);

		root.assumeInitialized();

		return root;
	}

	public static IJCTRootNode createJCT(final String name) {
		return JCTFactory.createJCT(name, true);
	}

	public static final IJCTPath PATH_TO_OBJECT =
		JCTFactory.parsePath(new StringBuffer()
			.append(JCTPathPart.PART_SEPARATOR)
			.append(JCTKind.ROOT_NODE.toString())
			.append(JCTPathPart.KIND_INDEX_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("null")
			.append(JCTPathPart.PART_SEPARATOR)
			.append(JCTKind.PACKAGE.toString())
			.append(JCTPathPart.KIND_INDEX_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append(Constants.PACKAGE_JAVA_LANG)
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("null")
			.append(JCTPathPart.PART_SEPARATOR)
			.append(JCTKind.CLASS.toString())
			.append(JCTPathPart.KIND_INDEX_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append(Constants.CLASSNAME_OBJECT)
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("01")
			.toString());

	public static final IJCTPath PATH_TO_VOID_CLASSTYPE =
		JCTFactory.parsePath(new StringBuffer()
			.append(JCTPathPart.PART_SEPARATOR)
			.append(JCTKind.ROOT_NODE.toString())
			.append(JCTPathPart.KIND_INDEX_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("null")
			.append(JCTPathPart.PART_SEPARATOR)
			.append(JCTKind.CLASS_TYPE.toString())
			.append(JCTPathPart.KIND_INDEX_SEPARATOR)
			.append("null")
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append(Constants.CLASS_BINARYNAME_VOID)
			.append(JCTPathPart.INDEX_DATA_SEPARATOR)
			.append("null")
			.toString());

	public static IJCTPath parsePath(final String stringPath) {
		final String[] parts = stringPath.split(JCTPathPart.PART_SEPARATOR);

		final IJCTPath path = new JCTPath();

		if (!"".equals(parts[0]))
			throw new IllegalArgumentException(
				"Nothing must be present before the first "
						+ JCTPathPart.PART_SEPARATOR + " : " + stringPath);

		IJCTPathPart part = JCTFactory.parsePathPart(parts[1]);

		if (JCTKind.ROOT_NODE != part.getResultKind())
			throw new IllegalArgumentException(
				"The first part of a path must designate a ROOT_NODE element");

		if (2 == parts.length)
			return path;

		part = JCTFactory.parsePathPart(parts[2]);
		path.addPart(part);

		for (int i = 3; i < parts.length; ++i) {
			final JCTPathPart toAdd = JCTFactory.parsePathPart(parts[i]);
			part.addPart(toAdd);
			part = toAdd;
		}

		return path;
	}

	public static JCTPathPart parsePathPart(final String stringPart) {
		final String[] kind_rest =
			stringPart.split(JCTPathPart.KIND_INDEX_SEPARATOR);

		if (kind_rest.length != 2)
			throw new IllegalArgumentException(
				"The path is malformed (no or more than one '"
						+ JCTPathPart.KIND_INDEX_SEPARATOR + "') : "
						+ stringPart);

		final JCTKind kind = JCTKind.valueOf(kind_rest[0]); // Throw an exception if fail

		final String[] args =
			kind_rest[1].split(JCTPathPart.INDEX_DATA_SEPARATOR);

		if (args.length != 3)
			throw new IllegalArgumentException(
				"The path is malformed (not enough or too many '"
						+ JCTPathPart.INDEX_DATA_SEPARATOR + "') : "
						+ stringPart);

		final Integer index =
			"null".equals(args[0]) ? null : Integer.parseInt(args[0]);

		if (args[2].length() % 2 != 0)
			throw new IllegalArgumentException(
				"The path is malforme (informative data string must have an even length).");

		final byte[] informativeData =
			"null".equals(args[2]) ? null : new byte[args[2].length() / 2];

		if (null != informativeData)
			for (int i = 0; i < informativeData.length; ++i)
				informativeData[i] =
					(byte) (Short.parseShort(args[2].substring(
						i * 2,
						(i + 1) * 2), 16) & 0xFF);

		return new JCTPathPart(kind, index, args[1], informativeData);
	}

}
