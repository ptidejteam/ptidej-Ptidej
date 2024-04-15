/**
 * @author Mathieu Lemoine
 * @created 2009-07-10 (金)
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

package jct.tools;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTComment;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTSourceCodePart;
import jct.util.IJCTContainer;

/**
 * This class attach the comments within each
 * compilation unit to the constituent following them directly.
 */
public class JCTCommentAttachor extends JCTMap<Void, Void> {
	private final Map<IJCTSourceCodePart, Integer> cache = new HashMap<IJCTSourceCodePart, Integer>();

	private int getAbsoluteOffset(final IJCTSourceCodePart scp,
			final IJCTCompilationUnit cu) {
		Integer offset = this.cache.get(scp);
		if (scp.getEnclosingCompilationUnit() != cu)
			throw new IllegalArgumentException(
					"scp (" + scp + ") must be enclosed in " + cu);
		if (null == offset)
			this.cache.put(scp, offset = scp.getStoredSourceCodeOffset(cu));
		if (null == offset)
			throw new IllegalArgumentException(
					"scp (" + scp + ") must be enclosed in " + cu
							+ " and contains stored source code data");

		return offset;
	}

	@Override
	public Void visitCompilationUnit(final IJCTCompilationUnit t,
			final Void p) {
		this.cache.clear();

		if (null == t.getStoredSourceCode())
			return null;

		final SortedSet<IJCTSourceCodePart> parts = new TreeSet<IJCTSourceCodePart>(
				new ConstituentOffsetOrder(t, this.cache));

		parts.addAll(t.getImporteds());
		for (IJCTClass c : t.getClazzs()) {
			parts.add(c);
			for (IJCTClassMember cm : c.getDeclaredMembers())
				parts.addAll(
						(Collection<IJCTSourceCodePart>) ((IJCTContainer<? extends IJCTSourceCodePart>) cm)
								.getAllEnclosedElements());
		}

		final SortedSet<IJCTComment> comments = new TreeSet<IJCTComment>(
				new ConstituentOffsetOrder(t, this.cache));

		comments.addAll(t.getComments());

		final Iterator<IJCTSourceCodePart> it = parts.iterator();
		if (!it.hasNext())
			return null;

		IJCTSourceCodePart last = it.next();

		comments: for (final IJCTComment c : comments) {
			while (true) {
				if (this.getAbsoluteOffset(last, t) >= this.getAbsoluteOffset(c,
						t) + c.getStoredSourceCodeLength()) {
					last.addComment(c);
					continue comments;
				}
				else if (it.hasNext()) {
					last = it.next();
					continue;
				}
				else
					return null;
			}
		}

		return null;
	}
}

class ConstituentOffsetOrder implements Comparator<IJCTSourceCodePart> {
	private final Map<IJCTSourceCodePart, Integer> cache;

	private final IJCTCompilationUnit compilationUnit;

	public ConstituentOffsetOrder(final IJCTCompilationUnit cu,
			final Map<IJCTSourceCodePart, Integer> cache) {
		this.compilationUnit = cu;
		this.cache = cache;
	}

	public ConstituentOffsetOrder(final IJCTCompilationUnit cu) {
		this(cu, new HashMap<IJCTSourceCodePart, Integer>());
	}

	private int getAbsoluteOffset(final IJCTSourceCodePart scp) {
		Integer offset = this.cache.get(scp);
		if (scp.getEnclosingCompilationUnit() != this.compilationUnit)
			throw new IllegalArgumentException("scp (" + scp
					+ ") must be enclosed in " + this.compilationUnit);
		if (null == offset)
			this.cache.put(scp, offset = scp
					.getStoredSourceCodeOffset(this.compilationUnit));
		if (null == offset)
			throw new IllegalArgumentException(
					"scp (" + scp + ") must contain stored source code data");
		return offset;
	}

	public int compare(final IJCTSourceCodePart a, final IJCTSourceCodePart b) {
		if (a.equals(b))
			return 0;

		final int ia = this.getAbsoluteOffset(a);
		final int ib = this.getAbsoluteOffset(b);

		if (ia != ib)
			return ia - ib;

		if (a.getStoredSourceCodeLength().intValue() != b
				.getStoredSourceCodeLength().intValue())
			return b.getStoredSourceCodeLength()
					- a.getStoredSourceCodeLength();

		return b.getPath().isEnclosing(a.getPath()) ? 1 : -1;
	}
}
