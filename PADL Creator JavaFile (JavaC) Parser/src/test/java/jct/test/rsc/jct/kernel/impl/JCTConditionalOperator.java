/**
 * @author Mathieu Lemoine
 * @created 2009-01-08 (木)
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



import java.util.Set;
import java.util.HashSet;

import java.io.Writer;
import java.io.IOException;


import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTConditionalOperator;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents a conditional operator expression
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTConditionalOperator}
 *
 * @author Mathieu Lemoine
 */
class JCTConditionalOperator extends JCTSourceCodePart<IJCTExpression> implements IJCTConditionalOperator
{
    /**
     * condition of this conditional operator
     */
    private final NotNullableReference<IJCTExpression> condition;
    
    /**
     * then expression of this conditional operator
     */
    private final NotNullableReference<IJCTExpression> thenExpression;
    
    /**
     * else expression of this conditional operator
     */
    private final NotNullableReference<IJCTExpression> elseExpression;
    
    
    JCTConditionalOperator(final IJCTRootNode aRootNode, final IJCTExpression condition, final IJCTExpression thenExpression, final IJCTExpression elseExpression)
    {
        super(aRootNode);
        this.condition = this.createInternalReference(condition);
        this.thenExpression = this.createInternalReference(thenExpression);
        this.elseExpression = this.createInternalReference(elseExpression);
        super.backpatchElements(new IndirectCollection<IJCTExpression>(this.condition, this.thenExpression, this.elseExpression));
    }
    
    /**
     * Modifies the condition of this conditional operator
     *
     * @param condition the new condition
     */
    @Override
	public void setCondition(final IJCTExpression condition)
    {
        this.condition.set(condition);
    }
    
    /**
     * Returns the condition of this conditional operator
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getCondition()
    {
        return this.condition.get();
    }
    
    /**
     * Modifies the then expression of this conditional operator
     *
     * @param thenExpression the new then expression
     */
    @Override
	public void setThenExpression(final IJCTExpression thenExpression)
    {
        this.thenExpression.set(thenExpression);
    }
    
    /**
     * Returns the then expression of this conditional operator
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getThenExpression()
    {
        return this.thenExpression.get();
    }
    
    /**
     * Modifies the else expression of this conditional operator
     *
     * @param elseExpression the new else expression
     */
    @Override
	public void setElseExpression(final IJCTExpression elseExpression)
    {
        this.elseExpression.set(elseExpression);
    }
    
