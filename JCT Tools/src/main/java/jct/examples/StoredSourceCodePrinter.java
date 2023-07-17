/**
 * @author Mathieu Lemoine
 * @created 2009-09-16 (水)
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
import jct.kernel.*;
import jct.tools.JCTMap;

public class StoredSourceCodePrinter extends JCTMap<Void, Void>
{
    private final Writer w;
    private final boolean extenso;
    private final boolean only_identifiable;

    public StoredSourceCodePrinter(final Writer w, final boolean extenso, final boolean only_identifiable)
    {
        this.w = w;
        this.extenso = extenso;
        this.only_identifiable = only_identifiable;
    }

    public void visit(final IJCTSourceCodePart scp)
    {
        if(!this.only_identifiable || (scp instanceof IJCTIdentifiable))
        try
        {
            this.w.append(scp.toString())
                .append(" : ")
                .append("" + scp.getStoredSourceCodeLength())
                .append("@")
                .append("" + scp.getStoredSourceCodeOffset())
                .append("[")
                .append("" + scp.getStoredSourceCodeOffset(scp.getEnclosingCompilationUnit()))
                .append("]");
            if(this.extenso)
                this.w.append("\n::")
                    .append(scp.getStoredSourceCode())
                    .append("\n\n\n========\n");
            this.w.append("\n");
            this.w.flush();
        }
		catch (IOException e) { e.printStackTrace(); }
    }

    public Void visitCompilationUnit(final IJCTCompilationUnit t, final Void p)
    {
        this.visit(t);
        return super.visitCompilationUnit(t, p);
    }

    public Void visitAnd(final IJCTAnd t, final Void p)
    {
        this.visit(t);
        return super.visitAnd(t, p);
    }

    public Void visitAndAssignment(final IJCTAndAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitAndAssignment(t, p);
    }

    public Void visitArrayAccess(final IJCTArrayAccess t, final Void p)
    {
        this.visit(t);
        return super.visitArrayAccess(t, p);
    }
    public Void visitAssert(final IJCTAssert t, final Void p)
    {
        this.visit(t);
        return super.visitAssert(t, p);
    }

    public Void visitAssignment(final IJCTAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitAssignment(t, p);
    }

    public Void visitBitwiseComplement(final IJCTBitwiseComplement t, final Void p)
    {
        this.visit(t);
        return super.visitBitwiseComplement(t, p);
    }

    public Void visitBlock(final IJCTBlock t, final Void p)
    {
        this.visit(t);
        return super.visitBlock(t, p);
    }

    public Void visitBooleanLiteral(final IJCTBooleanLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitBooleanLiteral(t, p);
    }

    public Void visitBreak(final IJCTBreak t, final Void p)
    {
        this.visit(t);
        return super.visitBreak(t, p);
    }

    public Void visitCase(final IJCTCase t, final Void p)
    {
        this.visit(t);
        return super.visitCase(t, p);
    }

    public Void visitCatch(final IJCTCatch t, final Void p)
    {
        this.visit(t);
        return super.visitCatch(t, p);
    }

    public Void visitCharacterLiteral(final IJCTCharacterLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitCharacterLiteral(t, p);
    }

    public Void visitClass(final IJCTClass t, final Void p)
    {
        this.visit(t);
        return super.visitClass(t, p);
    }

    public Void visitConditionalAnd(final IJCTConditionalAnd t, final Void p)
    {
        this.visit(t);
        return super.visitConditionalAnd(t, p);
    }

    public Void visitConditionalOperator(final IJCTConditionalOperator t, final Void p)
    {
        this.visit(t);
        return super.visitConditionalOperator(t, p);
    }

    public Void visitConditionalOr(final IJCTConditionalOr t, final Void p)
    {
        this.visit(t);
        return super.visitConditionalOr(t, p);
    }

    public Void visitContinue(final IJCTContinue t, final Void p)
    {
        this.visit(t);
        return super.visitContinue(t, p);
    }

    public Void visitDivide(final IJCTDivide t, final Void p)
    {
        this.visit(t);
        return super.visitDivide(t, p);
    }

    public Void visitDivideAssignment(final IJCTDivideAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitDivideAssignment(t, p);
    }

    public Void visitDoubleLiteral(final IJCTDoubleLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitDoubleLiteral(t, p);
    }

    public Void visitDoWhile(final IJCTDoWhile t, final Void p)
    {
        this.visit(t);
        return super.visitDoWhile(t, p);
    }

    public Void visitEmptyStatement(final IJCTEmptyStatement t, final Void p)
    {
        this.visit(t);
        return super.visitEmptyStatement(t, p);
    }

    public Void visitEnhancedFor(final IJCTEnhancedFor t, final Void p)
    {
        this.visit(t);
        return super.visitEnhancedFor(t, p);
    }

    public Void visitEqualTo(final IJCTEqualTo t, final Void p)
    {
        this.visit(t);
        return super.visitEqualTo(t, p);
    }

    public Void visitErroneousExpression(final IJCTErroneousExpression t, final Void p)
    {
        this.visit(t);
        return super.visitErroneousExpression(t, p);
    }

    public Void visitErroneousSelector(final IJCTErroneousSelector<?> t, final Void p)
    {
        this.visit(t);
        return super.visitErroneousSelector(t, p);
    }

    public Void visitExpressionStatement(final IJCTExpressionStatement t, final Void p)
    {
        this.visit(t);
        return super.visitExpressionStatement(t, p);
    }

    public Void visitFloatLiteral(final IJCTFloatLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitFloatLiteral(t, p);
    }

    public Void visitFor(final IJCTFor t, final Void p)
    {
        this.visit(t);
        return super.visitFor(t, p);
    }

    public Void visitGreaterThan(final IJCTGreaterThan t, final Void p)
    {
        this.visit(t);
        return super.visitGreaterThan(t, p);
    }

    public Void visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual t, final Void p)
    {
        this.visit(t);
        return super.visitGreaterThanOrEqual(t, p);
    }

    public Void visitIf(final IJCTIf t, final Void p)
    {
        this.visit(t);
        return super.visitIf(t, p);
    }

    public Void visitImport(final IJCTImport t, final Void p)
    {
        this.visit(t);
        return super.visitImport(t, p);
    }

    public Void visitInstanceOf(final IJCTInstanceOf t, final Void p)
    {
        this.visit(t);
        return super.visitInstanceOf(t, p);
    }

    public Void visitIntegerLiteral(final IJCTIntegerLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitIntegerLiteral(t, p);
    }

    public Void visitLabel(final IJCTLabel t, final Void p)
    {
        this.visit(t);
        return super.visitLabel(t, p);
    }

    public Void visitLeftShift(final IJCTLeftShift t, final Void p)
    {
        this.visit(t);
        return super.visitLeftShift(t, p);
    }

    public Void visitLeftShiftAssignment(final IJCTLeftShiftAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitLeftShiftAssignment(t, p);
    }

    public Void visitLessThan(final IJCTLessThan t, final Void p)
    {
        this.visit(t);
        return super.visitLessThan(t, p);
    }

    public Void visitLessThanOrEqual(final IJCTLessThanOrEqual t, final Void p)
    {
        this.visit(t);
        return super.visitLessThanOrEqual(t, p);
    }

    public Void visitLogicalComplement(final IJCTLogicalComplement t, final Void p)
    {
        this.visit(t);
        return super.visitLogicalComplement(t, p);
    }

    public Void visitLongLiteral(final IJCTLongLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitLongLiteral(t, p);
    }

    public <Element extends IJCTClassMember> Void visitMemberSelector(final IJCTMemberSelector<Element> t, final Void p)
    {
        this.visit(t);
        return super.visitMemberSelector(t, p);
    }

    public Void visitMethod(final IJCTMethod t, final Void p)
    {
        this.visit(t);
        return super.visitMethod(t, p);
    }

    public Void visitMethodInvocation(final IJCTMethodInvocation t, final Void p)
    {
        this.visit(t);
        return super.visitMethodInvocation(t, p);
    }

    public Void visitMinus(final IJCTMinus t, final Void p)
    {
        this.visit(t);
        return super.visitMinus(t, p);
    }

    public Void visitMinusAssignment(final IJCTMinusAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitMinusAssignment(t, p);
    }

    public Void visitMultiply(final IJCTMultiply t, final Void p)
    {
        this.visit(t);
        return super.visitMultiply(t, p);
    }

    public Void visitMultiplyAssignment(final IJCTMultiplyAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitMultiplyAssignment(t, p);
    }

    public Void visitNewArray(final IJCTNewArray t, final Void p)
    {
        this.visit(t);
        return super.visitNewArray(t, p);
    }

    public Void visitNewClass(final IJCTNewClass t, final Void p)
    {
        this.visit(t);
        return super.visitNewClass(t, p);
    }

    public Void visitNotEqualTo(final IJCTNotEqualTo t, final Void p)
    {
        this.visit(t);
        return super.visitNotEqualTo(t, p);
    }

    public Void visitNullLiteral(final IJCTNullLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitNullLiteral(t, p);
    }

    public Void visitOr(final IJCTOr t, final Void p)
    {
        this.visit(t);
        return super.visitOr(t, p);
    }

    public Void visitOrAssignment(final IJCTOrAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitOrAssignment(t, p);
    }

    public Void visitParenthesis(final IJCTParenthesis t, final Void p)
    {
        this.visit(t);
        return super.visitParenthesis(t, p);
    }

    public Void visitPlus(final IJCTPlus t, final Void p)
    {
        this.visit(t);
        return super.visitPlus(t, p);
    }

    public Void visitPlusAssignment(final IJCTPlusAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitPlusAssignment(t, p);
    }

    public Void visitPostfixDecrement(final IJCTPostfixDecrement t, final Void p)
    {
        this.visit(t);
        return super.visitPostfixDecrement(t, p);
    }

    public Void visitPostfixIncrement(final IJCTPostfixIncrement t, final Void p)
    {
        this.visit(t);
        return super.visitPostfixIncrement(t, p);
    }

    public Void visitPrefixDecrement(final IJCTPrefixDecrement t, final Void p)
    {
        this.visit(t);
        return super.visitPrefixDecrement(t, p);
    }

    public Void visitPrefixIncrement(final IJCTPrefixIncrement t, final Void p)
    {
        this.visit(t);
        return super.visitPrefixIncrement(t, p);
    }

    public Void visitRemainder(final IJCTRemainder t, final Void p)
    {
        this.visit(t);
        return super.visitRemainder(t, p);
    }

    public Void visitRemainderAssignment(final IJCTRemainderAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitRemainderAssignment(t, p);
    }

    public Void visitReturn(final IJCTReturn t, final Void p)
    {
        this.visit(t);
        return super.visitReturn(t, p);
    }

    public Void visitRightShift(final IJCTRightShift t, final Void p)
    {
        this.visit(t);
        return super.visitRightShift(t, p);
    }

    public Void visitRightShiftAssignment(final IJCTRightShiftAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitRightShiftAssignment(t, p);
    }

    public <Element extends IJCTIdentifiable> Void visitSimpleIdentifier(final IJCTSimpleSelector<Element> t, final Void p)
    {
        this.visit(t);
        return super.visitSimpleIdentifier(t, p);
    }

    public <Element extends IJCTIdentifiable> Void visitSimpleSelector(final IJCTSimpleSelector<Element> t, final Void p)
    {
        this.visit(t);
        return super.visitSimpleSelector(t, p);
    }

    public Void visitStringLiteral(final IJCTStringLiteral t, final Void p)
    {
        this.visit(t);
        return super.visitStringLiteral(t, p);
    }

    public Void visitSwitch(final IJCTSwitch t, final Void p)
    {
        this.visit(t);
        return super.visitSwitch(t, p);
    }

    public Void visitSynchronized(final IJCTSynchronized t, final Void p)
    {
        this.visit(t);
        return super.visitSynchronized(t, p);
    }

    public Void visitThrow(final IJCTThrow t, final Void p)
    {
        this.visit(t);
        return super.visitThrow(t, p);
    }

    public Void visitTry(final IJCTTry t, final Void p)
    {
        this.visit(t);
        return super.visitTry(t, p);
    }

    public Void visitCast(final IJCTCast t, final Void p)
    {
        this.visit(t);
        return super.visitCast(t, p);
    }

    public Void visitUnaryMinus(final IJCTUnaryMinus t, final Void p)
    {
        this.visit(t);
        return super.visitUnaryMinus(t, p);
    }

    public Void visitUnaryPlus(final IJCTUnaryPlus t, final Void p)
    {
        this.visit(t);
        return super.visitUnaryPlus(t, p);
    }

    public Void visitUnsignedRightShift(final IJCTUnsignedRightShift t, final Void p)
    {
        this.visit(t);
        return super.visitUnsignedRightShift(t, p);
    }

    public Void visitUnsignedRightShiftAssignment(final IJCTUnsignedRightShiftAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitUnsignedRightShiftAssignment(t, p);
    }

    public Void visitVariable(final IJCTVariable t, final Void p)
    {
        this.visit(t);
        return super.visitVariable(t, p);
    }

    public Void visitWhile(final IJCTWhile t, final Void p)
    {
        this.visit(t);
        return super.visitWhile(t, p);
    }

    public Void visitXor(final IJCTXor t, final Void p)
    {
        this.visit(t);
        return super.visitXor(t, p);
    }

    public Void visitXorAssignment(final IJCTXorAssignment t, final Void p)
    {
        this.visit(t);
        return super.visitXorAssignment(t, p);
    }

    @Override
    public Void visitField(final IJCTField t, final Void p)
    {
        this.visit(t);
        return super.visitField(t, p);
    }

    @Override
    public Void visitParameter(final IJCTParameter t, final Void p)
    {
        this.visit(t);
        return super.visitParameter(t, p);
    }

    @Override
    public Void visitComment(final IJCTComment t, final Void p)
    {
        this.visit(t);
        return super.visitComment(t, p);
    }
}
