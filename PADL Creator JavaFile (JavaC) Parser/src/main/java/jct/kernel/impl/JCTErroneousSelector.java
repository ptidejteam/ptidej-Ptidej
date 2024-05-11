/**
 * @author Mathieu Lemoine
 * @created 2008-10-29 (水)
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
import java.util.Collection;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTErroneousSelector;
import jct.kernel.IJCTExpression;
import jct.kernel.IJCTIdentifiable;
import jct.kernel.IJCTMemberSelector;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSelector;
import jct.kernel.IJCTSimpleSelector;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.reference.NotNullableReference;

/**
 * Interface representing a not yet or lately resolved selector.
 * When the selector is resolved, the interface behaves exactly
 * like the selector it is resolved to.
 * 
 * Default implementation for {@link jct.kernel.IJCTErroneousSelector}
 *
 * @author Mathieu Lemoine
 */
class JCTErroneousSelector extends JCTSourceCodePart implements
		IJCTErroneousSelector {
	/**
	 * identifier of this erroneous selector
	 */
	private final NotNullableReference<IJCTSelector> identifier;

	JCTErroneousSelector(final IJCTRootNode aRootNode, final String anIdentifier) {
		super(aRootNode);
		this.identifier =
			this.createInternalReference(new JCTUnresolvedSimpleSelector(
				aRootNode,
				anIdentifier));
		super.backpatchElements(this.identifier);
	}

	/**
	 * Resolves the selector.
	 *
	 * @throws IllegalStateException iff the selector is already resolved.
	 */
	public void resolveSelector(final IJCTSelector s) {
		if (JCTKind.ERRONEOUS_SELECTOR != this.getIdentifier().getKind())
			throw new IllegalStateException("Selector already resolved");
		this.identifier.set(s);
	}

	/**
	 * Returns the identifier of this erroneous selector
	 * <em>Included in the enclosed elements.</em>
	 */
	public IJCTSelector getIdentifier() {
		return this.identifier.get();
	}

	public IJCTClassMember getElement() {
		final IJCTIdentifiable element = this.getIdentifier().getElement();
		if (null != element && !(element instanceof IJCTClassMember))
			throw new IllegalStateException(
				"Erroneous Simple Selector, please use this.getSelector().getMember() instead.");
		return (IJCTClassMember) element;
	}

	//@SuppressWarnings("unchecked")
	public void setElement(final IJCTIdentifiable e) {
		this.getIdentifier().setElement(e);
	}

	public IJCTExpression getQualifyingExpression() {
		if (JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
			return ((IJCTMemberSelector) this.getIdentifier())
				.getQualifyingExpression();
		else
			throw new UnsupportedOperationException(
				"getQualifyingExpression is implemented only by MEMBER_SELECTOR");
	}

	public void setQualifyingExpression(final IJCTExpression e) {
		if (JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
			((IJCTMemberSelector) this.getIdentifier())
				.setQualifyingExpression(e);
		else
			throw new UnsupportedOperationException(
				"setQualifyingExpression is implemented only by MEMBER_SELECTOR");
	}

	public IJCTSimpleSelector getMemberSelector() {
		if (JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
			return ((IJCTMemberSelector) this.getIdentifier())
				.getMemberSelector();
		else
			throw new UnsupportedOperationException(
				"getMemberSelector is implemented only by MEMBER_SELECTOR");
	}

	//@SuppressWarnings("unchecked")
	public void setMemberSelector(final IJCTSimpleSelector e) {
		if (JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
			((IJCTMemberSelector) this.getIdentifier()).setMemberSelector(e);
		else
			throw new UnsupportedOperationException(
				"setMemberSelector is implemented only by MEMBER_SELECTOR");
	}

	public Writer getSourceCode(final Writer w) throws IOException {
		return this.getIdentifier().getSourceCode(w);
	}

	@Override
	public boolean equals(final Object o) {
		return this.getIdentifier().equals(o);
	}

	@Override
	public int hashCode() {
		return this.getIdentifier().hashCode();
	}

	public JCTKind getKind() {
		return this.getIdentifier().getKind();
	}

	public IJCTType getTypeResult() {
		return this.getIdentifier().getTypeResult();
	}

	@Override
	public String toString() {
		return this.getIdentifier().toString();
	}

	@Override
	//@SuppressWarnings("unchecked")
	public Collection<IJCTExpression> getEnclosedElements() {
		if (JCTKind.MEMBER_SELECTOR == this.getIdentifier().getKind())
			return ((JCTMemberSelector) this.getIdentifier())
				.getEnclosedElements();
		else
			throw new UnsupportedOperationException(
				"getEnclosedElements is implemented only by MEMBER_SELECTOR");
	}

	public <R, P> R accept(final IJCTVisitor<R, P> v, final P p) {
		if (this.getIdentifier() instanceof JCTUnresolvedSimpleSelector)
			return v.visitErroneousSelector(this, p);
		else
			return this.getIdentifier().accept(v, p);
	}

	public void setElement(final IJCTClassMember e) {
		this.setElement((IJCTIdentifiable) e);
	}

	private static final long serialVersionUID = 5562018097630802866L;

}
