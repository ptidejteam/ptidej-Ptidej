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


import java.io.Writer;
import java.io.IOException;


import java.util.Set;
import java.util.HashSet;


import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTClassMember;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTMethod;
import jct.test.rsc.jct.kernel.IJCTNonPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.reference.NotNullableReference;


/**
 * This class represents an Array Type
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTArrayType}
 *
 * @author Mathieu Lemoine
 */
class JCTArrayType extends JCTNonPrimitiveType<IJCTClassMember> implements IJCTArrayType
{
    /**
     * underlying type of this array type
     */
    private IJCTType underlyingType;
    
    /**
     * clone method of this array type
     */
    private final NotNullableReference<IJCTMethod> cloneMethod;
    
    /**
     * length field of this array type
     */
    private final NotNullableReference<IJCTField> lengthField;
    
    /**
     * underlying type name of this array type
     */
    private String underlyingTypeName = null;
    
    
    JCTArrayType(final IJCTRootNode aRootNode, final IJCTType underlyingType, final String registeredUnderlyingTypeName)
    {
        super(aRootNode);
        this.underlyingTypeName = registeredUnderlyingTypeName;
        this.underlyingType = underlyingType;
        this.lengthField = this.createInternalReference(JCTArrayType.createLength(this));
        this.cloneMethod = this.createInternalReference(JCTArrayType.createClone(this));
        super.backpatchElements(new IndirectCollection<IJCTClassMember>(this.lengthField, this.cloneMethod));
    }
    
    JCTArrayType(final IJCTRootNode aRootNode, final IJCTType underlyingType)
    {
        this(aRootNode, underlyingType, null);
    }
    
    @Override
	public String getTypeName()
    {
        return Constants.ARRAY_MARKER + this.getUnderlyingTypeName();
    }
    
    @Override
	public Writer getSourceCode(final Writer aWriter) throws IOException
    {
        return this.getUnderlyingType().getSourceCode(aWriter).append("[]");
    }
    
    @Override
	public Set<IJCTNonPrimitiveType> getDirectSuperClasses()
    {
        final Set<IJCTNonPrimitiveType> result = new HashSet<IJCTNonPrimitiveType>();
        final IJCTClassType object = this.getRootNode().getType(Constants.CLASSPATH_OBJECT, IJCTClassType.class);
        if(null != object.getSelector().getElement())
            result.add(object);
        return result;
    }
    
    /**
     * Modifies the underlying type, set also the registered type name.
     */
    @Override
	public void setUnderlyingType(final IJCTType underlyingType, final String registeredUnderlyingTypeName)
    {
        this.setUnderlyingType(underlyingType);
        this.underlyingTypeName = registeredUnderlyingTypeName;
    }
    
    /**
     * Returns the (registered) underlying type's name.
     */
    @Override
	public String getUnderlyingTypeName()
    {
        return null == this.underlyingTypeName
               ? this.underlyingType.getTypeName()
               : this.underlyingTypeName;
    }
    
    /**
     * Clears the registered underlying type name.
     * Post-condition: {@code this.getUnderlyingTypeName().equals(this.getUnderlyingType().getTypeName())} returns true;
     */
    @Override
	public void clearRegisteredTypeName()
    {
        this.underlyingTypeName = null;
    }
    
    /**
     * Modifies the underlying type of this array type
     *
     * @param underlyingType the new underlying type
     */
    @Override
	public void setUnderlyingType(final IJCTType underlyingType)
    {
        this.underlyingType = underlyingType;
    }
    
    /**
     * Returns the underlying type of this array type
     */
    @Override
	public IJCTType getUnderlyingType()
    {
        return this.underlyingType;
    }
    
    /**
     * Returns the clone method of this array type
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTMethod getCloneMethod()
    {
        return this.cloneMethod.get();
    }
    
    /**
     * Returns the length field of this array type
     * <em>Included in the enclosed elements.</em>
     */
    @Override
	public IJCTField getLengthField()
    {
        return this.lengthField.get();
    }
    
    /**
     * Returns the kind of this constituent (JCTKind.ARRAY_TYPE)
     */
    @Override
	public JCTKind getKind()
    {
        return JCTKind.ARRAY_TYPE;
    }
    
    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        return visitor.visitArrayType(this, aP);
    }
    
    private static IJCTField createLength(final JCTArrayType anArrayType)
    {
        final IJCTFactory f = anArrayType.getRootNode().getFactory();
    
        final IJCTField vLength = f.createField(Constants.LENGTH_NAME, anArrayType);
        vLength.addModifier(JCTModifiers.PUBLIC);
        vLength.addModifier(JCTModifiers.FINAL);
        ((JCTElement)vLength).updateEnclosingElement(anArrayType);
        return vLength;
    }
    
    private static IJCTMethod createClone(final JCTArrayType anArrayType)
    {
        final IJCTFactory f = anArrayType.getRootNode().getFactory();
    
        final IJCTMethod mClone = f.createMethod(Constants.CLONE_NAME);
        mClone.setReturnType(anArrayType);
        mClone.addModifier(JCTModifiers.PUBLIC);
        mClone.addModifier(JCTModifiers.FINAL);
        mClone.addModifier(JCTModifiers.NATIVE);
        ((JCTElement)mClone).updateEnclosingElement(anArrayType);
        return mClone;
    }
    
    @Override
    public boolean equals(final Object o)
    {
        if(!(o instanceof IJCTArrayType))
            return false;
    
        final IJCTArrayType at = (IJCTArrayType)o;
    
        if(null != this.getUnderlyingType()
           && null != at.getUnderlyingType())
            return this.getUnderlyingType().equals(at.getUnderlyingType());
        else
        {
            final String underlyingTypeName = this.getUnderlyingTypeName();
            return null == underlyingTypeName
                ? null == at.getUnderlyingTypeName()
                : underlyingTypeName.equals(at.getUnderlyingTypeName());
        }
    }

}
