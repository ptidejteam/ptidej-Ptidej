/**
 * @author Mathieu Lemoine
 * @created 2008-10-26 (日)
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

package jct.tools;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jct.kernel.*;
import jct.util.IJCTContainer;
/**
 * This class should be used to clone a JCT
 *
 * @author Mathieu Lemoine
 */
// TODO make test with single pass
public class JCTCloner
{
    public static IJCTRootNode clone(final IJCTRootNode root, final String newName, final Class<? extends IJCTFactory> implementation)
        throws InvocationTargetException
    {
    	String name = newName;
    	Class<? extends IJCTFactory> impl = implementation;

        if(null == name)
            name = root.getName();

        if(null == impl)
            impl = root.getFactory().getClass();

        IJCTRootNode newJCT;
        JCTClonerFirstPass fp;
        try
        {
            final Method createJCT = impl.getDeclaredMethod("createJCT", String.class);
            fp = new JCTClonerFirstPass((IJCTRootNode)createJCT.invoke(null, name));
            newJCT = (IJCTRootNode) createJCT.invoke(null, name);
        }
        catch (final NoSuchMethodException e) { throw new IllegalArgumentException(e); }
        catch (final IllegalAccessException e) { throw new IllegalArgumentException(e); }

        return (IJCTRootNode)((IJCTRootNode)root.accept(fp))
            .accept(new JCTClonerSecondPass(newJCT, fp.getTranslationMap(), fp.getCommentTranslationMap()));
    }
}

