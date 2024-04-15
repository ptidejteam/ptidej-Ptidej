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
import java.util.Collections;
import java.util.Collection;
import java.util.TreeSet;


import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;


/**
 * This class represents a class member
 * Two imports are equals iff they have the same textual representation
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTClassMember}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTClassMember<Component extends IJCTElement> extends JCTSourceCodePart<Component> implements IJCTClassMember
{
    JCTClassMember(final IJCTRootNode aRootNode, final String name, final Collection<Component> elements)
    {
        super(aRootNode, name, elements);
    }
    
    JCTClassMember(final IJCTRootNode aRootNode, final String name)
    {
        this(aRootNode, name, null);
    }
    
    private int modifiers = 0x0;
    
    @Override
	public Set<JCTModifiers> getModifiers()
    {
        final Set<JCTModifiers> result = new TreeSet<JCTModifiers>();
        for(final JCTModifiers m : JCTModifiers.values())
            if((this.modifiers & m.getFlag()) != 0)
                result.add(m);
    
        return Collections.unmodifiableSet(result);
    }
    
    protected int getModifierFlags()
    { return this.modifiers; }
    
    protected abstract boolean hasIncompatibleModifier(final JCTModifiers m);
    
    @Override
	public void addModifier(final JCTModifiers m)
    {
        if(this.hasIncompatibleModifier(m))
            throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") has an uncompatibility with one of the modifiers of this class member (0x" + Integer.toString(this.getModifierFlags(), 16) + ")");
    
        this.modifiers |= m.getFlag();
    }
    
    @Override
	public void removeModifier(final JCTModifiers m)
    { this.modifiers &= ~m.getFlag(); }
    
    @Override
	public boolean isMemberOf(final IJCTNonPrimitiveType c)
    {
        if(null == this.isStatic()) 
            return false;
    
        return c.getAllSuperClasses().contains(this.getEnclosingElement().getKind() == JCTKind.ARRAY_TYPE
                                               ? (IJCTArrayType)this.getEnclosingElement()
                                               : ((IJCTClass)this.getEnclosingElement()).createClassType());
    }
    
    @Override
	public Boolean isStatic()
    {
        if(null == this.getEnclosingElement()
           || (JCTKind.ARRAY_TYPE != this.getEnclosingElement().getKind()
               && JCTKind.CLASS != this.getEnclosingElement().getKind()))
            return null;
    
        return this.getModifiers().contains(JCTModifiers.STATIC);
    }
    
    @Override
	public IJCTClass getDirectEnclosingClass()
    {
        IJCTElement e = this.getEnclosingElement();
    
        while (null != e
               && JCTKind.CLASS != e.getKind()
               && JCTKind.ARRAY_TYPE != e.getKind())
            e = e.getEnclosingElement();
    
        return (IJCTClass) e;
    }
    
    @Override
	public IJCTClass getTopLevelEnclosingClass()
    {
        IJCTClass c = this.getDirectEnclosingClass();
    
        if(null == c)
            return null;
    
        IJCTClass ce = c.getDirectEnclosingClass();
    
        while(null != ce)
        {
            c = ce;
            ce = c.getDirectEnclosingClass();
        }
    
        return c;
    }

}
