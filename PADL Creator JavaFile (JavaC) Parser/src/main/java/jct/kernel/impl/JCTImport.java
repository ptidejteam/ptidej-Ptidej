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
import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassMember;
import jct.kernel.IJCTImport;
import jct.kernel.IJCTImportable;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;

/**
 * This class represents an import
 * Imports respect Java restrictions:
 * <ul>
 * <li>if an import is on demand and static, the imported element must be a top level class.</li>
 * <li>if an import is on demand but not static, the imported element must be a package</li>
 * <li>if an import is static but not on demand, the imported element must be a static class member</li>
 * <li>if an import is neither static nor on demand, the imported element must be a class</li>
 * </ul>
 * 
 * Default implementation for {@link jct.kernel.IJCTImport}
 *
 * @author Mathieu Lemoine
 */
class JCTImport extends JCTSourceCodePart implements IJCTImport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4806501398721585405L;

	/**
	 * imported element of this import
	 */
	private IJCTImportable importedElement;

	/**
	 * is static of this import
	 */
	private boolean isStatic;

	/**
	 * is on demand of this import
	 */
	private boolean isOnDemand;

	JCTImport(
		final IJCTRootNode aRootNode,
		final IJCTImportable importedElement,
		final boolean isStatic,
		final boolean isOnDemand) {
		super(aRootNode);
		this.importedElement = importedElement;
		this.isStatic = isStatic;
		this.isOnDemand = isOnDemand;
		this.check();
	}

	/**
	 * Returns the kind of this constituent (JCTKind.IMPORT)
	 */
	public JCTKind getKind() {
		return JCTKind.IMPORT;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitImport(this, aP);
	}

	/**
	 * Checks import validity
	 */
	protected void check() throws IllegalStateException {
		if (this.isOnDemand && this.isStatic // if on demand and static
				&& (JCTKind.CLASS != this.importedElement.getKind() // and not top level class
				|| null != ((IJCTClass) this.importedElement)
					.getDirectEnclosingClass()))
			throw new IllegalStateException(
				"On demand, static import must be on class");

		if (this.isOnDemand && !this.isStatic // if on demand and not static
				&& JCTKind.PACKAGE != this.importedElement.getKind()) // and not package
			throw new IllegalStateException(
				"On demand, non static import must be on package");

		if (!this.isOnDemand
				&& this.isStatic // if not on demand and static and ...
				&& (JCTKind.PACKAGE == this.importedElement.getKind() // ... package ...
						|| (JCTKind.CLASS == this.importedElement.getKind() // ... or top level class ...
						&& null == ((IJCTClass) this.importedElement)
							.getDirectEnclosingClass()) || !((IJCTClassMember) this.importedElement)
					.isStatic())) // ... or non static class member
			throw new IllegalStateException(
				"Static import must be on static class members");

		if (!this.isOnDemand && !this.isStatic // if not on demand and not static
				&& JCTKind.CLASS != this.importedElement.getKind()) // and not class
			throw new IllegalStateException(
				"Non static import must be on class");
	}

	/**
	 * Returns whether the import is qualified as "static" or not
	 */
	public boolean getIsStatic() {
		return this.isStatic;
	}

	/**
	 * Modifies the staticity qualification of the import
	 */
	public void setIsStatic(final boolean s) {
		if (s == this.isStatic)
			return;

		final boolean save = this.isStatic;
		this.isStatic = s;
		try {
			this.check();
		}
		catch (final IllegalStateException e) {
			this.isStatic = save;
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Returns whether the import is "on demand" (the last character is a *)
	 */
	public boolean getIsOnDemand() {
		return this.isOnDemand;
	}

	/**
	 * Modifies the "on demand" qualification of the import
	 */
	public void setIsOnDemand(final boolean od) {
		if (od == this.isOnDemand)
			return;

		final boolean save = this.isOnDemand;
		this.isOnDemand = od;
		try {
			this.check();
		}
		catch (final IllegalStateException e) {
			this.isOnDemand = save;
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * Returns the element imported
	 */
	public IJCTImportable getImportedElement() {
		return this.importedElement;
	}

	/**
	 * Modifies the imported element
	 */
	public void setImportedElement(final IJCTImportable i) {
		final IJCTImportable save = this.importedElement;
		this.importedElement = i;
		try {
			this.check();
		}
		catch (final IllegalStateException e) {
			this.importedElement = save;
			throw new IllegalArgumentException(e);
		}
	}

	public Writer getSourceCode(final Writer w) throws IOException {
		w.append("import ");

		if (this.getIsStatic())
			w.append("static ");

		switch (this.getImportedElement().getKind()) {
			case PACKAGE :
				w.append(this.getImportedElement().getName());
				break;
			case CLASS :
				w.append(((IJCTClass) this.getImportedElement()).getFQN());
				break;
			default :
				{
					final IJCTClass c =
						(IJCTClass) this
							.getImportedElement()
							.getEnclosingElement();
					w.append(c.getFQN() + "."
							+ this.getImportedElement().getName());
				}
		}

		if (this.getIsOnDemand())
			w.append(".*");

		return w.append(";\n");
	}

	@Override
	public JCTPathPartBuilder createPathPart() {
		final StringBuilder str = new StringBuilder();

		str.append(this.getIsStatic() ? '1' : '0');
		str.append(this.getIsOnDemand() ? '1' : '0');
		switch (this.getImportedElement().getKind()) {
			case PACKAGE :
				str.append(this.getImportedElement().getName());
				break;
			case CLASS :
				str.append(((IJCTClass) this.getImportedElement()).getFQN());
				break;
			default : //Class Member
				{
					final IJCTClass c =
						(IJCTClass) this
							.getImportedElement()
							.getEnclosingElement();
					str.append(c.getFQN()).append('.').append(
						this.getImportedElement().getName());
				}
				break;
		}

		return super.createPathPart().setData(str.toString());
	}

	@Override
	public boolean equals(final Object o) {
		return this.toString().equals(o.toString());
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		return this.getSourceCode();
	}

}