class JCTClonerFirstPass
    implements IJCTVisitor<IJCTElement, Void>
{
    private final IJCTRootNode newRootNode;
    private final IJCTFactory factory;

    private final Map<IJCTIdentifiable, IJCTIdentifiable> translator = new HashMap<IJCTIdentifiable, IJCTIdentifiable>();
    private final Map<IJCTComment, IJCTComment> commentTranslator = new HashMap<IJCTComment, IJCTComment>();

    public JCTClonerFirstPass(final IJCTRootNode root)
    {
        this.newRootNode = root;
        this.factory = root.getFactory();
    }

    public Map<IJCTComment, IJCTComment> getCommentTranslationMap()
    { return this.commentTranslator; }

    public Map<IJCTIdentifiable, IJCTIdentifiable> getTranslationMap()
    { return this.translator; }

    public IJCTElement visitRootNode(final IJCTRootNode t, final Void v)
    {
        this.translator.clear();

        for(final IJCTElement aJCTElement : ((IJCTContainer<? extends IJCTElement>)this.newRootNode).getAllEnclosedElements())
            if(aJCTElement instanceof IJCTIdentifiable)
            {
                final IJCTPath p = aJCTElement.getPath();
                final IJCTIdentifiable i = (IJCTIdentifiable)p.walk(t);
                if(null != i)
                    this.translator.put(i, (IJCTIdentifiable)aJCTElement);
            }

        for(final IJCTPackage p : t.getPackages())
            this.newRootNode.addPackage((IJCTPackage)p.accept(this, v));

        return this.newRootNode;
    }

    public IJCTElement visitPackage(final IJCTPackage t, final Void v)
    {
        IJCTPackage p = (IJCTPackage)this.translator.get(t);

        if(null == p)
        {
            p = this.factory.createPackage(t.getName(), t.getIsGhost());
            this.translator.put(t, p);
        }

        if(null != t.getPackageDeclaration())
            p.setPackageDeclaration((IJCTCompilationUnit)t.getPackageDeclaration().accept(this, v));

        for(final IJCTCompilationUnit cu : t.getCompilationUnits())
            p.addCompilationUnit((IJCTCompilationUnit)cu.accept(this, v));

        return p;
    }

    @SuppressWarnings("unlikely-arg-type")
	public IJCTElement visitCompilationUnit(final IJCTCompilationUnit t, final Void v)
    {
        IJCTCompilationUnit cu = (IJCTCompilationUnit)this.translator.get(t);

        if(null == cu)
            cu = this.factory.createCompilationUnit(t.getSourceFile());

        for(final IJCTComment c : t.getComments())
            cu.addComment((IJCTComment)c.accept(this, v));

        for(final IJCTClass c : t.getClazzs())
            cu.addClazz((IJCTClass)c.accept(this, v));

        return cu;
    }

    public IJCTElement visitClass(final IJCTClass t, final Void v)
    {
        IJCTClass c = (IJCTClass)this.translator.get(t);

        if(null == c)
            c = this.factory.createClass(t.getName(), t.getIsInterface(), t.getIsGhost());

        while(c.getModifiers().size() > 0)
        { c.removeModifier(c.getModifiers().iterator().next()); }

        for(final JCTModifiers m : t.getModifiers())
            c.addModifier(m);

        for(final IJCTClassMember cm : t.getDeclaredMembers())
            c.addDeclaredMember((IJCTClassMember)cm.accept(this, v));

        return c;
    }

    public IJCTElement visitVariable(final IJCTVariable t, final Void p)
    {
        IJCTVariable v = (IJCTVariable) this.translator.get(t);

        if(null == v)
        {
            v = this.factory.createVariable(t.getName());
            v.setType(t.getType());
            this.translator.put(t, v);
        }

        while(v.getModifiers().size() > 0)
        { v.removeModifier(v.getModifiers().iterator().next()); }

        for(final JCTModifiers m : t.getModifiers())
            v.addModifier(m);

        return v;
    }

    public IJCTElement visitMethod(final IJCTMethod t, final Void v)
    {
        IJCTMethod m = (IJCTMethod) this.translator.get(t);

        if(null == m)
        {
            m = this.factory.createMethod(t.getName());
            this.translator.put(t, m);
        }

        while(m.getModifiers().size() > 0)
        { m.removeModifier(m.getModifiers().iterator().next()); }

        for(final JCTModifiers mod : t.getModifiers())
            m.addModifier(mod);

        return m;
    }

    public IJCTElement visitOther(final IJCTElement t, final Void v)
    { return null; }


    public IJCTElement visitAnd(final IJCTAnd t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitAndAssignment(final IJCTAndAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitArrayAccess(final IJCTArrayAccess t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitArrayType(final IJCTArrayType t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitAssert(final IJCTAssert t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitAssignment(final IJCTAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitBitwiseComplement(final IJCTBitwiseComplement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitBlock(final IJCTBlock t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitBooleanLiteral(final IJCTBooleanLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitBreak(final IJCTBreak t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitCase(final IJCTCase t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitCatch(final IJCTCatch t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitCharacterLiteral(final IJCTCharacterLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitClassType(final IJCTClassType t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitConditionalAnd(final IJCTConditionalAnd t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitConditionalOperator(final IJCTConditionalOperator t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitConditionalOr(final IJCTConditionalOr t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitContinue(final IJCTContinue t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitDivide(final IJCTDivide t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitDivideAssignment(final IJCTDivideAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitDoubleLiteral(final IJCTDoubleLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitDoWhile(final IJCTDoWhile t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitEmptyStatement(final IJCTEmptyStatement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitEnhancedFor(final IJCTEnhancedFor t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitEqualTo(final IJCTEqualTo t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitErroneousExpression(final IJCTErroneousExpression t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitErroneousSelector(final IJCTErroneousSelector t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitExpressionStatement(final IJCTExpressionStatement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitFloatLiteral(final IJCTFloatLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitFor(final IJCTFor t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitGreaterThan(final IJCTGreaterThan t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitIf(final IJCTIf t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitImport(final IJCTImport t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitInstanceOf(final IJCTInstanceOf t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitIntegerLiteral(final IJCTIntegerLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitIntersectionType(final IJCTIntersectionType t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLabel(final IJCTLabel t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLeftShift(final IJCTLeftShift t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLeftShiftAssignment(final IJCTLeftShiftAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLessThan(final IJCTLessThan t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLessThanOrEqual(final IJCTLessThanOrEqual t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLogicalComplement(final IJCTLogicalComplement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitLongLiteral(final IJCTLongLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public <Element extends IJCTClassMember> IJCTElement visitMemberSelector(final IJCTMemberSelector<Element> t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitMethodInvocation(final IJCTMethodInvocation t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitMinus(final IJCTMinus t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitMinusAssignment(final IJCTMinusAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitMultiply(final IJCTMultiply t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitMultiplyAssignment(final IJCTMultiplyAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitNewArray(final IJCTNewArray t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitNewClass(final IJCTNewClass t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitNotEqualTo(final IJCTNotEqualTo t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitNullLiteral(final IJCTNullLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitOr(final IJCTOr t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitOrAssignment(final IJCTOrAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitParenthesis(final IJCTParenthesis t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPlus(final IJCTPlus t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPlusAssignment(final IJCTPlusAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPostfixDecrement(final IJCTPostfixDecrement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPostfixIncrement(final IJCTPostfixIncrement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPrefixDecrement(final IJCTPrefixDecrement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPrefixIncrement(final IJCTPrefixIncrement t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitPrimitiveType(final IJCTPrimitiveType t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitRemainder(final IJCTRemainder t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitRemainderAssignment(final IJCTRemainderAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitReturn(final IJCTReturn t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitRightShift(final IJCTRightShift t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitRightShiftAssignment(final IJCTRightShiftAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public <Element extends IJCTIdentifiable> IJCTElement visitSimpleIdentifier(final IJCTSimpleSelector<Element> t, final Void v)
    { return this.visitOther(t, v); }

    public <Element extends IJCTIdentifiable> IJCTElement visitSimpleSelector(final IJCTSimpleSelector<Element> t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitStringLiteral(final IJCTStringLiteral t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitSwitch(final IJCTSwitch t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitSynchronized(final IJCTSynchronized t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitThrow(final IJCTThrow t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitTry(final IJCTTry t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitCast(final IJCTCast t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitUnaryMinus(final IJCTUnaryMinus t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitUnaryPlus(final IJCTUnaryPlus t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitUnsignedRightShift(final IJCTUnsignedRightShift t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitUnsignedRightShiftAssignment(final IJCTUnsignedRightShiftAssignment t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitWhile(final IJCTWhile t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitXor(final IJCTXor t, final Void v)
    { return this.visitOther(t, v); }

    public IJCTElement visitXorAssignment(final IJCTXorAssignment t, final Void v)
    { return this.visitOther(t, v); }

    @Override
    public IJCTElement visitField(final IJCTField fieldElement,
                                  final Void additionalParameter)
    { return this.visitVariable(fieldElement, additionalParameter); }

    @Override
    public IJCTElement visitParameter(final IJCTParameter parameterElement,
                                      final Void additionalParameter)
    { return this.visitVariable(parameterElement, additionalParameter); }

    @Override
    public IJCTElement visitComment(final IJCTComment commentElement,
                                    final Void additionalParameter)
    {
        final IJCTComment copy = this.factory.createComment(commentElement.getIsEndOfLine(),
                                                            commentElement.getText());
        this.commentTranslator.put(commentElement, copy);
        return copy;
    }
}

class JCTClonerSecondPass
    implements IJCTVisitor<IJCTElement, Void>
{
    private final IJCTRootNode newRootNode;
    private final IJCTFactory factory;

    private final Map<IJCTIdentifiable, IJCTIdentifiable> translator;

    private final Map<IJCTComment, IJCTComment> commentTranslator;

    private final Map<String, IJCTLabel> labelScope = new HashMap<String, IJCTLabel>();

    private final Set<IJCTElement> visited = new HashSet<IJCTElement>();

    public JCTClonerSecondPass(final IJCTRootNode root, final Map<IJCTIdentifiable, IJCTIdentifiable> translator,
                               final Map<IJCTComment, IJCTComment> commentTranslator)
    {
        this.newRootNode = root;
        this.factory = root.getFactory();
        this.translator = translator;
        this.commentTranslator = commentTranslator;
    }

    public IJCTSourceCodePart visitSourceCodePart(final IJCTSourceCodePart copy, final IJCTSourceCodePart original)
    {
        copy.setStoredSourceCodeOffset(original.getStoredSourceCodeOffset());
        copy.setStoredSourceCodeLength(original.getStoredSourceCodeLength());
        for(final IJCTComment c : original.getComments())
            copy.addComment((IJCTComment)c.accept(this, null));
        return copy;
    }

    public IJCTElement visitRootNode(final IJCTRootNode t, final Void v)
    {
        for(final IJCTPackage p : t.getPackages())
            this.newRootNode.addPackage((IJCTPackage)p.accept(this, v));

        return this.newRootNode;
    }

    public IJCTElement visitPackage(final IJCTPackage t, final Void v)
    {
        final IJCTPackage p = (IJCTPackage) this.translator.get(t);

        if(this.visited.contains(p))
            return p;
        else
            this.visited.add(p);

        if(null != t.getPackageDeclaration())
            p.setPackageDeclaration((IJCTCompilationUnit)t.getPackageDeclaration().accept(this, v));

        for(final IJCTCompilationUnit cu : t.getCompilationUnits())
            p.addCompilationUnit((IJCTCompilationUnit)cu.accept(this, v));

        return p;
    }

    @SuppressWarnings("unlikely-arg-type")
	public IJCTElement visitCompilationUnit(final IJCTCompilationUnit t, final Void v)
    {
        final IJCTCompilationUnit cu = (IJCTCompilationUnit) this.translator.get(t);

        if(this.visited.contains(cu))
            return cu;
        else
            this.visited.add(cu);

        for(final IJCTImport i : t.getImporteds())
            cu.addImported((IJCTImport)i.accept(this, v));

        for(final IJCTClass c : t.getClazzs())
            cu.addClazz((IJCTClass)c.accept(this, v));

        cu.setStoredSourceCode(t.getStoredSourceCode());

        return cu;
    }

    public IJCTElement visitClass(final IJCTClass t, final Void v)
    {
        IJCTClass c = (IJCTClass) this.translator.get(t);

        if(null == c)
        {
            c = this.factory.createClass(t.getName(), t.getIsInterface(), t.getIsGhost());
            c.setIsInterface(t.getIsInterface());

            while(c.getModifiers().size() > 0)
            { c.removeModifier(c.getModifiers().iterator().next()); }

            for(final JCTModifiers m : t.getModifiers())
                c.addModifier(m);
        }

        if(this.visited.contains(c))
            return c;
        else
            this.visited.add(c);

        if(null != t.getDirectSuperClass())
        {
            final IJCTClassType e = (IJCTClassType)t.getDirectSuperClass().accept(this, v);
            if(e.getSelector().getElement().getIsInterface())
                c.addDirectlyImplementedInterface(e);
            else
                c.setDirectSuperClass(e);
        }

        for(final IJCTClassType i : t.getDirectlyImplementedInterfaces())
            c.addDirectlyImplementedInterface((IJCTClassType)i.accept(this, v));

        for(final IJCTClassMember cm : t.getDeclaredMembers())
            c.addDeclaredMember((IJCTClassMember)cm.accept(this, v));

        return c;
    }

    public IJCTElement visitVariable(final IJCTVariable t, final Void p)
    {
        IJCTVariable v = (IJCTVariable)this.translator.get(t);

        if(null == v)
        {
            v = this.factory.createVariable(t.getName());
            v.setType(t.getType());
            this.translator.put(t, v);

            while(v.getModifiers().size() > 0)
            { v.removeModifier(v.getModifiers().iterator().next()); }

            for(final JCTModifiers m : t.getModifiers())
                v.addModifier(m);
        }

        if(this.visited.contains(v))
            return v;
        else
            this.visited.add(v);

        v.setType((IJCTType)t.getType().accept(this, p));

        if(null != t.getInitialValue())
            v.setInitialValue((IJCTExpression)t.getInitialValue().accept(this, p));

        return this.visitSourceCodePart(v, t);
    }

    public IJCTElement visitMethod(final IJCTMethod t, final Void param)
    {
        IJCTMethod m = (IJCTMethod)this.translator.get(t);

        if(null == m)
        {
            m = this.factory.createMethod(t.getName());
            this.translator.put(t, m);

            while(m.getModifiers().size() > 0)
            { m.removeModifier(m.getModifiers().iterator().next()); }

            for(final JCTModifiers mod : t.getModifiers())
                m.addModifier(mod);
        }

        if(this.visited.contains(m))
            return m;
        else
            this.visited.add(m);

        m.setReturnType((IJCTType)t.getReturnType().accept(this, param));

        for(final IJCTParameter p : t.getParameters())
            m.addParameter((IJCTParameter)p.accept(this, param));

        for(final IJCTClassType c : t.getThrownExceptions())
            m.addThrownException((IJCTClassType)c.accept(this, param));

        if(null != t.getBody())
            m.setBody((IJCTBlock)t.getBody().accept(this, param));

        return this.visitSourceCodePart(m, t);
    }

    public IJCTElement visitOther(final IJCTElement t, final Void v)
    { throw new IllegalArgumentException("Unknown element " + t); }

    public IJCTElement visitAnd(final IJCTAnd t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createAnd((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                               (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitAndAssignment(final IJCTAndAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createAndAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                         (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitArrayAccess(final IJCTArrayAccess t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createArrayAccess((IJCTExpression)t.getArray().accept(this, v),
                                                                       (IJCTExpression)t.getIndex().accept(this, v)), t);
    }

    public IJCTElement visitArrayType(final IJCTArrayType t, final Void v)
    { return this.newRootNode.getType(t.getTypeName(), IJCTArrayType.class); }

    public IJCTElement visitAssert(final IJCTAssert t, final Void v)
    {
        final IJCTAssert a = this.factory.createAssert((IJCTExpression)t.getCondition().accept(this, v));

        if(null != t.getDetail())
            a.setDetail((IJCTExpression)t.getDetail().accept(this, v));

        return this.visitSourceCodePart(a, t);
    }

    public IJCTElement visitAssignment(final IJCTAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                      (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitBitwiseComplement(final IJCTBitwiseComplement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createBitwiseComplement((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitBlock(final IJCTBlock t, final Void v)
    {
        final IJCTBlock b = this.factory.createBlock();

        for(final IJCTStatement s : t.getStatements())
            b.addStatement((IJCTStatement)s.accept(this, v));

        return this.visitSourceCodePart(b, t);
    }

    public IJCTElement visitBooleanLiteral(final IJCTBooleanLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createBooleanLiteral(t.getValue()), t); }

    public IJCTElement visitBreak(final IJCTBreak t, final Void v)
    {
        final IJCTBreak b = this.factory.createBreak();

        if(null != t.getLabel())
            b.setLabel(this.labelScope.get(t.getLabel().getName()));

        return this.visitSourceCodePart(b, t);
    }

    public IJCTElement visitCase(final IJCTCase t, final Void v)
    {
        final IJCTCase c = this.factory.createCase();

        if(null != t.getLabel())
            c.setLabel((IJCTExpression)t.getLabel().accept(this, v));

        for(final IJCTStatement s : t.getStatements())
            c.addStatement((IJCTStatement)s.accept(this, v));

        return this.visitSourceCodePart(c, t);
    }

    public IJCTElement visitCatch(final IJCTCatch t, final Void v)
    {
        final IJCTCatch c = this.factory.createCatch((IJCTVariable)t.getVariable().accept(this, v));

        c.setBody((IJCTBlock)t.getBody().accept(this, v));

        return this.visitSourceCodePart(c, t);
    }

    public IJCTElement visitCharacterLiteral(final IJCTCharacterLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createCharacterLiteral(t.getValue()), t); }

    public IJCTElement visitClassType(final IJCTClassType t, final Void v)
    { return ((IJCTClass)t.getSelector().getElement().accept(this, v)).createClassType(); }

    public IJCTElement visitConditionalAnd(final IJCTConditionalAnd t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createConditionalAnd((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                          (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitConditionalOperator(final IJCTConditionalOperator t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createConditionalOperator((IJCTExpression)t.getCondition().accept(this, v),
                                                                               (IJCTExpression)t.getThenExpression().accept(this, v),
                                                                               (IJCTExpression)t.getElseExpression().accept(this, v)), t);
    }

    public IJCTElement visitConditionalOr(final IJCTConditionalOr t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createConditionalOr((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                         (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitContinue(final IJCTContinue t, final Void v)
    {
        final IJCTContinue b = this.factory.createContinue();

        if(null != t.getLabel())
            b.setLabel(this.labelScope.get(t.getLabel().getName()));

        return this.visitSourceCodePart(b, t);
    }

    public IJCTElement visitDivide(final IJCTDivide t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createDivide((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                  (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitDivideAssignment(final IJCTDivideAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createDivideAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                            (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitDoubleLiteral(final IJCTDoubleLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createDoubleLiteral(t.getValue()), t); }

    public IJCTElement visitDoWhile(final IJCTDoWhile t, final Void v)
    {
        final IJCTDoWhile result = this.factory.createDoWhile((IJCTExpression)t.getCondition().accept(this, v));
        result.setBody((IJCTStatement)t.getBody().accept(this, v));
        return this.visitSourceCodePart(result, t);
    }

    public IJCTElement visitEmptyStatement(final IJCTEmptyStatement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createEmptyStatement(), t); }

    public IJCTElement visitEnhancedFor(final IJCTEnhancedFor t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createEnhancedFor((IJCTVariable)t.getVariable().accept(this, v),
                                                                       (IJCTExpression)t.getIterable().accept(this, v),
                                                                       (IJCTStatement)t.getBody().accept(this, v)), t);
    }

    public IJCTElement visitEqualTo(final IJCTEqualTo t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createEqualTo((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                   (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitErroneousExpression(final IJCTErroneousExpression t, final Void v)
    {
        final IJCTErroneousExpression e = this.factory.createErroneousExpression();

        for(final IJCTExpression aJCTExpression : ((IJCTContainer<? extends IJCTExpression>)t).getEnclosedElements())
            e.addExpression((IJCTExpression)aJCTExpression.accept(this, v));

        return this.visitSourceCodePart(e, t);
    }

    public IJCTElement visitErroneousSelector(final IJCTErroneousSelector t, final Void v)
    { return this.visitSourceCodePart(this.factory.createErroneousSelector(t.getSourceCode()), t); }

    public IJCTElement visitExpression(final IJCTExpressionStatement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createExpressionStatement((IJCTExpression)t.getExpression().accept(this, v)), t); }

    public IJCTElement visitFloatLiteral(final IJCTFloatLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createFloatLiteral(t.getValue()), t); }

    public IJCTElement visitFor(final IJCTFor t, final Void v)
    {
        final IJCTFor f = this.factory.createFor((IJCTExpression)t.getCondition().accept(this, v),
                                                 (IJCTStatement)t.getBody().accept(this, v));

        for(final IJCTStatement i : t.getInitializers())
            f.addInitializer((IJCTStatement)i.accept(this, v));

        for(final IJCTExpressionStatement e : t.getUpdaters())
            f.addUpdater((IJCTExpressionStatement)e.accept(this, v));

        return this.visitSourceCodePart(f, t);
    }

    public IJCTElement visitGreaterThan(final IJCTGreaterThan t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createGreaterThan((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                       (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitGreaterThanOrEqual(final IJCTGreaterThanOrEqual t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createGreaterThanOrEqual((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                              (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitIf(final IJCTIf t, final Void v)
    {
        final IJCTIf i = this.factory.createIf((IJCTExpression)t.getCondition().accept(this, v),
                                           (IJCTStatement)t.getThenStatement().accept(this, v));

        if(null != t.getElseStatement())
            i.setElseStatement((IJCTStatement)t.getElseStatement().accept(this, v));

        return this.visitSourceCodePart(i, t);
    }

    public IJCTElement visitImport(final IJCTImport t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createImport((IJCTImportable)t.getImportedElement().accept(this, v),
                                                                  t.getIsStatic(), t.getIsOnDemand()), t);
    }

    public IJCTElement visitInstanceOf(final IJCTInstanceOf t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createInstanceOf((IJCTExpression)t.getOperand().accept(this, v),
                                                                      (IJCTType)t.getType().accept(this, v)), t);
    }

    public IJCTElement visitIntegerLiteral(final IJCTIntegerLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createIntegerLiteral(t.getValue()), t); }

    public IJCTElement visitIntersectionType(final IJCTIntersectionType t, final Void v)
    { return this.newRootNode.getType(t.getTypeName(), IJCTIntersectionType.class); }

    public IJCTElement visitLabel(final IJCTLabel t, final Void v)
    {
        final IJCTLabel l = this.factory.createLabel(t.getName(),
                                                 this.factory.createEmptyStatement());

        final IJCTLabel old = this.labelScope.put(l.getName(), l);
        l.setStatement((IJCTStatement)t.getStatement().accept(this, v));
        this.labelScope.put(l.getName(), old);

        return this.visitSourceCodePart(l, t);
    }

    public IJCTElement visitLeftShift(final IJCTLeftShift t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createLeftShift((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                     (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitLeftShiftAssignment(final IJCTLeftShiftAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createLeftShiftAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                               (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitLessThan(final IJCTLessThan t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createLessThan((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                    (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitLessThanOrEqual(final IJCTLessThanOrEqual t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createLessThanOrEqual((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                           (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitLogicalComplement(final IJCTLogicalComplement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createLogicalComplement((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitLongLiteral(final IJCTLongLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createLongLiteral(t.getValue()), t); }

    public <Element extends IJCTClassMember> IJCTElement visitMemberSelector(final IJCTMemberSelector<Element> t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createMemberSelector((IJCTExpression)t.getQualifyingExpression().accept(this, v),
                                                                          (IJCTClassMember)t.getElement().accept(this, v)), t);
    }

    public IJCTElement visitMethodInvocation(final IJCTMethodInvocation t, final Void v)
    {
        final IJCTMethodInvocation mi = this.factory.createMethodInvocation((IJCTSelector<IJCTMethod>)t.getMethodSelector().accept(this, v));

        for(final IJCTExpression e : t.getArguments())
            mi.addArgument((IJCTExpression)e.accept(this, v));

        return this.visitSourceCodePart(mi, t);
    }

    public IJCTElement visitMinus(final IJCTMinus t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createMinus((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                 (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitMinusAssignment(final IJCTMinusAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createMinusAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                           (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitMultiply(final IJCTMultiply t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createMultiply((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                    (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitMultiplyAssignment(final IJCTMultiplyAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createMultiplyAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                              (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitNewArray(final IJCTNewArray t, final Void v)
    {
        final IJCTNewArray na = this.factory.createNewArray((IJCTType)t.getElementType().accept(this, v));

        na.setUnspecifiedDimensions(t.getUnspecifiedDimensions());

        for(final IJCTExpression e : t.getDimensions())
            na.addDimension((IJCTExpression)e.accept(this, v));

        for(final IJCTExpression i : t.getInitializers())
            na.addInitializer((IJCTExpression)i.accept(this, v));

        return this.visitSourceCodePart(na, t);
    }

    public IJCTElement visitNewClass(final IJCTNewClass t, final Void v)
    {
        final IJCTNewClass nc = this.factory.createNewClass((IJCTClassType)t.getClassType().accept(this, v));

        if(null != t.getSelectingExpression())
            nc.setSelectingExpression((IJCTExpression)t.getSelectingExpression().accept(this,v ));

        for(final IJCTExpression p : t.getArguments())
            nc.addArgument((IJCTExpression)p.accept(this, v));

        if(null != t.getAnnonymousClass())
            nc.setAnnonymousClass((IJCTClass)t.getAnnonymousClass().accept(this, v));

        return this.visitSourceCodePart(nc, t);
    }

    public IJCTElement visitNotEqualTo(final IJCTNotEqualTo t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createNotEqualTo((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                      (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitNullLiteral(final IJCTNullLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createNullLiteral(), t); }

    public IJCTElement visitOr(final IJCTOr t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createOr((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                              (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitOrAssignment(final IJCTOrAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createOrAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                        (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitParenthesis(final IJCTParenthesis t, final Void v)
    { return this.visitSourceCodePart(this.factory.createParenthesis((IJCTExpression)t.getExpression().accept(this, v)), t); }

    public IJCTElement visitPlus(final IJCTPlus t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createPlus((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitPlusAssignment(final IJCTPlusAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createPlusAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                          (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitPostfixDecrement(final IJCTPostfixDecrement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createPostfixDecrement((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitPostfixIncrement(final IJCTPostfixIncrement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createPostfixIncrement((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitPrefixDecrement(final IJCTPrefixDecrement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createPrefixDecrement((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitPrefixIncrement(final IJCTPrefixIncrement t, final Void v)
    { return this.visitSourceCodePart(this.factory.createPrefixIncrement((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitPrimitiveType(final IJCTPrimitiveType t, final Void v)
    { return this.newRootNode.getType(t.getType()); }

    public IJCTElement visitRemainder(final IJCTRemainder t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createRemainder((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                     (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitRemainderAssignment(final IJCTRemainderAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createRemainderAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                               (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitReturn(final IJCTReturn t, final Void v)
    {
        final IJCTReturn r = this.factory.createReturn();

        if(null != t.getReturnedExpression())
            r.setReturnedExpression((IJCTExpression)t.getReturnedExpression().accept(this, v));

        return this.visitSourceCodePart(r, t);
    }

    public IJCTElement visitRightShift(final IJCTRightShift t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createRightShift((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                      (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitRightShiftAssignment(final IJCTRightShiftAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createRightShiftAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                                (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public <Element extends IJCTIdentifiable> IJCTElement visitSimpleIdentifier(final IJCTSimpleSelector<Element> t, final Void v)
    { return this.visitSourceCodePart(this.factory.createSimpleSelector((IJCTClassMember)t.getElement().accept(this, v)), t); }

    public <Element extends IJCTIdentifiable> IJCTElement visitSimpleSelector(final IJCTSimpleSelector<Element> t, final Void v)
    { return this.visitSourceCodePart(this.factory.createSimpleSelector((IJCTIdentifiable)t.getElement().accept(this, v)), t); }

    public IJCTElement visitStringLiteral(final IJCTStringLiteral t, final Void v)
    { return this.visitSourceCodePart(this.factory.createStringLiteral(t.getValue()), t); }

    public IJCTElement visitSwitch(final IJCTSwitch t, final Void v)
    {
        final IJCTSwitch s = this.factory.createSwitch((IJCTExpression)t.getExpression().accept(this, v));

        if(null != t.getDefaultCase())
            s.setDefaultCase((IJCTCase)t.getDefaultCase().accept(this, v));

        for(final IJCTCase c : t.getCases())
            s.addCase((IJCTCase)c.accept(this, v));

        return this.visitSourceCodePart(s, t);
    }

    public IJCTElement visitSynchronized(final IJCTSynchronized t, final Void v)
    {
        final IJCTSynchronized s = this.factory.createSynchronized((IJCTExpression)t.getSynchronizedObject().accept(this, v));

        s.setBody((IJCTBlock)t.getBody().accept(this, v));

        return this.visitSourceCodePart(s, t);
    }

    public IJCTElement visitThrow(final IJCTThrow t, final Void v)
    { return this.visitSourceCodePart(this.factory.createThrow((IJCTExpression)t.getThrownException().accept(this, v)), t); }

    public IJCTElement visitTry(final IJCTTry t, final Void v)
    {
        final IJCTTry aJCTTry = this.factory.createTry();

        aJCTTry.setTryBlock((IJCTBlock)t.getTryBlock().accept(this, v));

        for(final IJCTCatch c : t.getCatchBlocks())
            aJCTTry.addCatchBlock((IJCTCatch)c.accept(this, v));

        if(null != t.getFinallyBlock())
            aJCTTry.setFinallyBlock((IJCTBlock)t.getFinallyBlock().accept(this, v));

        return this.visitSourceCodePart(aJCTTry, t);
    }

    public IJCTElement visitCast(final IJCTCast t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createCast((IJCTType)t.getType().accept(this, v),
                                                                (IJCTExpression)t.getOperand().accept(this, v)), t);
    }

    public IJCTElement visitUnaryMinus(final IJCTUnaryMinus t, final Void v)
    { return this.visitSourceCodePart(this.factory.createUnaryMinus((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitUnaryPlus(final IJCTUnaryPlus t, final Void v)
    { return this.visitSourceCodePart(this.factory.createUnaryPlus((IJCTExpression)t.getOperand().accept(this, v)), t); }

    public IJCTElement visitUnsignedRightShift(final IJCTUnsignedRightShift t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createUnsignedRightShift((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                                              (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitUnsignedRightShiftAssignment(final IJCTUnsignedRightShiftAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createUnsignedRightShiftAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                                        (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    public IJCTElement visitWhile(final IJCTWhile t, final Void v)
    {
        final IJCTWhile result = this.factory.createWhile((IJCTExpression)t.getCondition().accept(this, v));
        result.setBody((IJCTStatement)t.getBody().accept(this, v));
        return this.visitSourceCodePart(result, t);
    }

    public IJCTElement visitXor(final IJCTXor t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createXor((IJCTExpression)t.getLeftOperand().accept(this, v),
                                                               (IJCTExpression)t.getRightOperand().accept(this, v)), t);
    }

    public IJCTElement visitXorAssignment(final IJCTXorAssignment t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createXorAssignment((IJCTExpression)t.getVariable().accept(this, v),
                                                                         (IJCTExpression)t.getValue().accept(this, v)), t);
    }

    @Override
    public IJCTElement visitExpressionStatement(final IJCTExpressionStatement t, final Void v)
    {
        return this.visitSourceCodePart(this.factory.createExpressionStatement((IJCTExpression)t.getExpression().accept(this, v)), t);
    }

    @Override
    public IJCTElement visitField(final IJCTField fieldElement,
                                  final Void additionalParameter)
    { return this.visitVariable(fieldElement, additionalParameter); }

    @Override
    public IJCTElement visitParameter(final IJCTParameter parameterElement,
                                      final Void additionalParameter)
    { return this.visitVariable(parameterElement, additionalParameter); }

    @Override
    public IJCTElement visitComment(final IJCTComment commentElement,
                                    final Void additionalParameter)
    {
        return this.commentTranslator.get(commentElement);
    }
}
