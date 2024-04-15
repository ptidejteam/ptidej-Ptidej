/**
 * @author Mathieu Lemoine
 * @created 2008-10-27 (月)
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
import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTIdentifiable;
import jct.kernel.IJCTMemberSelector;
import jct.kernel.IJCTPrimitiveType;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSelector;
import jct.kernel.IJCTSimpleSelector;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVariable;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;

/**
 * Interface representing a selector
 * (possibly qualified identifier which can be totally resolved just by knowing the element referenced)
 * or a simple identifier
 * (if the selector is used for a class member not resolvable by itself and as the second part
 * of a {@link IJCTMemberSelector}).
 * E.g.: a simple selector is an expression like:
 * <ul>
 *  <li>[*.]this.foo (TODO : remove this one)</li>
 *  <li>A reference to a static member of a (raw) class</li>
 *  <li>A reference to a (raw) class or a package</li>
 *  <li>A reference to a local variable</li>
 *  <li>... <em>this list is not exhaustive</em> ...</li>
 * </ul>
 * 
 * Default implementation for {@link jct.kernel.IJCTSimpleSelector}
 *
 * @author Mathieu Lemoine
 */
class JCTSimpleSelector<Identifiable extends IJCTIdentifiable> extends
		JCTSelector<Identifiable, IJCTElement /*Void*/> implements
		IJCTSimpleSelector<Identifiable> {
	/**
	 * element of this simple selector
	 */
	private Identifiable element;

	JCTSimpleSelector(
		final IJCTRootNode aRootNode,
		final Identifiable anIdentifiable) {
		super(aRootNode);
		this.element = anIdentifiable;
	} 

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitSimpleSelector(this, aP);
	}

	public Identifiable getElement() {
		return this.element;
	}

	public void setElement(final Identifiable e) {
		if (e == null)
			throw new NullPointerException("e must not be null");

		this.element = e;
	}

	// TODO : case of static qualified class member
	// TODO : not fully qualified class names (context-dependent)
	// TODO : take care of enclosing classes
	@SuppressWarnings("fallthrough")
	public Writer getSourceCode(final Writer w) throws IOException {
		if (JCTKind.SIMPLE_IDENTIFIER == this.getKind())
			w.append(this.getElement().getName());
		else {
			final Identifiable e = this.getElement();
			switch (e.getKind()) {
				case PACKAGE :
					w.append(e.getName());
					break;
				case PRIMITIVE_TYPE :
					w.append(((IJCTPrimitiveType) e)
						.getType()
						.toString()
						.toLowerCase());
					break;
				case CLASS :
					if (null == ((IJCTClassMember) e).isStatic()) {
						w.append(((IJCTClass) e).getFQN()); // FQN !!
						break;
					}
				case VARIABLE :
				case FIELD :
				case PARAMETER :
					if (null == ((IJCTClassMember) e).isStatic()) {
						w.append(e.getName());
						break;
					}
				case METHOD :
					{
						final IJCTClassMember m = (IJCTClassMember) e;
						final Boolean isStatic = m.isStatic();
						if (null != isStatic && isStatic) // FQN !!
							w
								.append(
									((IJCTClass) m.getEnclosingElement())
										.getFQN())
								.append('.')
								.append(m.getName());
						else
							w
								.append(null == isStatic ? "<NULL>." : "this.")
								.append(m.getName());
					}
					break;
				default :
					throw new AssertionError(
						"the element selected by a selector must be an identifiable element (i.e. either a package, a primitive type or a class member).");
			}
		}

		return w;
	}

	@Override
	public boolean equals(final Object o) {
		if (o instanceof IJCTSelector)
			return this.getElement().equals(((IJCTSelector) o).getElement());
		else
			return false;
	}

	@Override
	public int hashCode() {
		return this.getElement().hashCode();
	}

	public JCTKind getKind() {
		if (null != this.getEnclosingElement()
				&& JCTKind.MEMBER_SELECTOR == this
					.getEnclosingElement()
					.getKind()
				&& this == ((IJCTMemberSelector) this.getEnclosingElement())
					.getMemberSelector())
			if (!(this.getElement() instanceof IJCTClassMember)
					|| null == ((IJCTClassMember) this.getElement())
						.getDirectEnclosingClass())
				throw new AssertionError(
					"This element ("
							+ this.getElement().getPath()
							+ ") can not be selected as the right part of a member selector");
			else
				return JCTKind.SIMPLE_IDENTIFIER;
		else
			return JCTKind.SIMPLE_SELECTOR;
	}

	public IJCTType getTypeResult() {
		if (JCTKind.VARIABLE != this.getElement().getKind())
			throw new UnsupportedOperationException(
				"Can not get the type of an identifiable other than a variable.");

		return ((IJCTVariable) this.getElement()).getType();
	}

	private static final long serialVersionUID = -6216619544675856345L;

}
