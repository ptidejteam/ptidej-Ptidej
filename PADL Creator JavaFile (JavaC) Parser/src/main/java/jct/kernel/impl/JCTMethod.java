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

package jct.kernel.impl;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import jct.kernel.Constants;
import jct.kernel.IJCTBlock;
import jct.kernel.IJCTClassType;
import jct.kernel.IJCTMethod;
import jct.kernel.IJCTParameter;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTStatement;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.kernel.JCTModifiers;
import jct.kernel.JCTPrimitiveTypes;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * This clas represents a method
 * 
 * Default implementation for {@link jct.kernel.IJCTMethod}
 *
 * @author Mathieu Lemoine
 */
class JCTMethod extends JCTClassMember<IJCTStatement> implements IJCTMethod {
	/**
	 * return type of this method
	 */
	private IJCTType returnType;

	/**
	 * List of parameters of this method
	 */
	private final List<IJCTParameter> parameters = this.createInternalList();

	/**
	 * body of this method
	 */
	private final NotNullableReference<IJCTBlock> body;

	/**
	 * Set of thrown exceptions of this method
	 */
	private final Set<IJCTClassType> thrownExceptions =
		new HashSet<IJCTClassType>();

	JCTMethod(final IJCTRootNode aRootNode, final String name) {
		super(aRootNode, name);
		this.returnType = this.getRootNode().getType(JCTPrimitiveTypes.VOID);
		this.body =
			this.createInternalReference(this
				.getRootNode()
				.getFactory()
				.createBlock());
		super.backpatchElements(new IndirectCollection<IJCTStatement>(
			this.parameters,
			this.body));
	}

	/**
	 * Modifies the return type of this method
	 *
	 * @param returnType the new return type
	 */
	public void setReturnType(final IJCTType returnType) {
		this.returnType = returnType;
	}

	/**
	 * Returns the return type of this method
	 */
	public IJCTType getReturnType() {
		return this.returnType;
	}

	/**
	 * Adds a "parameter" at the index (or move it there)
	 */
	public void addParameter(final int anIndex, final IJCTParameter aParameter) {
		this.parameters.add(anIndex, aParameter);
	}

	/**
	 * Adds a "parameter" at the end of the list (or move it there)
	 */
	public void addParameter(final IJCTParameter aParameter) {
		this.parameters.add(aParameter);
	}

	/**
	 * Removes this parameter from the list
	 */
	public void removeParameter(final IJCTParameter aParameter) {
		this.parameters.remove(aParameter);
	}

	/**
	 * Remove the parameter at the index
	 */
	public void removeParameter(final int anIndex) {
		this.parameters.remove(anIndex);
	}

	/**
	 * Returns the list of parameters of this method
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTParameter> getParameters() {
		return Collections.unmodifiableList(this.parameters);
	}

	/**
	 * Modifies the body of this method
	 *
	 * @param body the new body
	 */
	public void setBody(final IJCTBlock body) {
		this.body.set(body);
	}

	/**
	 * Returns the body of this method
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTBlock getBody() {
		return this.body.get();
	}

	/**
	 * Adds a "thrown exception" in the set, if it was not already in it
	 */
	public void addThrownException(final IJCTClassType thrownException) {
		this.thrownExceptions.add(thrownException);
	}

	/**
	 * Removes this thrown exception from the set
	 */
	public void removeThrownException(final IJCTClassType thrownException) {
		this.thrownExceptions.remove(thrownException);
	}

	/**
	 * Returns the set of thrown exceptions of this method
	 */
	public Set<IJCTClassType> getThrownExceptions() {
		return Collections.unmodifiableSet(this.thrownExceptions);
	}

	/**
	 * Returns the kind of this constituent (JCTKind.METHOD)
	 */
	public JCTKind getKind() {
		return JCTKind.METHOD;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitMethod(this, aP);
	}

