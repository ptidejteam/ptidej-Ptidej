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

package jct.kernel;

import java.util.List;

/**
 * This class represents a element which is (indirectly) enclosed in a
 * {@link jct.kernel.IJCTCompilationUnit compilation unit}.
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTSourceCodePart extends IJCTElement {
	/**
	 * Returns the offset of the stored source code representation of this
	 * element, within its direct enclosing element's.
	 * 
	 * @return null iff not (indirectly) enclosed in a
	 *         {@link jct.kernel.IJCTCompilationUnit Compilation Unit} which has
	 *         a stored source code.
	 */
	public Integer getStoredSourceCodeOffset(final IJCTSourceCodePart enclosing);

	/**
	 * Returns the enclosing compilation unit of this source code part
	 * 
	 * @return null iff there is no enclosing compilation unit
	 */
	public IJCTCompilationUnit getEnclosingCompilationUnit();

	/**
	 * Modifies the stored source code of this source code part
	 * 
	 * @param storedSourceCode
	 *            the new stored source code, can be {@code null}
	 */
	public void setStoredSourceCode(final String storedSourceCode);

	/**
	 * Returns the stored source code of this source code part
	 * 
	 * @return null iff there is no stored source code
	 */
	public String getStoredSourceCode();

	/**
	 * Modifies the stored source code offset of this source code part
	 * 
	 * @param storedSourceCodeOffset
	 *            the new stored source code offset, can be {@code null}
	 */
	public void setStoredSourceCodeOffset(final Integer storedSourceCodeOffset);

	/**
	 * Returns the stored source code offset of this source code part
	 * 
	 * @return null iff there is no stored source code offset
	 */
	public Integer getStoredSourceCodeOffset();

	/**
	 * Modifies the stored source code length of this source code part
	 * 
	 * @param storedSourceCodeLength
	 *            the new stored source code length, can be {@code null}
	 */
	public void setStoredSourceCodeLength(final Integer storedSourceCodeLength);

	/**
	 * Returns the stored source code length of this source code part
	 * 
	 * @return null iff there is no stored source code length
	 */
	public Integer getStoredSourceCodeLength();

	/**
	 * Adds a "comment" at the index (or move it there)
	 */
	public void addComment(final int anIndex, final IJCTComment aComment);

	/**
	 * Adds a "comment" at the end of the list (or move it there)
	 */
	public void addComment(final IJCTComment aComment);

	/**
	 * Removes this comment from the list
	 */
	public void removeComment(final IJCTComment aComment);

	/**
	 * Remove the comment at the index
	 */
	public void removeComment(final int anIndex);

	/**
	 * Returns the list of comments of this source code part
	 */
	public List<IJCTComment> getComments();

}
