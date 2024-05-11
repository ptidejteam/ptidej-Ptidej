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

package jct.test.rsc.jct.kernel.impl;

import java.io.File;

import jct.test.rsc.jct.kernel.*;


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
public class JCTFactory implements IJCTFactory
{
    /**
     * original root node of this factory
     */
    private final IJCTRootNode originalRootNode;
    
    /**
     * associated root node of this factory
     */
    private final IJCTRootNode associatedRootNode;
    
    
    /**
     * Returns the version of {@literal jct} with the new name.
     * The two JCT use compatible factories. Any modification applied to any of
     * the both JCT is propagated to the other (as if they were using the same backstore).
     */
    public static IJCTRootNode renameJCT(final IJCTRootNode originalRootNode, final String new_name)
    {
        return new JCTRenamedRootNode(originalRootNode, new_name);
    }
    
    JCTFactory(final IJCTRootNode originalRootNode)
    {
        this(originalRootNode, null);
    }
    
    JCTFactory(final IJCTRootNode originalRootNode, final IJCTRootNode associatedRootNode)
    {
        this.originalRootNode = originalRootNode;
        this.associatedRootNode = associatedRootNode;
    }
    
    /**
     * Returns the JCT associated with this factory
     */
    @Override
	public IJCTRootNode getJCT()
    {
        return this.associatedRootNode;
    }
    
    /**
     * Test factory compatibility
     */
    @Override
    public boolean equals(final Object o)
    {
        if(!(o instanceof JCTFactory))
            return false;
        return this.originalRootNode == ((JCTFactory)o).originalRootNode;
    }
    
    /**
     * Returns the hash code following the strict contract of {@link java.lang.Object#hashCode()}.
     * I.e. : two factories a and b has the same hashcode iff a.equals(b) returns true
     */
    @Override
    public int hashCode()
    {
        return this.originalRootNode.hashCode();
    }
    
    /**
     * Returns a new primitive type.
     */
    @Override
	public IJCTPrimitiveType createPrimitiveType(final JCTPrimitiveTypes aPrimitiveTypeConstant)
    {
        return new JCTPrimitiveType(this.originalRootNode, aPrimitiveTypeConstant);
    }
    
    /**
     * Returns a new class type.
     */
    @Override
	public IJCTClassType createClassType(final IJCTSelector<IJCTClass> classDeclaration)
    {
        return new JCTClassType(this.originalRootNode, classDeclaration);
    }
    
