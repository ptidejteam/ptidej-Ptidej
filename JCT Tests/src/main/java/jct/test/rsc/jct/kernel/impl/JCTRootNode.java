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

package jct.test.rsc.jct.kernel.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

import jct.test.rsc.jct.kernel.Constants;
import jct.test.rsc.jct.kernel.IJCTArrayType;
import jct.test.rsc.jct.kernel.IJCTClass;
import jct.test.rsc.jct.kernel.IJCTClassType;
import jct.test.rsc.jct.kernel.IJCTElement;
import jct.test.rsc.jct.kernel.IJCTFactory;
import jct.test.rsc.jct.kernel.IJCTIntersectionType;
import jct.test.rsc.jct.kernel.IJCTPackage;
import jct.test.rsc.jct.kernel.IJCTPath;
import jct.test.rsc.jct.kernel.IJCTPrimitiveType;
import jct.test.rsc.jct.kernel.IJCTRootNode;
import jct.test.rsc.jct.kernel.IJCTType;
import jct.test.rsc.jct.kernel.IJCTVisitor;
import jct.test.rsc.jct.kernel.JCTKind;
import jct.test.rsc.jct.kernel.JCTPrimitiveTypes;
import jct.test.rsc.jct.util.ListOfElements;
import jct.test.rsc.jct.util.collection.IndirectCollection;
import jct.test.rsc.jct.util.collection.ListOfUnique;
import jct.test.rsc.jct.util.equiv.Identity;

/**
 * This class represents the root node of the tree
 * 
 * Default implementation for {@link jct.test.rsc.jct.kernel.IJCTRootNode}
 *
 * @author Mathieu Lemoine
 */
