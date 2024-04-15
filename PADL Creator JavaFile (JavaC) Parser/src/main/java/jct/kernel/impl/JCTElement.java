/**
 * @author Mathieu Lemoine
 * @created 2008-08-16 (土)
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
import java.lang.ref.SoftReference;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTVisitor;
import jct.util.AbstractJCTContainer;

/**
 * Basic interface representing an element of a JCT (JavaC bound abstract syntax Tree)
 * 
 * Default implementation for {@link jct.kernel.IJCTElement}
 *
 * @author Mathieu Lemoine
 */
abstract class JCTElement<Component extends IJCTElement> extends
		AbstractJCTContainer<Component> implements IJCTElement,
		java.io.Serializable {
	/**
	 * enclosing element of this element
	 */
	private JCTElementContainer<?> enclosingElement;

	/**
	 * root node of this element
	 */
	private final IJCTRootNode rootNode;

	/**
	 * name of this element
	 */
	private String name = null;

	/**
	 * Non-Identifiable constructor
	 */
	JCTElement(final IJCTRootNode aRootNode) {
		this(aRootNode, null);
	}

	/**
	 * Identifiable constructor
	 */
	JCTElement(final IJCTRootNode aRootNode, final String name) {
		this.rootNode =
			null == aRootNode && this instanceof IJCTRootNode ? (IJCTRootNode) this
					: aRootNode;
		this.name = name;
	}

	/**
	 * Accepts a visitor, using using {@code null} as additional parameter.
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> v) {
		return this.accept(v, null);
	}

	/**
	 * Returns the enclosing element
	 */
	public IJCTElement getEnclosingElement() {
		return this.enclosingElement;
	}

	/**
	 * Returns the RootNode of the model this element is refering to.
	 * Invariant: {@code this.getRootNode() == this.getRootNode().getRootNode()}
	 */
	public IJCTRootNode getRootNode() {
		return this.rootNode;
	}

	/**
	 * Returns a unique ID for this element.
	 */
	public String getID() {
		return super.toString();
	}

	/**
	 * Returns the textual representation of the expression as if it was within the source code
	 */
	public String getSourceCode() {
		try {
			return this.getSourceCode(new StringWriter()).toString();
		}
		catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Unsupported Operation because the interface does not specify it.
	 */
	public Collection<Component> getEnclosedElements() {
		throw new UnsupportedOperationException(
			"It is impossible to extract enclosed elements from a leaf.");
	}

	/**
	 * Modifies the name of this element
	 *
	 * @param name the new name, can be {@code null}
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Returns the name of this element
	 *
	 * @return null iff there is no name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Cached path builder
	 */
	private transient SoftReference<JCTPathPartBuilder> cachedPathPartBuilder =
		new SoftReference<JCTPathPartBuilder>(null);

	void updateEnclosingElement(final JCTElementContainer e) {
		if (null == this.enclosingElement)
			((JCTRootNode) this.getRootNode()).removeOrphan(this);
		else if (null != this.enclosingElement.elements && null != e)
			this.enclosingElement.elements.remove(this);

		if (e == null)
			((JCTRootNode) this.getRootNode()).addOrphan(this);
		else if (!e.getRootNode().equals(this.getRootNode()))
			throw new IllegalArgumentException(
				"An element's Root Node and its enclosing element's Root Node must be equals");

		this.enclosingElement = e;

		this.discardCachedPathPartBuilderIndex();
	}

	/**
	 * Discards the cached path part builder index
	 */
	protected final void discardCachedPathPartBuilderIndex() {
		if (null != this.cachedPathPartBuilder.get())
			this.cachedPathPartBuilder.get().setIndex(null);
	}

	/**
	 * Returns the {@link jct.kernel.IJCTPath Path} to this element.
	 */
	public final JCTPath getPath() {
		if (this instanceof IJCTRootNode) {
			return new JCTPath();
		}
		final JCTPath p = this.seePreviousPathStep().get(0).getPath();
		p.addPart(this.createPathPart().createPathPart());
		return p;
	}

	/**
	 * Returns the {@link jct.kernel.impl.JCTPathPartBuilder} which will be
	 * used to build the path to this element.
	 * Any class overriding this method should also overrides
	 * {@link #isDesignatedBy(String)} and obtains its JCTPathPartBuilder
	 * by calling {@code super.createPathPart()}.
	 */
	protected JCTPathPartBuilder createPathPart() {
		JCTPathPartBuilder p = this.cachedPathPartBuilder.get();

		if (null == p) {
			p = new JCTPathPartBuilder(this.getKind());
			this.cachedPathPartBuilder =
				new SoftReference<JCTPathPartBuilder>(p);
		}

		if (null == p.getIndex()) {
			int i = 0;
			final Iterator<?> it =
				this.seePreviousPathStep().get(0).seeNextPathStep(
					this.getKind()).iterator();
			while (it.hasNext())
				if (this == it.next()) {
					p.setIndex(i);
					break;
				}
				else
					++i;

			if (null == p.getIndex())
				throw new AssertionError(
					"lists: for(List<JCTElementContainer> l : this.seePreviousPathStep())\n"
							+ "{\n"
							+ "    for(IJCTElement e : l.seeNextPathStep(this.getKind))\n"
							+ "        if(this == e)\n"
							+ "            continue lists;\n"
							+ "    return false;\n" + "}\n" + "return true;\n"
							+ "must always return true. \n\n"
							+ "This is not valid for :\n" + "- enclosing 0 : "
							+ this.seePreviousPathStep().get(0).getClass()
							+ "\n" + "- enclosed : " + this.getClass() + "/"
							+ this.getName() + "\n" + "- enclosed Kind : "
							+ this.getKind() + "\n" + "- enclosed Name : "
							+ this.getName());
		}

		return p.setData(this.getName());
	}

	/**
	 * Returns the element desginated by {@code this.getPath().getPathToEnclosingElement()}.
	 * {@code lists: for(List<IJCTElement> l : this.seePreviousPathStep()) { for(IJCTElement e :
	 * l.seeNextPathStep(this.getKind)) if(this == e) continue lists; return false; {@literal }}
	 * return true; }
	 * must always return true.
	 */
	protected List<JCTElementContainer<?>> seePreviousPathStep() {
		return new LinkedList<JCTElementContainer<?>>() {
			private static final long serialVersionUID = -5192118916798989722L;

			{
				this
					.add((JCTElementContainer<?>) (null == JCTElement.this.enclosingElement ? JCTElement.this
						.getRootNode()
							: JCTElement.this.enclosingElement));
			}
		};
	}

	/**
	 * Determines whether the {@link jct.kernel.impl.JCTPathPart Path} designates
	 * this element or not.
	 *
	 * Any class overriding this method should also override
	 * {@link #createPathPart()} and call {@code super.isDesignatedBy}.
	 */
	protected boolean isDesignatedBy(final String designator) {
		return designator == null
				|| designator.equals(this.createPathPart().getData());
	}

	@Override
	public String toString() {
		try {
			return this.getPath().toString();
		}
		catch (final Throwable t) {
			t.printStackTrace();
			return this.getClass() + " : " + this.getName();
		}
	}

	private void readObject(final java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.cachedPathPartBuilder =
			new SoftReference<JCTPathPartBuilder>(null);
	}

	private static final long serialVersionUID = 135324744073668097L;
}
