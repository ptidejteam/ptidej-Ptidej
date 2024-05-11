package jct.test.rsc.jct.kernel;
import java.io.File;
public interface IJCTFactory
{
public jct.test.rsc.jct.kernel.IJCTRootNode getJCT()
{

}

public boolean equals(final java.lang.Object o)
{

}

public int hashCode()
{

}

public jct.test.rsc.jct.kernel.IJCTPrimitiveType createPrimitiveType(final jct.test.rsc.jct.kernel.JCTPrimitiveTypes aPrimitiveTypeConstant)
{

}

public jct.test.rsc.jct.kernel.IJCTClassType createClassType(final jct.test.rsc.jct.kernel.IJCTSelector classDeclaration)
{

}

public jct.test.rsc.jct.kernel.IJCTPackage createPackage(final java.lang.String name, final boolean isGhost)
{

}

public jct.test.rsc.jct.kernel.IJCTCompilationUnit createCompilationUnit(final java.io.File sourceFile)
{

}

public jct.test.rsc.jct.kernel.IJCTComment createComment(final boolean isEndOfLine, final java.lang.String text)
{

}

public jct.test.rsc.jct.kernel.IJCTImport createImport(final jct.test.rsc.jct.kernel.IJCTImportable importedElement, final boolean isStatic, final boolean isOnDemand)
{

}

public jct.test.rsc.jct.kernel.IJCTClass createClass(final java.lang.String name, final boolean isInterface, final boolean isGhost)
{

}

public jct.test.rsc.jct.kernel.IJCTMethod createMethod(final java.lang.String name)
{

}

public jct.test.rsc.jct.kernel.IJCTMethodInvocation createMethodInvocation(final jct.test.rsc.jct.kernel.IJCTSelector methodSelector)
{

}

public jct.test.rsc.jct.kernel.IJCTVariable createVariable(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{

}

public jct.test.rsc.jct.kernel.IJCTParameter createParameter(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{

}

public jct.test.rsc.jct.kernel.IJCTField createField(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{

}

public jct.test.rsc.jct.kernel.IJCTBlock createBlock()
{

}

public jct.test.rsc.jct.kernel.IJCTAssert createAssert(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{

}

public jct.test.rsc.jct.kernel.IJCTBreak createBreak()
{

}

public jct.test.rsc.jct.kernel.IJCTContinue createContinue()
{

}

public jct.test.rsc.jct.kernel.IJCTLabel createLabel(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTStatement statement)
{

}

public jct.test.rsc.jct.kernel.IJCTDoWhile createDoWhile(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{

}

public jct.test.rsc.jct.kernel.IJCTWhile createWhile(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{

}

public jct.test.rsc.jct.kernel.IJCTFor createFor(final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTStatement body)
{

}

public jct.test.rsc.jct.kernel.IJCTEnhancedFor createEnhancedFor(final jct.test.rsc.jct.kernel.IJCTVariable variable, final jct.test.rsc.jct.kernel.IJCTExpression iterable, final jct.test.rsc.jct.kernel.IJCTStatement body)
{

}

public jct.test.rsc.jct.kernel.IJCTIf createIf(final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTStatement thenStatement)
{

}

public jct.test.rsc.jct.kernel.IJCTReturn createReturn()
{

}

public jct.test.rsc.jct.kernel.IJCTSwitch createSwitch(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{

}

public jct.test.rsc.jct.kernel.IJCTCase createCase()
{

}

public jct.test.rsc.jct.kernel.IJCTSynchronized createSynchronized(final jct.test.rsc.jct.kernel.IJCTExpression synchronizedObject)
{

}

public jct.test.rsc.jct.kernel.IJCTThrow createThrow(final jct.test.rsc.jct.kernel.IJCTExpression thrownException)
{

}

public jct.test.rsc.jct.kernel.IJCTTry createTry()
{

}

public jct.test.rsc.jct.kernel.IJCTCatch createCatch(final jct.test.rsc.jct.kernel.IJCTVariable variable)
{

}

public jct.test.rsc.jct.kernel.IJCTArrayAccess createArrayAccess(final jct.test.rsc.jct.kernel.IJCTExpression array, final jct.test.rsc.jct.kernel.IJCTExpression index)
{

}

public jct.test.rsc.jct.kernel.IJCTExpressionStatement createExpressionStatement(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{

}

public jct.test.rsc.jct.kernel.IJCTAssignment createAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTAndAssignment createAndAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTDivideAssignment createDivideAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTLeftShiftAssignment createLeftShiftAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTMinusAssignment createMinusAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTMultiplyAssignment createMultiplyAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTOrAssignment createOrAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTPlusAssignment createPlusAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTRemainderAssignment createRemainderAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTRightShiftAssignment createRightShiftAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTUnsignedRightShiftAssignment createUnsignedRightShiftAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTXorAssignment createXorAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{

}

public jct.test.rsc.jct.kernel.IJCTBitwiseComplement createBitwiseComplement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTUnaryMinus createUnaryMinus(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTUnaryPlus createUnaryPlus(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTPrefixDecrement createPrefixDecrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTPrefixIncrement createPrefixIncrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTPostfixDecrement createPostfixDecrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTPostfixIncrement createPostfixIncrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTLogicalComplement createLogicalComplement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTEqualTo createEqualTo(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTNotEqualTo createNotEqualTo(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTConditionalAnd createConditionalAnd(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTConditionalOr createConditionalOr(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTLessThan createLessThan(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTLessThanOrEqual createLessThanOrEqual(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTGreaterThan createGreaterThan(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTGreaterThanOrEqual createGreaterThanOrEqual(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTAnd createAnd(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTDivide createDivide(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTLeftShift createLeftShift(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTMinus createMinus(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTMultiply createMultiply(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTOr createOr(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTPlus createPlus(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTRemainder createRemainder(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTRightShift createRightShift(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTUnsignedRightShift createUnsignedRightShift(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTXor createXor(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{

}

public jct.test.rsc.jct.kernel.IJCTConditionalOperator createConditionalOperator(final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTExpression thenExpression, final jct.test.rsc.jct.kernel.IJCTExpression elseExpression)
{

}

public jct.test.rsc.jct.kernel.IJCTInstanceOf createInstanceOf(final jct.test.rsc.jct.kernel.IJCTExpression operand, final jct.test.rsc.jct.kernel.IJCTType type)
{

}

public jct.test.rsc.jct.kernel.IJCTCast createCast(final jct.test.rsc.jct.kernel.IJCTType type, final jct.test.rsc.jct.kernel.IJCTExpression operand)
{

}

public jct.test.rsc.jct.kernel.IJCTNewArray createNewArray(final jct.test.rsc.jct.kernel.IJCTType elementType)
{

}

public jct.test.rsc.jct.kernel.IJCTNewClass createNewClass(final jct.test.rsc.jct.kernel.IJCTClassType classType)
{

}

public jct.test.rsc.jct.kernel.IJCTBooleanLiteral createBooleanLiteral(final boolean value)
{

}

public jct.test.rsc.jct.kernel.IJCTDoubleLiteral createDoubleLiteral(final double value)
{

}

public jct.test.rsc.jct.kernel.IJCTFloatLiteral createFloatLiteral(final float value)
{

}

public jct.test.rsc.jct.kernel.IJCTIntegerLiteral createIntegerLiteral(final int value)
{

}

public jct.test.rsc.jct.kernel.IJCTLongLiteral createLongLiteral(final long value)
{

}

public jct.test.rsc.jct.kernel.IJCTCharacterLiteral createCharacterLiteral(final char value)
{

}

public jct.test.rsc.jct.kernel.IJCTStringLiteral createStringLiteral(final java.lang.String value)
{

}

public jct.test.rsc.jct.kernel.IJCTNullLiteral createNullLiteral()
{

}

public jct.test.rsc.jct.kernel.IJCTParenthesis createParenthesis(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{

}

public jct.test.rsc.jct.kernel.IJCTEmptyStatement createEmptyStatement()
{

}

public jct.test.rsc.jct.kernel.IJCTMemberSelector createMemberSelector(final jct.test.rsc.jct.kernel.IJCTExpression qualifyingExpression, final jct.test.rsc.jct.kernel.IJCTClassMember member)
{

}

public jct.test.rsc.jct.kernel.IJCTSimpleSelector createSimpleSelector(final jct.test.rsc.jct.kernel.IJCTIdentifiable anIdentifiable)
{

}

public jct.test.rsc.jct.kernel.IJCTErroneousSelector createErroneousSelector(final java.lang.String anIdentifier)
{

}

public jct.test.rsc.jct.kernel.IJCTErroneousExpression createErroneousExpression()
{

}


}
