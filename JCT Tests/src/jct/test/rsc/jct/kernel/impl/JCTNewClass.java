/**
 * @author Mathieu Lemoine
 * @created 2009-01-0 8 (木)
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



import java.util.Iterator;

import java.io.Writer;
import java.io.IOException;

import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTNewClass;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NullableReference;


/**
 * This class represents a new class expression
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTNewClass}
 *
 * @author Mathieu Lemoine
 */
class JCTNewClass extends JCTSourceCodePart<IJCTElement> implements IJCTNewClass
{
    /**
     * class type of this new class
     */
    private IJCTClassType classType;
    
    /**
     * annonymous class of this new class
     */
    private final NullableReference<IJCTClass> annonymousClass = this.createNullableInternalReference();
    
    /**
     * selecting expression of this new class
     */
    private final NullableReference<IJCTExpression> selectingExpression = this.createNullableInternalReference();
    
    /**
     * List of arguments of this new class
     */
    private final List<IJCTExpression> arguments = this.createInternalList();
    
    
    JCTNewClass(final IJCTRootNode aRootNode, final IJCTClassType classType)
    {
        super(aRootNode);
        this.classType = classType;
        super.backpatchElements(new IndirectCollection<IJCTElement>(this.selectingExpression, this.annonymousClass, this.arguments));
    }
    
    /**
     * Modifies the selecting expression of this new class
     *
     * @param selectingExpression the new selecting expression, can be {@code null}
     */
    @Override
	public void setSelectingExpression(final IJCTExpression selectingExpression)
    {
        this.selectingExpression.set(selectingExpression);
    }
    
    /**
     * Returns the selecting expression of this new class
     * <em>Included in the enclosed elements.</em>
     *
     * @return null iff there is no selecting expression
     */
    @Override
	public IJCTExpression getSelectingExpression()
    {
        return this.selectingExpression.get();
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
     * Returns the list of arguments of this new class
     * <em>Part of the enclosed elements.</em>
     */
    @Override
	public List<IJCTExpression> getArguments()
    {
        return Collections.unmodifiableList(this.arguments);
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.NEW_CLASS)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.NEW_CLASS;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitNewClass(this, aP);
    }
    
    @Override
	public IJCTClassType getClassType()
    { return this.classType; }
    
    @Override
	public void setClassType(final IJCTClassType classType)
    {
        if(null != this.getAnnonymousClass()
           && !classType.isExtendingOrImplementing(this.getAnnonymousClass().createClassType()))
            throw new IllegalArgumentException("The annonymous declared class must extend or implements the identified class");
    
        if(null == this.getAnnonymousClass()
           && classType.getSelector().getElement().getIsInterface())
            throw new IllegalArgumentException("Instanciating an interface is not allewd.");
    
        this.classType = classType;
    }
    
    @Override
	public IJCTClass getAnnonymousClass()
    { return this.annonymousClass.get(); }
    
    @Override
	public void setAnnonymousClass(final IJCTClass annonymousClass)
    {
        if(null != annonymousClass)
        {
            if(!this.getClassType().isExtendingOrImplementing(annonymousClass.createClassType()))
                throw new IllegalArgumentException("The annonymous declared class must extend or implements the identified class");
    
            if(annonymousClass.getIsInterface())
                throw new IllegalArgumentException("Annonymous interface does not exists");
        }
        else
            if(this.getClassType().getSelector().getElement().getIsInterface())
                throw new IllegalArgumentException("Instanciating an interface is not allewd.");
    
        this.annonymousClass.set(annonymousClass);
    }
    
    @Override
	public IJCTType getTypeResult()
    {
        final IJCTClass c = this.getAnnonymousClass();
        return null != c ? c.createClassType() : this.getClassType();
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        if(null != this.getSelectingExpression())
            this.getSelectingExpression().getSourceCode(aWriter).append('.');
    
        aWriter.append("new ");
        this.getClassType().getSourceCode(aWriter)
            .append('(');
    
        final Iterator<IJCTExpression> it = this.getArguments().iterator();
        while(it.hasNext())
        {
            it.next().getSourceCode(aWriter);
            if(it.hasNext())
                aWriter.append(", ");
        }
        aWriter.append(')');
    
        if(null != this.getAnnonymousClass())
        {
            aWriter.append("\n{\n");
    
            for(final IJCTClassMember cm : this.getAnnonymousClass().getDeclaredMembers())
                cm.getSourceCode(aWriter).append('\n');
    
            aWriter.append('}');
        }
    
        return aWriter;
    }

}
