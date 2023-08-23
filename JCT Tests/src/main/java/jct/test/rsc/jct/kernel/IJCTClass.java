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

package jct.test.rsc.jct.kernel;


import java.util.Set;

import java.util.List;
import java.util.Collection;


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
 * @author Mathieu Lemoine
 */
public interface IJCTClass extends IJCTClassMember, IJCTElementContainer<IJCTClassMember>
{
    /**
     * Returns the Fully Qualified Name of the class
     */
    public String getFQN();
    
    /**
     * Returns a new IJCTClassType on this class.
     * Roughly equivalent to {@code this.getRootNode().getFactory().createClassType(this.getRootNode().getFactory().createSimpleSelector(this))}
     */
    public IJCTClassType createClassType();
    
    /**
     * Returns the field representing the variable {@code this} of this class.
     * Included in the enclosed elements
     */
    public IJCTField getThisField();
    
    /**
     * Returns the field representing the variable {@code super} of this class.
     * Included in the enclosed elements
     */
    public IJCTField getSuperField();
    
    /**
     * Returns the field representing the static variable {@code class} of this class.
     * Included in the enclosed elements
     */
    public IJCTField getClassField();
    
    /**
     * Returns the list of nested class
     *
     * @param staticOnly whether you want only the static or only the non-static ones, if null, returns both of them
     */
    public Collection<IJCTClass> getNestedClasses(final Boolean staticOnly);
    
    /**
     * Returns the list of fields
     *
     * @param staticOnly whether you want only the static or only the non-static ones, if null, returns both of them
     * @param includeSpecials whether you want the three special fields: this, super and class
     */
    public Collection<IJCTField> getFields(final Boolean staticOnly, final boolean includeSpecials);
    
    /**
     * Returns the list of methods
     *
     * @param staticOnly whether you want only the static or only the non-static ones, if null, returns both of them
     */
    public Collection<IJCTMethod> getMethods(final Boolean staticOnly);
    
    /**
     * Modifies the direct super class of this class
     *
     * @param directSuperClass the new direct super class
     */
    public void setDirectSuperClass(final IJCTClassType directSuperClass);
    
    /**
     * Returns the direct super class of this class
     */
    public IJCTClassType getDirectSuperClass();
    
    /**
     * Modifies the is interface of this class
     *
     * @param isInterface the new is interface
     */
    public void setIsInterface(final boolean isInterface);
    
    /**
     * Returns the is interface of this class
     */
    public boolean getIsInterface();
    
    /**
     * Adds a "declared member" at the index (or move it there)
     */
    public void addDeclaredMember(final int anIndex, final IJCTClassMember declaredMember);
    
    /**
     * Adds a "declared member" at the end of the list (or move it there)
     */
    public void addDeclaredMember(final IJCTClassMember declaredMember);
    
    /**
     * Removes this declared member from the list
     */
    public void removeDeclaredMember(final IJCTClassMember declaredMember);
    
    /**
     * Remove the declared member at the index
     */
    public void removeDeclaredMember(final int anIndex);
    
    /**
     * Returns the list of declared members of this class
     * <em>Part of the enclosed elements.</em>
     */
    public List<IJCTClassMember> getDeclaredMembers();
    
    /**
     * Modifies the is ghost of this class
     *
     * @param isGhost the new is ghost
     */
    public void setIsGhost(final boolean isGhost);
    
    /**
     * Returns the is ghost of this class
     */
    public boolean getIsGhost();
    
    /**
     * Adds a "directly implemented interface" in the set, if it was not already in it
     */
    public void addDirectlyImplementedInterface(final IJCTClassType directlyImplementedInterface);
    
    /**
     * Removes this directly implemented interface from the set
     */
    public void removeDirectlyImplementedInterface(final IJCTClassType directlyImplementedInterface);
    
    /**
     * Returns the set of directly implemented interfaces of this class
     */
    public Set<IJCTClassType> getDirectlyImplementedInterfaces();
    

}
