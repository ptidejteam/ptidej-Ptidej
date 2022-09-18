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

import java.util.List;
import java.util.Collections;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;


import java.io.Writer;
import java.io.IOException;


import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.util.collection.IndirectCollection;


/**
 * This class represents a (declaration of a) class
 * An enum is a class.
 * The class members are indexed, the indexes are globals for all class members, static or not.
 * 
 * Every class has three variable members: this, super and class (static), modifying them result in undefined behaviour.
 * Their respectives indexes are undefined but they <strong>must</strong> be the three first members.
 * Every class must have a super class. Only java.lang.Object and interfaces may have {@code null} as super class (possibly itself for Object).
 * Two classes are equals iff they have the same FQN
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTClass}
 *
 * @author Mathieu Lemoine
 */
class JCTClass extends JCTClassMember<IJCTClassMember> implements IJCTClass
{
    /**
     * direct super class of this class
     */
    private IJCTClassType directSuperClass;
    
    /**
     * is interface of this class
     */
    private boolean isInterface;
    
    /**
     * List of declared members of this class
     */
    private final List<IJCTClassMember> declaredMembers = this.createInternalList();
    
    /**
     * special members of this class
     */
    private final IJCTField[] specialMembers;
    
    /**
     * is ghost of this class
     */
    private boolean isGhost;
    
    /**
     * Set of directly implemented interfaces of this class
     */
    private final Set<IJCTClassType> directlyImplementedInterfaces = new HashSet<IJCTClassType>();
    
    
    JCTClass(final IJCTRootNode aRootNode, final String name, final boolean isInterface, final boolean isGhost)
    {
        super(aRootNode, name);
        this.setIsInterface(isInterface);
        this.setIsGhost(isGhost);
        this.specialMembers = this.createSpecialMembers();
        super.backpatchElements(new IndirectCollection<IJCTClassMember>(Collections.unmodifiableCollection(Arrays.asList(this.specialMembers)), this.declaredMembers));
    }
    
    /**
     * Returns a new IJCTClassType on this class.
     * Roughly equivalent to {@code this.getRootNode().getFactory().createClassType(this.getRootNode().getFactory().createSimpleSelector(this))}
     */
    @Override
	public IJCTClassType createClassType()
    {
        final IJCTFactory f = this.getRootNode().getFactory();
        return f.createClassType(f.createSimpleSelector(((IJCTClass)this)));
    }
    
    /**
     * Returns the field representing the variable {@code this} of this class.
     * Included in the enclosed elements
     */
    @Override
	public IJCTField getThisField()
    {
        return this.specialMembers[0];
    }
    
    /**
     * Returns the field representing the variable {@code super} of this class.
     * Included in the enclosed elements
     */
    @Override
	public IJCTField getSuperField()
    {
        return this.specialMembers[1];
    }
    
    /**
     * Returns the field representing the static variable {@code class} of this class.
     * Included in the enclosed elements
     */
    @Override
	public IJCTField getClassField()
    {
        return this.specialMembers[2];
    }
    
    /**
     * Returns a unique ID for this element.
     */
    @Override
    public String getID()
    {
        return (null == this.isStatic() ? this.getFQN() : this.getName()) + super.getID();
    }
    
    /**
     * Adds a "declared member" at the index (or move it there)
     */
    @Override
	public void addDeclaredMember(final int anIndex, final IJCTClassMember declaredMember)
    {
        this.declaredMembers.add(anIndex, declaredMember);
    }
    
    /**
     * Adds a "declared member" at the end of the list (or move it there)
     */
    @Override
	public void addDeclaredMember(final IJCTClassMember declaredMember)
    {
        this.declaredMembers.add(declaredMember);
    }
    
    /**
     * Removes this declared member from the list
     */
    @Override
	public void removeDeclaredMember(final IJCTClassMember declaredMember)
    {
        this.declaredMembers.remove(declaredMember);
    }
    
    /**
     * Remove the declared member at the index
     */
    @Override
	public void removeDeclaredMember(final int anIndex)
    {
        this.declaredMembers.remove(anIndex);
    }
    
    /**
     * Returns the list of declared members of this class
     * <em>Part of the enclosed elements.</em>
     */
    @Override
	public List<IJCTClassMember> getDeclaredMembers()
    {
        return Collections.unmodifiableList(this.declaredMembers);
    }
    
    /**
     * Modifies the is ghost of this class
     *
     * @param isGhost the new is ghost
     */
    @Override
	public void setIsGhost(final boolean isGhost)
    {
        this.isGhost = isGhost;
    }
    
