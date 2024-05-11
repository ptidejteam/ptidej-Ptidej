/**
 * @author Mathieu Lemoine
 * @created 2008-10-06 (月)
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
import jct.kernel.IJCTClassType;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTMemberSelector;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSelector;
import jct.kernel.IJCTSimpleSelector;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NotNullableReference;

/**
 * Interface representing a member select expression ({@code selector.member})
 * used <strong>only</strong> (i.e. the methods <strong>must</strong>
 * throw an UnsupportedOperationException or an IllegalArgumentException otherwise)
 * when a {@link IJCTSimpleSelector} is not usable
 * (i.e. when a qualifying expression is required to resolve the element).
 * 
 * Default implementation for {@link jct.kernel.IJCTMemberSelector}
 *
 * @author Mathieu Lemoine
 */
class JCTMemberSelector<Member extends IJCTClassMember> extends
		JCTSelector<Member, IJCTExpression> implements
		IJCTMemberSelector<Member> {
	/**
	 * qualifying expression of this member selector
	 */
	private final NotNullableReference<IJCTExpression> qualifyingExpression;

	/**
	 * member selector of this member selector
	 */
	private final NotNullableReference<IJCTSimpleSelector<Member>> memberSelector;

	/**
	 * TODO : implements verification that there is no misuse of JCTMemberSelector as JCTSimpleSelector
	 */
	JCTMemberSelector(
		final IJCTRootNode aRootNode,
		final IJCTExpression qualifyingExpression,
		final Member member) {
		super(aRootNode);
		this.qualifyingExpression =
			this.createInternalReference(qualifyingExpression);
		this.memberSelector =
			this.createInternalReference(this
				.getRootNode()
				.getFactory()
				.createSimpleSelector(member));
		super.backpatchElements(new IndirectCollection<IJCTExpression>(
			this.qualifyingExpression,
			this.memberSelector));
	}

	/**
	 * Returns the kind of this constituent (JCTKind.MEMBER_SELECTOR)
	 */
	public JCTKind getKind() {
		return JCTKind.MEMBER_SELECTOR;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitMemberSelector(this, aP);
	}

	@SuppressWarnings("unchecked")
	private void check() {
		if (JCTKind.CLASS != this.getElement().getEnclosingElement().getKind())
			throw new IllegalStateException(
				"the element must be a class member.");

		IJCTClassType c;

		if ((JCTKind.MEMBER_SELECTOR == this
			.getQualifyingExpression()
			.getKind() || JCTKind.SIMPLE_SELECTOR == this
			.getQualifyingExpression()
			.getKind())
				&& JCTKind.CLASS == ((IJCTSelector) this
					.getQualifyingExpression()).getElement().getKind())
			c =
				((IJCTSelector<IJCTClass>) this.getQualifyingExpression())
					.getElement()
					.createClassType();
		else {
			final IJCTType t = this.getQualifyingExpression().getTypeResult();
			if (JCTKind.CLASS_TYPE == t.getKind())
				c = (IJCTClassType) t;
			else
				//if(JCTKind.ARRAY_TYPE == t.getKind())
				c = null; //((IJCTArrayType)t).getArrayTypeClass().createClassType();
		}

		if (!this.getElement().isMemberOf(c))
			throw new IllegalStateException(
				"the element must be a class member of the type specified by the qualifying expression.");
	}

	public Member getElement() {
		return this.memberSelector.get().getElement();
	}

	// todo : implements verification that there is no misuse of JCTMemberSelector as JCTSimpleSelector
	public void setElement(final Member e) {
		final Member old = this.getElement();
		this.memberSelector.get().setElement(e);
		try {
			this.check();
		}
		catch (final IllegalStateException ex) {
			this.memberSelector.get().setElement(old);
			throw new IllegalArgumentException(ex);
		}
	}

	public IJCTExpression getQualifyingExpression() {
		return this.qualifyingExpression.get();
	}

	public void setQualifyingExpression(final IJCTExpression e) {
		final IJCTExpression old = this.getQualifyingExpression();
		this.qualifyingExpression.set(e);
		try {
			this.check();
		}
		catch (final IllegalStateException ex) {
			this.qualifyingExpression.set(old);
			throw new IllegalArgumentException(ex);
		}
	}

	public IJCTSimpleSelector<Member> getMemberSelector() {
		return this.memberSelector.get();
	}

	public void setMemberSelector(final IJCTSimpleSelector<Member> e) {
		final IJCTSimpleSelector<Member> old = this.getMemberSelector();
		this.memberSelector.set(e);
		try {
			this.check();
		}
		catch (final IllegalStateException ex) {
			this.memberSelector.set(old);
			throw new IllegalArgumentException(ex);
		}
	}

	public Writer getSourceCode(final Writer w) throws IOException {
		return this
			.getQualifyingExpression()
			.getSourceCode(w)
			.append('.')
			.append(this.getElement().getName());
	}

	public IJCTType getTypeResult() {
		return this.getMemberSelector().getTypeResult();
	}

	@Override
	public String toString() {
		return this.getSourceCode();
	}

	private static final long serialVersionUID = 6368668412833343974L;

}
