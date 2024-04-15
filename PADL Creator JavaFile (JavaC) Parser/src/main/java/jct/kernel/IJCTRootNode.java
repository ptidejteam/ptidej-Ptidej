/**
 * @author Mathieu Lemoine
 * @created 2008-12-02
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

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

/**
 * This class represents the root node of the tree
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTRootNode extends IJCTElementContainer<IJCTPackage> {
	/**
	 * Returns the element within the model. There is a difference between
	 * knowing the path and walking the path... Equivalent to {@code
	 * aPath.walk(this);}.
	 */
	public IJCTElement walk(final IJCTPath aPath);

	/**
	 * Returns the Factory associated to this JCT
	 */
	public IJCTFactory getFactory();

	/**
	 * Returns the name of the JCT. This data is purely informative
	 */
	public String getName();

	/**
	 * Returns the intersection of the types
	 * 
	 * @return null iff the type can not be found
	 */
	public <T extends IJCTType> T getType(
		final String path,
		final Class<T> typeClass);

	/**
	 * Returns the intersection of the types
	 * 
	 * @return null iff the type can not be found
	 */
	public IJCTType getType(final IJCTType... types);

	/**
	 * Roughly equivalent to {@code this.registerArrayType(underlyingType,
	 * null);}.
	 */
	public IJCTArrayType registerArrayType(final IJCTType underlyingType);

	/**
	 * Register a new array type, if it does not exists already.
	 * 
	 * @param underlyingTypeName
	 *            the name used to register the array type, if null, {@code
	 *            underlyingType.getTypeName()} will be used.
	 * @return the associated array type.
	 */
	public IJCTArrayType registerArrayType(
		final IJCTType underlyingType,
		final String underlyingTypeName);

	public IJCTPrimitiveType getType(
		final JCTPrimitiveTypes aPrimitiveTypeConstant);

	/**
	 * Returns the textual representation. 0x1D (Group Separator) Unicode
	 * Character is used to separate packages.
	 */
	public String getSourceCode();

	/**
	 * Writes the textual representation in the stream, the separators are not
	 * wrote in the writer.
	 */
	public Writer getSourceCode(final Writer aWriter) throws IOException;

	/**
	 * Returns wether this root node has been initialized or not
	 */
	public boolean isInitialized();

	/**
	 * From now, this root node will assume that is has been initialized
	 */
	public void assumeInitialized();

	/**
	 * Adds a "package" in the set, if it was not already in it
	 */
	public void addPackage(final IJCTPackage aPackage);

	/**
	 * Removes this package from the set
	 */
	public void removePackage(final IJCTPackage aPackage);

	/**
	 * Returns the set of packages of this root node
	 * <em>Part of the enclosed elements.</em>
	 */
	public Set<IJCTPackage> getPackages();

	/**
	 * Adds a "orphan" at the index (or move it there)
	 */
	public void addOrphan(final int anIndex, final IJCTElement orphan);

	/**
	 * Adds a "orphan" at the end of the list (or move it there)
	 */
	public void addOrphan(final IJCTElement orphan);

	/**
	 * Removes this orphan from the list
	 */
	public void removeOrphan(final IJCTElement orphan);

	/**
	 * Remove the orphan at the index
	 */
	public void removeOrphan(final int anIndex);

	/**
	 * Returns the list of orphans of this root node
	 */
	public List<IJCTElement> getOrphans();

}