    /**
     * Returns the is ghost of this class
     */
    @Override
	public boolean getIsGhost()
    {
        return this.isGhost;
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.CLASS)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.CLASS;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitClass(this, aP);
    }
    
    @Override
    protected JCTPathPartBuilder createPathPart()
    {
        final JCTPathPartBuilder p = super.createPathPart();
    
        if(null == p.getInformativeData())
        {
            byte informativeData = 0x00;
            if(this.getIsGhost())
                informativeData |= 0x01;
            
            if(this.getIsInterface())
                informativeData |= 0x02;
    
            p.setInformativeData(new byte[] { informativeData });
        }
    
        return p;
    }
    
    @Override
    void updateEnclosingElement(final JCTElementContainer e)
    {
        IJCTElement t = e;
        while(null != t)
        {
            if(this == t)
                throw new IllegalArgumentException("Auto-Enclosing class" + this + " : " + e);
    
            t = t.getEnclosingElement();
        }
    
        super.updateEnclosingElement(e);
    }
    
    private IJCTField[] createSpecialMembers()
    {
        final IJCTRootNode r = this.getRootNode();
        final IJCTFactory f = r.getFactory();
    
        final IJCTField vThis = f.createField(Constants.THIS_NAME, this.createClassType());
        vThis.addModifier(JCTModifiers.PRIVATE);
        vThis.addModifier(JCTModifiers.FINAL);
        ((JCTElement)vThis).updateEnclosingElement(this);
    
        final IJCTField vSuper = f.createField(Constants.SUPER_NAME,
                                                     this.getDirectSuperClass() != null ? this.getDirectSuperClass() : this.createClassType());
        vSuper.addModifier(JCTModifiers.PRIVATE);
        vSuper.addModifier(JCTModifiers.FINAL);
        ((JCTElement)vSuper).updateEnclosingElement(this);
    
        final IJCTClassType tClass = r.getType(Constants.CLASS_BINARYNAME_CLASS, IJCTClassType.class);
        final IJCTField vClass = f.createField(Constants.CLASS_NAME,
                                                     tClass != null ? tClass : this.createClassType());
        vClass.addModifier(JCTModifiers.PUBLIC);
        vClass.addModifier(JCTModifiers.FINAL);
        vClass.addModifier(JCTModifiers.STATIC);
        ((JCTElement)vClass).updateEnclosingElement(this);
    
        return new IJCTField[] { vThis, vSuper, vClass };
    }
    
    @Override
	public boolean getIsInterface()
    { return this.isInterface; }
    
    // todo: referential integrity checking !!
    @Override
	public void setIsInterface(final boolean i)
    {
        this.isInterface = i;
    
        if(!this.getIsInterface() &&
           null == this.getDirectSuperClass())
        {
            final IJCTClassType c = this.getRootNode().getType(Constants.CLASS_BINARYNAME_OBJECT, IJCTClassType.class);
            if(c != null)
                this.setDirectSuperClass(c);
        }
    }
    
    @Override
	public String getFQN()
    {
        if(null == this.getEnclosingElement())
            return this.getName();
    
        if(JCTKind.COMPILATION_UNIT == this.getEnclosingElement().getKind())
        {
            final IJCTPackage p = (IJCTPackage)this.getEnclosingElement().getEnclosingElement();
            return null == p || p.isUnnamed()
                ? this.getName()
                : p.getName() + Constants.DOT_SEPARATOR + this.getName();
        }
        else
            return this.getDirectEnclosingClass().getFQN() + Constants.DOT_SEPARATOR + this.getName();
    }
    
    
    @Override
    protected List<JCTElementContainer<?>> seePreviousPathStep()
    {
        final JCTClass enclosingClass = (JCTClass)this.getDirectEnclosingClass();
        final List<JCTElementContainer<?>> list = super.seePreviousPathStep();
    
        if(null != enclosingClass)
            list.add(enclosingClass);
        else if(null != this.getEnclosingElement()
                && JCTKind.COMPILATION_UNIT == this.getEnclosingElement().getKind())
            if(null != this.getEnclosingElement().getEnclosingElement())
                list.add((JCTElementContainer<?>)this.getEnclosingElement().getEnclosingElement());
            else
                list.add((JCTElementContainer<?>)this.getEnclosingElement());
    
        return list;
    }
    
    
    @Override
	public IJCTClassType getDirectSuperClass()
    { return this.getIsInterface() ? null : this.directSuperClass; }
    
    @Override
	public void setDirectSuperClass(final IJCTClassType aJCTClassType)
    {
        IJCTClassType c = aJCTClassType;
    
        if(!this.getIsInterface()
           && null == c)
            c = this.getRootNode().getType(Constants.CLASS_BINARYNAME_OBJECT, IJCTClassType.class);
    
        if(c.getSelector().getElement().getIsInterface())
            throw new IllegalArgumentException("An interface can not be extended");
    
        this.directSuperClass = c;
    
        if(null != this.specialMembers)
            this.getSuperField().setType(c);
    }
    
    @Override
	public Set<IJCTClassType> getDirectlyImplementedInterfaces()
    { return Collections.unmodifiableSet(this.directlyImplementedInterfaces); }
    
    @Override
	public void addDirectlyImplementedInterface(final IJCTClassType c)
    {
        if(!c.getSelector().getElement().getIsInterface())
            throw new IllegalArgumentException("A class cannot be put in the implements list");
    
        this.directlyImplementedInterfaces.add(c);
    }
    
    @Override
	public void removeDirectlyImplementedInterface(final IJCTClassType c)
    { this.directlyImplementedInterfaces.remove(c); }
    
    @Override
	public Collection<IJCTClass> getNestedClasses(final Boolean staticOnly)
    {
        final Collection<IJCTClass> result = new LinkedList<IJCTClass>();
    
        for(final IJCTClassMember cm : this.getEnclosedElements())
            if(cm instanceof IJCTClass
               && (null == staticOnly
                   || staticOnly.equals(cm.isStatic())))
                result.add((IJCTClass)cm);
    
        return Collections.unmodifiableCollection(result);
    }
    
    @Override
	public Collection<IJCTField> getFields(final Boolean staticOnly, final boolean includeSpecials)
    {
        final Collection<IJCTField> result = new LinkedList<IJCTField>();
    
        for(final IJCTClassMember cm : includeSpecials
                ? this.getEnclosedElements()
                : this.getDeclaredMembers())
            if(cm instanceof IJCTField
               && (null == staticOnly
                   || staticOnly.equals(cm.isStatic())))
                result.add((IJCTField)cm);
    
        return Collections.unmodifiableCollection(result);
    }
    
    @Override
	public Collection<IJCTMethod> getMethods(final Boolean staticOnly)
    {
        final Collection<IJCTMethod> result = new LinkedList<IJCTMethod>();
    
        for(final IJCTClassMember cm : this.getEnclosedElements())
            if(cm instanceof IJCTMethod
               && (null == staticOnly
                   || staticOnly.equals(cm.isStatic())))
                result.add((IJCTMethod)cm);
    
        return Collections.unmodifiableCollection(result);
    }
    
    @Override
	public Writer getSourceCode(final Writer w) throws IOException
    {
        for(final JCTModifiers m : this.getModifiers())
            w.append(m.toString().toLowerCase()).append(' ');
    
        w.append(this.getIsInterface() ? "interface" : "class")
            .append(' ')
            .append(this.getName());
    
        if(null != this.getDirectSuperClass()
           && ! "Ljava.lang.Object".equals(this.getDirectSuperClass().getTypeName()))
        {
            w.append("\nextends ");
            this.getDirectSuperClass().getSourceCode(w);
        }
    
        if(this.getDirectlyImplementedInterfaces().size() > 0)
        {
            w.append('\n')
                .append(this.getIsInterface() ? "extends" : "implements")
                .append(' ');
    
            final SortedSet<IJCTClassType> implemented = new TreeSet<IJCTClassType>(new Comparator<IJCTClassType>() { @Override
			public int compare(final IJCTClassType o1, final IJCTClassType o2) { return o1.getSelector().getElement().getFQN().compareTo(o2.getSelector().getElement().getFQN()); } });
            implemented.addAll(this.getDirectlyImplementedInterfaces());
    
            final Iterator<IJCTClassType> it = implemented.iterator();
            while(it.hasNext())
            {
                it.next().getSourceCode(w);
                if(it.hasNext())
                    w.append(", ");
            }
        }
    
        w.append("\n{\n");
    
        for(final IJCTClassMember cm : this.getDeclaredMembers())
            cm.getSourceCode(w).append('\n');
    
        return w.append("\n}\n");
    }
    
    private static final Map<JCTModifiers, Integer> modifiersIncompatibility = new HashMap<JCTModifiers, Integer>();
    
    static
    {
        JCTClass.modifiersIncompatibility.put(JCTModifiers.ABSTRACT,
                                              JCTModifiers.FINAL.getFlag());
    
        JCTClass.modifiersIncompatibility.put(JCTModifiers.FINAL,
                                              JCTModifiers.ABSTRACT.getFlag());
    
        JCTClass.modifiersIncompatibility.put(JCTModifiers.PRIVATE,
                                              JCTModifiers.PROTECTED.getFlag()
                                              | JCTModifiers.PUBLIC.getFlag());
    
        JCTClass.modifiersIncompatibility.put(JCTModifiers.PROTECTED,
                                              JCTModifiers.PRIVATE.getFlag()
                                              | JCTModifiers.PUBLIC.getFlag());
    
        JCTClass.modifiersIncompatibility.put(JCTModifiers.PUBLIC,
                                              JCTModifiers.PRIVATE.getFlag()
                                              | JCTModifiers.PROTECTED.getFlag());
    
        JCTClass.modifiersIncompatibility.put(JCTModifiers.STATIC, 0);
        JCTClass.modifiersIncompatibility.put(JCTModifiers.STRICTFP, 0);
    }
    
    @Override
    protected boolean hasIncompatibleModifier(final JCTModifiers m)
    {
        final Integer incompatibility = JCTClass.modifiersIncompatibility.get(m);
        if(null == incompatibility)
            throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by classes.");
        return (this.getModifierFlags() & incompatibility) != 0;
    }

}
