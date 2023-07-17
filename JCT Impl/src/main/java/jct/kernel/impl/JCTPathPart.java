/**
 * @author Mathieu Lemoine
 * @created 2008-10-19 (日)
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTElementContainer;
import jct.kernel.IJCTPackage;
import jct.kernel.IJCTPathPart;
import jct.kernel.IJCTRootNode;
import jct.kernel.JCTKind;
import jct.util.IJCTContainer;

/**
 * Represents a Path Part
 * 
 * Default implementation for {@link jct.kernel.IJCTPathPart}
 *
 * @author Mathieu Lemoine
 */
public class JCTPathPart implements IJCTPathPart {
	/**
	 * result kind of this path part
	 */
	private final JCTKind resultKind;

	/**
	 * data of this path part
	 */
	private final String data;

	/**
	 * index of this path part
	 */
	private final Integer index;

	/**
	 * informative data of this path part
	 */
	private final byte[] informativeData;

	/**
	 * next part of this path part
	 */
	private JCTPathPart nextPart = null;

	/**
	 * last part of this path part
	 */
	private JCTPathPart lastPart = null;

	/**
	 * Returns the result kind of this path part
	 */
	public JCTKind getResultKind() {
		return this.resultKind;
	}

	/**
	 * Returns the data of this path part
	 */
	public String getData() {
		return this.data;
	}

	/**
	 * Returns the index of this path part
	 */
	public Integer getIndex() {
		return this.index;
	}

	/**
	 * Returns the informative data of this path part
	 */
	public byte[] getInformativeData() {
		return this.informativeData;
	}

	protected JCTPathPart(final JCTKind resultKind) {
		this(resultKind, null);
	}

	public JCTPathPart(final JCTKind resultKind, final Integer index) {
		this(resultKind, index, null);
	}

	public JCTPathPart(
		final JCTKind resultKind,
		final Integer index,
		final String data) {
		this(resultKind, index, data, null);
	}

	public JCTPathPart(
		final JCTKind resultKind,
		final Integer index,
		final String data,
		final byte[] informativeData) {
		this.resultKind = resultKind;
		this.index = index;
		this.data = "null".equals(data) ? null : data;
		this.informativeData = informativeData;
	}

	/**
	 * Returns the nextPart Part of the Path
	 */
	public JCTPathPart getNextPart() {
		return this.nextPart;
	}

	public JCTPathPart getLastPart() {
		if (null == this.lastPart)
			if (null != this.nextPart)
				return this.lastPart = this.nextPart.getLastPart();
			else
				return this.lastPart = this;

		if (null != this.lastPart.getNextPart())
			this.lastPart = this.lastPart.getLastPart();

		return this.lastPart;
	}

	/**
	 * Returns path part leading to the element enclosing the one this path leads to
	 */
	public JCTPathPart getPathPartToEnclosing() {
		if (null == this.nextPart)
			return null;

		final JCTPathPart p =
			new JCTPathPart(this.resultKind, this.index, this.data);
		p.addPart(this.nextPart.getPathPartToEnclosing());
		return p;
	}

	public void addPart(final IJCTPathPart p) {
		if (null == p)
			return;

		final JCTPathPart part;

		if (p instanceof JCTPathPart)
			part = (JCTPathPart) p;
		else {
			part =
				new JCTPathPart(
					p.getResultKind(),
					p.getIndex(),
					p.getData(),
					p.getInformativeData());
			IJCTPathPart it = p.getNextPart();
			JCTPathPart to = part;
			while (null != it) {
				final JCTPathPart toAdd =
					new JCTPathPart(
						it.getResultKind(),
						it.getIndex(),
						it.getData(),
						it.getInformativeData());
				to.addPart(toAdd);
				to = toAdd;
				it = it.getNextPart();
			}
		}

		if (null != this.nextPart)
			this.getLastPart().addPart(part);
		else
			this.nextPart = part;

		this.lastPart = part.getLastPart();
	}

