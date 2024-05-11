/**
 * @author Mathieu Lemoine
 * @created 2008-08-20 (水)
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


import java.util.List;
import java.util.Collections;



import java.io.Writer;
import java.io.IOException;

import java.util.Iterator;

import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTMethodInvocation;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTSelector;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents a method invocation
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTMethodInvocation}
 *
 * @author Mathieu Lemoine
 */
class JCTMethodInvocation extends JCTSourceCodePart<IJCTExpression> implements IJCTMethodInvocation
{
    /**
     * method selector of this method invocation
     */
    private final NotNullableReference<IJCTSelector<IJCTMethod>> methodSelector;
    
    /**
     * List of arguments of this method invocation
     */
    private final List<IJCTExpression> arguments = this.createInternalList();
    
    
    JCTMethodInvocation(final IJCTRootNode aRootNode, final IJCTSelector<IJCTMethod> methodSelector)
    {
        super(aRootNode);
        this.methodSelector = this.createInternalReference(methodSelector);
        super.backpatchElements(new IndirectCollection<IJCTExpression>(this.methodSelector, this.arguments));
    }
    
    /**
     * Modifies the method selector of this method invocation
     *
     * @param methodSelector the new method selector
     */
    @Override
	public void setMethodSelector(final IJCTSelector<IJCTMethod> methodSelector)
    {
        this.methodSelector.set(methodSelector);
    }
    
    /**
     * Returns the method selector of this method invocation
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTSelector<IJCTMethod> getMethodSelector()
    {
        return this.methodSelector.get();
    }
    
    /**
     * Adds a "argument" at the index (or move it there)
     */
    @Override
	public void addArgument(final int anIndex, final IJCTExpression argument)
    {
        this.arguments.add(anIndex, argument);
    }
    
    /**
     * Adds a "argument" at the end of the list (or move it there)
     */
    @Override
	public void addArgument(final IJCTExpression argument)
    {
        this.arguments.add(argument);
    }
    
    /**
     * Removes this argument from the list
     */
    @Override
	public void removeArgument(final IJCTExpression argument)
    {
        this.arguments.remove(argument);
    }
    
    /**
     * Remove the argument at the index
     */
    @Override
	public void removeArgument(final int anIndex)
    {
        this.arguments.remove(anIndex);
    }
    
    /**
     * Returns the list of arguments of this method invocation
     * <em>Part of the enclosed elements.</em>
     */
    @Override
	public List<IJCTExpression> getArguments()
    {
        return Collections.unmodifiableList(this.arguments);
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.METHOD_INVOCATION)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.METHOD_INVOCATION;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitMethodInvocation(this, aP);
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        final IJCTMethod m = this.getMethodSelector().getElement();
    
        if(null == m)
            return null;
    
        return m.getReturnType();
    }
    
    @Override
	public Writer getSourceCode(final Writer w) throws IOException
    {
        this.getMethodSelector().getSourceCode(w)
            .append('(');
    
        final Iterator<IJCTExpression> it = this.getArguments().iterator();
        while(it.hasNext())
        {
            it.next().getSourceCode(w);
            if(it.hasNext())
                w.append(", ");
        }
    
        return w.append(')');
    }

}
