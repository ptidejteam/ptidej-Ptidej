/**
 * @author Mathieu Lemoine
 * @created 2009-03-09 (月)
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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import jct.kernel.IJCTComment;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTSourceCodePart;
import jct.kernel.JCTKind;

/**
 * This class represents a element which is (indirectly) enclosed in
 * a {@link jct.kernel.IJCTCompilationUnit compilation unit}.
 * 
 * Default implementation for {@link jct.kernel.IJCTSourceCodePart}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTSourceCodePart<Element extends IJCTElement> extends
		JCTElementContainer<Element> implements IJCTSourceCodePart {
	private static final long serialVersionUID = -5351711300068082445L;

	/**
	 * List of comments of this source code part
	 */
	private final List<IJCTComment> comments = new LinkedList<IJCTComment>();

	/**
	 * stored source code length of this source code part
	 */
	private Integer storedSourceCodeLength = null;

	/**
	 * stored source code offset of this source code part
	 */
	private Integer storedSourceCodeOffset = null;

	/**
	 * Non-Identifiable constructor with backpatch enclosed elements
	 */
	JCTSourceCodePart(final IJCTRootNode aRootNode) {
		super(aRootNode);
	}

	/**
	 * Non-Identifiable constructor
	 */
	JCTSourceCodePart(
		final IJCTRootNode aRootNode,
		final Collection<Element> elements) {
		super(aRootNode, elements);
	}

	/**
	 * Identifiable contructor with backpatch enclosed elements
	 */
	JCTSourceCodePart(final IJCTRootNode aRootNode, final String name) {
		super(aRootNode, name);
	}

	/**
	 * Identifiable contructor
	 */
	JCTSourceCodePart(
		final IJCTRootNode aRootNode,
		final String name,
		final Collection<Element> elements) {
		super(aRootNode, name, elements);
	}

	/**
	 * Adds a "comment" at the end of the list (or move it there)
	 */
	public void addComment(final IJCTComment aComment) {
		this.comments.add(aComment);
	}

	/**
	 * Adds a "comment" at the index (or move it there)
	 */
	public void addComment(final int anIndex, final IJCTComment aComment) {
		this.comments.add(anIndex, aComment);
	}

	/**
	 * Returns the list of comments of this source code part
	 */
	public List<IJCTComment> getComments() {
		return Collections.unmodifiableList(this.comments);
	}

	public IJCTCompilationUnit getEnclosingCompilationUnit() {
		IJCTElement e = this;

		while (null != e && JCTKind.COMPILATION_UNIT != e.getKind())
			e = e.getEnclosingElement();

		return (IJCTCompilationUnit) e;
	}

	/**
	 * Returns the stored code source representation of this element.
	 *
	 * @return null iff not (indirectly) enclosed in a {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
	 */
	public String getStoredSourceCode() {
		if (null == this.getEnclosingCompilationUnit()
				|| null == this.storedSourceCodeLength)
			return null;

		final String cuRepr =
			this.getEnclosingCompilationUnit().getStoredSourceCode();
		if (null == cuRepr)
			return null;

		final int absoluteOffset =
			this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit());

		return cuRepr.substring(absoluteOffset, absoluteOffset
				+ this.storedSourceCodeLength);
	}

	/**
	 * Returns the length of the stored code source representation of this element.
	 *
	 * @return null iff not (indirectly) enclosed in a {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
	 */
	public Integer getStoredSourceCodeLength() {
		return this.getEnclosingCompilationUnit() == null
				|| this.getEnclosingCompilationUnit().getStoredSourceCode() == null ? null
				: (null == this.storedSourceCodeLength ? this
					.getSourceCode()
					.length() : this.storedSourceCodeLength);
	}

	/**
	 * Returns the offset of the stored code source representation of
	 * this element, within its direct enclosing element's.
	 *
	 * @return null iff not (indirectly) enclosed in a {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has a stored source code.
	 */
	public Integer getStoredSourceCodeOffset() {
		return this.getEnclosingCompilationUnit() == null
				|| this.getEnclosingCompilationUnit().getStoredSourceCode() == null ? null
				: this.getStoredSourceCodeOffset((IJCTSourceCodePart) this
					.getEnclosingElement());
	}

	public Integer getStoredSourceCodeOffset(
		final IJCTSourceCodePart enclosingElement) {
		if (null == this.getEnclosingCompilationUnit()
				|| null == this.storedSourceCodeOffset)
			return null;

		try {
			if (enclosingElement == this.getEnclosingElement()
					|| null == enclosingElement)
				return this.storedSourceCodeOffset;
			else
				return this.storedSourceCodeOffset
						+ ((IJCTSourceCodePart) this.getEnclosingElement())
							.getStoredSourceCodeOffset(enclosingElement);
		}
		catch (final ClassCastException e) {
			throw new IllegalArgumentException(
				"enclosingElement must be an Enclosing Element of this",
				e);
		}
		catch (final NullPointerException e) {
			return null;
		}
	}

	/**
	 * Removes this comment from the list
	 */
	public void removeComment(final IJCTComment aComment) {
		this.comments.remove(aComment);
	}

	/**
	 * Remove the comment at the index
	 */
	public void removeComment(final int anIndex) {
		this.comments.remove(anIndex);
	}

	public void setStoredSourceCode(final String storedSourceCode) {
		final String actualStoredSourceCode =
			null == storedSourceCode ? "" : storedSourceCode;

		((JCTCompilationUnit) this.getEnclosingCompilationUnit())
			.updateStoredSourceCode(this, actualStoredSourceCode);
		this.storedSourceCodeLength = actualStoredSourceCode.length();
	}

	public void setStoredSourceCodeLength(final Integer length) {
		this.storedSourceCodeLength = length;
	}

	public void setStoredSourceCodeOffset(final Integer offset) {
		this.storedSourceCodeOffset = offset;
	}

	protected void updateStoredSourceCode(
		final JCTSourceCodePart part,
		final int absoluteOffset,
		final int delta) {
		if (this.getStoredSourceCodeOffset(this.getEnclosingCompilationUnit()) > absoluteOffset)
			this.setStoredSourceCodeOffset(this.getStoredSourceCodeOffset()
					+ delta);
		else if (this.getStoredSourceCodeOffset(this
			.getEnclosingCompilationUnit())
				+ this.getStoredSourceCodeLength() < absoluteOffset)
			return; // throw new IllegalArgumentException("updateStoredSourceCode should be called only on elements whose end is after absoluteOffset");
		else if (this != part)
			for (final IJCTElement enclosingPart : this.getEnclosedElements()) {
				if (!(enclosingPart instanceof JCTSourceCodePart)
						|| null == ((JCTSourceCodePart) enclosingPart)
							.getStoredSourceCodeOffset())
					continue;
				final JCTSourceCodePart scp = (JCTSourceCodePart) enclosingPart;
				if (scp.getStoredSourceCodeOffset(this
					.getEnclosingCompilationUnit())
						+ scp.getStoredSourceCodeLength() >= absoluteOffset)
					scp.updateStoredSourceCode(part, absoluteOffset, delta);
			}
	}

}
