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

package jct.kernel.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jct.kernel.IJCTClass;
import jct.kernel.IJCTCompilationUnit;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTPackage;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.util.ListOfElements;
import jct.util.collection.IndirectCollection;
import jct.util.reference.NullableReference;

/**
 * This class represents a package
 * 
 * Default implementation for {@link jct.kernel.IJCTPackage}
 *
 * @author Mathieu Lemoine
 */
class JCTPackage extends JCTElementContainer<IJCTCompilationUnit> implements
		IJCTPackage {
	/**
	 * package declaration of this package
	 */
	private final NullableReference<IJCTCompilationUnit> packageDeclaration =
		this.createNullableInternalReference();

	/**
	 * List of compilation units of this package
	 */
	private final List<IJCTCompilationUnit> compilationUnits =
		this.createInternalList();

	/**
	 * is ghost of this package
	 */
	private boolean isGhost;

	JCTPackage(
		final IJCTRootNode aRootNode,
		final String name,
		final boolean isGhost) {
		this(aRootNode, name, null, isGhost);
	}

	JCTPackage(
		final IJCTRootNode aRootNode,
		final String name,
		final IJCTCompilationUnit packageDeclaration,
		final boolean isGhost) {
		super(aRootNode, name);
		super.backpatchElements(new IndirectCollection<IJCTCompilationUnit>(
			this.packageDeclaration,
			this.compilationUnits));
		this.isGhost = isGhost;
		this.packageDeclaration.set(packageDeclaration);
	}

	/**
	 * Returns the package declaration of this package
	 */
	public IJCTCompilationUnit getPackageDeclaration() {
		return this.packageDeclaration.get();
	}

	/**
	 * Modifies the package declaration of this package
	 */
	public void setPackageDeclaration(
		final IJCTCompilationUnit packageDeclaration) {
		if (null != packageDeclaration
				&& !packageDeclaration.isPackageDeclaration())
			throw new IllegalArgumentException(
				"Compilation Unit must be a package declaration");

		this.packageDeclaration.set(packageDeclaration);
	}

	/**
	 * Returns a unique ID for this element.
	 */
	@Override
	public String getID() {
		return this.getName() + super.getID();
	}

	/**
	 * Returns whether the package is annonymous or named
	 */
	public boolean isUnnamed() {
		return null == this.getName();
	}

	/**
	 * Adds a "compilation unit" at the index (or move it there)
	 */
	public void addCompilationUnit(
		final int anIndex,
		final IJCTCompilationUnit aCompilationUnit) {
		this.compilationUnits.add(anIndex, aCompilationUnit);
	}

	/**
	 * Adds a "compilation unit" at the end of the list (or move it there)
	 */
	public void addCompilationUnit(final IJCTCompilationUnit aCompilationUnit) {
		this.compilationUnits.add(aCompilationUnit);
	}

	/**
	 * Removes this compilation unit from the list
	 */
	public void removeCompilationUnit(final IJCTCompilationUnit aCompilationUnit) {
		this.compilationUnits.remove(aCompilationUnit);
	}

	/**
	 * Remove the compilation unit at the index
	 */
	public void removeCompilationUnit(final int anIndex) {
		this.compilationUnits.remove(anIndex);
	}

	/**
	 * Returns the list of compilation units of this package
	 * <em>Part of the enclosed elements.</em>
	 */
	public List<IJCTCompilationUnit> getCompilationUnits() {
		return Collections.unmodifiableList(this.compilationUnits);
	}

	/**
	 * Modifies the is ghost of this package
	 *
	 * @param isGhost the new is ghost
	 */
	public void setIsGhost(final boolean isGhost) {
		this.isGhost = isGhost;
	}

	/**
	 * Returns the is ghost of this package
	 */
	public boolean getIsGhost() {
		return this.isGhost;
	}

	/**
	 * Returns the kind of this constituent (JCTKind.PACKAGE)
	 */
	public JCTKind getKind() {
		return JCTKind.PACKAGE;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitPackage(this, aP);
	}

	@Override
	protected JCTPathPartBuilder createPathPart() {
		final JCTPathPartBuilder p = super.createPathPart();

		if (null == p.getInformativeData()) {
			byte informativeData = 0x00;
			if (this.getIsGhost())
				informativeData |= 0x01;

			p.setInformativeData(new byte[] { informativeData });
		}

		return p;
	}

	@Override
	public String getSourceCode() {
		try {
			final String fileSeparator = new String(Character.toChars(0x1C)); // File Separator

			final StringWriter w = new StringWriter();

			final Iterator<IJCTCompilationUnit> it =
				this.getEnclosedElements().iterator();
			while (it.hasNext()) {
				it.next().getSourceCode(w);
				if (it.hasNext())
					w.append(fileSeparator);
			}

			return w.toString();
		}
		catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Writer getSourceCode(final Writer w) throws IOException {
		for (final IJCTCompilationUnit cu : this.getEnclosedElements())
			if (cu != null)
				cu.getSourceCode(w);
		return w;
	}

	/**
	 * Returns the List of elements potentialy designated by a next path part on this kind.
	 */
	@Override
	protected <T extends IJCTElement> ListOfElements<T> seeNextPathStep(
		final JCTKind aKind) {
		if (JCTKind.CLASS == aKind)
			return new ListOfElements<T>((Collection<T>) this
				.getAllEnclosedElements(JCTKind.CLASS, IJCTClass.class, true));

		final ListOfElements<T> result = new ListOfElements<T>();
		for (final IJCTCompilationUnit e : this.getEnclosedElements())
			if (null != e && aKind == e.getKind())
				result.add((T) e);
		return result;
	}

	private static final long serialVersionUID = -3077264082215917277L;

}