    /**
     * Returns a new package.
     */
    @Override
	public IJCTPackage createPackage(final String name, final boolean isGhost)
    {
        final JCTPackage result = new JCTPackage(this.originalRootNode, name, isGhost);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new compilation unit.
     */
    @Override
	public IJCTCompilationUnit createCompilationUnit(final File sourceFile)
    {
        final JCTCompilationUnit result = new JCTCompilationUnit(this.originalRootNode, sourceFile);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new comment.
     */
    @Override
	public IJCTComment createComment(final boolean isEndOfLine, final String text)
    {
        final JCTComment result = new JCTComment(this.originalRootNode, isEndOfLine, text);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new import.
     */
    @Override
	public IJCTImport createImport(final IJCTImportable importedElement, final boolean isStatic, final boolean isOnDemand)
    {
        final JCTImport result = new JCTImport(this.originalRootNode, importedElement, isStatic, isOnDemand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new class.
     */
    @Override
	public IJCTClass createClass(final String name, final boolean isInterface, final boolean isGhost)
    {
        final JCTClass result = new JCTClass(this.originalRootNode, name, isInterface, isGhost);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new method.
     */
    @Override
	public IJCTMethod createMethod(final String name)
    {
        final JCTMethod result = new JCTMethod(this.originalRootNode, name);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new method invocation.
     */
    @Override
	public IJCTMethodInvocation createMethodInvocation(final IJCTSelector<IJCTMethod> methodSelector)
    {
        final JCTMethodInvocation result = new JCTMethodInvocation(this.originalRootNode, methodSelector);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new variable.
     */
    @Override
	public IJCTVariable createVariable(final String name, final IJCTType type)
    {
        final JCTVariable result = new JCTVariable(this.originalRootNode, name, type);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new parameter.
     */
    @Override
	public IJCTParameter createParameter(final String name, final IJCTType type)
    {
        final JCTParameter result = new JCTParameter(this.originalRootNode, name, type);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new field.
     */
    @Override
	public IJCTField createField(final String name, final IJCTType type)
    {
        final JCTField result = new JCTField(this.originalRootNode, name, type);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new block.
     */
    @Override
	public IJCTBlock createBlock()
    {
        final JCTBlock result = new JCTBlock(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new assert.
     */
    @Override
	public IJCTAssert createAssert(final IJCTExpression condition)
    {
        final JCTAssert result = new JCTAssert(this.originalRootNode, condition);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new break.
     */
    @Override
	public IJCTBreak createBreak()
    {
        final JCTBreak result = new JCTBreak(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new continue.
     */
    @Override
	public IJCTContinue createContinue()
    {
        final JCTContinue result = new JCTContinue(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new label.
     */
    @Override
	public IJCTLabel createLabel(final String name, final IJCTStatement statement)
    {
        final JCTLabel result = new JCTLabel(this.originalRootNode, name, statement);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new do while.
     */
    @Override
	public IJCTDoWhile createDoWhile(final IJCTExpression condition)
    {
        final JCTDoWhile result = new JCTDoWhile(this.originalRootNode, condition);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new while.
     */
    @Override
	public IJCTWhile createWhile(final IJCTExpression condition)
    {
        final JCTWhile result = new JCTWhile(this.originalRootNode, condition);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new for.
     */
    @Override
	public IJCTFor createFor(final IJCTExpression condition, final IJCTStatement body)
    {
        final JCTFor result = new JCTFor(this.originalRootNode, condition, body);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new enhanced for.
     */
    @Override
	public IJCTEnhancedFor createEnhancedFor(final IJCTVariable variable, final IJCTExpression iterable, final IJCTStatement body)
    {
        final JCTEnhancedFor result = new JCTEnhancedFor(this.originalRootNode, variable, iterable, body);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new if.
     */
    @Override
	public IJCTIf createIf(final IJCTExpression condition, final IJCTStatement thenStatement)
    {
        final JCTIf result = new JCTIf(this.originalRootNode, condition, thenStatement);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new return.
     */
    @Override
	public IJCTReturn createReturn()
    {
        final JCTReturn result = new JCTReturn(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new switch.
     */
    @Override
	public IJCTSwitch createSwitch(final IJCTExpression expression)
    {
        final JCTSwitch result = new JCTSwitch(this.originalRootNode, expression);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new case.
     */
    @Override
	public IJCTCase createCase()
    {
        final JCTCase result = new JCTCase(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new synchronized.
     */
    @Override
	public IJCTSynchronized createSynchronized(final IJCTExpression synchronizedObject)
    {
        final JCTSynchronized result = new JCTSynchronized(this.originalRootNode, synchronizedObject);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new throw.
     */
    @Override
	public IJCTThrow createThrow(final IJCTExpression thrownException)
    {
        final JCTThrow result = new JCTThrow(this.originalRootNode, thrownException);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new try.
     */
    @Override
	public IJCTTry createTry()
    {
        final JCTTry result = new JCTTry(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new catch.
     */
    @Override
	public IJCTCatch createCatch(final IJCTVariable variable)
    {
        final JCTCatch result = new JCTCatch(this.originalRootNode, variable);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new array access.
     */
    @Override
	public IJCTArrayAccess createArrayAccess(final IJCTExpression array, final IJCTExpression index)
    {
        final JCTArrayAccess result = new JCTArrayAccess(this.originalRootNode, array, index);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new expression statement.
     */
    @Override
	public IJCTExpressionStatement createExpressionStatement(final IJCTExpression expression)
    {
        final JCTExpressionStatement result = new JCTExpressionStatement(this.originalRootNode, expression);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new assignment.
     */
    @Override
	public IJCTAssignment createAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTAssignment result = new JCTAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new and assignment.
     */
    @Override
	public IJCTAndAssignment createAndAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTAndAssignment result = new JCTAndAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new divide assignment.
     */
    @Override
	public IJCTDivideAssignment createDivideAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTDivideAssignment result = new JCTDivideAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new left shift assignment.
     */
    @Override
	public IJCTLeftShiftAssignment createLeftShiftAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTLeftShiftAssignment result = new JCTLeftShiftAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new minus assignment.
     */
    @Override
	public IJCTMinusAssignment createMinusAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTMinusAssignment result = new JCTMinusAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new multiply assignment.
     */
    @Override
	public IJCTMultiplyAssignment createMultiplyAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTMultiplyAssignment result = new JCTMultiplyAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new or assignment.
     */
    @Override
	public IJCTOrAssignment createOrAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTOrAssignment result = new JCTOrAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new plus assignment.
     */
    @Override
	public IJCTPlusAssignment createPlusAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTPlusAssignment result = new JCTPlusAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new remainder assignment.
     */
    @Override
	public IJCTRemainderAssignment createRemainderAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTRemainderAssignment result = new JCTRemainderAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new right shift assignment.
     */
    @Override
	public IJCTRightShiftAssignment createRightShiftAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTRightShiftAssignment result = new JCTRightShiftAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new unsigned right shift assignment.
     */
    @Override
	public IJCTUnsignedRightShiftAssignment createUnsignedRightShiftAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTUnsignedRightShiftAssignment result = new JCTUnsignedRightShiftAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new xor assignment.
     */
    @Override
	public IJCTXorAssignment createXorAssignment(final IJCTExpression variable, final IJCTExpression value)
    {
        final JCTXorAssignment result = new JCTXorAssignment(this.originalRootNode, variable, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new bitwise complement.
     */
    @Override
	public IJCTBitwiseComplement createBitwiseComplement(final IJCTExpression operand)
    {
        final JCTBitwiseComplement result = new JCTBitwiseComplement(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new unary minus.
     */
    @Override
	public IJCTUnaryMinus createUnaryMinus(final IJCTExpression operand)
    {
        final JCTUnaryMinus result = new JCTUnaryMinus(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new unary plus.
     */
    @Override
	public IJCTUnaryPlus createUnaryPlus(final IJCTExpression operand)
    {
        final JCTUnaryPlus result = new JCTUnaryPlus(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new prefix decrement.
     */
    @Override
	public IJCTPrefixDecrement createPrefixDecrement(final IJCTExpression operand)
    {
        final JCTPrefixDecrement result = new JCTPrefixDecrement(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new prefix increment.
     */
    @Override
	public IJCTPrefixIncrement createPrefixIncrement(final IJCTExpression operand)
    {
        final JCTPrefixIncrement result = new JCTPrefixIncrement(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new postfix decrement.
     */
    @Override
	public IJCTPostfixDecrement createPostfixDecrement(final IJCTExpression operand)
    {
        final JCTPostfixDecrement result = new JCTPostfixDecrement(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new postfix increment.
     */
    @Override
	public IJCTPostfixIncrement createPostfixIncrement(final IJCTExpression operand)
    {
        final JCTPostfixIncrement result = new JCTPostfixIncrement(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new logical complement.
     */
    @Override
	public IJCTLogicalComplement createLogicalComplement(final IJCTExpression operand)
    {
        final JCTLogicalComplement result = new JCTLogicalComplement(this.originalRootNode, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new equal to.
     */
    @Override
	public IJCTEqualTo createEqualTo(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTEqualTo result = new JCTEqualTo(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new not equal to.
     */
    @Override
	public IJCTNotEqualTo createNotEqualTo(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTNotEqualTo result = new JCTNotEqualTo(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new conditional and.
     */
    @Override
	public IJCTConditionalAnd createConditionalAnd(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTConditionalAnd result = new JCTConditionalAnd(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new conditional or.
     */
    @Override
	public IJCTConditionalOr createConditionalOr(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTConditionalOr result = new JCTConditionalOr(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new less than.
     */
    @Override
	public IJCTLessThan createLessThan(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTLessThan result = new JCTLessThan(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new less than or equal.
     */
    @Override
	public IJCTLessThanOrEqual createLessThanOrEqual(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTLessThanOrEqual result = new JCTLessThanOrEqual(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new greater than.
     */
    @Override
	public IJCTGreaterThan createGreaterThan(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTGreaterThan result = new JCTGreaterThan(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new greater than or equal.
     */
    @Override
	public IJCTGreaterThanOrEqual createGreaterThanOrEqual(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTGreaterThanOrEqual result = new JCTGreaterThanOrEqual(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new and.
     */
    @Override
	public IJCTAnd createAnd(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTAnd result = new JCTAnd(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new divide.
     */
    @Override
	public IJCTDivide createDivide(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTDivide result = new JCTDivide(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new left shift.
     */
    @Override
	public IJCTLeftShift createLeftShift(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTLeftShift result = new JCTLeftShift(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new minus.
     */
    @Override
	public IJCTMinus createMinus(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTMinus result = new JCTMinus(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new multiply.
     */
    @Override
	public IJCTMultiply createMultiply(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTMultiply result = new JCTMultiply(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new or.
     */
    @Override
	public IJCTOr createOr(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTOr result = new JCTOr(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new plus.
     */
    @Override
	public IJCTPlus createPlus(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTPlus result = new JCTPlus(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new remainder.
     */
    @Override
	public IJCTRemainder createRemainder(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTRemainder result = new JCTRemainder(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new right shift.
     */
    @Override
	public IJCTRightShift createRightShift(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTRightShift result = new JCTRightShift(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new unsigned right shift.
     */
    @Override
	public IJCTUnsignedRightShift createUnsignedRightShift(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTUnsignedRightShift result = new JCTUnsignedRightShift(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new xor.
     */
    @Override
	public IJCTXor createXor(final IJCTExpression leftOperand, final IJCTExpression rightOperand)
    {
        final JCTXor result = new JCTXor(this.originalRootNode, leftOperand, rightOperand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new conditional operator.
     */
    @Override
	public IJCTConditionalOperator createConditionalOperator(final IJCTExpression condition, final IJCTExpression thenExpression, final IJCTExpression elseExpression)
    {
        final JCTConditionalOperator result = new JCTConditionalOperator(this.originalRootNode, condition, thenExpression, elseExpression);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new instance of.
     */
    @Override
	public IJCTInstanceOf createInstanceOf(final IJCTExpression operand, final IJCTType type)
    {
        final JCTInstanceOf result = new JCTInstanceOf(this.originalRootNode, operand, type);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new cast.
     */
    @Override
	public IJCTCast createCast(final IJCTType type, final IJCTExpression operand)
    {
        final JCTCast result = new JCTCast(this.originalRootNode, type, operand);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new new array.
     */
    @Override
	public IJCTNewArray createNewArray(final IJCTType elementType)
    {
        final JCTNewArray result = new JCTNewArray(this.originalRootNode, elementType);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new new class.
     */
    @Override
	public IJCTNewClass createNewClass(final IJCTClassType classType)
    {
        final JCTNewClass result = new JCTNewClass(this.originalRootNode, classType);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new boolean literal.
     */
    @Override
	public IJCTBooleanLiteral createBooleanLiteral(final boolean value)
    {
        final JCTBooleanLiteral result = new JCTBooleanLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new double literal.
     */
    @Override
	public IJCTDoubleLiteral createDoubleLiteral(final double value)
    {
        final JCTDoubleLiteral result = new JCTDoubleLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new float literal.
     */
    @Override
	public IJCTFloatLiteral createFloatLiteral(final float value)
    {
        final JCTFloatLiteral result = new JCTFloatLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new integer literal.
     */
    @Override
	public IJCTIntegerLiteral createIntegerLiteral(final int value)
    {
        final JCTIntegerLiteral result = new JCTIntegerLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new long literal.
     */
    @Override
	public IJCTLongLiteral createLongLiteral(final long value)
    {
        final JCTLongLiteral result = new JCTLongLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new character literal.
     */
    @Override
	public IJCTCharacterLiteral createCharacterLiteral(final char value)
    {
        final JCTCharacterLiteral result = new JCTCharacterLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new string literal.
     */
    @Override
	public IJCTStringLiteral createStringLiteral(final String value)
    {
        final JCTStringLiteral result = new JCTStringLiteral(this.originalRootNode, value);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new null literal.
     */
    @Override
	public IJCTNullLiteral createNullLiteral()
    {
        final JCTNullLiteral result = new JCTNullLiteral(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new parenthesis.
     */
    @Override
	public IJCTParenthesis createParenthesis(final IJCTExpression expression)
    {
        final JCTParenthesis result = new JCTParenthesis(this.originalRootNode, expression);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new empty statement.
     */
    @Override
	public IJCTEmptyStatement createEmptyStatement()
    {
        final JCTEmptyStatement result = new JCTEmptyStatement(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new member selector.
     */
    @SuppressWarnings("unchecked")
	@Override
	public <Member extends IJCTClassMember> IJCTMemberSelector<Member> createMemberSelector(final IJCTExpression qualifyingExpression, final Member member)
    {
        final JCTMemberSelector result = new JCTMemberSelector(this.originalRootNode, qualifyingExpression, member);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new simple selector.
     */
    @SuppressWarnings("unchecked")
	@Override
	public <Identifiable extends IJCTIdentifiable> IJCTSimpleSelector<Identifiable> createSimpleSelector(final Identifiable anIdentifiable)
    {
        final JCTSimpleSelector result = new JCTSimpleSelector(this.originalRootNode, anIdentifiable);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new erroneous selector.
     */
    @Override
	public IJCTErroneousSelector createErroneousSelector(final String anIdentifier)
    {
        final JCTErroneousSelector result = new JCTErroneousSelector(this.originalRootNode, anIdentifier);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    /**
     * Returns a new erroneous expression.
     */
    @Override
	public IJCTErroneousExpression createErroneousExpression()
    {
        final JCTErroneousExpression result = new JCTErroneousExpression(this.originalRootNode);
        this.originalRootNode.addOrphan(result);
        return result;
    }
    
    public static IJCTRootNode createJCT(final String name, final boolean initialized)
    {
        final IJCTRootNode root = new JCTRootNode(name);
    
        if(!initialized)
            return root;
    
        /// Initializing
        final IJCTFactory f = root.getFactory();
    
        final IJCTPackage javaLang = f.createPackage(Constants.PACKAGE_JAVA_LANG, true);
        root.addPackage(javaLang);
    
        // Create java.long.Object class
        final IJCTCompilationUnit objectCU = f.createCompilationUnit(new File(Constants.PATH_TO_PACKAGE_JAVA_LANG
                                                                              + Constants.CLASSNAME_OBJECT
                                                                              + Constants.CLASSFILE_EXTENSION));
        javaLang.addCompilationUnit(objectCU);
    
        final IJCTClass javaLangObject = f.createClass(Constants.CLASSNAME_OBJECT, false, true);
        objectCU.addClazz(javaLangObject);
    
        // Backpatch java.lang.Object extends clause and super field
        javaLangObject.setDirectSuperClass(javaLangObject.createClassType());
        javaLangObject.getSuperField().setType(javaLangObject.createClassType());
    
        // Create java.lang.Class class
        final IJCTCompilationUnit classCU = f.createCompilationUnit(new File(Constants.PATH_TO_PACKAGE_JAVA_LANG
                                                                             + Constants.CLASSNAME_CLASS
                                                                             + Constants.CLASSFILE_EXTENSION));
        javaLang.addCompilationUnit(classCU);
    
        final IJCTClass javaLangClass = f.createClass(Constants.CLASSNAME_CLASS, false, true);
        classCU.addClazz(javaLangClass);
    
        //Backpatch java.lang.Object and java.lang.Class class fields
        javaLangObject.getClassField().setType(javaLangClass.createClassType());
        javaLangClass.getClassField().setType(javaLangClass.createClassType());
    
        // Create java.lang.Void class
        final IJCTCompilationUnit voidCU = f.createCompilationUnit(new File(Constants.PATH_TO_PACKAGE_JAVA_LANG
                                                                            + Constants.CLASSNAME_VOID
                                                                            + Constants.CLASSFILE_EXTENSION));
        javaLang.addCompilationUnit(voidCU);
    
        final IJCTClass javaLangVoid = f.createClass(Constants.CLASSNAME_VOID, false, true);
        voidCU.addClazz(javaLangVoid);
    
        root.assumeInitialized();
    
        return root;
    }
    
    public static IJCTRootNode createJCT(final String name)
    { return JCTFactory.createJCT(name, true); }
    
    public static final IJCTPath PATH_TO_OBJECT = JCTFactory.parsePath(new StringBuffer()
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
                                                                       .append("java.lang")
                                                                       .append(JCTPathPart.INDEX_DATA_SEPARATOR)
                                                                       .append("null")
                                                                       .append(JCTPathPart.PART_SEPARATOR)
                                                                       .append(JCTKind.CLASS.toString())
                                                                       .append(JCTPathPart.KIND_INDEX_SEPARATOR)
                                                                       .append("null")
                                                                       .append(JCTPathPart.INDEX_DATA_SEPARATOR)
                                                                       .append("Object")
                                                                       .append(JCTPathPart.INDEX_DATA_SEPARATOR)
                                                                       .append("01")
                                                                       .toString());
    
    public static IJCTPath parsePath(final String stringPath)
    {
        final String[] parts = stringPath.split(JCTPathPart.PART_SEPARATOR);
    
        final IJCTPath path = new JCTPath();
    
        IJCTPathPart part = JCTFactory.parsePathPart(parts[0]);
    
        if(JCTKind.ROOT_NODE != part.getResultKind())
            throw new IllegalArgumentException("The first part of a path must designate a ROOT_NODE element");
    
        if(1 == parts.length)
            return path;
    
        part = JCTFactory.parsePathPart(parts[1]);
        path.addPart(part);
    
        for(int i = 2; i < parts.length; ++i)
        {
            final JCTPathPart toAdd = JCTFactory.parsePathPart(parts[i]);
            part.addPart(toAdd);
            part = toAdd;
        }
    
        return path;
    }
    
    private static JCTPathPart parsePathPart(final String stringPart)
    {
        final String[] kind_rest = stringPart.split(JCTPathPart.KIND_INDEX_SEPARATOR);
    
        if(kind_rest.length != 2)
            throw new IllegalArgumentException("The path is malformed (no or more than one '" + JCTPathPart.KIND_INDEX_SEPARATOR + "')");
    
        final JCTKind kind = JCTKind.valueOf(kind_rest[0]); // Throw an exception if fail
    
        final String[] args = kind_rest[1].split(JCTPathPart.INDEX_DATA_SEPARATOR);
    
        if(args.length != 3)
            throw new IllegalArgumentException("The path is malformed (not enough or too many '" + JCTPathPart.INDEX_DATA_SEPARATOR + "')");
    
        final Integer index = "null".equals(args[0]) ? null : Integer.parseInt(args[0]);
    
        if(args[2].length() % 2 != 0)
            throw new IllegalArgumentException("The path is malforme (informative data string must have an even length).");
    
                final byte[] informativeData = "null".equals(args[2]) ? null : new byte[args[2].length() / 2];
    
        if(null != informativeData)
            for(int i = 0; i < informativeData.length; ++i)
                informativeData[i] = (byte)(Short.parseShort(args[2].substring(i*2, (i+1)*2), 16) & 0xFF);
    
        return new JCTPathPart(kind, index, args[1], informativeData);
    }

}