	/**
	 * Returns the element designated by this path part
	 */
	public IJCTElement walk(final IJCTElementContainer<? extends IJCTElement> e) {
		return this.walk(e, e == null || e.getRootNode().isInitialized());
	}

	protected IJCTElement walk(
		final IJCTElementContainer<? extends IJCTElement> e,
		final boolean displayError) {

		if (null == e) {
			if (displayError)
				System.err.println("NoSuchElement :: " + this + " / " + e);
			return null;
		}

		final IJCTElement r = this.resolve(e);

		return null == this.nextPart ? r : this.nextPart.walk(
			(IJCTElementContainer<? extends IJCTElement>) r,
			displayError);
	}

	protected IJCTElement resolve(
		final IJCTElementContainer<? extends IJCTElement> ee) {

		final Collection<IJCTElement> ec =
			ee instanceof JCTElementContainer ? ((JCTElementContainer<IJCTElement>) ee)
				.seeNextPathStep(this.resultKind)
					: ((IJCTContainer<IJCTElement>) ee).getEnclosedElements();

		IJCTElement e = null;
		if (null != this.index) {
			final Iterator<IJCTElement> it = ec.iterator();
			for (int i = 0; it.hasNext() && i < this.index; ++i)
				it.next();
			if (it.hasNext())
				e = it.next();
			else
				return null;
		}

		if (e == null
				|| (e instanceof JCTElement && !((JCTElement) e)
					.isDesignatedBy(this.data)))
			for (final IJCTElement el : ec)
				if (el instanceof JCTElement
						&& ((JCTElement) el).isDesignatedBy(this.data)) {
					e = el;
					break;
				}

		return e;
	}

	public IJCTElement walk(final IJCTRootNode aRootNode) {
		return this.walk((IJCTElementContainer<IJCTPackage>) aRootNode);
	}

	public final static String KIND_INDEX_SEPARATOR = "::";
	public final static String INDEX_DATA_SEPARATOR = ";";
	public final static String PART_SEPARATOR = "/";

	@Override
	public String toString() {
		final StringBuffer result =
			new StringBuffer()
				.append(JCTPathPart.PART_SEPARATOR)
				.append(this.resultKind.toString())
				.append(JCTPathPart.KIND_INDEX_SEPARATOR)
				.append(null == this.index ? null : this.index.toString())
				.append(JCTPathPart.INDEX_DATA_SEPARATOR)
				.append(this.data)
				.append(JCTPathPart.INDEX_DATA_SEPARATOR);

		if (null == this.informativeData) {
			result.append("null");
		}
		else {
			for (final byte b : this.informativeData) {
				result.append(String.format("%02X", b));
			}
		}

		if (null != this.nextPart) {
			result.append(this.nextPart.toString());
		}

		return result.toString();
	}

	@Override
	public JCTPathPart clone() {
		try {
			final JCTPathPart that = (JCTPathPart) super.clone();
			that.nextPart = that.nextPart.clone();
			that.lastPart = null;
			return that;
		}
		catch (final CloneNotSupportedException ex) {
			throw new IllegalStateException(ex);
		}
	}

	@Override
	public boolean equals(final Object that) {
		if (super.equals(that))
			return true;

		if (!(that instanceof IJCTPathPart))
			return false;

		final IJCTPathPart part = (IJCTPathPart) that;

		return this.getResultKind() == part.getResultKind()
				&& (this.getIndex() == null ? part.getIndex() == null : this
					.getIndex()
					.equals(part.getIndex()))
				&& (this.getData() == null ? part.getData() == null : this
					.getData()
					.equals(part.getData()))
				&& (this.getInformativeData() == null ? part
					.getInformativeData() == null : Arrays.equals(
					this.getInformativeData(),
					part.getInformativeData()));
	}

	private static final long serialVersionUID = 8293905259074720443L;

}
