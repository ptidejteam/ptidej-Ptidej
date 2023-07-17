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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import jct.kernel.Constants;
import jct.kernel.IJCTIntersectionType;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;

/**
 * This class represents an intersection of types
 * 
 * Default implementation for {@link jct.kernel.IJCTIntersectionType}
 *
 * @author Mathieu Lemoine
 */
class JCTIntersectionType extends JCTType implements IJCTIntersectionType {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7245761818274745143L;
	/**
	 * Set of types of this intersection type
	 */
	private final Set<IJCTType> types;

	JCTIntersectionType(final IJCTRootNode aRootNode, final IJCTType... types) {
		super(aRootNode);
		if (types.length <= 1)
			throw new IllegalArgumentException(
				"To create the intersection of 0 types, use JCTPrimitiveTypes.VOID, and to create the intersection type of a sole type, use the type itself.");
		this.types = new HashSet<IJCTType>();

		final List<IJCTType> lTypes = Arrays.asList(types);
		final ListIterator<IJCTType> it = lTypes.listIterator();
		while (it.hasNext()) {
			final IJCTType type = it.next();
			if (type instanceof IJCTIntersectionType) {
				it.remove();
				for (final IJCTType t : ((IJCTIntersectionType) type)
					.getTypes()) {
					it.add(t);
					it.previous();
				}
			}
		}
	}

	/**
	 * Returns the set of types composing this intersection types
	 */
	public Set<IJCTType> getTypes() {
		return Collections.unmodifiableSet(this.types);
	}

	public Writer getSourceCode(final Writer aWriter) throws IOException {
		throw new IllegalStateException(
			"An Intersection Type should never appear in source code.");
	}

	public String getTypeName() {
		final StringBuilder typeName =
			new StringBuilder(Constants.INTERSECTION_MARKER);
		for (final IJCTType type : this.getTypes())
			typeName.append(type.getTypeName()).append(
				Constants.INTERSECTION_SEPARATOR);

		return typeName.toString();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.INTERSECTION_TYPE)
	 */
	public JCTKind getKind() {
		return JCTKind.INTERSECTION_TYPE;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitIntersectionType(this, aP);
	}

}
