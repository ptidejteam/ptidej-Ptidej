/**
 * @author Mathieu Lemoine
 * @created 2009-06-05 (金)
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

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import jct.test.rsc.jct.kernel.IJCTExpression;
import jct.test.rsc.jct.kernel.IJCTField;
import jct.test.rsc.jct.kernel.IJCTParameter;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVariable;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTModifiers;
import jct.test.rsc.jct.util.reference.NullableReference;


/**
 * This class implements a variable, field or parameter
 *
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTVariable},
 * {@link jct.test.rsc.jct.kernel.IJCTField}, {@link jct.test.rsc.jct.kernel.IJCTParameter}
 *
 * <!-- This class is not automatically generated -->
 *
 * @author Mathieu Lemoine
 */
class JCTVariableImpl extends JCTClassMember<IJCTExpression> implements IJCTVariable, IJCTField, IJCTParameter
{
    /**
     * default kind
     */
    private final JCTKind defaultKind;

    /**
     * type of this variable
     */
    private IJCTType type;

    /**
     * initial value of this variable
     */
    private final NullableReference<IJCTExpression> initialValue = this.createNullableInternalReference();


    JCTVariableImpl(final IJCTRootNode aRootNode, final String name, final IJCTType type, final JCTKind defaultKind)
    {
        super(aRootNode, name);
        this.defaultKind = defaultKind;
        this.type = type;
        super.backpatchElements(this.initialValue);
    }

    /**
     * Modifies the type of this variable
     *
     * @param type the new type
     */
    @Override
	public void setType(final IJCTType type)
    {
        this.type = type;
    }

    /**
     * Returns the type of this variable
     */
    @Override
	public IJCTType getType()
    {
        return this.type;
    }

    /**
     * Modifies the initial value of this variable
     *
     * @param initialValue the new initial value, can be {@code null}
     */
    @Override
	public void setInitialValue(final IJCTExpression initialValue)
    {
        this.initialValue.set(initialValue);
    }

    /**
     * Returns the initial value of this variable
     * Included in the enclosed elements
     *
     * @return null iff there is no initial value
     */
    @Override
	public IJCTExpression getInitialValue()
    {
        return this.initialValue.get();
    }

    /**
     * Returns the kind of this constituent (JCTKind.VARIABLE)
     */
    @Override
	public JCTKind getKind()
    {
        if(null == this.getEnclosingElement())
            return this.defaultKind;

        final JCTKind enclosingKind = this.getEnclosingElement().getKind();

        if(JCTKind.CLASS == enclosingKind)
            return JCTKind.FIELD;

        if(JCTKind.METHOD == enclosingKind)
            return JCTKind.PARAMETER;

        return JCTKind.VARIABLE;
    }

    /**
     * Calls the appropriate visit* method on the visitor
     */
    @Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP)
    {
        final JCTKind kind = this.getKind();

        if(JCTKind.FIELD == kind)
            return visitor.visitField(this, aP);

        if(JCTKind.PARAMETER == kind)
            return visitor.visitParameter(this, aP);

        return visitor.visitVariable(this, aP);
    }

    @Override
	public Writer getSourceCode(final Writer w) throws IOException
    {
        for(final JCTModifiers m : this.getModifiers())
            w.append(m.toString().toLowerCase())
                .append(' ');

        if(null != this.getType())
            this.getType().getSourceCode(w)
                .append(' ');

        w.append(this.getName());

        if(null != this.getInitialValue())
            this.getInitialValue().getSourceCode(w.append(" = "));

        return w.append(";\n");
    }

    private static final Map<JCTModifiers, Integer> modifiersIncompatibility = new HashMap<JCTModifiers, Integer>();

    static
    {
        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.FINAL,
                                                 JCTModifiers.VOLATILE.getFlag());

        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.PRIVATE,
                                                 JCTModifiers.PROTECTED.getFlag()
                                                 | JCTModifiers.PUBLIC.getFlag());

        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.PROTECTED,
                                                 JCTModifiers.PRIVATE.getFlag()
                                                 | JCTModifiers.PUBLIC.getFlag());

        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.PUBLIC,
                                                 JCTModifiers.PRIVATE.getFlag()
                                                 | JCTModifiers.PROTECTED.getFlag());

        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.STATIC, 0);

        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.TRANSIENT, 0);

        JCTVariableImpl.modifiersIncompatibility.put(JCTModifiers.VOLATILE,
                                                 JCTModifiers.FINAL.getFlag());
    }

    @Override
    protected boolean hasIncompatibleModifier(final JCTModifiers m)
    {
        final Integer incompatibility = JCTVariableImpl.modifiersIncompatibility.get(m);
        if(null == incompatibility)
            throw new IllegalArgumentException("This modifier (" + m.toString() + " " + m.getFlag() + ") is not supported by variables and fields.");
        return (this.getModifierFlags() & incompatibility) != 0;
    }


    /**
     * Returns a unique ID for this element.
     */
    @Override
    public String getID()
    {
        return this.getName() + super.getID();
    }
}