    /**
     * Returns the else expression of this conditional operator
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTExpression getElseExpression()
    {
        return this.elseExpression.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.CONDITIONAL_OPERATOR)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.CONDITIONAL_OPERATOR;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitConditionalOperator(this, aP);
    }
    
    private static IJCTType UnboxedType(final IJCTType t)
    {
        if(null == t)
            return null;
    
        if(t instanceof IJCTPrimitiveType)
            return t;
    
        final IJCTClass c = ((IJCTClassType)t).getSelector().getElement();
    
        if(     Constants.CLASSPATH_DOUBLE .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.DOUBLE);
        else if(Constants.CLASSPATH_FLOAT  .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.FLOAT);
        else if(Constants.CLASSPATH_LONG   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.LONG);
        else if(Constants.CLASSPATH_INT    .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.INTEGER);
        else if(Constants.CLASSPATH_SHORT  .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.SHORT);
        else if(Constants.CLASSPATH_BYTE   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.BYTE);
        else if(Constants.CLASSPATH_BOOLEAN.equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.BOOLEAN);
        else if(Constants.CLASSPATH_CHAR   .equals(c.getFQN())) return t.getRootNode().getType(JCTPrimitiveTypes.CHARACTER);
        else                                                    return t;
    }
    
    private static IJCTType BoxedType(final IJCTType t)
    {
        if(!(t instanceof IJCTPrimitiveType))
            return t;
    
        switch(((IJCTPrimitiveType)t).getType())
        {
            case DOUBLE:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_DOUBLE,  IJCTClassType.class);
            case FLOAT:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_FLOAT,   IJCTClassType.class);
            case LONG:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_LONG,    IJCTClassType.class);
            case INTEGER:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_INT,     IJCTClassType.class);
            case SHORT:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_SHORT,   IJCTClassType.class);
            case BYTE:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_BYTE,    IJCTClassType.class);
            case BOOLEAN:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_BOOLEAN, IJCTClassType.class);
            case CHARACTER:
                return t.getRootNode().getType(Constants.CLASS_BINARYNAME_CHAR,    IJCTClassType.class);
            case VOID:
            default:
                return t;
        }
    }
    
    private static void GetLUB(final IJCTClassType c, final Set<IJCTNonPrimitiveType> grid, final Set<IJCTType> result)
    {
        if(grid.contains(c))
        {
            result.add(c);
            return;
        }
    
        final IJCTClassType e = c.getSelector().getElement().getDirectSuperClass();
    
        if(null != e)
            JCTConditionalOperator.GetLUB(e, grid, result);
    
        for(final IJCTClassType i : c.getSelector().getElement().getDirectlyImplementedInterfaces())
            JCTConditionalOperator.GetLUB(i, grid, result);
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        return JCTConditionalOperator.GetTypeResult(this.getThenExpression().getTypeResult(),
                                                    this.getElseExpression().getTypeResult());
    }
    
    private static IJCTType GetTypeResult(final IJCTType type, final IJCTType expression)
    {
        IJCTType t = type;
        IJCTType e = expression;
    
        if(t == e)
            return t; // same type propagation, trivial propagation
    
        final Set<IJCTType> result = new HashSet<IJCTType>();
    
        if((t instanceof IJCTIntersectionType)
           && (e instanceof IJCTIntersectionType))
            for(final IJCTType ti : ((IJCTIntersectionType)t).getTypes())
                for(final IJCTType ei : ((IJCTIntersectionType)e).getTypes())
                    result.add(JCTConditionalOperator.GetTypeResult(ti, ei));
        else if(t instanceof IJCTIntersectionType)
            for(final IJCTType ti : ((IJCTIntersectionType)t).getTypes())
                result.add(JCTConditionalOperator.GetTypeResult(ti, e));
        else if(e instanceof IJCTIntersectionType)
            for(final IJCTType ei : ((IJCTIntersectionType)e).getTypes())
                result.add(JCTConditionalOperator.GetTypeResult(t, ei));
        else
        {
            t = JCTConditionalOperator.UnboxedType(t);
            e = JCTConditionalOperator.UnboxedType(e);
    
            if((t instanceof IJCTPrimitiveType)
               && (e instanceof IJCTPrimitiveType))
                return ((IJCTPrimitiveType)t).getType().compareTo(((IJCTPrimitiveType)e).getType()) > 0 ?
                    t : e; // Primitive type propagation
    
            if((t instanceof IJCTPrimitiveType)
               && JCTPrimitiveTypes.VOID == ((IJCTPrimitiveType)t).getType())
                return e;
    
            if((e instanceof IJCTPrimitiveType)
               && JCTPrimitiveTypes.VOID == ((IJCTPrimitiveType)e).getType())
                return t;
    
            t = JCTConditionalOperator.BoxedType(t);
            e = JCTConditionalOperator.BoxedType(e);
    
            if(!(t instanceof IJCTClass)
               || !(e instanceof IJCTClass))
                return null; // not same array type !
    
            final IJCTClassType ct = (IJCTClassType)t;
            final IJCTClassType ce = (IJCTClassType)e;
    
            JCTConditionalOperator.GetLUB(ce, ct.getAllSuperClasses(), result);
        }
    
        return t.getRootNode().getType(new JCTIntersectionType(t.getRootNode(), result.toArray(new IJCTType[0])).getTypeName(), IJCTType.class);
    }
    
    @Override
	public Writer getSourceCode(final Writer w) throws IOException
    {
        this.getCondition().getSourceCode(w).append(" ? ");
        this.getThenExpression().getSourceCode(w).append(" : ");
        return this.getElseExpression().getSourceCode(w);
    }

}
