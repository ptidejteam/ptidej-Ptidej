/**
 * @author Mathieu Lemoine
 * @created 2008-12-02 (火)
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
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.Spliterator;

import jct.kernel.Constants;
import jct.kernel.IJCTArrayType;
import jct.kernel.IJCTClass;
import jct.kernel.IJCTClassType;
import jct.kernel.IJCTElement;
import jct.kernel.IJCTFactory;
import jct.kernel.IJCTIntersectionType;
import jct.kernel.IJCTPackage;
import jct.kernel.IJCTPath;
import jct.kernel.IJCTPrimitiveType;
import jct.kernel.IJCTRootNode;
import jct.kernel.IJCTType;
import jct.kernel.IJCTVisitor;
import jct.kernel.JCTKind;
import jct.kernel.JCTPrimitiveTypes;
import jct.util.ListOfElements;
import jct.util.collection.ListOfUnique;
import jct.util.equiv.Identity;

/**
 * This class represents the root node of the tree
 * 
 * Default implementation for {@link jct.kernel.IJCTRootNode}
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
	private transient SoftReference<IJCTFactory> factory;

	/**
	 * List of orphans of this root node
	 */
	private final List<IJCTElement> orphans =
		ListOfElements.decorateList(new OrphanList());

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
	public IJCTElement walk(final IJCTPath aPath) {
		return aPath.walk(this);
	}

	/**
	 * Returns the Factory associated to this JCT
	 */
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
	public IJCTArrayType registerArrayType(final IJCTType underlyingType) {
		return this.registerArrayType(underlyingType, null);
	}

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
	public boolean isInitialized() {
		return this.initialized;
	}

	/**
	 * From now, this root node will assume that is has been initialized
	 */
	public void assumeInitialized() {
		this.initialized = true;
	}

	/**
	 * Adds a "package" in the set, if it was not already in it
	 */
	public void addPackage(final IJCTPackage aPackage) {
		this.packages.add(aPackage);
	}

	/**
	 * Removes this package from the set
	 */
	public void removePackage(final IJCTPackage aPackage) {
		this.packages.remove(aPackage);
	}

	/**
	 * Returns the set of packages of this root node
	 * <em>Part of the enclosed elements.</em>
	 */
	public Set<IJCTPackage> getPackages() {
		return Collections.unmodifiableSet(this.packages);
	}

	/**
	 * Adds a "orphan" at the index (or move it there)
	 */
	public void addOrphan(final int anIndex, final IJCTElement orphan) {
		this.orphans.add(anIndex, orphan);
	}

	/**
	 * Adds a "orphan" at the end of the list (or move it there)
	 */
	public void addOrphan(final IJCTElement orphan) {
		this.orphans.add(orphan);
	}

	/**
	 * Removes this orphan from the list
	 */
	public void removeOrphan(final IJCTElement orphan) {
		this.orphans.remove(orphan);
	}

	/**
	 * Remove the orphan at the index
	 */
	public void removeOrphan(final int anIndex) {
		this.orphans.remove(anIndex);
	}

	/**
	 * Returns the list of orphans of this root node
	 */
	public List<IJCTElement> getOrphans() {
		return Collections.unmodifiableList(this.orphans);
	}

	/**
	 * Returns the kind of this constituent (JCTKind.ROOT_NODE)
	 */
	public JCTKind getKind() {
		return JCTKind.ROOT_NODE;
	}

	/**
	 * Calls the appropriate visit* method on the visitor
	 */
	public <R, P> R accept(final IJCTVisitor<R, P> visitor, final P aP) {
		return visitor.visitRootNode(this, aP);
	}

	private class OrphanList extends ListOfUnique<IJCTElement> implements
			Serializable {
		private static final long serialVersionUID = 4627063330787906314L;

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
	private void discardOrphansCachedPathPartBuilderIndex(final JCTElement<?> e) {
		// Yann 2010/06/21: New, fastest implementation.
		final int startIndex = this.orphans.indexOf(e);
		for (int i = startIndex + 1; i < this.orphans.size(); i++) {
			final IJCTElement element = this.orphans.get(i);
			((JCTElement<?>) element).discardCachedPathPartBuilderIndex();
		}
		//	final Iterator<IJCTElement> it = this.getOrphans().iterator();
		//	// Reach e.
		//	while (it.hasNext() && e != it.next())
		//		;
		//	// Discard after e.
		//	while (it.hasNext()) {
		//		final IJCTElement elem = it.next();
		//		// Yann 2010/06/21: Unnecessary test?
		//		//	if (null != elem && elem instanceof JCTElement) {
		//		((JCTElement<?>) elem).discardCachedPathPartBuilderIndex();
		//	}
	}

	private final Map<JCTPrimitiveTypes, IJCTPrimitiveType> primitiveTypes =
		new HashMap<JCTPrimitiveTypes, IJCTPrimitiveType>();
	private transient final Map<String, WeakReference<IJCTArrayType>> arrayTypes =
		new HashMap<String, WeakReference<IJCTArrayType>>();
	private transient final Map<String, WeakReference<IJCTIntersectionType>> intersectionTypes =
		new HashMap<String, WeakReference<IJCTIntersectionType>>();
	private transient final Map<String, WeakReference<IJCTClassType>> classTypes =
		new HashMap<String, WeakReference<IJCTClassType>>();
	private transient final Set<WeakReference<IJCTClassType>> classesTypes =
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

	private IJCTClassType getCachedClassType(final String name) {
		final WeakReference<IJCTClassType> ref = this.classTypes.get(name);

		if (null == ref) {
			return null;
		}

		if (null == ref.get()) {
			this.classTypes.remove(name);
			return null;
		}

		return ref.get();
	}

	/**
	 * Returns the type designed by the path
	 */
	public <T extends IJCTType> T getType(final String path, final Class<T> type) {
		if (1 == path.length()) {
			return type.cast(this.getType(JCTPrimitiveTypes.resolveType(path)));
		}

		if (Constants.ARRAY_MARKER == path.charAt(0)) {
			IJCTArrayType t = this.getCachedArrayType(path);
			if (null == t) {
				this.arrayTypes.put(path, new WeakReference<IJCTArrayType>(t =
					new JCTArrayType(this, this.getType(
						path.substring(1),
						IJCTType.class))));
			}
			return type.cast(t);
		}

		if (Constants.INTERSECTION_MARKER == path.charAt(0)) {
			final String names[] =
				Constants.INTERSECTION_SPLITTER_PATTERN
					.split(path.substring(1));

			if (1 == names.length) {
				return this.getType(path.substring(1), type);
			}

			IJCTIntersectionType t = this.getCachedIntersectionType(path);
			if (null == t) {
				final IJCTType types[] = new IJCTType[names.length];
				for (int i = 0; i < names.length; ++i) {
					types[i] = this.getType(names[i], IJCTType.class);
				}
				this.intersectionTypes.put(
					path,
					new WeakReference<IJCTIntersectionType>(t =
						new JCTIntersectionType(this, types)));
			}
			return type.cast(t);
		}

		if (Constants.CLASS_MARKER == path.charAt(0)) {
			// Yann 2010/06/20: Why not cache the result of this branch?
			// I added the same cache mechanism as used in the previous
			// branches in the hope to speed-up computations.
			IJCTClassType t = this.getCachedClassType(path);
			if (null == t) {
				final JCTPath p = this.getPath();

				final int dotIndex = path.lastIndexOf(Constants.DOT_SEPARATOR);
				String classes[];
				if (-1 != dotIndex) {
					//p.addPart(new JCTPathPartBuilder(JCTKind.PACKAGE).setData(path.substring(1, dotIndex)).createPathPart());
					classes =
						Constants.DOLLAR_SPLITTER_PATTERN.split(path
							.substring(dotIndex + 1));
				}
				else {
					classes =
						Constants.DOLLAR_SPLITTER_PATTERN.split(path
							.substring(1));
				}

				for (final String s : classes) {
					p.addPart(new JCTPathPartBuilder(JCTKind.CLASS)
						.setData(s)
						.createPathPart());
				}

				final IJCTClass c = (IJCTClass) p.walk(this);

				this.classTypes.put(path, new WeakReference<IJCTClassType>(t =
					null == c ? null : c.createClassType()));
			}
			return type.cast(t);
		}

		throw new IllegalArgumentException(
			"A Type path must be a primitive type name (length = 1) or start by '"
					+ Constants.ARRAY_MARKER + "', '" + Constants.CLASS_MARKER
					+ "' or '" + Constants.INTERSECTION_MARKER + "'.\n" + path);
	}
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
		else if (!t.getUnderlyingType().getTypeName().equals(
			underlyingType.getTypeName()))
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

	//	private final Map<JCTKind, ListOfElements> cachedNextPathStep =
	//		new HashMap<JCTKind, ListOfElements>();

	@Override
	protected <T extends IJCTElement> ListOfElements<T> seeNextPathStep(
		final JCTKind aKind) {

		//	if (this.cachedNextPathStep.containsKey(aKind)) {
		//		return this.cachedNextPathStep.get(aKind);
		//	}

		ListOfElements<T> result = new ListOfElements<T>();

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
							result.add((T) type);
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
							result.add((T) type);
					}
				}
				break;
			case PRIMITIVE_TYPE :
				// return new ListOfElements(this.primitiveTypes.values());
				result = new ListOfElements(this.primitiveTypes.values());
				break;
			case CLASS_TYPE :
				// return (ListOfElements<T>) this.getAllClassTypes();
				result = (ListOfElements<T>) this.getAllClassTypes();
				break;
			case ROOT_NODE :
				result.add((T) this);
				break;
			case CLASS :
				result.addAll((Collection<T>) this.getAllEnclosedElements(
					JCTKind.CLASS,
					IJCTClass.class,
					true));
			default :
				// Yann 2010/0619: Speed-up!
				// By making the following changes, I removed
				// a performance bottleneck: when creating a model
				// for Juzzle, the old code used to spend 262s in
				// this loop, while the new code spends about 9s
				// here... Quite an improvement!
				// TODO: Check if the other uses of IndirectCollection could be optimised?
				//	for (final IJCTElement e : new IndirectCollection<IJCTElement>(
				//		this.getEnclosedElements(),
				//		this.getOrphans())) {
				//		if (aKind == e.getKind()) {
				//			result.add((T) e);
				//		}
				//	}
				for (final IJCTElement e : this.getEnclosedElements()) {
					if (aKind == e.getKind()) {
						result.add((T) e);
					}
				}
				for (final IJCTElement e : this.getOrphans()) {
					if (aKind == e.getKind()) {
						result.add((T) e);
					}
				}
		}

		//	System.out.print(aKind);
		//	System.out.print(": ");
		//	for (final T t : result) {
		//		System.out.print(t.getID());
		//		System.out.print(", ");
		//	}
		//	System.out.println();

		//	this.cachedNextPathStep.put(aKind, result);

		return result;
	}

	private void writeObject(final java.io.ObjectOutputStream out)
			throws IOException {
		out.defaultWriteObject();

		final Map<String, IJCTArrayType> arrayTypes =
			new HashMap<String, IJCTArrayType>();
		for (final Map.Entry<String, WeakReference<IJCTArrayType>> e : this.arrayTypes
			.entrySet()) {
			final IJCTArrayType type = e.getValue().get();
			if (null != type)
				arrayTypes.put(e.getKey(), type);
		}
		out.writeObject(arrayTypes);

		final Map<String, IJCTIntersectionType> intersectionTypes =
			new HashMap<String, IJCTIntersectionType>();
		for (final Map.Entry<String, WeakReference<IJCTIntersectionType>> e : this.intersectionTypes
			.entrySet()) {
			final IJCTIntersectionType type = e.getValue().get();
			if (null != type)
				intersectionTypes.put(e.getKey(), type);
		}
		out.writeObject(intersectionTypes);

		final Set<IJCTClassType> classesTypes = new HashSet<IJCTClassType>();
		for (final WeakReference<IJCTClassType> t : this.classesTypes) {
			final IJCTClassType type = t.get();
			if (null != type)
				classesTypes.add(type);
		}
		out.writeObject(classesTypes);
	}

	private void readObject(final java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		this.factory = new SoftReference<IJCTFactory>(null);

		try {
			final Field arrayTypesField =
				JCTRootNode.class.getDeclaredField("arrayTypes");
			arrayTypesField.setAccessible(true);
			arrayTypesField.set(
				this,
				new HashMap<String, WeakReference<IJCTArrayType>>());
			arrayTypesField.setAccessible(false);
			final Field intersectionTypesField =
				JCTRootNode.class.getDeclaredField("intersectionTypes");
			intersectionTypesField.setAccessible(true);
			intersectionTypesField.set(
				this,
				new HashMap<String, WeakReference<IJCTIntersectionType>>());
			intersectionTypesField.setAccessible(false);
			final Field classesTypesField =
				JCTRootNode.class.getDeclaredField("classesTypes");
			classesTypesField.setAccessible(true);
			classesTypesField.set(
				this,
				new HashSet<WeakReference<IJCTClassType>>());
			classesTypesField.setAccessible(false);
		}
		catch (final NoSuchFieldException ex) {
			throw new LinkageError(ex.toString());
		}
		catch (final IllegalAccessException ex) {
			throw new LinkageError(ex.toString());
		}

		final Map<String, IJCTArrayType> arrayTypes =
			(Map<String, IJCTArrayType>) in.readObject();
		for (final Map.Entry<String, IJCTArrayType> e : arrayTypes.entrySet())
			this.arrayTypes.put(e.getKey(), new WeakReference<IJCTArrayType>(e
				.getValue()));

		final Map<String, IJCTIntersectionType> intersectionTypes =
			(Map<String, IJCTIntersectionType>) in.readObject();
		for (final Map.Entry<String, IJCTIntersectionType> e : intersectionTypes
			.entrySet())
			this.intersectionTypes.put(
				e.getKey(),
				new WeakReference<IJCTIntersectionType>(e.getValue()));

		final Set<IJCTClassType> classesTypes =
			(Set<IJCTClassType>) in.readObject();
		for (final IJCTClassType t : classesTypes)
			this.classesTypes.add(new WeakReference<IJCTClassType>(t));
	}

	private static final long serialVersionUID = -2164333244376938613L;

}