class JCTRootNode extends JCTElementContainer<IJCTPackage> implements
		IJCTRootNode {
	/**
	 * Set of packages of this root node
	 */
	private final Set<IJCTPackage> packages = this.createInternalSet();

	/**
	 * factory of this root node
	 */
	private SoftReference<IJCTFactory> factory;

	/**
	 * List of orphans of this root node
	 */
	private final List<IJCTElement> orphans = ListOfElements
		.decorateList(new OrphanList());

	/**
	 * initialized of this root node
	 */
	private boolean initialized = false;

	JCTRootNode(final String name) {
		super(null, name, null);
		this.factory = new SoftReference<IJCTFactory>(null);
		super.backpatchElements(this.packages);
	}

	/**
	 * Returns the element within the model.
	 * There is a difference between knowing the path and walking the path...
	 * Equivalent to {@code aPath.walk(this);}.
	 */
	@Override
	public IJCTElement walk(final IJCTPath aPath) {
		return aPath.walk(this);
	}

	/**
	 * Returns the Factory associated to this JCT
	 */
	@Override
	public IJCTFactory getFactory() {
		IJCTFactory f = this.factory.get();

		if (null == f)
			this.factory =
				new SoftReference<IJCTFactory>(f = new JCTFactory(this));
		return f;
	}

	/**
	 * Roughly equivalent to {@code this.registerArrayType(underlyingType, null);}.
	 */
	@Override
	public IJCTArrayType registerArrayType(final IJCTType underlyingType) {
		return this.registerArrayType(underlyingType, null);
	}

	@Override
	public IJCTPrimitiveType getType(
		final JCTPrimitiveTypes aPrimitiveTypeConstant) {
		IJCTPrimitiveType t = this.primitiveTypes.get(aPrimitiveTypeConstant);

		if (null == t)
			this.primitiveTypes.put(aPrimitiveTypeConstant, t =
				new JCTPrimitiveType(this, aPrimitiveTypeConstant));

		return t;
	}

	/**
	 * Writes the textual representation in the stream,
	 * the separators are not wrote in the writer.
	 */
	@Override
	public Writer getSourceCode(final Writer aWriter) throws IOException {
		for (final IJCTPackage p : this.getPackages())
			p.getSourceCode(aWriter);

		return aWriter;
	}

	/**
	 * Returns the path to the root node.
	 */
	@Override
	protected final JCTPathPartBuilder createPathPart() {
		return super.createPathPart().setKind(null);
	}

	/**
	 * Returns wether this root node has been initialized or not
	 */
	@Override
	public boolean isInitialized() {
		return this.initialized;
	}

	/**
	 * From now, this root node will assume that is has been initialized
	 */
	@Override
	public void assumeInitialized() {
		this.initialized = true;
	}

	/**
	 * Adds a "package" in the set, if it was not already in it
	 */
	@Override
	public void addPackage(final IJCTPackage aPackage) {
		this.packages.add(aPackage);
	}

	/**
	 * Removes this package from the set
	 */
	@Override
	public void removePackage(final IJCTPackage aPackage) {
		this.packages.remove(aPackage);
	}

	/**
	 * Returns the set of packages of this root node
	 * <em>Part of the enclosed elements.</em>
	 */
	@Override
	public Set<IJCTPackage> getPackages() {
		return Collections.unmodifiableSet(this.packages);
	}

	/**
	 * Adds a "orphan" at the index (or move it there)
	 */
	@Override
	public void addOrphan(final int anIndex, final IJCTElement orphan) {
		this.orphans.add(anIndex, orphan);
	}

	/**
	 * Adds a "orphan" at the end of the list (or move it there)
	 */
	@Override
	public void addOrphan(final IJCTElement orphan) {
		this.orphans.add(orphan);
	}

	/**
	 * Removes this orphan from the list
	 */
	@Override
	public void removeOrphan(final IJCTElement orphan) {
		this.orphans.remove(orphan);
	}

	/**
	 * Remove the orphan at the index
	 */
	@Override
	public void removeOrphan(final int anIndex) {
		this.orphans.remove(anIndex);
	}

	/**
	 * Returns the list of orphans of this root node
	 */
	@Override
	public List<IJCTElement> getOrphans() {
		return Collections.unmodifiableList(this.orphans);
	}

	/**
	 * Returns the kind of this constituent (JCTKind.ROOT_NODE)
	 */
	@Override
	public JCTKind getKind() {
		return JCTKind.ROOT_NODE;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	@Override
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitRootNode(this, aP);
	}

	private class OrphanList extends ListOfUnique<IJCTElement> {
		public OrphanList() {
			super(new Identity<IJCTElement>());
		}

		@Override
		public void add(final int i, final IJCTElement e) {
			final int old_index = this.indexOf(e);

			if (i == old_index)
				return;

			if (-1 != old_index) {
				final int max = i > old_index ? i : old_index;
				final ListIterator<IJCTElement> it =
					this.listIterator(i < old_index ? i : old_index);
				while (it.hasNext() && it.nextIndex() <= max) {
					final IJCTElement elem = it.next();
					if (elem instanceof JCTElement)
						((JCTElement) elem).discardCachedPathPartBuilderIndex();
				}
			}

			super.add(i, e);

			if (-1 == old_index)
				JCTRootNode.this
					.discardOrphansCachedPathPartBuilderIndex((JCTElement) e);
		}

		@Override
		public IJCTElement remove(final int i) {
			final JCTElement e = (JCTElement) this.get(i);
			JCTRootNode.this.discardOrphansCachedPathPartBuilderIndex(e);
			return super.remove(i);
		}

		@Override
		public Spliterator<IJCTElement> spliterator() {
			return super.spliterator();
		}
	}

	/**
	 * Discards the Cached Path Part Builder Index stored in each orphan after e
	 */
	private void discardOrphansCachedPathPartBuilderIndex(final JCTElement e) {
		final Iterator<IJCTElement> it = this.getOrphans().iterator();
		while (it.hasNext() && e != it.next()) {
		}
		while (it.hasNext()) {
			final IJCTElement elem = it.next();
			if (null != elem && elem instanceof JCTElement)
				((JCTElement) elem).discardCachedPathPartBuilderIndex();
		}
	}

	private final Map<String, WeakReference<IJCTArrayType>> arrayTypes =
		new HashMap<String, WeakReference<IJCTArrayType>>();
	private final Map<String, WeakReference<IJCTIntersectionType>> intersectionTypes =
		new HashMap<String, WeakReference<IJCTIntersectionType>>();
	private final Map<JCTPrimitiveTypes, IJCTPrimitiveType> primitiveTypes =
		new HashMap<JCTPrimitiveTypes, IJCTPrimitiveType>();
	private final Set<WeakReference<IJCTClassType>> classesTypes =
		new HashSet<WeakReference<IJCTClassType>>();

	private IJCTArrayType getCachedArrayType(final String name) {
		final WeakReference<IJCTArrayType> ref = this.arrayTypes.get(name);

		if (null == ref)
			return null;

		if (null == ref.get()) {
			this.arrayTypes.remove(name);
			return null;
		}

		return ref.get();
	}

	private IJCTIntersectionType getCachedIntersectionType(final String name) {
		final WeakReference<IJCTIntersectionType> ref =
			this.intersectionTypes.get(name);

		if (null == ref)
			return null;

		if (null == ref.get()) {
			this.intersectionTypes.remove(name);
			return null;
		}

		return ref.get();
	}

	/**
	 * Returns the type designed by the path
	 */
	@Override
	public <T extends IJCTType> T getType(final String path, final Class<T> type) {
		if (1 == path.length())
			return type.cast(this.getType(JCTPrimitiveTypes.resolveType(path)));

		if (Constants.ARRAY_MARKER == path.charAt(0)) {
			IJCTArrayType t = this.getCachedArrayType(path);
			if (null == t)
				this.arrayTypes.put(
					path,
					new WeakReference<IJCTArrayType>(t =
						new JCTArrayType(this, this.getType(
							path.substring(1),
							IJCTType.class))));
			return type.cast(t);
		}

		if (Constants.INTERSECTION_MARKER == path.charAt(0)) {
			final String names[] =
				Constants.INTERSECTION_SPLITTER_PATTERN
					.split(path.substring(1));
			if (1 == names.length)
				return this.getType(path.substring(1), type);

			IJCTIntersectionType t = this.getCachedIntersectionType(path);
			if (null == t) {
				final IJCTType types[] = new IJCTType[names.length];
				for (int i = 0; i < names.length; ++i)
					types[i] = this.getType(names[i], IJCTType.class);
				this.intersectionTypes.put(
					path,
					new WeakReference<IJCTIntersectionType>(t =
						new JCTIntersectionType(this, types)));
			}
			return type.cast(t);
		}

		if (Constants.CLASS_MARKER == path.charAt(0)) {
			final JCTPath p = this.getPath();

			final int dotIndex = path.lastIndexOf(Constants.DOT_SEPARATOR);
			String classes[];
			if (-1 != dotIndex) {
				//p.addPart(new JCTPathPartBuilder(JCTKind.PACKAGE).setData(path.substring(1, dotIndex)).createPathPart());
				classes =
					Constants.DOLLAR_SPLITTER_PATTERN.split(path
						.substring(dotIndex + 1));
			}
			else
				classes =
					Constants.DOLLAR_SPLITTER_PATTERN.split(path.substring(1));

			for (final String s : classes)
				p.addPart(new JCTPathPartBuilder(JCTKind.CLASS)
					.setData(s)
					.createPathPart());

			final IJCTClass c = (IJCTClass) p.walk(this);

			return type.cast(null == c ? null : c.createClassType());
		}

		throw new IllegalArgumentException(
			"A Type path must be a primitive type name (length = 1) or start by '"
					+ Constants.ARRAY_MARKER + "', '" + Constants.CLASS_MARKER
					+ "' or '" + Constants.INTERSECTION_MARKER + "'.\n" + path);
	}

	@Override
	public IJCTType getType(final IJCTType... types) {
		if (0 == types.length)
			return this.getType(JCTPrimitiveTypes.VOID);
		if (1 == types.length)
			return types[0];

		final String names[] = new String[types.length];

		for (int i = 0; i < types.length; ++i)
			names[i] = types[i].getTypeName();

		final StringBuilder typeName =
			new StringBuilder(Constants.INTERSECTION_MARKER);

		for (final String name : names)
			typeName.append(name).append(Constants.INTERSECTION_SEPARATOR);

		typeName.setLength(typeName.length() - 1);

		IJCTIntersectionType t =
			this.getCachedIntersectionType(typeName.toString());
		if (null == t)
			this.intersectionTypes.put(
				typeName.toString(),
				new WeakReference<IJCTIntersectionType>(t =
					new JCTIntersectionType(this, types)));

		return t;
	}

	@Override
	public IJCTArrayType registerArrayType(
		final IJCTType underlyingType,
		final String underlyingTypeName) {
		final String arrayTypePath =
			Constants.ARRAY_MARKER
					+ (null == underlyingTypeName ? underlyingType
						.getTypeName() : underlyingTypeName);

		IJCTArrayType t = this.getCachedArrayType(arrayTypePath);
		if (null == t)
			this.arrayTypes
				.put(arrayTypePath, new WeakReference<IJCTArrayType>(t =
					new JCTArrayType(this, underlyingType, underlyingTypeName)));
		else if (!t
			.getUnderlyingType()
			.getTypeName()
			.equals(underlyingType.getTypeName()))
			throw new IllegalStateException(
				"An array type is registered with this name ("
						+ underlyingTypeName
						+ "), but does not use the same underlying type.");

		return t;
	}

	protected void registerClassType(final IJCTClassType aClassType) {
		this.classesTypes.add(new WeakReference<IJCTClassType>(aClassType));
	}

	private ListOfElements<IJCTClassType> getAllClassTypes() {
		final ListOfElements<IJCTClassType> result =
			new ListOfElements<IJCTClassType>();
		final Iterator<WeakReference<IJCTClassType>> it =
			this.classesTypes.iterator();
		while (it.hasNext()) {
			final WeakReference<IJCTClassType> wct = it.next();
			if (null == wct.get())
				it.remove();
			else
				result.add(wct.get());
		}
		return result;
	}

	@Override
	public String getSourceCode() {
		try {
			final StringWriter w = new StringWriter();

			final String packageSeparator = new String(Character.toChars(0x1D)); // GROUP SEPARATOR control character

			final Iterator<IJCTPackage> it = this.getPackages().iterator();
			while (it.hasNext()) {
				it.next().getSourceCode(w);
				if (it.hasNext())
					w.append(packageSeparator);
			}

			return w.toString();
		}
		catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected ListOfElements<? extends IJCTElement> seeNextPathStep(
		final JCTKind aKind) {

		final ListOfElements<IJCTElement> result =
			new ListOfElements<IJCTElement>();

		switch (aKind) {
			case INTERSECTION_TYPE :
				{
					final Iterator<Map.Entry<String, WeakReference<IJCTIntersectionType>>> it =
						this.intersectionTypes.entrySet().iterator();
					while (it.hasNext()) {
						final Map.Entry<String, WeakReference<IJCTIntersectionType>> entry =
							it.next();
						final IJCTIntersectionType type =
							entry.getValue().get();
						if (null == type)
							it.remove();
						else
							result.add(type);
					}
				}
				break;
			case ARRAY_TYPE :
				{
					final Iterator<Map.Entry<String, WeakReference<IJCTArrayType>>> it =
						this.arrayTypes.entrySet().iterator();
					while (it.hasNext()) {
						final Map.Entry<String, WeakReference<IJCTArrayType>> entry =
							it.next();
						final IJCTArrayType type = entry.getValue().get();
						if (null == type)
							it.remove();
						else
							result.add(type);
					}
				}
				break;
			case PRIMITIVE_TYPE :
				return new ListOfElements<IJCTElement>(
					this.primitiveTypes.values());
			case CLASS_TYPE :
				return this.getAllClassTypes();
			case ROOT_NODE :
				result.add(this);
				break;
			case CLASS :
				result.addAll(this.getAllEnclosedElements(
					JCTKind.CLASS,
					IJCTClass.class,
					true));
			//$FALL-THROUGH$
		default :
				for (final IJCTElement e : new IndirectCollection<IJCTElement>(
					this.getEnclosedElements(),
					this.getOrphans()))
					if (aKind == e.getKind())
						result.add(e);
		}

		return result;
	}

}