	@Override
	public JCTPathPartBuilder createPathPart() {
		final StringBuilder str = new StringBuilder(this.getName());

		str.append(Constants.METHOD_MARKER);
		final Iterator<IJCTParameter> it = this.getParameters().iterator();

		while (it.hasNext()) {
			final IJCTParameter p = it.next();
			str.append(null == p.getType() ? null : p.getType().getTypeName());
			if (it.hasNext())
				str.append(Constants.PARAMETER_SEPARATOR);
		}

		return super.createPathPart().setData(str.toString());
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;

		if (!(o instanceof IJCTMethod))
			return false;

		final IJCTMethod m = (IJCTMethod) o;

		if (Constants.INSTANCE_INITIALIZER_NAME.equals(m.getName())
				|| Constants.CLASS_INITIALIZER_NAME.equals(m.getName()))
			return false;

		if (!this.getName().equals(m.getName()))
			return false;

		if (this.getParameters().size() != m.getParameters().size())
			return false;

		final Iterator<IJCTParameter> tit = this.getParameters().iterator();
		final Iterator<IJCTParameter> mit = m.getParameters().iterator();

		while (mit.hasNext()) {
			final IJCTType targtype = tit.next().getType();
			final IJCTType margtype = mit.next().getType();
			if (null == margtype || null == targtype
					|| !margtype.equals(targtype))
				return false;
		}

		return true;
	}

	@Override
	public String getID() {
		final StringBuffer result = new StringBuffer();

		result.append(this.getName()).append('(');

		final Iterator<IJCTParameter> pit = this.getParameters().iterator();
		while (pit.hasNext()) {
			final String type = pit.next().getType().getTypeName();
			result.append(type.substring(1));
			if (pit.hasNext())
				result.append(", ");
		}

		return result.append(')').toString();
	}

	public Writer getSourceCode(final Writer w) throws IOException {
		for (final JCTModifiers m : this.getModifiers())
			w.append(m.toString().toLowerCase()).append(' ');

		this
			.getReturnType()
			.getSourceCode(w)
			.append(' ')
			.append(this.getName())
			.append('(');

		final Iterator<IJCTParameter> pit = this.getParameters().iterator();
		while (pit.hasNext()) {
			final String var = pit.next().getSourceCode();
			w.append(var.substring(0, var.length() - 2));
			if (pit.hasNext())
				w.append(", ");
		}

		w.append(')');

		if (this.getThrownExceptions().size() > 0) {
			w.append(" throws ");
			final SortedSet<IJCTClassType> thrown =
				new TreeSet<IJCTClassType>(new Comparator<IJCTClassType>() {
					public int compare(
						final IJCTClassType o1,
						final IJCTClassType o2) {
						return o1
							.getSelector()
							.getElement()
							.getFQN()
							.compareTo(o2.getSelector().getElement().getFQN());
					}
				});
			thrown.addAll(this.getThrownExceptions());

			final Iterator<IJCTClassType> cit = thrown.iterator();
			while (cit.hasNext()) {
				cit.next().getSourceCode(w);
				if (cit.hasNext())
					w.append(", ");
			}
		}

		if (null != this.getBody())
			this.getBody().getSourceCode(w);
		else
			w.append(";\n");

		return w;
	}

	private static final Map<JCTModifiers, Integer> modifiersIncompatibility =
		new HashMap<JCTModifiers, Integer>();

	static {
		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.ABSTRACT,
			JCTModifiers.PRIVATE.getFlag() | JCTModifiers.FINAL.getFlag()
					| JCTModifiers.STATIC.getFlag()
					| JCTModifiers.NATIVE.getFlag()
					| JCTModifiers.STRICTFP.getFlag()
					| JCTModifiers.SYNCHRONIZED.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.FINAL,
			JCTModifiers.ABSTRACT.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.NATIVE,
			JCTModifiers.STRICTFP.getFlag() | JCTModifiers.ABSTRACT.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.PRIVATE,
			JCTModifiers.PROTECTED.getFlag() | JCTModifiers.PUBLIC.getFlag()
					| JCTModifiers.ABSTRACT.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.PROTECTED,
			JCTModifiers.PRIVATE.getFlag() | JCTModifiers.PUBLIC.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.PUBLIC,
			JCTModifiers.PRIVATE.getFlag() | JCTModifiers.PROTECTED.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.STATIC,
			JCTModifiers.ABSTRACT.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.STRICTFP,
			JCTModifiers.ABSTRACT.getFlag() | JCTModifiers.NATIVE.getFlag());

		JCTMethod.modifiersIncompatibility.put(
			JCTModifiers.SYNCHRONIZED,
			JCTModifiers.ABSTRACT.getFlag());
	};

	@Override
	protected boolean hasIncompatibleModifier(final JCTModifiers m) {
		final Integer incompatibility =
			JCTMethod.modifiersIncompatibility.get(m);
		if (null == incompatibility)
			throw new IllegalArgumentException("This modifier (" + m.toString()
					+ " " + m.getFlag() + ") is not supported by methods.");
		return (this.getModifierFlags() & incompatibility) != 0;
	}

	private static final long serialVersionUID = -8854746396705409982L;

}
