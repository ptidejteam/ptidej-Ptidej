package jct.test.rsc.jct.kernel.impl;
import java.io.File;
import jct.test.rsc.jct.kernel.*;
public class JCTFactory
implements jct.test.rsc.jct.kernel.IJCTFactory
{
final private jct.test.rsc.jct.kernel.IJCTRootNode originalRootNode;

final private jct.test.rsc.jct.kernel.IJCTRootNode associatedRootNode;

public static jct.test.rsc.jct.kernel.IJCTRootNode renameJCT(final jct.test.rsc.jct.kernel.IJCTRootNode originalRootNode, final java.lang.String new_name)
{
return new jct.test.rsc.jct.kernel.impl.JCTRenamedRootNode(originalRootNode, new_name);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode originalRootNode)
{
this.<init>(originalRootNode, null);

}

void <init>(final jct.test.rsc.jct.kernel.IJCTRootNode originalRootNode, final jct.test.rsc.jct.kernel.IJCTRootNode associatedRootNode)
{
this.<init>();
this.originalRootNode = originalRootNode;
this.associatedRootNode = associatedRootNode;

}

public jct.test.rsc.jct.kernel.IJCTRootNode getJCT()
{
return this.associatedRootNode;

}

public boolean equals(final java.lang.Object o)
{
if(! (o instanceof jct.test.rsc.jct.kernel.impl.JCTFactory)) return false;
return this.originalRootNode == ((jct.test.rsc.jct.kernel.impl.JCTFactory)o).originalRootNode;

}

public int hashCode()
{
return this.originalRootNode.hashCode();

}

public jct.test.rsc.jct.kernel.IJCTPrimitiveType createPrimitiveType(final jct.test.rsc.jct.kernel.JCTPrimitiveTypes aPrimitiveTypeConstant)
{
return new jct.test.rsc.jct.kernel.impl.JCTPrimitiveType(this.originalRootNode, aPrimitiveTypeConstant);

}

public jct.test.rsc.jct.kernel.IJCTClassType createClassType(final jct.test.rsc.jct.kernel.IJCTSelector classDeclaration)
{
return new jct.test.rsc.jct.kernel.impl.JCTClassType(this.originalRootNode, classDeclaration);

}

public jct.test.rsc.jct.kernel.IJCTPackage createPackage(final java.lang.String name, final boolean isGhost)
{
final jct.test.rsc.jct.kernel.impl.JCTPackage result = new jct.test.rsc.jct.kernel.impl.JCTPackage(this.originalRootNode, name, isGhost);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTCompilationUnit createCompilationUnit(final java.io.File sourceFile)
{
final jct.test.rsc.jct.kernel.impl.JCTCompilationUnit result = new jct.test.rsc.jct.kernel.impl.JCTCompilationUnit(this.originalRootNode, sourceFile);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTComment createComment(final boolean isEndOfLine, final java.lang.String text)
{
final jct.test.rsc.jct.kernel.impl.JCTComment result = new jct.test.rsc.jct.kernel.impl.JCTComment(this.originalRootNode, isEndOfLine, text);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTImport createImport(final jct.test.rsc.jct.kernel.IJCTImportable importedElement, final boolean isStatic, final boolean isOnDemand)
{
final jct.test.rsc.jct.kernel.impl.JCTImport result = new jct.test.rsc.jct.kernel.impl.JCTImport(this.originalRootNode, importedElement, isStatic, isOnDemand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTClass createClass(final java.lang.String name, final boolean isInterface, final boolean isGhost)
{
final jct.test.rsc.jct.kernel.impl.JCTClass result = new jct.test.rsc.jct.kernel.impl.JCTClass(this.originalRootNode, name, isInterface, isGhost);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMethod createMethod(final java.lang.String name)
{
final jct.test.rsc.jct.kernel.impl.JCTMethod result = new jct.test.rsc.jct.kernel.impl.JCTMethod(this.originalRootNode, name);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMethodInvocation createMethodInvocation(final jct.test.rsc.jct.kernel.IJCTSelector methodSelector)
{
final jct.test.rsc.jct.kernel.impl.JCTMethodInvocation result = new jct.test.rsc.jct.kernel.impl.JCTMethodInvocation(this.originalRootNode, methodSelector);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTVariable createVariable(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{
final jct.test.rsc.jct.kernel.impl.JCTVariable result = new jct.test.rsc.jct.kernel.impl.JCTVariable(this.originalRootNode, name, type);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTParameter createParameter(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{
final jct.test.rsc.jct.kernel.impl.JCTParameter result = new jct.test.rsc.jct.kernel.impl.JCTParameter(this.originalRootNode, name, type);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTField createField(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTType type)
{
final jct.test.rsc.jct.kernel.impl.JCTField result = new jct.test.rsc.jct.kernel.impl.JCTField(this.originalRootNode, name, type);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTBlock createBlock()
{
final jct.test.rsc.jct.kernel.impl.JCTBlock result = new jct.test.rsc.jct.kernel.impl.JCTBlock(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTAssert createAssert(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
final jct.test.rsc.jct.kernel.impl.JCTAssert result = new jct.test.rsc.jct.kernel.impl.JCTAssert(this.originalRootNode, condition);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTBreak createBreak()
{
final jct.test.rsc.jct.kernel.impl.JCTBreak result = new jct.test.rsc.jct.kernel.impl.JCTBreak(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTContinue createContinue()
{
final jct.test.rsc.jct.kernel.impl.JCTContinue result = new jct.test.rsc.jct.kernel.impl.JCTContinue(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLabel createLabel(final java.lang.String name, final jct.test.rsc.jct.kernel.IJCTStatement statement)
{
final jct.test.rsc.jct.kernel.impl.JCTLabel result = new jct.test.rsc.jct.kernel.impl.JCTLabel(this.originalRootNode, name, statement);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTDoWhile createDoWhile(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
final jct.test.rsc.jct.kernel.impl.JCTDoWhile result = new jct.test.rsc.jct.kernel.impl.JCTDoWhile(this.originalRootNode, condition);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTWhile createWhile(final jct.test.rsc.jct.kernel.IJCTExpression condition)
{
final jct.test.rsc.jct.kernel.impl.JCTWhile result = new jct.test.rsc.jct.kernel.impl.JCTWhile(this.originalRootNode, condition);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTFor createFor(final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTStatement body)
{
final jct.test.rsc.jct.kernel.impl.JCTFor result = new jct.test.rsc.jct.kernel.impl.JCTFor(this.originalRootNode, condition, body);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTEnhancedFor createEnhancedFor(final jct.test.rsc.jct.kernel.IJCTVariable variable, final jct.test.rsc.jct.kernel.IJCTExpression iterable, final jct.test.rsc.jct.kernel.IJCTStatement body)
{
final jct.test.rsc.jct.kernel.impl.JCTEnhancedFor result = new jct.test.rsc.jct.kernel.impl.JCTEnhancedFor(this.originalRootNode, variable, iterable, body);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTIf createIf(final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTStatement thenStatement)
{
final jct.test.rsc.jct.kernel.impl.JCTIf result = new jct.test.rsc.jct.kernel.impl.JCTIf(this.originalRootNode, condition, thenStatement);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTReturn createReturn()
{
final jct.test.rsc.jct.kernel.impl.JCTReturn result = new jct.test.rsc.jct.kernel.impl.JCTReturn(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTSwitch createSwitch(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
final jct.test.rsc.jct.kernel.impl.JCTSwitch result = new jct.test.rsc.jct.kernel.impl.JCTSwitch(this.originalRootNode, expression);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTCase createCase()
{
final jct.test.rsc.jct.kernel.impl.JCTCase result = new jct.test.rsc.jct.kernel.impl.JCTCase(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTSynchronized createSynchronized(final jct.test.rsc.jct.kernel.IJCTExpression synchronizedObject)
{
final jct.test.rsc.jct.kernel.impl.JCTSynchronized result = new jct.test.rsc.jct.kernel.impl.JCTSynchronized(this.originalRootNode, synchronizedObject);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTThrow createThrow(final jct.test.rsc.jct.kernel.IJCTExpression thrownException)
{
final jct.test.rsc.jct.kernel.impl.JCTThrow result = new jct.test.rsc.jct.kernel.impl.JCTThrow(this.originalRootNode, thrownException);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTTry createTry()
{
final jct.test.rsc.jct.kernel.impl.JCTTry result = new jct.test.rsc.jct.kernel.impl.JCTTry(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTCatch createCatch(final jct.test.rsc.jct.kernel.IJCTVariable variable)
{
final jct.test.rsc.jct.kernel.impl.JCTCatch result = new jct.test.rsc.jct.kernel.impl.JCTCatch(this.originalRootNode, variable);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTArrayAccess createArrayAccess(final jct.test.rsc.jct.kernel.IJCTExpression array, final jct.test.rsc.jct.kernel.IJCTExpression index)
{
final jct.test.rsc.jct.kernel.impl.JCTArrayAccess result = new jct.test.rsc.jct.kernel.impl.JCTArrayAccess(this.originalRootNode, array, index);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTExpressionStatement createExpressionStatement(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
final jct.test.rsc.jct.kernel.impl.JCTExpressionStatement result = new jct.test.rsc.jct.kernel.impl.JCTExpressionStatement(this.originalRootNode, expression);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTAssignment createAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTAssignment result = new jct.test.rsc.jct.kernel.impl.JCTAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTAndAssignment createAndAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTAndAssignment result = new jct.test.rsc.jct.kernel.impl.JCTAndAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTDivideAssignment createDivideAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTDivideAssignment result = new jct.test.rsc.jct.kernel.impl.JCTDivideAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLeftShiftAssignment createLeftShiftAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTLeftShiftAssignment result = new jct.test.rsc.jct.kernel.impl.JCTLeftShiftAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMinusAssignment createMinusAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTMinusAssignment result = new jct.test.rsc.jct.kernel.impl.JCTMinusAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMultiplyAssignment createMultiplyAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTMultiplyAssignment result = new jct.test.rsc.jct.kernel.impl.JCTMultiplyAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTOrAssignment createOrAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTOrAssignment result = new jct.test.rsc.jct.kernel.impl.JCTOrAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTPlusAssignment createPlusAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTPlusAssignment result = new jct.test.rsc.jct.kernel.impl.JCTPlusAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTRemainderAssignment createRemainderAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTRemainderAssignment result = new jct.test.rsc.jct.kernel.impl.JCTRemainderAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTRightShiftAssignment createRightShiftAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTRightShiftAssignment result = new jct.test.rsc.jct.kernel.impl.JCTRightShiftAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTUnsignedRightShiftAssignment createUnsignedRightShiftAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTUnsignedRightShiftAssignment result = new jct.test.rsc.jct.kernel.impl.JCTUnsignedRightShiftAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTXorAssignment createXorAssignment(final jct.test.rsc.jct.kernel.IJCTExpression variable, final jct.test.rsc.jct.kernel.IJCTExpression value)
{
final jct.test.rsc.jct.kernel.impl.JCTXorAssignment result = new jct.test.rsc.jct.kernel.impl.JCTXorAssignment(this.originalRootNode, variable, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTBitwiseComplement createBitwiseComplement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTBitwiseComplement result = new jct.test.rsc.jct.kernel.impl.JCTBitwiseComplement(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTUnaryMinus createUnaryMinus(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTUnaryMinus result = new jct.test.rsc.jct.kernel.impl.JCTUnaryMinus(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTUnaryPlus createUnaryPlus(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTUnaryPlus result = new jct.test.rsc.jct.kernel.impl.JCTUnaryPlus(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTPrefixDecrement createPrefixDecrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTPrefixDecrement result = new jct.test.rsc.jct.kernel.impl.JCTPrefixDecrement(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTPrefixIncrement createPrefixIncrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTPrefixIncrement result = new jct.test.rsc.jct.kernel.impl.JCTPrefixIncrement(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTPostfixDecrement createPostfixDecrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTPostfixDecrement result = new jct.test.rsc.jct.kernel.impl.JCTPostfixDecrement(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTPostfixIncrement createPostfixIncrement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTPostfixIncrement result = new jct.test.rsc.jct.kernel.impl.JCTPostfixIncrement(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLogicalComplement createLogicalComplement(final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTLogicalComplement result = new jct.test.rsc.jct.kernel.impl.JCTLogicalComplement(this.originalRootNode, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTEqualTo createEqualTo(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTEqualTo result = new jct.test.rsc.jct.kernel.impl.JCTEqualTo(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTNotEqualTo createNotEqualTo(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTNotEqualTo result = new jct.test.rsc.jct.kernel.impl.JCTNotEqualTo(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTConditionalAnd createConditionalAnd(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTConditionalAnd result = new jct.test.rsc.jct.kernel.impl.JCTConditionalAnd(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTConditionalOr createConditionalOr(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTConditionalOr result = new jct.test.rsc.jct.kernel.impl.JCTConditionalOr(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLessThan createLessThan(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTLessThan result = new jct.test.rsc.jct.kernel.impl.JCTLessThan(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLessThanOrEqual createLessThanOrEqual(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTLessThanOrEqual result = new jct.test.rsc.jct.kernel.impl.JCTLessThanOrEqual(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTGreaterThan createGreaterThan(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTGreaterThan result = new jct.test.rsc.jct.kernel.impl.JCTGreaterThan(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTGreaterThanOrEqual createGreaterThanOrEqual(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTGreaterThanOrEqual result = new jct.test.rsc.jct.kernel.impl.JCTGreaterThanOrEqual(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTAnd createAnd(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTAnd result = new jct.test.rsc.jct.kernel.impl.JCTAnd(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTDivide createDivide(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTDivide result = new jct.test.rsc.jct.kernel.impl.JCTDivide(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLeftShift createLeftShift(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTLeftShift result = new jct.test.rsc.jct.kernel.impl.JCTLeftShift(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMinus createMinus(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTMinus result = new jct.test.rsc.jct.kernel.impl.JCTMinus(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMultiply createMultiply(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTMultiply result = new jct.test.rsc.jct.kernel.impl.JCTMultiply(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTOr createOr(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTOr result = new jct.test.rsc.jct.kernel.impl.JCTOr(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTPlus createPlus(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTPlus result = new jct.test.rsc.jct.kernel.impl.JCTPlus(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTRemainder createRemainder(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTRemainder result = new jct.test.rsc.jct.kernel.impl.JCTRemainder(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTRightShift createRightShift(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTRightShift result = new jct.test.rsc.jct.kernel.impl.JCTRightShift(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTUnsignedRightShift createUnsignedRightShift(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTUnsignedRightShift result = new jct.test.rsc.jct.kernel.impl.JCTUnsignedRightShift(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTXor createXor(final jct.test.rsc.jct.kernel.IJCTExpression leftOperand, final jct.test.rsc.jct.kernel.IJCTExpression rightOperand)
{
final jct.test.rsc.jct.kernel.impl.JCTXor result = new jct.test.rsc.jct.kernel.impl.JCTXor(this.originalRootNode, leftOperand, rightOperand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTConditionalOperator createConditionalOperator(final jct.test.rsc.jct.kernel.IJCTExpression condition, final jct.test.rsc.jct.kernel.IJCTExpression thenExpression, final jct.test.rsc.jct.kernel.IJCTExpression elseExpression)
{
final jct.test.rsc.jct.kernel.impl.JCTConditionalOperator result = new jct.test.rsc.jct.kernel.impl.JCTConditionalOperator(this.originalRootNode, condition, thenExpression, elseExpression);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTInstanceOf createInstanceOf(final jct.test.rsc.jct.kernel.IJCTExpression operand, final jct.test.rsc.jct.kernel.IJCTType type)
{
final jct.test.rsc.jct.kernel.impl.JCTInstanceOf result = new jct.test.rsc.jct.kernel.impl.JCTInstanceOf(this.originalRootNode, operand, type);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTCast createCast(final jct.test.rsc.jct.kernel.IJCTType type, final jct.test.rsc.jct.kernel.IJCTExpression operand)
{
final jct.test.rsc.jct.kernel.impl.JCTCast result = new jct.test.rsc.jct.kernel.impl.JCTCast(this.originalRootNode, type, operand);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTNewArray createNewArray(final jct.test.rsc.jct.kernel.IJCTType elementType)
{
final jct.test.rsc.jct.kernel.impl.JCTNewArray result = new jct.test.rsc.jct.kernel.impl.JCTNewArray(this.originalRootNode, elementType);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTNewClass createNewClass(final jct.test.rsc.jct.kernel.IJCTClassType classType)
{
final jct.test.rsc.jct.kernel.impl.JCTNewClass result = new jct.test.rsc.jct.kernel.impl.JCTNewClass(this.originalRootNode, classType);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTBooleanLiteral createBooleanLiteral(final boolean value)
{
final jct.test.rsc.jct.kernel.impl.JCTBooleanLiteral result = new jct.test.rsc.jct.kernel.impl.JCTBooleanLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTDoubleLiteral createDoubleLiteral(final double value)
{
final jct.test.rsc.jct.kernel.impl.JCTDoubleLiteral result = new jct.test.rsc.jct.kernel.impl.JCTDoubleLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTFloatLiteral createFloatLiteral(final float value)
{
final jct.test.rsc.jct.kernel.impl.JCTFloatLiteral result = new jct.test.rsc.jct.kernel.impl.JCTFloatLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTIntegerLiteral createIntegerLiteral(final int value)
{
final jct.test.rsc.jct.kernel.impl.JCTIntegerLiteral result = new jct.test.rsc.jct.kernel.impl.JCTIntegerLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTLongLiteral createLongLiteral(final long value)
{
final jct.test.rsc.jct.kernel.impl.JCTLongLiteral result = new jct.test.rsc.jct.kernel.impl.JCTLongLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTCharacterLiteral createCharacterLiteral(final char value)
{
final jct.test.rsc.jct.kernel.impl.JCTCharacterLiteral result = new jct.test.rsc.jct.kernel.impl.JCTCharacterLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTStringLiteral createStringLiteral(final java.lang.String value)
{
final jct.test.rsc.jct.kernel.impl.JCTStringLiteral result = new jct.test.rsc.jct.kernel.impl.JCTStringLiteral(this.originalRootNode, value);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTNullLiteral createNullLiteral()
{
final jct.test.rsc.jct.kernel.impl.JCTNullLiteral result = new jct.test.rsc.jct.kernel.impl.JCTNullLiteral(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTParenthesis createParenthesis(final jct.test.rsc.jct.kernel.IJCTExpression expression)
{
final jct.test.rsc.jct.kernel.impl.JCTParenthesis result = new jct.test.rsc.jct.kernel.impl.JCTParenthesis(this.originalRootNode, expression);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTEmptyStatement createEmptyStatement()
{
final jct.test.rsc.jct.kernel.impl.JCTEmptyStatement result = new jct.test.rsc.jct.kernel.impl.JCTEmptyStatement(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTMemberSelector createMemberSelector(final jct.test.rsc.jct.kernel.IJCTExpression qualifyingExpression, final jct.test.rsc.jct.kernel.IJCTClassMember member)
{
final jct.test.rsc.jct.kernel.impl.JCTMemberSelector result = new jct.test.rsc.jct.kernel.impl.JCTMemberSelector(this.originalRootNode, qualifyingExpression, member);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTSimpleSelector createSimpleSelector(final jct.test.rsc.jct.kernel.IJCTIdentifiable anIdentifiable)
{
final jct.test.rsc.jct.kernel.impl.JCTSimpleSelector result = new jct.test.rsc.jct.kernel.impl.JCTSimpleSelector(this.originalRootNode, anIdentifiable);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTErroneousSelector createErroneousSelector(final java.lang.String anIdentifier)
{
final jct.test.rsc.jct.kernel.impl.JCTErroneousSelector result = new jct.test.rsc.jct.kernel.impl.JCTErroneousSelector(this.originalRootNode, anIdentifier);
this.originalRootNode.addOrphan(result);
return result;

}

public jct.test.rsc.jct.kernel.IJCTErroneousExpression createErroneousExpression()
{
final jct.test.rsc.jct.kernel.impl.JCTErroneousExpression result = new jct.test.rsc.jct.kernel.impl.JCTErroneousExpression(this.originalRootNode);
this.originalRootNode.addOrphan(result);
return result;

}

public static jct.test.rsc.jct.kernel.IJCTRootNode createJCT(final java.lang.String name, final boolean initialized)
{
final jct.test.rsc.jct.kernel.IJCTRootNode root = new jct.test.rsc.jct.kernel.impl.JCTRootNode(name);
if(! initialized) return root;
final jct.test.rsc.jct.kernel.IJCTFactory f = root.getFactory();
final jct.test.rsc.jct.kernel.IJCTPackage javaLang = f.createPackage(jct.test.rsc.jct.kernel.Constants.PACKAGE_JAVA_LANG, true);
root.addPackage(javaLang);
final jct.test.rsc.jct.kernel.IJCTCompilationUnit objectCU = f.createCompilationUnit(new java.io.File(jct.test.rsc.jct.kernel.Constants.PATH_TO_PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.CLASSNAME_OBJECT + jct.test.rsc.jct.kernel.Constants.CLASSFILE_EXTENSION));
javaLang.addCompilationUnit(objectCU);
final jct.test.rsc.jct.kernel.IJCTClass javaLangObject = f.createClass(jct.test.rsc.jct.kernel.Constants.CLASSNAME_OBJECT, false, true);
objectCU.addClazz(javaLangObject);
javaLangObject.setDirectSuperClass(javaLangObject.createClassType());
javaLangObject.getSuperField().setType(javaLangObject.createClassType());
final jct.test.rsc.jct.kernel.IJCTCompilationUnit classCU = f.createCompilationUnit(new java.io.File(jct.test.rsc.jct.kernel.Constants.PATH_TO_PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.CLASSNAME_CLASS + jct.test.rsc.jct.kernel.Constants.CLASSFILE_EXTENSION));
javaLang.addCompilationUnit(classCU);
final jct.test.rsc.jct.kernel.IJCTClass javaLangClass = f.createClass(jct.test.rsc.jct.kernel.Constants.CLASSNAME_CLASS, false, true);
classCU.addClazz(javaLangClass);
javaLangObject.getClassField().setType(javaLangClass.createClassType());
javaLangClass.getClassField().setType(javaLangClass.createClassType());
final jct.test.rsc.jct.kernel.IJCTCompilationUnit voidCU = f.createCompilationUnit(new java.io.File(jct.test.rsc.jct.kernel.Constants.PATH_TO_PACKAGE_JAVA_LANG + jct.test.rsc.jct.kernel.Constants.CLASSNAME_VOID + jct.test.rsc.jct.kernel.Constants.CLASSFILE_EXTENSION));
javaLang.addCompilationUnit(voidCU);
final jct.test.rsc.jct.kernel.IJCTClass javaLangVoid = f.createClass(jct.test.rsc.jct.kernel.Constants.CLASSNAME_VOID, false, true);
voidCU.addClazz(javaLangVoid);
root.assumeInitialized();
return root;

}

public static jct.test.rsc.jct.kernel.IJCTRootNode createJCT(final java.lang.String name)
{
return jct.test.rsc.jct.kernel.impl.JCTFactory.createJCT(name, true);

}

final public static jct.test.rsc.jct.kernel.IJCTPath PATH_TO_OBJECT = jct.test.rsc.jct.kernel.impl.JCTFactory.parsePath(new java.lang.StringBuffer().append(jct.test.rsc.jct.kernel.JCTKind.ROOT_NODE.toString()).append(jct.test.rsc.jct.kernel.impl.JCTPathPart.KIND_INDEX_SEPARATOR).append("null").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append("null").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append("null").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.PART_SEPARATOR).append(jct.test.rsc.jct.kernel.JCTKind.PACKAGE.toString()).append(jct.test.rsc.jct.kernel.impl.JCTPathPart.KIND_INDEX_SEPARATOR).append("null").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append("java.lang").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append("null").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.PART_SEPARATOR).append(jct.test.rsc.jct.kernel.JCTKind.CLASS.toString()).append(jct.test.rsc.jct.kernel.impl.JCTPathPart.KIND_INDEX_SEPARATOR).append("null").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append("Object").append(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR).append("01").toString());

public static jct.test.rsc.jct.kernel.IJCTPath parsePath(final java.lang.String stringPath)
{
final java.lang.String[] parts = stringPath.split(jct.test.rsc.jct.kernel.impl.JCTPathPart.PART_SEPARATOR);
final jct.test.rsc.jct.kernel.IJCTPath path = new jct.test.rsc.jct.kernel.impl.JCTPath();
jct.test.rsc.jct.kernel.IJCTPathPart part = jct.test.rsc.jct.kernel.impl.JCTFactory.parsePathPart(parts[0]);
if(jct.test.rsc.jct.kernel.JCTKind.ROOT_NODE != part.getResultKind()) throw new java.lang.IllegalArgumentException("The first part of a path must designate a ROOT_NODE element");
if(1 == parts.length) return path;
part = jct.test.rsc.jct.kernel.impl.JCTFactory.parsePathPart(parts[1]);
path.addPart(part);
for(int i = 2; i < parts.length; ++ i) 
{
final jct.test.rsc.jct.kernel.impl.JCTPathPart toAdd = jct.test.rsc.jct.kernel.impl.JCTFactory.parsePathPart(parts[i]);
part.addPart(toAdd);
part = toAdd;

}
return path;

}

private static jct.test.rsc.jct.kernel.impl.JCTPathPart parsePathPart(final java.lang.String stringPart)
{
final java.lang.String[] kind_rest = stringPart.split(jct.test.rsc.jct.kernel.impl.JCTPathPart.KIND_INDEX_SEPARATOR);
if(kind_rest.length != 2) throw new java.lang.IllegalArgumentException("The path is malformed (no or more than one '" + jct.test.rsc.jct.kernel.impl.JCTPathPart.KIND_INDEX_SEPARATOR + "')");
final jct.test.rsc.jct.kernel.JCTKind kind = <NULL>.valueOf(kind_rest[0]);
final java.lang.String[] args = kind_rest[1].split(jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR);
if(args.length != 3) throw new java.lang.IllegalArgumentException("The path is malformed (not enough or too many '" + jct.test.rsc.jct.kernel.impl.JCTPathPart.INDEX_DATA_SEPARATOR + "')");
final java.lang.Integer index = "null".equals(args[0]) ? null : java.lang.Integer.parseInt(args[0]);
if(args[2].length() % 2 != 0) throw new java.lang.IllegalArgumentException("The path is malforme (informative data string must have an even length).");
final byte[] informativeData = "null".equals(args[2]) ? null : new byte[args[2].length() / 2];
if(null != informativeData) for(int i = 0; i < informativeData.length; ++ i) informativeData[i] = (byte)(java.lang.Short.parseShort(args[2].substring(i * 2, (i + 1) * 2), 16) & 255);
return new jct.test.rsc.jct.kernel.impl.JCTPathPart(kind, index, args[1], informativeData);

}


}
