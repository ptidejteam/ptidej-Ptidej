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

package jct.kernel;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

/**
 * This class represents a compilation unit (typically, a compilation unit is a
 * Java source file).<br>
 * Two compilation units are equals iff they are representing the same file.
 * 
 * @author Mathieu Lemoine
 */
public interface IJCTCompilationUnit extends
		IJCTElementContainer<IJCTSourceCodePart>, IJCTSourceCodePart {
	/**
	 * Returns whether the compilation unit is a {@literal package-info.java}
	 * file
	 */
	public boolean isPackageDeclaration();

	/**
	 * Writes the textual representation in the stream, the name and its
	 * separator is not wrote in the writer.
	 */
	public Writer getSourceCode(final Writer aWriter) throws IOException;

	/**
	 * Returns the textual representation. 0x00 (NULL) Unicode Character is used
	 * to separate name and content.
	 */
	public String getSourceCode();

	/**
	 * Modifies the source file of this compilation unit
	 * 
	 * @param sourceFile
	 *            the new source file
	 */
	public void setSourceFile(final File sourceFile);

	/**
	 * Returns the source file of this compilation unit
	 */
	public File getSourceFile();

	/**
	 * Adds a "imported" in the set, if it was not already in it
	 */
	public void addImported(final IJCTImport imported);

	/**
	 * Removes this imported from the set
	 */
	public void removeImported(final IJCTImport imported);

	/**
	 * Returns the set of importeds of this compilation unit
	 * <em>Part of the enclosed elements.</em>
	 */
	public Set<IJCTImport> getImporteds();

	/**
	 * Adds a "clazz" in the set, if it was not already in it
	 */
	public void addClazz(final IJCTClass clazz);

	/**
	 * Removes this clazz from the set
	 */
	public void removeClazz(final IJCTClass clazz);

	/**
	 * Returns the set of clazzs of this compilation unit
	 * <em>Part of the enclosed elements.</em>
	 */
	public Set<IJCTClass> getClazzs();

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
	 * Returns the list of comments of this compilation unit
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTComment> getComments();

}
